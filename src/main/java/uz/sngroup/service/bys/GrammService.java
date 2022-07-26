package uz.sngroup.service.bys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.bys.Gramm;
import uz.sngroup.repository.bys.GrammRepository;
import uz.sngroup.service.event.BarcodeService;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class GrammService {
    @Autowired GrammRepository grammRepository;
    @Autowired
    BarcodeService barcodeService;

    public List<Gramm> getAll() {
        return grammRepository.findAll();
    }

    public String save(Gramm gramm) {
        try{
            grammRepository.save(gramm);
            barcodeService.updateLists();
            return "Malumot Kiritildi!!!";
        }catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            return "Xato!!!";
        }
    }

    public void delete(Long id){
        grammRepository.deleteById(id);
    }

    public Gramm getById(Long id){
        return grammRepository.getById(id);
    }
}
