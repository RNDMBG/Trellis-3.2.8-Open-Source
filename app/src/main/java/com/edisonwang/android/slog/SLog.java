            package com.edisonwang.android.slog;

            import android.util.Log;
            import co.vine.android.util.CrashUtil;
            import java.util.Random;
            
            
    public class SLog {
                private static final String TAG = "Playback_";
                private static String sAuthority;
                public static boolean sIsAmazon;
                public static boolean sLogsOn;
                private static Random sRandom = new Random();

        public static void setup(boolean logsOn, boolean isAmazon, String authority) {
            sLogsOn = logsOn;
            sIsAmazon = isAmazon;
            sAuthority = authority;
            sRandom = new Random();
                }

        public static String getAuthority() {
            return sAuthority;
                }

                
        public static void m472v(String msg) {
            if (sLogsOn) {
                Log.v(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
        public static void m473v(String format, Object arg) {
            if (sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public static void m474v(String format, Object arg1, Object arg2) {
            if (sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public static void m476v(String format, Object[] args) {
            if (sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, args);
                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public static void m475v(String msg, Throwable throwable) {
            if (sLogsOn) {
                Log.v(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

        public static void dWithTag(String tag, String msg) {
            if (sLogsOn) {
                Log.d(tag, getCallingClass() + ":" + getCallingLine() + msg);
                    }
                }

                
        public static void m445b(String format, Object[] arg, boolean error) {
            if (sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                if (error) {
                    Log.e("Playback_[Benchmark]", ft.getMessage());
                        } else {
                    Log.d("Playback_[Benchmark]", ft.getMessage());
                        }
                if (error && sRandom.nextInt(100) < 10) {
                    CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                
        public static void m443b(String format, Object arg, boolean error) {
            if (sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                if (error) {
                    Log.e("Playback_[Benchmark]", ft.getMessage());
                        } else {
                    Log.d("Playback_[Benchmark]", ft.getMessage());
                        }
                if (error && sRandom.nextInt(100) < 10) {
                    CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                
        public static void m444b(String format, Object[] arg) {
           m445b(format, arg, false);
                }

                
       public static void m442b(String format, Object arg) {
           m443b(format, arg, false);
                }

                
       public static void m446d(String msg) {
           if (sLogsOn) {
               Log.d(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public static void m447d(String format, Object arg) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m454d(String format, Object[] args, Throwable throwable) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public static void m451d(String format, Object arg1, Throwable throwable) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public static void m450d(String format, Object arg1, Object arg2, Throwable arg3) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), arg3);
                    }
                }

                
       public static void m449d(String format, Object arg1, Object arg2, Object arg3) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m448d(String format, Object arg1, Object arg2) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m453d(String format, Object[] args) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m452d(String msg, Throwable throwable) {
           if (sLogsOn) {
               Log.d(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public static void m464i(String msg) {
           if (sLogsOn) {
               Log.i(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public static void m465i(String format, Object arg) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m468i(String format, Object arg1, Throwable ex) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), ex);
                    }
                }

                
       public static void m466i(String format, Object arg1, Object arg2) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m467i(String format, Object arg1, Object arg2, Object arg3) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m470i(String format, Object[] args) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m469i(String msg, Throwable throwable) {
           if (sLogsOn) {
               Log.i(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public static void m477w(String msg) {
           if (sLogsOn) {
               Log.w(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public static void m478w(String format, Object arg) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m481w(String format, Object[] args) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m479w(String format, Object arg1, Object arg2) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m482w(String format, Object[] arg1, Throwable throwable) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public static void m480w(String msg, Throwable throwable) {
           if (sLogsOn) {
               Log.w(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public static void m455e(String msg) {
           if (sLogsOn) {
               Log.e(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public static void m456e(String format, Object arg) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m457e(String format, Object arg1, Object arg2) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public static void m461e(String format, Object[] arg, Throwable throwable) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public static void m458e(String format, Object arg, Throwable throwable) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public static void m460e(String format, Object[] args) {
           if (sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.e(TAG + getCallingClass(), ft.getMessage());
                    }
                }

                
       public static void m459e(String msg, Throwable throwable) {
           if (sLogsOn) {
               Log.e(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

       private static String getCallingClass() {
           String className = Thread.currentThread().getStackTrace()[4].getClassName();
           int lastPeriod = className.lastIndexOf(46);
           if (lastPeriod >= className.length() - 1) {
               return "";
                    }
           if (lastPeriod >= 0) {
               return className.substring(lastPeriod + 1);
                    }
           return className;
                }

       private static String getCallingLine() {
           return Thread.currentThread().getStackTrace()[4].getMethodName() + "():" + Thread.currentThread().getStackTrace()[4].getLineNumber() + " - ";
                }

       private static String getCallingLine2() {
           return Thread.currentThread().getStackTrace()[5].getMethodName() + "():" + Thread.currentThread().getStackTrace()[5].getLineNumber() + " - ";
                }

       private static String getCallingClass2() {
           String className = Thread.currentThread().getStackTrace()[5].getClassName();
           int lastPeriod = className.lastIndexOf(46);
           if (lastPeriod >= className.length() - 1) {
               return "";
                    }
           if (lastPeriod >= 0) {
               return className.substring(lastPeriod + 1);
                    }
           return className;
                }

                
       public static void m462e2(String msg, Exception e) {
           if (sLogsOn) {
               Log.e(TAG + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                
       public static void m471i2(String msg) {
           if (sLogsOn) {
               Log.i(TAG + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                
       public static void m463e2(String msg, Throwable e) {
           if (sLogsOn) {
               Log.e(TAG + getCallingClass2(), getCallingLine2() + msg, e);
                    }
                }
            }
