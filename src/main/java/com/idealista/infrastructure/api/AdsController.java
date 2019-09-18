package com.idealista.infrastructure.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdsController {

    @Autowired
    private RepositoryService repositoryService;

    //TODO añade url del endpoint
    @GetMapping("/ads/quality")
    //public ResponseEntity<List<QualityAd>> qualityListing() {
    //    return ResponseEntity.notFound().build();
    public String qualityListing(){
        //TODO rellena el cuerpo del método
        return "List of ads for the quality management\n";
    }

    //TODO añade url del endpoint
    @GetMapping("/ads/public")
    //public ResponseEntity<List<PublicAd>> publicListing() {
        //return ResponseEntity.notFound().build();
    public String publicListing() {
        //TODO rellena el cuerpo del método
        return "List of public ads.\n";
    }

    //TODO añade url del endpoint
    @GetMapping("/ads/score")
    //public ResponseEntity<Void> calculateScore() {
    // return ResponseEntity.notFound().build();
    public String calculateScore() {
        //TODO rellena el cuerpo del método
        return "Ads scores computed!\n";
    }
}
