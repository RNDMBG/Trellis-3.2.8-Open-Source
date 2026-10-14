            package com.google.android.gms.maps.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.internal.InterfaceC1190d;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.k */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1167k extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.k$a */
                public static abstract class a extends Binder implements InterfaceC1167k {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.k$a$a, reason: collision with other inner class name */
                    private static class C1358a implements InterfaceC1167k {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2274dG;

                        C1358a(IBinder iBinder) {
                            this.f2274dG = iBinder;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1167k
                        /* JADX INFO: renamed from: a */
                        public boolean mo2203a(InterfaceC1190d interfaceC1190d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                                parcelObtain.writeStrongBinder(interfaceC1190d != null ? interfaceC1190d.asBinder() : null);
                                this.f2274dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                            return this.f2274dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    }

                    /* JADX INFO: renamed from: V */
                    public static InterfaceC1167k m2252V(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1167k)) ? new C1358a(iBinder) : (InterfaceC1167k) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                                boolean zA = mo2203a(InterfaceC1190d.a.m2321af(data.readStrongBinder()));
                                reply.writeNoException();
                                reply.writeInt(zA ? 1 : 0);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                boolean mo2203a(InterfaceC1190d interfaceC1190d) throws RemoteException;
            }
