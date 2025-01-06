package guy.example.FinalprojectUpdated.dto.directordto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteDirectorResponseDto {
    private boolean deleted;
    private DirectorResponseDto directorResponseDto;
}
