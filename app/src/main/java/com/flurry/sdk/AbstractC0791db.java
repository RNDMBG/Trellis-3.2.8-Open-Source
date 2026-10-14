            package com.flurry.sdk;

            import java.util.HashSet;
            import java.util.List;
            import java.util.Set;
            
            /* JADX INFO: renamed from: com.flurry.sdk.db */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public abstract class AbstractC0791db implements InterfaceC0833er {

                /* JADX INFO: renamed from: d */
                protected final String f569d;

                /* JADX INFO: renamed from: f */
                C0793dd f571f;

                /* JADX INFO: renamed from: e */
                Set<String> f570e = new HashSet();

                /* JADX INFO: renamed from: g */
                protected String f572g = "defaultDataKey_";

                /* JADX INFO: renamed from: com.flurry.sdk.db$a */
                public interface a {
                    /* JADX INFO: renamed from: a */
                    void m592a();
                }

                /* JADX INFO: renamed from: a */
                protected abstract void mo574a(byte[] bArr, String str, String str2);

/* 35 */        public AbstractC0791db(String str, String str2) {
/* 37 */            this.f569d = str2;
/* 39 */            C0834es.m907a().m910a(this);
/* 40 */            m580a(str);
                }

                /* JADX INFO: renamed from: a */
/* 43 */        protected void m579a(AbstractRunnableC0844fc abstractRunnableC0844fc) {
/* 44 */            C0801dl.m732a().m740c(abstractRunnableC0844fc);
                }

                /* JADX INFO: renamed from: com.flurry.sdk.db$1, reason: invalid class name */
/* 46 */        class AnonymousClass1 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ String f573a;

/* 47 */            AnonymousClass1(String str) {
                        this.f573a = str;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 50 */            public void mo576a() {
/* 51 */                AbstractC0791db.this.f571f = new C0793dd(this.f573a);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 47 */        protected void m580a(String str) {
/* 48 */            m579a(new AnonymousClass1(str));
                }

                @Override // com.flurry.sdk.InterfaceC0833er
                /* JADX INFO: renamed from: a */
/* 61 */        public void mo581a(boolean z) {
/* 62 */            C0827el.m885a(4, this.f569d, "onNetworkStateChanged : isNetworkEnable = " + z);
/* 63 */            if (z) {
/* 64 */                m589d();
                    }
                }

                /* JADX INFO: renamed from: b */
/* 74 */        public void m584b(byte[] bArr, String str, String str2) {
/* 75 */            m582a(bArr, str, str2, null);
                }

                /* JADX INFO: renamed from: a */
/* 80 */        protected void m582a(byte[] bArr, String str, String str2, a aVar) {
/* 81 */            if (bArr == null || bArr.length == 0) {
/* 83 */                C0827el.m885a(6, this.f569d, "Report that has to be sent is EMPTY or NULL");
                    } else {
/* 87 */                m587c(bArr, str, str2);
/* 89 */                m578a(aVar);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 95 */        protected int m585c() {
/* 96 */            return this.f570e.size();
                }

                /* JADX INFO: renamed from: com.flurry.sdk.db$2, reason: invalid class name */
/* 99 */        class AnonymousClass2 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ byte[] f575a;

                    /* JADX INFO: renamed from: b */
                    final /* synthetic */ String f576b;

                    /* JADX INFO: renamed from: c */
                    final /* synthetic */ String f577c;

/* 100 */           AnonymousClass2(byte[] bArr, String str, String str2) {
                        this.f575a = bArr;
                        this.f576b = str;
                        this.f577c = str2;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 103 */           public void mo576a() {
/* 104 */               AbstractC0791db.this.m588d(this.f575a, this.f576b, this.f577c);
                    }
                }

                /* JADX INFO: renamed from: c */
/* 100 */       protected void m587c(byte[] bArr, String str, String str2) {
/* 101 */           m579a(new AnonymousClass2(bArr, str, str2));
                }

                /* JADX INFO: renamed from: d */
/* 109 */       protected void m589d() {
/* 110 */           m578a((a) null);
                }

                /* JADX INFO: renamed from: com.flurry.sdk.db$3, reason: invalid class name */
/* 112 */       class AnonymousClass3 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ a f579a;

/* 113 */           AnonymousClass3(a aVar) {
                        this.f579a = aVar;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 116 */           public void mo576a() {
/* 117 */               AbstractC0791db.this.m591f();
/* 119 */               if (this.f579a != null) {
/* 120 */                   this.f579a.m592a();
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 113 */       protected void m578a(a aVar) {
/* 114 */           m579a(new AnonymousClass3(aVar));
                }

                /* JADX INFO: renamed from: e */
/* 128 */       protected boolean m590e() {
                    return m585c() <= 5;
                }

                /* JADX INFO: renamed from: a */
/* 170 */       protected String m577a(String str, String str2) {
/* 171 */           return this.f572g + str + "_" + str2;
                }

                /* JADX INFO: renamed from: d */
/* 176 */       protected String m588d(byte[] bArr, String str, String str2) throws Throwable {
/* 177 */           String strM577a = m577a(str, str2);
/* 179 */           C0792dc c0792dc = new C0792dc();
/* 180 */           c0792dc.m594a(bArr);
/* 182 */           String strM593a = c0792dc.m593a();
/* 184 */           this.f571f.m602a(c0792dc, strM577a);
/* 186 */           return strM593a;
                }

                /* JADX INFO: renamed from: f */
/* 192 */       protected void m591f() throws Throwable {
/* 193 */           if (!C0834es.m907a().m912c()) {
/* 194 */               C0827el.m885a(5, this.f569d, "Reports were not sent! No Internet connection!");
/* 242 */               return;
                    }
/* 199 */           List<String> listM601a = this.f571f.m601a();
/* 200 */           if (listM601a == null || listM601a.isEmpty()) {
/* 201 */               C0827el.m885a(4, this.f569d, "No more reports to send.");
/* 242 */               return;
                    }
                    for (String str : listM601a) {
/* 206 */               if (m590e()) {
/* 210 */                   List<String> listM607c = this.f571f.m607c(str);
/* 212 */                   C0827el.m885a(4, this.f569d, "Number of not sent blocks = " + listM607c.size());
/* 214 */                   int i = 0;
                            while (true) {
                                int i2 = i;
                                if (i2 >= listM607c.size()) {
                                    break;
                                }
/* 216 */                       String str2 = listM607c.get(i2);
/* 218 */                       if (!this.f570e.contains(str2)) {
/* 223 */                           if (!m590e()) {
                                        break;
                                    }
/* 228 */                           byte[] bArrM595b = new C0792dc(str2).m595b();
/* 230 */                           if (bArrM595b == null || bArrM595b.length == 0) {
/* 231 */                               C0827el.m885a(6, this.f569d, "Internal ERROR! Report is empty!");
/* 232 */                               this.f571f.m604a(str2, str);
                                    } else {
/* 236 */                               this.f570e.add(str2);
/* 238 */                               mo574a(bArrM595b, str2, str);
                                    }
                                }
/* 214 */                       i = i2 + 1;
                            }
                        } else {
/* 242 */                   return;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.flurry.sdk.db$4, reason: invalid class name */
/* 243 */       class AnonymousClass4 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ String f581a;

                    /* JADX INFO: renamed from: b */
                    final /* synthetic */ String f582b;

/* 244 */           AnonymousClass4(String str, String str2) {
                        this.f581a = str;
                        this.f582b = str2;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 248 */           public void mo576a() {
/* 251 */               if (!AbstractC0791db.this.f571f.m604a(this.f581a, this.f582b)) {
/* 252 */                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block wasn't deleted with id = " + this.f581a);
                        }
/* 258 */               if (!AbstractC0791db.this.f570e.remove(this.f581a)) {
/* 259 */                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block with id = " + this.f581a + " was not in progress state");
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
/* 244 */       protected void mo573a(String str, String str2, int i) {
/* 245 */           m579a(new AnonymousClass4(str, str2));
                }

                /* JADX INFO: renamed from: com.flurry.sdk.db$5, reason: invalid class name */
/* 265 */       class AnonymousClass5 extends AbstractRunnableC0844fc {

                    /* JADX INFO: renamed from: a */
                    final /* synthetic */ String f584a;

/* 266 */           AnonymousClass5(String str) {
                        this.f584a = str;
                    }

                    @Override // com.flurry.sdk.AbstractRunnableC0844fc
                    /* JADX INFO: renamed from: a */
/* 270 */           public void mo576a() {
/* 272 */               if (!AbstractC0791db.this.f570e.remove(this.f584a)) {
/* 273 */                   C0827el.m885a(6, AbstractC0791db.this.f569d, "Internal error. Block with id = " + this.f584a + " was not in progress state");
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 266 */       protected void m583b(String str, String str2) {
/* 267 */           m579a(new AnonymousClass5(str));
                }

                /* JADX INFO: renamed from: c */
/* 281 */       protected void m586c(String str, String str2) {
/* 284 */           if (!this.f571f.m604a(str, str2)) {
/* 285 */               C0827el.m885a(6, this.f569d, "Internal error. Block wasn't deleted with id = " + str);
                    }
/* 291 */           if (!this.f570e.remove(str)) {
/* 292 */               C0827el.m885a(6, this.f569d, "Internal error. Block with id = " + str + " was not in progress state");
                    }
                }
            }
