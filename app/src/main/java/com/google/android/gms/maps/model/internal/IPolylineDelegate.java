            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.LatLng;
            import java.util.ArrayList;
            import java.util.List;
            
            
            public interface IPolylineDelegate extends IInterface {

                
                public static abstract class AbstractBinderC1186a extends Binder implements IPolylineDelegate {

                    
                    private static class a implements IPolylineDelegate {

                        
                        private IBinder f2363dG;

                        a(IBinder iBinder) {
                            this.f2363dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2363dG;
                        }

                        @Override 
                        public boolean equalsRemote(IPolylineDelegate other) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeStrongBinder(other != null ? other.asBinder() : null);
                                this.f2363dG.transact(15, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                boolean z = parcelObtain2.readInt() != 0;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public int getColor() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                        public String getId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public List<LatLng> getPoints() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                ArrayList arrayListCreateTypedArrayList = parcelObtain2.createTypedArrayList(LatLng.CREATOR);
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return arrayListCreateTypedArrayList;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public float getWidth() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                float f = parcelObtain2.readFloat();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return f;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public float getZIndex() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(10, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                float f = parcelObtain2.readFloat();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return f;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public int hashCodeRemote() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(16, parcelObtain, parcelObtain2, 0);
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
                        public boolean isGeodesic() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(14, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                boolean z = parcelObtain2.readInt() != 0;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public boolean isVisible() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(12, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                boolean z = parcelObtain2.readInt() != 0;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public void remove() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                this.f2363dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        public void setColor(int color) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeInt(color);
                                this.f2363dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                        public void setGeodesic(boolean geodesic) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeInt(geodesic ? 1 : 0);
                                this.f2363dG.transact(13, parcelObtain, parcelObtain2, 0);
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
                        public void setPoints(List<LatLng> points) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeTypedList(points);
                                this.f2363dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        public void setVisible(boolean visible) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeInt(visible ? 1 : 0);
                                this.f2363dG.transact(11, parcelObtain, parcelObtain2, 0);
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
                        public void setWidth(float width) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeFloat(width);
                                this.f2363dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        public void setZIndex(float zIndex) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                parcelObtain.writeFloat(zIndex);
                                this.f2363dG.transact(9, parcelObtain, parcelObtain2, 0);
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

                    
                    public static IPolylineDelegate m2304ah(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPolylineDelegate)) ? new a(iBinder) : (IPolylineDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                remove();
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setPoints(parcel.createTypedArrayList(LatLng.CREATOR));
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                List<LatLng> points = getPoints();
                                parcel2.writeNoException();
                                parcel2.writeTypedList(points);
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setWidth(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                float width = getWidth();
                                parcel2.writeNoException();
                                parcel2.writeFloat(width);
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setColor(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                int color = getColor();
                                parcel2.writeNoException();
                                parcel2.writeInt(color);
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setZIndex(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                float zIndex = getZIndex();
                                parcel2.writeNoException();
                                parcel2.writeFloat(zIndex);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setVisible(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                boolean zIsVisible = isVisible();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsVisible ? 1 : 0);
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                setGeodesic(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                boolean zIsGeodesic = isGeodesic();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsGeodesic ? 1 : 0);
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                boolean zEqualsRemote = equalsRemote(m2304ah(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                parcel2.writeInt(zEqualsRemote ? 1 : 0);
                                return true;
                            case 16:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                int iHashCodeRemote = hashCodeRemote();
                                parcel2.writeNoException();
                                parcel2.writeInt(iHashCodeRemote);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                boolean equalsRemote(IPolylineDelegate iPolylineDelegate) throws RemoteException;

                int getColor() throws RemoteException;

                String getId() throws RemoteException;

                List<LatLng> getPoints() throws RemoteException;

                float getWidth() throws RemoteException;

                float getZIndex() throws RemoteException;

                int hashCodeRemote() throws RemoteException;

                boolean isGeodesic() throws RemoteException;

                boolean isVisible() throws RemoteException;

                void remove() throws RemoteException;

                void setColor(int i) throws RemoteException;

                void setGeodesic(boolean z) throws RemoteException;

                void setPoints(List<LatLng> list) throws RemoteException;

                void setVisible(boolean z) throws RemoteException;

                void setWidth(float f) throws RemoteException;

                void setZIndex(float f) throws RemoteException;
            }
