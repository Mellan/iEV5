package com.example.iev5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mellan on 2016/6/26.
 */
public class StringUtils {
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat dateTimeFormat;
    private static SimpleDateFormat dateTimeNoSplitFormat;

    static {
        StringUtils.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        StringUtils.dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        StringUtils.dateTimeNoSplitFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
    }

    public StringUtils() {
        super();
    }

    public static String getASCIIString(int num) {
        return StringUtils.getASCIIString(Integer.toString(num));
    }

    public static String getASCIIString(String originalString) {
        StringBuffer v1 = new StringBuffer();
        int v0;
        for(v0 = 0; v0 < originalString.length(); ++v0) {
            v1.append(Integer.toHexString(originalString.charAt(v0)));
        }

        return v1.toString();
    }

    public static String getCurrentTime() {
        return StringUtils.dateTimeFormat.format(new Date());
    }

    public static String getNoSplitCurrentTime() {
        //System.out.print(StringUtils.dateTimeNoSplitFormat.format(new Date()));
        return StringUtils.dateTimeNoSplitFormat.format(new Date());
    }

    public static boolean hasStringContent(String str) {
        boolean v0 = str == null || str.trim().length() == 0 ? false : true;
        return v0;
    }

    public static boolean isDateSectionValid(String beginTime, String endTime) {
        boolean v0 = false;
        try {
            if(!StringUtils.hasStringContent(beginTime)) {
                return v0;
            }

            if(!StringUtils.hasStringContent(endTime)) {
                return v0;
            }

            if(beginTime.compareTo(endTime) >= 0) {
                return v0;
            }

            StringUtils.dateFormat.parse(beginTime);
            StringUtils.dateFormat.parse(endTime);
            v0 = true;
        }
        catch(ParseException v1) {
        }

        return v0;
    }
}
