            package com.google.android.gms.panorama;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1075ff;
            
            
            public class PanoramaClient implements GooglePlayServicesClient {

                
                private final C1075ff f2371qU;

                public interface OnPanoramaInfoLoadedListener {
                    void onPanoramaInfoLoaded(ConnectionResult connectionResult, Intent intent);
                }

                
                public interface InterfaceC1196a {
                    
                    void m2329a(ConnectionResult connectionResult, int i, Intent intent);
                }

                public PanoramaClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this.f2371qU = new C1075ff(context, connectionCallbacks, connectionFailedListener);
                }

                @Override 
                public void connect() {
                    this.f2371qU.connect();
                }

                @Override 
                public void disconnect() {
                    this.f2371qU.disconnect();
                }

                @Override 
                public boolean isConnected() {
                    return this.f2371qU.isConnected();
                }

                @Override 
                public boolean isConnecting() {
                    return this.f2371qU.isConnecting();
                }

                @Override 
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2371qU.isConnectionCallbacksRegistered(listener);
                }

                @Override 
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2371qU.isConnectionFailedListenerRegistered(listener);
                }

                public void loadPanoramaInfo(OnPanoramaInfoLoadedListener listener, Uri uri) {
                    this.f2371qU.m1944a(listener, uri, false);
                }

                public void loadPanoramaInfoAndGrantAccess(OnPanoramaInfoLoadedListener listener, Uri uri) {
                    this.f2371qU.m1944a(listener, uri, true);
                }

                @Override 
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2371qU.registerConnectionCallbacks(listener);
                }

                @Override 
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2371qU.registerConnectionFailedListener(listener);
                }

                @Override 
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2371qU.unregisterConnectionCallbacks(listener);
                }

                @Override 
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2371qU.unregisterConnectionFailedListener(listener);
                }
            }
