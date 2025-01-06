package guy.example.FinalprojectUpdated.dto.seriesDto.request;

import guy.example.FinalprojectUpdated.entity.RateAvg;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//getting all primtive params in the request dto.
public class AddSeriesRequestDto {
    @NotNull
    @Size(min = 1, max = 30)
    private String seriesName;
    @NotNull
    @Lob
    private  String img;
    @Lob
    private String trailer;

    private int publishedYear;

    @NotNull
    private  int numberOfEpisodes;
    @Column(columnDefinition = "LONGTEXT")
    private String seriesDescription;



    //1. categories to series many to Many
//    @ManyToMany
//    @JoinTable(
//            name = "series_category",
//            joinColumns = {@JoinColumn(name = "Series_id", referencedColumnName = "id"),
//                    @JoinColumn(name = "seriesName", referencedColumnName = "seriesName") },
//            inverseJoinColumns={@JoinColumn(name = "Category_id",referencedColumnName = "id"),
//                    @JoinColumn(name = "name",referencedColumnName = "name")
//
//            } )
//
//
//    private Set<Category> categories= new HashSet<>();


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
//    @ManyToOne
//    @JoinColumn(name = "user_id",referencedColumnName ="id",nullable = false)
//    @JoinColumn(name = "userName",referencedColumnName ="userName",nullable = false )
//    private User user;

}
