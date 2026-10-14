            package com.edisonwang.android.slog;

            import android.util.Log;
            import co.vine.android.util.CrashUtil;
            import java.util.Random;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class SLog {
                private static final String TAG = "Playback_";
                private static String sAuthority;
                public static boolean sIsAmazon;
                public static boolean sLogsOn;
                private static Random sRandom = new Random();

/* 21 */        public static void setup(boolean logsOn, boolean isAmazon, String authority) {
/* 22 */            sLogsOn = logsOn;
/* 23 */            sIsAmazon = isAmazon;
/* 24 */            sAuthority = authority;
/* 25 */            sRandom = new Random();
                }

/* 28 */        public static String getAuthority() {
/* 29 */            return sAuthority;
                }

                /* JADX INFO: renamed from: v */
/* 32 */        public static void m472v(String msg) {
/* 33 */            if (sLogsOn) {
/* 34 */                Log.v(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: v */
/* 38 */        public static void m473v(String format, Object arg) {
/* 39 */            if (sLogsOn) {
/* 40 */                FormattingTuple ft = MessageFormatter.format(format, arg);
/* 41 */                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 45 */        public static void m474v(String format, Object arg1, Object arg2) {
/* 46 */            if (sLogsOn) {
/* 47 */                FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 48 */                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 52 */        public static void m476v(String format, Object[] args) {
/* 53 */            if (sLogsOn) {
/* 54 */                FormattingTuple ft = MessageFormatter.format(format, args);
/* 55 */                Log.v(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: v */
/* 59 */        public static void m475v(String msg, Throwable throwable) {
/* 60 */            if (sLogsOn) {
/* 61 */                Log.v(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

/* 65 */        public static void dWithTag(String tag, String msg) {
/* 66 */            if (sLogsOn) {
/* 67 */                Log.d(tag, getCallingClass() + ":" + getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 71 */        public static void m445b(String format, Object[] arg, boolean error) {
/* 72 */            if (sLogsOn) {
/* 73 */                FormattingTuple ft = MessageFormatter.format(format, arg);
/* 74 */                if (error) {
/* 75 */                    Log.e("Playback_[Benchmark]", ft.getMessage());
                        } else {
/* 77 */                    Log.d("Playback_[Benchmark]", ft.getMessage());
                        }
/* 79 */                if (error && sRandom.nextInt(100) < 10) {
/* 80 */                    CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 85 */        public static void m443b(String format, Object arg, boolean error) {
/* 86 */            if (sLogsOn) {
/* 87 */                FormattingTuple ft = MessageFormatter.format(format, arg);
/* 88 */                if (error) {
/* 89 */                    Log.e("Playback_[Benchmark]", ft.getMessage());
                        } else {
/* 91 */                    Log.d("Playback_[Benchmark]", ft.getMessage());
                        }
/* 93 */                if (error && sRandom.nextInt(100) < 10) {
/* 94 */                    CrashUtil.logException(new IllegalMonitorStateException(ft.getMessage()));
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 99 */        public static void m444b(String format, Object[] arg) {
/* 100 */           m445b(format, arg, false);
                }

                /* JADX INFO: renamed from: b */
/* 103 */       public static void m442b(String format, Object arg) {
/* 104 */           m443b(format, arg, false);
                }

                /* JADX INFO: renamed from: d */
/* 107 */       public static void m446d(String msg) {
/* 108 */           if (sLogsOn) {
/* 109 */               Log.d(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 113 */       public static void m447d(String format, Object arg) {
/* 114 */           if (sLogsOn) {
/* 115 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 116 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 120 */       public static void m454d(String format, Object[] args, Throwable throwable) {
/* 121 */           if (sLogsOn) {
/* 122 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 123 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 127 */       public static void m451d(String format, Object arg1, Throwable throwable) {
/* 128 */           if (sLogsOn) {
/* 129 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 130 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 134 */       public static void m450d(String format, Object arg1, Object arg2, Throwable arg3) {
/* 135 */           if (sLogsOn) {
/* 136 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 137 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), arg3);
                    }
                }

                /* JADX INFO: renamed from: d */
/* 141 */       public static void m449d(String format, Object arg1, Object arg2, Object arg3) {
/* 142 */           if (sLogsOn) {
/* 143 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
/* 144 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 148 */       public static void m448d(String format, Object arg1, Object arg2) {
/* 149 */           if (sLogsOn) {
/* 150 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 151 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 155 */       public static void m453d(String format, Object[] args) {
/* 156 */           if (sLogsOn) {
/* 157 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 158 */               Log.d(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: d */
/* 162 */       public static void m452d(String msg, Throwable throwable) {
/* 163 */           if (sLogsOn) {
/* 164 */               Log.d(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 168 */       public static void m464i(String msg) {
/* 169 */           if (sLogsOn) {
/* 170 */               Log.i(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 174 */       public static void m465i(String format, Object arg) {
/* 175 */           if (sLogsOn) {
/* 176 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 177 */               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 181 */       public static void m468i(String format, Object arg1, Throwable ex) {
/* 182 */           if (sLogsOn) {
/* 183 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 184 */               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), ex);
                    }
                }

                /* JADX INFO: renamed from: i */
/* 188 */       public static void m466i(String format, Object arg1, Object arg2) {
/* 189 */           if (sLogsOn) {
/* 190 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 191 */               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 195 */       public static void m467i(String format, Object arg1, Object arg2, Object arg3) {
/* 196 */           if (sLogsOn) {
/* 197 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2, arg3);
/* 198 */               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 202 */       public static void m470i(String format, Object[] args) {
/* 203 */           if (sLogsOn) {
/* 204 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 205 */               Log.i(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: i */
/* 209 */       public static void m469i(String msg, Throwable throwable) {
/* 210 */           if (sLogsOn) {
/* 211 */               Log.i(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 215 */       public static void m477w(String msg) {
/* 216 */           if (sLogsOn) {
/* 217 */               Log.w(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 221 */       public static void m478w(String format, Object arg) {
/* 222 */           if (sLogsOn) {
/* 223 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 224 */               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 228 */       public static void m481w(String format, Object[] args) {
/* 229 */           if (sLogsOn) {
/* 230 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 231 */               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 235 */       public static void m479w(String format, Object arg1, Object arg2) {
/* 236 */           if (sLogsOn) {
/* 237 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 238 */               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: w */
/* 242 */       public static void m482w(String format, Object[] arg1, Throwable throwable) {
/* 243 */           if (sLogsOn) {
/* 244 */               FormattingTuple ft = MessageFormatter.format(format, arg1);
/* 245 */               Log.w(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: w */
/* 249 */       public static void m480w(String msg, Throwable throwable) {
/* 250 */           if (sLogsOn) {
/* 251 */               Log.w(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 255 */       public static void m455e(String msg) {
/* 256 */           if (sLogsOn) {
/* 257 */               Log.e(TAG + getCallingClass(), getCallingLine() + msg);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 261 */       public static void m456e(String format, Object arg) {
/* 262 */           if (sLogsOn) {
/* 263 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 264 */               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 268 */       public static void m457e(String format, Object arg1, Object arg2) {
/* 269 */           if (sLogsOn) {
/* 270 */               FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
/* 271 */               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 275 */       public static void m461e(String format, Object[] arg, Throwable throwable) {
/* 276 */           if (sLogsOn) {
/* 277 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 278 */               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 282 */       public static void m458e(String format, Object arg, Throwable throwable) {
/* 283 */           if (sLogsOn) {
/* 284 */               FormattingTuple ft = MessageFormatter.format(format, arg);
/* 285 */               Log.e(TAG + getCallingClass(), getCallingLine() + ft.getMessage(), throwable);
                    }
                }

                /* JADX INFO: renamed from: e */
/* 289 */       public static void m460e(String format, Object[] args) {
/* 290 */           if (sLogsOn) {
/* 291 */               FormattingTuple ft = MessageFormatter.format(format, args);
/* 292 */               Log.e(TAG + getCallingClass(), ft.getMessage());
                    }
                }

                /* JADX INFO: renamed from: e */
/* 296 */       public static void m459e(String msg, Throwable throwable) {
/* 297 */           if (sLogsOn) {
/* 298 */               Log.e(TAG + getCallingClass(), getCallingLine() + msg, throwable);
                    }
                }

/* 302 */       private static String getCallingClass() {
/* 303 */           String className = Thread.currentThread().getStackTrace()[4].getClassName();
/* 304 */           int lastPeriod = className.lastIndexOf(46);
/* 305 */           if (lastPeriod >= className.length() - 1) {
/* 306 */               return "";
                    }
/* 307 */           if (lastPeriod >= 0) {
/* 308 */               return className.substring(lastPeriod + 1);
                    }
/* 310 */           return className;
                }

/* 313 */       private static String getCallingLine() {
/* 314 */           return Thread.currentThread().getStackTrace()[4].getMethodName() + "():" + Thread.currentThread().getStackTrace()[4].getLineNumber() + " - ";
                }

/* 318 */       private static String getCallingLine2() {
/* 319 */           return Thread.currentThread().getStackTrace()[5].getMethodName() + "():" + Thread.currentThread().getStackTrace()[5].getLineNumber() + " - ";
                }

/* 323 */       private static String getCallingClass2() {
/* 324 */           String className = Thread.currentThread().getStackTrace()[5].getClassName();
/* 325 */           int lastPeriod = className.lastIndexOf(46);
/* 326 */           if (lastPeriod >= className.length() - 1) {
/* 327 */               return "";
                    }
/* 328 */           if (lastPeriod >= 0) {
/* 329 */               return className.substring(lastPeriod + 1);
                    }
/* 331 */           return className;
                }

                /* JADX INFO: renamed from: e2 */
/* 334 */       public static void m462e2(String msg, Exception e) {
/* 335 */           if (sLogsOn) {
/* 336 */               Log.e(TAG + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                /* JADX INFO: renamed from: i2 */
/* 340 */       public static void m471i2(String msg) {
/* 341 */           if (sLogsOn) {
/* 342 */               Log.i(TAG + getCallingClass2(), getCallingLine2() + msg);
                    }
                }

                /* JADX INFO: renamed from: e2 */
/* 346 */       public static void m463e2(String msg, Throwable e) {
/* 347 */           if (sLogsOn) {
/* 348 */               Log.e(TAG + getCallingClass2(), getCallingLine2() + msg, e);
                    }
                }
            }
