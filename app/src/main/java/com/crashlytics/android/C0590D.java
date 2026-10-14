            package com.crashlytics.android;

            import com.crashlytics.android.internal.AbstractRunnableC0680aa;
            import java.io.File;
            
            /* JADX INFO: renamed from: com.crashlytics.android.D */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 1005 */  final class C0590D extends AbstractRunnableC0680aa {

                /* JADX INFO: renamed from: a */
                private /* synthetic */ File f150a;

/* 1006 */      C0590D(C0744v c0744v, File file) {
/* 1007 */          this.f150a = file;
                }

                @Override // com.crashlytics.android.internal.AbstractRunnableC0680aa
                /* JADX INFO: renamed from: a */
/* 1008 */      public final void mo38a() {
/* 1009 */          InterfaceC0608V interfaceC0608VM35q = Crashlytics.getInstance().m35q();
/* 1013 */          if (interfaceC0608VM35q != null) {
/* 1014 */              new C0614aa(interfaceC0608VM35q).m58a(new C0612Z(this.f150a, C0744v.m421j()));
                    }
                }
            }
