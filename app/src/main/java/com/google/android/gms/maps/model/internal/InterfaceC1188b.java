            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.LatLng;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1188b extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.b$a */
                public static abstract class a extends Binder implements InterfaceC1188b {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.b$a$a, reason: collision with other inner class name */
                    private static class C1364a implements InterfaceC1188b {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2365dG;

                        C1364a(IBinder iBinder) {
                            this.f2365dG = iBinder;
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        /* JADX INFO: renamed from: a */
                        public boolean mo2313a(InterfaceC1188b interfaceC1188b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeStrongBinder(interfaceC1188b != null ? interfaceC1188b.asBinder() : null);
                                this.f2365dG.transact(17, parcelObtain, parcelObtain2, 0);
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

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2365dG;
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public LatLng getCenter() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(4, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public int getFillColor() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(12, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public String getId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public double getRadius() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                double d = parcelObtain2.readDouble();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return d;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public int getStrokeColor() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(10, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public float getStrokeWidth() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(8, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public float getZIndex() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(14, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public int hashCodeRemote() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(18, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public boolean isVisible() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(16, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void remove() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                this.f2365dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setCenter(LatLng center) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                if (center != null) {
                                    parcelObtain.writeInt(1);
                                    center.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2365dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setFillColor(int color) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeInt(color);
                                this.f2365dG.transact(11, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setRadius(double radius) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeDouble(radius);
                                this.f2365dG.transact(5, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setStrokeColor(int color) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeInt(color);
                                this.f2365dG.transact(9, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setStrokeWidth(float width) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeFloat(width);
                                this.f2365dG.transact(7, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setVisible(boolean visible) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeInt(visible ? 1 : 0);
                                this.f2365dG.transact(15, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1188b
                        public void setZIndex(float zIndex) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                parcelObtain.writeFloat(zIndex);
                                this.f2365dG.transact(13, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: ad */
                    public static InterfaceC1188b m2314ad(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1188b)) ? new C1364a(iBinder) : (InterfaceC1188b) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                remove();
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setCenter(parcel.readInt() != 0 ? LatLng.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                LatLng center = getCenter();
                                parcel2.writeNoException();
                                if (center == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                center.writeToParcel(parcel2, 1);
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setRadius(parcel.readDouble());
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                double radius = getRadius();
                                parcel2.writeNoException();
                                parcel2.writeDouble(radius);
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setStrokeWidth(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                float strokeWidth = getStrokeWidth();
                                parcel2.writeNoException();
                                parcel2.writeFloat(strokeWidth);
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setStrokeColor(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                int strokeColor = getStrokeColor();
                                parcel2.writeNoException();
                                parcel2.writeInt(strokeColor);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setFillColor(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                int fillColor = getFillColor();
                                parcel2.writeNoException();
                                parcel2.writeInt(fillColor);
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setZIndex(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                float zIndex = getZIndex();
                                parcel2.writeNoException();
                                parcel2.writeFloat(zIndex);
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                setVisible(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 16:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                boolean zIsVisible = isVisible();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsVisible ? 1 : 0);
                                return true;
                            case 17:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                boolean zA = mo2313a(m2314ad(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                parcel2.writeInt(zA ? 1 : 0);
                                return true;
                            case 18:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                int iHashCodeRemote = hashCodeRemote();
                                parcel2.writeNoException();
                                parcel2.writeInt(iHashCodeRemote);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.model.internal.ICircleDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                boolean mo2313a(InterfaceC1188b interfaceC1188b) throws RemoteException;

                LatLng getCenter() throws RemoteException;

                int getFillColor() throws RemoteException;

                String getId() throws RemoteException;

                double getRadius() throws RemoteException;

                int getStrokeColor() throws RemoteException;

                float getStrokeWidth() throws RemoteException;

                float getZIndex() throws RemoteException;

                int hashCodeRemote() throws RemoteException;

                boolean isVisible() throws RemoteException;

                void remove() throws RemoteException;

                void setCenter(LatLng latLng) throws RemoteException;

                void setFillColor(int i) throws RemoteException;

                void setRadius(double d) throws RemoteException;

                void setStrokeColor(int i) throws RemoteException;

                void setStrokeWidth(float f) throws RemoteException;

                void setVisible(boolean z) throws RemoteException;

                void setZIndex(float f) throws RemoteException;
            }
