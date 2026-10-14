            package com.google.android.gms.ads;

            import android.app.Activity;
            import android.os.Bundle;
            import android.os.RemoteException;
            import com.google.android.gms.internal.C0974bm;
            import com.google.android.gms.internal.C1002cn;
            import com.google.android.gms.internal.InterfaceC0975bn;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AdActivity extends Activity {
                public static final String CLASS_NAME = "com.google.android.gms.ads.AdActivity";
                public static final String SIMPLE_CLASS_NAME = "AdActivity";

                /* JADX INFO: renamed from: dH */
                private InterfaceC0975bn f879dH;

                @Override // android.app.Activity
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    this.f879dH = C0974bm.m1338a(this);
                    if (this.f879dH == null) {
                        C1002cn.m1458q("Could not create ad overlay.");
                        finish();
                        return;
                    }
                    try {
                        this.f879dH.onCreate(savedInstanceState);
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onCreate to ad overlay:", e);
                        finish();
                    }
                }

                @Override // android.app.Activity
                protected void onDestroy() {
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onDestroy();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onDestroy to ad overlay:", e);
                    }
                    super.onDestroy();
                }

                @Override // android.app.Activity
                protected void onPause() {
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onPause();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onPause to ad overlay:", e);
                        finish();
                    }
                    super.onPause();
                }

                @Override // android.app.Activity
                protected void onRestart() {
                    super.onRestart();
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onRestart();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onRestart to ad overlay:", e);
                        finish();
                    }
                }

                @Override // android.app.Activity
                protected void onResume() {
                    super.onResume();
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onResume();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onResume to ad overlay:", e);
                        finish();
                    }
                }

                @Override // android.app.Activity
                protected void onSaveInstanceState(Bundle outState) {
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onSaveInstanceState(outState);
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onSaveInstanceState to ad overlay:", e);
                        finish();
                    }
                    super.onSaveInstanceState(outState);
                }

                @Override // android.app.Activity
                protected void onStart() {
                    super.onStart();
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onStart();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onStart to ad overlay:", e);
                        finish();
                    }
                }

                @Override // android.app.Activity
                protected void onStop() {
                    try {
                        if (this.f879dH != null) {
                            this.f879dH.onStop();
                        }
                    } catch (RemoteException e) {
                        C1002cn.m1452b("Could not forward onStop to ad overlay:", e);
                        finish();
                    }
                    super.onStop();
                }
            }
