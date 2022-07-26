package uz.sngroup.repository.bys;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sngroup.model.bys.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByColor_IdAndGramm_IdAndQuality_Id(Long colorId, Long grammId, Long qualityId);
}
