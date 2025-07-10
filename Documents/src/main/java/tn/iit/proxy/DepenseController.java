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


    @PostMapping("/create")
    DepenseResponse createDepense(@RequestBody CreateDepenseRequest createDepenseRequest);

    @GetMapping("/getById/{id}")
    DepenseResponse getById(@PathVariable("id") Long id);


    @GetMapping("/getAll")
    List<DepenseResponse> getAll();


    @PutMapping("/update/{id}")
    DepenseResponse updateDepense(@PathVariable("id") Long id, @RequestBody UpdateDepenseRequest updateDepenseRequest);


    @DeleteMapping("/delete/{id}")
    void deleteDepense(@PathVariable("id") Long id);
}
