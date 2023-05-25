package com.mpaiement.web.controller;

import com.mpaiement.dao.PaiementDao;
import com.mpaiement.model.Paiement;
import com.mpaiement.web.exceptions.PaiementExistantException;
import com.mpaiement.web.exceptions.PaiementImpossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaiementController {

    private final PaiementDao paiementDao;

    public PaiementController(PaiementDao paiementDao) {
        this.paiementDao = paiementDao;
    }

    @PostMapping(value = "/paiement")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement){

        //Vérifions s'il y a déjà un paiement enregistré pour cette commande
        Paiement paiementExistant = paiementDao.findByIdCommande(paiement.getIdCommande());
        // 409
        if(paiementExistant != null) throw new PaiementExistantException("Cette commande est déjà payée");

        //Enregistrer le paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);
        // 500
        if(nouveauPaiement == null) throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard");


        //TODO Nous allons appeler le Microservice Commandes ici pour lui signifier que le paiement est accepté

        return new ResponseEntity<>(nouveauPaiement, HttpStatus.CREATED);

    }




}
