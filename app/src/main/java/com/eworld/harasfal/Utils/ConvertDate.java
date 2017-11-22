package com.eworld.harasfal.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by evox on 23/11/16.
 */

public class ConvertDate {



    public static  String ConvertJsonDate(String date)
    {

        date = date.replace("/Date(", "").replace(")/", "");
        long time = Long.parseLong(date);
        Date d= new Date(time); //dd/MM/yyyy HH:mm:ss z
        return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
    }
    public static  String ConvertJsonHour(String date)
    {

        date = date.replace("/Date(", "").replace(")/", "");
        long time = Long.parseLong(date);
        Date d= new Date(time); //dd/MM/yyyy
        return new SimpleDateFormat("HH:mm").format(d).toString();
    }
}
