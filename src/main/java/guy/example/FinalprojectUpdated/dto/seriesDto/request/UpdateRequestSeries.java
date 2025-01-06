package guy.example.FinalprojectUpdated.dto.seriesDto.request;

import guy.example.FinalprojectUpdated.entity.RateAvg;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateRequestSeries
        (

                @Lob
           String img,
                String seriesName,
                int numberOfEpisodes,

                int publishedYear,
                @Lob
             String trailer,

            @Column(columnDefinition = "LONGTEXT")
      String seriesDescription    ){}
