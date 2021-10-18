package com.team20.core;

import java.util.Random;

import com.team20.WebControllers.CourseService;
import com.team20.services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Initializer {
    
    private static String[] firstNames = {"Abe", "Bea", "Charles", "Donna", "Edgar", "Fiona", "Gareth", "Helen", "Inigo", "Julie", "Kyle", "Lisa", "Moe", "Noelle", "Oscar", "Penelope", "Quinn", "Rhianna", "Steve", "Tiffany", "Umar", "Victoria", "Will", "Xena", "Yourssef", "Zoey"};
    private static String[] lastNames = {"Forrest", "Heath", "Stewart", "Rogers", "Bowen", "Molina", "Rowe", "Harrison", "Humphery", "Lewis", "Harmon", "Oliver", "Castillo", "Briggs", "Wang", "Richardson", "Gonzalez", "McGregor", "Baker", "Wilson"};
    private static String[] courseNames = {"COMP1405", "COMP1406", "COMP1804", "COMP2401", "COMP2402", "COMP2404", "COMP2406", "COMP2804", "MATH1007", "PSYC1001", "ASLA1001", "ASLA1002"};
    private static char[] courseSections = {'A', 'B', 'C', 'D'};

    private CourseService cc;
    private UserService uc;
    private Random rand;

    public Initializer(CourseService c, UserService u) {
        cc = c;
        uc = u;
        rand = new Random();
    }

    public void initialize() {
        ArrayList<Professor> profs = new ArrayList<Professor>();
        ArrayList<Student> stus = new ArrayList<Student>();
        ArrayList<Course> courses = new ArrayList<Course>();

        //Profs
        for(int i = 0; i < 15; i++) {
            String fn = genFN();
            String ln = genLN();
            Professor temp = new Professor((fn+ln).toLowerCase(), "p", fn, ln, true);
            profs.add(temp);
        }

        //Students
        for(int i = 0; i < 2500; i++) {
            String fn = genFN();
            String ln = genLN();
            LocalDate bd = genBDay();
            Student temp = new Student((fn+ln).toLowerCase(), "p", fn, ln, bd.toString(), true);
            stus.add(temp);
        }

        //Courses
        for(int i = 0; i < 200; i++) {
            int index = rand.nextInt(courseNames.length);

            ArrayList<String> prereqs = new ArrayList<String>();

            int prereqNum = rand.nextInt(3);

            if(index > 0) {
                for(int j = 0; j < prereqNum; j++) {
                    prereqs.add(courseNames[rand.nextInt(index)]);
                }
            }


            Lecture temp = new Lecture(courseNames[index], courseSections[rand.nextInt(courseSections.length)], rand.nextInt(2), genTime().toString(), 100+rand.nextInt(300), null);
            temp.setPrereqs(prereqs);
            courses.add(temp);
        }
        
        // Tutorials
        int numLecture = courses.size();
        for (int i = 0; i < numLecture; i++) { // have to use this because we cant add to list while iterating over it.
            for (int j = 0; j < 2+rand.nextInt(6); j++) {
                Tutorial temp = new Tutorial(courses.get(i), genTime().toString(), 10+rand.nextInt(25), null);
                courses.add(temp);
            }
        }

        //Create course info
        for(Course c : courses) {
            Professor p = profs.get(rand.nextInt(profs.size()));

            //Give course a prof
            c.setProf(p);

            //If in past, add to prof past courses, add up to 500 students having taken, gen marks for students
            if (c.getTerm() == 0) {
                p.addPastCourses(c);

                for(int i = 0; i < 200; i++) {
                    Student s = stus.get(rand.nextInt(stus.size()));

                    if(c.enroll(s)) {
                        s.drop(c); //This is due to the fact that enrolling auto adds it to the student's current list, so it must be removed from the list
                        s.addCompletedCourses(c);
                    
                        //Gen a mark, 80% [60, 90), 10% [0, 60), 10% [90, 100)
                        int temp = rand.nextInt(20);
                        if (temp <= 15) {
                            c.updateMark(s.getId(), rand.nextInt(30) + 60);
                        } else if (temp <= 17) {
                            c.updateMark(s.getId(), rand.nextInt(60));
                        } else {
                            c.updateMark(s.getId(), rand.nextInt(10) + 90);
                        }
                    }
                }

            } else { //Else, add to prof current courses, add up to 500 students taking
                p.addCoursesTaught(c);


                for(int i = 0; i < 500; i++) {
                    Student s = stus.get(rand.nextInt(stus.size()));
                    c.enroll(s);
                }
            }
        }

        //Dump to userController and courseController;
        for(Professor p : profs) {
            uc.addProfessor(p);
        }
        for(Student s : stus) {
            uc.addStudent(s);
        }
        for(Course c : courses) {
            cc.addCourse(c);
        }

        // Personal user that will always exist for easier testing
        uc.addStudent(new Student("aidansaull", "test", "Aidan", "Saull", "1999-07-13", true));

    }

    private String genFN() {return firstNames[rand.nextInt(firstNames.length)];}
    private String genLN() {return lastNames[rand.nextInt(lastNames.length)];}
    private LocalDate genBDay() {
        LocalDate temp = LocalDate.now();

        temp = temp.minusYears((18 + rand.nextInt(4)));
        temp = temp.minusMonths(rand.nextInt(12));
        temp = temp.minusDays(rand.nextInt(31));
        return temp;
    }
    private LocalTime genTime() {
        LocalTime temp = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        temp = temp.minusHours(rand.nextInt(24));

        return temp;
    }

}
