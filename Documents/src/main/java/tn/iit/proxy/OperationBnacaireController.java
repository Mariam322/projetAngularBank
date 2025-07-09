package tn.iit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateOperationRequest;
import tn.iit.request.UpdateOperationRequest;
import tn.iit.response.OperationResponse;

import java.util.List;

@FeignClient(
    name = "BANQUESERVICE",

    configuration = FeignConfig.class
)
public interface OperationBnacaireController {

    // Créer une nouvelle opération bancaire
    @PostMapping("/operations")
    OperationResponse createOperation(@RequestBody CreateOperationRequest createOperationRequest);

    // Récupérer une opération par ID
    @GetMapping("/operations/{id}")
    OperationResponse getById(@PathVariable("id") Long id);

    // Récupérer toutes les opérations
    @GetMapping("/operations")
    List<OperationResponse> getAll();

    // Mettre à jour une opération
    @PutMapping("/operations/{id}")
    OperationResponse updateOperation(@PathVariable("id") Long id, @RequestBody UpdateOperationRequest updateOperationRequest);

    // Supprimer une opération
    @DeleteMapping("/operations/delete/{id}")
    void deleteOperation(@PathVariable("id") Long id);
}
