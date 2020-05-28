package com.soapwebservices.soapmanagment;

import com.soapwebservices.soapmanagment.bean.Course;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CourseDetailsService {

    private static List<Course> courses = new ArrayList<>();

    static {
        Course course1 = new Course(1, "Spring", "Lorem");
        courses.add(course1);

        Course course2 = new Course(2, "Struts", "Ipsum");
        courses.add(course2);

        Course course3 = new Course(3, "Java", "Ipsum");
        courses.add(course3);

        Course course4 = new Course(4, "J2ee", "Ipsum");
        courses.add(course4);
        }


    Course findById(int id){
        for(Course course:courses ){
            if(course.getId() == id){
                return course;
            }
        }
        return null;
    }

    List<Course> findAll(){
        return courses;
    }

    public enum Status {
        FAILURE,SUCCESS
    }

    public Status deleteById(int id){

        Iterator<Course> iterator = courses.iterator();
        while(iterator.hasNext()){
            Course course = iterator.next();
            if(course.getId() == id){
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }

}
