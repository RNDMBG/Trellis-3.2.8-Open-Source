            package com.google.android.gms.maps.internal;

            import android.graphics.Bitmap;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.dynamic.InterfaceC0904b;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.internal.o */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1171o extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.internal.o$a */
                public static abstract class a extends Binder implements InterfaceC1171o {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.internal.o$a$a, reason: collision with other inner class name */
                    private static class C1362a implements InterfaceC1171o {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2278dG;

                        C1362a(IBinder iBinder) {
                            this.f2278dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2278dG;
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1171o
                        /* JADX INFO: renamed from: c */
                        public void mo2202c(InterfaceC0904b interfaceC0904b) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                                parcelObtain.writeStrongBinder(interfaceC0904b != null ? interfaceC0904b.asBinder() : null);
                                this.f2278dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.maps.internal.InterfaceC1171o
                        public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                                if (snapshot != null) {
                                    parcelObtain.writeInt(1);
                                    snapshot.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f2278dG.transact(1, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                    }

                    /* JADX INFO: renamed from: aa */
                    public static InterfaceC1171o m2256aa(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1171o)) ? new C1362a(iBinder) : (InterfaceC1171o) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                                onSnapshotReady(data.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                                mo2202c(InterfaceC0904b.a.m1164z(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.internal.ISnapshotReadyCallback");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: c */
                void mo2202c(InterfaceC0904b interfaceC0904b) throws RemoteException;

                void onSnapshotReady(Bitmap bitmap) throws RemoteException;
            }
