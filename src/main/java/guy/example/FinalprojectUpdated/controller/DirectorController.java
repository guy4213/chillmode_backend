package guy.example.FinalprojectUpdated.controller;

import guy.example.FinalprojectUpdated.dto.directordto.request.AddPostRequestDirectorDto;
import guy.example.FinalprojectUpdated.dto.directordto.request.PutUpdateRequestDirector;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorListDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DeleteDirectorResponseDto;
import guy.example.FinalprojectUpdated.dto.directordto.response.DirectorResponseDto;
import guy.example.FinalprojectUpdated.service.DirectorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/directors")
@CrossOrigin("")
public class DirectorController {
    //props:
    private final DirectorServiceImpl directorService;

    @PostMapping
    public ResponseEntity<DirectorResponseDto> addDirector(@Valid @RequestBody AddPostRequestDirectorDto dto) {
        var res = directorService.addDirector(dto);
        var id= res.getId();

        var uri = URI.create("/directors/%d".formatted(id));

        return ResponseEntity.created(uri).body(res);
    }
    @GetMapping
    public ResponseEntity<Collection<DirectorResponseDto>> getAll(){

        var res = directorService.getAll();

        return ResponseEntity.ok(res);

    }
    @GetMapping("/top/{num}")
    public ResponseEntity<List<DirectorResponseDto>> top(@PathVariable int num) {
        return ResponseEntity.ok(directorService.getTop(num));
    }
    @GetMapping("/last/{num}")
    public ResponseEntity<List<DirectorResponseDto>> last(@PathVariable int num) {
        return ResponseEntity.ok(directorService.getLast(num));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDirectorResponseDto> deleteDirectorById(@PathVariable long id){

        return ResponseEntity.ok(directorService.deleteDirectorById(id));
    }


@PutMapping("/{id}")
public ResponseEntity<DirectorResponseDto> updateDirector(
        @PathVariable long id,
        @Valid @RequestBody PutUpdateRequestDirector req) {

    return ResponseEntity.ok(directorService.update(id,req));
}

@DeleteMapping("/deleteAll")
public ResponseEntity<DeleteDirectorListDto> deleteAll(){

    var res = directorService.deleteAll();

    return ResponseEntity.ok(res);

}


    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponseDto> getDirectorById(@PathVariable long id){
        return ResponseEntity.ok(directorService.getDirectorById(id));

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