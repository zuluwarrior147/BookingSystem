package dao.util.time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Zulu Warrior on 5/21/2017.
 */
public class TimeConverter {
    public static Date toDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }
}
