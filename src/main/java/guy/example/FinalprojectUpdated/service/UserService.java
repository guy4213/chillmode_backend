package guy.example.FinalprojectUpdated.service;


import guy.example.FinalprojectUpdated.dto.userDto.request.PostUserSignUpDTO;
import guy.example.FinalprojectUpdated.dto.userDto.request.PutUpdateRequestUser;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserListDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.DeleteUserResponseDto;
import guy.example.FinalprojectUpdated.dto.userDto.response.UserResponseDTO;
import guy.example.FinalprojectUpdated.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


interface UserService {


    UserResponseDTO signUp(PostUserSignUpDTO dto);


    Collection<UserResponseDTO> getAll();


    List<UserResponseDTO> getTop(int top);


    List<UserResponseDTO> getLast(int last);


    List<UserResponseDTO> get(int top, ArrayList<User> sorted);


    DeleteUserResponseDto deleteUserById(long id);


    UserResponseDTO update(long id, PutUpdateRequestUser req);


    UserResponseDTO getUserById(long id);


    DeleteUserListDto deleteAll();


}

