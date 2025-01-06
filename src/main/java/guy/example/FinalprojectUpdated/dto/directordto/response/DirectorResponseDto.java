package guy.example.FinalprojectUpdated.dto.directordto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorResponseDto {
    private Long id;


    private String directorName;


    private String city;


    private String country;

}
