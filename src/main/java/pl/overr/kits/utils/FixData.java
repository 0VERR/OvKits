package pl.overr.kits.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FixData {

    private static final SimpleDateFormat sdf = new SimpleDateFormat( "YYYY-MM-dd HH:mm");

    public static String getData(long data){
        Date date = new Date(data);
        return sdf.format(date);

    }
}
