            package com.google.android.gms.internal;

            import android.app.PendingIntent;
            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.ew */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1065ew extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.ew$a */
                public static abstract class a extends Binder implements InterfaceC1065ew {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.ew$a$a, reason: collision with other inner class name */
                    private static class C1335a implements InterfaceC1065ew {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1859dG;

                        C1335a(IBinder iBinder) {
                            this.f1859dG = iBinder;
                        }

                        @Override // android.os.IInterface
                        public IBinder asBinder() {
                            return this.f1859dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1065ew
                        public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeStringArray(geofenceRequestIds);
                                this.f1859dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1065ew
                        public void onRemoveGeofencesByPendingIntentResult(int statusCode, PendingIntent pendingIntent) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                parcelObtain.writeInt(statusCode);
                                if (pendingIntent != null) {
                                    parcelObtain.writeInt(1);
                                    pendingIntent.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1859dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1065ew
                        public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] geofenceRequestIds) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeStringArray(geofenceRequestIds);
                                this.f1859dG.transact(2, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.location.internal.IGeofencerCallbacks");
                    }

                    /* JADX INFO: renamed from: E */
                    public static InterfaceC1065ew m1896E(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1065ew)) ? new C1335a(iBinder) : (InterfaceC1065ew) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                onAddGeofencesResult(data.readInt(), data.createStringArray());
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                onRemoveGeofencesByRequestIdsResult(data.readInt(), data.createStringArray());
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                onRemoveGeofencesByPendingIntentResult(data.readInt(), data.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.location.internal.IGeofencerCallbacks");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                void onAddGeofencesResult(int i, String[] strArr) throws RemoteException;

                void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent) throws RemoteException;

                void onRemoveGeofencesByRequestIdsResult(int i, String[] strArr) throws RemoteException;
            }
