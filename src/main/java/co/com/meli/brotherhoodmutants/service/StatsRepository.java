package co.com.meli.brotherhoodmutants.service;

import co.com.meli.brotherhoodmutants.model.entity.Stats;
import co.com.meli.brotherhoodmutants.model.response.StatsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {

    @Query(value = "SELECT NEW co.com.meli.brotherhoodmutants.model.response.StatsResponse(COUNT(s.mutant), COUNT(s.human)) from Stats s")
    StatsResponse findStatsRatio();
}
