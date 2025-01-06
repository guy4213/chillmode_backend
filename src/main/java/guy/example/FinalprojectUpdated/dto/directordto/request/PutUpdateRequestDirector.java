package guy.example.FinalprojectUpdated.dto.directordto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PutUpdateRequestDirector
        (
         Long id,

         String directorName,

         String country,

         String city)
{}
