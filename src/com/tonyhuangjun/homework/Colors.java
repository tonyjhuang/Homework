package com.tonyhuangjun.homework;

import java.util.HashMap;

import android.content.res.Resources;

public class Colors {
    public static HashMap<String, Integer> colorScheme1(Resources r) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        h.put("tu", r.getColor(R.color.color_a1));
        h.put("bu", r.getColor(R.color.color_a2));
        h.put("tf", r.getColor(R.color.color_a3));
        h.put("bf", r.getColor(R.color.color_a4));
        return h;
    }

    public static HashMap<String, Integer> colorScheme2(Resources r) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        h.put("tu", r.getColor(R.color.color_b1));
        h.put("bu", r.getColor(R.color.color_b2));
        h.put("tf", r.getColor(R.color.color_b3));
        h.put("bf", r.getColor(R.color.color_b4));
        return h;
    }

    public static HashMap<String, Integer> colorScheme3(Resources r) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        h.put("tu", r.getColor(R.color.color_c1));
        h.put("bu", r.getColor(R.color.color_c2));
        h.put("tf", r.getColor(R.color.color_c3));
        h.put("bf", r.getColor(R.color.color_c4));
        return h;
    }

    public static HashMap<String, Integer> colorScheme4(Resources r) {

        HashMap<String, Integer> h = new HashMap<String, Integer>();
        h.put("tu", r.getColor(R.color.color_d1));
        h.put("bu", r.getColor(R.color.color_d2));
        h.put("tf", r.getColor(R.color.color_d3));
        h.put("bf", r.getColor(R.color.color_d4));
        return h;
    }
}
