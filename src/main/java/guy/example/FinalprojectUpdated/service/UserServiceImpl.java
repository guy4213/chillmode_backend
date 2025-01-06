package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PutUpdateRequestUser;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserListDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserResponseDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import guy.example.FinalprojectUpdated.entity.User;
import guy.example.FinalprojectUpdated.error.ResourceNotFoundException;
import guy.example.FinalprojectUpdated.repository.RoleRepository;
import guy.example.FinalprojectUpdated.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserResponseDTO signUp(PostUserSignUpDTO dto) {
        //request dto=>user
        var userEntity = modelMapper.map(dto, User.class);
        //User->saved user
        var saved = userRepository.save(userEntity);
        //saved user->Response Dto
        return modelMapper.map(saved, UserResponseDTO.class);
    }

    public Collection<UserResponseDTO> getAll() {
        var all = userRepository.findAll();
        return all.stream().map(m -> modelMapper.map(m, UserResponseDTO.class)).toList();
    }

    public List<UserResponseDTO> getTop(int top) {
        ArrayList<User> sorted = new ArrayList<>(userRepository.findAll());
        return get(top, sorted);
    }

    public List<UserResponseDTO> getLast(int last) {
        ArrayList<User> sorted = new ArrayList<>(userRepository.findAll());
        Collections.reverse(sorted);
        return get(last, sorted);
    }


    public List<UserResponseDTO> get(int top, ArrayList<User> sorted) {
        if (top > sorted.size())
            top = sorted.size();
        var sublist = sorted.subList(0, top).stream().map(user -> modelMapper.map(user, UserResponseDTO.class));
        return sublist.toList();
    }

    public DeleteUserResponseDto deleteUserById(long id) {
        //check for existence before deleting:
        var user = userRepository.findById(id);

        //delete:
        userRepository.deleteById(id);

        //return true if existed before deletion:
        return DeleteUserResponseDto.builder()
                .deleted(user.isPresent()).userResponseDTO(modelMapper.map(user, UserResponseDTO.class)).build();
    }

    public UserResponseDTO update(long id, PutUpdateRequestUser req) {

        User user=getUserByIdOrThrow(id);

        //update:
        user.setEmail(req.email());
        user.setPassword(req.password());
        var saved = userRepository.save(user);
            return modelMapper.map(saved, UserResponseDTO.class);
        }



    public UserResponseDTO getUserById(long id) {
        User user=getUserByIdOrThrow(id);
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponseDTO.class);
        }

    public UserResponseDTO getUserByUserName(String userName){
        User user=getUserByNameOrThrow(userName);
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponseDTO.class);

    }

public DeleteUserListDto deleteAll() {
        var all = userRepository.findAll();
        var x = all.stream().map(m -> modelMapper.map(m, User.class)).toList();

        x.forEach(user -> userRepository.deleteById(user.getId()));
        return DeleteUserListDto.builder()
                .deleted(!x.isEmpty()).build();
    }


 public User getUserByNameOrThrow(String userName) {
    return userRepository.findUserByUserNameIgnoreCase(userName).orElseThrow(
            () -> new ResourceNotFoundException("User", "userName", " " + userName, "can't be found")

    );
}


User getUserByIdOrThrow(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", "" + id, "can't be updated because its id is out of bounds")

        );
    }
}

