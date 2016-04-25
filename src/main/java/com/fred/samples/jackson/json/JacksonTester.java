package com.fred.samples.jackson.json;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fred.samples.jackson.json.bean.Student;

import java.io.IOException;

/**
 * Inspired from http://www.tutorialspoint.com/jackson/jackson_quick_guide.htm
 * @author Frédéric Massart
 */
public class JacksonTester {

	public static void main(String[] args) {
		System.out.println("Hi");
		ObjectMapper mapper = new ObjectMapper();

		String jsonString = "{\"name\" : \"Mahesh\", \"age\":21}";

		try {
			MappingIterator<Student> objectMappingIterator = mapper.reader().forType(Student.class).readValues(jsonString);

			Student student = objectMappingIterator.next();

			System.out.println(student);

			mapper.enable(SerializationFeature.INDENT_OUTPUT);

			jsonString = mapper.writeValueAsString(student);
			System.out.println(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
