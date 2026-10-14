            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.LatLng;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.h */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1164h extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.h$a */
                public static abstract class a extends Binder implements InterfaceC1164h {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.h$a$a, reason: collision with other inner class name */
                    private static class C1355a implements InterfaceC1164h {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2271dG;

                        C1355a(IBinder iBinder) {
                            this.f2271dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2271dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1164h
                        public void onMapClick(LatLng point) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMapClickListener");
                                if (point != null) {
                                    parcelObtain.writeInt(1);
                                    point.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2271dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMapClickListener");
                    }

                    /* JADX INFO: renamed from: S */
                    public static InterfaceC1164h m2249S(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMapClickListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1164h)) ? new C1355a(iBinder) : (InterfaceC1164h) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMapClickListener");
                                onMapClick(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMapClickListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onMapClick(LatLng latLng) throws RemoteException;
            }
