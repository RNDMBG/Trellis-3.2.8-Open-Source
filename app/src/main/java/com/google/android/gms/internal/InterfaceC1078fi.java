            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            
            public interface InterfaceC1078fi extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1078fi {

                    
                    private static class C1340a implements InterfaceC1078fi {

                        
                        private IBinder f1913dG;

                        C1340a(IBinder iBinder) {
                            this.f1913dG = iBinder;
                        }

                        @Override 
                        
                        public InterfaceC0904b mo1955a(InterfaceC0904b interfaceC0904b, int i, int i2, String str, int i3) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i3);
                                this.f1913dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        
                        public InterfaceC0904b mo1956a(InterfaceC0904b interfaceC0904b, int i, int i2, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1913dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                            return this.f1913dG;
                        }
                    }

                    
                    public static InterfaceC1078fi m1957ao(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1078fi)) ? new C1340a(iBinder) : (InterfaceC1078fi) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                                InterfaceC0904b interfaceC0904bA = mo1955a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt(), data.readInt(), data.readString(), data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bA != null ? interfaceC0904bA.asBinder() : null);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                                InterfaceC0904b interfaceC0904bA2 = mo1956a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt(), data.readInt(), data.readString(), data.readString());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bA2 != null ? interfaceC0904bA2.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                InterfaceC0904b mo1955a(InterfaceC0904b interfaceC0904b, int i, int i2, String str, int i3) throws RemoteException;

                
                InterfaceC0904b mo1956a(InterfaceC0904b interfaceC0904b, int i, int i2, String str, String str2) throws RemoteException;
            }
