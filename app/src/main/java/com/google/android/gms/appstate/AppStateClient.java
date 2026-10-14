            package com.google.android.gms.appstate;

            import android.content.Context;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.Scopes;
            import com.google.android.gms.internal.C1011cw;
            import com.google.android.gms.internal.C1028dm;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class AppStateClient implements GooglePlayServicesClient {
                public static final int STATUS_CLIENT_RECONNECT_REQUIRED = 2;
                public static final int STATUS_DEVELOPER_ERROR = 7;
                public static final int STATUS_INTERNAL_ERROR = 1;
                public static final int STATUS_NETWORK_ERROR_NO_DATA = 4;
                public static final int STATUS_NETWORK_ERROR_OPERATION_DEFERRED = 5;
                public static final int STATUS_NETWORK_ERROR_OPERATION_FAILED = 6;
                public static final int STATUS_NETWORK_ERROR_STALE_DATA = 3;
                public static final int STATUS_OK = 0;
                public static final int STATUS_STATE_KEY_LIMIT_EXCEEDED = 2003;
                public static final int STATUS_STATE_KEY_NOT_FOUND = 2002;
                public static final int STATUS_WRITE_OUT_OF_DATE_VERSION = 2000;
                public static final int STATUS_WRITE_SIZE_EXCEEDED = 2001;

                /* JADX INFO: renamed from: io */
                private final C1011cw f910io;

                public static final class Builder {

                    /* JADX INFO: renamed from: ip */
                    private static final String[] f911ip = {Scopes.APP_STATE};

                    /* JADX INFO: renamed from: iq */
                    private GooglePlayServicesClient.ConnectionCallbacks f912iq;

                    /* JADX INFO: renamed from: ir */
                    private GooglePlayServicesClient.OnConnectionFailedListener f913ir;

                    /* JADX INFO: renamed from: is */
                    private String[] f914is = f911ip;

                    /* JADX INFO: renamed from: it */
                    private String f915it = "<<default account>>";
                    private Context mContext;

                    public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                        this.mContext = context;
                        this.f912iq = connectedListener;
                        this.f913ir = connectionFailedListener;
                    }

                    public AppStateClient create() {
                        return new AppStateClient(this.mContext, this.f912iq, this.f913ir, this.f915it, this.f914is, null);
                    }

                    public Builder setAccountName(String accountName) {
                        this.f915it = (String) C1028dm.m1605e(accountName);
                        return this;
                    }

                    public Builder setScopes(String... scopes) {
                        this.f914is = scopes;
                        return this;
                    }
                }

                private AppStateClient(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener, String accountName, String[] scopes) {
                    this.f910io = new C1011cw(context, connectedListener, connectionFailedListener, accountName, scopes);
                }

                /* synthetic */ AppStateClient(Context x0, GooglePlayServicesClient.ConnectionCallbacks x1, GooglePlayServicesClient.OnConnectionFailedListener x2, String x3, String[] x4, Object x5) {
                    this(x0, x1, x2, x3, x4);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f910io.connect();
                }

                public void deleteState(OnStateDeletedListener listener, int stateKey) {
                    this.f910io.deleteState(listener, stateKey);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f910io.disconnect();
                }

                public int getMaxNumKeys() {
                    return this.f910io.getMaxNumKeys();
                }

                public int getMaxStateSize() {
                    return this.f910io.getMaxStateSize();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f910io.isConnected();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    return this.f910io.isConnecting();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f910io.isConnectionCallbacksRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f910io.isConnectionFailedListenerRegistered(listener);
                }

                public void listStates(OnStateListLoadedListener listener) {
                    this.f910io.listStates(listener);
                }

                public void loadState(OnStateLoadedListener listener, int stateKey) {
                    this.f910io.loadState(listener, stateKey);
                }

                public void reconnect() {
                    this.f910io.disconnect();
                    this.f910io.connect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f910io.registerConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f910io.registerConnectionFailedListener(listener);
                }

                public void resolveState(OnStateLoadedListener listener, int stateKey, String resolvedVersion, byte[] resolvedData) {
                    this.f910io.resolveState(listener, stateKey, resolvedVersion, resolvedData);
                }

                public void signOut() {
                    this.f910io.signOut(null);
                }

                public void signOut(OnSignOutCompleteListener listener) {
                    C1028dm.m1601a(listener, "Must provide a valid listener");
                    this.f910io.signOut(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f910io.unregisterConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f910io.unregisterConnectionFailedListener(listener);
                }

                public void updateState(int stateKey, byte[] data) {
                    this.f910io.m1499a((OnStateLoadedListener) null, stateKey, data);
                }

                public void updateStateImmediate(OnStateLoadedListener listener, int stateKey, byte[] data) {
                    C1028dm.m1601a(listener, "Must provide a valid listener");
                    this.f910io.m1499a(listener, stateKey, data);
                }
            }
