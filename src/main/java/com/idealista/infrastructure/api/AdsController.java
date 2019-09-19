package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.idealista.infrastructure.persistence.PictureVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdsController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/ads/quality")
    public ResponseEntity<List<QualityAd>> qualityListing(){
        return new ResponseEntity<>(repositoryService.getAllAds(), HttpStatus.OK);
    }

    @GetMapping("/ads/public")
    public ResponseEntity<List<PublicAd>> publicListing() {
        List<PublicAd> ads = new ArrayList<>();
        // sort quality ads descending
        List<QualityAd> qualityAds = repositoryService.getAllAds();
        Collections.sort(qualityAds);
        Collections.reverse(qualityAds);
        // make them public
        for (QualityAd ad : qualityAds){
            if(isRelevant(ad))
                ads.add(repositoryService.QualityAdToPublicAd(ad));            
        }
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    // method to tell if an ad is relevant or not
    private boolean isRelevant(QualityAd ad){
        if(ad.getScore() != null){
            int score = ad.getScore();
            if(score >= 40)
                return true;
            else
                return false;
        }
        return false;
    }

    @GetMapping("/ads/score")
    public ResponseEntity<Void> calculateScore() {
        int score = 0;
        for (QualityAd ad : repositoryService.getAllAds()){
            // compute score
            score += picsScore(ad);
            score += descriptionScore(ad);
            score += completionScore(ad);

            // if the score is negative, fix it;
            if(score < 0){
                score = 0;
            }

            // set score to ad
            ad.setScore(score);

            // check if it is relevant or not
            if(score < 40){
                // ad is irrelevant
                ad.setIrrelevantSince(new Date());
            }

            // update object in DB
            repositoryService.updateAd(ad);

            // restart score for the next ad
            score = 0;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // method to compute the pics scores of an ad
    private Integer picsScore(QualityAd ad){
        List<String> urls = ad.getPictureUrls();
        // The ad has no pictures
        if(urls == null || urls.isEmpty()){
            return -10;
        }
        // The ad has pictures
        Integer score = 0;
        for(String url : urls){
            PictureVO pic = repositoryService.getPicByUrl(url);
            String quality = pic.getQuality();
            if(quality.equals("HD")){
                score += 20;
            }else{
                score += 10;
            }
        }

        return score;
    }

    // method to count words of a string
    private int countWords(String str) {
        return str.split("\\P{L}+").length;
    }

    // method to tell if a list of string contains a string case insensitive
    private boolean containsCaseInsensitive(String s, List<String> l){
        for (String string : l){
            if (string.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    // method to compute the scores that the description gives to an ad
    private Integer descriptionScore(QualityAd ad){
        // The ad does not have a description
        if(ad.getDescription() == null || ad.getDescription().isEmpty()){
            return 0;
        }

        // The ad has a description
        String description = ad.getDescription();
        Integer score = 5;

        String type = ad.getTypology();
        if(type.equals("FLAT")){
            Integer numwords = countWords(description);
            if(20 <= numwords && numwords < 50){
                score += 10;
            }else if(numwords >= 50){
                score += 30;
            }
        }
        if(type.equals("CHALET")){
            Integer numwords = countWords(description);
            if(numwords >= 50){
                score += 20;
            }
        }

        // if description contains certain words
        List<String> descriptionWords = Arrays.asList(description.split("\\P{L}+"));
        if(containsCaseInsensitive("Luminoso", descriptionWords)){
            score += 5;    
        }
        if(containsCaseInsensitive("Nuevo", descriptionWords)){
            score += 5;
        }
        if(containsCaseInsensitive("Céntrico", descriptionWords)){
            score += 5;
        }
        if(containsCaseInsensitive("Reformado", descriptionWords)){
            score += 5;
        }
        if(containsCaseInsensitive("Ático", descriptionWords)){
            score += 5;
        }
        
        return score;
    }

    // method to return the corresponding score to a complete ad
    private Integer completionScore(QualityAd ad){

        List<String> picsUrls = ad.getPictureUrls();
        int score = 0;

        // ad has no pictures
        if(picsUrls == null || picsUrls.isEmpty())
            return score;
        
        // ad has pictures    
        String typology = ad.getTypology();

        if(typology.equals("CHALET")){
            if(ad.getDescription() == null || ad.getDescription().isEmpty())
                // has no description
                return score;

            if(ad.getHouseSize() == null || ad.getHouseSize() == 0)
                // has no house size
                return score;
                
            if(ad.getGardenSize() == null || ad.getGardenSize() == 0)
                // has no garden size
                return score;
            
            // is complete
            score = 40;
        }else if(typology.equals("FLAT")){
            if(ad.getDescription() == null || ad.getDescription().isEmpty())
                // has no description
                return score;

            if(ad.getHouseSize() == null || ad.getHouseSize() == 0)
                // has no house size
                return score;

            // is complete
            score = 40;
        }else if(typology.equals("GARAGE")){
            if(ad.getHouseSize() == null || ad.getHouseSize() == 0)
                // has no house size
                return score;
            // is complete
            score = 40;
        }

        return score;
    }
}
