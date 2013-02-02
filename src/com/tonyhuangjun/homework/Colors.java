package com.tonyhuangjun.homework;

import java.util.HashMap;

import android.content.res.Resources;

public class Colors {
    public static final String TITLE_UNFINISHED = "tu";
    public static final String TITLE_FINISHED = "tf";
    public static final String BODY_UNFINISHED = "bu";
    public static final String BODY_FINISHED = "bf";
    
    
    
    public static HashMap<String, Integer> colorScheme(Resources r,
                    int c) {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
        switch (c) {
        case 1:
            h.put("tu", r.getColor(R.color.color_a1));
            h.put("bu", r.getColor(R.color.color_a2));
            h.put("tf", r.getColor(R.color.color_a3));
            h.put("bf", r.getColor(R.color.color_a4));
            break;
        case 2:
            h.put("tu", r.getColor(R.color.color_b1));
            h.put("bu", r.getColor(R.color.color_b2));
            h.put("tf", r.getColor(R.color.color_b3));
            h.put("bf", r.getColor(R.color.color_b4));
            break;
        case 3:
            h.put("tu", r.getColor(R.color.color_c1));
            h.put("bu", r.getColor(R.color.color_c2));
            h.put("tf", r.getColor(R.color.color_c3));
            h.put("bf", r.getColor(R.color.color_c4));
            break;
        case 4:
            h.put("tu", r.getColor(R.color.color_d1));
            h.put("bu", r.getColor(R.color.color_d2));
            h.put("tf", r.getColor(R.color.color_d3));
            h.put("bf", r.getColor(R.color.color_d4));
            break;
        }
        return h;
    }

}
