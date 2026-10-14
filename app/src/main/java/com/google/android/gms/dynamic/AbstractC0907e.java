            package com.google.android.gms.dynamic;

            import android.content.Context;
            import android.os.IBinder;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.internal.C1028dm;
            
            
            
            public abstract class AbstractC0907e<T> {

                
                private final String f1028mi;

                
                private T f1029mj;

                
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

                
                protected abstract T mo1167d(IBinder iBinder);

                
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
