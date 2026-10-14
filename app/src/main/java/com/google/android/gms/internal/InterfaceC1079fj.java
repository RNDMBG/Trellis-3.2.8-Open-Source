            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC1079fj extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1079fj {

                    
                    private static class C1341a implements InterfaceC1079fj {

                        
                        private IBinder f1914dG;

                        C1341a(IBinder iBinder) {
                            this.f1914dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f1914dG;
                        }

                        @Override 
                        public void cancelClick() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                this.f1914dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        public PendingIntent getResolution() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                this.f1914dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                PendingIntent pendingIntent = parcelObtain2.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return pendingIntent;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public void reinitialize() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                this.f1914dG.transact(3, parcelObtain, parcelObtain2, 0);
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

                    
                    public static InterfaceC1079fj m1958ap(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1079fj)) ? new C1341a(iBinder) : (InterfaceC1079fj) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                PendingIntent resolution = getResolution();
                                reply.writeNoException();
                                if (resolution == null) {
                                    reply.writeInt(0);
                                    return true;
                                }
                                reply.writeInt(1);
                                resolution.writeToParcel(reply, 1);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                cancelClick();
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                reinitialize();
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.plus.internal.IPlusOneDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void cancelClick() throws RemoteException;

                PendingIntent getResolution() throws RemoteException;

                void reinitialize() throws RemoteException;
            }
