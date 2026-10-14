            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.LatLng;
            import java.util.ArrayList;
            import java.util.List;
            
            
            
            public interface InterfaceC1191e extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1191e {

                    
                    private static class C1367a implements InterfaceC1191e {

                        
                        private IBinder f2368dG;

                        C1367a(IBinder iBinder) {
                            this.f2368dG = iBinder;
                        }

                        @Override 
                        
                        public boolean mo2322a(InterfaceC1191e interfaceC1191e) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeStrongBinder(interfaceC1191e != null ? interfaceC1191e.asBinder() : null);
                                this.f2368dG.transact(19, parcelObtain, parcelObtain2, 0);
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
                        public IBinder asBinder() {
                            return this.f2368dG;
                        }

                        @Override 
                        public int getFillColor() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(12, parcelObtain, parcelObtain2, 0);
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
                        public List getHoles() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                ArrayList arrayList = parcelObtain2.readArrayList(getClass().getClassLoader());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return arrayList;
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(4, parcelObtain, parcelObtain2, 0);
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
                        public int getStrokeColor() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(10, parcelObtain, parcelObtain2, 0);
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
                        public float getStrokeWidth() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(14, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(20, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(18, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(16, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                this.f2368dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        public void setFillColor(int color) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeInt(color);
                                this.f2368dG.transact(11, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeInt(geodesic ? 1 : 0);
                                this.f2368dG.transact(17, parcelObtain, parcelObtain2, 0);
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
                        public void setHoles(List holes) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeList(holes);
                                this.f2368dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeTypedList(points);
                                this.f2368dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        public void setStrokeColor(int color) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeInt(color);
                                this.f2368dG.transact(9, parcelObtain, parcelObtain2, 0);
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
                        public void setStrokeWidth(float width) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeFloat(width);
                                this.f2368dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeInt(visible ? 1 : 0);
                                this.f2368dG.transact(15, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                parcelObtain.writeFloat(zIndex);
                                this.f2368dG.transact(13, parcelObtain, parcelObtain2, 0);
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

                    
                    public static InterfaceC1191e m2323ag(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1191e)) ? new C1367a(iBinder) : (InterfaceC1191e) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                remove();
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setPoints(parcel.createTypedArrayList(LatLng.CREATOR));
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                List<LatLng> points = getPoints();
                                parcel2.writeNoException();
                                parcel2.writeTypedList(points);
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setHoles(parcel.readArrayList(getClass().getClassLoader()));
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                List holes = getHoles();
                                parcel2.writeNoException();
                                parcel2.writeList(holes);
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setStrokeWidth(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                float strokeWidth = getStrokeWidth();
                                parcel2.writeNoException();
                                parcel2.writeFloat(strokeWidth);
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setStrokeColor(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                int strokeColor = getStrokeColor();
                                parcel2.writeNoException();
                                parcel2.writeInt(strokeColor);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setFillColor(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                int fillColor = getFillColor();
                                parcel2.writeNoException();
                                parcel2.writeInt(fillColor);
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setZIndex(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                float zIndex = getZIndex();
                                parcel2.writeNoException();
                                parcel2.writeFloat(zIndex);
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setVisible(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 16:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                boolean zIsVisible = isVisible();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsVisible ? 1 : 0);
                                return true;
                            case 17:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                setGeodesic(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 18:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                boolean zIsGeodesic = isGeodesic();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsGeodesic ? 1 : 0);
                                return true;
                            case 19:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                boolean zA = mo2322a(m2323ag(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                parcel2.writeInt(zA ? 1 : 0);
                                return true;
                            case 20:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                int iHashCodeRemote = hashCodeRemote();
                                parcel2.writeNoException();
                                parcel2.writeInt(iHashCodeRemote);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                
                boolean mo2322a(InterfaceC1191e interfaceC1191e) throws RemoteException;

                int getFillColor() throws RemoteException;

                List getHoles() throws RemoteException;

                String getId() throws RemoteException;

                List<LatLng> getPoints() throws RemoteException;

                int getStrokeColor() throws RemoteException;

                float getStrokeWidth() throws RemoteException;

                float getZIndex() throws RemoteException;

                int hashCodeRemote() throws RemoteException;

                boolean isGeodesic() throws RemoteException;

                boolean isVisible() throws RemoteException;

                void remove() throws RemoteException;

                void setFillColor(int i) throws RemoteException;

                void setGeodesic(boolean z) throws RemoteException;

                void setHoles(List list) throws RemoteException;

                void setPoints(List<LatLng> list) throws RemoteException;

                void setStrokeColor(int i) throws RemoteException;

                void setStrokeWidth(float f) throws RemoteException;

                void setVisible(boolean z) throws RemoteException;

                void setZIndex(float f) throws RemoteException;
            }
