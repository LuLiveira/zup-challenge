package br.com.lucas.feign;

import br.com.lucas.feign.dto.CepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-via-cep", url = "${via-cep-api.url}")
public interface ViaCepFeign {

    @GetMapping("/{cep}/json/")
    ResponseEntity<CepDTO> getCep(@PathVariable String cep);
}
