            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.by */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC0986by extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.by$a */
                public static abstract class a extends Binder implements InterfaceC0986by {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.by$a$a, reason: collision with other inner class name */
                    private static class C1325a implements InterfaceC0986by {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1517dG;

                        C1325a(IBinder iBinder) {
                            this.f1517dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC0986by
                        /* JADX INFO: renamed from: a */
                        public C0984bw mo1377a(C0982bu c0982bu) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.internal.request.IAdRequestService");
                                if (c0982bu != null) {
                                    parcelObtain.writeInt(1);
                                    c0982bu.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1517dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                C0984bw c0984bwM1375f = parcelObtain2.readInt() != 0 ? C0984bw.CREATOR.m1375f(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return c0984bwM1375f;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1517dG;
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.ads.internal.request.IAdRequestService");
                    }

                    /* JADX INFO: renamed from: q */
                    public static InterfaceC0986by m1378q(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.request.IAdRequestService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0986by)) ? new C1325a(iBinder) : (InterfaceC0986by) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.ads.internal.request.IAdRequestService");
                                C0984bw c0984bwA = mo1377a(data.readInt() != 0 ? C0982bu.CREATOR.m1372e(data) : null);
                                reply.writeNoException();
                                if (c0984bwA != null) {
                                    reply.writeInt(1);
                                    c0984bwA.writeToParcel(reply, 1);
                                } else {
                                    reply.writeInt(0);
                                }
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.ads.internal.request.IAdRequestService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                C0984bw mo1377a(C0982bu c0982bu) throws RemoteException;
            }
