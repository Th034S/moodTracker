package com.thomas.siadous.moodtracker.controller;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;


public class Time {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        System.out.println(sdf.format(timeStamp));

    }

}

