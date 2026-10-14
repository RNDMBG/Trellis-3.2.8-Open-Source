            package com.google.android.gms.maps.model.internal;

            import android.graphics.Bitmap;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public interface InterfaceC1187a extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1187a {

                    
                    private static class C1363a implements InterfaceC1187a {

                        
                        private IBinder f2364dG;

                        C1363a(IBinder iBinder) {
                            this.f2364dG = iBinder;
                        }

                        @Override 
                        
                        public InterfaceC0904b mo2305S(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                parcelObtain.writeString(str);
                                this.f2364dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2306T(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                parcelObtain.writeString(str);
                                this.f2364dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2307U(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                parcelObtain.writeString(str);
                                this.f2364dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2308a(Bitmap bitmap) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                if (bitmap != null) {
                                    parcelObtain.writeInt(1);
                                    bitmap.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2364dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2309ad(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                parcelObtain.writeInt(i);
                                this.f2364dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        public IBinder asBinder() {
                            return this.f2364dG;
                        }

                        @Override 
                        
                        public InterfaceC0904b mo2310c(float f) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                parcelObtain.writeFloat(f);
                                this.f2364dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo2311cQ() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                this.f2364dG.transact(4, parcelObtain, parcelObtain2, 0);
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

                    
                    public static InterfaceC1187a m2312ac(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1187a)) ? new C1363a(iBinder) : (InterfaceC1187a) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bAd = mo2309ad(data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bAd != null ? interfaceC0904bAd.asBinder() : null);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bS = mo2305S(data.readString());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bS != null ? interfaceC0904bS.asBinder() : null);
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bT = mo2306T(data.readString());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bT != null ? interfaceC0904bT.asBinder() : null);
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bCQ = mo2311cQ();
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bCQ != null ? interfaceC0904bCQ.asBinder() : null);
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bC = mo2310c(data.readFloat());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bC != null ? interfaceC0904bC.asBinder() : null);
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bA = mo2308a(data.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bA != null ? interfaceC0904bA.asBinder() : null);
                                return true;
                            case 7:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                InterfaceC0904b interfaceC0904bU = mo2307U(data.readString());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bU != null ? interfaceC0904bU.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                InterfaceC0904b mo2305S(String str) throws RemoteException;

                
                InterfaceC0904b mo2306T(String str) throws RemoteException;

                
                InterfaceC0904b mo2307U(String str) throws RemoteException;

                
                InterfaceC0904b mo2308a(Bitmap bitmap) throws RemoteException;

                
                InterfaceC0904b mo2309ad(int i) throws RemoteException;

                
                InterfaceC0904b mo2310c(float f) throws RemoteException;

                
                InterfaceC0904b mo2311cQ() throws RemoteException;
            }
