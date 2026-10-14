            package com.google.android.gms.maps.internal;

            import android.location.Location;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.model.CameraPosition;
            import com.google.android.gms.maps.model.CircleOptions;
            import com.google.android.gms.maps.model.GroundOverlayOptions;
            import com.google.android.gms.maps.model.MarkerOptions;
            import com.google.android.gms.maps.model.PolygonOptions;
            import com.google.android.gms.maps.model.PolylineOptions;
            import com.google.android.gms.maps.model.TileOverlayOptions;
            import com.google.android.gms.maps.model.internal.IPolylineDelegate;
            import com.google.android.gms.maps.model.internal.InterfaceC1188b;
            import com.google.android.gms.maps.model.internal.InterfaceC1189c;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            import com.google.android.gms.maps.model.internal.InterfaceC1191e;
            import com.google.android.gms.maps.model.internal.InterfaceC1192f;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface IGoogleMapDelegate extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.IGoogleMapDelegate$a */
                public static abstract class AbstractBinderC1151a extends Binder implements IGoogleMapDelegate {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.IGoogleMapDelegate$a$a */
                    private static class a implements IGoogleMapDelegate {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2259dG;

                        a(IBinder iBinder) {
                            this.f2259dG = iBinder;
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC1188b addCircle(CircleOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(35, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1188b interfaceC1188bM2314ad = InterfaceC1188b.a.m2314ad(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1188bM2314ad;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC1189c addGroundOverlay(GroundOverlayOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(12, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1189c interfaceC1189cM2318ae = InterfaceC1189c.a.m2318ae(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1189cM2318ae;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC1190d addMarker(MarkerOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(11, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1190d interfaceC1190dM2321af = InterfaceC1190d.a.m2321af(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1190dM2321af;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC1191e addPolygon(PolygonOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(10, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1191e interfaceC1191eM2323ag = InterfaceC1191e.a.m2323ag(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1191eM2323ag;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public IPolylineDelegate addPolyline(PolylineOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(9, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IPolylineDelegate iPolylineDelegateM2304ah = IPolylineDelegate.AbstractBinderC1186a.m2304ah(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iPolylineDelegateM2304ah;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC1192f addTileOverlay(TileOverlayOptions options) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2259dG.transact(13, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1192f interfaceC1192fM2325ai = InterfaceC1192f.a.m2325ai(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1192fM2325ai;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void animateCamera(InterfaceC0904b update) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                                this.f2259dG.transact(5, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void animateCameraWithCallback(InterfaceC0904b update, InterfaceC1158b callback) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                                parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                                this.f2259dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void animateCameraWithDurationAndCallback(InterfaceC0904b update, int durationMs, InterfaceC1158b callback) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                                parcelObtain.writeInt(durationMs);
                                parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                                this.f2259dG.transact(7, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2259dG;
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void clear() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(14, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public CameraPosition getCameraPosition() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                CameraPosition cameraPositionCreateFromParcel = parcelObtain2.readInt() != 0 ? CameraPosition.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return cameraPositionCreateFromParcel;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public int getMapType() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(15, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public float getMaxZoomLevel() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(2, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public float getMinZoomLevel() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(3, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public Location getMyLocation() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(23, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                Location location = parcelObtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return location;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public IProjectionDelegate getProjection() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(26, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IProjectionDelegate iProjectionDelegateM2232Z = IProjectionDelegate.AbstractBinderC1155a.m2232Z(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iProjectionDelegateM2232Z;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public InterfaceC0904b getTestingHelper() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(34, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public IUiSettingsDelegate getUiSettings() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(25, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IUiSettingsDelegate iUiSettingsDelegateM2233ab = IUiSettingsDelegate.AbstractBinderC1156a.m2233ab(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iUiSettingsDelegateM2233ab;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public boolean isBuildingsEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(40, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public boolean isIndoorEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(19, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public boolean isMyLocationEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(21, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public boolean isTrafficEnabled() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(17, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void moveCamera(InterfaceC0904b update) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(update != null ? update.asBinder() : null);
                                this.f2259dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setBuildingsEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2259dG.transact(41, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public boolean setIndoorEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2259dG.transact(20, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setInfoWindowAdapter(InterfaceC1160d adapter) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(adapter != null ? adapter.asBinder() : null);
                                this.f2259dG.transact(33, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setLocationSource(ILocationSourceDelegate source) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(source != null ? source.asBinder() : null);
                                this.f2259dG.transact(24, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setMapType(int type) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(type);
                                this.f2259dG.transact(16, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setMyLocationEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2259dG.transact(22, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnCameraChangeListener(InterfaceC1161e listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(27, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnInfoWindowClickListener(InterfaceC1162f listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(32, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMapClickListener(InterfaceC1164h listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(28, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMapLoadedCallback(InterfaceC1165i callback) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                                this.f2259dG.transact(42, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMapLongClickListener(InterfaceC1166j listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(29, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMarkerClickListener(InterfaceC1167k listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(30, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMarkerDragListener(InterfaceC1168l listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(31, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMyLocationButtonClickListener(InterfaceC1169m listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(37, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setOnMyLocationChangeListener(InterfaceC1170n listener) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(listener != null ? listener.asBinder() : null);
                                this.f2259dG.transact(36, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setPadding(int left, int top, int right, int bottom) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(left);
                                parcelObtain.writeInt(top);
                                parcelObtain.writeInt(right);
                                parcelObtain.writeInt(bottom);
                                this.f2259dG.transact(39, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void setTrafficEnabled(boolean enabled) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeInt(enabled ? 1 : 0);
                                this.f2259dG.transact(18, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void snapshot(InterfaceC1171o callback, InterfaceC0904b bitmap) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                parcelObtain.writeStrongBinder(callback != null ? callback.asBinder() : null);
                                parcelObtain.writeStrongBinder(bitmap != null ? bitmap.asBinder() : null);
                                this.f2259dG.transact(38, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.IGoogleMapDelegate
                        public void stopAnimation() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                this.f2259dG.transact(8, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: K */
                    public static IGoogleMapDelegate m2228K(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGoogleMapDelegate)) ? new a(iBinder) : (IGoogleMapDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                CameraPosition cameraPosition = getCameraPosition();
                                parcel2.writeNoException();
                                if (cameraPosition == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                cameraPosition.writeToParcel(parcel2, 1);
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                float maxZoomLevel = getMaxZoomLevel();
                                parcel2.writeNoException();
                                parcel2.writeFloat(maxZoomLevel);
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                float minZoomLevel = getMinZoomLevel();
                                parcel2.writeNoException();
                                parcel2.writeFloat(minZoomLevel);
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                moveCamera(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                animateCamera(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                animateCameraWithCallback(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()), InterfaceC1158b.a.m2236I(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                animateCameraWithDurationAndCallback(InterfaceC0904b.a.m1164z(parcel.readStrongBinder()), parcel.readInt(), InterfaceC1158b.a.m2236I(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                stopAnimation();
                                parcel2.writeNoException();
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                IPolylineDelegate iPolylineDelegateAddPolyline = addPolyline(parcel.readInt() != 0 ? PolylineOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(iPolylineDelegateAddPolyline != null ? iPolylineDelegateAddPolyline.asBinder() : null);
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC1191e interfaceC1191eAddPolygon = addPolygon(parcel.readInt() != 0 ? PolygonOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(interfaceC1191eAddPolygon != null ? interfaceC1191eAddPolygon.asBinder() : null);
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC1190d interfaceC1190dAddMarker = addMarker(parcel.readInt() != 0 ? MarkerOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(interfaceC1190dAddMarker != null ? interfaceC1190dAddMarker.asBinder() : null);
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC1189c interfaceC1189cAddGroundOverlay = addGroundOverlay(parcel.readInt() != 0 ? GroundOverlayOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(interfaceC1189cAddGroundOverlay != null ? interfaceC1189cAddGroundOverlay.asBinder() : null);
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC1192f interfaceC1192fAddTileOverlay = addTileOverlay(parcel.readInt() != 0 ? TileOverlayOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(interfaceC1192fAddTileOverlay != null ? interfaceC1192fAddTileOverlay.asBinder() : null);
                                return true;
                            case 14:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                clear();
                                parcel2.writeNoException();
                                return true;
                            case 15:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                int mapType = getMapType();
                                parcel2.writeNoException();
                                parcel2.writeInt(mapType);
                                return true;
                            case 16:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setMapType(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 17:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                boolean zIsTrafficEnabled = isTrafficEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsTrafficEnabled ? 1 : 0);
                                return true;
                            case 18:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setTrafficEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 19:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                boolean zIsIndoorEnabled = isIndoorEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsIndoorEnabled ? 1 : 0);
                                return true;
                            case 20:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                boolean indoorEnabled = setIndoorEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                parcel2.writeInt(indoorEnabled ? 1 : 0);
                                return true;
                            case 21:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                boolean zIsMyLocationEnabled = isMyLocationEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsMyLocationEnabled ? 1 : 0);
                                return true;
                            case 22:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setMyLocationEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 23:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                Location myLocation = getMyLocation();
                                parcel2.writeNoException();
                                if (myLocation == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                myLocation.writeToParcel(parcel2, 1);
                                return true;
                            case 24:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setLocationSource(ILocationSourceDelegate.AbstractBinderC1152a.m2229M(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 25:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                IUiSettingsDelegate uiSettings = getUiSettings();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(uiSettings != null ? uiSettings.asBinder() : null);
                                return true;
                            case 26:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                IProjectionDelegate projection = getProjection();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(projection != null ? projection.asBinder() : null);
                                return true;
                            case 27:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnCameraChangeListener(InterfaceC1161e.a.m2245P(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 28:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMapClickListener(InterfaceC1164h.a.m2249S(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 29:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMapLongClickListener(InterfaceC1166j.a.m2251U(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 30:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMarkerClickListener(InterfaceC1167k.a.m2252V(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 31:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMarkerDragListener(InterfaceC1168l.a.m2253W(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 32:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnInfoWindowClickListener(InterfaceC1162f.a.m2246Q(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 33:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setInfoWindowAdapter(InterfaceC1160d.a.m2244L(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 34:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC0904b testingHelper = getTestingHelper();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(testingHelper != null ? testingHelper.asBinder() : null);
                                return true;
                            case 35:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                InterfaceC1188b interfaceC1188bAddCircle = addCircle(parcel.readInt() != 0 ? CircleOptions.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(interfaceC1188bAddCircle != null ? interfaceC1188bAddCircle.asBinder() : null);
                                return true;
                            case 36:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMyLocationChangeListener(InterfaceC1170n.a.m2255Y(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 37:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMyLocationButtonClickListener(InterfaceC1169m.a.m2254X(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 38:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                snapshot(InterfaceC1171o.a.m2256aa(parcel.readStrongBinder()), InterfaceC0904b.a.m1164z(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 39:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setPadding(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 40:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                boolean zIsBuildingsEnabled = isBuildingsEnabled();
                                parcel2.writeNoException();
                                parcel2.writeInt(zIsBuildingsEnabled ? 1 : 0);
                                return true;
                            case 41:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setBuildingsEnabled(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 42:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                setOnMapLoadedCallback(InterfaceC1165i.a.m2250T(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                InterfaceC1188b addCircle(CircleOptions circleOptions) throws RemoteException;

                InterfaceC1189c addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException;

                InterfaceC1190d addMarker(MarkerOptions markerOptions) throws RemoteException;

                InterfaceC1191e addPolygon(PolygonOptions polygonOptions) throws RemoteException;

                IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException;

                InterfaceC1192f addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException;

                void animateCamera(InterfaceC0904b interfaceC0904b) throws RemoteException;

                void animateCameraWithCallback(InterfaceC0904b interfaceC0904b, InterfaceC1158b interfaceC1158b) throws RemoteException;

                void animateCameraWithDurationAndCallback(InterfaceC0904b interfaceC0904b, int i, InterfaceC1158b interfaceC1158b) throws RemoteException;

                void clear() throws RemoteException;

                CameraPosition getCameraPosition() throws RemoteException;

                int getMapType() throws RemoteException;

                float getMaxZoomLevel() throws RemoteException;

                float getMinZoomLevel() throws RemoteException;

                Location getMyLocation() throws RemoteException;

                IProjectionDelegate getProjection() throws RemoteException;

                InterfaceC0904b getTestingHelper() throws RemoteException;

                IUiSettingsDelegate getUiSettings() throws RemoteException;

                boolean isBuildingsEnabled() throws RemoteException;

                boolean isIndoorEnabled() throws RemoteException;

                boolean isMyLocationEnabled() throws RemoteException;

                boolean isTrafficEnabled() throws RemoteException;

                void moveCamera(InterfaceC0904b interfaceC0904b) throws RemoteException;

                void setBuildingsEnabled(boolean z) throws RemoteException;

                boolean setIndoorEnabled(boolean z) throws RemoteException;

                void setInfoWindowAdapter(InterfaceC1160d interfaceC1160d) throws RemoteException;

                void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException;

                void setMapType(int i) throws RemoteException;

                void setMyLocationEnabled(boolean z) throws RemoteException;

                void setOnCameraChangeListener(InterfaceC1161e interfaceC1161e) throws RemoteException;

                void setOnInfoWindowClickListener(InterfaceC1162f interfaceC1162f) throws RemoteException;

                void setOnMapClickListener(InterfaceC1164h interfaceC1164h) throws RemoteException;

                void setOnMapLoadedCallback(InterfaceC1165i interfaceC1165i) throws RemoteException;

                void setOnMapLongClickListener(InterfaceC1166j interfaceC1166j) throws RemoteException;

                void setOnMarkerClickListener(InterfaceC1167k interfaceC1167k) throws RemoteException;

                void setOnMarkerDragListener(InterfaceC1168l interfaceC1168l) throws RemoteException;

                void setOnMyLocationButtonClickListener(InterfaceC1169m interfaceC1169m) throws RemoteException;

                void setOnMyLocationChangeListener(InterfaceC1170n interfaceC1170n) throws RemoteException;

                void setPadding(int i, int i2, int i3, int i4) throws RemoteException;

                void setTrafficEnabled(boolean z) throws RemoteException;

                void snapshot(InterfaceC1171o interfaceC1171o, InterfaceC0904b interfaceC0904b) throws RemoteException;

                void stopAnimation() throws RemoteException;
            }
