            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            
            public interface IMapViewDelegate extends IInterface {

                
                public static abstract class AbstractBinderC1154a extends Binder implements IMapViewDelegate {

                    
                    private static class a implements IMapViewDelegate {

                        
                        private IBinder f2262dG;

                        a(IBinder iBinder) {
                            this.f2262dG = iBinder;
                        }

                        @Override 
                        public IBinder asBinder() {
                            return this.f2262dG;
                        }

                        @Override 
                        public IGoogleMapDelegate getMap() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        public InterfaceC0904b getView() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(8, parcelObtain, parcelObtain2, 0);
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
                        public void onCreate(Bundle savedInstanceState) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                if (savedInstanceState != null) {
                                    parcelObtain.writeInt(1);
                                    savedInstanceState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2262dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        public void onDestroy() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(5, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(6, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(4, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                this.f2262dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                                if (outState != null) {
                                    parcelObtain.writeInt(1);
                                    outState.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2262dG.transact(7, parcelObtain, parcelObtain2, 0);
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

                    
                    public static IMapViewDelegate m2231O(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMapViewDelegate)) ? new a(iBinder) : (IMapViewDelegate) iInterfaceQueryLocalInterface;
                    }

                    @Override 
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                IGoogleMapDelegate map = getMap();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(map != null ? map.asBinder() : null);
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                onCreate(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                onResume();
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                onPause();
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                onDestroy();
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                onLowMemory();
                                parcel2.writeNoException();
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                                onSaveInstanceState(bundle);
                                parcel2.writeNoException();
                                if (bundle != null) {
                                    parcel2.writeInt(1);
                                    bundle.writeToParcel(parcel2, 1);
                                } else {
                                    parcel2.writeInt(0);
                                }
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                                InterfaceC0904b view = getView();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(view != null ? view.asBinder() : null);
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.maps.internal.IMapViewDelegate");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                IGoogleMapDelegate getMap() throws RemoteException;

                InterfaceC0904b getView() throws RemoteException;

                void onCreate(Bundle bundle) throws RemoteException;

                void onDestroy() throws RemoteException;

                void onLowMemory() throws RemoteException;

                void onPause() throws RemoteException;

                void onResume() throws RemoteException;

                void onSaveInstanceState(Bundle bundle) throws RemoteException;
            }
