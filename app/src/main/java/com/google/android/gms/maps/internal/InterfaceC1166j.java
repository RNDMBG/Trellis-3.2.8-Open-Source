            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.LatLng;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.j */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1166j extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.j$a */
                public static abstract class a extends Binder implements InterfaceC1166j {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.j$a$a, reason: collision with other inner class name */
                    private static class C1357a implements InterfaceC1166j {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2273dG;

                        C1357a(IBinder iBinder) {
                            this.f2273dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2273dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1166j
                        public void onMapLongClick(LatLng point) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                                if (point != null) {
                                    parcelObtain.writeInt(1);
                                    point.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2273dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMapLongClickListener");
                    }

                    /* JADX INFO: renamed from: U */
                    public static InterfaceC1166j m2251U(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1166j)) ? new C1357a(iBinder) : (InterfaceC1166j) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                                onMapLongClick(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onMapLongClick(LatLng latLng) throws RemoteException;
            }
