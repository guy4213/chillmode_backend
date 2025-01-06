package guy.example.FinalprojectUpdated.dto.userDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeleteUserListDto {
    private boolean deleted;

}
