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

    // Cr�er une nouvelle op�ration bancaire
    @PostMapping("/operations")
    OperationResponse createOperation(@RequestBody CreateOperationRequest createOperationRequest);

    // R�cup�rer une op�ration par ID
    @GetMapping("/operations/{id}")
    OperationResponse getById(@PathVariable("id") Long id);

    // R�cup�rer toutes les op�rations
    @GetMapping("/operations")
    List<OperationResponse> getAll();

    // Mettre � jour une op�ration
    @PutMapping("/operations/{id}")
    OperationResponse updateOperation(@PathVariable("id") Long id, @RequestBody UpdateOperationRequest updateOperationRequest);

    // Supprimer une op�ration
    @DeleteMapping("/operations/delete/{id}")
    void deleteOperation(@PathVariable("id") Long id);
}
