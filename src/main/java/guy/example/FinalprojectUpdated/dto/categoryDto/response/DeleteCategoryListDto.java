package guy.example.FinalprojectUpdated.dto.categoryDto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeleteCategoryListDto {
    private boolean deleted;

}
