package guy.example.FinalprojectUpdated.dto.actorDto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PutUpdateRequestActor
(
long id,
@NotNull
@Size(min = 2, max = 30)
  String actorName,
@NotNull
@Size(min = 2, max = 30)
  String city,

@NotNull
@Size(min = 2, max = 30)
 String country,
 @NotNull
 @NotEmpty
 @Size(min = 4, max = 9)//2 possible answers:main/secondary
 String role){}
