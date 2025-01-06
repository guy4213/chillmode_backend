package guy.example.FinalprojectUpdated.dto.actorDto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddPostReqActorDto {
    @NotNull
    @Size(min = 2, max = 30)
    private String actorName;


    @NotNull
    @Size(min = 2, max = 30)
    private String city;
    @NotNull
    @Size(min = 2, max = 30)
    private String country;
    @NotNull
    @Size(min = 4, max = 9,message ="2 possible answers:main/secondary" )//2 possible answers:main/secondary
    private String role;



}
