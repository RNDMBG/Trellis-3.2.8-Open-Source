            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface IUiSettingsDelegate extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.IUiSettingsDelegate$a */
                public static abstract class AbstractBinderC1156a extends Binder implements IUiSettingsDelegate {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.IUiSettingsDelegate$a$a */
                    private static class a implements IUiSettingsDelegate {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2264dG;

                        a(IBinder iBinder) {
                            this.f2264dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2264dG;
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isCompassEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(10, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isMyLocationButtonEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(11, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isRotateGesturesEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(15, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isScrollGesturesEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(12, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isTiltGesturesEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(14, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isZoomControlsEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(9, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public boolean isZoomGesturesEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                this.f2264dG.transact(13, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setAllGesturesEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(8, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setCompassEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setMyLocationButtonEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setRotateGesturesEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(7, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setScrollGesturesEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setTiltGesturesEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setZoomControlsEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IUiSettingsDelegate
                        public void setZoomGesturesEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2264dG.transact(5, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: ab */
                    public static IUiSettingsDelegate m2233ab(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUiSettingsDelegate)) ? new a(iBinder) : (IUiSettingsDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setZoomControlsEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setCompassEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setMyLocationButtonEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setScrollGesturesEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setZoomGesturesEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setTiltGesturesEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setRotateGesturesEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                setAllGesturesEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsZoomControlsEnabled = isZoomControlsEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsZoomControlsEnabled ? 1 : 0);
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsCompassEnabled = isCompassEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsCompassEnabled ? 1 : 0);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsMyLocationButtonEnabled = isMyLocationButtonEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsMyLocationButtonEnabled ? 1 : 0);
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsScrollGesturesEnabled = isScrollGesturesEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsScrollGesturesEnabled ? 1 : 0);
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsZoomGesturesEnabled = isZoomGesturesEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsZoomGesturesEnabled ? 1 : 0);
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsTiltGesturesEnabled = isTiltGesturesEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsTiltGesturesEnabled ? 1 : 0);
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                boolean zIsRotateGesturesEnabled = isRotateGesturesEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsRotateGesturesEnabled ? 1 : 0);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                boolean isCompassEnabled() throws RemoteException;

                boolean isMyLocationButtonEnabled() throws RemoteException;

                boolean isRotateGesturesEnabled() throws RemoteException;

                boolean isScrollGesturesEnabled() throws RemoteException;

                boolean isTiltGesturesEnabled() throws RemoteException;

                boolean isZoomControlsEnabled() throws RemoteException;

                boolean isZoomGesturesEnabled() throws RemoteException;

                void setAllGesturesEnabled(boolean z) throws RemoteException;

                void setCompassEnabled(boolean z) throws RemoteException;

                void setMyLocationButtonEnabled(boolean z) throws RemoteException;

                void setRotateGesturesEnabled(boolean z) throws RemoteException;

                void setScrollGesturesEnabled(boolean z) throws RemoteException;

                void setTiltGesturesEnabled(boolean z) throws RemoteException;

                void setZoomControlsEnabled(boolean z) throws RemoteException;

                void setZoomGesturesEnabled(boolean z) throws RemoteException;
            }
