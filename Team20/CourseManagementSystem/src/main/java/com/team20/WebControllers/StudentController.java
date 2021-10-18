package com.team20.WebControllers;

import javax.validation.Valid;

import com.team20.core.Course;
import com.team20.core.Student;
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
public class StudentController {
    @Autowired
    private UserService userService = new UserService();
    @Autowired
    private CourseService courseService = new CourseService();


    @GetMapping("/student/home")
    public String student(Model model, @AuthenticationPrincipal User user) {
        Student student = userService.findStudentByUsername(user.getUsername());
        model.addAttribute("courses", student.getCurrent());
        return "studenthome";
    }
    @PostMapping("/student/home")
    public String homePost(@ModelAttribute @Valid ModifyDto modifyDto, BindingResult result, Model model, @AuthenticationPrincipal User user) {
        Student student = userService.findStudentByUsername(user.getUsername());
        Course course = courseService.findCourseById(modifyDto.getId());
        if (modifyDto.getAction().equals("drop")) {
            course.drop(student);
            return "redirect:/student/home";
        } else if (modifyDto.getAction().equals("view")) {
            model.addAttribute("course", course);
            model.addAttribute("deliverables", course.getActiveDeliverables());
            return "studentcourse";
        }
        return "redirect:/student/home";
    }
    

    @GetMapping("/student/enroll")
    public String enroll(Model model, @AuthenticationPrincipal User user) {
        Student student = userService.findStudentByUsername(user.getUsername());
        model.addAttribute("courses", courseService.getCoursesAvailable(student));
        return "courseenroll";
    }
    @PostMapping("/student/enroll")
    public String enrollPost(@ModelAttribute @Valid ModifyDto modifyDto, BindingResult result, Model model, @AuthenticationPrincipal User user) {
        Student student = userService.findStudentByUsername(user.getUsername());
        Course course = courseService.findCourseById(modifyDto.getId());
        course.enroll(student);
        //userService.updateStudent(student);
        return "redirect:/student/enroll";
    }
}