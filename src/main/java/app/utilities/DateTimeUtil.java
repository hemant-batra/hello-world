package app.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");

    public static Timestamp toTimestamp(String strTimestamp)  {
        try {
            return new Timestamp(sdf.parse(strTimestamp).getTime());
        } catch(ParseException pe) {
            throw new RuntimeException(pe);
        }
    }
}
