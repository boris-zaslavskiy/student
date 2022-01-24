package telran.java40.students.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import telran.java40.students.dao.StudentsRepository;
import telran.java40.students.dto.ScoreDto;
import telran.java40.students.dto.StudentCredentialsDto;
import telran.java40.students.dto.StudentDto;
import telran.java40.students.dto.UpdateStudentDto;
import telran.java40.students.dto.exeptions.StudentNotFoundExeption;
import telran.java40.students.model.Student;

@Service
public class StudentServiceImpl implements StudentService {
	
	StudentsRepository studentsRepository;
	ModelMapper modelMapper;
	
	@Autowired
	public StudentServiceImpl(StudentsRepository studentsRepository, ModelMapper modelMapper) {
		this.studentsRepository = studentsRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean addStudent(StudentCredentialsDto studentCredentialsDto) {
		if (studentsRepository.existsById(studentCredentialsDto.getId())) {
			return false;
		}
		Student student = modelMapper.map(studentCredentialsDto,Student.class);
		studentsRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentsRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentDto deleteStudent(Integer id) {
		Student student = studentsRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		studentsRepository.deleteById(id);
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentCredentialsDto updateStudent(Integer id, UpdateStudentDto updateStudentDto) {
		Student student = studentsRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		student.setName(updateStudentDto.getName());
		student.setPassword(updateStudentDto.getPassword());
		studentsRepository.save(student); 
		return modelMapper.map(student, StudentCredentialsDto.class);
				
	}

	@Override
	public boolean addScore(Integer id, ScoreDto scoreDto) {
		Student student = studentsRepository.findById(id).orElseThrow(StudentNotFoundExeption::new);
		boolean res = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
		studentsRepository.save(student); 
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		//List<Student> students = studentsRepository.findByName( name);
	//	List<StudentDto> studentsDto= new ArrayList<>();
	//	for (Student student : students) {
	//		studentsDto.add(modelMapper.map(student, StudentDto.class));
	//	}
	//	return studentsDto;
		//return studentsRepository.findAll().stream()
		//		.filter(s -> s.getName().equalsIgnoreCase(name))
		//		.map(s -> modelMapper.map(s, StudentDto.class))
		//		.collect(Collectors.toList()); 
		return studentsRepository.findByNameIgnoreCase( name)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList()); 
		
	}

	@Override
	public long getStudentsNamesQuantity(List<String> names) {
		
		return studentsRepository.countByNameInIgnoreCase( names);
	}

	@Override
	public List<StudentDto> getStudentsByExamScore(String exam, int min) {
	//	List<Student> students = studentsRepository.findByExamScoreGraterThen(exam, min);
	//	List<StudentDto> studentsDto= new ArrayList<>();
	//	for (Student student : students) {
	//		studentsDto.add(modelMapper.map(student, StudentDto.class));
	//	}
	//	return studentsDto;
		return studentsRepository.findByExamAndScoreGreaterEqualsThen(exam,min)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
	}

}
