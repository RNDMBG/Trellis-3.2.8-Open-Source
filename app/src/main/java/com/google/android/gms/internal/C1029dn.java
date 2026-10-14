            package com.google.android.gms.internal;

            import android.content.Context;
            import android.os.IBinder;
            import android.view.View;
            import com.google.android.gms.dynamic.AbstractC0907e;
            import com.google.android.gms.dynamic.BinderC0905c;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dn */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1029dn extends AbstractC0907e<InterfaceC1026dk> {

                /* JADX INFO: renamed from: ll */
                private static final C1029dn f1719ll = new C1029dn();

                private C1029dn() {
                    super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
                }

                /* JADX INFO: renamed from: d */
                public static View m1610d(Context context, int i, int i2) throws AbstractC0907e.a {
                    return f1719ll.m1611e(context, i, i2);
                }

                /* JADX INFO: renamed from: e */
                private View m1611e(Context context, int i, int i2) throws AbstractC0907e.a {
                    try {
                        return (View) BinderC0905c.m1165b(m1168t(context).mo1597a(BinderC0905c.m1166g(context), i, i2));
                    } catch (Exception e) {
                        throw new AbstractC0907e.a("Could not get button with size " + i + " and color " + i2, e);
                    }
                }

                @Override // com.google.android.gms.dynamic.AbstractC0907e
                /* JADX INFO: renamed from: d */
                public /* synthetic */ InterfaceC1026dk mo1167d(IBinder iBinder) {
                    return m1612y(iBinder);
                }

                /* JADX INFO: renamed from: y */
                public InterfaceC1026dk m1612y(IBinder iBinder) {
                    return InterfaceC1026dk.a.m1598x(iBinder);
                }
            }
