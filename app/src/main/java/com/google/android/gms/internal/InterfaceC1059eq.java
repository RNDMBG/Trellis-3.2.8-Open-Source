            package com.google.android.gms.internal;

            import android.os.Binder;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.Parcel;
            import android.os.RemoteException;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.GamesClient;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.eq */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface InterfaceC1059eq extends IInterface {

                /* JADX INFO: renamed from: com.google.android.gms.internal.eq$a */
                public static abstract class a extends Binder implements InterfaceC1059eq {

                    /* JADX INFO: renamed from: com.google.android.gms.internal.eq$a$a, reason: collision with other inner class name */
                    private static class C1333a implements InterfaceC1059eq {

                        /* JADX INFO: renamed from: dG */
                        private IBinder f1848dG;

                        C1333a(IBinder iBinder) {
                            this.f1848dG = iBinder;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: M */
                        public void mo1742M(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1848dG.transact(5013, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: N */
                        public void mo1743N(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1848dG.transact(5015, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: O */
                        public void mo1744O(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1848dG.transact(5036, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: P */
                        public void mo1745P(int i) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                this.f1848dG.transact(5040, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: a */
                        public void mo1746a(int i, int i2, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                parcelObtain.writeInt(i2);
                                parcelObtain.writeString(str);
                                this.f1848dG.transact(5033, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: a */
                        public void mo1747a(int i, String str, boolean z) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                parcelObtain.writeInt(z ? 1 : 0);
                                this.f1848dG.transact(5034, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: a */
                        public void mo1748a(C0891d c0891d, C0891d c0891d2) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                if (c0891d2 != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d2.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5005, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: a */
                        public void mo1749a(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5026, parcelObtain, parcelObtain2, 0);
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
                            return this.f1848dG;
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: b */
                        public void mo1750b(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5002, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: b */
                        public void mo1751b(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5027, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: c */
                        public void mo1752c(int i, String str) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(i);
                                parcelObtain.writeString(str);
                                this.f1848dG.transact(5001, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: c */
                        public void mo1753c(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5004, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: c */
                        public void mo1754c(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5028, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: d */
                        public void mo1755d(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5006, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: d */
                        public void mo1756d(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5029, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: e */
                        public void mo1757e(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5007, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: e */
                        public void mo1758e(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5030, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: f */
                        public void mo1759f(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5008, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: f */
                        public void mo1760f(C0891d c0891d, String[] strArr) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                parcelObtain.writeStringArray(strArr);
                                this.f1848dG.transact(5031, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: g */
                        public void mo1761g(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5009, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: h */
                        public void mo1762h(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5010, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: i */
                        public void mo1763i(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5011, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: j */
                        public void mo1764j(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5017, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: k */
                        public void mo1765k(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5037, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: l */
                        public void mo1766l(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5012, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: m */
                        public void mo1767m(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5014, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: n */
                        public void mo1768n(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5018, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: o */
                        public void mo1769o(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5019, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onAchievementUpdated(int statusCode, String achievementId) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeString(achievementId);
                                this.f1848dG.transact(5003, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onLeftRoom(int statusCode, String roomId) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeInt(statusCode);
                                parcelObtain.writeString(roomId);
                                this.f1848dG.transact(5020, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onP2PConnected(String participantId) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeString(participantId);
                                this.f1848dG.transact(GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onP2PDisconnected(String participantId) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                parcelObtain.writeString(participantId);
                                this.f1848dG.transact(6002, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onRealTimeMessageReceived(RealTimeMessage message) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (message != null) {
                                    parcelObtain.writeInt(1);
                                    message.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5032, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        public void onSignOutComplete() throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                this.f1848dG.transact(5016, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: p */
                        public void mo1770p(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5021, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: q */
                        public void mo1771q(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5022, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: r */
                        public void mo1772r(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5023, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: s */
                        public void mo1773s(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5024, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: t */
                        public void mo1774t(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5025, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: u */
                        public void mo1775u(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5038, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: v */
                        public void mo1776v(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5035, parcelObtain, parcelObtain2, 0);
                                parcelObtain2.readException();
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                            } catch (Throwable th) {
                                parcelObtain2.recycle();
                                parcelObtain.recycle();
                                throw th;
                            }
                        }

                        @Override // com.google.android.gms.internal.InterfaceC1059eq
                        /* JADX INFO: renamed from: w */
                        public void mo1777w(C0891d c0891d) throws RemoteException {
                            Parcel parcelObtain = Parcel.obtain();
                            Parcel parcelObtain2 = Parcel.obtain();
                            try {
                                parcelObtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                                if (c0891d != null) {
                                    parcelObtain.writeInt(1);
                                    c0891d.writeToParcel(parcelObtain, 0);
                                } else {
                                    parcelObtain.writeInt(0);
                                }
                                this.f1848dG.transact(5039, parcelObtain, parcelObtain2, 0);
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
                        attachInterface(this, "com.google.android.gms.games.internal.IGamesCallbacks");
                    }

                    /* JADX INFO: renamed from: B */
                    public static InterfaceC1059eq m1805B(IBinder iBinder) {
                        if (iBinder == null) {
                            return null;
                        }
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC1059eq)) ? new C1333a(iBinder) : (InterfaceC1059eq) iInterfaceQueryLocalInterface;
                    }

                    @Override // android.os.IInterface
                    public IBinder asBinder() {
                        return this;
                    }

                    @Override // android.os.Binder
                    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                        switch (i) {
                            case 5001:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1752c(parcel.readInt(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5002:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1750b(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5003:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onAchievementUpdated(parcel.readInt(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5004:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1753c(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5005:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1748a(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5006:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1755d(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5007:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1757e(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5008:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1759f(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5009:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1761g(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5010:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1762h(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5011:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1763i(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5012:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1766l(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5013:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1742M(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5014:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1767m(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5015:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1743N(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5016:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onSignOutComplete();
                                parcel2.writeNoException();
                                return true;
                            case 5017:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1764j(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5018:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1768n(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5019:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1769o(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5020:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onLeftRoom(parcel.readInt(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5021:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1770p(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5022:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1771q(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5023:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1772r(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5024:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1773s(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5025:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1774t(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5026:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1749a(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5027:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1751b(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5028:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1754c(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5029:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1756d(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5030:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1758e(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5031:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1760f(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null, parcel.createStringArray());
                                parcel2.writeNoException();
                                return true;
                            case 5032:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onRealTimeMessageReceived(parcel.readInt() != 0 ? RealTimeMessage.CREATOR.createFromParcel(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5033:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1746a(parcel.readInt(), parcel.readInt(), parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 5034:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1747a(parcel.readInt(), parcel.readString(), parcel.readInt() != 0);
                                parcel2.writeNoException();
                                return true;
                            case 5035:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1776v(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5036:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1744O(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case 5037:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1765k(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5038:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1775u(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5039:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1777w(parcel.readInt() != 0 ? C0891d.CREATOR.m1054h(parcel) : null);
                                parcel2.writeNoException();
                                return true;
                            case 5040:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                mo1745P(parcel.readInt());
                                parcel2.writeNoException();
                                return true;
                            case GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER /* 6001 */:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onP2PConnected(parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 6002:
                                parcel.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                                onP2PDisconnected(parcel.readString());
                                parcel2.writeNoException();
                                return true;
                            case 1598968902:
                                parcel2.writeString("com.google.android.gms.games.internal.IGamesCallbacks");
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                    }
                }

                /* JADX INFO: renamed from: M */
                void mo1742M(int i) throws RemoteException;

                /* JADX INFO: renamed from: N */
                void mo1743N(int i) throws RemoteException;

                /* JADX INFO: renamed from: O */
                void mo1744O(int i) throws RemoteException;

                /* JADX INFO: renamed from: P */
                void mo1745P(int i) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1746a(int i, int i2, String str) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1747a(int i, String str, boolean z) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1748a(C0891d c0891d, C0891d c0891d2) throws RemoteException;

                /* JADX INFO: renamed from: a */
                void mo1749a(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1750b(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: b */
                void mo1751b(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1752c(int i, String str) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1753c(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: c */
                void mo1754c(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1755d(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: d */
                void mo1756d(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo1757e(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: e */
                void mo1758e(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1759f(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: f */
                void mo1760f(C0891d c0891d, String[] strArr) throws RemoteException;

                /* JADX INFO: renamed from: g */
                void mo1761g(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: h */
                void mo1762h(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: i */
                void mo1763i(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: j */
                void mo1764j(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: k */
                void mo1765k(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: l */
                void mo1766l(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: m */
                void mo1767m(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: n */
                void mo1768n(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: o */
                void mo1769o(C0891d c0891d) throws RemoteException;

                void onAchievementUpdated(int i, String str) throws RemoteException;

                void onLeftRoom(int i, String str) throws RemoteException;

                void onP2PConnected(String str) throws RemoteException;

                void onP2PDisconnected(String str) throws RemoteException;

                void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) throws RemoteException;

                void onSignOutComplete() throws RemoteException;

                /* JADX INFO: renamed from: p */
                void mo1770p(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: q */
                void mo1771q(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: r */
                void mo1772r(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: s */
                void mo1773s(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: t */
                void mo1774t(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: u */
                void mo1775u(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: v */
                void mo1776v(C0891d c0891d) throws RemoteException;

                /* JADX INFO: renamed from: w */
                void mo1777w(C0891d c0891d) throws RemoteException;
            }
