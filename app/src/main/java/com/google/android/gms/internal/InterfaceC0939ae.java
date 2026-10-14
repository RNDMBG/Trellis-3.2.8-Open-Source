            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC0939ae extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC0939ae {

                    
                    private static class C1319a implements InterfaceC0939ae {

                        
                        private IBinder f1143dG;

                        C1319a(IBinder iBinder) {
                            this.f1143dG = iBinder;
                        }

                        @Override 
                        
                        public void mo1247a(String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAppEventListener");
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1143dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                            return this.f1143dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.ads.internal.client.IAppEventListener");
                    }

                    
                    public static InterfaceC0939ae m1248h(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAppEventListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0939ae)) ? new C1319a(iBinder) : (InterfaceC0939ae) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAppEventListener");
                                mo1247a(data.readString(), data.readString());
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.client.IAppEventListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                void mo1247a(String str, String str2) throws RemoteException;
            }
