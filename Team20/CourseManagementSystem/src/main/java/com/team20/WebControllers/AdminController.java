package com.team20.WebControllers;



import javax.validation.Valid;

import com.team20.core.Course;
import com.team20.core.Lecture;
import com.team20.core.Professor;
import com.team20.core.Student;
import com.team20.core.Tutorial;
import com.team20.dataTransferObjects.AcceptDto;
import com.team20.dataTransferObjects.CourseDto;
import com.team20.dataTransferObjects.DeliverableDto;
import com.team20.dataTransferObjects.ModifyDto;
import com.team20.dataTransferObjects.ProfessorDto;
import com.team20.dataTransferObjects.StudentDto;
import com.team20.dataTransferObjects.TutorialDto;
import com.team20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class AdminController {
    @Autowired
    private UserService userService = new UserService();
    @Autowired
    private CourseService courseService = new CourseService();

    // Course Creation ////////////////////////////////////////////////////////
    @GetMapping("/admin/course")
    public String course(Model model) {
        model.addAttribute("profs", userService.getProfs());
        model.addAttribute("course", new CourseDto());
        return "course";
    }
    @GetMapping("/admin/courselist")
    public String courseList(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        return "courselist";
    }
    @RequestMapping(value="/admin/courselist", method=RequestMethod.POST)
    public String courseListDelete(@ModelAttribute ModifyDto modifyDto) {
        if (modifyDto.getAction().equals("delete")) {
            courseService.deleteCourseById(modifyDto.getId());
        }
        return "redirect:/admin/courselist";
    }
    @PostMapping("/admin/course")
    public String createCourse(@ModelAttribute @Valid CourseDto courseDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/course";
        }
        Professor tmpProf = userService.findProfessorByUsername(courseDto.getProf());
        Lecture tmpLecture = new Lecture(courseDto.getName(), courseDto.getSection(), courseDto.getTerm(), courseDto.getTime(), courseDto.getCap(), tmpProf);
        courseService.addCourse(tmpLecture);
        model.addAttribute("course", new CourseDto());
        return "redirect:/admin/course";
    }

    // Tutorial ///////////////////////////////////////////////////////////////
    @GetMapping("/admin/tutorial")
    public String tutorial(Model model) {
        model.addAttribute("profs", userService.getProfs());
        model.addAttribute("lectures", courseService.getLectures());
        model.addAttribute("tutorial", new CourseDto());
        return "tutorial";
    }
    @PostMapping("/admin/tutorial")
    public String createTutorial(@ModelAttribute @Valid TutorialDto tutorialDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/tutorial";
        }
        Professor tmpProf = userService.findProfessorByUsername(tutorialDto.getProf());
        Course course = courseService.findCourseById(tutorialDto.getCid());
        Tutorial tmpTut = new Tutorial(course, tutorialDto.getTime(), tutorialDto.getCap(), tmpProf);
        courseService.addCourse(tmpTut);
        return "redirect:/admin/home";
    }

    // Deliverable Creation ///////////////////////////////////////////////////
    @GetMapping("/admin/deliverable")
    public String deliverable(Model model) {
        model.addAttribute("courses", courseService.getCourses());
        model.addAttribute("deliverable", new DeliverableDto());
        return "deliverable";
    }
    
    @GetMapping("/admin/deliverablelist")
    public String deliverableList(Model model) {
        model.addAttribute("deliverables", courseService.getDeliverables());
        return "deliverablelist";
    }
    @PostMapping("/admin/deliverable")
    public String createDeliverable(@ModelAttribute DeliverableDto deliverableDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/deliverable";
        }
        Course course = courseService.findCourseById(deliverableDto.getCid());
        course.createDeliverable(deliverableDto.getType(), deliverableDto.getRequirements(), deliverableDto.getOpendate(), deliverableDto.getDeadline(), deliverableDto.getMaxgrade());
        return "redirect:/admin/home";
    }

    // Professor Creation /////////////////////////////////////////////////////
    @GetMapping("/admin/prof")
    public String prof(Model model) {
        model.addAttribute("prof", new ProfessorDto());
        return "createprof";
    }
    @GetMapping("/admin/proflist")
    public String profList(Model model) {
        model.addAttribute("profs", userService.getProfs()); 
        return "proflist";  
    }
    @RequestMapping(value="/admin/proflist", method=RequestMethod.POST)
    public String modifyProf(@ModelAttribute ModifyDto modifyDto) {
        if (modifyDto.getAction().equals("delete")) {
            // The user should be deleted
            userService.deleteUser(modifyDto.getId());
        } else if (modifyDto.getAction().equals("edit")) {
            // The user should be edited
            userService.deleteUser(modifyDto.getId());
        }
        return "redirect:/admin/proflist";
    }
    @RequestMapping(value="/admin/prof", method=RequestMethod.POST)
    public String createProfessor(@ModelAttribute("prof") @Valid ProfessorDto professorDto,BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/prof";
          }
        userService.addProfessor(professorDto);
        model.addAttribute("prof", new ProfessorDto());
        return "/admin";
    }

    // Student Creation ///////////////////////////////////////////////////////
    @GetMapping("/admin/student")
    public String student(Model model) {
        model.addAttribute("student", new StudentDto());
        return "createstudent";
    }
    @GetMapping("/admin/studentlist")
    public String studentList(Model model) {
        model.addAttribute("students", userService.getStudents()); // We clone so that we can edit the page without conflict
        return "studentlist";
    }
    @RequestMapping(value="/admin/studentlist", method=RequestMethod.POST)
    public String modifyStudent(@ModelAttribute ModifyDto modifyDto) {
        if (modifyDto.getAction().equals("delete")) {
            // The user should be deleted
            userService.deleteUser(modifyDto.getId());
        } else if (modifyDto.getAction().equals("edit")) {
            // The user should be edited
            userService.deleteUser(modifyDto.getId());
        }
        return "redirect:/admin/studentlist";
    }
    @RequestMapping(value="/admin/student", method=RequestMethod.POST)
    public String createStudent(@ModelAttribute @Valid StudentDto studentDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/student";
        }
        Student registeredStudent = userService.addStudent(studentDto); 
        
        model.addAttribute("student", new StudentDto());
        return "/admin";
    }

    // LIST USERS /////////////////////////////////////////////////////////////

    @GetMapping("admin/userlist")
    public String userList(Model model) {
        model.addAttribute("user", userService.getRegisteredUsers());
        return "userlist";
    }
    @GetMapping("/admin/unregistereduserlist")
    public String unregisteredUserList(Model model) {
        model.addAttribute("user", userService.getUnregisteredUsers());
        return "unregistereduserlist";
    }
    @RequestMapping(value="/admin/unregistereduserlist", method=RequestMethod.POST)
    public String processUser(@ModelAttribute AcceptDto acceptDto) {
        if (acceptDto.isAccepted()) {
            // The user is accepted by the admin
            userService.acceptUser(acceptDto.getUid());
        } else {
            // The user is not accepted by the admin
            userService.deleteUser(acceptDto.getUid());
        }
        return "redirect:/admin/unregistereduserlist";
    }

    @GetMapping("admin/home")
    public String admin(Model model) {
        //model.addAttribute("appName", appName);
        return "admin";
    }
}