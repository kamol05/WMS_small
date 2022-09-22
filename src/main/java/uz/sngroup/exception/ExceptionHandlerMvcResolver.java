package uz.sngroup.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerMvcResolver {


    @ExceptionHandler(value = {Error.class})
    protected String handleConflict1(Exception ex, Model model) {
        log.error("handleConflict1 ->", ex);
        String m2 = ex.getMessage();
        model.addAttribute("m2", m2);
        return "main/exception";
    }

    @ExceptionHandler(value = {Exception.class})
    protected String handleConflict(Exception ex, Model model) {
        if (ex instanceof SQLException){
            log.error("handleConflict sql ->", ex);
            String m = ex.getCause().getCause().getMessage();
            String m2 = ex.getMessage();
            model.addAttribute("m", m);
            model.addAttribute("m2", m2);
            return "main/exception";
        }else if (ex instanceof IOException){
            log.error("handleConflict io ->", ex);
            String m2 = ex.getMessage();
            model.addAttribute("m2", m2);
            return "main/exception";
        }else {
            log.error("handleConflict unknown ->", ex);
            String m2 = ex.getMessage();
            model.addAttribute("m2", m2);
            return "main/exception";
        }
    }

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable, final Model model) {
        log.error("handleConflict unknown ->", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}


