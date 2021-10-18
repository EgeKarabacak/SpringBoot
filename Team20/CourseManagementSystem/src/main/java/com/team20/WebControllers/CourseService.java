package com.team20.WebControllers;

import java.util.ArrayList;

import com.team20.core.Course;
import com.team20.core.Deliverable;
import com.team20.core.Student;

import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private ArrayList<Course> courses;
    private ArrayList<Deliverable> deliverables;
    

    public CourseService() {
        courses = new ArrayList<Course>();
        deliverables = new ArrayList<Deliverable>();
    }

    public void addCourse(Course c) {
        System.out.println("Adding " + c.getCourseFullName());
        System.out.println("Prof: " + c.getProf());
        c.getProf().addCoursesTaught(c);
        courses.add(c);
    }
    public ArrayList<Course> getCourses() {return courses;}
    public ArrayList<Deliverable> getDeliverables(){return deliverables;}

    public Course findCourseById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> getCoursesAvailable(Student student) {
        ArrayList<Course> notEnrolled = new ArrayList<Course>();
        for (Course course : courses) {
            if (course.meetsPrereqs(student) && !course.isPrecluded(student) && !course.isFull()) {
                notEnrolled.add(course);
            }
        }
        return notEnrolled;
    }

    public ArrayList<Course> getLectures() {
        ArrayList<Course> lectures = new ArrayList<Course>();
        for (Course course : courses) {
            if (course.getType() == "Lecture")
            {
                lectures.add(course);
            }
        }
        return lectures;
    }

    public void deleteCourseById(int id) {
        Course tmp = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                tmp = course;
            }
        }
        tmp.removeFromAll();
        courses.remove(tmp);
    }
}