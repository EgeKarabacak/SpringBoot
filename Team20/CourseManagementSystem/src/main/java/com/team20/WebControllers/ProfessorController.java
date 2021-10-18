package com.team20.WebControllers;

import javax.validation.Valid;

import com.team20.core.Course;
import com.team20.core.Professor;
import com.team20.dataTransferObjects.DeliverableDto;
import com.team20.dataTransferObjects.ModifyDto;
import com.team20.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ProfessorController {
    @Autowired
    private UserService userService = new UserService();
    @Autowired
    private CourseService courseService = new CourseService();


    @GetMapping("/professor/home")
    public String student(Model model, @AuthenticationPrincipal User user) {
        Professor prof = userService.findProfessorByUsername(user.getUsername());
        model.addAttribute("courses", prof.getCoursesTaught());
        return "professorhome";
    }
    @PostMapping("/professor/home")
    public String homePost(@ModelAttribute @Valid ModifyDto modifyDto, BindingResult result, Model model, @AuthenticationPrincipal User user) {
        Professor prof = userService.findProfessorByUsername(user.getUsername());
        model.addAttribute("professor", prof);
        return "redirect:/professor/home";
    }

    @PostMapping("/professor/deliverable")
    public String deliverablePost(@ModelAttribute @Valid ModifyDto modifyDto, BindingResult result, Model model) {
        if (modifyDto.getAction().equals("create")) {
            // We are giving the create deliverable page the right course
            Course course = courseService.findCourseById(modifyDto.getId());
            model.addAttribute("deliverable", new DeliverableDto());
            model.addAttribute("course", course);
            if (course.getType().equals("Lecture")) {
                return "profdeliverable";
            } else {
                return "profdeliverabletut";
            }
        }
        return "redirect:/professor/home";
    }
    @PostMapping("/professor/createdeliverable")
    public String deliverablePost(@ModelAttribute DeliverableDto deliverableDto, BindingResult result, Model model) {
        Course course = courseService.findCourseById(deliverableDto.getCid());
        course.createDeliverable(deliverableDto.getType(), deliverableDto.getRequirements(), deliverableDto.getOpendate(), deliverableDto.getDeadline(), deliverableDto.getMaxgrade());
        return "redirect:/professor/home";
    }

    @GetMapping("/professor/deliverablelist")
    public String deliverableList(Model model) {
        model.addAttribute("deliverables", courseService.getDeliverables());
        return "professordeliverablelist";
    }
}