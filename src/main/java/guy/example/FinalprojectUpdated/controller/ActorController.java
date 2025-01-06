package guy.example.FinalprojectUpdated.controller;

import guy.example.FinalprojectUpdated.dto.actorDto.request.AddPostReqActorDto;
import guy.example.FinalprojectUpdated.dto.actorDto.request.PutUpdateRequestActor;
import guy.example.FinalprojectUpdated.dto.actorDto.response.ActorResponseDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorListDto;
import guy.example.FinalprojectUpdated.dto.actorDto.response.DeleteActorResponseDto;
import guy.example.FinalprojectUpdated.service.ActorService;
import guy.example.FinalprojectUpdated.service.ActorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/actors")
@CrossOrigin
public class ActorController {
    //props:
    private final ActorServiceImpl  actorService;

    @PostMapping
    public ResponseEntity<ActorResponseDto> addActor(@Valid @RequestBody AddPostReqActorDto dto,
                                                     UriComponentsBuilder uriBuilder) {
        var res = actorService.addActor(dto);
        var id= res.getId();

         var uri = uriBuilder.path("/api/v1/actors/{id}").buildAndExpand(res.getId()).toUri();

        return ResponseEntity.created(uri).body(res);
    }

    @GetMapping
    public ResponseEntity<Collection<ActorResponseDto>> getAll(){

        var res = actorService.getAll();

        return ResponseEntity.ok(res);

    }  @GetMapping("/top/{num}")
    public ResponseEntity<List<ActorResponseDto>> top(@PathVariable int num) {
        return ResponseEntity.ok(actorService.getTop(num));
    }
    @GetMapping("/last/{num}")
    public ResponseEntity<List<ActorResponseDto>> last(@PathVariable int num) {
        return ResponseEntity.ok(actorService.getLast(num));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteActorResponseDto> deleteActorById(@PathVariable long id){

        return ResponseEntity.ok(actorService.deleteActorById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponseDto> getActorById(@PathVariable long id){
        return ResponseEntity.ok(actorService.getActorById(id));

    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<DeleteActorListDto> deleteAll(){

        var res = actorService.deleteAll();

        return ResponseEntity.ok(res);

    }
@PutMapping("/{id}")
public ResponseEntity<ActorResponseDto> updateActor(
        @PathVariable long id,
        @Valid @RequestBody PutUpdateRequestActor req) {

    return ResponseEntity.ok(actorService.update(id,req));
}


}

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