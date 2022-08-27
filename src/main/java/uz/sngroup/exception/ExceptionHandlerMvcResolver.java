package uz.sngroup.exception;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;



@ControllerAdvice
public class ExceptionHandlerMvcResolver {

//    @ExceptionHandler(value = {SQLException.class, IOException.class})
//    protected String handleConflicts(IOException ex, Model model) {
////        StringWriter stringWriter = new StringWriter();
////        PrintWriter printWriter = new PrintWriter(stringWriter);
////        ex.printStackTrace(printWriter);
////        String exceptionString = stringWriter.toString();
////        model.addAttribute("error", exceptionString);
//        String m2 = ex.getMessage();
//        model.addAttribute("m2", m2);
//        return "main/exception";
//    }

    @ExceptionHandler(value = {Error.class})
    protected String handleConflict1(Exception ex, Model model) {
        String m2 = ex.getMessage();
        model.addAttribute("m2", m2);
        return "main/exception";
    }

    @ExceptionHandler(value = {Exception.class})
    protected String handleConflict(Exception ex, Model model) {
        if (ex instanceof SQLException){
            String m = ex.getCause().getCause().getMessage();
            String m2 = ex.getMessage();
            model.addAttribute("m", m);
            model.addAttribute("m2", m2);
            return "main/exception";
        }else if (ex instanceof IOException){
            String m2 = ex.getMessage();
            model.addAttribute("m2", m2);
            return "main/exception";
        }else {
            String m2 = ex.getMessage();
            model.addAttribute("m2", m2);
            return "main/exception";
        }
    }

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable, final Model model) {
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}


