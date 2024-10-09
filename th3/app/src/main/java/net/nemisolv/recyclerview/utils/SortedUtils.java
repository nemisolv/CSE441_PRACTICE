package net.nemisolv.recyclerview.utils;

import net.nemisolv.recyclerview.data.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortedUtils {

    // Sort by Student ID in Ascending Order
    public static void sortByStudentIdAsc(List<Student> students) {
        students.sort(Comparator.comparing(Student::getId));
    }

    // Sort by Student ID in Descending Order
    public static void sortByStudentIdDesc(List<Student> students) {
        students.sort(Comparator.comparing(Student::getId).reversed());
    }

    // Sort by Last Name and First Name in Ascending Order
    public static void sortByLastNameAndFirstNameAsc(List<Student> students) {
        students.sort((student, t1) -> {
            if (Objects.equals(student.getFullName().getFirst(), t1.getFullName().getFirst())) {
                return student.getFullName().getLast().compareTo(t1.getFullName().getLast());
            }
            return student.getFullName().getFirst().compareTo(t1.getFullName().getFirst());
        });
    }

    // Sort by Last Name and First Name in Descending Order
    public static void sortByLastNameAndFirstNameDesc(List<Student> students) {
        students.sort((student, t1) -> {
            if (Objects.equals(t1.getFullName().getFirst(), student.getFullName().getFirst())) {
                return t1.getFullName().getLast().compareTo(student.getFullName().getLast());
            }
            return t1.getFullName().getFirst().compareTo(student.getFullName().getFirst());
        });
    }

    // Sort by GPA in Ascending Order
    public static void sortByGpaAsc(List<Student> students) {
        students.sort(Comparator.comparingDouble(Student::getGpa));
    }

    // Sort by GPA in Descending Order
    public static void sortByGpaDesc(List<Student> students) {
        students.sort(Comparator.comparingDouble(Student::getGpa).reversed());
    }
}
