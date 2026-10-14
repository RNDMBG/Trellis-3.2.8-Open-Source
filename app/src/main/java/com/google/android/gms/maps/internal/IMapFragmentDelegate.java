            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            import com.google.android.gms.maps.GoogleMapOptions;
            
            
            public interface IMapFragmentDelegate extends IInterface {

                
                public static abstract class AbstractBinderC1153a extends Binder implements IMapFragmentDelegate {

                    
                    private static class a implements IMapFragmentDelegate {

                        
                        private IBinder f2261dG;

                        a(IBinder iBinder) {
                            this.f2261dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2261dG;
                        }

                        @Override 
                        public IGoogleMapDelegate getMap() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                IGoogleMapDelegate iGoogleMapDelegateM2228K = IGoogleMapDelegate.AbstractBinderC1151a.m2228K(parcelObtain2.readStrongBinder());
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return iGoogleMapDelegateM2228K;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override 
                        public boolean isReady() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(11, parcelObtain, parcelObtain2, 0);
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
                        public void onCreate(Bundle savedInstanceState) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                if (savedInstanceState != null) {
                                    parcelObtain.writeInt(1);
                                    savedInstanceState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2261dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                        public InterfaceC0904b onCreateView(InterfaceC0904b inflaterWrapper, InterfaceC0904b containerWrapper, Bundle savedInstanceState) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                parcelObtain.writeStrongBinder(inflaterWrapper != null ? inflaterWrapper.asBinder() : null);
                                parcelObtain.writeStrongBinder(containerWrapper != null ? containerWrapper.asBinder() : null);
                                if (savedInstanceState != null) {
                                    parcelObtain.writeInt(1);
                                    savedInstanceState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2261dG.transact(4, parcelObtain, parcelObtain2, 0);
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

                        @Override 
                        public void onDestroy() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                        public void onDestroyView() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(7, parcelObtain, parcelObtain2, 0);
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
                        public void onInflate(InterfaceC0904b activityWrapper, GoogleMapOptions options, Bundle savedInstanceState) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                parcelObtain.writeStrongBinder(activityWrapper != null ? activityWrapper.asBinder() : null);
                                if (options != null) {
                                    parcelObtain.writeInt(1);
                                    options.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (savedInstanceState != null) {
                                    parcelObtain.writeInt(1);
                                    savedInstanceState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2261dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        public void onLowMemory() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(9, parcelObtain, parcelObtain2, 0);
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
                        public void onPause() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                        public void onResume() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                this.f2261dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                        public void onSaveInstanceState(Bundle outState) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                if (outState != null) {
                                    parcelObtain.writeInt(1);
                                    outState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2261dG.transact(10, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                if (parcelObtain2.readInt() != 0) {
                                    outState.readFromParcel(parcelObtain2);
                                }
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    
                    public static IMapFragmentDelegate m2230N(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMapFragmentDelegate)) ? new a(iBinder) : (IMapFragmentDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                IGoogleMapDelegate map = getMap();
                                reply.writeNoException();
                                reply.writeStrongBinder(map != null ? map.asBinder() : null);
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onInflate(InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onCreate(data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                InterfaceC0904b interfaceC0904bOnCreateView = onCreateView(InterfaceC0904b.a.m1164z(data.readStrongBinder()), InterfaceC0904b.a.m1164z(data.readStrongBinder()), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                reply.writeStrongBinder(interfaceC0904bOnCreateView != null ? interfaceC0904bOnCreateView.asBinder() : null);
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onResume();
                                reply.writeNoException();
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onPause();
                                reply.writeNoException();
                                return true;
                            case 7:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onDestroyView();
                                reply.writeNoException();
                                return true;
                            case 8:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onDestroy();
                                reply.writeNoException();
                                return true;
                            case 9:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                onLowMemory();
                                reply.writeNoException();
                                return true;
                            case 10:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                Bundle bundle = data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null;
                                onSaveInstanceState(bundle);
                                reply.writeNoException();
                                if (bundle == null) {
                                    reply.writeInt(0);
                                    return true;
                                }
                                reply.writeInt(1);
                                bundle.writeToParcel(reply, 1);
                                return true;
                            case 11:
                                data.enforceInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                boolean zIsReady = isReady();
                                reply.writeNoException();
                                reply.writeInt(zIsReady ? 1 : 0);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                IGoogleMapDelegate getMap() throws RemoteException;

                boolean isReady() throws RemoteException;

                void onCreate(Bundle bundle) throws RemoteException;

                InterfaceC0904b onCreateView(InterfaceC0904b interfaceC0904b, InterfaceC0904b interfaceC0904b2, Bundle bundle) throws RemoteException;

                void onDestroy() throws RemoteException;

                void onDestroyView() throws RemoteException;

                void onInflate(InterfaceC0904b interfaceC0904b, GoogleMapOptions googleMapOptions, Bundle bundle) throws RemoteException;

                void onLowMemory() throws RemoteException;

                void onPause() throws RemoteException;

                void onResume() throws RemoteException;

                void onSaveInstanceState(Bundle bundle) throws RemoteException;
            }
