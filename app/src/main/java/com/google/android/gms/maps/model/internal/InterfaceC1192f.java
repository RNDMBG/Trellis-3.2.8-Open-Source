            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.f */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1192f extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.f$a */
                public static abstract class a extends Binder implements InterfaceC1192f {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.f$a$a, reason: collision with other inner class name */
                    private static class C1368a implements InterfaceC1192f {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2369dG;

                        C1368a(IBinder iBinder) {
                            this.f2369dG = iBinder;
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        /* JADX INFO: renamed from: a */
                        public boolean mo2324a(InterfaceC1192f interfaceC1192f) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                parcelObtain.writeStrongBinder(interfaceC1192f != null ? interfaceC1192f.asBinder() : null);
                                this.f2369dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                            return this.f2369dG;
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public void clearTileCache() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public String getId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(3, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public float getZIndex() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(5, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public int hashCodeRemote() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(9, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public boolean isVisible() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(7, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public void remove() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                this.f2369dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public void setVisible(boolean visible) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                parcelObtain.writeInt(visible ? 1 : 0);
                                this.f2369dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1192f
                        public void setZIndex(float zIndex) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                parcelObtain.writeFloat(zIndex);
                                this.f2369dG.transact(4, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: ai */
                    public static InterfaceC1192f m2325ai(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1192f)) ? new C1368a(iBinder) : (InterfaceC1192f) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                remove();
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                clearTileCache();
                                parcel2.writeNoException();
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                String id = getId();
                                parcel2.writeNoException();
                                parcel2.writeString(id);
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                setZIndex(parcel.readFloat());
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                float zIndex = getZIndex();
                                parcel2.writeNoException();
                                parcel2.writeFloat(zIndex);
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                setVisible(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                boolean zIsVisible = isVisible();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsVisible ? 1 : 0);
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                boolean zA = mo2324a(m2325ai(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                parcel2.writeInt(zA ? 1 : 0);
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                int iHashCodeRemote = hashCodeRemote();
                                parcel2.writeNoException();
                                parcel2.writeInt(iHashCodeRemote);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                boolean mo2324a(InterfaceC1192f interfaceC1192f) throws RemoteException;

                void clearTileCache() throws RemoteException;

                String getId() throws RemoteException;

                float getZIndex() throws RemoteException;

                int hashCodeRemote() throws RemoteException;

                boolean isVisible() throws RemoteException;

                void remove() throws RemoteException;

                void setVisible(boolean z) throws RemoteException;

                void setZIndex(float f) throws RemoteException;
            }
