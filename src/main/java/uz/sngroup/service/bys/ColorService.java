package uz.sngroup.service.bys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.bys.Color;
import uz.sngroup.repository.bys.ColorRepository;
import uz.sngroup.service.event.BarcodeService;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ColorService {
    @Autowired ColorRepository colorRepository;
    @Autowired BarcodeService barcodeService;

    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    public String save(Color color) {
        try{
            colorRepository.save(color);
            barcodeService.updateLists();
            return "Malumot Kiritildi!!!";
        }catch (Exception e){
            log.error(Arrays.toString(e.getStackTrace()));
            return "Xato!!!";
        }

    }

    public void delete(Long id){
        colorRepository.deleteById(id);
    }

    public Color getById(Long id){
        return colorRepository.getById(id);
    }
}
