            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public interface InterfaceC0938ad extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC0938ad {

                    
                    private static class C1318a implements InterfaceC0938ad {

                        
                        private IBinder f1142dG;

                        C1318a(IBinder iBinder) {
                            this.f1142dG = iBinder;
                        }

                        @Override 
                        
                        public IBinder mo1245a(InterfaceC0904b interfaceC0904b, C1126x c1126x, String str, InterfaceC0957aw interfaceC0957aw, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                if (c1126x != null) {
                                    parcelObtain.writeInt(1);
                                    c1126x.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeString(str);
                                parcelObtain.writeStrongBinder(interfaceC0957aw != null ? interfaceC0957aw.asBinder() : null);
                                parcelObtain.writeInt(i);
                                this.f1142dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IBinder strongBinder = parcelObtain2.readStrongBinder();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return strongBinder;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f1142dG;
                        }
                    }

                    
                    public static InterfaceC0938ad m1246g(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0938ad)) ? new C1318a(iBinder) : (InterfaceC0938ad) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                                IBinder iBinderA = mo1245a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt() != 0 ? C1126x.CREATOR.m2182b(data) : null, data.readString(), InterfaceC0957aw.a.m1281i(data.readStrongBinder()), data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(iBinderA);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.client.IAdManagerCreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                IBinder mo1245a(InterfaceC0904b interfaceC0904b, C1126x c1126x, String str, InterfaceC0957aw interfaceC0957aw, int i) throws RemoteException;
            }
