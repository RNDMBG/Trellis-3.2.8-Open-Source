            package com.google.android.gms.internal;

            import android.net.Uri;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fe */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1074fe extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.fe$a */
                public static abstract class a extends Binder implements InterfaceC1074fe {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.fe$a$a, reason: collision with other inner class name */
                    private static class C1338a implements InterfaceC1074fe {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1901dG;

                        C1338a(IBinder iBinder) {
                            this.f1901dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1074fe
                        /* JADX INFO: renamed from: a */
                        public void mo1941a(InterfaceC1073fd interfaceC1073fd, Uri uri, Bundle bundle, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaService");
                                parcelObtain.writeStrongBinder(interfaceC1073fd != null ? interfaceC1073fd.asBinder() : null);
                                if (uri != null) {
                                    parcelObtain.writeInt(1);
                                    uri.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1901dG.transact(1, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1901dG;
                        }
                    }

                    /* JADX INFO: renamed from: al */
                    public static InterfaceC1074fe m1942al(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1074fe)) ? new C1338a(iBinder) : (InterfaceC1074fe) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaService");
                                mo1941a(InterfaceC1073fd.a.m1940ak(data.readStrongBinder()), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt() != 0);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.panorama.internal.IPanoramaService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo1941a(InterfaceC1073fd interfaceC1073fd, Uri uri, Bundle bundle, boolean z) throws RemoteException;
            }
