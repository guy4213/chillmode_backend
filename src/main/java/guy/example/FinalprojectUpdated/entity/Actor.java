package guy.example.FinalprojectUpdated.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "actorName", columnNames = {"actorName"})

})
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


  private String actorName;


  private String city;


  private String country;


  private String role;


//many to many actors to



}
