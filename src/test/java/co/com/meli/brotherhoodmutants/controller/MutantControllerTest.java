package co.com.meli.brotherhoodmutants.controller;

import co.com.meli.brotherhoodmutants.model.request.MutantRequest;
import co.com.meli.brotherhoodmutants.model.response.StatsResponse;
import co.com.meli.brotherhoodmutants.service.MutantService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;

@RunWith(MockitoJUnitRunner.class)
public class MutantControllerTest {

    @Mock private MutantService service;

    private MutantController controller;

    @Before
    public void init() {
        controller = new MutantController(service);
    }

    @Test
    public void isMutantTrue() {
        MutantRequest request = new MutantRequest();
        Mockito.when(service.isMutant(request.getDna()))
                .thenReturn(Boolean.TRUE);
        HttpEntity<Object> result = controller.isMutant(request);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void isMutantFalse() {
        MutantRequest request = new MutantRequest();
        Mockito.when(service.isMutant(request.getDna()))
                .thenReturn(Boolean.FALSE);
        HttpEntity<Object> result = controller.isMutant(request);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void getStats() {
        StatsResponse response = new StatsResponse(40L, 100L);
        Mockito.when(service.getStats())
                .thenReturn(response);
        HttpEntity<StatsResponse> stats = controller.getStats();
        Assertions.assertThat(stats.getBody().getCount_human_dna()).isNotNull();
        Assertions.assertThat(stats.getBody().getCount_mutant_dna()).isNotNull();
        Assertions.assertThat(stats.getBody().getRatio()).isNotNull();
    }
}
