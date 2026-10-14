            package com.crashlytics.android.internal;

            import com.crashlytics.android.Crashlytics;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.List;
            import java.util.Locale;
            import java.util.TreeSet;
            import java.util.UUID;
            import java.util.concurrent.CopyOnWriteArrayList;
            
            /* JADX INFO: renamed from: com.crashlytics.android.internal.K */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    final class C0637K {

                /* JADX INFO: renamed from: a */
                private final C0650X f231a;

                /* JADX INFO: renamed from: b */
                private final C0687ah f232b;

                /* JADX INFO: renamed from: c */
                private C0664aK f233c;

                /* JADX INFO: renamed from: d */
                private final C0689aj f234d;

                /* JADX INFO: renamed from: e */
                private final int f235e;

                /* JADX INFO: renamed from: f */
                private List<InterfaceC0690ak> f236f;

/* 51 */        private C0637K(C0650X c0650x, C0687ah c0687ah, C0689aj c0689aj, int i) throws IOException {
/* 49 */            this.f236f = new CopyOnWriteArrayList();
/* 54 */            this.f231a = c0650x;
/* 55 */            this.f234d = c0689aj;
/* 56 */            this.f232b = c0687ah;
/* 58 */            this.f232b.m240a();
/* 60 */            this.f235e = 100;
                }

/* 64 */        C0637K(C0650X c0650x, C0687ah c0687ah, C0689aj c0689aj) throws IOException {
/* 65 */            this(c0650x, c0687ah, c0689aj, 100);
                }

                /* JADX INFO: renamed from: a */
/* 68 */        final void m120a(C0648V c0648v) throws Throwable {
/* 69 */            byte[] bArrM153a = this.f231a.m153a(c0648v);
/* 70 */            int length = bArrM153a.length;
                    if (!this.f234d.m248a(length, m119e())) {
                        C0681ab.m209a(4, String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", Integer.valueOf(this.f234d.m243a()), Integer.valueOf(length), Integer.valueOf(m119e())));
                        m124a();
                    }
/* 72 */            this.f234d.m247a(bArrM153a);
                }

                /* JADX INFO: renamed from: a */
/* 79 */        final void m122a(InterfaceC0690ak interfaceC0690ak) {
/* 80 */            if (interfaceC0690ak != null) {
                        this.f236f.add(interfaceC0690ak);
                    }
                }

                /* JADX INFO: renamed from: a */
/* 88 */        final boolean m124a() throws Throwable {
                    boolean z = true;
/* 89 */            String str = null;
/* 93 */            if (this.f234d.m249b()) {
                        z = false;
                    } else {
                        str = "sa_" + UUID.randomUUID().toString() + "_" + this.f232b.m240a() + ".tap";
/* 95 */                this.f234d.m245a(str);
/* 97 */                C0681ab.m209a(4, String.format(Locale.US, "generated new to-send analytics file %s", str));
/* 100 */               this.f232b.m240a();
                    }
/* 104 */           m117a(str);
/* 105 */           return z;
                }

                /* JADX INFO: renamed from: e */
/* 139 */       private int m119e() {
/* 140 */           if (this.f233c == null) {
                        return 8000;
                    }
                    return this.f233c.f307c;
                }

                /* JADX INFO: renamed from: a */
/* 144 */       final void m121a(C0664aK c0664aK) {
/* 145 */           this.f233c = c0664aK;
                }

                /* JADX INFO: renamed from: a */
/* 152 */       private void m117a(String str) {
/* 153 */           Iterator<InterfaceC0690ak> it = this.f236f.iterator();
                    while (it.hasNext()) {
                        try {
/* 155 */                   it.next().mo145c();
                        } catch (Exception e) {
/* 157 */                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "One of the roll over listeners threw an exception", e);
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
/* 162 */       final List<File> m125b() {
/* 163 */           return this.f234d.m244a(1);
                }

                /* JADX INFO: renamed from: a */
/* 166 */       final void m123a(List<File> list) {
/* 167 */           this.f234d.m246a(list);
                }

                /* JADX INFO: renamed from: c */
/* 170 */       final void m126c() {
/* 171 */           this.f234d.m246a(this.f234d.m250c());
/* 172 */           this.f234d.m251d();
                }

                /* JADX INFO: renamed from: d */
/* 175 */       final void m127d() {
/* 176 */           List<File> listM250c = this.f234d.m250c();
/* 178 */           if (listM250c.size() > this.f235e) {
/* 180 */               int size = listM250c.size() - this.f235e;
                        C0681ab.m221c(String.format(Locale.US, "Found %d files in session analytics roll over directory, this is greater than %d, deleting %d oldest files", Integer.valueOf(listM250c.size()), Integer.valueOf(this.f235e), Integer.valueOf(size)));
/* 186 */               TreeSet treeSet = new TreeSet(new C0638L(this));
                        for (File file : listM250c) {
/* 195 */                   treeSet.add(new C0639M(this, file, m118b(file.getName())));
                        }
/* 198 */               ArrayList arrayList = new ArrayList();
/* 199 */               Iterator it = treeSet.iterator();
                        while (it.hasNext()) {
/* 200 */                   arrayList.add(((C0639M) it.next()).f237a);
/* 202 */                   if (arrayList.size() == size) {
                                break;
                            }
                        }
/* 205 */               this.f234d.m246a(arrayList);
                    }
                }

                /* JADX INFO: renamed from: b */
/* 208 */       private static long m118b(String str) {
/* 209 */           String[] strArrSplit = str.split("_");
/* 211 */           if (strArrSplit.length != 3) {
                        return 0L;
                    }
                    try {
/* 214 */               return Long.valueOf(strArrSplit[2]).longValue();
                    } catch (NumberFormatException e) {
                        return 0L;
                    }
                }
            }
