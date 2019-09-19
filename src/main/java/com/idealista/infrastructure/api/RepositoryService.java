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
    public PictureVO getPicByUrl(String url){return memoryPersistence.findPicByUrl(url);}

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
    public void updateAd(QualityAd updatedAd){
        QualityAd oldAd = getAdById(updatedAd.getId());
        int index = qualityAds.indexOf(oldAd);
        qualityAds.set(index, updatedAd);
        // convert quality ad to advo
        AdVO adVo = QualityAdToAdVO(updatedAd);
        // update ad in DB
        memoryPersistence.updateAd(adVo);
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

        if(ad.getScore() != null){
            outAd.setScore(ad.getScore());
        }

        if(ad.getIrrelevantSince() != null){
            outAd.setIrrelevantSince(ad.getIrrelevantSince());
        }

        return outAd;
    }

    private AdVO QualityAdToAdVO(QualityAd ad){

        AdVO outAd = new AdVO();
        // set properties
        outAd.setId(ad.getId());
        outAd.setTypology(ad.getTypology());
        outAd.setDescription(ad.getDescription());
        outAd.setTypology(ad.getTypology());
        // set pic ids
        List<String> urls = ad.getPictureUrls();
        List<Integer> picsIds = new ArrayList<>();

        for(String url : urls){
            PictureVO pic = getPicByUrl(url);
            picsIds.add(pic.getId());
        }

        // set pics
        outAd.setPictures(picsIds);
        outAd.setHouseSize(ad.getHouseSize());
        outAd.setGardenSize(ad.getGardenSize());

        // if there is score and irrelevant since
        if(ad.getScore() != null){
            outAd.setScore(ad.getScore());
        }

        if(ad.getIrrelevantSince() != null){
            outAd.setIrrelevantSince(ad.getIrrelevantSince());
        }

        return outAd;
    }

    public PublicAd QualityAdToPublicAd(QualityAd ad){
        PublicAd outAd = new PublicAd();
        // set properties
        outAd.setId(ad.getId());
        outAd.setTypology(ad.getTypology());
        outAd.setDescription(ad.getDescription());
        outAd.setTypology(ad.getTypology());
        outAd.setPictureUrls(ad.getPictureUrls());
        outAd.setHouseSize(ad.getHouseSize());
        outAd.setGardenSize(ad.getGardenSize());

        return outAd;
    }

}