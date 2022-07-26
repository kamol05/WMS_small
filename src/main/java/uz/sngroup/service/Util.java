package uz.sngroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.sys.UserRepository;

@Component
public class Util {
    @Autowired
    private UserRepository userRepository;


}
