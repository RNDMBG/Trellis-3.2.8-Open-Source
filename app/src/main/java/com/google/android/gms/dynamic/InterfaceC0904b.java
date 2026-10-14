            package com.google.android.gms.dynamic;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.dynamic.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC0904b extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.dynamic.b$a */
                public static abstract class a extends Binder implements InterfaceC0904b {

                    /* JADX INFO: renamed from: com.google.android.gms.dynamic.b$a$a, reason: collision with other inner class name */
                    private static class C1315a implements InterfaceC0904b {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1026dG;

                        C1315a(IBinder iBinder) {
                            this.f1026dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1026dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.dynamic.IObjectWrapper");
                    }

                    /* JADX INFO: renamed from: z */
                    public static InterfaceC0904b m1164z(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0904b)) ? new C1315a(iBinder) : (InterfaceC0904b) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1598968902:
                                reply.writeString("com.google.android.gms.dynamic.IObjectWrapper");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }
            }
