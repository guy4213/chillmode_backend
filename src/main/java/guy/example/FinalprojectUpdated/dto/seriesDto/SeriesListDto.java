package guy.example.FinalprojectUpdated.dto.seriesDto;


import guy.example.FinalprojectUpdated.dto.seriesDto.response.SeriesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SeriesListDto {
//pagination:
    private long totalSerieses;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;

    private final Collection<SeriesResponseDto> series;
}
