            package com.google.android.gms.dynamic;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            
            
            public interface InterfaceC0904b extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC0904b {

                    
                    private static class C1315a implements InterfaceC0904b {

                        
                        private IBinder f1026dG;

                        C1315a(IBinder iBinder) {
                            this.f1026dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f1026dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.dynamic.IObjectWrapper");
                    }

                    
                    public static InterfaceC0904b m1164z(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0904b)) ? new C1315a(iBinder) : (InterfaceC0904b) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1598968902:
                                reply.writeString("com.google.android.gms.dynamic.IObjectWrapper");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }
            }
