            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.wallet.FullWallet;
            import com.google.android.gms.wallet.MaskedWallet;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.gi */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1105gi extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.gi$a */
                public static abstract class a extends Binder implements InterfaceC1105gi {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.gi$a$a, reason: collision with other inner class name */
                    private static class C1345a implements InterfaceC1105gi {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2104dG;

                        C1345a(IBinder iBinder) {
                            this.f2104dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1105gi
                        /* JADX INFO: renamed from: a */
                        public void mo2100a(int i, FullWallet fullWallet, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                parcelObtain.writeInt(i);
                                if (fullWallet != null) {
                                    parcelObtain.writeInt(1);
                                    fullWallet.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2104dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1105gi
                        /* JADX INFO: renamed from: a */
                        public void mo2101a(int i, MaskedWallet maskedWallet, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                parcelObtain.writeInt(i);
                                if (maskedWallet != null) {
                                    parcelObtain.writeInt(1);
                                    maskedWallet.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2104dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1105gi
                        /* JADX INFO: renamed from: a */
                        public void mo2102a(int i, boolean z, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2104dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                            return this.f2104dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    }

                    /* JADX INFO: renamed from: at */
                    public static InterfaceC1105gi m2103at(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1105gi)) ? new C1345a(iBinder) : (InterfaceC1105gi) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                mo2101a(data.readInt(), data.readInt() != 0 ? MaskedWallet.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                mo2100a(data.readInt(), data.readInt() != 0 ? FullWallet.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                mo2102a(data.readInt(), data.readInt() != 0, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo2100a(int i, FullWallet fullWallet, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2101a(int i, MaskedWallet maskedWallet, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2102a(int i, boolean z, Bundle bundle) throws RemoteException;
            }
