package pl.stormit.eduquiz.admin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.eduquiz.admin.controllers.CategoryAdminViewController;

@ControllerAdvice(basePackageClasses = {CategoryAdminViewController.class})
public class AdminViewExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ModelAndView handleAdminViewException(Exception ex, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Oops! Something went wrong.");
        return new ModelAndView("redirect:/admin");
    }
}