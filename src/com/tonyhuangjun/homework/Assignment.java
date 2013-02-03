package com.tonyhuangjun.homework;

public class Assignment {
    
    private String name, date;
    public Assignment(String _name, String _date) {
        name = _name;
        date = _date;
    }
    
    public Assignment(String _name) {
        name = _name;
    }
    
    // Takes the form: "assignmentname(startOfDate)date(endOfAssignment)"
    public String toString() {
        String result = name;
        if (hasDate())
            result += Interpreter.startOfDate + date + Interpreter.endOfAssignment;
        else
            result += Interpreter.endOfAssignment;
        return result;
    }
    
    public boolean hasDate(){
        return date != null;
    }

}
