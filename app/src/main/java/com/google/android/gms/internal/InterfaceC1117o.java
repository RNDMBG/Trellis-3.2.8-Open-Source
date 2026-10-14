            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC1117o extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1117o {

                    
                    private static class C1346a implements InterfaceC1117o {

                        
                        private IBinder f2137dG;

                        C1346a(IBinder iBinder) {
                            this.f2137dG = iBinder;
                        }

                        @Override 
                        
                        public Bundle mo2148a(String str, String str2, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2137dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                Bundle bundle2 = parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return bundle2;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2137dG;
                        }
                    }

                    
                    public static InterfaceC1117o m2149a(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.auth.IAuthManagerService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1117o)) ? new C1346a(iBinder) : (InterfaceC1117o) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.auth.IAuthManagerService");
                                Bundle bundleA = mo2148a(data.readString(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                if (bundleA != null) {
                                    reply.writeInt(1);
                                    bundleA.writeToParcel(reply, 1);
                                } else {
                                    reply.writeInt(0);
                                }
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.auth.IAuthManagerService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                Bundle mo2148a(String str, String str2, Bundle bundle) throws RemoteException;
            }
