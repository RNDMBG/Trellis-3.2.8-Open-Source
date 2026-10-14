            package com.google.android.gms.location;

            import android.app.PendingIntent;
            import android.content.Context;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1068ez;
            
            
            public class ActivityRecognitionClient implements GooglePlayServicesClient {

                
                private final C1068ez f2166ou;

                public ActivityRecognitionClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this.f2166ou = new C1068ez(context, connectedListener, connectionFailedListener, "activity_recognition");
                }

                @Override 
                public void connect() {
                    this.f2166ou.connect();
                }

                @Override 
                public void disconnect() {
                    this.f2166ou.disconnect();
                }

                @Override 
                public boolean isConnected() {
                    return this.f2166ou.isConnected();
                }

                @Override 
                public boolean isConnecting() {
                    return this.f2166ou.isConnecting();
                }

                @Override 
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2166ou.isConnectionCallbacksRegistered(listener);
                }

                @Override 
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2166ou.isConnectionFailedListenerRegistered(listener);
                }

                @Override 
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2166ou.registerConnectionCallbacks(listener);
                }

                @Override 
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2166ou.registerConnectionFailedListener(listener);
                }

                public void removeActivityUpdates(PendingIntent callbackIntent) {
                    this.f2166ou.removeActivityUpdates(callbackIntent);
                }

                public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
                    this.f2166ou.requestActivityUpdates(detectionIntervalMillis, callbackIntent);
                }

                @Override 
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2166ou.unregisterConnectionCallbacks(listener);
                }

                @Override 
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2166ou.unregisterConnectionFailedListener(listener);
                }
            }
