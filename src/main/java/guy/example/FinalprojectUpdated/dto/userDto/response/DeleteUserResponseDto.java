package guy.example.FinalprojectUpdated.dto.userDto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteUserResponseDto {
    private boolean deleted;
    private UserResponseDTO userResponseDTO;
}
