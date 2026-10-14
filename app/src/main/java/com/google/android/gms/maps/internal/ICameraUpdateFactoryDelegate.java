            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.model.CameraPosition;
            import com.google.android.gms.maps.model.LatLng;
            import com.google.android.gms.maps.model.LatLngBounds;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ICameraUpdateFactoryDelegate extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate$a */
                public static abstract class AbstractBinderC1150a extends Binder implements ICameraUpdateFactoryDelegate {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate$a$a */
                    private static class a implements ICameraUpdateFactoryDelegate {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2258dG;

                        a(IBinder iBinder) {
                            this.f2258dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2258dG;
                        }

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b newCameraPosition(CameraPosition cameraPosition) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                if (cameraPosition != null) {
                                    parcelObtain.writeInt(1);
                                    cameraPosition.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2258dG.transact(7, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b newLatLng(LatLng latLng) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                if (latLng != null) {
                                    parcelObtain.writeInt(1);
                                    latLng.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2258dG.transact(8, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b newLatLngBounds(LatLngBounds bounds, int padding) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                if (bounds != null) {
                                    parcelObtain.writeInt(1);
                                    bounds.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(padding);
                                this.f2258dG.transact(10, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b newLatLngBoundsWithSize(LatLngBounds bounds, int width, int height, int padding) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                if (bounds != null) {
                                    parcelObtain.writeInt(1);
                                    bounds.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(width);
                                parcelObtain.writeInt(height);
                                parcelObtain.writeInt(padding);
                                this.f2258dG.transact(11, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b newLatLngZoom(LatLng latLng, float zoom) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                if (latLng != null) {
                                    parcelObtain.writeInt(1);
                                    latLng.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeFloat(zoom);
                                this.f2258dG.transact(9, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b scrollBy(float xPixel, float yPixel) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                parcelObtain.writeFloat(xPixel);
                                parcelObtain.writeFloat(yPixel);
                                this.f2258dG.transact(3, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b zoomBy(float amount) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                parcelObtain.writeFloat(amount);
                                this.f2258dG.transact(5, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b zoomByWithFocus(float amount, int screenFocusX, int screenFocusY) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                parcelObtain.writeFloat(amount);
                                parcelObtain.writeInt(screenFocusX);
                                parcelObtain.writeInt(screenFocusY);
                                this.f2258dG.transact(6, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b zoomIn() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                this.f2258dG.transact(1, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b zoomOut() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                this.f2258dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
                        public InterfaceC0904b zoomTo(float zoom) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                parcelObtain.writeFloat(zoom);
                                this.f2258dG.transact(4, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: H */
                    public static ICameraUpdateFactoryDelegate m2227H(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICameraUpdateFactoryDelegate)) ? new a(iBinder) : (ICameraUpdateFactoryDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bZoomIn = zoomIn();
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bZoomIn != null ? interfaceC0904bZoomIn.asBinder() : null);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bZoomOut = zoomOut();
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bZoomOut != null ? interfaceC0904bZoomOut.asBinder() : null);
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bScrollBy = scrollBy(data.readFloat(), data.readFloat());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bScrollBy != null ? interfaceC0904bScrollBy.asBinder() : null);
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bZoomTo = zoomTo(data.readFloat());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bZoomTo != null ? interfaceC0904bZoomTo.asBinder() : null);
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bZoomBy = zoomBy(data.readFloat());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bZoomBy != null ? interfaceC0904bZoomBy.asBinder() : null);
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bZoomByWithFocus = zoomByWithFocus(data.readFloat(), data.readInt(), data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bZoomByWithFocus != null ? interfaceC0904bZoomByWithFocus.asBinder() : null);
                                return true;
                            case 7:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bNewCameraPosition = newCameraPosition(data.readInt() != 0 ? CameraPosition.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bNewCameraPosition != null ? interfaceC0904bNewCameraPosition.asBinder() : null);
                                return true;
                            case 8:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bNewLatLng = newLatLng(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bNewLatLng != null ? interfaceC0904bNewLatLng.asBinder() : null);
                                return true;
                            case 9:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bNewLatLngZoom = newLatLngZoom(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null, data.readFloat());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bNewLatLngZoom != null ? interfaceC0904bNewLatLngZoom.asBinder() : null);
                                return true;
                            case 10:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bNewLatLngBounds = newLatLngBounds(data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bNewLatLngBounds != null ? interfaceC0904bNewLatLngBounds.asBinder() : null);
                                return true;
                            case 11:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                InterfaceC0904b interfaceC0904bNewLatLngBoundsWithSize = newLatLngBoundsWithSize(data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt(), data.readInt(), data.readInt());
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bNewLatLngBoundsWithSize != null ? interfaceC0904bNewLatLngBoundsWithSize.asBinder() : null);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                InterfaceC0904b newCameraPosition(CameraPosition cameraPosition) throws RemoteException;

                InterfaceC0904b newLatLng(LatLng latLng) throws RemoteException;

                InterfaceC0904b newLatLngBounds(LatLngBounds latLngBounds, int i) throws RemoteException;

                InterfaceC0904b newLatLngBoundsWithSize(LatLngBounds latLngBounds, int i, int i2, int i3) throws RemoteException;

                InterfaceC0904b newLatLngZoom(LatLng latLng, float f) throws RemoteException;

                InterfaceC0904b scrollBy(float f, float f2) throws RemoteException;

                InterfaceC0904b zoomBy(float f) throws RemoteException;

                InterfaceC0904b zoomByWithFocus(float f, int i, int i2) throws RemoteException;

                InterfaceC0904b zoomIn() throws RemoteException;

                InterfaceC0904b zoomOut() throws RemoteException;

                InterfaceC0904b zoomTo(float f) throws RemoteException;
            }
