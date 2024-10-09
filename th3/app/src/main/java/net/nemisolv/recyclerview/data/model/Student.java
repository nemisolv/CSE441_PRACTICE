package net.nemisolv.recyclerview.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    @SerializedName("full_name")
    private FullName fullName;
    private String gender;
    @SerializedName("birth_date")
    private String birthDate;
    private String email;
    private String address;
    private String major;
    private double gpa;
    private int year;

    // Constructor
    public Student(String id, FullName fullName, String gender, String birthDate, String email,
                   String address, String major, double gpa, int year) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
        this.major = major;
        this.gpa = gpa;
        this.year = year;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // toString method
    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", fullName=" + fullName +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", major='" + major + '\'' +
                ", gpa=" + gpa +
                ", year=" + year +
                '}';
    }

    // Inner class for FullName
    public static class FullName implements Serializable {
        private String first;
        private String last;
        private String midd;

        // Constructor
        public FullName(String first, String last, String midd) {
            this.first = first;
            this.last = last;
            this.midd = midd;
        }

        // Getters and Setters
        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getMidd() {
            return midd;
        }

        public void setMidd(String midd) {
            this.midd = midd;
        }

        // toString method
        @Override
        public String toString() {
            return last + " " + midd + " " + first;
        }

    }
}