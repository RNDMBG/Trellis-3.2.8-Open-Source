            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.location.Location;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.location.InterfaceC1129a;
            import com.google.android.gms.location.LocationRequest;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ex */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1066ex extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.ex$a */
                public static abstract class a extends Binder implements InterfaceC1066ex {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.ex$a$a, reason: collision with other inner class name */
                    private static class C1336a implements InterfaceC1066ex {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1860dG;

                        C1336a(IBinder iBinder) {
                            this.f1860dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1897a(long j, boolean z, PendingIntent pendingIntent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeLong(j);
                                parcelObtain.writeInt(z ? 1 : 0);
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1860dG.transact(5, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1898a(PendingIntent pendingIntent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1860dG.transact(11, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1899a(PendingIntent pendingIntent, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1065ew != null ? interfaceC1065ew.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1860dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1900a(InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeStrongBinder(interfaceC1065ew != null ? interfaceC1065ew.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1860dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1901a(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (locationRequest != null) {
                                    parcelObtain.writeInt(1);
                                    locationRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1860dG.transact(9, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1902a(LocationRequest locationRequest, InterfaceC1129a interfaceC1129a) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (locationRequest != null) {
                                    parcelObtain.writeInt(1);
                                    locationRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1129a != null ? interfaceC1129a.asBinder() : null);
                                this.f1860dG.transact(8, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1903a(LocationRequest locationRequest, InterfaceC1129a interfaceC1129a, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (locationRequest != null) {
                                    parcelObtain.writeInt(1);
                                    locationRequest.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1129a != null ? interfaceC1129a.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1860dG.transact(20, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1904a(InterfaceC1129a interfaceC1129a) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeStrongBinder(interfaceC1129a != null ? interfaceC1129a.asBinder() : null);
                                this.f1860dG.transact(10, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1905a(List<C1070fa> list, PendingIntent pendingIntent, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeTypedList(list);
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStrongBinder(interfaceC1065ew != null ? interfaceC1065ew.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1860dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: a */
                        public void mo1906a(String[] strArr, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeStringArray(strArr);
                                parcelObtain.writeStrongBinder(interfaceC1065ew != null ? interfaceC1065ew.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1860dG.transact(3, parcelObtain, parcelObtain2, 0);
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
                            return this.f1860dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        /* JADX INFO: renamed from: cl */
                        public Location mo1907cl() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                this.f1860dG.transact(7, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        public void removeActivityUpdates(PendingIntent callbackIntent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (callbackIntent != null) {
                                    parcelObtain.writeInt(1);
                                    callbackIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1860dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        public void setMockLocation(Location location) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                if (location != null) {
                                    parcelObtain.writeInt(1);
                                    location.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1860dG.transact(13, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1066ex
                        public void setMockMode(boolean isMockMode) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                parcelObtain.writeInt(isMockMode ? 1 : 0);
                                this.f1860dG.transact(12, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: F */
                    public static InterfaceC1066ex m1908F(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1066ex)) ? new C1336a(iBinder) : (InterfaceC1066ex) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 1:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1905a(parcel.createTypedArrayList(C1070fa.CREATOR), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, InterfaceC1065ew.a.m1896E(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 2:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1899a(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, InterfaceC1065ew.a.m1896E(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 3:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1906a(parcel.createStringArray(), InterfaceC1065ew.a.m1896E(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 4:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1900a(InterfaceC1065ew.a.m1896E(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1897a(parcel.readLong(), parcel.readInt() != 0, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 6:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                removeActivityUpdates(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 7:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                Location locationCl = mo1907cl();
                                parcel2.writeNoException();
                                if (locationCl == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                locationCl.writeToParcel(parcel2, 1);
                                return true;
                            case 8:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1902a(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, InterfaceC1129a.a.m2193D(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 9:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1901a(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 10:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1904a(InterfaceC1129a.a.m2193D(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 11:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1898a(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 12:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                setMockMode(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 13:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                setMockLocation(parcel.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 20:
                                parcel.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                mo1903a(parcel.readInt() != 0 ? LocationRequest.CREATOR.createFromParcel(parcel) : null, InterfaceC1129a.a.m2193D(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo1897a(long j, boolean z, PendingIntent pendingIntent) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1898a(PendingIntent pendingIntent) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1899a(PendingIntent pendingIntent, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1900a(InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1901a(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1902a(LocationRequest locationRequest, InterfaceC1129a interfaceC1129a) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1903a(LocationRequest locationRequest, InterfaceC1129a interfaceC1129a, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1904a(InterfaceC1129a interfaceC1129a) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1905a(List<C1070fa> list, PendingIntent pendingIntent, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1906a(String[] strArr, InterfaceC1065ew interfaceC1065ew, String str) throws RemoteException;

                /* JADX INFO: renamed from: cl */
                Location mo1907cl() throws RemoteException;

                void removeActivityUpdates(PendingIntent pendingIntent) throws RemoteException;

                void setMockLocation(Location location) throws RemoteException;

                void setMockMode(boolean z) throws RemoteException;
            }
