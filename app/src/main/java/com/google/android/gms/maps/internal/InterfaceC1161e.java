            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.CameraPosition;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.e */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1161e extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.e$a */
                public static abstract class a extends Binder implements InterfaceC1161e {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.e$a$a, reason: collision with other inner class name */
                    private static class C1352a implements InterfaceC1161e {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2268dG;

                        C1352a(IBinder iBinder) {
                            this.f2268dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2268dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1161e
                        public void onCameraChange(CameraPosition position) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnCameraChangeListener");
                                if (position != null) {
                                    parcelObtain.writeInt(1);
                                    position.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2268dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnCameraChangeListener");
                    }

                    /* JADX INFO: renamed from: P */
                    public static InterfaceC1161e m2245P(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnCameraChangeListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1161e)) ? new C1352a(iBinder) : (InterfaceC1161e) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnCameraChangeListener");
                                onCameraChange(data.readInt() != 0 ? CameraPosition.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnCameraChangeListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onCameraChange(CameraPosition cameraPosition) throws RemoteException;
            }
