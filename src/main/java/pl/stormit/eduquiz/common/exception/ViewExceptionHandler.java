package pl.stormit.eduquiz.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.stormit.eduquiz.common.controller.CategoryManagementViewController;
import pl.stormit.eduquiz.common.controller.IndexViewController;
import pl.stormit.eduquiz.common.controller.QuizManagementViewController;


@ControllerAdvice(basePackageClasses= {CategoryManagementViewController.class, IndexViewController.class,
        QuizManagementViewController.class})
public class ViewExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ModelAndView handleViewException(Exception ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Oops! Something went wrong. Please contact your administrator");
        return new ModelAndView("redirect:/");
    }


}
