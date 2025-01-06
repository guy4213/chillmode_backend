package guy.example.FinalprojectUpdated.service;

import guy.example.FinalprojectUpdated.dto.categoryDto.request.AddPostRequestCategoryDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.request.PutUpdateRequestCategory;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.CategoryResponseDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryListDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryResponseDto;
import guy.example.FinalprojectUpdated.entity.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface  CategoryService {
     CategoryResponseDto addCategory(AddPostRequestCategoryDto dto);


     Collection<CategoryResponseDto> getAll();

     List<CategoryResponseDto> getTop(int top);

     List<CategoryResponseDto> getLast(int last);


    //get-getting the top||last by number- improves and shorten the code
     List<CategoryResponseDto> get(int top, ArrayList<Category> sorted);

    DeleteCategoryResponseDto deleteCategoryById(long id);

     CategoryResponseDto update(long id, PutUpdateRequestCategory req);
     CategoryResponseDto getCategoryById( long id);

     DeleteCategoryListDto deleteAll();
}
