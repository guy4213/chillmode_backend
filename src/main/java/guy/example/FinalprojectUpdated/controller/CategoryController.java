package guy.example.FinalprojectUpdated.controller;

import guy.example.FinalprojectUpdated.dto.categoryDto.request.AddPostRequestCategoryDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.request.PutUpdateRequestCategory;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.CategoryResponseDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryListDto;
import guy.example.FinalprojectUpdated.dto.categoryDto.response.DeleteCategoryResponseDto;
import guy.example.FinalprojectUpdated.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryController {
    //props:
    private final CategoryServiceImpl  categoryService;

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> addCategory
            (@Valid @RequestBody AddPostRequestCategoryDto dto,
               UriComponentsBuilder uriBuilder) {
        var res = categoryService.addCategory(dto);
        var uri = uriBuilder.path("/api/v1/categories/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(uri).body(res);
    }

    @GetMapping()
    public ResponseEntity<Collection<CategoryResponseDto>> getAll(){

        var res = categoryService.getAll();

        return ResponseEntity.ok(res);

    }  @GetMapping("/top/{num}")
    public ResponseEntity<List<CategoryResponseDto>> top(@PathVariable int num) {
        return ResponseEntity.ok(categoryService.getTop(num));
    }
    @GetMapping("last/{num}")
    public ResponseEntity<List<CategoryResponseDto>> last(@PathVariable int num) {
        return ResponseEntity.ok(categoryService.getLast(num));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCategoryResponseDto> deleteCategoryById(@PathVariable long id){

        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }

@PutMapping("/{id}")
public ResponseEntity<CategoryResponseDto> updateCategory(
        @PathVariable long id,
        @Valid @RequestBody PutUpdateRequestCategory req) {

    return ResponseEntity.ok(categoryService.update(id,req));
}

@DeleteMapping("/deleteAll")
public ResponseEntity<DeleteCategoryListDto> deleteAll(){

    var res = categoryService.deleteAll();

    return ResponseEntity.ok(res);

}

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));

    }
}

//@DeleteMapping("/deleteAll")
//public ResponseEntity<DeleteActorListDto> deleteAll(){
//
//    var res = actorService.deleteAll();
//
//    return ResponseEntity.ok(res);
//
//}
//public DeleteActorListDto deleteAll() {
//        var all = actorRepository.findAll();
//        var x = all.stream().map(m -> modelMapper.map(m, Actor.class)).toList();
//
//        x.forEach(actor -> {
//            actorRepository.deleteById(actor.getId());
//
//        });
//        return DeleteActorListDto.builder()
//                .deleted(!x.isEmpty()).build();
//    }



//@PutMapping("/{id}")
//public ResponseEntity<UserResponseDTO> updateUser(
//        @PathVariable long id,
//        @Valid @RequestBody PutUpdateRequestUser req) {
//
//    return ResponseEntity.ok(userService.update(id,req));
//}
//
//public UserResponseDTO update(long id,PutUpdateRequestUser req) {
//
//    User user=getUserByIdOrThrow(id);
//
//    //update:
//    user.setEmail(req.email());
//    user.setPassword(req.password());
//    var saved = userRepository.save(user);
//    return modelMapper.map(saved, UserResponseDTO.class);
//}
//
//private Movie getMovieByIdOrThrow(long id) {
//    return movieRepository.findById(id).orElseThrow(
//            () -> new ResourceNotFoundException("Movie", "id", id)
//    );
//}