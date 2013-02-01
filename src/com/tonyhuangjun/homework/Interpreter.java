package com.tonyhuangjun.homework;

import java.util.ArrayList;
import java.util.Iterator;

public class Interpreter {
    public static ArrayList<String> stringToArrayList(String s)
    {
        ArrayList<String> result = new ArrayList<String>();
        
        int index = s.indexOf("|");
        while(index != 0){
            result.add(s.substring(0, index));
            if(index + 1 == s.length())
                break;
            s = s.substring(index + 1, s.length());
            index = s.indexOf("|");
        }
        return result;
    }
    public static String arrayListToString(ArrayList<String> a){
        String result = "";
        Iterator<String> i = a.iterator();
        
        while(i.hasNext()){
            result += i.next() + "|";
        } 
        return result;
    }
}
