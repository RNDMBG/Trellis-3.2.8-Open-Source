            package com.google.android.gms.internal;

            import android.accounts.Account;
            import android.app.Activity;
            import android.app.PendingIntent;
            import android.content.Intent;
            import android.content.IntentSender;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import android.text.TextUtils;
            import android.util.Log;
            import com.google.android.gms.auth.GoogleAuthUtil;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.wallet.FullWallet;
            import com.google.android.gms.wallet.FullWalletRequest;
            import com.google.android.gms.wallet.MaskedWallet;
            import com.google.android.gms.wallet.MaskedWalletRequest;
            import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
            import com.google.android.gms.wallet.WalletConstants;
            
            
            
            public class C1106gj extends AbstractC1020de<InterfaceC1104gh> {

                
                private final Activity f2105fD;

                
                private final String f2106it;
                private final int mTheme;

                
                private final int f2107us;

                
                final class a extends InterfaceC1105gi.a {

                    
                    private final int f2108ky;

                    public a(int i) {
                        this.f2108ky = i;
                    }

                    @Override 
                    
                    public void mo2100a(int i, FullWallet fullWallet, Bundle bundle) {
                        int i2;
                        Intent intent;
                        ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT") : null);
                        if (connectionResult.hasResolution()) {
                            try {
                                connectionResult.startResolutionForResult(C1106gj.m2104a(C1106gj.this), this.f2108ky);
                                return;
                            } catch (IntentSender.SendIntentException e) {
                                Log.w("WalletClientImpl", "Exception starting pending intent", e);
                                return;
                            }
                        }
                        if (connectionResult.isSuccess()) {
                            i2 = -1;
                            intent = new Intent();
                            intent.putExtra(WalletConstants.EXTRA_FULL_WALLET, fullWallet);
                        } else {
                            i2 = i == 408 ? 0 : 1;
                            intent = new Intent();
                            intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
                        }
                        try {
                            C1106gj.m2104a(C1106gj.this).createPendingResult(this.f2108ky, intent, 1073741824).send(i2);
                        } catch (PendingIntent.CanceledException e2) {
                            Log.w("WalletClientImpl", "Exception setting pending result", e2);
                        }
                    }

                    @Override 
                    
                    public void mo2101a(int i, MaskedWallet maskedWallet, Bundle bundle) {
                        int i2;
                        Intent intent;
                        ConnectionResult connectionResult = new ConnectionResult(i, bundle != null ? (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT") : null);
                        if (connectionResult.hasResolution()) {
                            try {
                                connectionResult.startResolutionForResult(C1106gj.m2104a(C1106gj.this), this.f2108ky);
                                return;
                            } catch (IntentSender.SendIntentException e) {
                                Log.w("WalletClientImpl", "Exception starting pending intent", e);
                                return;
                            }
                        }
                        if (connectionResult.isSuccess()) {
                            i2 = -1;
                            intent = new Intent();
                            intent.putExtra(WalletConstants.EXTRA_MASKED_WALLET, maskedWallet);
                        } else {
                            i2 = i == 408 ? 0 : 1;
                            intent = new Intent();
                            intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
                        }
                        try {
                            C1106gj.m2104a(C1106gj.this).createPendingResult(this.f2108ky, intent, 1073741824).send(i2);
                        } catch (PendingIntent.CanceledException e2) {
                            Log.w("WalletClientImpl", "Exception setting pending result", e2);
                        }
                    }

                    @Override 
                    
                    public void mo2102a(int i, boolean z, Bundle bundle) {
                        Intent intent = new Intent();
                        intent.putExtra(WalletConstants.EXTRA_IS_USER_PREAUTHORIZED, z);
                        try {
                            C1106gj.m2104a(C1106gj.this).createPendingResult(this.f2108ky, intent, 1073741824).send(-1);
                        } catch (PendingIntent.CanceledException e) {
                            Log.w("WalletClientImpl", "Exception setting pending result", e);
                        }
                    }
                }

                public C1106gj(Activity activity, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, int i, String str, int i2) {
                    super(activity, connectionCallbacks, onConnectionFailedListener, new String[0]);
                    this.f2105fD = activity;
                    this.f2107us = i;
                    this.f2106it = str;
                    this.mTheme = i2;
                }

                
                static  Activity m2104a(C1106gj c1106gj) {
                    return c1106gj.f2105fD;
                }

                
                private Bundle m2105eb() {
                    Bundle bundle = new Bundle();
                    bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", this.f2107us);
                    bundle.putString("androidPackageName", this.f2105fD.getPackageName());
                    if (!TextUtils.isEmpty(this.f2106it)) {
                        bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", new Account(this.f2106it, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE));
                    }
                    bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", this.mTheme);
                    return bundle;
                }

                @Override 
                
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    interfaceC1025dj.mo1581a(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                }

                @Override 
                
                protected String mo1366ag() {
                    return "com.google.android.gms.wallet.service.BIND";
                }

                @Override 
                
                protected String mo1367ah() {
                    return "com.google.android.gms.wallet.internal.IOwService";
                }

                
                protected InterfaceC1104gh m2106au(IBinder iBinder) {
                    return InterfaceC1104gh.a.m2099as(iBinder);
                }

                public void changeMaskedWallet(String googleTransactionId, String merchantTransactionId, int requestCode) {
                    Bundle bundleM2105eb = m2105eb();
                    a aVar = new a(requestCode);
                    try {
                        m1549bd().mo2098a(googleTransactionId, merchantTransactionId, bundleM2105eb, aVar);
                    } catch (RemoteException e) {
                        Log.e("WalletClientImpl", "RemoteException changing masked wallet", e);
                        aVar.mo2101a(8, (MaskedWallet) null, (Bundle) null);
                    }
                }

                public void checkForPreAuthorization(int requestCode) {
                    Bundle bundleM2105eb = m2105eb();
                    a aVar = new a(requestCode);
                    try {
                        m1549bd().mo2094a(bundleM2105eb, aVar);
                    } catch (RemoteException e) {
                        Log.e("WalletClientImpl", "RemoteException during checkForPreAuthorization", e);
                        aVar.mo2102a(8, false, (Bundle) null);
                    }
                }

                public void loadFullWallet(FullWalletRequest request, int requestCode) {
                    a aVar = new a(requestCode);
                    try {
                        m1549bd().mo2095a(request, m2105eb(), aVar);
                    } catch (RemoteException e) {
                        Log.e("WalletClientImpl", "RemoteException getting full wallet", e);
                        aVar.mo2100a(8, (FullWallet) null, (Bundle) null);
                    }
                }

                public void loadMaskedWallet(MaskedWalletRequest request, int requestCode) {
                    Bundle bundleM2105eb = m2105eb();
                    a aVar = new a(requestCode);
                    try {
                        m1549bd().mo2096a(request, bundleM2105eb, aVar);
                    } catch (RemoteException e) {
                        Log.e("WalletClientImpl", "RemoteException getting masked wallet", e);
                        aVar.mo2101a(8, (MaskedWallet) null, (Bundle) null);
                    }
                }

                public void notifyTransactionStatus(NotifyTransactionStatusRequest request) {
                    try {
                        m1549bd().mo2097a(request, m2105eb());
                    } catch (RemoteException e) {
                    }
                }

                @Override 
                
                protected  IInterface mo1370p(IBinder iBinder) {
                    return m2106au(iBinder);
                }
            }
