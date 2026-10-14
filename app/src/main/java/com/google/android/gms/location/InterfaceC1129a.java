            package com.google.android.gms.location;

            import android.location.Location;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.location.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1129a extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.location.a$a */
                public static abstract class a extends Binder implements InterfaceC1129a {

                    /* JADX INFO: renamed from: com.google.android.gms.location.a$a$a, reason: collision with other inner class name */
                    private static class C1348a implements InterfaceC1129a {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2191dG;

                        C1348a(IBinder iBinder) {
                            this.f2191dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2191dG;
                        }

                        @Override // com.google.android.gms.location.InterfaceC1129a
                        public void onLocationChanged(Location location) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.ILocationListener");
                                if (location != null) {
                                    parcelObtain.writeInt(1);
                                    location.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2191dG.transact(1, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.location.ILocationListener");
                    }

                    /* JADX INFO: renamed from: D */
                    public static InterfaceC1129a m2193D(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.ILocationListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1129a)) ? new C1348a(iBinder) : (InterfaceC1129a) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.location.ILocationListener");
                                onLocationChanged(data.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(data) : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.location.ILocationListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onLocationChanged(Location location) throws RemoteException;
            }
