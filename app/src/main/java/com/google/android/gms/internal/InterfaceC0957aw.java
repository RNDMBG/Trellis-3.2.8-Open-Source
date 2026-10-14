            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC0957aw extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC0957aw {

                    
                    private static class C1320a implements InterfaceC0957aw {

                        
                        private IBinder f1194dG;

                        C1320a(IBinder iBinder) {
                            this.f1194dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f1194dG;
                        }

                        @Override 
                        
                        public InterfaceC0958ax mo1280g(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                                parcelObtain.writeString(str);
                                this.f1194dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC0958ax interfaceC0958axM1284j = InterfaceC0958ax.a.m1284j(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC0958axM1284j;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                    }

                    
                    public static InterfaceC0957aw m1281i(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0957aw)) ? new C1320a(iBinder) : (InterfaceC0957aw) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                                InterfaceC0958ax interfaceC0958axG = mo1280g(data.readString());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0958axG != null ? interfaceC0958axG.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.mediation.client.IAdapterCreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                InterfaceC0958ax mo1280g(String str) throws RemoteException;
            }
