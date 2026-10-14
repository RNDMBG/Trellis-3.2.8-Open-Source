            package com.google.android.gms.plus;

            import android.content.Context;
            import android.net.Uri;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.internal.C1081fl;
            import com.google.android.gms.internal.C1084fo;
            import com.google.android.gms.plus.model.moments.Moment;
            import com.google.android.gms.plus.model.moments.MomentBuffer;
            import com.google.android.gms.plus.model.people.Person;
            import com.google.android.gms.plus.model.people.PersonBuffer;
            import java.util.Collection;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class PlusClient implements GooglePlayServicesClient {

                /* JADX INFO: renamed from: rb */
                final C1081fl f2372rb;

                public static class Builder {

                    /* JADX INFO: renamed from: ir */
                    private final GooglePlayServicesClient.OnConnectionFailedListener f2373ir;
                    private final Context mContext;

                    /* JADX INFO: renamed from: rc */
                    private final GooglePlayServicesClient.ConnectionCallbacks f2374rc;

                    /* JADX INFO: renamed from: rd */
                    private final C1084fo f2375rd;

                    public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                        this.mContext = context;
                        this.f2374rc = connectionCallbacks;
                        this.f2373ir = connectionFailedListener;
                        this.f2375rd = new C1084fo(this.mContext);
                    }

                    public PlusClient build() {
                        return new PlusClient(new C1081fl(this.mContext, this.f2375rd.m1992dh(), this.f2374rc, this.f2373ir));
                    }

                    public Builder clearScopes() {
                        this.f2375rd.m1991dg();
                        return this;
                    }

                    public Builder setAccountName(String accountName) {
                        this.f2375rd.m1989Z(accountName);
                        return this;
                    }

                    public Builder setActions(String... actions) {
                        this.f2375rd.m1993e(actions);
                        return this;
                    }

                    public Builder setScopes(String... scopes) {
                        this.f2375rd.m1990d(scopes);
                        return this;
                    }
                }

                public interface OnAccessRevokedListener {
                    void onAccessRevoked(ConnectionResult connectionResult);
                }

                public interface OnMomentsLoadedListener {
                    void onMomentsLoaded(ConnectionResult connectionResult, MomentBuffer momentBuffer, String str, String str2);
                }

                public interface OnPeopleLoadedListener {
                    void onPeopleLoaded(ConnectionResult connectionResult, PersonBuffer personBuffer, String str);
                }

                public interface OrderBy {
                    public static final int ALPHABETICAL = 0;
                    public static final int BEST = 1;
                }

                PlusClient(C1081fl plusClientImpl) {
                    this.f2372rb = plusClientImpl;
                }

                /* JADX INFO: renamed from: cR */
                C1081fl m2330cR() {
                    return this.f2372rb;
                }

                public void clearDefaultAccount() {
                    this.f2372rb.clearDefaultAccount();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f2372rb.connect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f2372rb.disconnect();
                }

                public String getAccountName() {
                    return this.f2372rb.getAccountName();
                }

                public Person getCurrentPerson() {
                    return this.f2372rb.getCurrentPerson();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f2372rb.isConnected();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    return this.f2372rb.isConnecting();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f2372rb.isConnectionCallbacksRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f2372rb.isConnectionFailedListenerRegistered(listener);
                }

                public void loadMoments(OnMomentsLoadedListener listener) {
                    this.f2372rb.loadMoments(listener);
                }

                public void loadMoments(OnMomentsLoadedListener listener, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
                    this.f2372rb.loadMoments(listener, maxResults, pageToken, targetUrl, type, userId);
                }

                public void loadPeople(OnPeopleLoadedListener listener, Collection<String> personIds) {
                    this.f2372rb.m1976a(listener, personIds);
                }

                public void loadPeople(OnPeopleLoadedListener listener, String... personIds) {
                    this.f2372rb.m1977a(listener, personIds);
                }

                public void loadVisiblePeople(OnPeopleLoadedListener listener, int orderBy, String pageToken) {
                    this.f2372rb.loadVisiblePeople(listener, orderBy, pageToken);
                }

                public void loadVisiblePeople(OnPeopleLoadedListener listener, String pageToken) {
                    this.f2372rb.loadVisiblePeople(listener, pageToken);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2372rb.registerConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2372rb.registerConnectionFailedListener(listener);
                }

                public void removeMoment(String momentId) {
                    this.f2372rb.removeMoment(momentId);
                }

                public void revokeAccessAndDisconnect(OnAccessRevokedListener listener) {
                    this.f2372rb.revokeAccessAndDisconnect(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f2372rb.unregisterConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f2372rb.unregisterConnectionFailedListener(listener);
                }

                public void writeMoment(Moment moment) {
                    this.f2372rb.writeMoment(moment);
                }
            }
