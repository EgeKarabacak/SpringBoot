package com.team20.services;

import java.util.ArrayList;
import java.util.List;

import com.team20.core.Administrator;
import com.team20.core.Professor;
import com.team20.core.Student;
import com.team20.core.User;
import com.team20.dataTransferObjects.ProfessorDto;
import com.team20.dataTransferObjects.StudentDto;
import com.team20.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    private ArrayList<User> users;
    private ArrayList<Professor> profs;
    private ArrayList<Student> students;

    @Autowired
    private UserRepository repository;

    public UserService() {
        profs = new ArrayList<Professor>();
        students = new ArrayList<Student>();
        users = new ArrayList<User>();
        users.add(Administrator.getInstance("admin", "admin"));
    }

    @Transactional
    public Student addStudent(Student stu) {
        String usernameCore = stu.getUsername();
        String username = usernameCore;
        // Incrementing username number if theres a duplicate
        int i = 1;
        while(findUserByUsername(username) != null)
        {
            username = usernameCore + Integer.toString(i);
            i++;
        }
        stu.setUsername(username);
        users.add(stu);
        students.add(stu);
        repository.save(stu);
        return stu;
    }

    @Transactional
    public Student addStudent(StudentDto studentDto) {
        String usernameCore = studentDto.getFirstname().toLowerCase() + studentDto.getLastname().toLowerCase();
        String username = usernameCore;
        // Incrementing username number if theres a duplicate
        int i = 1;
        while(findUserByUsername(username) != null)
        {
            username = usernameCore + Integer.toString(i);
            i++;
        }

        final Student student = new Student(username,
                                            studentDto.getPassword(),
                                            studentDto.getFirstname(),
                                            studentDto.getLastname(),
                                            studentDto.getBirthday(), false);
        users.add(student);
        students.add(student);
        // adding to the repository as well
        repository.save(student);
        return student;
    }

    @Transactional
    public Professor addProfessor(Professor pro) {
        String usernameCore = pro.getUsername();
        String username = usernameCore;
        // Incrementing username number if theres a duplicate
        int i = 1;
        while(findUserByUsername(username) != null)
        {
            username = usernameCore + Integer.toString(i);
            i++;
        }
        pro.setUsername(username);
        users.add(pro);
        profs.add(pro);
        repository.save(pro);
        return pro;
    }

    @Transactional
    public Professor addProfessor(ProfessorDto professorDto) {
        String usernameCore = professorDto.getFirstname().toLowerCase() + professorDto.getLastname().toLowerCase();
        String username = usernameCore;
        // Incrementing username number if theres a duplicate
        int i = 1;
        while(findUserByUsername(username) != null)
        {
            username = usernameCore + Integer.toString(i);
            i++;
        }

        final Professor professor = new Professor(username,
                                            professorDto.getPassword(),
                                            professorDto.getFirstname(),
                                            professorDto.getLastname(), false);
        users.add(professor);
        profs.add(professor);
        // adding to the repository as well
        repository.save(professor);
        return professor;
    }

    public Iterable<Professor> getProfs() {
        return profs;
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    public User login(String u, String p) {
        for(User e : users) {
            if (e.verifyLogin(u, p)) {
                return e;
            }
        }
        return null;
    }

	public Professor findProfessorByUsername(String un) {
		for (Professor tmpProfessor : profs) {
            System.out.println("comparing " + tmpProfessor.getUsername() + " with " + un);
            if (tmpProfessor.getUsername().equals(un)) {
                return tmpProfessor;
            }
        }
        return null;
	}

    public Student findStudentByUsername(String un) {
		for (Student tmpStudent : students) {
            if (tmpStudent.getUsername().equals(un)) {
                return tmpStudent;
            }
        }
        return null;
	}

    public User findUserByUsername(String un) {
		for (User tmpUser : users) {
            if (tmpUser.getUsername().equals(un)) {
                return tmpUser;
            }
        }
        return null;
	}

    public Iterable<User> getUsers() {
        List<User> usrs = new ArrayList<User>();
        repository.findAll().forEach(usrs::add);
        return usrs;
    }

    public Iterable<User> getRegisteredUsers() {
        List<User> usrs = new ArrayList<User>();
        repository.findByEnabled(true).forEach(usrs::add);
        return usrs;
    }

    public Iterable<User> getUnregisteredUsers() {
        List<User> usrs = new ArrayList<User>();
        repository.findByEnabled(false).forEach(usrs::add);
        return usrs;
    }

    public void acceptUser(int uid) {
        System.out.println("accepting " + uid);
        for (User u : users)
        {
            if (u.getId() == uid) {
                u.enable();
                repository.save(u);
            }
        }
    }

    @Transactional
    public void deleteUser(int uid) {
        User tmp = null;
        for (User u : users) {
            if (u.getId() == uid) {
                tmp = u;
            }
        }
        users.remove(tmp);
        students.remove(tmp);
        profs.remove(tmp);
        repository.deleteByUsername(tmp.getUsername());
    }

    public void updateStudent(Student student) {
        //repository.save(student);
    }

}