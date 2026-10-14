            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.common.data.C0891d;
            
            
            
            public interface InterfaceC1012cx extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1012cx {

                    
                    private static class C1326a implements InterfaceC1012cx {

                        
                        private IBinder f1646dG;

                        C1326a(IBinder iBinder) {
                            this.f1646dG = iBinder;
                        }

                        @Override 
                        
                        public void mo1496a(int i, C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(i);
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1646dG.transact(5001, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1497a(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1646dG.transact(5002, parcelObtain, parcelObtain2, 0);
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
                            return this.f1646dG;
                        }

                        @Override 
                        
                        public void mo1498m(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1646dG.transact(5005, parcelObtain, parcelObtain2, 0);
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
                        public void onSignOutComplete() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                this.f1646dG.transact(5004, parcelObtain, parcelObtain2, 0);
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
                        public void onStateDeleted(int statusCode, int stateKey) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeInt(stateKey);
                                this.f1646dG.transact(5003, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    }

                    
                    public static InterfaceC1012cx m1505s(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1012cx)) ? new C1326a(iBinder) : (InterfaceC1012cx) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 5001:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1496a(data.readInt(), data.readInt() != 0 ? C0891d.CREATOR.m1054h(data) : null);
                                reply.writeNoException();
                                return true;
                            case 5002:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1497a(data.readInt() != 0 ? C0891d.CREATOR.m1054h(data) : null);
                                reply.writeNoException();
                                return true;
                            case 5003:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                onStateDeleted(data.readInt(), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 5004:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                onSignOutComplete();
                                reply.writeNoException();
                                return true;
                            case 5005:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1498m(data.readInt());
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                void mo1496a(int i, C0891d c0891d) throws RemoteException;

                
                void mo1497a(C0891d c0891d) throws RemoteException;

                
                void mo1498m(int i) throws RemoteException;

                void onSignOutComplete() throws RemoteException;

                void onStateDeleted(int i, int i2) throws RemoteException;
            }
