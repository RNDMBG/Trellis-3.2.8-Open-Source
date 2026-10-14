            package com.google.android.gms.location;

            import android.app.PendingIntent;
            import android.content.Context;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1068ez;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class ActivityRecognitionClient implements GooglePlayServicesClient {

                /* JADX INFO: renamed from: ou */
                private final C1068ez f2166ou;

                public ActivityRecognitionClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this.f2166ou = new C1068ez(context, connectedListener, connectionFailedListener, "activity_recognition");
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f2166ou.connect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f2166ou.disconnect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f2166ou.isConnected();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    return this.f2166ou.isConnecting();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2166ou.isConnectionCallbacksRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2166ou.isConnectionFailedListenerRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2166ou.registerConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2166ou.registerConnectionFailedListener(listener);
                }

                public void removeActivityUpdates(PendingIntent callbackIntent) {
                    this.f2166ou.removeActivityUpdates(callbackIntent);
                }

                public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
                    this.f2166ou.requestActivityUpdates(detectionIntervalMillis, callbackIntent);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2166ou.unregisterConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2166ou.unregisterConnectionFailedListener(listener);
                }
            }
