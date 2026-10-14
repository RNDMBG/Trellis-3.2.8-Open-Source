            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.i */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1165i extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.i$a */
                public static abstract class a extends Binder implements InterfaceC1165i {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.i$a$a, reason: collision with other inner class name */
                    private static class C1356a implements InterfaceC1165i {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2272dG;

                        C1356a(IBinder iBinder) {
                            this.f2272dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2272dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1165i
                        public void onMapLoaded() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMapLoadedCallback");
                                this.f2272dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMapLoadedCallback");
                    }

                    /* JADX INFO: renamed from: T */
                    public static InterfaceC1165i m2250T(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMapLoadedCallback");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1165i)) ? new C1356a(iBinder) : (InterfaceC1165i) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMapLoadedCallback");
                                onMapLoaded();
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMapLoadedCallback");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onMapLoaded() throws RemoteException;
            }
