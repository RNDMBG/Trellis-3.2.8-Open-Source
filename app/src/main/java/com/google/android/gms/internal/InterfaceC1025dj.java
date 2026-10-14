            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC1025dj extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1025dj {

                    
                    private static class C1331a implements InterfaceC1025dj {

                        
                        private IBinder f1715dG;

                        C1331a(IBinder iBinder) {
                            this.f1715dG = iBinder;
                        }

                        @Override 
                        
                        public void mo1581a(InterfaceC1024di interfaceC1024di, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                this.f1715dG.transact(4, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1582a(InterfaceC1024di interfaceC1024di, int i, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                this.f1715dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1583a(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1584a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeStringArray(strArr);
                                this.f1715dG.transact(10, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1585a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr, String str3, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeStringArray(strArr);
                                parcelObtain.writeString(str3);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1586a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr, String str3, IBinder iBinder, String str4, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeStringArray(strArr);
                                parcelObtain.writeString(str3);
                                parcelObtain.writeStrongBinder(iBinder);
                                parcelObtain.writeString(str4);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(9, parcelObtain, parcelObtain2, 0);
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
                            return this.f1715dG;
                        }

                        @Override 
                        
                        public void mo1587b(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1588c(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1589d(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1590e(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1591f(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(11, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1592g(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(12, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1593h(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(13, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1594i(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(14, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo1595j(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                                parcelObtain.writeStrongBinder(interfaceC1024di != null ? interfaceC1024di.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1715dG.transact(15, parcelObtain, parcelObtain2, 0);
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

                    
                    public static InterfaceC1025dj m1596w(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1025dj)) ? new C1331a(iBinder) : (InterfaceC1025dj) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1585a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1583a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1582a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString());
                                reply.writeNoException();
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1581a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1587b(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1588c(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 7:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1589d(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 8:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1590e(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 9:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1586a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray(), data.readString(), data.readStrongBinder(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 10:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1584a(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray());
                                reply.writeNoException();
                                return true;
                            case 11:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1591f(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 12:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1592g(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 13:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1593h(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 14:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1594i(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 15:
                                data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                                mo1595j(InterfaceC1024di.a.m1580v(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.common.internal.IGmsServiceBroker");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                
                void mo1581a(InterfaceC1024di interfaceC1024di, int i) throws RemoteException;

                
                void mo1582a(InterfaceC1024di interfaceC1024di, int i, String str) throws RemoteException;

                
                void mo1583a(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1584a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr) throws RemoteException;

                
                void mo1585a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr, String str3, Bundle bundle) throws RemoteException;

                
                void mo1586a(InterfaceC1024di interfaceC1024di, int i, String str, String str2, String[] strArr, String str3, IBinder iBinder, String str4, Bundle bundle) throws RemoteException;

                
                void mo1587b(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1588c(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1589d(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1590e(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1591f(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1592g(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1593h(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1594i(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;

                
                void mo1595j(InterfaceC1024di interfaceC1024di, int i, String str, Bundle bundle) throws RemoteException;
            }
