package model;

public class BAKTeacher {
private String teacherId;
private String firstName; 
private String lastName;
private String email;

public BAKTeacher(String teacherId, String firstName, String lastName, String email) {
	this.teacherId = teacherId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
}

//getters 

public String getTeacherId() {
	return teacherId;
}

public String getFirstName() {
	return firstName;
}

public String getLastName() {
	return lastName;
}

public String getEmail() {
	return email;
}

// Simple toString for easy logging and debugging
@Override
public String toString() {
    return "Teacher{" +
           "id='" + teacherId + '\'' +
           ", name='" + firstName + " " + lastName + '\'' +
           ", email='" + email + '\'' +
           '}';
}
}
