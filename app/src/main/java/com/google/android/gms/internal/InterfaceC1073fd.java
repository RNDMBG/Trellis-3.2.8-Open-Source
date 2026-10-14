            package com.google.android.gms.internal;

            import android.content.Intent;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fd */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1073fd extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.fd$a */
                public static abstract class a extends Binder implements InterfaceC1073fd {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.fd$a$a, reason: collision with other inner class name */
                    private static class C1337a implements InterfaceC1073fd {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1900dG;

                        C1337a(IBinder iBinder) {
                            this.f1900dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1073fd
                        /* JADX INFO: renamed from: a */
                        public void mo1939a(int i, Bundle bundle, int i2, Intent intent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                                parcelObtain.writeInt(i);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(i2);
                                if (intent != null) {
                                    parcelObtain.writeInt(1);
                                    intent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1900dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1900dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                    }

                    /* JADX INFO: renamed from: ak */
                    public static InterfaceC1073fd m1940ak(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1073fd)) ? new C1337a(iBinder) : (InterfaceC1073fd) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                                mo1939a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt(), data.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.panorama.internal.IPanoramaCallbacks");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo1939a(int i, Bundle bundle, int i2, Intent intent) throws RemoteException;
            }
