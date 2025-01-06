package guy.example.FinalprojectUpdated.dto.seriesDto.response;


import guy.example.FinalprojectUpdated.entity.Actor;
import guy.example.FinalprojectUpdated.entity.Category;
import guy.example.FinalprojectUpdated.entity.Director;
import guy.example.FinalprojectUpdated.entity.RateAvg;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.SortNatural;
import org.modelmapper.internal.bytebuddy.build.HashCodeAndEqualsPlugin;

import java.util.Set;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesResponseDto {

    private Long id;
    @Lob
    private String message;
    private  String img;
    private String seriesName;
    private String trailer;
    private int publishedYear;

    private int numberOfEpisodes;

    @Column(columnDefinition = "LONGTEXT")
    private String seriesDescription;



    private  double averageRate;


    private Set<Category> categories;
    private Set<Actor> actors ;

    private Director director;
//
//    //add a prop
//
//    //1. categories to series many to one
//    @ManyToOne
//    @JoinColumn(name = "Category_id", referencedColumnName = "id",nullable = false)
//    private Category category;
//
//
//    //2. actors to series  many to one
//    @ManyToOne
//    @JoinColumn(name = "actor_id",nullable = false)
//    private Actor actor;
//
//    //3.director to series many to one
//    @ManyToOne
//    @JoinColumn(name = "director_id",nullable = false)
//    private Director director;
//    //4. user to series many to one
//

}


//find text in all files intellij: ctrl+shift+f