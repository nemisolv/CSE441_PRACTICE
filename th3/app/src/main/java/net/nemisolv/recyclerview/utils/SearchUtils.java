package net.nemisolv.recyclerview.utils;

import net.nemisolv.recyclerview.data.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
public static List<Student> searchStudents(List<Student> studentList, String query) {
    List<Student> searchResults = new ArrayList<>();
    for (Student student : studentList) {
        String fullName = student.getFullName().getLast() + " " + student.getFullName().getMidd() + " " + student.getFullName().getFirst();
        if (fullName.toLowerCase().contains(query.toLowerCase())) {
            searchResults.add(student);
        }
    }
    return searchResults;
}
}
