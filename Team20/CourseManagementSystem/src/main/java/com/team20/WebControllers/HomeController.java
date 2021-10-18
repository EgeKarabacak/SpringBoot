package com.team20.WebControllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.team20.core.Initializer;
import com.team20.core.Professor;
import com.team20.core.Student;
import com.team20.dataTransferObjects.ProfessorDto;
import com.team20.dataTransferObjects.StudentDto;
import com.team20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class HomeController {
    @Autowired
    private UserService userService;// = new UserService();
    @Autowired
    private CourseService courseService;// = new CourseService();
    private Initializer initializer;

    @PostConstruct
    public void init() {
        initializer = new Initializer(courseService, userService);
        initializer.initialize();
    }


    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal User user, HttpServletResponse httpResponse) {

        model.addAttribute("appName", appName);

        // Keep in mind this User is Userdetails NOT team20.core.User
        try{
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                httpResponse.sendRedirect("/admin/home");
                return "admin";
            } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
                Student student = userService.findStudentByUsername(user.getUsername());
                model.addAttribute("courses", student.getCurrent());
                httpResponse.sendRedirect("/student/home");
                return "studenthome";
            } else if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"))) {
                Professor professor = userService.findProfessorByUsername(user.getUsername());
                System.out.println(user.getUsername());
                model.addAttribute("courses", professor.getCoursesTaught());
                httpResponse.sendRedirect("/professor/home");
                return "professorhome";
            } else {
                return "home";
            }
        } catch(Exception e) {
            return "home";
        }

    }

    @GetMapping("/registerprof")
    public String prof(Model model) {
        model.addAttribute("prof", new ProfessorDto());
        return "registerprof";
    }
    @RequestMapping(value="/registerprof", method=RequestMethod.POST)
    public String registerProf(@ModelAttribute @Valid ProfessorDto professorDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/registerprof";
        }
        Professor registeredProf = userService.addProfessor(professorDto);
        
        model.addAttribute("prof", new ProfessorDto());
        return "login";
    }

    @GetMapping("/registerstudent")
    public String student(Model model) {
        model.addAttribute("student", new StudentDto());
        return "registerstudent";
    }
    @RequestMapping(value="/registerstudent", method=RequestMethod.POST)
    public String registerStudent(@ModelAttribute @Valid StudentDto studentDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/registerstudent";
        }
        Student registeredStudent = userService.addStudent(studentDto);
        
        model.addAttribute("student", new StudentDto());
        return "login";
    }
}