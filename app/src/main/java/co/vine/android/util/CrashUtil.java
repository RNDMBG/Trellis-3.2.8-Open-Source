            package co.vine.android.util;

            import android.content.Context;
            import android.os.Environment;
            import android.util.Log;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.InputStreamReader;
            import java.io.PrintStream;
            import java.io.PrintWriter;
            import java.io.StringWriter;
            import java.io.Writer;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class CrashUtil {
                private static final HashMap<String, Object> sEnv = new HashMap<>();
                private static final int[] sLock = new int[0];
                private static boolean sLogsOn;

/* 31 */        public static void setup(boolean logsOn) {
/* 32 */            sLogsOn = logsOn;
                }

/* 35 */        public static void logOrThrowInDebug(Throwable e) {
/* 36 */            if (sLogsOn) {
/* 37 */                throw new RuntimeException(e);
                    }
/* 39 */            VineCrashReporter.logException(e);
/* 40 */            VineCrashReporter.setString("LastException", getStackTraceAsString(e));
                }

/* 44 */        public static void logException(Throwable e) {
/* 45 */            if (sLogsOn) {
/* 46 */                SLog.m463e2("Exception was logged.", e);
                    } else {
/* 48 */                VineCrashReporter.logException(e);
/* 49 */                VineCrashReporter.setString("LastException", getStackTraceAsString(e));
                    }
                }

/* 53 */        public static void log(String log) {
/* 54 */            if (sLogsOn) {
/* 55 */                SLog.m471i2(log);
                    } else {
/* 57 */                VineCrashReporter.log(log);
                    }
                }

/* 61 */        public static void log(String message, Object... objects) {
/* 62 */            String log = MessageFormatter.format(message, objects).getMessage();
/* 63 */            if (sLogsOn) {
/* 64 */                SLog.m471i2(log);
                    } else {
/* 66 */                VineCrashReporter.log(log);
                    }
                }

/* 70 */        public static void log2(int priority, String tag, String message, Object... objects) {
/* 71 */            String log = MessageFormatter.format(message, objects).getMessage();
/* 72 */            if (sLogsOn) {
/* 73 */                Log.println(priority, tag, log);
                    } else {
/* 75 */                VineCrashReporter.log(priority, tag, log);
                    }
                }

/* 79 */        public static void logException(Throwable e, String message, Object... objects) {
/* 80 */            String log = MessageFormatter.format(message, objects).getMessage();
/* 81 */            if (sLogsOn) {
                        synchronized (sLock) {
                            try {
/* 83 */                        SLog.m455e("logException triggered: ");
                                for (String key : sEnv.keySet()) {
/* 85 */                            SLog.m464i(key + ": " + sEnv.get(key));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 88 */                SLog.m463e2(log, e);
/* 93 */                return;
                    }
/* 90 */            VineCrashReporter.log(log);
/* 91 */            VineCrashReporter.logException(e);
                }

/* 95 */        public static String getStackTraceAsString(Throwable throwable) {
/* 96 */            StringWriter sw = new StringWriter();
/* 97 */            PrintWriter pw = new PrintWriter((Writer) sw, true);
/* 98 */            throwable.printStackTrace(pw);
/* 99 */            return sw.getBuffer().toString();
                }

/* 102 */       public static void set(String key, String value) {
/* 103 */           if (value == null) {
/* 104 */               value = "null";
                    }
/* 106 */           if (sLogsOn) {
/* 107 */               setEnv(key, value);
                    } else {
/* 109 */               VineCrashReporter.setString(key, value);
                    }
                }

/* 113 */       public static void set(String key, int value) {
/* 114 */           if (sLogsOn) {
/* 115 */               setEnv(key, Integer.valueOf(value));
                    } else {
/* 117 */               VineCrashReporter.setInt(key, value);
                    }
                }

/* 121 */       private static void setEnv(String key, Object value) {
                    synchronized (sLock) {
                        try {
/* 123 */                   Log.d("Vine", "" + key + " = " + value);
/* 124 */                   sEnv.put(key, value);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 128 */       public static void start(Context context) {
/* 129 */           if (sLogsOn) {
/* 130 */               Log.d("Vine", "Crashlytics will be started now if this is running with logs off.");
                    } else {
/* 132 */               VineCrashReporter.start(context);
                    }
                }

/* 137 */       public static void collectLogs(int lineCount, int ignoreCount) {
                    try {
/* 138 */               long start = System.currentTimeMillis();
/* 139 */               ArrayList<String> commandLine = new ArrayList<>();
/* 140 */               commandLine.add("logcat");
/* 141 */               commandLine.add("-d");
/* 142 */               commandLine.add("V");
/* 143 */               String[] last = new String[lineCount + ignoreCount];
/* 144 */               Process process = Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()]));
/* 145 */               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
/* 146 */               int n = 0;
/* 147 */               boolean hasMore = false;
                        while (true) {
                            try {
/* 150 */                       String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
/* 151 */                       last[n] = line;
/* 152 */                       n++;
/* 153 */                       if (n == last.length) {
/* 154 */                           hasMore = true;
/* 155 */                           n = 0;
                                }
                            } catch (Throwable th) {
                                bufferedReader.close();
                                throw th;
                            }
                        }
/* 159 */               bufferedReader.close();
/* 161 */               int total = hasMore ? lineCount : n;
/* 162 */               if (!hasMore) {
/* 163 */                   n = 0;
                        }
/* 165 */               PrintStream outputStream = null;
                        for (int i = 0; i < total; i++) {
/* 167 */                   if (SLog.sLogsOn) {
/* 168 */                       if (outputStream == null) {
/* 169 */                           outputStream = new PrintStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "vine_logs.txt")));
                                }
/* 171 */                       outputStream.print(last[n]);
/* 172 */                       outputStream.println();
                            } else {
/* 174 */                       log("[$]" + last[n]);
                            }
/* 176 */                   n++;
/* 177 */                   if (n == last.length) {
/* 178 */                       n = 0;
                            }
                        }
/* 181 */               if (outputStream != null) {
/* 182 */                   outputStream.flush();
/* 183 */                   outputStream.close();
                        }
/* 185 */               Thread.sleep(1000L);
/* 186 */               SLog.m456e("Collecting logs took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                    } catch (Throwable e) {
/* 188 */               log("Failed to print out the last few lines: " + e);
                    }
                }

/* 192 */       public static int getDefaultIgnoreCountForLogCollection(Throwable ex) {
                    StackTraceElement[] st;
/* 194 */           if (ex == null || (st = ex.getStackTrace()) == null) {
/* 193 */               return 4;
                    }
/* 197 */           int ignoreCount = st.length;
/* 200 */           return ignoreCount;
                }
            }
