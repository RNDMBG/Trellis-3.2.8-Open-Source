            package com.google.android.gms.location;

            import android.app.PendingIntent;
            import android.content.Context;
            import android.content.Intent;
            import android.location.Location;
            import android.os.Looper;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1068ez;
            import com.google.android.gms.internal.C1070fa;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class LocationClient implements GooglePlayServicesClient {
                public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
                public static final String KEY_MOCK_LOCATION = "mockLocation";

                /* JADX INFO: renamed from: ou */
                private final C1068ez f2183ou;

                public interface OnAddGeofencesResultListener {
                    void onAddGeofencesResult(int i, String[] strArr);
                }

                public interface OnRemoveGeofencesResultListener {
                    void onRemoveGeofencesByPendingIntentResult(int i, PendingIntent pendingIntent);

                    void onRemoveGeofencesByRequestIdsResult(int i, String[] strArr);
                }

                public LocationClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                    this.f2183ou = new C1068ez(context, connectionCallbacks, connectionFailedListener, "location");
                }

                public static int getErrorCode(Intent intent) {
                    return intent.getIntExtra("gms_error_code", -1);
                }

                public static int getGeofenceTransition(Intent intent) {
                    int intExtra = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
                    if (intExtra == -1) {
                        return -1;
                    }
                    if (intExtra == 1 || intExtra == 2 || intExtra == 4) {
                        return intExtra;
                    }
                    return -1;
                }

                public static List<Geofence> getTriggeringGeofences(Intent intent) {
                    ArrayList arrayList = (ArrayList) intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
                    if (arrayList == null) {
                        return null;
                    }
                    ArrayList arrayList2 = new ArrayList(arrayList.size());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(C1070fa.m1931d((byte[]) it.next()));
                    }
                    return arrayList2;
                }

                public static boolean hasError(Intent intent) {
                    return intent.hasExtra("gms_error_code");
                }

                public void addGeofences(List<Geofence> geofences, PendingIntent pendingIntent, OnAddGeofencesResultListener listener) {
                    ArrayList arrayList = null;
                    if (geofences != null) {
                        ArrayList arrayList2 = new ArrayList();
                        for (Geofence geofence : geofences) {
                            C1028dm.m1604b(geofence instanceof C1070fa, "Geofence must be created using Geofence.Builder.");
                            arrayList2.add((C1070fa) geofence);
                        }
                        arrayList = arrayList2;
                    }
                    this.f2183ou.addGeofences(arrayList, pendingIntent, listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f2183ou.connect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f2183ou.disconnect();
                }

                public Location getLastLocation() {
                    return this.f2183ou.getLastLocation();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f2183ou.isConnected();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    return this.f2183ou.isConnecting();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2183ou.isConnectionCallbacksRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2183ou.isConnectionFailedListenerRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2183ou.registerConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2183ou.registerConnectionFailedListener(listener);
                }

                public void removeGeofences(PendingIntent pendingIntent, OnRemoveGeofencesResultListener listener) {
                    this.f2183ou.removeGeofences(pendingIntent, listener);
                }

                public void removeGeofences(List<String> geofenceRequestIds, OnRemoveGeofencesResultListener listener) {
                    this.f2183ou.removeGeofences(geofenceRequestIds, listener);
                }

                public void removeLocationUpdates(PendingIntent callbackIntent) {
                    this.f2183ou.removeLocationUpdates(callbackIntent);
                }

                public void removeLocationUpdates(LocationListener listener) {
                    this.f2183ou.removeLocationUpdates(listener);
                }

                public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
                    this.f2183ou.requestLocationUpdates(request, callbackIntent);
                }

                public void requestLocationUpdates(LocationRequest request, LocationListener listener) {
                    this.f2183ou.requestLocationUpdates(request, listener);
                }

                public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
                    this.f2183ou.requestLocationUpdates(request, listener, looper);
                }

                public void setMockLocation(Location mockLocation) {
                    this.f2183ou.setMockLocation(mockLocation);
                }

                public void setMockMode(boolean isMockMode) {
                    this.f2183ou.setMockMode(isMockMode);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2183ou.unregisterConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2183ou.unregisterConnectionFailedListener(listener);
                }
            }
