            package com.flurry.sdk;

            import com.flurry.android.FlurryAgent;
            import java.util.HashMap;
            import java.util.Map;
            
            
            
    public class C0784cu implements C0803dn.a, Thread.UncaughtExceptionHandler {

                
                private static final String f531a = C0784cu.class.getSimpleName();

                
                private static C0784cu f532b;

                
                private final HashMap<String, Map<String, String>> f533c = new HashMap<>();

                
                private boolean f534d;

                
                public static class a {

                    
                    public int f535a;
                }

        private C0784cu() {
            C0815dz.m815a().m821a(this);
            m533d();
                }

                
        private void m533d() {
            C0803dn c0803dnM742a = C0802dm.m742a();
            this.f534d = ((Boolean) c0803dnM742a.m746a("CaptureUncaughtExceptions")).booleanValue();
            c0803dnM742a.m747a("CaptureUncaughtExceptions", (C0803dn.a) this);
            C0827el.m885a(4, f531a, "initSettings, CrashReportingEnabled = " + this.f534d);
            String str = (String) c0803dnM742a.m746a("VersionName");
            c0803dnM742a.m747a("VersionName", (C0803dn.a) this);
            C0814dy.m813a(str);
            C0827el.m885a(4, f531a, "initSettings, VersionName = " + str);
                }

                @Override 
                
        public void mo535a(String str, Object obj) {
            if (str.equals("CaptureUncaughtExceptions")) {
                this.f534d = ((Boolean) obj).booleanValue();
                C0827el.m885a(4, f531a, "onSettingUpdate, CrashReportingEnabled = " + this.f534d);
                    } else {
                if (str.equals("VersionName")) {
                    String str2 = (String) obj;
                    C0814dy.m813a(str2);
                    C0827el.m885a(4, f531a, "onSettingUpdate, VersionName = " + str2);
                    return;
                        }
                C0827el.m885a(6, f531a, "onSettingUpdate internal error!");
                    }
                }

                
        public static synchronized C0784cu m532a() {
                    try {
                        if (f532b == null) {
                    f532b = new C0784cu();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return f532b;
                }

                
        public void m538a(String str, String str2, Map<String, String> map) {
            if (map == null) {
                map = new HashMap<>();
                    }
           if (map.size() >= 10) {
               C0827el.m900d(f531a, "MaxOriginParams exceeded: " + map.size());
               return;
                    }
           map.put("flurryOriginVersion", str2);
                    synchronized (this.f533c) {
                        try {
                   if (this.f533c.size() >= 10 && !this.f533c.containsKey(str)) {
                       C0827el.m900d(f531a, "MaxOrigins exceeded: " + this.f533c.size());
                            } else {
                       this.f533c.put(str, map);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                
       public HashMap<String, Map<String, String>> m543b() {
                    HashMap<String, Map<String, String>> map;
                    synchronized (this.f533c) {
                        try {
                   map = new HashMap<>(this.f533c);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return map;
                }

                
       public void m534a(String str) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, null, false);
                    }
                }

                
       public void m539a(String str, Map<String, String> map) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, map, false);
                    }
                }

                
       public void m541a(String str, boolean z) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, null, z);
                    }
                }

                
       public void m540a(String str, Map<String, String> map, boolean z) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, map, z);
                    }
                }

                
       public void m544b(String str) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m676a(str, (Map<String, String>) null);
                    }
                }

                
       public void m545b(String str, Map<String, String> map) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m676a(str, map);
                    }
                }

                @Deprecated
                
       public void m536a(String str, String str2, String str3) {
                    StackTraceElement[] stackTraceElementArr;
           StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
           if (stackTrace == null || stackTrace.length <= 2) {
                        stackTraceElementArr = stackTrace;
                    } else {
               stackTraceElementArr = new StackTraceElement[stackTrace.length - 2];
               System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
                    }
           Throwable th = new Throwable(str2);
           th.setStackTrace(stackTraceElementArr);
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m675a(str, str2, str3, th);
                    }
                }

                
       public void m537a(String str, String str2, Throwable th) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m675a(str, str2, th.getClass().getName(), th);
                    }
                }

                
       public void m547c(String str) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, null, false);
                    }
                }

                
       public void m548c(String str, Map<String, String> map) {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m677a(str, map, false);
                    }
                }

                
       public void m546c() {
           C0796dg c0796dgM715c = C0798di.m709a().m715c();
           if (c0796dgM715c != null) {
               c0796dgM715c.m683e();
                    }
                }

                @Override 
       public void uncaughtException(Thread thread, Throwable th) {
           th.printStackTrace();
           if (this.f534d) {
               String message = "";
               StackTraceElement[] stackTrace = th.getStackTrace();
               if (stackTrace != null && stackTrace.length > 0) {
                   StringBuilder sb = new StringBuilder();
                   if (th.getMessage() != null) {
                       sb.append(" (" + th.getMessage() + ")\n");
                            }
                   message = sb.toString();
                        } else if (th.getMessage() != null) {
                   message = th.getMessage();
                        }
               FlurryAgent.onError("uncaught", message, th);
                    }
           C0798di.m709a().m716d();
           C0812dw.m781a().m795g();
                }

                
       public void m542a(boolean z) {
           C0827el.m889a(z);
                }
            }
