            package com.google.android.gms.dynamic;

            import android.content.Context;
            import android.os.IBinder;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.internal.C1028dm;
            
            /* JADX INFO: renamed from: com.google.android.gms.dynamic.e */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC0907e<T> {

                /* JADX INFO: renamed from: mi */
                private final String f1028mi;

                /* JADX INFO: renamed from: mj */
                private T f1029mj;

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.e$a */
                public static class a extends Exception {
                    public a(String str) {
                        super(str);
                    }

                    public a(String str, Throwable th) {
                        super(str, th);
                    }
                }

                protected AbstractC0907e(String str) {
                    this.f1028mi = str;
                }

                /* JADX INFO: renamed from: d */
                protected abstract T mo1167d(IBinder iBinder);

                /* JADX INFO: renamed from: t */
                protected final T m1168t(Context context) throws a {
                    if (this.f1029mj == null) {
                        C1028dm.m1605e(context);
                        Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
                        if (remoteContext == null) {
                            throw new a("Could not get remote context.");
                        }
                        try {
                            this.f1029mj = mo1167d((IBinder) remoteContext.getClassLoader().loadClass(this.f1028mi).newInstance());
                        } catch (ClassNotFoundException e) {
                            throw new a("Could not load creator class.");
                        } catch (IllegalAccessException e2) {
                            throw new a("Could not access creator.");
                        } catch (InstantiationException e3) {
                            throw new a("Could not instantiate creator.");
                        }
                    }
                    return this.f1029mj;
                }
            }
