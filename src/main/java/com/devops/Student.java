package com.devops;

import java.util.ArrayList;

public class Student {
    static ArrayList<Integer> studentsIDs = new ArrayList<Integer>();
    int id;
    String lastName;
    String firstName;

    public Student(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;

        /* Check if the list is empty then it's the first student else we will +1 to the last one*/
        /* Very robust solution IK*/
        id = studentsIDs.size() > 0 ? (studentsIDs.get(studentsIDs.size() - 1 ) + 1) : 1;
        studentsIDs.add(id);
    }

    /* Method to return the student ID if doesn't exist will return 0 */
    public Boolean checkStudentExistsById(int id){
        return studentsIDs.contains(id);
    }
}
