            package com.google.android.gms.internal;

            import android.net.Uri;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import java.util.List;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.fk */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1080fk extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.fk$a */
                public static abstract class a extends Binder implements InterfaceC1080fk {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.fk$a$a, reason: collision with other inner class name */
                    private static class C1342a implements InterfaceC1080fk {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1915dG;

                        C1342a(IBinder iBinder) {
                            this.f1915dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1959a(C1045ec c1045ec) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                if (c1045ec != null) {
                                    parcelObtain.writeInt(1);
                                    c1045ec.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1915dG.transact(4, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1960a(InterfaceC1077fh interfaceC1077fh) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                this.f1915dG.transact(8, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1961a(InterfaceC1077fh interfaceC1077fh, int i, int i2, int i3, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(i3);
                                parcelObtain.writeString(str);
                                this.f1915dG.transact(16, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1962a(InterfaceC1077fh interfaceC1077fh, int i, String str, Uri uri, String str2, String str3) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                if (uri != null) {
                                    parcelObtain.writeInt(1);
                                    uri.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeString(str2);
                                parcelObtain.writeString(str3);
                                this.f1915dG.transact(14, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1963a(InterfaceC1077fh interfaceC1077fh, Uri uri, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                if (uri != null) {
                                    parcelObtain.writeInt(1);
                                    uri.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1915dG.transact(9, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1964a(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1915dG.transact(1, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1965a(InterfaceC1077fh interfaceC1077fh, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1915dG.transact(2, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: a */
                        public void mo1966a(InterfaceC1077fh interfaceC1077fh, List<String> list) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeStringList(list);
                                this.f1915dG.transact(34, parcelObtain, parcelObtain2, 0);
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
                            return this.f1915dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: b */
                        public void mo1967b(InterfaceC1077fh interfaceC1077fh) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                this.f1915dG.transact(19, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: b */
                        public void mo1968b(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1915dG.transact(3, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: c */
                        public void mo1969c(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1915dG.transact(18, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: cW */
                        public String mo1970cW() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                this.f1915dG.transact(41, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: cX */
                        public boolean mo1971cX() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                this.f1915dG.transact(42, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: cY */
                        public String mo1972cY() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                this.f1915dG.transact(43, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        public void clearDefaultAccount() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                this.f1915dG.transact(6, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        /* JADX INFO: renamed from: d */
                        public void mo1973d(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeStrongBinder(interfaceC1077fh != null ? interfaceC1077fh.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1915dG.transact(40, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        public String getAccountName() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                this.f1915dG.transact(5, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                String string = parcelObtain2.readString();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return string;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1080fk
                        public void removeMoment(String momentId) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                                parcelObtain.writeString(momentId);
                                this.f1915dG.transact(17, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: aq */
                    public static InterfaceC1080fk m1974aq(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1080fk)) ? new C1342a(iBinder) : (InterfaceC1080fk) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                        switch (code) {
                            case 1:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1964a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readString());
                                reply.writeNoException();
                                return true;
                            case 2:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1965a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readString(), data.readString());
                                reply.writeNoException();
                                return true;
                            case 3:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1968b(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readString());
                                reply.writeNoException();
                                return true;
                            case 4:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1959a(data.readInt() != 0 ? C1045ec.CREATOR.m1721s(data) : null);
                                reply.writeNoException();
                                return true;
                            case 5:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                String accountName = getAccountName();
                                reply.writeNoException();
                                reply.writeString(accountName);
                                return true;
                            case 6:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                clearDefaultAccount();
                                reply.writeNoException();
                                return true;
                            case 8:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1960a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 9:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1963a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                                reply.writeNoException();
                                return true;
                            case 14:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1962a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readInt(), data.readString(), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readString(), data.readString());
                                reply.writeNoException();
                                return true;
                            case 16:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1961a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readInt(), data.readInt(), data.readInt(), data.readString());
                                reply.writeNoException();
                                return true;
                            case 17:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                removeMoment(data.readString());
                                reply.writeNoException();
                                return true;
                            case 18:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1969c(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readString());
                                reply.writeNoException();
                                return true;
                            case 19:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1967b(InterfaceC1077fh.a.m1954an(data.readStrongBinder()));
                                reply.writeNoException();
                                return true;
                            case 34:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1966a(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.createStringArrayList());
                                reply.writeNoException();
                                return true;
                            case 40:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                mo1973d(InterfaceC1077fh.a.m1954an(data.readStrongBinder()), data.readString());
                                reply.writeNoException();
                                return true;
                            case 41:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                String strCW = mo1970cW();
                                reply.writeNoException();
                                reply.writeString(strCW);
                                return true;
                            case 42:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                boolean zCX = mo1971cX();
                                reply.writeNoException();
                                reply.writeInt(zCX ? 1 : 0);
                                return true;
                            case 43:
                                data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                                String strCY = mo1972cY();
                                reply.writeNoException();
                                reply.writeString(strCY);
                                return true;
                            case 1598968902:
                                reply.writeString("com.google.android.gms.plus.internal.IPlusService");
                                return true;
                            default:
                                return super.onTransact(code, data, reply, flags);
                        }
                    }
                }

                /* JADX INFO: renamed from: a */
                void mo1959a(C1045ec c1045ec) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1960a(InterfaceC1077fh interfaceC1077fh) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1961a(InterfaceC1077fh interfaceC1077fh, int i, int i2, int i3, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1962a(InterfaceC1077fh interfaceC1077fh, int i, String str, Uri uri, String str2, String str3) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1963a(InterfaceC1077fh interfaceC1077fh, Uri uri, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1964a(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1965a(InterfaceC1077fh interfaceC1077fh, String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1966a(InterfaceC1077fh interfaceC1077fh, List<String> list) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1967b(InterfaceC1077fh interfaceC1077fh) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1968b(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1969c(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException;

                /* JADX INFO: renamed from: cW */
                String mo1970cW() throws RemoteException;

                /* JADX INFO: renamed from: cX */
                boolean mo1971cX() throws RemoteException;

                /* JADX INFO: renamed from: cY */
                String mo1972cY() throws RemoteException;

                void clearDefaultAccount() throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1973d(InterfaceC1077fh interfaceC1077fh, String str) throws RemoteException;

                String getAccountName() throws RemoteException;

                void removeMoment(String str) throws RemoteException;
            }
