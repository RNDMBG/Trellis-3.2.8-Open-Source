            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            public interface ILocationSourceDelegate extends IInterface {

                
                public static abstract class AbstractBinderC1152a extends Binder implements ILocationSourceDelegate {

                    
                    private static class a implements ILocationSourceDelegate {

                        
                        private IBinder f2260dG;

                        a(IBinder iBinder) {
                            this.f2260dG = iBinder;
                        }

                        @Override 
                        public void activate(InterfaceC1163g listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2260dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                            return this.f2260dG;
                        }

                        @Override 
                        public void deactivate() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                                this.f2260dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                    public AbstractBinderC1152a() {
                        attachInterface(this, "com.google.android.gms.maps.internal.ILocationSourceDelegate");
                    }

                    
                    public static ILocationSourceDelegate m2229M(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILocationSourceDelegate)) ? new a(iBinder) : (ILocationSourceDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                                activate(InterfaceC1163g.a.m2248R(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                                deactivate();
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.ILocationSourceDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void activate(InterfaceC1163g interfaceC1163g) throws RemoteException;

                void deactivate() throws RemoteException;
            }
