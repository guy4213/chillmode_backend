package guy.example.FinalprojectUpdated.dto.categoryDto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteCategoryResponseDto {
    private boolean deleted;
    private CategoryResponseDto categoryResponseDto;
}
