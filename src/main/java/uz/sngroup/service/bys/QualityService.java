package uz.sngroup.service.bys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.bys.Quality;
import uz.sngroup.repository.bys.QualityRepository;
import uz.sngroup.service.event.BarcodeService;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class QualityService {
    @Autowired QualityRepository qualityRepository;
    @Autowired
    BarcodeService barcodeService;

    public List<Quality> getAll() {
        return qualityRepository.findAll();
    }

    public String save(Quality quality) {
        try{
            qualityRepository.save(quality);
            barcodeService.updateLists();
            return "Malumot Kiritildi!!!";
        }catch (Exception e){
            log.error("->", e);
            return "Xato!!!";
        }
    }

    public void delete(Long id){
        qualityRepository.deleteById(id);
    }

    public Quality getById(Long id){
        return qualityRepository.getById(id);
    }
}
