            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.m */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1169m extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.m$a */
                public static abstract class a extends Binder implements InterfaceC1169m {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.m$a$a, reason: collision with other inner class name */
                    private static class C1360a implements InterfaceC1169m {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2276dG;

                        C1360a(IBinder iBinder) {
                            this.f2276dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2276dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1169m
                        public boolean onMyLocationButtonClick() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                                this.f2276dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                boolean z = parcelObtain2.readInt() != 0;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                    }

                    /* JADX INFO: renamed from: X */
                    public static InterfaceC1169m m2254X(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1169m)) ? new C1360a(iBinder) : (InterfaceC1169m) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                                boolean zOnMyLocationButtonClick = onMyLocationButtonClick();
                                reply.writeNoException();
                                reply.writeInt(zOnMyLocationButtonClick ? 1 : 0);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMyLocationButtonClickListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                boolean onMyLocationButtonClick() throws RemoteException;
            }
