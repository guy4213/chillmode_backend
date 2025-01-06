package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.userDto.request.LoginRequestDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.LoginResponseDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserResponseDTO signUp(PostUserSignUpDTO dto);

    LoginResponseDTO signIn(LoginRequestDTO dto);
}
