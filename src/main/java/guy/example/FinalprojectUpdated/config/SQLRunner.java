package guy.example.FinalprojectUpdated.config;


import guy.example.FinalprojectUpdated.entity.*;
import guy.example.FinalprojectUpdated.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SQLRunner implements CommandLineRunner {
    private  final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
  private final SeriesRepository seriesRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {


        //1.initialize series & categories& Actors & Directors:
        if (actorRepository.count()==0){
            var danielMoreshet=actorRepository.save(Actor.builder()
                            .id(1L)
                            .city("Holon")
                            .country("Israel")
                    .role("Main Character")
                            .actorName("daniel moreshet")
                            .build());

            var sarkanBulat=actorRepository.save(Actor.builder()
                    .id(2L)
                    .city("Instanbul")
                    .country("Israel")
                    .role("Main Character")
                    .actorName("sarkan Bulat")
                    .build());

        if (directorRepository.count()==0){
            var linoyKarziaSheli=directorRepository.save(Director.builder()
                    .id(1L)
                    .directorName("Linoy Karzia Sheli")
                    .city("bat yam ir haarsim")
                    .country("Israel")
                    .build());

            var rubiBlum=directorRepository.save(Director.builder()
                    .id(2L)
                    .city("Instanbul")
                    .directorName("Rubi Blum")
                    .country("Israel")
                     .build());









        if (categoryRepository.count() == 0) {
            var action = categoryRepository
                    .save(Category.builder().id(1L)
                            .name("Action")
                            .description("Description")
                            .build());
            var drama = categoryRepository.save(Category.builder().id(2L).name("Drama").description("Description").build());

            seriesRepository.save(
                    Series.builder().seriesName("spiderMan").id(1L).img("https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png")
                            .categories(Set.of(action, drama))
                            .director(linoyKarziaSheli)
                            .trailer("https://www.youtube.com/watch?v=JfVOs4VSpmA")
                            .actors(Set.of(danielMoreshet,sarkanBulat))
                    .numberOfEpisodes(2).build()
            );
            seriesRepository.save(
                    Series.builder().seriesName("batman")
                            .id(2L).img("http://tinyurl.com/95h78j8e")
                             .trailer("https://www.youtube.com/watch?v=JfVOs4VSpmA")
                            .actors(Set.of(danielMoreshet)).director(rubiBlum).categories(Set.of(drama, action)).numberOfEpisodes(7).build()
            );
        }
        if (roleRepository.count() == 0) {
            var adminRole = roleRepository.save(new Role(1L, "ROLE_ADMIN"));
            var userRole = roleRepository.save(new Role(2L, "ROLE_USER"));

            userRepository.save(
                    new User(1L,
                            "Guy421367",
                            "alufei zahal 48",
                            "0534271418",
                            "holon",
                            "Israel",
                            "guy421367@gmail.com",

                            passwordEncoder.encode("Guy7@2000"),
                            Set.of(adminRole)
//                            passwordEncoder.encode("Guy7@2000")
                    )
            );

            userRepository.save(
                    new User(2L,
                            "Ofek9082",
                            "tel giborim 48",
                            "0534271418",
                            "holon",
                            "Israel",
                            "Ofek9082@gmail.com",

                            passwordEncoder.encode("Ofek1@22004"),
                            Set.of(userRole)


                    )
            );
        }
        }

    }
}
}