            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            
            
            
            public interface InterfaceC1160d extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1160d {

                    
                    private static class C1351a implements InterfaceC1160d {

                        
                        private IBinder f2267dG;

                        C1351a(IBinder iBinder) {
                            this.f2267dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2267dG;
                        }

                        @Override 
                        
                        public InterfaceC0904b mo2199f(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2267dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2200g(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2267dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    }

                    
                    public static InterfaceC1160d m2244L(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1160d)) ? new C1351a(iBinder) : (InterfaceC1160d) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                                InterfaceC0904b interfaceC0904bF = mo2199f(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bF != null ? interfaceC0904bF.asBinder() : null);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                                InterfaceC0904b interfaceC0904bG = mo2200g(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bG != null ? interfaceC0904bG.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                InterfaceC0904b mo2199f(InterfaceC1190d interfaceC1190d) throws RemoteException;

                
                InterfaceC0904b mo2200g(InterfaceC1190d interfaceC1190d) throws RemoteException;
            }
