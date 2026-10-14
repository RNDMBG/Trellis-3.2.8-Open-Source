            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.n */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1170n extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.n$a */
                public static abstract class a extends Binder implements InterfaceC1170n {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.n$a$a, reason: collision with other inner class name */
                    private static class C1361a implements InterfaceC1170n {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2277dG;

                        C1361a(IBinder iBinder) {
                            this.f2277dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2277dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1170n
                        /* JADX INFO: renamed from: d */
                        public void mo2201d(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f2277dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                    }

                    /* JADX INFO: renamed from: Y */
                    public static InterfaceC1170n m2255Y(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1170n)) ? new C1361a(iBinder) : (InterfaceC1170n) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                                mo2201d(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: d */
                void mo2201d(InterfaceC0904b interfaceC0904b) throws RemoteException;
            }
