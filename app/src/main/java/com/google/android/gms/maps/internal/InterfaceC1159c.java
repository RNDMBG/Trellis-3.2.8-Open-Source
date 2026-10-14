            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.GoogleMapOptions;
            import com.google.android.gms.maps.model.internal.InterfaceC1187a;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.c */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1159c extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.c$a */
                public static abstract class a extends Binder implements InterfaceC1159c {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.c$a$a, reason: collision with other inner class name */
                    private static class C1350a implements InterfaceC1159c {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2266dG;

                        C1350a(IBinder iBinder) {
                            this.f2266dG = iBinder;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: a */
                        public IMapViewDelegate mo2237a(InterfaceC0904b interfaceC0904b, GoogleMapOptions googleMapOptions) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                if (googleMapOptions != null) {
                                    parcelObtain.writeInt(1);
                                    googleMapOptions.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2266dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IMapViewDelegate iMapViewDelegateM2231O = IMapViewDelegate.AbstractBinderC1154a.m2231O(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iMapViewDelegateM2231O;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: a */
                        public void mo2238a(InterfaceC0904b interfaceC0904b, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                parcelObtain.writeInt(i);
                                this.f2266dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                            return this.f2266dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: cG */
                        public ICameraUpdateFactoryDelegate mo2239cG() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                this.f2266dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegateM2227H = ICameraUpdateFactoryDelegate.AbstractBinderC1150a.m2227H(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iCameraUpdateFactoryDelegateM2227H;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: cH */
                        public InterfaceC1187a mo2240cH() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                this.f2266dG.transact(5, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                InterfaceC1187a interfaceC1187aM2312ac = InterfaceC1187a.a.m2312ac(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return interfaceC1187aM2312ac;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: e */
                        public void mo2241e(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f2266dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1159c
                        /* JADX INFO: renamed from: f */
                        public IMapFragmentDelegate mo2242f(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f2266dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IMapFragmentDelegate iMapFragmentDelegateM2230N = IMapFragmentDelegate.AbstractBinderC1153a.m2230N(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iMapFragmentDelegateM2230N;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    /* JADX INFO: renamed from: J */
                    public static InterfaceC1159c m2243J(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1159c)) ? new C1350a(iBinder) : (InterfaceC1159c) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                mo2241e(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                IMapFragmentDelegate iMapFragmentDelegateF = mo2242f(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                reply.writeStrongBinder(iMapFragmentDelegateF != null ? iMapFragmentDelegateF.asBinder() : null);
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                IMapViewDelegate iMapViewDelegateA = mo2237a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(iMapViewDelegateA != null ? iMapViewDelegateA.asBinder() : null);
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                ICameraUpdateFactoryDelegate iCameraUpdateFactoryDelegateCG = mo2239cG();
                                reply.writeNoException();
                                reply.writeStrongBinder(iCameraUpdateFactoryDelegateCG != null ? iCameraUpdateFactoryDelegateCG.asBinder() : null);
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                InterfaceC1187a interfaceC1187aCH = mo2240cH();
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC1187aCH != null ? interfaceC1187aCH.asBinder() : null);
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                                mo2238a(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.ICreator");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                IMapViewDelegate mo2237a(InterfaceC0904b interfaceC0904b, GoogleMapOptions googleMapOptions) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo2238a(InterfaceC0904b interfaceC0904b, int i) throws RemoteException;

                /* JADX INFO: renamed from: cG */
                ICameraUpdateFactoryDelegate mo2239cG() throws RemoteException;

                /* JADX INFO: renamed from: cH */
                InterfaceC1187a mo2240cH() throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo2241e(InterfaceC0904b interfaceC0904b) throws RemoteException;

                /* JADX INFO: renamed from: f */
                IMapFragmentDelegate mo2242f(InterfaceC0904b interfaceC0904b) throws RemoteException;
            }
