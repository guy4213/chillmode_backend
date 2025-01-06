package guy.example.FinalprojectUpdated.dto.categoryDto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PutUpdateRequestCategory(

        Long id,
        @NotNull
        @Size(min = 2, max = 30)
         String name,
        @NotNull
        @Size(min = 2, max = 255)
        String description)
{
}
