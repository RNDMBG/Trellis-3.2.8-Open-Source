            package com.edisonwang.android.slog;

            import android.util.Log;
            import co.vine.android.util.CrashUtil;
            import java.util.HashMap;
            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class SLogger {
                private final String mTag;
                private static Random sRandom = new Random();
                private static final HashMap<String, SLogger> sLoggers = new HashMap<>();
                private final HashMap<String, Long> mTimings = new HashMap<>();
                private long mStartTime = -1;

/* 25 */        public static synchronized SLogger getLogger(String tag) {
                    SLogger logger;
                    try {
                        logger = sLoggers.get(tag);
/* 27 */                if (logger == null) {
/* 28 */                    logger = new SLogger(tag);
/* 29 */                    sLoggers.put(tag, logger);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return logger;
                }

/* 33 */        private SLogger(String tag) {
/* 35 */            this.mTag = tag + "_";
                }

                /* JADX INFO: renamed from: v */
/* 38 */        public void m513v(String msg) {
/* 39 */            if (SLog.sLogsOn) {
/* 40 */                Log.v(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

/* 44 */        public void timingStart(String tag) {
/* 45 */            if (SLog.sLogsOn) {
/* 46 */                this.mTimings.put(tag, Long.valueOf(System.currentTimeMillis()));
                    }
                }

/* 50 */        public void timingStop(String tag) {
/* 51 */            if (SLog.sLogsOn) {
/* 52 */                Long time = this.mTimings.get(tag);
/* 53 */                if (time == null) {
/* 54 */                    m506i("Timing does not exist for {}", tag);
                        } else {
/* 56 */                    m507i("{} took {} ms", tag, Long.valueOf(System.currentTimeMillis() - time.longValue()));
                        }
                    }
                }

/* 61 */        public void timingStart() {
/* 62 */            this.mStartTime = System.currentTimeMillis();
                }

/* 65 */        public void timingStop() {
/* 66 */            if (SLog.sLogsOn) {
                        if (this.mStartTime < 0) {
/* 68 */                    m505i("Timing does not exist for this logger.");
                        } else {
/* 70 */                    m507i("{} took {} ms", this.mTag, Long.valueOf(System.currentTimeMillis() - this.mStartTime));
                        }
                    }
                }

                /* JADX INFO: renamed from: v */
/* 75 */        public void m514v(String format, Object arg) {
/* 76 */            if (SLog.sLogsOn) {
/* 77 */                FormattingTuple ft = MessageFormatter.format(format, arg);
/* 78 */                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 82 */        public void m515v(String format, Object arg1, Object arg2) {
/* 83 */            if (SLog.sLogsOn) {
/* 84 */                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 85 */                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 89 */        public void m517v(String format, Object[] args) {
/* 90 */            if (SLog.sLogsOn) {
/* 91 */                FormattingTuple ft = MessageFormatter.format(format, args);
/* 92 */                Log.v(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 96 */        public void m516v(String msg, Throwable throwable) {
/* 97 */            if (SLog.sLogsOn) {
/* 98 */                Log.v(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 102 */       public void m486b(String format, Object[] arg, boolean error) {
/* 103 */           if (SLog.sLogsOn) {
/* 104 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 105 */               if (error) {
/* 106 */                   Log.e(this.mTag + "[Benchmark]", ft.getMessage());
                        } else {
/* 108 */                   Log.d(this.mTag + "[Benchmark]", ft.getMessage());
                        }
/* 110 */               if (error && sRandom.nextInt(100) < 10) {
/* 111 */                   CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 116 */       public void m484b(String format, Object arg, boolean error) {
/* 117 */           if (SLog.sLogsOn) {
/* 118 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 119 */               if (error) {
/* 120 */                   Log.e(this.mTag + "[Benchmark]", ft.getMessage());
                        } else {
/* 122 */                   Log.d(this.mTag + "[Benchmark]", ft.getMessage());
                        }
/* 124 */               if (error && sRandom.nextInt(100) < 10) {
/* 125 */                   CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 130 */       public void m485b(String format, Object[] arg) {
/* 131 */           m486b(format, arg, false);
                }

                /* JADX INFO: renamed from: b */
/* 134 */       public void m483b(String format, Object arg) {
/* 135 */           m484b(format, arg, false);
                }

                /* JADX INFO: renamed from: d */
/* 138 */       public void m487d(String msg) {
/* 139 */           if (SLog.sLogsOn) {
/* 140 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 144 */       public void m488d(String format, Object arg) {
/* 145 */           if (SLog.sLogsOn) {
/* 146 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 147 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 151 */       public void m495d(String format, Object[] args, Throwable throwable) {
/* 152 */           if (SLog.sLogsOn) {
/* 153 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 154 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 158 */       public void m492d(String format, Object arg1, Throwable throwable) {
/* 159 */           if (SLog.sLogsOn) {
/* 160 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 161 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 165 */       public void m491d(String format, Object arg1, Object arg2, Throwable arg3) {
/* 166 */           if (SLog.sLogsOn) {
/* 167 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 168 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), arg3);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 172 */       public void m490d(String format, Object arg1, Object arg2, Object arg3) {
/* 173 */           if (SLog.sLogsOn) {
/* 174 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
/* 175 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 179 */       public void m489d(String format, Object arg1, Object arg2) {
/* 180 */           if (SLog.sLogsOn) {
/* 181 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 182 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 186 */       public void m494d(String format, Object[] args) {
/* 187 */           if (SLog.sLogsOn) {
/* 188 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 189 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 193 */       public void m493d(String msg, Throwable throwable) {
/* 194 */           if (SLog.sLogsOn) {
/* 195 */               Log.d(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 199 */       public void m505i(String msg) {
/* 200 */           if (SLog.sLogsOn) {
/* 201 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 205 */       public void m506i(String format, Object arg) {
/* 206 */           if (SLog.sLogsOn) {
/* 207 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 208 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 212 */       public void m509i(String format, Object arg1, Throwable ex) {
/* 213 */           if (SLog.sLogsOn) {
/* 214 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 215 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), ex);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 219 */       public void m507i(String format, Object arg1, Object arg2) {
/* 220 */           if (SLog.sLogsOn) {
/* 221 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 222 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 226 */       public void m508i(String format, Object arg1, Object arg2, Object arg3) {
/* 227 */           if (SLog.sLogsOn) {
/* 228 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
/* 229 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 233 */       public void m511i(String format, Object[] args) {
/* 234 */           if (SLog.sLogsOn) {
/* 235 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 236 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 240 */       public void m510i(String msg, Throwable throwable) {
/* 241 */           if (SLog.sLogsOn) {
/* 242 */               Log.i(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 246 */       public void m518w(String msg) {
/* 247 */           if (SLog.sLogsOn) {
/* 248 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 252 */       public void m519w(String format, Object arg) {
/* 253 */           if (SLog.sLogsOn) {
/* 254 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 255 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 259 */       public void m522w(String format, Object[] args) {
/* 260 */           if (SLog.sLogsOn) {
/* 261 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 262 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 266 */       public void m520w(String format, Object arg1, Object arg2) {
/* 267 */           if (SLog.sLogsOn) {
/* 268 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 269 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 273 */       public void m523w(String format, Object[] arg1, Throwable throwable) {
/* 274 */           if (SLog.sLogsOn) {
/* 275 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 276 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 280 */       public void m521w(String msg, Throwable throwable) {
/* 281 */           if (SLog.sLogsOn) {
/* 282 */               Log.w(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 286 */       public void m496e(String msg) {
/* 287 */           if (SLog.sLogsOn) {
/* 288 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 292 */       public void m497e(String format, Object arg) {
/* 293 */           if (SLog.sLogsOn) {
/* 294 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 295 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 299 */       public void m498e(String format, Object arg1, Object arg2) {
/* 300 */           if (SLog.sLogsOn) {
/* 301 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 302 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 306 */       public void m502e(String format, Object[] arg, Throwable throwable) {
/* 307 */           if (SLog.sLogsOn) {
/* 308 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 309 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 313 */       public void m499e(String format, Object arg, Throwable throwable) {
/* 314 */           if (SLog.sLogsOn) {
/* 315 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 316 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 320 */       public void m501e(String format, Object[] args) {
/* 321 */           if (SLog.sLogsOn) {
/* 322 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 323 */               Log.e(this.mTag + getCallingClass(), ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 327 */       public void m500e(String msg, Throwable throwable) {
/* 328 */           if (SLog.sLogsOn) {
/* 329 */               Log.e(this.mTag + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: e2 */
/* 333 */       public void m503e2(String msg, Exception e) {
/* 334 */           if (SLog.sLogsOn) {
/* 335 */               Log.e(this.mTag + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                /* JADX INFO: renamed from: i2 */
/* 339 */       public void m512i2(String msg) {
/* 340 */           if (SLog.sLogsOn) {
/* 341 */               Log.i(this.mTag + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                /* JADX INFO: renamed from: e2 */
/* 345 */       public void m504e2(String msg, Throwable e) {
/* 346 */           if (SLog.sLogsOn) {
/* 347 */               Log.e(this.mTag + getCallingClass2(), getCallingLine2() + msg, e);
                    }
                }

/* 351 */       protected String getCallingClass() {
/* 352 */           return getShortClassName(Thread.currentThread().getStackTrace()[5].getClassName());
                }

/* 355 */       protected String getCallingClass2() {
/* 356 */           return getShortClassName(Thread.currentThread().getStackTrace()[6].getClassName());
                }

/* 359 */       protected String getCallingLine() {
/* 360 */           return getLine(Thread.currentThread().getStackTrace()[5]);
                }

/* 363 */       protected String getCallingLine2() {
/* 364 */           return getLine(Thread.currentThread().getStackTrace()[6]);
                }

/* 367 */       protected static String getShortClassName(String className) {
/* 368 */           int lastPeriod = className.lastIndexOf(46);
/* 369 */           if (lastPeriod >= className.length() - 1) {
/* 370 */               return "";
                    }
/* 371 */           if (lastPeriod >= 0) {
/* 372 */               return className.substring(lastPeriod + 1);
                    }
/* 374 */           return className;
                }

/* 377 */       protected static String getLine(StackTraceElement element) {
/* 378 */           return element.getMethodName() + "():" + element.getLineNumber() + " - ";
                }
            }
