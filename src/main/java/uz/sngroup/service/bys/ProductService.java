package uz.sngroup.service.bys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.Barcode;
import uz.sngroup.model.bys.Product;
import uz.sngroup.model.event.Counter;
import uz.sngroup.repository.bys.ProductRepository;
import uz.sngroup.repository.event.CounterRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;
    @Autowired private CounterRepository counterRepository;

    public Optional<Product> find(Barcode b) {
        return productRepository.findProductByColor_IdAndGramm_IdAndQuality_Id(b.getColor(), b.getGramm(), b.getQuality());
    }

    public Product create(Product product) {
        Counter counter = counterRepository.getById(1L);
        BigInteger big = BigInteger.valueOf(Long.parseLong(counter.getEan()));
//        counter.setEan(counter.getEan() + 1);
//        product.setEan(counter.getEan());
        counter.setEan(String.valueOf(big.add(BigInteger.ONE)));
        product.setEan(String.valueOf(big));
        counterRepository.save(counter);
        productRepository.save(product);
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public Product getById(Long id){
        return productRepository.getById(id);
    }
}
