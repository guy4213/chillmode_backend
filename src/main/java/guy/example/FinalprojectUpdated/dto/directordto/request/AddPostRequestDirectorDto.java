package guy.example.FinalprojectUpdated.dto.directordto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddPostRequestDirectorDto {
    @NotNull
    @Size(min = 2, max = 30)
    private String directorName;
    @NotNull
    @Size(min = 2, max = 30)
    private String city;
    @NotNull
    @Size(min = 2, max = 30)
    private String country;




}
