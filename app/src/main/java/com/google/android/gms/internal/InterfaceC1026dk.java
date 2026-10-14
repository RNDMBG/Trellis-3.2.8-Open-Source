            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.dk */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1026dk extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.dk$a */
                public static abstract class a extends Binder implements InterfaceC1026dk {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.dk$a$a, reason: collision with other inner class name */
                    private static class C1332a implements InterfaceC1026dk {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1716dG;

                        C1332a(IBinder iBinder) {
                            this.f1716dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1026dk
                        /* JADX INFO: renamed from: a */
                        public InterfaceC0904b mo1597a(InterfaceC0904b interfaceC0904b, int i, int i2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                this.f1716dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC0904b interfaceC0904bM1164z = InterfaceC0904b.a.m1164z(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC0904bM1164z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1716dG;
                        }
                    }

                    /* JADX INFO: renamed from: x */
                    public static InterfaceC1026dk m1598x(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1026dk)) ? new C1332a(iBinder) : (InterfaceC1026dk) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                                InterfaceC0904b interfaceC0904bA = mo1597a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt(), data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bA != null ? interfaceC0904bA.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                InterfaceC0904b mo1597a(InterfaceC0904b interfaceC0904b, int i, int i2) throws RemoteException;
            }
