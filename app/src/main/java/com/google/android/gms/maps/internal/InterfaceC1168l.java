            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.l */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1168l extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.l$a */
                public static abstract class a extends Binder implements InterfaceC1168l {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.l$a$a, reason: collision with other inner class name */
                    private static class C1359a implements InterfaceC1168l {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2275dG;

                        C1359a(IBinder iBinder) {
                            this.f2275dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2275dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1168l
                        /* JADX INFO: renamed from: b */
                        public void mo2204b(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2275dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1168l
                        /* JADX INFO: renamed from: c */
                        public void mo2205c(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2275dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1168l
                        /* JADX INFO: renamed from: d */
                        public void mo2206d(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2275dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    }

                    /* JADX INFO: renamed from: W */
                    public static InterfaceC1168l m2253W(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1168l)) ? new C1359a(iBinder) : (InterfaceC1168l) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                mo2204b(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                mo2206d(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                mo2205c(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: b */
                void mo2204b(InterfaceC1190d interfaceC1190d) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo2205c(InterfaceC1190d interfaceC1190d) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo2206d(InterfaceC1190d interfaceC1190d) throws RemoteException;
            }
