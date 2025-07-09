package tn.iit.controller;


import java.util.List;


import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.OperationBancaire;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.OperationNotFoundException;
import tn.iit.services.OperationBancaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j

@Tag(name = "Gestion des operations bancaires", description = "API pour effectuer des operations bancaires liees aux comptes")
public class OperationBancaireController {

    private final OperationBancaireService operationService;

 
 


	@GetMapping("/operations")
 @Operation(summary = "Lister toutes les operations", description = "Permet d�obtenir la liste de toutes les op�rations bancaires")
    @ApiResponse(responseCode = "200", description = "Liste des operations r�cup�r�e avec succ�s")
    public List<OperationBancaire> getAllOperations() {
        return operationService.findAll();
    }


    @GetMapping("/operations/{id}")
    
    @Operation(summary = "Obtenir une op�ration par ID", description = "Recupere une operation bancaire par son identifiant")
    @ApiResponse(responseCode = "200", description = "Operation trouvee")
    @ApiResponse(responseCode = "404", description = "Op�ration non trouvee")
    public OperationBancaire getById(@PathVariable("id") Long id) throws OperationNotFoundException {
        return operationService.getById(id);
    }

   
    @PostMapping("/operations")
     @Operation(summary = "Creer ou mettre � jour une operation", description = "Ajoute ou met e jour une op�ration bancaire")
    @ApiResponse(responseCode = "200", description = "Op�ration cr��e ou mise � jour avec succ�s")
    @ApiResponse(responseCode = "400", description = "Donn�es invalides fournies")
    public OperationBancaire createOrUpdateOperation(@RequestBody OperationBancaire operation) {
        return operationService.saveOrUpdate(operation);
    }


    @DeleteMapping("/operations/delete/{id}")
    @Operation(summary = "Supprimer une operation", description = "Supprime une operation bancaire par son identifiant")
    @ApiResponse(responseCode = "200", description = "Op�ration supprim�e avec succ�s")
    @ApiResponse(responseCode = "404", description = "Op�ration non trouv�e")
    public void deleteOperation(@PathVariable("id") Long id) throws OperationNotFoundException {
        operationService.deleteOperationByIdCompte(id);
    }


    @PostMapping("/comptes/{id}/operations")
     @Operation(summary = "Ajouter une operation � un compte", description = "Ajoute une nouvelle op�ration � un compte bancaire donne")
    @ApiResponse(responseCode = "200", description = "Operation enregistree avec succes pour le compte")
    @ApiResponse(responseCode = "404", description = "Compte non trouve")
    public OperationBancaire saveOperationForCompte(@PathVariable("id") Long id,
                                                    @RequestBody OperationBancaire operation) throws CompteNotFoundException {
        return operationService.saveOperationBnacaireByIdCompte(id, operation);
    }
}
