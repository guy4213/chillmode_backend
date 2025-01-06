package guy.example.FinalprojectUpdated.dto.actorDto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorResponseDto {

    private Long id;


    private String actorName;


    private String city;


    private String country;


    private String role;

}
