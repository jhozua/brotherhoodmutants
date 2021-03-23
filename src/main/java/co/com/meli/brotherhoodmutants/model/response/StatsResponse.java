package co.com.meli.brotherhoodmutants.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class StatsResponse {

    public StatsResponse(Long mutants, Long humans){
        super();
        this.count_mutant_dna = mutants;
        this.count_human_dna = humans;
        this.ratio = ((double) mutants / humans);
    }

    private Long count_mutant_dna;
    private Long count_human_dna;
    private Double ratio;
}
