package guy.example.FinalprojectUpdated.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "seriesName", columnNames = {"seriesName"}),
        @UniqueConstraint(name = "img", columnNames = {"img"})
})
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //AUTO_INCREMENT @GeneratedValue
    private Long id;

    private String seriesName;

    @Lob
    private String img;
    @Lob
    private String trailer;
    private int publishedYear;

    private int numberOfEpisodes;
    @Column(columnDefinition="LONGTEXT")
     private String seriesDescription;


    @OneToOne(cascade = CascadeType.ALL)
    private RateAvg rateAvg=new RateAvg();
    private double averageRate=0;

//    //todo:link it to User entity
//        private int idUser;
//    //todo:link it to Director entity
//        private int idDirector;
//    //todo:link it to Actor entity
//        private int idActor;

    //add a prop

    //1. categories to series many to Many
    @ManyToMany(fetch = FetchType.EAGER)

    @JoinTable(
            name = "series_category",
            joinColumns = {@JoinColumn(name = "Series_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "Category_id", referencedColumnName = "id")}
    )
    private Set<Category> categories ;

    //2. actors to series  many to Many
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "series_actor",
            joinColumns = {@JoinColumn(name = "Series_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id", referencedColumnName = "id")}
    )
    private Set<Actor> actors ;


    //3.director to series many to one
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

//{todo:add trailer in doubleClick-similar to img-check embed display.}
    //4. user to series many to one    @ManyToOne
    ////    @JoinColumn(name = "user_id",referencedColumnName ="id",nullable = false)
    ////    @JoinColumn(name = "userName",referencedColumnName ="userName",nullable = false )
    ////    private User user;
//
}





