            package com.edisonwang.android.slog;

            import android.util.Log;
            import co.vine.android.util.CrashUtil;
            import java.util.HashMap;
            import java.util.Random;
            
            
    public class SLogger {
                private final String mTag;
                private static Random sRandom = new Random();
                private static final HashMap<String, SLogger> sLoggers = new HashMap<>();
                private final HashMap<String, Long> mTimings = new HashMap<>();
                private long mStartTime = -1;

        public static synchronized SLogger getLogger(String tag) {
                    SLogger logger;
                    try {
                        logger = sLoggers.get(tag);
                if (logger == null) {
                    logger = new SLogger(tag);
                    sLoggers.put(tag, logger);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return logger;
                }

        private SLogger(String tag) {
            this.mTag = tag + "_";
                }

                
        public void m513v(String msg) {
            if (SLog.sLogsOn) {
                Log.v(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

        public void timingStart(String tag) {
            if (SLog.sLogsOn) {
                this.mTimings.put(tag, Long.valueOf(System.currentTimeMillis()));
                    }
                }

        public void timingStop(String tag) {
            if (SLog.sLogsOn) {
                Long time = this.mTimings.get(tag);
                if (time == null) {
                    m506i("Timing does not exist for {}", tag);
                        } else {
                    m507i("{} took {} ms", tag, Long.valueOf(System.currentTimeMillis() - time.longValue()));
                        }
                    }
                }

        public void timingStart() {
            this.mStartTime = System.currentTimeMillis();
                }

        public void timingStop() {
            if (SLog.sLogsOn) {
                        if (this.mStartTime < 0) {
                    m505i("Timing does not exist for this logger.");
                        } else {
                    m507i("{} took {} ms", this.mTag, Long.valueOf(System.currentTimeMillis() - this.mStartTime));
                        }
                    }
                }

                
        public void m514v(String format, Object arg) {
            if (SLog.sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg);
                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public void m515v(String format, Object arg1, Object arg2) {
            if (SLog.sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public void m517v(String format, Object[] args) {
            if (SLog.sLogsOn) {
                FormattingTuple ft = MessageFormatter.format(format, args);
                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
        public void m516v(String msg, Throwable throwable) {
            if (SLog.sLogsOn) {
                Log.v(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public void m486b(String format, Object[] arg, boolean error) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               if (error) {
                   Log.e(this.mTag + "[Benchmark]", ft.getMessage());
                        } else {
                   Log.d(this.mTag + "[Benchmark]", ft.getMessage());
                        }
               if (error && sRandom.nextInt(100) < 10) {
                   CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                
       public void m484b(String format, Object arg, boolean error) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               if (error) {
                   Log.e(this.mTag + "[Benchmark]", ft.getMessage());
                        } else {
                   Log.d(this.mTag + "[Benchmark]", ft.getMessage());
                        }
               if (error && sRandom.nextInt(100) < 10) {
                   CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                
       public void m485b(String format, Object[] arg) {
           m486b(format, arg, false);
                }

                
       public void m483b(String format, Object arg) {
           m484b(format, arg, false);
                }

                
       public void m487d(String msg) {
           if (SLog.sLogsOn) {
               Log.d(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public void m488d(String format, Object arg) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m495d(String format, Object[] args, Throwable throwable) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public void m492d(String format, Object arg1, Throwable throwable) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public void m491d(String format, Object arg1, Object arg2, Throwable arg3) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), arg3);
                    }
                }

                
       public void m490d(String format, Object arg1, Object arg2, Object arg3) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m489d(String format, Object arg1, Object arg2) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m494d(String format, Object[] args) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m493d(String msg, Throwable throwable) {
           if (SLog.sLogsOn) {
               Log.d(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public void m505i(String msg) {
           if (SLog.sLogsOn) {
               Log.i(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public void m506i(String format, Object arg) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m509i(String format, Object arg1, Throwable ex) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), ex);
                    }
                }

                
       public void m507i(String format, Object arg1, Object arg2) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m508i(String format, Object arg1, Object arg2, Object arg3) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m511i(String format, Object[] args) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m510i(String msg, Throwable throwable) {
           if (SLog.sLogsOn) {
               Log.i(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public void m518w(String msg) {
           if (SLog.sLogsOn) {
               Log.w(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public void m519w(String format, Object arg) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m522w(String format, Object[] args) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m520w(String format, Object arg1, Object arg2) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m523w(String format, Object[] arg1, Throwable throwable) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1);
               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public void m521w(String msg, Throwable throwable) {
           if (SLog.sLogsOn) {
               Log.w(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public void m496e(String msg) {
           if (SLog.sLogsOn) {
               Log.e(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                
       public void m497e(String format, Object arg) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m498e(String format, Object arg1, Object arg2) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                
       public void m502e(String format, Object[] arg, Throwable throwable) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public void m499e(String format, Object arg, Throwable throwable) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, arg);
               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                
       public void m501e(String format, Object[] args) {
           if (SLog.sLogsOn) {
               FormattingTuple ft = MessageFormatter.format(format, args);
               Log.e(this.mTag + getCallingClass(), ft.getMessage());
                    }
                }

                
       public void m500e(String msg, Throwable throwable) {
           if (SLog.sLogsOn) {
               Log.e(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                
       public void m503e2(String msg, Exception e) {
           if (SLog.sLogsOn) {
               Log.e(this.mTag + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                
       public void m512i2(String msg) {
           if (SLog.sLogsOn) {
               Log.i(this.mTag + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                
       public void m504e2(String msg, Throwable e) {
           if (SLog.sLogsOn) {
               Log.e(this.mTag + getCallingClass2(), getCallingLine2() + msg, e);
                    }
                }

       protected String getCallingClass() {
           return getShortClassName(Thread.currentThread().getStackTrace()[5].getClassName());
                }

       protected String getCallingClass2() {
           return getShortClassName(Thread.currentThread().getStackTrace()[6].getClassName());
                }

       protected String getCallingLine() {
           return getLine(Thread.currentThread().getStackTrace()[5]);
                }

       protected String getCallingLine2() {
           return getLine(Thread.currentThread().getStackTrace()[6]);
                }

       protected static String getShortClassName(String className) {
           int lastPeriod = className.lastIndexOf(46);
           if (lastPeriod >= className.length() - 1) {
               return "";
                    }
           if (lastPeriod >= 0) {
               return className.substring(lastPeriod + 1);
                    }
           return className;
                }

       protected static String getLine(StackTraceElement element) {
           return element.getMethodName() + "():" + element.getLineNumber() + " - ";
                }
            }
