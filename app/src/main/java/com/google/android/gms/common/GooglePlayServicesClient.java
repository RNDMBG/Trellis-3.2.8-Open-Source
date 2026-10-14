            package com.google.android.gms.common;

            import android.os.Bundle;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface GooglePlayServicesClient {

                public interface ConnectionCallbacks {
                    void onConnected(Bundle bundle);

                    void onDisconnected();
                }

                public interface OnConnectionFailedListener {
                    void onConnectionFailed(ConnectionResult connectionResult);
                }

                void connect();

                void disconnect();

                boolean isConnected();

                boolean isConnecting();

                boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks);

                boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener);

                void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

                void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);

                void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks);

                void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener);
            }
