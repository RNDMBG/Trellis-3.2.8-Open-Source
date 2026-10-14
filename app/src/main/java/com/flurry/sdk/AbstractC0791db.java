            package com.flurry.sdk;

            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            
            
    public abstract class AbstractC0791db implements InterfaceC0833er {

                
                protected final String f569d;

                
                C0793dd f571f;

                
                Set<String> f570e = new HashSet();

                
                protected String f572g = "defaultDataKey_";

                
                public interface a {
                    
                    void m592a();
                }

                
                protected abstract void mo574a(byte[] bArr, String str, String str2);

        public AbstractC0791db(String str, String str2) {
            this.f569d = str2;
            C0834es.m907a().m910a(this);
            m580a(str);
                }

                
        protected void m579a(AbstractRunnableC0844fc abstractRunnableC0844fc) {
            C0801dl.m732a().m740c(abstractRunnableC0844fc);
                }

                
        class AnonymousClass1 extends AbstractRunnableC0844fc {

                    
                    final  String f573a;

            AnonymousClass1(String str) {
                        this.f573a = str;
                    }

                    @Override 
                    
            public void mo576a() {
                AbstractC0791db.this.f571f = new C0793dd(this.f573a);
                    }
                }

                
        protected void m580a(String str) {
            m579a(new AnonymousClass1(str));
                }

                @Override 
                
        public void mo581a(boolean z) {
            C0827el.m885a(4, this.f569d, "onNetworkStateChanged : isNetworkEnable = " + z);
            if (z) {
                m589d();
                    }
                }

                
        public void m584b(byte[] bArr, String str, String str2) {
            m582a(bArr, str, str2, null);
                }

                
        protected void m582a(byte[] bArr, String str, String str2, a aVar) {
            if (bArr == null || bArr.length == 0) {
                C0827el.m885a(6, this.f569d, "Report that has to be sent is EMPTY or NULL");
                    } else {
                m587c(bArr, str, str2);
                m578a(aVar);
                    }
                }

                
        protected int m585c() {
            return this.f570e.size();
                }

                
        class AnonymousClass2 extends AbstractRunnableC0844fc {

                    
                    final  byte[] f575a;

                    
                    final  String f576b;

                    
                    final  String f577c;

           AnonymousClass2(byte[] bArr, String str, String str2) {
                        this.f575a = bArr;
                        this.f576b = str;
                        this.f577c = str2;
                    }

                    @Override 
                    
           public void mo576a() {
               AbstractC0791db.this.m588d(this.f575a, this.f576b, this.f577c);
                    }
                }

                
       protected void m587c(byte[] bArr, String str, String str2) {
           m579a(new AnonymousClass2(bArr, str, str2));
                }

                
       protected void m589d() {
           m578a((a) null);
                }

                
       class AnonymousClass3 extends AbstractRunnableC0844fc {

                    
                    final  a f579a;

           AnonymousClass3(a aVar) {
                        this.f579a = aVar;
                    }

                    @Override 
                    
           public void mo576a() {
               AbstractC0791db.this.m591f();
               if (this.f579a != null) {
                   this.f579a.m592a();
                        }
                    }
                }

                
       protected void m578a(a aVar) {
           m579a(new AnonymousClass3(aVar));
                }

                
       protected boolean m590e() {
                    return m585c() <= 5;
                }

                
       protected String m577a(String str, String str2) {
           return this.f572g + str + "_" + str2;
                }

                
       protected String m588d(byte[] bArr, String str, String str2) throws Throwable {
           String strM577a = m577a(str, str2);
           C0792dc c0792dc = new C0792dc();
           c0792dc.m594a(bArr);
           String strM593a = c0792dc.m593a();
           this.f571f.m602a(c0792dc, strM577a);
           return strM593a;
                }

                
       protected void m591f() throws Throwable {
           if (!C0834es.m907a().m912c()) {
               C0827el.m885a(5, this.f569d, "Reports were not sent! No Internet connection!");
               return;
                    }
           List<String> listM601a = this.f571f.m601a();
           if (listM601a == null || listM601a.isEmpty()) {
               C0827el.m885a(4, this.f569d, "No more reports to send.");
               return;
                    }
                    for (String str : listM601a) {
               if (m590e()) {
                   List<String> listM607c = this.f571f.m607c(str);
                   C0827el.m885a(4, this.f569d, "Number of not sent blocks = " + listM607c.size());
                   int i = 0;
                            while (true) {
                                int i2 = i;
                                if (i2 >= listM607c.size()) {
                                    break;
                                }
                       String str2 = listM607c.get(i2);
                       if (!this.f570e.contains(str2)) {
                           if (!m590e()) {
                                        break;
                                    }
                           byte[] bArrM595b = new C0792dc(str2).m595b();
                           if (bArrM595b == null || bArrM595b.length == 0) {
                               C0827el.m885a(6, this.f569d, "Internal ERROR! Report is empty!");
                               this.f571f.m604a(str2, str);
                                    } else {
                               this.f570e.add(str2);
                               mo574a(bArrM595b, str2, str);
                                    }
                                }
                       i = i2 + 1;
                            }
                        } else {
                   return;
                        }
                    }
                }

                
       class AnonymousClass4 extends AbstractRunnableC0844fc {

                    
                    final  String f581a;

                    
                    final  String f582b;

           AnonymousClass4(String str, String str2) {
                        this.f581a = str;
                        this.f582b = str2;
                    }

                    @Override 
                    
           public void mo576a() {
               if (!AbstractC0791db.this.f571f.m604a(this.f581a, this.f582b)) {
                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block wasn't deleted with id = " + this.f581a);
                        }
               if (!AbstractC0791db.this.f570e.remove(this.f581a)) {
                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block with id = " + this.f581a + " was not in progress state");
                        }
                    }
                }

                
       protected void mo573a(String str, String str2, int i) {
           m579a(new AnonymousClass4(str, str2));
                }

                
       class AnonymousClass5 extends AbstractRunnableC0844fc {

                    
                    final  String f584a;

           AnonymousClass5(String str) {
                        this.f584a = str;
                    }

                    @Override 
                    
           public void mo576a() {
               if (!AbstractC0791db.this.f570e.remove(this.f584a)) {
                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block with id = " + this.f584a + " was not in progress state");
                        }
                    }
                }

                
       protected void m583b(String str, String str2) {
           m579a(new AnonymousClass5(str));
                }

                
       protected void m586c(String str, String str2) {
           if (!this.f571f.m604a(str, str2)) {
               C0827el.m885a(6, this.f569d, "Internal error. Block wasn't deleted with id = " + str);
                    }
           if (!this.f570e.remove(str)) {
               C0827el.m885a(6, this.f569d, "Internal error. Block with id = " + str + " was not in progress state");
                    }
                }
            }
