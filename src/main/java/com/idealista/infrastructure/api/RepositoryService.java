package com.idealista.infrastructure.api;

import java.util.List;
import com.idealista.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    // Repository
    private InMemoryPersistence memoryPersistence;

    // Picture Methods
    public List<PictureVO> getAllPictures(){return memoryPersistence.findAllPictures();}
    public PictureVO getPicById(int id){return memoryPersistence.findPicById(id);}

    // Ad Methods
    public List<AdVO> getAllAds(){return memoryPersistence.findAllAds();}
    public AdVO getAdById(int id){return memoryPersistence.findAdById(id);}

}