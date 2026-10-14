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
            
            
            
    final class C0637K {

                
                private final C0650X f231a;

                
                private final C0687ah f232b;

                
                private C0664aK f233c;

                
                private final C0689aj f234d;

                
                private final int f235e;

                
                private List<InterfaceC0690ak> f236f;

        private C0637K(C0650X c0650x, C0687ah c0687ah, C0689aj c0689aj, int i) throws IOException {
            this.f236f = new CopyOnWriteArrayList();
            this.f231a = c0650x;
            this.f234d = c0689aj;
            this.f232b = c0687ah;
            this.f232b.m240a();
            this.f235e = 100;
                }

        C0637K(C0650X c0650x, C0687ah c0687ah, C0689aj c0689aj) throws IOException {
            this(c0650x, c0687ah, c0689aj, 100);
                }

                
        final void m120a(C0648V c0648v) throws Throwable {
            byte[] bArrM153a = this.f231a.m153a(c0648v);
            int length = bArrM153a.length;
                    if (!this.f234d.m248a(length, m119e())) {
                        C0681ab.m209a(4, String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", Integer.valueOf(this.f234d.m243a()), Integer.valueOf(length), Integer.valueOf(m119e())));
                        m124a();
                    }
            this.f234d.m247a(bArrM153a);
                }

                
        final void m122a(InterfaceC0690ak interfaceC0690ak) {
            if (interfaceC0690ak != null) {
                        this.f236f.add(interfaceC0690ak);
                    }
                }

                
        final boolean m124a() throws Throwable {
                    boolean z = true;
            String str = null;
            if (this.f234d.m249b()) {
                        z = false;
                    } else {
                        str = "sa_" + UUID.randomUUID().toString() + "_" + this.f232b.m240a() + ".tap";
                this.f234d.m245a(str);
                C0681ab.m209a(4, String.format(Locale.US, "generated new to-send analytics file %s", str));
               this.f232b.m240a();
                    }
           m117a(str);
           return z;
                }

                
       private int m119e() {
           if (this.f233c == null) {
                        return 8000;
                    }
                    return this.f233c.f307c;
                }

                
       final void m121a(C0664aK c0664aK) {
           this.f233c = c0664aK;
                }

                
       private void m117a(String str) {
           Iterator<InterfaceC0690ak> it = this.f236f.iterator();
                    while (it.hasNext()) {
                        try {
                   it.next().mo145c();
                        } catch (Exception e) {
                   C0727v.m357a().m365b().mo97a(Crashlytics.TAG, "One of the roll over listeners threw an exception", e);
                        }
                    }
                }

                
       final List<File> m125b() {
           return this.f234d.m244a(1);
                }

                
       final void m123a(List<File> list) {
           this.f234d.m246a(list);
                }

                
       final void m126c() {
           this.f234d.m246a(this.f234d.m250c());
           this.f234d.m251d();
                }

                
       final void m127d() {
           List<File> listM250c = this.f234d.m250c();
           if (listM250c.size() > this.f235e) {
               int size = listM250c.size() - this.f235e;
                        C0681ab.m221c(String.format(Locale.US, "Found %d files in session analytics roll over directory, this is greater than %d, deleting %d oldest files", Integer.valueOf(listM250c.size()), Integer.valueOf(this.f235e), Integer.valueOf(size)));
               TreeSet treeSet = new TreeSet(new C0638L(this));
                        for (File file : listM250c) {
                   treeSet.add(new C0639M(this, file, m118b(file.getName())));
                        }
               ArrayList arrayList = new ArrayList();
               Iterator it = treeSet.iterator();
                        while (it.hasNext()) {
                   arrayList.add(((C0639M) it.next()).f237a);
                   if (arrayList.size() == size) {
                                break;
                            }
                        }
               this.f234d.m246a(arrayList);
                    }
                }

                
       private static long m118b(String str) {
           String[] strArrSplit = str.split("_");
           if (strArrSplit.length != 3) {
                        return 0L;
                    }
                    try {
               return Long.valueOf(strArrSplit[2]).longValue();
                    } catch (NumberFormatException e) {
                        return 0L;
                    }
                }
            }
