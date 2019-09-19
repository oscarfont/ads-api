package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdsController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/ads/quality")
    //public ResponseEntity<List<QualityAd>> qualityListing() {
    //    return ResponseEntity.notFound().build();
    public List<QualityAd> qualityListing(){
        return repositoryService.getAllAds();
    }

    @GetMapping("/ads/public")
    //public ResponseEntity<List<PublicAd>> publicListing() {
        //return ResponseEntity.notFound().build();
    public List<PublicAd> publicListing() {
        List<PublicAd> ads = new ArrayList<>();
        for (QualityAd ad : repositoryService.getAllAds()){
            ads.add(repositoryService.QualityAdToPublicAd(ad));            
        }
        return ads;
    }

    @GetMapping("/ads/score")
    //public ResponseEntity<Void> calculateScore() {
    // return ResponseEntity.notFound().build();
    public String calculateScore() {
        //TODO rellena el cuerpo del método
        return "Ads scores computed!\n";
    }
}
