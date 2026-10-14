            package com.squareup.okhttp.internal.http;

            import java.text.DateFormat;
            import java.text.ParsePosition;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.Locale;
            import java.util.TimeZone;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public final class HttpDate {
                private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
                private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT = new C12761();
                private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
                private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length];

/* 28 */        static /* synthetic */ TimeZone access$000() {
/* 29 */            return GMT;
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.http.HttpDate$1 */
/* 36 */        static class C12761 extends ThreadLocal<DateFormat> {
/* 37 */            C12761() {
                    }


                    @Override // java.lang.ThreadLocal
                    /* JADX INFO: renamed from: initialValue, reason: avoid collision after fix types in other method */
/* 40 */            protected DateFormat initialValue() {
/* 41 */                DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
/* 42 */                rfc1123.setLenient(false);
/* 43 */                rfc1123.setTimeZone(HttpDate.access$000());
/* 44 */                return rfc1123;
                    }
                }

/* 75 */        public static Date parse(String value) {
/* 76 */            if (value.length() == 0) {
                        return null;
                    }
/* 80 */            ParsePosition position = new ParsePosition(0);
/* 81 */            Date date = STANDARD_DATE_FORMAT.get().parse(value, position);
/* 82 */            if (position.getIndex() != value.length()) {
                        synchronized (BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS) {
                            try {
                                int count = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
                                for (int i = 0; i < count; i++) {
/* 89 */                            DateFormat format = BROWSER_COMPATIBLE_DATE_FORMATS[i];
/* 90 */                            if (format == null) {
/* 91 */                                format = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[i], Locale.US);
/* 94 */                                format.setTimeZone(GMT);
/* 95 */                                BROWSER_COMPATIBLE_DATE_FORMATS[i] = format;
                                    }
/* 97 */                            position.setIndex(0);
/* 98 */                            Date result = format.parse(value, position);
/* 99 */                            if (position.getIndex() != 0) {
/* 109 */                               return result;
                                    }
                                }
                                return null;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
/* 109 */           return date;
                }

/* 113 */       public static String format(Date value) {
/* 114 */           return STANDARD_DATE_FORMAT.get().format(value);
                }

/* 116 */       private HttpDate() {
                }
            }
