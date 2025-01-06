package guy.example.FinalprojectUpdated.controller;

import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PutUpdateRequestUser;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserListDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserResponseDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import guy.example.FinalprojectUpdated.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    //props:
    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody PostUserSignUpDTO dto) {
        var res = userService.signUp(dto);
        var id = res.getId();

        var uri = URI.create("/users/%d".formatted(id));

        return ResponseEntity.created(uri).body(res);
    }

    @GetMapping("/ALL")

    public ResponseEntity<Collection<UserResponseDTO>> getAll() {

        var res = userService.getAll();

        return ResponseEntity.ok(res);

    }

    @GetMapping("/top/{num}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> top(@PathVariable int num) {
        return ResponseEntity.ok(userService.getTop(num));
    }

    @GetMapping("/last/{num}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> last(@PathVariable int num) {
        return ResponseEntity.ok(userService.getLast(num));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteUserResponseDto> deleteUserById(@PathVariable long id) {

        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteUserListDto> deleteAll() {

        var res = userService.deleteAll();

        return ResponseEntity.ok(res);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable long id,
            @Valid @RequestBody PutUpdateRequestUser req) {

        return ResponseEntity.ok(userService.update(id, req));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @GetMapping("getUserByUserName/{userName}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUserByUserName(userName));
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

