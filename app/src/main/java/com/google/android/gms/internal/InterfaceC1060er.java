            package com.google.android.gms.internal;

            import android.net.Uri;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.ParcelFileDescriptor;
            import android.os.RemoteException;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.GamesClient;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.er */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1060er extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.er$a */
                public static abstract class a extends Binder implements InterfaceC1060er {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.er$a$a, reason: collision with other inner class name */
                    private static class C1334a implements InterfaceC1060er {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1849dG;

                        C1334a(IBinder iBinder) {
                            this.f1849dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: L */
                        public String mo1806L(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5064, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: M */
                        public String mo1807M(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5035, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: N */
                        public void mo1808N(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5050, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: O */
                        public int mo1809O(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5060, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: P */
                        public Uri mo1810P(String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5066, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                Uri uri = parcelObtain2.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return uri;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public int mo1811a(InterfaceC1059eq interfaceC1059eq, byte[] bArr, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeByteArray(bArr);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(5033, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1812a(IBinder iBinder, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(iBinder);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(5005, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1813a(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5002, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1814a(InterfaceC1059eq interfaceC1059eq, int i, int i2, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5044, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1815a(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5015, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1816a(InterfaceC1059eq interfaceC1059eq, long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5058, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1817a(InterfaceC1059eq interfaceC1059eq, Bundle bundle, int i, int i2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                this.f1849dG.transact(5021, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1818a(InterfaceC1059eq interfaceC1059eq, IBinder iBinder, int i, String[] strArr, Bundle bundle, boolean z, long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeStrongBinder(iBinder);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeStringArray(strArr);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5030, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1819a(InterfaceC1059eq interfaceC1059eq, IBinder iBinder, String str, boolean z, long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeStrongBinder(iBinder);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5031, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1820a(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5008, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1821a(InterfaceC1059eq interfaceC1059eq, String str, int i, int i2, int i3, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(i3);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5019, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1822a(InterfaceC1059eq interfaceC1059eq, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeStrongBinder(iBinder);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(5025, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1823a(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5045, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1824a(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2, boolean z3, boolean z4) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                parcelObtain.writeInt(z3 ? 1 : 0);
                                parcelObtain.writeInt(z4 ? 1 : 0);
                                this.f1849dG.transact(6501, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1825a(InterfaceC1059eq interfaceC1059eq, String str, long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5016, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1826a(InterfaceC1059eq interfaceC1059eq, String str, long j, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeLong(j);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(GamesClient.STATUS_INVALID_REAL_TIME_ROOM_ID, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1827a(InterfaceC1059eq interfaceC1059eq, String str, IBinder iBinder, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeStrongBinder(iBinder);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(5023, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1828a(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(5009, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1829a(InterfaceC1059eq interfaceC1059eq, String str, String str2, int i, int i2, int i3, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(i3);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5039, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1830a(InterfaceC1059eq interfaceC1059eq, String str, String str2, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6002, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1831a(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5054, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1832a(InterfaceC1059eq interfaceC1059eq, String str, boolean z, long[] jArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeLongArray(jArr);
                                this.f1849dG.transact(5011, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1833a(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5063, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: a */
                        public void mo1834a(String str, String str2, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeInt(i);
                                this.f1849dG.transact(5051, parcelObtain, parcelObtain2, 0);
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
                            return this.f1849dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public int mo1835b(byte[] bArr, String str, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeByteArray(bArr);
                                parcelObtain.writeString(str);
                                parcelObtain.writeStringArray(strArr);
                                this.f1849dG.transact(5034, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1836b(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5017, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1837b(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5046, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1838b(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5010, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1839b(InterfaceC1059eq interfaceC1059eq, String str, int i, int i2, int i3, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(i3);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5020, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1840b(InterfaceC1059eq interfaceC1059eq, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeStrongBinder(iBinder);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(GamesClient.STATUS_PARTICIPANT_NOT_CONNECTED, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1841b(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5501, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1842b(InterfaceC1059eq interfaceC1059eq, String str, IBinder iBinder, Bundle bundle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeStrongBinder(iBinder);
                                if (bundle != null) {
                                    parcelObtain.writeInt(1);
                                    bundle.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(5024, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1843b(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(5038, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1844b(InterfaceC1059eq interfaceC1059eq, String str, String str2, int i, int i2, int i3, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeInt(i3);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(5040, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1845b(InterfaceC1059eq interfaceC1059eq, String str, String str2, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6506, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1846b(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6502, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: b */
                        public void mo1847b(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: bT */
                        public void mo1848bT() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5006, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: bU */
                        public C0891d mo1849bU() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5013, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                C0891d c0891dM1054h = parcelObtain2.readInt() != 0 ? C0891d.CREATOR.m1054h(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return c0891dM1054h;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: bV */
                        public boolean mo1850bV() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5067, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: bW */
                        public C0891d mo1851bW() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5502, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                C0891d c0891dM1054h = parcelObtain2.readInt() != 0 ? C0891d.CREATOR.m1054h(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return c0891dM1054h;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: ba */
                        public Bundle mo1852ba() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5004, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                Bundle bundle = parcelObtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return bundle;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1853c(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5022, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1854c(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(5048, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1855c(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5014, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1856c(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(5041, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1857c(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6504, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: c */
                        public void mo1858c(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6503, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        public void clearNotifications(int notificationTypes) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeInt(notificationTypes);
                                this.f1849dG.transact(5036, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: d */
                        public void mo1859d(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5026, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: d */
                        public void mo1860d(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(6003, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: d */
                        public void mo1861d(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5018, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: d */
                        public void mo1862d(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1849dG.transact(6505, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: e */
                        public ParcelFileDescriptor mo1863e(Uri uri) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                if (uri != null) {
                                    parcelObtain.writeInt(1);
                                    uri.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1849dG.transact(6507, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                ParcelFileDescriptor parcelFileDescriptor = parcelObtain2.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcelObtain2) : null;
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return parcelFileDescriptor;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: e */
                        public void mo1864e(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5027, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: e */
                        public void mo1865e(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(z ? 1 : 0);
                                parcelObtain.writeInt(z2 ? 1 : 0);
                                this.f1849dG.transact(6004, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: e */
                        public void mo1866e(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5032, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: f */
                        public void mo1867f(long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5001, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: f */
                        public void mo1868f(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5047, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: f */
                        public void mo1869f(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5037, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: f */
                        public void mo1870f(String str, String str2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeString(str2);
                                this.f1849dG.transact(5065, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: g */
                        public void mo1871g(long j) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeLong(j);
                                this.f1849dG.transact(5059, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: g */
                        public void mo1872g(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5049, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: g */
                        public void mo1873g(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5042, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        public String getAppId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5003, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        public String getCurrentAccountName() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5007, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        public String getCurrentPlayerId() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                this.f1849dG.transact(5012, parcelObtain, parcelObtain2, 0);
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

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: h */
                        public void mo1874h(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5056, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: h */
                        public void mo1875h(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5043, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: i */
                        public void mo1876i(InterfaceC1059eq interfaceC1059eq) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                this.f1849dG.transact(5062, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: i */
                        public void mo1877i(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5052, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: i */
                        public void mo1878i(String str, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                this.f1849dG.transact(5029, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: j */
                        public int mo1879j(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5053, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                int i = parcelObtain2.readInt();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                return i;
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: j */
                        public void mo1880j(String str, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                this.f1849dG.transact(5028, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: k */
                        public void mo1881k(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5061, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: k */
                        public void mo1882k(String str, int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(i);
                                this.f1849dG.transact(5055, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: l */
                        public void mo1883l(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(5057, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        /* JADX INFO: renamed from: m */
                        public void mo1884m(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeStrongBinder(interfaceC1059eq != null ? interfaceC1059eq.asBinder() : null);
                                parcelObtain.writeString(str);
                                this.f1849dG.transact(GamesClient.STATUS_REAL_TIME_MESSAGE_SEND_FAILED, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1060er
                        public void setUseNewPlayerNotificationsFirstParty(boolean newPlayerStyle) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesService");
                                parcelObtain.writeInt(newPlayerStyle ? 1 : 0);
                                this.f1849dG.transact(5068, parcelObtain, parcelObtain2, 0);
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

                    /* JADX INFO: renamed from: C */
                    public static InterfaceC1060er m1885C(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesService");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1060er)) ? new C1334a(iBinder) : (InterfaceC1060er) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 5001:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1867f(parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5002:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1813a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5003:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                String appId = getAppId();
                                parcel2.writeNoException();
                                parcel2.writeString(appId);
                                return true;
                            case 5004:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                Bundle bundleBa = mo1852ba();
                                parcel2.writeNoException();
                                if (bundleBa == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                bundleBa.writeToParcel(parcel2, 1);
                                return true;
                            case 5005:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1812a(parcel.readStrongBinder(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5006:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1848bT();
                                parcel2.writeNoException();
                                return true;
                            case 5007:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                String currentAccountName = getCurrentAccountName();
                                parcel2.writeNoException();
                                parcel2.writeString(currentAccountName);
                                return true;
                            case 5008:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1820a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5009:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1828a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5010:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1838b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5011:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1832a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt() != 0, parcel.createLongArray());
                                parcel2.writeNoException();
                                return true;
                            case 5012:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                String currentPlayerId = getCurrentPlayerId();
                                parcel2.writeNoException();
                                parcel2.writeString(currentPlayerId);
                                return true;
                            case 5013:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                C0891d c0891dBU = mo1849bU();
                                parcel2.writeNoException();
                                if (c0891dBU == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                c0891dBU.writeToParcel(parcel2, 1);
                                return true;
                            case 5014:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1855c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5015:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1815a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5016:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1825a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5017:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1836b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5018:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1861d(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5019:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1821a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5020:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1839b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5021:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1817a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5022:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1853c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5023:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1827a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readStrongBinder(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5024:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1842b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readStrongBinder(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5025:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1822a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readStrongBinder(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5026:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1859d(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5027:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1864e(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5028:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1880j(parcel.readString(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5029:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1878i(parcel.readString(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5030:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1818a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readStrongBinder(), parcel.readInt(), parcel.createStringArray(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0, parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5031:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1819a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readStrongBinder(), parcel.readString(), parcel.readInt() != 0, parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5032:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1866e(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5033:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                int iA = mo1811a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.createByteArray(), parcel.readString(), parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeInt(iA);
                                return true;
                            case 5034:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                int iB = mo1835b(parcel.createByteArray(), parcel.readString(), parcel.createStringArray());
                                parcel2.writeNoException();
                                parcel2.writeInt(iB);
                                return true;
                            case 5035:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                String strM = mo1807M(parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeString(strM);
                                return true;
                            case 5036:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                clearNotifications(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5037:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1869f(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5038:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1843b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5039:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1829a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5040:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1844b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5041:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1856c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5042:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1873g(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5043:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1875h(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5044:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1814a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5045:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1823a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5046:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1837b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5047:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1868f(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5048:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1854c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5049:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1872g(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5050:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1808N(parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5051:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1834a(parcel.readString(), parcel.readString(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5052:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1877i(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5053:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                int iJ = mo1879j(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeInt(iJ);
                                return true;
                            case 5054:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1831a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5055:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1882k(parcel.readString(), parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5056:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1874h(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5057:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1883l(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5058:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1816a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5059:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1871g(parcel.readLong());
                                parcel2.writeNoException();
                                return true;
                            case 5060:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                int iO = mo1809O(parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeInt(iO);
                                return true;
                            case 5061:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1881k(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5062:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1876i(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                return true;
                            case 5063:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1833a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5064:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                String strL = mo1806L(parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeString(strL);
                                return true;
                            case 5065:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1870f(parcel.readString(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5066:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                Uri uriP = mo1810P(parcel.readString());
                                parcel2.writeNoException();
                                if (uriP == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                uriP.writeToParcel(parcel2, 1);
                                return true;
                            case 5067:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                boolean zBV = mo1850bV();
                                parcel2.writeNoException();
                                parcel2.writeInt(zBV ? 1 : 0);
                                return true;
                            case 5068:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                setUseNewPlayerNotificationsFirstParty(parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5501:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1841b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5502:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                C0891d c0891dBW = mo1851bW();
                                parcel2.writeNoException();
                                if (c0891dBW == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                c0891dBW.writeToParcel(parcel2, 1);
                                return true;
                            case GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER /* 6001 */:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1847b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6002:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1830a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6003:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1860d(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6004:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1865e(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6501:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1824a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6502:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1846b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6503:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1858c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6504:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1857c(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6505:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1862d(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6506:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1845b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 6507:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                ParcelFileDescriptor parcelFileDescriptorE = mo1863e(parcel.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                if (parcelFileDescriptorE == null) {
                                    parcel2.writeInt(0);
                                    return true;
                                }
                                parcel2.writeInt(1);
                                parcelFileDescriptorE.writeToParcel(parcel2, 1);
                                return true;
                            case GamesClient.STATUS_REAL_TIME_MESSAGE_SEND_FAILED /* 7001 */:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1884m(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case GamesClient.STATUS_INVALID_REAL_TIME_ROOM_ID /* 7002 */:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1826a(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readLong(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case GamesClient.STATUS_PARTICIPANT_NOT_CONNECTED /* 7003 */:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesService");
                                mo1840b(InterfaceC1059eq.a.m1805B(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readStrongBinder(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.games.internal.IGamesService");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: L */
                String mo1806L(String str) throws RemoteException;

                /* JADX INFO: renamed from: M */
                String mo1807M(String str) throws RemoteException;

                /* JADX INFO: renamed from: N */
                void mo1808N(String str) throws RemoteException;

                /* JADX INFO: renamed from: O */
                int mo1809O(String str) throws RemoteException;

                /* JADX INFO: renamed from: P */
                Uri mo1810P(String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                int mo1811a(InterfaceC1059eq interfaceC1059eq, byte[] bArr, String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1812a(IBinder iBinder, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1813a(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1814a(InterfaceC1059eq interfaceC1059eq, int i, int i2, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1815a(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1816a(InterfaceC1059eq interfaceC1059eq, long j) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1817a(InterfaceC1059eq interfaceC1059eq, Bundle bundle, int i, int i2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1818a(InterfaceC1059eq interfaceC1059eq, IBinder iBinder, int i, String[] strArr, Bundle bundle, boolean z, long j) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1819a(InterfaceC1059eq interfaceC1059eq, IBinder iBinder, String str, boolean z, long j) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1820a(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1821a(InterfaceC1059eq interfaceC1059eq, String str, int i, int i2, int i3, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1822a(InterfaceC1059eq interfaceC1059eq, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1823a(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1824a(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2, boolean z3, boolean z4) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1825a(InterfaceC1059eq interfaceC1059eq, String str, long j) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1826a(InterfaceC1059eq interfaceC1059eq, String str, long j, String str2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1827a(InterfaceC1059eq interfaceC1059eq, String str, IBinder iBinder, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1828a(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1829a(InterfaceC1059eq interfaceC1059eq, String str, String str2, int i, int i2, int i3, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1830a(InterfaceC1059eq interfaceC1059eq, String str, String str2, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1831a(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1832a(InterfaceC1059eq interfaceC1059eq, String str, boolean z, long[] jArr) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1833a(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1834a(String str, String str2, int i) throws RemoteException;

                /* JADX INFO: renamed from: b */
                int mo1835b(byte[] bArr, String str, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1836b(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1837b(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1838b(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1839b(InterfaceC1059eq interfaceC1059eq, String str, int i, int i2, int i3, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1840b(InterfaceC1059eq interfaceC1059eq, String str, int i, IBinder iBinder, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1841b(InterfaceC1059eq interfaceC1059eq, String str, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1842b(InterfaceC1059eq interfaceC1059eq, String str, IBinder iBinder, Bundle bundle) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1843b(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1844b(InterfaceC1059eq interfaceC1059eq, String str, String str2, int i, int i2, int i3, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1845b(InterfaceC1059eq interfaceC1059eq, String str, String str2, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1846b(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1847b(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: bT */
                void mo1848bT() throws RemoteException;

                /* JADX INFO: renamed from: bU */
                C0891d mo1849bU() throws RemoteException;

                /* JADX INFO: renamed from: bV */
                boolean mo1850bV() throws RemoteException;

                /* JADX INFO: renamed from: bW */
                C0891d mo1851bW() throws RemoteException;

                /* JADX INFO: renamed from: ba */
                Bundle mo1852ba() throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1853c(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1854c(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1855c(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1856c(InterfaceC1059eq interfaceC1059eq, String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1857c(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1858c(InterfaceC1059eq interfaceC1059eq, boolean z) throws RemoteException;

                void clearNotifications(int i) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1859d(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1860d(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1861d(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1862d(InterfaceC1059eq interfaceC1059eq, String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: e */
                ParcelFileDescriptor mo1863e(Uri uri) throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo1864e(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo1865e(InterfaceC1059eq interfaceC1059eq, int i, boolean z, boolean z2) throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo1866e(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1867f(long j) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1868f(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1869f(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1870f(String str, String str2) throws RemoteException;

                /* JADX INFO: renamed from: g */
                void mo1871g(long j) throws RemoteException;

                /* JADX INFO: renamed from: g */
                void mo1872g(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: g */
                void mo1873g(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                String getAppId() throws RemoteException;

                String getCurrentAccountName() throws RemoteException;

                String getCurrentPlayerId() throws RemoteException;

                /* JADX INFO: renamed from: h */
                void mo1874h(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: h */
                void mo1875h(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: i */
                void mo1876i(InterfaceC1059eq interfaceC1059eq) throws RemoteException;

                /* JADX INFO: renamed from: i */
                void mo1877i(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: i */
                void mo1878i(String str, int i) throws RemoteException;

                /* JADX INFO: renamed from: j */
                int mo1879j(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: j */
                void mo1880j(String str, int i) throws RemoteException;

                /* JADX INFO: renamed from: k */
                void mo1881k(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: k */
                void mo1882k(String str, int i) throws RemoteException;

                /* JADX INFO: renamed from: l */
                void mo1883l(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                /* JADX INFO: renamed from: m */
                void mo1884m(InterfaceC1059eq interfaceC1059eq, String str) throws RemoteException;

                void setUseNewPlayerNotificationsFirstParty(boolean z) throws RemoteException;
            }
