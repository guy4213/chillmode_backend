package guy.example.FinalprojectUpdated.dto.actorDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeleteActorListDto {
    private boolean deleted;

}
