package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.userDto.request.LoginRequestDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.LoginResponseDTO;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import guy.example.FinalprojectUpdated.error.AuthenticationException;
import guy.example.FinalprojectUpdated.error.UserAlreadyExistsException;
import guy.example.FinalprojectUpdated.repository.RoleRepository;
import guy.example.FinalprojectUpdated.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import guy.example.FinalprojectUpdated.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //props:
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;

    @Override
    public UserResponseDTO signUp(PostUserSignUpDTO dto) {

        //check that the user does not exist email/username:
        userRepository.findUserByUserNameIgnoreCaseOrEmailIgnoreCase(dto.getUserName(), dto.getEmail()).ifPresent((u) -> {
            throw new UserAlreadyExistsException(u.getUserName(), u.getEmail());
        });

        var user = modelMapper.map(dto, User.class);
        //encrypt password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        var role = roleRepository.findRoleByNameIgnoreCase("ROLE_USER").orElseThrow();
        user.setRoles(Set.of(role));

        var saved = userRepository.save(user);

        return modelMapper.map(saved, UserResponseDTO.class);
    }

    @Override
    public LoginResponseDTO signIn(LoginRequestDTO dto) {
        var user= getUserEntityOrThrow(dto.userName());
        if(!passwordEncoder.matches(dto.password(), user.getPassword())){
            throw new AuthenticationException("username or pass dont match");
            }
        Authentication authentication=new UsernamePasswordAuthenticationToken(
                user.getUserName(),
                dto.password(),
                user.getRoles().stream().map(r->new SimpleGrantedAuthority(r.getName())).toList()


                );
        var jwt=jwtService.jwtToken(authentication);
        return new LoginResponseDTO(jwt);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our entity from database:
        var user = getUserEntityOrThrow(username);

        //map the roles for Spring:
        //map our Set<Role> to Set<spring.Role>
        var roles =
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet());

        //map the user to Spring User:
        //return new Spring User:
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), roles);
    }

    private User getUserEntityOrThrow(String username) {
        var user = userRepository.findUserByUserNameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
        return user;
    }
}
