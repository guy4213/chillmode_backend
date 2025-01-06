package guy.example.FinalprojectUpdated.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
                @UniqueConstraint(name = "UQ_USER_NAME", columnNames = {"userName"}),
                @UniqueConstraint(name = "UQ_USER_EMAIL", columnNames = {"email"})
})
        public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //AUTO_INCREMENT @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 2)
    private String userName;

    private String address;

    private String phoneNumber;

    private String city ;

    private String country;

    @NotNull
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;

//    @NotNull
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@$%^&]).{8,32}$"
//            ,message = "password must contain at least 1 lowercase letter" +
//            ",1 uppercase letter,1 digit and 1 special character")
//    @Size(min = 2, max = 30)
    @NotNull
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )},
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> roles;



    }
