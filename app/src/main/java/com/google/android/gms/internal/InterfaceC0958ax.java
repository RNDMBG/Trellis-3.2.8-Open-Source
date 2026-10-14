            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public interface InterfaceC0958ax extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC0958ax {

                    
                    private static class C1321a implements InterfaceC0958ax {

                        
                        private IBinder f1195dG;

                        C1321a(IBinder iBinder) {
                            this.f1195dG = iBinder;
                        }

                        @Override 
                        
                        public void mo1282a(InterfaceC0904b interfaceC0904b, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                if (c1124v != null) {
                                    parcelObtain.writeInt(1);
                                    c1124v.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeString(str);
                                parcelObtain.writeStrongBinder(interfaceC0959ay != null ? interfaceC0959ay.asBinder() : null);
                                this.f1195dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1283a(InterfaceC0904b interfaceC0904b, C1126x c1126x, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                if (c1126x != null) {
                                    parcelObtain.writeInt(1);
                                    c1126x.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (c1124v != null) {
                                    parcelObtain.writeInt(1);
                                    c1124v.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeString(str);
                                parcelObtain.writeStrongBinder(interfaceC0959ay != null ? interfaceC0959ay.asBinder() : null);
                                this.f1195dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                            return this.f1195dG;
                        }

                        @Override 
                        public void destroy() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                this.f1195dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        public InterfaceC0904b getView() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                this.f1195dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                        @Override 
                        public void showInterstitial() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                this.f1195dG.transact(4, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                    }

                    
                    public static InterfaceC0958ax m1284j(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0958ax)) ? new C1321a(iBinder) : (InterfaceC0958ax) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                mo1283a(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()), parcel.readInt() != 0 ? C1126x.CREATOR.m2182b(parcel) : null, parcel.readInt() != 0 ? C1124v.CREATOR.m2176a(parcel) : null, parcel.readString(), InterfaceC0959ay.a.m1285k(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                InterfaceC0904b view = getView();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(view != null ? view.asBinder() : null);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                mo1282a(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()), parcel.readInt() != 0 ? C1124v.CREATOR.m2176a(parcel) : null, parcel.readString(), InterfaceC0959ay.a.m1285k(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                showInterstitial();
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                destroy();
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.ads.internal.mediation.client.IMediationAdapter");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                
                void mo1282a(InterfaceC0904b interfaceC0904b, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException;

                
                void mo1283a(InterfaceC0904b interfaceC0904b, C1126x c1126x, C1124v c1124v, String str, InterfaceC0959ay interfaceC0959ay) throws RemoteException;

                void destroy() throws RemoteException;

                InterfaceC0904b getView() throws RemoteException;

                void showInterstitial() throws RemoteException;
            }
