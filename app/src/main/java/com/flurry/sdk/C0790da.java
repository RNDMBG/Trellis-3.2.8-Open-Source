            package com.flurry.sdk;

            import android.widget.Toast;
            
            
            
    public class C0790da extends AbstractC0791db implements C0803dn.a {

                
                static String f557a;

                
                private boolean f561i;

                
                private static final String f560h = C0790da.class.getSimpleName();

                
                static String f558b = "http://data.flurry.com/aap.do";

                
                static String f559c = "https://data.flurry.com/aap.do";

                
        static  String m569b() {
            return f560h;
                }

        public C0790da() {
            this(null);
                }

        C0790da(AbstractC0791db.a aVar) {
            super("Analytics", C0790da.class.getSimpleName());
            this.f572g = "AnalyticsData_";
            m571g();
            m578a(aVar);
                }

                
        private void m571g() {
            C0803dn c0803dnM742a = C0802dm.m742a();
            this.f561i = ((Boolean) c0803dnM742a.m746a("UseHttps")).booleanValue();
            c0803dnM742a.m747a("UseHttps", (C0803dn.a) this);
            C0827el.m885a(4, f560h, "initSettings, UseHttps = " + this.f561i);
            String str = (String) c0803dnM742a.m746a("ReportUrl");
            c0803dnM742a.m747a("ReportUrl", (C0803dn.a) this);
            m570b(str);
            C0827el.m885a(4, f560h, "initSettings, ReportUrl = " + str);
                }

                @Override 
                
        public void mo535a(String str, Object obj) {
            if (str.equals("UseHttps")) {
                this.f561i = ((Boolean) obj).booleanValue();
                C0827el.m885a(4, f560h, "onSettingUpdate, UseHttps = " + this.f561i);
                    } else {
                if (str.equals("ReportUrl")) {
                    String str2 = (String) obj;
                    m570b(str2);
                    C0827el.m885a(4, f560h, "onSettingUpdate, ReportUrl = " + str2);
                    return;
                        }
                C0827el.m885a(6, f560h, "onSettingUpdate internal error!");
                    }
                }

                
        private void m570b(String str) {
            if (str != null && !str.endsWith(".do")) {
                C0827el.m885a(5, f560h, "overriding analytics agent report URL without an endpoint, are you sure?");
                    }
            f557a = str;
                }

                
       String m572a() {
           if (f557a != null) {
               return f557a;
                    }
           if (this.f561i) {
               return f559c;
                    }
           return f558b;
                }

                @Override 
                
       protected void mo574a(byte[] bArr, String str, String str2) {
           String strM572a = m572a();
           C0827el.m885a(4, f560h, "FlurryDataSender: start upload data " + bArr + " with id = " + str + " to " + strM572a);
           C0824ei c0824ei = new C0824ei();
           c0824ei.m871a(strM572a);
           c0824ei.m869a(C0826ek.a.kPost);
           c0824ei.m872a("Content-Type", "application/octet-stream");
           c0824ei.m850a((InterfaceC0836eu) new C0835et());
           c0824ei.m851a(bArr);
           c0824ei.m849a((C0824ei.a) new AnonymousClass1(str, str2));
           C0825ej.m856a().m836a(this, c0824ei);
                }

                
       class AnonymousClass1 implements C0824ei.a<byte[], Void> {

                    
                    final  String f562a;

                    
                    final  String f563b;

           AnonymousClass1(String str, String str2) {
                        this.f562a = str;
                        this.f563b = str2;
                    }

                    @Override 
                    

                    
           public void mo575a(C0824ei<byte[], Void> c0824ei, Void r6) {
               int iE = c0824ei.m878e();
               if (iE > 0) {
                   C0827el.m900d(C0790da.m569b(), "FlurryDataSender: report " + this.f562a + " sent. HTTP response: " + iE);
                   if (C0827el.m895c() <= 3 && C0827el.m901d()) {
                       C0801dl.m732a().m734a(new RunnableC13131(iE));
                            }
                   C0790da.this.mo573a(this.f562a, this.f563b, iE);
                   C0790da.this.m589d();
                   return;
                        }
               C0790da.this.m583b(this.f562a, this.f563b);
                    }

                    
           class RunnableC13131 implements Runnable {

                        
                        final  int f565a;

               RunnableC13131(int i) {
                            this.f565a = i;
                        }

                        @Override 
               public void run() {
                   Toast.makeText(C0801dl.m732a().m736b(), "SD HTTP Response Code: " + this.f565a, 0).show();
                        }
                    }
                }

                
       class AnonymousClass2 extends AbstractRunnableC0844fc {

                    
                    final  int f567a;

           AnonymousClass2(int i) {
                        this.f567a = i;
                    }

                    @Override 
                    
           public void mo576a() {
                        C0796dg c0796dgM715c;
               if (this.f567a == 200 && (c0796dgM715c = C0798di.m709a().m715c()) != null) {
                   c0796dgM715c.m678b();
                        }
                    }
                }

                @Override 
                
       protected void mo573a(String str, String str2, int i) {
           m579a(new AnonymousClass2(i));
           super.mo573a(str, str2, i);
                }
            }
