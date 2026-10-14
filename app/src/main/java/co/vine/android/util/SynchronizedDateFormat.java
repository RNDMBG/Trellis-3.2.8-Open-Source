            package co.vine.android.util;

            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.Locale;
            
            
    public class SynchronizedDateFormat extends SimpleDateFormat {
                private static final long serialVersionUID = 6612933786679648650L;

        public SynchronizedDateFormat() {
                }

        public SynchronizedDateFormat(String pattern) {
            super(pattern);
                }

        public SynchronizedDateFormat(String pattern, Locale locale) {
            super(pattern, locale);
                }

                @Override 
        public Date parse(String s) throws ParseException {
                    Date date;
                    synchronized (this) {
                        try {
                    date = super.parse(s);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return date;
                }

                @Override 
        public void applyPattern(String s) {
                    synchronized (this) {
                        try {
                    super.applyPattern(s);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
