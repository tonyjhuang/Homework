package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

public class Interpreter {
    public static final String endOfAssignment = "‼"; // alt+1555
    public static final String startOfDate = "¶"; // alt+1556
    public static final String NULL = "Assignment" + endOfAssignment;

    public static Assignment stringToAssignment(String s) {
        int index = s.indexOf(endOfAssignment);
        int index2 = s.indexOf(startOfDate);
        if (index2 != -1)
            return new Assignment(s.substring(0, index2),
                            s.substring(index2 + 1, index));
        else
            return new Assignment(s.substring(0, index));

    }

    public static ArrayList<Assignment> stringToArrayList2(String s) {
        ArrayList<Assignment> result = new ArrayList<Assignment>();
        int index = s.indexOf(endOfAssignment);
        int index2 = s.indexOf(startOfDate);
        while (index != 0) {
            if (index2 != -1 && index2 < index) {
                // There is a date present in this Assignment.
                result.add(new Assignment(s.substring(0, index2), s
                                .substring(index2 + 1, index)));
            } else if (index + 1 == s.length()) {
                // There are no more Assignments.
                break;
            } else {
                // There is only a name present in this Assignment.
                result.add(new Assignment(s.substring(0, index)));
            }
            s = s.substring(index + 1, s.length());
            index = s.indexOf(endOfAssignment);
            index2 = s.indexOf(startOfDate);

        }
        return result;
    }

    public static String arrayListToString2(ArrayList<Assignment> a) {
        String result = "";
        Iterator<Assignment> i = a.iterator();

        while (i.hasNext()) {
            Assignment a2 = i.next();
            result += a2.toString();
        }
        return result;
    }

    public static ArrayList<String> stringToArrayList(String s) {
        ArrayList<String> result = new ArrayList<String>();

        int index = s.indexOf("|");
        while (index != 0) {
            result.add(s.substring(0, index));
            if (index + 1 == s.length())
                break;
            s = s.substring(index + 1, s.length());
            index = s.indexOf("|");
        }
        return result;
    }

    public static String arrayListToString(ArrayList<String> a) {
        String result = "";
        Iterator<String> i = a.iterator();

        while (i.hasNext()) {
            result += i.next() + "|";
        }
        return result;
    }
}
