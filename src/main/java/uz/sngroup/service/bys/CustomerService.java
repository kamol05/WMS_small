package uz.sngroup.service.bys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.sngroup.model.bys.Customer;
import uz.sngroup.repository.bys.CustomerRepository;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public String save(Customer customer) {
        try{
            customerRepository.save(customer);
            return "Malumot Kiritildi!!!";
        }catch (Exception e){
            log.error("->", e);
            return "Xato!!!";
        }
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

    public Customer getById(Long id){
        return customerRepository.getById(id);
    }
}
