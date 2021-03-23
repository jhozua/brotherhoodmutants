package co.com.meli.brotherhoodmutants.service;

import co.com.meli.brotherhoodmutants.model.request.MutantRequest;
import co.com.meli.brotherhoodmutants.model.response.StatsResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MutantServiceTest {

    public static final String[] MUTANT_DNA = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    public static final String[] HUMAN_DNA = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    private MutantService service;

    @Mock private StatsRepository statsRepository;

    @Before
    public void init() {
        service = new MutantService(statsRepository);
    }

    @Test
    public void isMutantTrue() {
        MutantRequest request = new MutantRequest();
        request.setDna(MUTANT_DNA);
        boolean mutant = service.isMutant(request.getDna());
        Assertions.assertThat(mutant).isTrue();
    }

    @Test
    public void isMutantFalse() {
        MutantRequest request = new MutantRequest();
        request.setDna(HUMAN_DNA);
        boolean mutant = service.isMutant(request.getDna());
        Assertions.assertThat(mutant).isFalse();
    }

    @Test
    public void getStats() {
        StatsResponse query = new StatsResponse(40L, 100L);
        Mockito.when(statsRepository.findStatsRatio())
                .thenReturn(query);
        StatsResponse stats = service.getStats();
        Assertions.assertThat(stats.getCount_human_dna()).isNotNull();
        Assertions.assertThat(stats.getCount_mutant_dna()).isNotNull();
        Assertions.assertThat(stats.getRatio()).isNotNull();
    }
}
