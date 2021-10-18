package com.team20.services;

import com.team20.core.Student;
import com.team20.dataTransferObjects.StudentDto;

public interface IUserService {
    Student registerNewStudent(StudentDto studentDto);
}