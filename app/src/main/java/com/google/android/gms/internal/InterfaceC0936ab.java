            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ab */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC0936ab extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.ab$a */
                public static abstract class a extends Binder implements InterfaceC0936ab {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.ab$a$a, reason: collision with other inner class name */
                    private static class C1316a implements InterfaceC0936ab {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1140dG;

                        C1316a(IBinder iBinder) {
                            this.f1140dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1140dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0936ab
                        public void onAdClosed() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdListener");
                                this.f1140dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0936ab
                        public void onAdFailedToLoad(int errorCode) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdListener");
                                parcelObtain.writeInt(errorCode);
                                this.f1140dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0936ab
                        public void onAdLeftApplication() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdListener");
                                this.f1140dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0936ab
                        public void onAdLoaded() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdListener");
                                this.f1140dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0936ab
                        public void onAdOpened() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdListener");
                                this.f1140dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.ads.internal.client.IAdListener");
                    }

                    /* JADX INFO: renamed from: e */
                    public static InterfaceC0936ab m1239e(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0936ab)) ? new C1316a(iBinder) : (InterfaceC0936ab) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdListener");
                                onAdClosed();
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdListener");
                                onAdFailedToLoad(data.readInt());
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdListener");
                                onAdLeftApplication();
                                reply.writeNoException();
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdListener");
                                onAdLoaded();
                                reply.writeNoException();
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdListener");
                                onAdOpened();
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.client.IAdListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onAdClosed() throws RemoteException;

                void onAdFailedToLoad(int i) throws RemoteException;

                void onAdLeftApplication() throws RemoteException;

                void onAdLoaded() throws RemoteException;

                void onAdOpened() throws RemoteException;
            }
