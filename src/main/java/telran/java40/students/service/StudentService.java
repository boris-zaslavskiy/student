package telran.java40.students.service;

import java.util.List;

import telran.java40.students.dto.ScoreDto;
import telran.java40.students.dto.StudentCredentialsDto;
import telran.java40.students.dto.StudentDto;
import telran.java40.students.dto.UpdateStudentDto;

public interface StudentService {
	boolean addStudent(StudentCredentialsDto studentCredentialsDto);
	
	StudentDto findStudent(Integer id);
	
	StudentDto deleteStudent(Integer id);
	
	StudentCredentialsDto updateStudent(Integer id, UpdateStudentDto updateStudentDto);
	
	boolean addScore(Integer id, ScoreDto scoreDto);
	
	List<StudentDto> findStudentsByName(String name);
	
	long getStudentsNamesQuantity(List<String> names);
	
	List<StudentDto> getStudentsByExamScore(String exam, int min);

}
