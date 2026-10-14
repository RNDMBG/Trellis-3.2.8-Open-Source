            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.wallet.FullWalletRequest;
            import com.google.android.gms.wallet.MaskedWalletRequest;
            import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gh */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1104gh extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.gh$a */
                public static abstract class a extends Binder implements InterfaceC1104gh {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.gh$a$a, reason: collision with other inner class name */
                    private static class C1344a implements InterfaceC1104gh {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2103dG;

                        C1344a(IBinder iBinder) {
                            this.f2103dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1104gh
                        /* JADX INFO: renamed from: a */
                        public void mo2094a(Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1105gi != null ? interfaceC1105gi.asBinder() : null);
                                this.f2103dG.transact(5, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1104gh
                        /* JADX INFO: renamed from: a */
                        public void mo2095a(FullWalletRequest fullWalletRequest, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                                if (fullWalletRequest != null) {
                                    parcelObtain.writeInt(1);
                                    fullWalletRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1105gi != null ? interfaceC1105gi.asBinder() : null);
                                this.f2103dG.transact(2, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1104gh
                        /* JADX INFO: renamed from: a */
                        public void mo2096a(MaskedWalletRequest maskedWalletRequest, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                                if (maskedWalletRequest != null) {
                                    parcelObtain.writeInt(1);
                                    maskedWalletRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1105gi != null ? interfaceC1105gi.asBinder() : null);
                                this.f2103dG.transact(1, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1104gh
                        /* JADX INFO: renamed from: a */
                        public void mo2097a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                                if (notifyTransactionStatusRequest != null) {
                                    parcelObtain.writeInt(1);
                                    notifyTransactionStatusRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2103dG.transact(4, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1104gh
                        /* JADX INFO: renamed from: a */
                        public void mo2098a(String str, String str2, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IOwService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1105gi != null ? interfaceC1105gi.asBinder() : null);
                                this.f2103dG.transact(3, parcelObtain, null, 1);
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2103dG;
                        }
                    }

                    /* JADX INFO: renamed from: as */
                    public static InterfaceC1104gh m2099as(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IOwService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1104gh)) ? new C1344a(iBinder) : (InterfaceC1104gh) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                                mo2096a(data.readInt() != 0 ? MaskedWalletRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, InterfaceC1105gi.a.m2103at(data.readStrongBinder()));
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                                mo2095a(data.readInt() != 0 ? FullWalletRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, InterfaceC1105gi.a.m2103at(data.readStrongBinder()));
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                                mo2098a(data.readString(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, InterfaceC1105gi.a.m2103at(data.readStrongBinder()));
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                                mo2097a(data.readInt() != 0 ? NotifyTransactionStatusRequest.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IOwService");
                                mo2094a(data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, InterfaceC1105gi.a.m2103at(data.readStrongBinder()));
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.wallet.internal.IOwService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo2094a(Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2095a(FullWalletRequest fullWalletRequest, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2096a(MaskedWalletRequest maskedWalletRequest, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2097a(NotifyTransactionStatusRequest notifyTransactionStatusRequest, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2098a(String str, String str2, Bundle bundle, InterfaceC1105gi interfaceC1105gi) throws RemoteException;
            }
