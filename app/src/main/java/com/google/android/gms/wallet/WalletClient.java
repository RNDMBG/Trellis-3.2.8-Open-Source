            package com.google.android.gms.wallet;

            import android.app.Activity;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1106gj;
            
            
            public class WalletClient implements GooglePlayServicesClient {

                
                private final C1106gj f2527uq;

                public WalletClient(Activity activity, int environment, String accountName, int theme, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this.f2527uq = new C1106gj(activity, connectionCallbacks, connectionFailedListener, environment, accountName, theme);
                }

                public WalletClient(Activity activity, int environment, String accountName, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this(activity, environment, accountName, 0, connectionCallbacks, connectionFailedListener);
                }

                public void changeMaskedWallet(String googleTransactionId, String merchantTransactionId, int requestCode) {
                    this.f2527uq.changeMaskedWallet(googleTransactionId, merchantTransactionId, requestCode);
                }

                public void checkForPreAuthorization(int requestCode) {
                    this.f2527uq.checkForPreAuthorization(requestCode);
                }

                @Override 
                public void connect() {
                    this.f2527uq.connect();
                }

                @Override 
                public void disconnect() {
                    this.f2527uq.disconnect();
                }

                @Override 
                public boolean isConnected() {
                    return this.f2527uq.isConnected();
                }

                @Override 
                public boolean isConnecting() {
                    return this.f2527uq.isConnecting();
                }

                @Override 
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2527uq.isConnectionCallbacksRegistered(listener);
                }

                @Override 
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2527uq.isConnectionFailedListenerRegistered(listener);
                }

                public void loadFullWallet(FullWalletRequest request, int requestCode) {
                    this.f2527uq.loadFullWallet(request, requestCode);
                }

                public void loadMaskedWallet(MaskedWalletRequest request, int requestCode) {
                    this.f2527uq.loadMaskedWallet(request, requestCode);
                }

                public void notifyTransactionStatus(NotifyTransactionStatusRequest request) {
                    this.f2527uq.notifyTransactionStatus(request);
                }

                @Override 
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2527uq.registerConnectionCallbacks(listener);
                }

                @Override 
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2527uq.registerConnectionFailedListener(listener);
                }

                @Override 
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2527uq.unregisterConnectionCallbacks(listener);
                }

                @Override 
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2527uq.unregisterConnectionFailedListener(listener);
                }
            }
