            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC1013cy extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1013cy {

                    
                    private static class C1327a implements InterfaceC1013cy {

                        
                        private IBinder f1647dG;

                        C1327a(IBinder iBinder) {
                            this.f1647dG = iBinder;
                        }

                        @Override 
                        
                        public void mo1506a(InterfaceC1012cx interfaceC1012cx) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                this.f1647dG.transact(5005, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1507a(InterfaceC1012cx interfaceC1012cx, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                parcelObtain.writeInt(i);
                                this.f1647dG.transact(5004, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1508a(InterfaceC1012cx interfaceC1012cx, int i, String str, byte[] bArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                parcelObtain.writeByteArray(bArr);
                                this.f1647dG.transact(5006, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1509a(InterfaceC1012cx interfaceC1012cx, int i, byte[] bArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeByteArray(bArr);
                                this.f1647dG.transact(5003, parcelObtain, parcelObtain2, 0);
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
                            return this.f1647dG;
                        }

                        @Override 
                        
                        public void mo1510b(InterfaceC1012cx interfaceC1012cx) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                this.f1647dG.transact(5008, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1511b(InterfaceC1012cx interfaceC1012cx, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                parcelObtain.writeInt(i);
                                this.f1647dG.transact(5007, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1512c(InterfaceC1012cx interfaceC1012cx) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                parcelObtain.writeStrongBinder(interfaceC1012cx != null ? interfaceC1012cx.asBinder() : null);
                                this.f1647dG.transact(5009, parcelObtain, parcelObtain2, 0);
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
                        public int getMaxNumKeys() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                this.f1647dG.transact(5002, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public int getMaxStateSize() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                                this.f1647dG.transact(5001, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    
                    public static InterfaceC1013cy m1513t(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1013cy)) ? new C1327a(iBinder) : (InterfaceC1013cy) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 5001:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                int maxStateSize = getMaxStateSize();
                                reply.writeNoException();
                                reply.writeInt(maxStateSize);
                                return true;
                            case 5002:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                int maxNumKeys = getMaxNumKeys();
                                reply.writeNoException();
                                reply.writeInt(maxNumKeys);
                                return true;
                            case 5003:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1509a(InterfaceC1012cx.a.m1505s(data.readStrongBinder()), data.readInt(), data.createByteArray());
                                reply.writeNoException();
                                return true;
                            case 5004:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1507a(InterfaceC1012cx.a.m1505s(data.readStrongBinder()), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 5005:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1506a(InterfaceC1012cx.a.m1505s(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 5006:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1508a(InterfaceC1012cx.a.m1505s(data.readStrongBinder()), data.readInt(), data.readString(), data.createByteArray());
                                reply.writeNoException();
                                return true;
                            case 5007:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1511b(InterfaceC1012cx.a.m1505s(data.readStrongBinder()), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 5008:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1510b(InterfaceC1012cx.a.m1505s(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 5009:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                                mo1512c(InterfaceC1012cx.a.m1505s(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.appstate.internal.IAppStateService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                void mo1506a(InterfaceC1012cx interfaceC1012cx) throws RemoteException;

                
                void mo1507a(InterfaceC1012cx interfaceC1012cx, int i) throws RemoteException;

                
                void mo1508a(InterfaceC1012cx interfaceC1012cx, int i, String str, byte[] bArr) throws RemoteException;

                
                void mo1509a(InterfaceC1012cx interfaceC1012cx, int i, byte[] bArr) throws RemoteException;

                
                void mo1510b(InterfaceC1012cx interfaceC1012cx) throws RemoteException;

                
                void mo1511b(InterfaceC1012cx interfaceC1012cx, int i) throws RemoteException;

                
                void mo1512c(InterfaceC1012cx interfaceC1012cx) throws RemoteException;

                int getMaxNumKeys() throws RemoteException;

                int getMaxStateSize() throws RemoteException;
            }
