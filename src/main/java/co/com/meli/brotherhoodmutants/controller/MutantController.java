package co.com.meli.brotherhoodmutants.controller;

import co.com.meli.brotherhoodmutants.model.request.MutantRequest;
import co.com.meli.brotherhoodmutants.model.response.StatsResponse;
import co.com.meli.brotherhoodmutants.service.MutantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MutantController {

    private MutantService mutantService;

    @PostMapping("/mutant")
    public HttpEntity<Object> isMutant(@RequestBody MutantRequest mutantRequest) {
        if(mutantService.isMutant(mutantRequest.getDna())){
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Object>(new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/stats")
    public HttpEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(mutantService.getStats());
    }
}
