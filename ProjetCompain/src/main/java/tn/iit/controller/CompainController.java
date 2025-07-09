package tn.iit.controller; import org.springframework.web.bind.annotation.ResponseStatus; import java.util.List; import
org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.DeleteMapping; import
org.springframework.web.bind.annotation.GetMapping; import org.springframework.web.bind.annotation.PathVariable; import
org.springframework.web.bind.annotation.PostMapping; import org.springframework.web.bind.annotation.PutMapping; import
org.springframework.web.bind.annotation.RequestBody; import org.springframework.web.bind.annotation.RestController; import
org.springframework.web.bind.annotation.CrossOrigin;  import tn.iit.entites.Compain; import
tn.iit.exception.CompainNotFoundException; import tn.iit.services.CompainService;
import tn.iit.entites.TypeClientFournisseur;
import org.springframework.security.access.prepost.PreAuthorize;
import tn.iit.request.UpdateCompainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


import tn.iit.request.CreateCompainRequest;
import tn.iit.response.CompainResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Tag(name = "Compain Management", description = "API pour la gestion des compagnies")
public class CompainController {

    private static final Logger logger = LoggerFactory.getLogger(CompainController.class);

    @Autowired
    private CompainService compainService;

@PostMapping("/compain/add")
@Operation(summary = "Creer une nouvelle compagnie", 
               description = "Permet de cr�er une nouvelle compagnie avec les informations fournies")
    @ApiResponse(responseCode = "200", description = "Compagnie cr��e avec succ�s",
                content = @Content(schema = @Schema(implementation = CompainResponse.class)))
    @ApiResponse(responseCode = "400", description = "Donn�es invalides fournies")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    @ApiResponse(responseCode = "403", description = "Acc�s refus�")
    public CompainResponse createCompain(@RequestBody CreateCompainRequest createCompainRequest) {
        return compainService.createCompain(createCompainRequest);
    }

    @GetMapping("/compain/{id}")
      @Operation(summary = "Recuperer une compagnie par ID", 
               description = "Obtenir les d�tails d'une compagnie sp�cifique par son identifiant")
    @ApiResponse(responseCode = "200", description = "Compagnie trouv�e",
                content = @Content(schema = @Schema(implementation = CompainResponse.class)))
    @ApiResponse(responseCode = "404", description = "Compagnie non trouv�e")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    public CompainResponse getById(@PathVariable long id) {
        return compainService.getById(id);
    }

           @ResponseStatus(HttpStatus.OK)
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        @GetMapping("/compain")
            @Operation(summary = "Lister toutes les compagnies", 
               description = "Obtenir la liste compl�te de toutes les compagnies disponibles")
    @ApiResponse(responseCode = "200", description = "Liste des compagnies r�cup�r�e avec succ�s",
                content = @Content(schema = @Schema(implementation = CompainResponse.class)))
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    @ApiResponse(responseCode = "403", description = "Acc�s refus�")
    public List<CompainResponse> getAll() {
        return compainService.getAllCompains();
    }

    @PutMapping("/compain/update/{id}")
                @Operation(summary = "Modifier une compagnie", 
               description = "Mettre � jour les informations d'une compagnie existante")
    @ApiResponse(responseCode = "200", description = "Compagnie mise � jour avec succ�s",
                content = @Content(schema = @Schema(implementation = CompainResponse.class)))
    @ApiResponse(responseCode = "400", description = "Donn�es invalides fournies")
    @ApiResponse(responseCode = "404", description = "Compagnie non trouv�e")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    @ApiResponse(responseCode = "403", description = "Acc�s refus�")
    public CompainResponse updateCompain(@PathVariable("id") long id, @RequestBody UpdateCompainRequest updateCompainRequest) {
        logger.info("Received request to update compain with ID: {}", id);
        return compainService.updateCompain(id, updateCompainRequest);
    }
              
    @DeleteMapping("/compain/delete/{id}")
    @Operation(summary = "Supprimer une compagnie", 
               description = "Supprimer une compagnie existante par son identifiant")
    @ApiResponse(responseCode = "200", description = "Compagnie supprim�e avec succ�s")
    @ApiResponse(responseCode = "404", description = "Compagnie non trouv�e")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    @ApiResponse(responseCode = "403", description = "Acc�s refus�")
    public void deleteCompain(@PathVariable long id) {
        compainService.deleteCompain(id);
    }
     @GetMapping("/compain/type/{type}")
                 @Operation(summary = "Recuperer les compagnies par type", 
               description = "Obtenir la liste des compagnies filtr�es par type (client/fournisseur)")
    @ApiResponse(responseCode = "200", description = "Liste des compagnies filtr�es par type",
                content = @Content(schema = @Schema(implementation = CompainResponse.class)))
    @ApiResponse(responseCode = "400", description = "Type invalide")
    @ApiResponse(responseCode = "401", description = "Non autoris�")
    public List<CompainResponse> getCompainsByType(@PathVariable TypeClientFournisseur type) {
        return compainService.getCompainsByType(type);
    }
}
