package guy.example.FinalprojectUpdated.dto.userDto.response;


import guy.example.FinalprojectUpdated.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String userName;
    private String email;

    private String password;
    private Set<Role> roles;


}
