            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.p */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1118p extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.p$a */
                public static abstract class a extends Binder implements InterfaceC1118p {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.p$a$a, reason: collision with other inner class name */
                    private static class C1347a implements InterfaceC1118p {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2138dG;

                        C1347a(IBinder iBinder) {
                            this.f2138dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1118p
                        /* JADX INFO: renamed from: a */
                        public boolean mo2150a(boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f2138dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                boolean z2 = parcelObtain2.readInt() != 0;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return z2;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2138dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1118p
                        /* JADX INFO: renamed from: b */
                        public void mo2151b(String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f2138dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1118p
                        /* JADX INFO: renamed from: e */
                        public String mo2152e(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                parcelObtain.writeString(str);
                                this.f2138dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1118p
                        public String getId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                this.f2138dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: b */
                    public static InterfaceC1118p m2153b(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1118p)) ? new C1347a(iBinder) : (InterfaceC1118p) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                boolean zA = mo2150a(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                parcel2.writeInt(zA ? 1 : 0);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                String strE = mo2152e(parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeString(strE);
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                mo2151b(parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                boolean mo2150a(boolean z) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo2151b(String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: e */
                String mo2152e(String str) throws RemoteException;

                String getId() throws RemoteException;
            }
