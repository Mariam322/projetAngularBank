package tn.iit.proxy;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateFactureRequest;
import tn.iit.request.UpdateFactureRequest;
import tn.iit.response.FactureResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "APIGATEWAYFACTURE",
    path = "/projetfacture",
    configuration = FeignConfig.class
)
public interface FactureController {

    // Créer une nouvelle facture
    @PostMapping("/facture/create")
    FactureResponse createFacture(@RequestBody CreateFactureRequest createFactureRequest);

    // Récupérer une facture par son ID
    @GetMapping("/facture/getById/{id}")
    FactureResponse getById(@PathVariable("id") Long id);

    // Récupérer toutes les factures
    @GetMapping("/facture/getAll")
    List<FactureResponse> getAll();

    // Mettre à jour une facture
    @PutMapping("/facture/update/{id}")
    FactureResponse updateFacture(@PathVariable("id") Long id, @RequestBody UpdateFactureRequest updateFactureRequest);

    // Supprimer une facture par son ID
    @DeleteMapping("/facture/delete/{id}")
    void deleteFacture(@PathVariable("id") Long id);
}
