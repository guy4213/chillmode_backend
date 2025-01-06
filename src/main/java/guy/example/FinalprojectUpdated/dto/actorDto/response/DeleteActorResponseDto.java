package guy.example.FinalprojectUpdated.dto.actorDto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteActorResponseDto {
    private boolean deleted;
    private ActorResponseDto actorResponseDto;
}
