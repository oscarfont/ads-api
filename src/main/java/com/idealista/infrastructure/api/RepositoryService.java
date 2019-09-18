package com.idealista.infrastructure.api;

import java.util.ArrayList;
import java.util.List;
import com.idealista.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    // Repository
    private InMemoryPersistence memoryPersistence;

    // QualityAds
    List<QualityAd> qualityAds;

    // Constructor
    public RepositoryService(){
        memoryPersistence = new InMemoryPersistence();
    }

    // Picture Methods
    public List<PictureVO> getAllPictures(){return memoryPersistence.findAllPictures();}
    public PictureVO getPicById(int id){return memoryPersistence.findPicById(id);}

    // Ad Methods
    public List<QualityAd> getAllAds(){
        qualityAds = new ArrayList<>();

        for(AdVO ad : memoryPersistence.findAllAds()){
            QualityAd newAd = AdVoToQualityAd(ad);
            qualityAds.add(newAd);
        }

        return qualityAds;
    }
    public QualityAd getAdById(int id){
        for(QualityAd ad : qualityAds){
            if(id == ad.getId()){
                return ad;
            }
        }
        return null;
    }

    // Other methods
    private QualityAd AdVoToQualityAd(AdVO ad){

        QualityAd outAd = new QualityAd();
        // set properties
        outAd.setId(ad.getId());
        outAd.setTypology(ad.getTypology());
        outAd.setDescription(ad.getDescription());
        outAd.setTypology(ad.getTypology());
        // set pic urls
        List<Integer> picIds = ad.getPictures();
        List<String> picsUrls = new ArrayList<>();
        // get urls
        for (Integer id : picIds){
            PictureVO pic = getPicById(id);
            String url = pic.getUrl();
            picsUrls.add(url);
        }
        // set pics
        outAd.setPictureUrls(picsUrls);
        outAd.setHouseSize(ad.getHouseSize());
        outAd.setGardenSize(ad.getGardenSize());

        return outAd;
    }

}