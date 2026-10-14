            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.bo */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC0976bo extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.bo$a */
                public static abstract class a extends Binder implements InterfaceC0976bo {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.bo$a$a, reason: collision with other inner class name */
                    private static class C1324a implements InterfaceC0976bo {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1469dG;

                        C1324a(IBinder iBinder) {
                            this.f1469dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0976bo
                        /* JADX INFO: renamed from: a */
                        public IBinder mo1343a(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f1469dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IBinder strongBinder = parcelObtain2.readStrongBinder();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return strongBinder;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1469dG;
                        }
                    }

                    /* JADX INFO: renamed from: n */
                    public static InterfaceC0976bo m1344n(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0976bo)) ? new C1324a(iBinder) : (InterfaceC0976bo) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                                IBinder iBinderA = mo1343a(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                reply.writeStrongBinder(iBinderA);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                IBinder mo1343a(InterfaceC0904b interfaceC0904b) throws RemoteException;
            }
