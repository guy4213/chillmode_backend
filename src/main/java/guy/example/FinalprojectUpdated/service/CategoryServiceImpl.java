package guy.example.FinalprojectUpdated.service;

import guy.example.FinalprojectUpdated.dto.actorDto.response.ActorResponseDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorResponseDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.request.AddPostRequestCategoryDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.request.PutUpdateRequestCategory;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.CategoryResponseDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryListDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryResponseDto;
import guy.example.FinalprojectUpdated.entity.Category;
import guy.example.FinalprojectUpdated.error.ResourceNotFoundException;
import guy.example.FinalprojectUpdated.repository.CategoryRepository;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements  CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    public CategoryResponseDto addCategory(AddPostRequestCategoryDto dto) {
        //request dto=> category
        var categoryEntity = modelMapper.map(dto, Category.class);
        //Series->saved category
        var saved = categoryRepository.save(categoryEntity);
        //saved category->Response Dto
        return modelMapper.map(saved, CategoryResponseDto.class);
    }


    public Collection<CategoryResponseDto> getAll() {
        var all = categoryRepository.findAll();
        return all.stream().map(m -> modelMapper.map(m, CategoryResponseDto.class)).toList();
    }

    public List<CategoryResponseDto> getTop(int top) {
        ArrayList<Category> sorted = new ArrayList<>(categoryRepository.findAll());
        return get(top, sorted);
    }

    public List<CategoryResponseDto> getLast(int last) {
        ArrayList<Category> sorted = new ArrayList<>(categoryRepository.findAll());
        Collections.reverse(sorted);
        return get(last, sorted);
    }


    //get-getting the top||last by number- improves and shorten the code
    public List<CategoryResponseDto> get(int top, ArrayList<Category> sorted) {
        if (top > sorted.size())
            top = sorted.size();
        var sublist = sorted.subList(0, top).stream().map(actor -> modelMapper.map(actor, CategoryResponseDto.class));
        return sublist.toList();
    }

    public DeleteCategoryResponseDto deleteCategoryById(long id) {
        //check for existence before deleting:
        var category = categoryRepository.findById(id);

        // Delete:
        try {
            categoryRepository.deleteById(id);

            // Return DTO if actor was deleted successfully:
            if (category.isPresent()) {
                return DeleteCategoryResponseDto.builder()
                        .deleted(true)
                        .categoryResponseDto(modelMapper.map(category.get(), CategoryResponseDto.class))
                        .build();
            } else {
                return DeleteCategoryResponseDto.builder()
                        .deleted(false)
                        .categoryResponseDto(null)
                        .build();
            }
        } catch (DataIntegrityViolationException e) {
            // Handle constraint violation exception
            logger.error("Error deleting category with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete category with ID: " + id + " due to existing references", e);
        } catch (EmptyResultDataAccessException e) {
            // Handle non-existent actor
            logger.error("Error deleting category with ID: " + id + ". category not found.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with ID: " + id + " not found.", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Error deleting category with ID: " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting category with ID: " + id, e);
        }
    }

    public CategoryResponseDto update(long id, PutUpdateRequestCategory req) {

        Category category = getCategoryByIdOrThrow(id);

        //update:
        if(req.name()!=null&&   !req.name().trim().isEmpty()){
            category.setName(req.name());
        }
        if(req.description()!=null&&   !req.description().trim().isEmpty()) {
            category.setDescription(req.description());
        }

        var saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryResponseDto.class);
    }

    public CategoryResponseDto getCategoryById( long id) {
        Category category = getCategoryByIdOrThrow(id);

        // Ensure that the series set is loaded before mapping to DTO
//        System.out.println(category.getSeries().size());

        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public DeleteCategoryListDto deleteAll() {
        var all = categoryRepository.findAll();
        var x = all.stream().map(m -> modelMapper.map(m, Category.class)).toList();

        x.forEach(category -> categoryRepository.deleteById(category.getId()));
        return DeleteCategoryListDto.builder()
                .deleted(!x.isEmpty()).build();
    }


    Category getCategoryByIdOrThrow(long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );
    }

}
