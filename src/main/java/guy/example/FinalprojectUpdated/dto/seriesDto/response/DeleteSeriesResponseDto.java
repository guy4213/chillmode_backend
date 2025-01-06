package guy.example.FinalprojectUpdated.dto.seriesDto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteSeriesResponseDto {
    private boolean deleted;
    private SeriesResponseDto seriesResponseDto;
}
