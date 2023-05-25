package com.clientui.clientui.controller;

import com.clientui.clientui.beans.ProductBean;
import com.clientui.clientui.proxies.MicroserviceProduitsProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

public class ClientController {
    private final MicroserviceProduitsProxy produitsProxy;

    public ClientController(MicroserviceProduitsProxy produitsProxy) {
        this.produitsProxy = produitsProxy;
    }
    @RequestMapping("/")
    @GetMapping
    public String accueil(Model model) {
        String message = "Welcome to Mini Commerce Application";
        List<ProductBean> produits = produitsProxy.listeDesProduits();

        model.addAttribute("message", message);
        model.addAttribute("produits", produits);
        return "Accueil";
    }

    @GetMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id, Model model){
        ProductBean produit = produitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);
        return "FicheProduit";
    }
}
