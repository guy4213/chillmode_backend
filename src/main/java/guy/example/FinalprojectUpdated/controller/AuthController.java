package guy.example.FinalprojectUpdated.controller;



import guy.example.FinalprojectUpdated.dto.userDto.request.LoginRequestDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.LoginResponseDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import guy.example.FinalprojectUpdated.service.AuthService;
import guy.example.FinalprojectUpdated.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

@Tag(
        name = "Auth Control",
        description = "authentication "
)
public class AuthController {

    private final AuthServiceImpl authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto){
    var resDto=authService.signIn(dto);
    return ResponseEntity.ok(resDto);
    }



    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid PostUserSignUpDTO dto, UriComponentsBuilder uriBuilder) {
        return ResponseEntity.created(uriBuilder.path("/login").build().toUri()).body(authService.signUp(dto));
    }

    @Hidden
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> userDetails(Authentication authentication) {
        return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "authorities", authentication.getAuthorities()
                )
        );
    }
}

//SortAlgorithm
//QuickSortImpl
//BubbleSortImpl