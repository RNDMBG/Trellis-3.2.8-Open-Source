            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.common.data.C0891d;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.cx */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1012cx extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.cx$a */
                public static abstract class a extends Binder implements InterfaceC1012cx {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.cx$a$a, reason: collision with other inner class name */
                    private static class C1326a implements InterfaceC1012cx {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1646dG;

                        C1326a(IBinder iBinder) {
                            this.f1646dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1012cx
                        /* JADX INFO: renamed from: a */
                        public void mo1496a(int i, C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(i);
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1646dG.transact(5001, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1012cx
                        /* JADX INFO: renamed from: a */
                        public void mo1497a(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1646dG.transact(5002, parcelObtain, parcelObtain2, 0);
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
                            return this.f1646dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1012cx
                        /* JADX INFO: renamed from: m */
                        public void mo1498m(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1646dG.transact(5005, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1012cx
                        public void onSignOutComplete() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                this.f1646dG.transact(5004, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1012cx
                        public void onStateDeleted(int statusCode, int stateKey) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeInt(stateKey);
                                this.f1646dG.transact(5003, parcelObtain, parcelObtain2, 0);
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

                    public a() {
                        attachInterface(this, "com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    }

                    /* JADX INFO: renamed from: s */
                    public static InterfaceC1012cx m1505s(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1012cx)) ? new C1326a(iBinder) : (InterfaceC1012cx) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 5001:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1496a(data.readInt(), data.readInt() != 0 ? C0891d.CREATOR.m1054h(data) : null);
                                reply.writeNoException();
                                return true;
                            case 5002:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1497a(data.readInt() != 0 ? C0891d.CREATOR.m1054h(data) : null);
                                reply.writeNoException();
                                return true;
                            case 5003:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                onStateDeleted(data.readInt(), data.readInt());
                                reply.writeNoException();
                                return true;
                            case 5004:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                onSignOutComplete();
                                reply.writeNoException();
                                return true;
                            case 5005:
                                data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                mo1498m(data.readInt());
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo1496a(int i, C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1497a(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: m */
                void mo1498m(int i) throws RemoteException;

                void onSignOutComplete() throws RemoteException;

                void onStateDeleted(int i, int i2) throws RemoteException;
            }
