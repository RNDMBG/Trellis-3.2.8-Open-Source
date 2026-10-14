            package co.vine.android.util;

            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.Calendar;
            import java.util.Date;
            import java.util.Locale;
            import java.util.TimeZone;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class DateTimeUtil {
/* 15 */        public static Calendar getStartOfDay() {
/* 16 */            return clearTime(Calendar.getInstance());
                }

/* 19 */        public static Calendar clearTime(Calendar calendar) {
/* 20 */            calendar.set(11, 0);
/* 21 */            calendar.set(10, 0);
/* 22 */            calendar.set(12, 0);
/* 23 */            calendar.set(13, 0);
/* 24 */            calendar.set(14, 0);
/* 25 */            return calendar;
                }

/* 36 */        public static long getTimeInMsFromString(String time, String format) {
/* 37 */            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
/* 38 */            long timestamp = 0;
                    try {
/* 40 */                Date date = sdf.parse(time);
/* 41 */                timestamp = date.getTime();
/* 42 */                TimeZone tz = TimeZone.getDefault();
/* 43 */                Date now = new Date();
/* 44 */                int offsetFromUtc = tz.getOffset(now.getTime());
/* 45 */                return timestamp + ((long) offsetFromUtc);
                    } catch (ParseException e) {
/* 48 */                return timestamp;
                    }
                }

/* 51 */        public static String getTimeInStringFromMs(long time, String format) {
/* 52 */            return new SimpleDateFormat(format, Locale.US).format(new Date(time));
                }
            }
