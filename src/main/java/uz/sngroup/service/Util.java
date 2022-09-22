package uz.sngroup.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sngroup.model.sys.User;
import uz.sngroup.repository.sys.UserRepository;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
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

    public File getFileFromRecFolder(String fileName, String folderName){
        File file = new File(System.getProperty("user.dir") + "\\" + folderName + "\\" + fileName);
        log.error(file.getAbsolutePath());
        return file;
    }

    public BufferedImage getBufferedImageFromFolder(String imageName, String folderName) {
        try {
            return ImageIO.read(getFileFromRecFolder(imageName, folderName));
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
