            package com.google.android.gms.maps.model.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.maps.model.Tile;
            
            /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.g */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1193g extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.g$a */
                public static abstract class a extends Binder implements InterfaceC1193g {

                    /* JADX INFO: renamed from: com.google.android.gms.maps.model.internal.g$a$a, reason: collision with other inner class name */
                    private static class C1369a implements InterfaceC1193g {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f2370dG;

                        C1369a(IBinder iBinder) {
                            this.f2370dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f2370dG;
                        }

                        @Override // com.google.android.gms.maps.model.internal.InterfaceC1193g
                        public Tile getTile(int x, int y, int zoom) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
                                parcelObtain.writeInt(x);
                                parcelObtain.writeInt(y);
                                parcelObtain.writeInt(zoom);
                                this.f2370dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                Tile tileCreateFromParcel = parcelObtain2.readInt() != 0 ? Tile.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return tileCreateFromParcel;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }
                    }

                    public a() {
                        attachInterface(this, "com.google.android.gms.maps.model.internal.ITileProviderDelegate");
                    }

                    /* JADX INFO: renamed from: aj */
                    public static InterfaceC1193g m2326aj(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1193g)) ? new C1369a(iBinder) : (InterfaceC1193g) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
                                Tile tile = getTile(data.readInt(), data.readInt(), data.readInt());
                                reply.writeNoException();
                                if (tile == null) {
                                    reply.writeInt(0);
                                    return true;
                                }
                                reply.writeInt(1);
                                tile.writeToParcel(reply, 1);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.maps.model.internal.ITileProviderDelegate");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                Tile getTile(int i, int i2, int i3) throws RemoteException;
            }
