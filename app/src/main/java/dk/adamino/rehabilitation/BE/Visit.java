package dk.adamino.rehabilitation.BE;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adamino.
 */
public class Visit {
    public String uid;
    public Date date;
    public String note;
    private SimpleDateFormat mSimpleDateFormat;

    public Visit() {String pattern = "d/M - yyyy";
        mSimpleDateFormat = new SimpleDateFormat(pattern);
    }

    public String getDate() {
        return mSimpleDateFormat.format(date);
    }
}
