            package co.vine.android.util;

            import java.text.ParseException;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class SynchronizedDateFormat extends SimpleDateFormat {
                private static final long serialVersionUID = 6612933786679648650L;

/* 14 */        public SynchronizedDateFormat() {
                }

/* 18 */        public SynchronizedDateFormat(String pattern) {
/* 19 */            super(pattern);
                }

/* 22 */        public SynchronizedDateFormat(String pattern, Locale locale) {
/* 23 */            super(pattern, locale);
                }

                @Override // java.text.DateFormat
/* 27 */        public Date parse(String s) throws ParseException {
                    Date date;
                    synchronized (this) {
                        try {
/* 29 */                    date = super.parse(s);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return date;
                }

                @Override // java.text.SimpleDateFormat
/* 34 */        public void applyPattern(String s) {
                    synchronized (this) {
                        try {
/* 36 */                    super.applyPattern(s);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
