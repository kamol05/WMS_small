package uz.sngroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.sys.UserRepository;

@Component
public class Util {
    @Autowired UserRepository userRepository;

    public User getLoginFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null){
            return getUser("admin");
        } else {
            return getUser(authentication.getName());
        }
    }

    private  User getUser(String login){
        return userRepository.findByLogin(login).get();
    }


}
