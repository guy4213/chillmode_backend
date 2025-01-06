package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.categoryDto.response.CategoryResponseDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryResponseDto;
import guy.example.FinalprojectUpdated.dto.directordto.request.AddPostRequestDirectorDto;
import guy.example.FinalprojectUpdated.dto.directordto.request.PutUpdateRequestDirector;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorListDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorResponseDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DirectorResponseDto;
import guy.example.FinalprojectUpdated.entity.Director;
import guy.example.FinalprojectUpdated.error.ResourceNotFoundException;
import guy.example.FinalprojectUpdated.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    public DirectorResponseDto addDirector(AddPostRequestDirectorDto dto){
        //request dto=> Director
        var DirectorEntity=modelMapper.map(dto, Director.class);
        //Director->saved Director
        var saved= directorRepository.save(DirectorEntity);
        //saved Director->Response Dto
        return modelMapper.map(saved,DirectorResponseDto.class);
    }
    public List<DirectorResponseDto> getAll() {
        var all = directorRepository.findAll();
        return  all.stream().map(m->modelMapper.map(m,DirectorResponseDto.class)).toList();
    }
    public List<DirectorResponseDto> getTop(int top) {
        ArrayList<Director> sorted = new ArrayList<>(directorRepository.findAll());
              return get( top, sorted );
    }

    public List<DirectorResponseDto> getLast(int last) {
        ArrayList<Director> sorted = new ArrayList<>(directorRepository.findAll());
        Collections.reverse(sorted);
        return get(last,sorted);
    }


    public List<DirectorResponseDto> get(int top,ArrayList<Director> sorted ) {
        if(top > sorted.size())
            top =sorted.size();
        var sublist =sorted.subList(0, top).stream().map(director -> modelMapper.map(director, DirectorResponseDto.class));
        return sublist.toList();
    }


    public DeleteDirectorResponseDto deleteDirectorById(long id) {
        //check for existence before deleting:
        var director = directorRepository.findById(id);

        // Delete:
        try {
            directorRepository.deleteById(id);

            // Return DTO if actor was deleted successfully:
            if (director.isPresent()) {
                return DeleteDirectorResponseDto.builder()
                        .deleted(true)
                        .directorResponseDto(modelMapper.map(director.get(), DirectorResponseDto.class))
                        .build();
            } else {
                return DeleteDirectorResponseDto.builder()
                        .deleted(false)
                        .directorResponseDto(null)
                        .build();
            }
        } catch (DataIntegrityViolationException e) {
            // Handle constraint violation exception
            logger.error("Error deleting director with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete director with ID: " + id + " due to existing references", e);
        } catch (EmptyResultDataAccessException e) {
            // Handle non-existent actor
            logger.error("Error deleting director with ID: " + id + ". director not found.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "director with ID: " + id + " not found.", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error deleting director with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting director with ID: " + id, e);
        }
    }

    public DirectorResponseDto update(long id, PutUpdateRequestDirector req) {

    Director director=getDirectorByIdOrThrow(id);

    //update:
        if(req.directorName()!=null&&   !req.directorName().trim().isEmpty()){
            director.setDirectorName(req.directorName());
        }
        if(req.city()!=null&&   !req.city().trim().isEmpty()) {
            director.setCity(req.city());
        }
        if(req.country()!=null&&   !req.country().trim().isEmpty()) {
            director.setCountry(req.country());
        }
    var saved = directorRepository.save(director);
    return modelMapper.map(saved, DirectorResponseDto.class);
}

    public DirectorResponseDto getDirectorById( long id){
        Director director=getDirectorByIdOrThrow(id);

            Director saved = directorRepository.save(director);
            return modelMapper.map(saved,DirectorResponseDto.class);

    }

public DeleteDirectorListDto deleteAll() {
        var all = directorRepository.findAll();
        var x = all.stream().map(m -> modelMapper.map(m, Director.class)).toList();

        x.forEach(director -> directorRepository.deleteById(director.getId()));
        return DeleteDirectorListDto.builder()
                .deleted(!x.isEmpty()).build();
    }




    private Director getDirectorByIdOrThrow(long id) {
    return directorRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("director", "id", id)
    );
}

}
