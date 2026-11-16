package libman06.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Reader06 implements Serializable {
    private int id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String studentCode;
    private String className;
    private String address;
    private String email;
    private String phone;

    public Reader06() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
