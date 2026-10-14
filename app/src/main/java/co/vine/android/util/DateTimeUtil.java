            package co.vine.android.util;

            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.Calendar;
            import java.util.Date;
            import java.util.Locale;
            import java.util.TimeZone;
            
            
    public class DateTimeUtil {
        public static Calendar getStartOfDay() {
            return clearTime(Calendar.getInstance());
                }

        public static Calendar clearTime(Calendar calendar) {
            calendar.set(11, 0);
            calendar.set(10, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            return calendar;
                }

        public static long getTimeInMsFromString(String time, String format) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            long timestamp = 0;
                    try {
                Date date = sdf.parse(time);
                timestamp = date.getTime();
                TimeZone tz = TimeZone.getDefault();
                Date now = new Date();
                int offsetFromUtc = tz.getOffset(now.getTime());
                return timestamp + ((long) offsetFromUtc);
                    } catch (ParseException e) {
                return timestamp;
                    }
                }

        public static String getTimeInStringFromMs(long time, String format) {
            return new SimpleDateFormat(format, Locale.US).format(new Date(time));
                }
            }
