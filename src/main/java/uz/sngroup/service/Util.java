package uz.sngroup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sngroup.Application;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.sys.UserRepository;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

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


    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws URISyntaxException {
        Path path = Paths.get(Application.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        String H2 = "jdbc:h2:" + path + "\\Baza";


        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocation(new FileSystemResource("/Users/home/conf.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }


}
