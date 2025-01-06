package guy.example.FinalprojectUpdated.dto.userDto.request;

import jakarta.validation.constraints.*;

public record PutUpdateRequestUser(

        Long id,
        @NotNull
        @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,


        @NotNull
        @NotEmpty
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@$%^&]).{8,32}$"
        ,message = "password must contain at least 1 lowercase letter,1 uppercase letter,1 digit and 1 special character")

        @Size(min = 2, max = 30)

         String password
        )
{
}
