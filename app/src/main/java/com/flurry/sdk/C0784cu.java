            package com.flurry.sdk;

            import com.flurry.android.FlurryAgent;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.flurry.sdk.cu */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class C0784cu implements C0803dn.a, Thread.UncaughtExceptionHandler {

                /* JADX INFO: renamed from: a */
                private static final String f531a = C0784cu.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                private static C0784cu f532b;

                /* JADX INFO: renamed from: c */
                private final HashMap<String, Map<String, String>> f533c = new HashMap<>();

                /* JADX INFO: renamed from: d */
                private boolean f534d;

                /* JADX INFO: renamed from: com.flurry.sdk.cu$a */
                public static class a {

                    /* JADX INFO: renamed from: a */
                    public int f535a;
                }

/* 41 */        private C0784cu() {
/* 43 */            C0815dz.m815a().m821a(this);
/* 45 */            m533d();
                }

                /* JADX INFO: renamed from: d */
/* 48 */        private void m533d() {
/* 49 */            C0803dn c0803dnM742a = C0802dm.m742a();
/* 51 */            this.f534d = ((Boolean) c0803dnM742a.m746a("CaptureUncaughtExceptions")).booleanValue();
/* 53 */            c0803dnM742a.m747a("CaptureUncaughtExceptions", (C0803dn.a) this);
/* 54 */            C0827el.m885a(4, f531a, "initSettings, CrashReportingEnabled = " + this.f534d);
/* 56 */            String str = (String) c0803dnM742a.m746a("VersionName");
/* 57 */            c0803dnM742a.m747a("VersionName", (C0803dn.a) this);
/* 58 */            C0814dy.m813a(str);
/* 59 */            C0827el.m885a(4, f531a, "initSettings, VersionName = " + str);
                }

                @Override // com.flurry.sdk.C0803dn.a
                /* JADX INFO: renamed from: a */
/* 63 */        public void mo535a(String str, Object obj) {
/* 64 */            if (str.equals("CaptureUncaughtExceptions")) {
/* 65 */                this.f534d = ((Boolean) obj).booleanValue();
/* 66 */                C0827el.m885a(4, f531a, "onSettingUpdate, CrashReportingEnabled = " + this.f534d);
                    } else {
/* 68 */                if (str.equals("VersionName")) {
/* 69 */                    String str2 = (String) obj;
/* 70 */                    C0814dy.m813a(str2);
/* 71 */                    C0827el.m885a(4, f531a, "onSettingUpdate, VersionName = " + str2);
/* 75 */                    return;
                        }
/* 73 */                C0827el.m885a(6, f531a, "onSettingUpdate internal error!");
                    }
                }

                /* JADX INFO: renamed from: a */
/* 77 */        public static synchronized C0784cu m532a() {
                    try {
                        if (f532b == null) {
/* 79 */                    f532b = new C0784cu();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 82 */            return f532b;
                }

                /* JADX INFO: renamed from: a */
/* 95 */        public void m538a(String str, String str2, Map<String, String> map) {
/* 96 */            if (map == null) {
/* 97 */                map = new HashMap<>();
                    }
/* 100 */           if (map.size() >= 10) {
/* 101 */               C0827el.m900d(f531a, "MaxOriginParams exceeded: " + map.size());
/* 116 */               return;
                    }
/* 106 */           map.put("flurryOriginVersion", str2);
                    synchronized (this.f533c) {
                        try {
/* 109 */                   if (this.f533c.size() >= 10 && !this.f533c.containsKey(str)) {
/* 110 */                       C0827el.m900d(f531a, "MaxOrigins exceeded: " + this.f533c.size());
                            } else {
/* 114 */                       this.f533c.put(str, map);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 118 */       public HashMap<String, Map<String, String>> m543b() {
                    HashMap<String, Map<String, String>> map;
                    synchronized (this.f533c) {
                        try {
/* 120 */                   map = new HashMap<>(this.f533c);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return map;
                }

                /* JADX INFO: renamed from: a */
/* 124 */       public void m534a(String str) {
/* 125 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 126 */           if (c0796dgM715c != null) {
/* 127 */               c0796dgM715c.m677a(str, null, false);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 131 */       public void m539a(String str, Map<String, String> map) {
/* 132 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 133 */           if (c0796dgM715c != null) {
/* 134 */               c0796dgM715c.m677a(str, map, false);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 138 */       public void m541a(String str, boolean z) {
/* 139 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 140 */           if (c0796dgM715c != null) {
/* 141 */               c0796dgM715c.m677a(str, null, z);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 145 */       public void m540a(String str, Map<String, String> map, boolean z) {
/* 146 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 147 */           if (c0796dgM715c != null) {
/* 148 */               c0796dgM715c.m677a(str, map, z);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 152 */       public void m544b(String str) {
/* 153 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 154 */           if (c0796dgM715c != null) {
/* 155 */               c0796dgM715c.m676a(str, (Map<String, String>) null);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 159 */       public void m545b(String str, Map<String, String> map) {
/* 160 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 161 */           if (c0796dgM715c != null) {
/* 162 */               c0796dgM715c.m676a(str, map);
                    }
                }

                @Deprecated
                /* JADX INFO: renamed from: a */
/* 167 */       public void m536a(String str, String str2, String str3) {
                    StackTraceElement[] stackTraceElementArr;
/* 168 */           StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/* 169 */           if (stackTrace == null || stackTrace.length <= 2) {
                        stackTraceElementArr = stackTrace;
                    } else {
/* 171 */               stackTraceElementArr = new StackTraceElement[stackTrace.length - 2];
/* 172 */               System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
                    }
/* 175 */           Throwable th = new Throwable(str2);
/* 176 */           th.setStackTrace(stackTraceElementArr);
/* 177 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 178 */           if (c0796dgM715c != null) {
/* 179 */               c0796dgM715c.m675a(str, str2, str3, th);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 183 */       public void m537a(String str, String str2, Throwable th) {
/* 184 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 185 */           if (c0796dgM715c != null) {
/* 186 */               c0796dgM715c.m675a(str, str2, th.getClass().getName(), th);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 190 */       public void m547c(String str) {
/* 191 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 192 */           if (c0796dgM715c != null) {
/* 193 */               c0796dgM715c.m677a(str, null, false);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 197 */       public void m548c(String str, Map<String, String> map) {
/* 198 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 199 */           if (c0796dgM715c != null) {
/* 200 */               c0796dgM715c.m677a(str, map, false);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 204 */       public void m546c() {
/* 205 */           C0796dg c0796dgM715c = C0798di.m709a().m715c();
/* 206 */           if (c0796dgM715c != null) {
/* 207 */               c0796dgM715c.m683e();
                    }
                }

                @Override // java.lang.Thread.UncaughtExceptionHandler
/* 212 */       public void uncaughtException(Thread thread, Throwable th) {
/* 213 */           th.printStackTrace();
/* 215 */           if (this.f534d) {
/* 216 */               String message = "";
/* 217 */               StackTraceElement[] stackTrace = th.getStackTrace();
/* 218 */               if (stackTrace != null && stackTrace.length > 0) {
/* 219 */                   StringBuilder sb = new StringBuilder();
/* 220 */                   if (th.getMessage() != null) {
/* 221 */                       sb.append(" (" + th.getMessage() + ")\n");
                            }
/* 226 */                   message = sb.toString();
                        } else if (th.getMessage() != null) {
/* 229 */                   message = th.getMessage();
                        }
/* 232 */               FlurryAgent.onError("uncaught", message, th);
                    }
/* 235 */           C0798di.m709a().m716d();
/* 236 */           C0812dw.m781a().m795g();
                }

                /* JADX INFO: renamed from: a */
/* 249 */       public void m542a(boolean z) {
/* 250 */           C0827el.m889a(z);
                }
            }
