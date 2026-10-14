            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.model.LatLng;
            
            
            
            public interface InterfaceC1190d extends IInterface {

                
                public static abstract class a extends Binder implements InterfaceC1190d {

                    
                    private static class C1366a implements InterfaceC1190d {

                        
                        private IBinder f2367dG;

                        C1366a(IBinder iBinder) {
                            this.f2367dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2367dG;
                        }

                        @Override 
                        public float getAlpha() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(26, parcelObtain, parcelObtain2, 0);
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
                        public String getId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        public LatLng getPosition() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(4, parcelObtain, parcelObtain2, 0);
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
                        public float getRotation() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(23, parcelObtain, parcelObtain2, 0);
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
                        public String getSnippet() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                        public String getTitle() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                        
                        public boolean mo2319h(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2367dG.transact(16, parcelObtain, parcelObtain2, 0);
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
                        public int hashCodeRemote() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(17, parcelObtain, parcelObtain2, 0);
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
                        public void hideInfoWindow() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(12, parcelObtain, parcelObtain2, 0);
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
                        
                        public void mo2320i(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f2367dG.transact(18, parcelObtain, parcelObtain2, 0);
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
                        public boolean isDraggable() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(10, parcelObtain, parcelObtain2, 0);
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
                        public boolean isFlat() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(21, parcelObtain, parcelObtain2, 0);
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
                        public boolean isInfoWindowShown() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(13, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(15, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        public void setAlpha(float alpha) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeFloat(alpha);
                                this.f2367dG.transact(25, parcelObtain, parcelObtain2, 0);
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
                        public void setAnchor(float anchorU, float anchorV) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeFloat(anchorU);
                                parcelObtain.writeFloat(anchorV);
                                this.f2367dG.transact(19, parcelObtain, parcelObtain2, 0);
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
                        public void setDraggable(boolean draggable) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeInt(draggable ? 1 : 0);
                                this.f2367dG.transact(9, parcelObtain, parcelObtain2, 0);
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
                        public void setFlat(boolean flat) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeInt(flat ? 1 : 0);
                                this.f2367dG.transact(20, parcelObtain, parcelObtain2, 0);
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
                        public void setInfoWindowAnchor(float anchorU, float anchorV) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeFloat(anchorU);
                                parcelObtain.writeFloat(anchorV);
                                this.f2367dG.transact(24, parcelObtain, parcelObtain2, 0);
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
                        public void setPosition(LatLng latlng) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                if (latlng != null) {
                                    parcelObtain.writeInt(1);
                                    latlng.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2367dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        public void setRotation(float rotation) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeFloat(rotation);
                                this.f2367dG.transact(22, parcelObtain, parcelObtain2, 0);
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
                        public void setSnippet(String snippet) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeString(snippet);
                                this.f2367dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                        public void setTitle(String title) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeString(title);
                                this.f2367dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                parcelObtain.writeInt(visible ? 1 : 0);
                                this.f2367dG.transact(14, parcelObtain, parcelObtain2, 0);
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
                        public void showInfoWindow() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                this.f2367dG.transact(11, parcelObtain, parcelObtain2, 0);
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

                    
                    public static InterfaceC1190d m2321af(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1190d)) ? new C1366a(iBinder) : (InterfaceC1190d) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                remove();
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setPosition(parcel.readInt() != 0 ? LatLng.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                LatLng position = getPosition();
                                parcel2.writeNoException();
                                if (position == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                position.writeToParcel(parcel2, 1);
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setTitle(parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                String title = getTitle();
                                parcel2.writeNoException();
                                parcel2.writeString(title);
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setSnippet(parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                String snippet = getSnippet();
                                parcel2.writeNoException();
                                parcel2.writeString(snippet);
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setDraggable(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                boolean zIsDraggable = isDraggable();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsDraggable ? 1 : 0);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                showInfoWindow();
                                parcel2.writeNoException();
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                hideInfoWindow();
                                parcel2.writeNoException();
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                boolean zIsInfoWindowShown = isInfoWindowShown();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsInfoWindowShown ? 1 : 0);
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setVisible(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                boolean zIsVisible = isVisible();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsVisible ? 1 : 0);
                                return true;
                            case 16:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                boolean zH = mo2319h(m2321af(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                parcel2.writeInt(zH ? 1 : 0);
                                return true;
                            case 17:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                int iHashCodeRemote = hashCodeRemote();
                                parcel2.writeNoException();
                                parcel2.writeInt(iHashCodeRemote);
                                return true;
                            case 18:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                mo2320i(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 19:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setAnchor(parcel.readFloat(), parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 20:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setFlat(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 21:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                boolean zIsFlat = isFlat();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsFlat ? 1 : 0);
                                return true;
                            case 22:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setRotation(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 23:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                float rotation = getRotation();
                                parcel2.writeNoException();
                                parcel2.writeFloat(rotation);
                                return true;
                            case 24:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setInfoWindowAnchor(parcel.readFloat(), parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 25:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                setAlpha(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 26:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                float alpha = getAlpha();
                                parcel2.writeNoException();
                                parcel2.writeFloat(alpha);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                float getAlpha() throws RemoteException;

                String getId() throws RemoteException;

                LatLng getPosition() throws RemoteException;

                float getRotation() throws RemoteException;

                String getSnippet() throws RemoteException;

                String getTitle() throws RemoteException;

                
                boolean mo2319h(InterfaceC1190d interfaceC1190d) throws RemoteException;

                int hashCodeRemote() throws RemoteException;

                void hideInfoWindow() throws RemoteException;

                
                void mo2320i(InterfaceC0904b interfaceC0904b) throws RemoteException;

                boolean isDraggable() throws RemoteException;

                boolean isFlat() throws RemoteException;

                boolean isInfoWindowShown() throws RemoteException;

                boolean isVisible() throws RemoteException;

                void remove() throws RemoteException;

                void setAlpha(float f) throws RemoteException;

                void setAnchor(float f, float f2) throws RemoteException;

                void setDraggable(boolean z) throws RemoteException;

                void setFlat(boolean z) throws RemoteException;

                void setInfoWindowAnchor(float f, float f2) throws RemoteException;

                void setPosition(LatLng latLng) throws RemoteException;

                void setRotation(float f) throws RemoteException;

                void setSnippet(String str) throws RemoteException;

                void setTitle(String str) throws RemoteException;

                void setVisible(boolean z) throws RemoteException;

                void showInfoWindow() throws RemoteException;
            }
