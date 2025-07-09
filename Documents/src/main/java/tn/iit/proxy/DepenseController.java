package tn.iit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.UpdateDepenseRequest;
import tn.iit.response.DepenseResponse;

import java.util.List;

@FeignClient(
    name = "DEPENSE",
   path = "/depense",
    configuration = FeignConfig.class
)
public interface DepenseController {

    // Cr�er une nouvelle d�pense
    @PostMapping("/create")
    DepenseResponse createDepense(@RequestBody CreateDepenseRequest createDepenseRequest);

    // R�cup�rer une d�pense par ID
    @GetMapping("/getById/{id}")
    DepenseResponse getById(@PathVariable("id") Long id);

    // R�cup�rer toutes les d�penses
    @GetMapping("/getAll")
    List<DepenseResponse> getAll();

    // Mettre � jour une d�pense
    @PutMapping("/update/{id}")
    DepenseResponse updateDepense(@PathVariable("id") Long id, @RequestBody UpdateDepenseRequest updateDepenseRequest);

    // Supprimer une d�pense
    @DeleteMapping("/delete/{id}")
    void deleteDepense(@PathVariable("id") Long id);
}
