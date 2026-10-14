            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1158b extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.b$a */
                public static abstract class a extends Binder implements InterfaceC1158b {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.b$a$a, reason: collision with other inner class name */
                    private static class C1349a implements InterfaceC1158b {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2265dG;

                        C1349a(IBinder iBinder) {
                            this.f2265dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2265dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1158b
                        public void onCancel() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICancelableCallback");
                                this.f2265dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1158b
                        public void onFinish() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICancelableCallback");
                                this.f2265dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.ICancelableCallback");
                    }

                    /* JADX INFO: renamed from: I */
                    public static InterfaceC1158b m2236I(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICancelableCallback");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1158b)) ? new C1349a(iBinder) : (InterfaceC1158b) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICancelableCallback");
                                onFinish();
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICancelableCallback");
                                onCancel();
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.ICancelableCallback");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onCancel() throws RemoteException;

                void onFinish() throws RemoteException;
            }
