package guy.example.FinalprojectUpdated.repository;

import guy.example.FinalprojectUpdated.entity.RateAvg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateAvgRepository extends JpaRepository<RateAvg,Long > {
    Optional<RateAvg> findByUserIdAndSeriesId(Long userId, Long seriesId);
    List<RateAvg> findAllBySeriesId(Long seriesId);

}
