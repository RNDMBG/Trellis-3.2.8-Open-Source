            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.wallet.FullWallet;
            import com.google.android.gms.wallet.MaskedWallet;
            
            
            
            public interface InterfaceC1105gi extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1105gi {

                    
                    private static class C1345a implements InterfaceC1105gi {

                        
                        private IBinder f2104dG;

                        C1345a(IBinder iBinder) {
                            this.f2104dG = iBinder;
                        }

                        @Override 
                        
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

                        @Override 
                        
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

                        @Override 
                        
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

                        @Override 
                        public IBinder asBinder() {
                            return this.f2104dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                    }

                    
                    public static InterfaceC1105gi m2103at(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletServiceCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1105gi)) ? new C1345a(iBinder) : (InterfaceC1105gi) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
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

                
                void mo2100a(int i, FullWallet fullWallet, Bundle bundle) throws RemoteException;

                
                void mo2101a(int i, MaskedWallet maskedWallet, Bundle bundle) throws RemoteException;

                
                void mo2102a(int i, boolean z, Bundle bundle) throws RemoteException;
            }
