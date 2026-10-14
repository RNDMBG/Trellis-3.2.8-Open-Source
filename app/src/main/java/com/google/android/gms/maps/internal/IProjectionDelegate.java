            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.model.LatLng;
            import com.google.android.gms.maps.model.VisibleRegion;
            
            
            public interface IProjectionDelegate extends IInterface {

                
                public static abstract class AbstractBinderC1155a extends Binder implements IProjectionDelegate {

                    
                    private static class a implements IProjectionDelegate {

                        
                        private IBinder f2263dG;

                        a(IBinder iBinder) {
                            this.f2263dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2263dG;
                        }

                        @Override 
                        public LatLng fromScreenLocation(InterfaceC0904b point) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                                parcelObtain.writeStrongBinder(point != null ? point.asBinder() : null);
                                this.f2263dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                LatLng latLngCreateFromParcel = parcelObtain2.readInt() != 0 ? LatLng.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return latLngCreateFromParcel;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public VisibleRegion getVisibleRegion() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                                this.f2263dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                VisibleRegion visibleRegionCreateFromParcel = parcelObtain2.readInt() != 0 ? VisibleRegion.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return visibleRegionCreateFromParcel;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public InterfaceC0904b toScreenLocation(LatLng location) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                                if (location != null) {
                                    parcelObtain.writeInt(1);
                                    location.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2263dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                    
                    public static IProjectionDelegate m2232Z(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IProjectionDelegate)) ? new a(iBinder) : (IProjectionDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                                LatLng latLngFromScreenLocation = fromScreenLocation(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                if (latLngFromScreenLocation != null) {
                                    reply.writeInt(1);
                                    latLngFromScreenLocation.writeToParcel(reply, 1);
                                } else {
                                    reply.writeInt(0);
                                }
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                                InterfaceC0904b screenLocation = toScreenLocation(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(screenLocation != null ? screenLocation.asBinder() : null);
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                                VisibleRegion visibleRegion = getVisibleRegion();
                                reply.writeNoException();
                                if (visibleRegion != null) {
                                    reply.writeInt(1);
                                    visibleRegion.writeToParcel(reply, 1);
                                } else {
                                    reply.writeInt(0);
                                }
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IProjectionDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                LatLng fromScreenLocation(InterfaceC0904b interfaceC0904b) throws RemoteException;

                VisibleRegion getVisibleRegion() throws RemoteException;

                InterfaceC0904b toScreenLocation(LatLng latLng) throws RemoteException;
            }
