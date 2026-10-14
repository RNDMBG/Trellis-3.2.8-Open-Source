            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            
            
            
            public interface InterfaceC1162f extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1162f {

                    
                    private static class C1353a implements InterfaceC1162f {

                        
                        private IBinder f2269dG;

                        C1353a(IBinder iBinder) {
                            this.f2269dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2269dG;
                        }

                        @Override 
                        
                        public void mo2198e(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2269dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                    }

                    
                    public static InterfaceC1162f m2246Q(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1162f)) ? new C1353a(iBinder) : (InterfaceC1162f) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                                mo2198e(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                void mo2198e(InterfaceC1190d interfaceC1190d) throws RemoteException;
            }
