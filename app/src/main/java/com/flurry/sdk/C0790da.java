            package com.flurry.sdk;

            import android.widget.Toast;
            
            /* JADX INFO: renamed from: com.flurry.sdk.da */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class C0790da extends AbstractC0791db implements C0803dn.a {

                /* JADX INFO: renamed from: a */
                static String f557a;

                /* JADX INFO: renamed from: i */
                private boolean f561i;

                /* JADX INFO: renamed from: h */
                private static final String f560h = C0790da.class.getSimpleName();

                /* JADX INFO: renamed from: b */
                static String f558b = "http://data.flurry.com/aap.do";

                /* JADX INFO: renamed from: c */
                static String f559c = "https://data.flurry.com/aap.do";

                /* JADX INFO: renamed from: b */
/* 25 */        static /* synthetic */ String m569b() {
/* 26 */            return f560h;
                }

/* 43 */        public C0790da() {
/* 44 */            this(null);
                }

/* 47 */        C0790da(AbstractC0791db.a aVar) {
/* 48 */            super("Analytics", C0790da.class.getSimpleName());
/* 49 */            this.f572g = "AnalyticsData_";
/* 50 */            m571g();
/* 51 */            m578a(aVar);
                }

                /* JADX INFO: renamed from: g */
/* 54 */        private void m571g() {
/* 55 */            C0803dn c0803dnM742a = C0802dm.m742a();
/* 57 */            this.f561i = ((Boolean) c0803dnM742a.m746a("UseHttps")).booleanValue();
/* 58 */            c0803dnM742a.m747a("UseHttps", (C0803dn.a) this);
/* 59 */            C0827el.m885a(4, f560h, "initSettings, UseHttps = " + this.f561i);
/* 61 */            String str = (String) c0803dnM742a.m746a("ReportUrl");
/* 62 */            c0803dnM742a.m747a("ReportUrl", (C0803dn.a) this);
/* 63 */            m570b(str);
/* 64 */            C0827el.m885a(4, f560h, "initSettings, ReportUrl = " + str);
                }

                @Override // com.flurry.sdk.C0803dn.a
                /* JADX INFO: renamed from: a */
/* 73 */        public void mo535a(String str, Object obj) {
/* 74 */            if (str.equals("UseHttps")) {
/* 75 */                this.f561i = ((Boolean) obj).booleanValue();
/* 76 */                C0827el.m885a(4, f560h, "onSettingUpdate, UseHttps = " + this.f561i);
                    } else {
/* 77 */                if (str.equals("ReportUrl")) {
/* 78 */                    String str2 = (String) obj;
/* 79 */                    m570b(str2);
/* 80 */                    C0827el.m885a(4, f560h, "onSettingUpdate, ReportUrl = " + str2);
/* 84 */                    return;
                        }
/* 82 */                C0827el.m885a(6, f560h, "onSettingUpdate internal error!");
                    }
                }

                /* JADX INFO: renamed from: b */
/* 91 */        private void m570b(String str) {
/* 92 */            if (str != null && !str.endsWith(".do")) {
/* 93 */                C0827el.m885a(5, f560h, "overriding analytics agent report URL without an endpoint, are you sure?");
                    }
/* 96 */            f557a = str;
                }

                /* JADX INFO: renamed from: a */
/* 100 */       String m572a() {
/* 101 */           if (f557a != null) {
/* 102 */               return f557a;
                    }
/* 105 */           if (this.f561i) {
/* 106 */               return f559c;
                    }
/* 108 */           return f558b;
                }

                @Override // com.flurry.sdk.AbstractC0791db
                /* JADX INFO: renamed from: a */
/* 113 */       protected void mo574a(byte[] bArr, String str, String str2) {
/* 114 */           String strM572a = m572a();
/* 116 */           C0827el.m885a(4, f560h, "FlurryDataSender: start upload data " + bArr + " with id = " + str + " to " + strM572a);
/* 119 */           C0824ei c0824ei = new C0824ei();
/* 120 */           c0824ei.m871a(strM572a);
/* 121 */           c0824ei.m869a(C0826ek.a.kPost);
/* 122 */           c0824ei.m872a("Content-Type", "application/octet-stream");
/* 123 */           c0824ei.m850a((InterfaceC0836eu) new C0835et());
/* 124 */           c0824ei.m851a(bArr);
/* 125 */           c0824ei.m849a((C0824ei.a) new AnonymousClass1(str, str2));
/* 153 */           C0825ej.m856a().m836a(this, c0824ei);
                }

                /* JADX INFO: renamed from: com.flurry.sdk.da$1, reason: invalid class name */
/* 123 */       class AnonymousClass1 implements C0824ei.a<byte[], Void> {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ String f562a;

                    /* JADX INFO: renamed from: b */
                    final /* synthetic */ String f563b;

/* 124 */           AnonymousClass1(String str, String str2) {
                        this.f562a = str;
                        this.f563b = str2;
                    }

                    @Override // com.flurry.sdk.C0824ei.a
                    /* JADX INFO: renamed from: a */

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
/* 130 */           public void mo575a(C0824ei<byte[], Void> c0824ei, Void r6) {
/* 131 */               int iE = c0824ei.m878e();
/* 132 */               if (iE > 0) {
/* 133 */                   C0827el.m900d(C0790da.m569b(), "FlurryDataSender: report " + this.f562a + " sent. HTTP response: " + iE);
/* 136 */                   if (C0827el.m895c() <= 3 && C0827el.m901d()) {
/* 137 */                       C0801dl.m732a().m734a(new RunnableC13131(iE));
                            }
/* 146 */                   C0790da.this.mo573a(this.f562a, this.f563b, iE);
/* 147 */                   C0790da.this.m589d();
/* 151 */                   return;
                        }
/* 149 */               C0790da.this.m583b(this.f562a, this.f563b);
                    }

                    /* JADX INFO: renamed from: com.flurry.sdk.da$1$1, reason: invalid class name and collision with other inner class name */
/* 135 */           class RunnableC13131 implements Runnable {

                        /* JADX INFO: renamed from: a */
                        final /* synthetic */ int f565a;

/* 136 */               RunnableC13131(int i) {
                            this.f565a = i;
                        }

                        @Override // java.lang.Runnable
/* 139 */               public void run() {
/* 140 */                   Toast.makeText(C0801dl.m732a().m736b(), "SD HTTP Response Code: " + this.f565a, 0).show();
                        }
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.da$2, reason: invalid class name */
/* 156 */       class AnonymousClass2 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ int f567a;

/* 157 */           AnonymousClass2(int i) {
                        this.f567a = i;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 161 */           public void mo576a() {
                        C0796dg c0796dgM715c;
/* 162 */               if (this.f567a == 200 && (c0796dgM715c = C0798di.m709a().m715c()) != null) {
/* 165 */                   c0796dgM715c.m678b();
                        }
                    }
                }

                @Override // com.flurry.sdk.AbstractC0791db
                /* JADX INFO: renamed from: a */
/* 157 */       protected void mo573a(String str, String str2, int i) {
/* 158 */           m579a(new AnonymousClass2(i));
/* 171 */           super.mo573a(str, str2, i);
                }
            }
