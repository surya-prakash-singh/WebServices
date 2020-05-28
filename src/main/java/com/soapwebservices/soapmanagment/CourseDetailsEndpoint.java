package com.soapwebservices.soapmanagment;

import com.courseinfo.courses.*;
import com.soapwebservices.soapmanagment.bean.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Iterator;
import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService service;

    @ResponsePayload
    @PayloadRoot(namespace = "http://courseInfo.com/courses",localPart = "GetCourseDetailsRequest")
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request){

        GetCourseDetailsResponse response =  new GetCourseDetailsResponse();
        Course course = service.findById(request.getId());
        if(course == null) throw new CourseNotFoundException("Course Not Found");
        response.setCourseDetails(mapCourse(course));
        return response;
    }

    private CourseDetails mapCourse(Course course) {
        CourseDetails couseDetails = new CourseDetails();
        couseDetails.setId(course.getId());
        couseDetails.setName(course.getName());
        couseDetails.setDescription(course.getDescription());
        return couseDetails;
    }

    @ResponsePayload
    @PayloadRoot(namespace = "http://courseInfo.com/courses", localPart = "GetAllCourseDetailsRequest")
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request){

        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        List<Course> courses = service.findAll();

        for (Course course:
                courses ) {
            response.getCourseDetails().add(mapCourse(course));
        }
    return response;
    }

    @ResponsePayload
    @PayloadRoot(namespace ="http://courseInfo.com/courses", localPart = "DeleteCourseRequest")
    public DeleteCourseResponse processdeleteCourseRequest(@RequestPayload DeleteCourseRequest request){
        CourseDetailsService.Status status = service.deleteById(request.getId());

        DeleteCourseResponse response = new DeleteCourseResponse();
        response.setStatus(mapStatus(status));
        return response;
    }

    private com.courseinfo.courses.Status mapStatus(CourseDetailsService.Status Status){
    if(Status == CourseDetailsService.Status.FAILURE){
        return com.courseinfo.courses.Status.FAILURE;
    }else{
        return com.courseinfo.courses.Status.SUCCESS;
    }
    }
}
