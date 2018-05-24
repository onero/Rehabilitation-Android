package dk.adamino.rehabilitation.BE;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Adamino.
 */
public class Visit implements Comparable<Visit> {
    public Date date;
    public String note;
    private SimpleDateFormat mSimpleDateFormat;

    public Visit() {
        // EEEE is being used to get the whole day instead of only 3 characters.
        String pattern = "EEEE, d/M - yyyy";
        mSimpleDateFormat = new SimpleDateFormat(pattern, new Locale("da", "DK"));
    }

    public String getDateAsUppercaseString() {
        return mSimpleDateFormat.format(date).toUpperCase();
    }

    @Override
    public int compareTo(@NonNull Visit o) {
        // Sort by newest date first
        return o.date.compareTo(date);
    }
}
