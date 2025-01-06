package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.seriesDto.SeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.AddSeriesRequestDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.request.UpdateRequestSeries;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesListDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.DeleteSeriesResponseDto;
import guy.example.FinalprojectUpdated.dto.seriesDto.response.SeriesResponseDto;
import guy.example.FinalprojectUpdated.entity.*;
import guy.example.FinalprojectUpdated.error.PaginationException;
import guy.example.FinalprojectUpdated.error.RateOutOFRangeException;
import guy.example.FinalprojectUpdated.error.ResourceNotFoundException;
import guy.example.FinalprojectUpdated.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final RateAvgRepository rateAvgRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;
    private final ModelMapper modelMapper;


    public SeriesResponseDto addRate(long userId, long seriesId, float rate) throws Exception {
        if (rate < 0 || rate > 10) {
            System.out.println("rate must be btw 0-10");
            throw new RateOutOFRangeException(rate);
        }
        Series seriesEntity = getSeriesByIdOrThrow(seriesId);
        Optional<RateAvg> existingRating = rateAvgRepository.findByUserIdAndSeriesId(userId, seriesId);
        if (existingRating.isPresent()) {
            // Update existing rating
            RateAvg ratingToUpdate = existingRating.get();
            float oldRating = ratingToUpdate.getRating();
            ratingToUpdate.setRating(rate);
            rateAvgRepository.save(ratingToUpdate);

            seriesEntity.setAverageRate(updateAverageRating(seriesId, oldRating, rate));
        } else {
            // Add new rating
            RateAvg newRating = new RateAvg();
            newRating.setUserId(userId);
            newRating.setSeriesId(seriesId);
            newRating.setRating(rate);
            rateAvgRepository.save(newRating);
            seriesEntity.setAverageRate(updateAverageRating(seriesId, 0, rate));

        }
        var saved = seriesRepository.save(seriesEntity);

        return modelMapper.map(saved, SeriesResponseDto.class);
    }


    private Double updateAverageRating(Long seriesId, float oldRating, float newRating) {
        List<RateAvg> ratings = rateAvgRepository.findAllBySeriesId(seriesId);
        int totalRatings = ratings.size();
        double sumRatings = ratings.stream().mapToDouble(RateAvg::getRating).sum();
        double averageRating = totalRatings > 0 ? sumRatings / totalRatings : 0;
        return averageRating;
        // Update average rating of the series
        // You can implement the logic to update the average rating of the series here
        // For example, update the series entity in the database with the new average rating
    }

    public SeriesResponseDto addSeries(AddSeriesRequestDto dto) {
        //request dto=> Series
        var seriesEntity = modelMapper.map(dto, Series.class);
        //Series->saved Series
        var saved = seriesRepository.save(seriesEntity);
        //saved Series->Response Dto
        return modelMapper.map(saved, SeriesResponseDto.class);
    }

    public SeriesResponseDto addCategories(long seriesId, String[] categoryNames) {
        Series seriesEntity = getSeriesByIdOrThrow(seriesId);

        var categoryList = new ArrayList<Category>();

        // Create a list to hold names of actors not found
        List<String> notFoundCategories = new ArrayList<>();

        // Iterate over actor names
        for (String categoryName : categoryNames) {
            // Find actor by name
            Category category = categoryRepository.findCategoryByNameIgnoreCase(categoryName);

            // If actor is found, add it to the list, otherwise add the name to notFoundActors list
            if (category != null) {
                categoryList.add(category);
            } else {
                notFoundCategories.add(categoryName);
            }
        }

        // Add only the found actors to the seriesEntity
        seriesEntity.getCategories().addAll(categoryList);

        // Save seriesEntity
        Series savedSeries = seriesRepository.save(seriesEntity);

        // Map saved seriesEntity to SeriesResponseDto
        SeriesResponseDto responseDto = modelMapper.map(savedSeries, SeriesResponseDto.class);

        // If any actors were not found, throw an IllegalArgumentException with appropriate message
        if (!notFoundCategories.isEmpty()) {
            throw new IllegalArgumentException("Categories not found: " + notFoundCategories + "; " +
                    "Categories that have been added: " + categoryList.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(", ")));

        }

        // Return the SeriesResponseDto
        return responseDto;
    }

    public SeriesResponseDto addActors(long seriesId, String[] actorNames) {
        Series seriesEntity = getSeriesByIdOrThrow(seriesId);

        var actorList = new ArrayList<Actor>();

        // Create a list to hold names of actors not found
        List<String> notFoundActors = new ArrayList<>();

        // Iterate over actor names
        for (String actorName : actorNames) {
            // Find actor by name
            Actor actor = actorRepository.findActorByActorNameIgnoreCase(actorName);

            // If actor is found, add it to the list, otherwise add the name to notFoundActors list
            if (actor != null) {
                actorList.add(actor);
            } else {
                notFoundActors.add(actorName);
            }
        }

        // Add only the found actors to the seriesEntity
        seriesEntity.getActors().addAll(actorList);

        // Save seriesEntity
        Series savedSeries = seriesRepository.save(seriesEntity);

        // Map saved seriesEntity to SeriesResponseDto
        SeriesResponseDto responseDto = modelMapper.map(savedSeries, SeriesResponseDto.class);

        // If any actors were not found, throw an IllegalArgumentException with appropriate message
        if (!notFoundActors.isEmpty()) {
            throw new IllegalArgumentException("Actors not found: " + notFoundActors + "; " +
                    "Actors that have been added: " + actorList.stream()
                    .map(Actor::getActorName)
                    .collect(Collectors.joining(", ")));

        }

        // Return the SeriesResponseDto
        return responseDto;
    }

    public SeriesResponseDto addDirector(long seriesId, String DirectorName) {
        Series seriesEntity = getSeriesByIdOrThrow(seriesId);
        Director director = directorRepository.findDirectorByDirectorNameIgnoreCase(DirectorName);
        if (director==null) {
            throw new IllegalArgumentException(" Director " +DirectorName +" cant be found");
        }
        seriesEntity.setDirector(director);
        var saved = seriesRepository.save(seriesEntity);
        System.out.println(saved.getDirector());
        return modelMapper.map(saved, SeriesResponseDto.class);
    }


    //getAllPosts(1, 10, "asc", "title", "author", "releaseDate")

    public SeriesListDto getAll(int pageNo, int pageSize, String sortDir, String... sortBy) {
        try {
            //Direction from String ('asc/des')
            Sort.Direction sort = Sort.Direction.fromString(sortDir);
            //build the page req
            var pageable = PageRequest.of(pageNo, pageSize, sort, sortBy);

            //get the page result from the repository:
            Page<Series> pr = seriesRepository.findAll(pageable);

            if (pageNo > pr.getTotalPages()) {
                throw new PaginationException("Series " + pageNo + " pageNo " + pr.getTotalPages());
            }
            // transferring data into the SeriesListDto
            List<SeriesResponseDto> seriesListDto =
                    pr.getContent().stream()
                            .map(p -> modelMapper.map(p, SeriesResponseDto.class))
                            .toList();

            return new SeriesListDto(
                    pr.getTotalElements(),
                    pr.getNumber(),
                    pr.getSize(),
                    pr.getTotalPages(),
                    pr.isFirst(),
                    pr.isLast(),
                    seriesListDto
            );
        } catch (IllegalArgumentException e) {
            throw new PaginationException((e.getMessage()));

        }
    }

    public List<SeriesResponseDto> getTop(int top) {
        ArrayList<Series> sorted = new ArrayList<>(seriesRepository.findAll());
        return get(top, sorted);
    }

    public List<SeriesResponseDto> getLast(int last) {
        ArrayList<Series> sorted = new ArrayList<>(seriesRepository.findAll());
        Collections.reverse(sorted);
        return get(last, sorted);
    }


    //get-getting the top||last by number- improves and shorten the code
    public List<SeriesResponseDto> get(int top, ArrayList<Series> sorted) {
        //if the number the user entered
        // is bigger than arralist size-> we equalise it to the array size
        //to prevent an error
        if (top > sorted.size() || top < 0)
            top = sorted.size();
        var sublist = sorted.subList(0, top).stream().map(series -> modelMapper.map(series, SeriesResponseDto.class));
        return sublist.toList();
    }


    public DeleteSeriesResponseDto deleteSeriesById(long id) {
        var series = seriesRepository.findById(id);

        // Delete:
        try {
            seriesRepository.deleteById(id);

            // Return DTO if actor was deleted successfully:
            if (series.isPresent()) {
                return DeleteSeriesResponseDto.builder()
                        .deleted(true)
                        .seriesResponseDto(modelMapper.map(series.get(), SeriesResponseDto.class))
                        .build();
            } else {
                return DeleteSeriesResponseDto.builder()
                        .deleted(false)
                        .seriesResponseDto(null)
                        .build();
            }
        } catch (DataIntegrityViolationException e) {
            // Handle constraint violation exception
            logger.error("Error deleting series with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete series with ID: " + id + " due to existing references", e);
        } catch (EmptyResultDataAccessException e) {
            // Handle non-existent actor
            logger.error("Error deleting series with ID: " + id + ". series not found.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "series with ID: " + id + " not found.", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error deleting series with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting series with ID: " + id, e);
        }
    }

    public DeleteSeriesListDto deleteAll() {
        var all = seriesRepository.findAll();
        var x= all.stream().map(m->modelMapper.map(m,Series.class)).toList();

        x.forEach(series -> seriesRepository.deleteById(series.getId()));

       return DeleteSeriesListDto.builder()
                .deleted(!x.isEmpty()).build();
    }
public SeriesResponseDto update(long id, UpdateRequestSeries req) {

    Series series=getSeriesByIdOrThrow(id);

    //update:
    if(req.seriesName()!=null&&   !req.seriesName().trim().isEmpty()){
        series.setSeriesName(req.seriesName());
    }
    if(req.trailer()!=null&&   !req.trailer().trim().isEmpty()) {
        series.setTrailer(req.trailer());
    }

    if(req.img()!=null && !req.img().trim().isEmpty()){
        series.setImg(req.img());
    }
    if(req.seriesDescription()!=null && !req.seriesDescription().trim().isEmpty()){
        series.setSeriesDescription(req.seriesDescription());
    }
    if(req.publishedYear()!=0){
        series.setPublishedYear(req.publishedYear());
    }
    if(req.numberOfEpisodes()!=0){
        series.setNumberOfEpisodes(req.numberOfEpisodes());
    }




    var saved = seriesRepository.save(series);
    return modelMapper.map(saved, SeriesResponseDto.class);
}


    public SeriesResponseDto getSeriesById(long id){
        Series series=getSeriesByIdOrThrow(id);
        var saved = seriesRepository.save(series);
        return modelMapper.map(saved,SeriesResponseDto.class);
        }

     Series getSeriesByIdOrThrow(long id) {
        return seriesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Series", "id", id)
        );
    }
    public List<SeriesResponseDto> search( String name){

         var ContainsList =  seriesRepository.findAll().stream().filter
                 (series ->  series.getSeriesName().toLowerCase().contains(name.toLowerCase()));
        return ContainsList.map(series -> modelMapper.map(series, SeriesResponseDto.class)).toList();
    }


}
