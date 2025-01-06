package guy.example.FinalprojectUpdated.dto.userDto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor

public class PostUserSignUpDTO {

    @NotNull
    @Size(min = 2)

    private String userName;



    @NotNull

    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

    private String email;



    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@$%^&]).{8,32}$"
            ,message = "password must contain at least 1 lowercase letter,1 uppercase letter,1 digit and 1 special character")
    @Size(min = 2, max = 30)
    private String password;




}