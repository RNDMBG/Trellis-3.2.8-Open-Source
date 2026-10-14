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
            
            
    public class CrashUtil {
                private static final HashMap<String, Object> sEnv = new HashMap<>();
                private static final int[] sLock = new int[0];
                private static boolean sLogsOn;

        public static void setup(boolean logsOn) {
            sLogsOn = logsOn;
                }

        public static void logOrThrowInDebug(Throwable e) {
            if (sLogsOn) {
                throw new RuntimeException(e);
                    }
            VineCrashReporter.logException(e);
            VineCrashReporter.setString("LastException", getStackTraceAsString(e));
                }

        public static void logException(Throwable e) {
            if (sLogsOn) {
                SLog.m463e2("Exception was logged.", e);
                    } else {
                VineCrashReporter.logException(e);
                VineCrashReporter.setString("LastException", getStackTraceAsString(e));
                    }
                }

        public static void log(String log) {
            if (sLogsOn) {
                SLog.m471i2(log);
                    } else {
                VineCrashReporter.log(log);
                    }
                }

        public static void log(String message, Object... objects) {
            String log = MessageFormatter.format(message, objects).getMessage();
            if (sLogsOn) {
                SLog.m471i2(log);
                    } else {
                VineCrashReporter.log(log);
                    }
                }

        public static void log2(int priority, String tag, String message, Object... objects) {
            String log = MessageFormatter.format(message, objects).getMessage();
            if (sLogsOn) {
                Log.println(priority, tag, log);
                    } else {
                VineCrashReporter.log(priority, tag, log);
                    }
                }

        public static void logException(Throwable e, String message, Object... objects) {
            String log = MessageFormatter.format(message, objects).getMessage();
            if (sLogsOn) {
                        synchronized (sLock) {
                            try {
                        SLog.m455e("logException triggered: ");
                                for (String key : sEnv.keySet()) {
                            SLog.m464i(key + ": " + sEnv.get(key));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                SLog.m463e2(log, e);
                return;
                    }
            VineCrashReporter.log(log);
            VineCrashReporter.logException(e);
                }

        public static String getStackTraceAsString(Throwable throwable) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter((Writer) sw, true);
            throwable.printStackTrace(pw);
            return sw.getBuffer().toString();
                }

       public static void set(String key, String value) {
           if (value == null) {
               value = "null";
                    }
           if (sLogsOn) {
               setEnv(key, value);
                    } else {
               VineCrashReporter.setString(key, value);
                    }
                }

       public static void set(String key, int value) {
           if (sLogsOn) {
               setEnv(key, Integer.valueOf(value));
                    } else {
               VineCrashReporter.setInt(key, value);
                    }
                }

       private static void setEnv(String key, Object value) {
                    synchronized (sLock) {
                        try {
                   Log.d("Vine", "" + key + " = " + value);
                   sEnv.put(key, value);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static void start(Context context) {
           if (sLogsOn) {
               Log.d("Vine", "Crashlytics will be started now if this is running with logs off.");
                    } else {
               VineCrashReporter.start(context);
                    }
                }

       public static void collectLogs(int lineCount, int ignoreCount) {
                    try {
               long start = System.currentTimeMillis();
               ArrayList<String> commandLine = new ArrayList<>();
               commandLine.add("logcat");
               commandLine.add("-d");
               commandLine.add("V");
               String[] last = new String[lineCount + ignoreCount];
               Process process = Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[commandLine.size()]));
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
               int n = 0;
               boolean hasMore = false;
                        while (true) {
                            try {
                       String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                       last[n] = line;
                       n++;
                       if (n == last.length) {
                           hasMore = true;
                           n = 0;
                                }
                            } catch (Throwable th) {
                                bufferedReader.close();
                                throw th;
                            }
                        }
               bufferedReader.close();
               int total = hasMore ? lineCount : n;
               if (!hasMore) {
                   n = 0;
                        }
               PrintStream outputStream = null;
                        for (int i = 0; i < total; i++) {
                   if (SLog.sLogsOn) {
                       if (outputStream == null) {
                           outputStream = new PrintStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "vine_logs.txt")));
                                }
                       outputStream.print(last[n]);
                       outputStream.println();
                            } else {
                       log("[$]" + last[n]);
                            }
                   n++;
                   if (n == last.length) {
                       n = 0;
                            }
                        }
               if (outputStream != null) {
                   outputStream.flush();
                   outputStream.close();
                        }
               Thread.sleep(1000L);
               SLog.m456e("Collecting logs took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                    } catch (Throwable e) {
               log("Failed to print out the last few lines: " + e);
                    }
                }

       public static int getDefaultIgnoreCountForLogCollection(Throwable ex) {
                    StackTraceElement[] st;
           if (ex == null || (st = ex.getStackTrace()) == null) {
               return 4;
                    }
           int ignoreCount = st.length;
           return ignoreCount;
                }
            }
