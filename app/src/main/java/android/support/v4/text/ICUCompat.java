            package android.support.v4.text;

            import android.os.Build;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class ICUCompat {
                private static final ICUCompatImpl IMPL;

                interface ICUCompatImpl {
                    String addLikelySubtags(String str);

                    String getScript(String str);
                }

/* 26 */        static class ICUCompatImplBase implements ICUCompatImpl {
/* 27 */            ICUCompatImplBase() {
                    }

                    @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
/* 30 */            public String getScript(String locale) {
/* 31 */                return null;
                    }

                    @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
/* 35 */            public String addLikelySubtags(String locale) {
/* 36 */                return locale;
                    }
                }

/* 38 */        static class ICUCompatImplIcs implements ICUCompatImpl {
/* 39 */            ICUCompatImplIcs() {
                    }

                    @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
/* 42 */            public String getScript(String locale) {
/* 43 */                return ICUCompatIcs.getScript(locale);
                    }

                    @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
/* 47 */            public String addLikelySubtags(String locale) {
/* 48 */                return ICUCompatIcs.addLikelySubtags(locale);
                    }
                }

                static {
/* 55 */            int version = Build.VERSION.SDK_INT;
/* 56 */            if (version >= 14) {
/* 57 */                IMPL = new ICUCompatImplIcs();
                    } else {
/* 59 */                IMPL = new ICUCompatImplBase();
                    }
                }

/* 69 */        public static String getScript(String locale) {
/* 70 */            return IMPL.getScript(locale);
                }

/* 95 */        public static String addLikelySubtags(String locale) {
/* 96 */            return IMPL.addLikelySubtags(locale);
                }
            }
