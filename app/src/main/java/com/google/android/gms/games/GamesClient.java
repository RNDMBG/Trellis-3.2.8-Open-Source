            package com.google.android.gms.games;

            import android.content.Context;
            import android.content.Intent;
            import android.view.View;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.Scopes;
            import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
            import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
            import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
            import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
            import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
            import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
            import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
            import com.google.android.gms.games.multiplayer.OnInvitationsLoadedListener;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
            import com.google.android.gms.games.multiplayer.realtime.Room;
            import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
            import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
            import com.google.android.gms.internal.C1028dm;
            import com.google.android.gms.internal.C1055em;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class GamesClient implements GooglePlayServicesClient {
                public static final String EXTRA_EXCLUSIVE_BIT_MASK = "exclusive_bit_mask";
                public static final String EXTRA_INVITATION = "invitation";
                public static final String EXTRA_MAX_AUTOMATCH_PLAYERS = "max_automatch_players";
                public static final String EXTRA_MIN_AUTOMATCH_PLAYERS = "min_automatch_players";
                public static final String EXTRA_PLAYERS = "players";
                public static final String EXTRA_ROOM = "room";
                public static final int MAX_RELIABLE_MESSAGE_LEN = 1400;
                public static final int MAX_UNRELIABLE_MESSAGE_LEN = 1168;
                public static final int NOTIFICATION_TYPES_ALL = -1;
                public static final int NOTIFICATION_TYPES_MULTIPLAYER = 1;
                public static final int NOTIFICATION_TYPE_INVITATION = 1;
                public static final int STATUS_ACHIEVEMENT_NOT_INCREMENTAL = 3002;
                public static final int STATUS_ACHIEVEMENT_UNKNOWN = 3001;
                public static final int STATUS_ACHIEVEMENT_UNLOCKED = 3003;
                public static final int STATUS_ACHIEVEMENT_UNLOCK_FAILURE = 3000;
                public static final int STATUS_APP_MISCONFIGURED = 8;
                public static final int STATUS_CLIENT_RECONNECT_REQUIRED = 2;
                public static final int STATUS_INTERNAL_ERROR = 1;
                public static final int STATUS_INVALID_REAL_TIME_ROOM_ID = 7002;
                public static final int STATUS_LICENSE_CHECK_FAILED = 7;
                public static final int STATUS_MULTIPLAYER_ERROR_CREATION_NOT_ALLOWED = 6000;
                public static final int STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER = 6001;
                public static final int STATUS_NETWORK_ERROR_NO_DATA = 4;
                public static final int STATUS_NETWORK_ERROR_OPERATION_DEFERRED = 5;
                public static final int STATUS_NETWORK_ERROR_OPERATION_FAILED = 6;
                public static final int STATUS_NETWORK_ERROR_STALE_DATA = 3;
                public static final int STATUS_OK = 0;
                public static final int STATUS_PARTICIPANT_NOT_CONNECTED = 7003;
                public static final int STATUS_REAL_TIME_CONNECTION_FAILED = 7000;
                public static final int STATUS_REAL_TIME_INACTIVE_ROOM = 7005;
                public static final int STATUS_REAL_TIME_MESSAGE_FAILED = -1;
                public static final int STATUS_REAL_TIME_MESSAGE_SEND_FAILED = 7001;
                public static final int STATUS_REAL_TIME_ROOM_NOT_JOINED = 7004;

                /* JADX INFO: renamed from: mz */
                private final C1055em f1046mz;

                public static final class Builder {

                    /* JADX INFO: renamed from: iq */
                    private final GooglePlayServicesClient.ConnectionCallbacks f1047iq;

                    /* JADX INFO: renamed from: ir */
                    private final GooglePlayServicesClient.OnConnectionFailedListener f1048ir;

                    /* JADX INFO: renamed from: mA */
                    private String f1051mA;

                    /* JADX INFO: renamed from: mC */
                    private View f1053mC;
                    private final Context mContext;

                    /* JADX INFO: renamed from: it */
                    private String f1050it = "<<default account>>";

                    /* JADX INFO: renamed from: is */
                    private String[] f1049is = {Scopes.GAMES};

                    /* JADX INFO: renamed from: mB */
                    private int f1052mB = 49;

                    public Builder(Context context, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener) {
                        this.mContext = context;
                        this.f1051mA = context.getPackageName();
                        this.f1047iq = connectedListener;
                        this.f1048ir = connectionFailedListener;
                    }

                    public GamesClient create() {
                        return new GamesClient(this.mContext, this.f1051mA, this.f1050it, this.f1047iq, this.f1048ir, this.f1049is, this.f1052mB, this.f1053mC, null);
                    }

                    public Builder setAccountName(String accountName) {
                        this.f1050it = (String) C1028dm.m1605e(accountName);
                        return this;
                    }

                    public Builder setGravityForPopups(int gravity) {
                        this.f1052mB = gravity;
                        return this;
                    }

                    public Builder setScopes(String... scopes) {
                        this.f1049is = scopes;
                        return this;
                    }

                    public Builder setViewForPopups(View gamesContentView) {
                        this.f1053mC = (View) C1028dm.m1605e(gamesContentView);
                        return this;
                    }
                }

                private GamesClient(Context context, String gamePackageName, String accountName, GooglePlayServicesClient.ConnectionCallbacks connectedListener, GooglePlayServicesClient.OnConnectionFailedListener connectionFailedListener, String[] scopes, int gravity, View gamesContentView) {
                    this.f1046mz = new C1055em(context, gamePackageName, accountName, connectedListener, connectionFailedListener, scopes, gravity, gamesContentView, false);
                }

                /* synthetic */ GamesClient(Context x0, String x1, String x2, GooglePlayServicesClient.ConnectionCallbacks x3, GooglePlayServicesClient.OnConnectionFailedListener x4, String[] x5, int x6, View x7, Object x8) {
                    this(x0, x1, x2, x3, x4, x5, x6, x7);
                }

                public void clearAllNotifications() {
                    this.f1046mz.clearNotifications(-1);
                }

                public void clearNotifications(int notificationTypes) {
                    this.f1046mz.clearNotifications(notificationTypes);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    this.f1046mz.connect();
                }

                public void createRoom(RoomConfig config) {
                    this.f1046mz.createRoom(config);
                }

                public void declineRoomInvitation(String invitationId) {
                    this.f1046mz.m1794j(invitationId, 0);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f1046mz.disconnect();
                }

                public void dismissRoomInvitation(String invitationId) {
                    this.f1046mz.m1793i(invitationId, 0);
                }

                public Intent getAchievementsIntent() {
                    return this.f1046mz.getAchievementsIntent();
                }

                public Intent getAllLeaderboardsIntent() {
                    return this.f1046mz.getAllLeaderboardsIntent();
                }

                public String getAppId() {
                    return this.f1046mz.getAppId();
                }

                public String getCurrentAccountName() {
                    return this.f1046mz.getCurrentAccountName();
                }

                public Game getCurrentGame() {
                    return this.f1046mz.getCurrentGame();
                }

                public Player getCurrentPlayer() {
                    return this.f1046mz.getCurrentPlayer();
                }

                public String getCurrentPlayerId() {
                    return this.f1046mz.getCurrentPlayerId();
                }

                public Intent getInvitationInboxIntent() {
                    return this.f1046mz.getInvitationInboxIntent();
                }

                public Intent getLeaderboardIntent(String leaderboardId) {
                    return this.f1046mz.getLeaderboardIntent(leaderboardId);
                }

                public RealTimeSocket getRealTimeSocketForParticipant(String roomId, String participantId) {
                    return this.f1046mz.getRealTimeSocketForParticipant(roomId, participantId);
                }

                public Intent getRealTimeWaitingRoomIntent(Room room, int minParticipantsToStart) {
                    return this.f1046mz.getRealTimeWaitingRoomIntent(room, minParticipantsToStart);
                }

                public Intent getSelectPlayersIntent(int minPlayers, int maxPlayers) {
                    return this.f1046mz.getSelectPlayersIntent(minPlayers, maxPlayers);
                }

                public Intent getSettingsIntent() {
                    return this.f1046mz.getSettingsIntent();
                }

                public void incrementAchievement(String id, int numSteps) {
                    this.f1046mz.m1788a((OnAchievementUpdatedListener) null, id, numSteps);
                }

                public void incrementAchievementImmediate(OnAchievementUpdatedListener listener, String id, int numSteps) {
                    this.f1046mz.m1788a(listener, id, numSteps);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnected() {
                    return this.f1046mz.isConnected();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnecting() {
                    return this.f1046mz.isConnecting();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    return this.f1046mz.isConnectionCallbacksRegistered(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    return this.f1046mz.isConnectionFailedListenerRegistered(listener);
                }

                public void joinRoom(RoomConfig config) {
                    this.f1046mz.joinRoom(config);
                }

                public void leaveRoom(RoomUpdateListener listener, String roomId) {
                    this.f1046mz.leaveRoom(listener, roomId);
                }

                public void loadAchievements(OnAchievementsLoadedListener listener, boolean forceReload) {
                    this.f1046mz.loadAchievements(listener, forceReload);
                }

                public void loadGame(OnGamesLoadedListener listener) {
                    this.f1046mz.loadGame(listener);
                }

                public void loadInvitablePlayers(OnPlayersLoadedListener listener, int pageSize, boolean forceReload) {
                    this.f1046mz.m1786a(listener, pageSize, false, forceReload);
                }

                public void loadInvitations(OnInvitationsLoadedListener listener) {
                    this.f1046mz.loadInvitations(listener);
                }

                @Deprecated
                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener) {
                    loadLeaderboardMetadata(listener, false);
                }

                @Deprecated
                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, String leaderboardId) {
                    loadLeaderboardMetadata(listener, leaderboardId, false);
                }

                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, String leaderboardId, boolean forceReload) {
                    this.f1046mz.loadLeaderboardMetadata(listener, leaderboardId, forceReload);
                }

                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, boolean forceReload) {
                    this.f1046mz.loadLeaderboardMetadata(listener, forceReload);
                }

                public void loadMoreInvitablePlayers(OnPlayersLoadedListener listener, int pageSize) {
                    this.f1046mz.m1786a(listener, pageSize, true, false);
                }

                public void loadMoreScores(OnLeaderboardScoresLoadedListener listener, LeaderboardScoreBuffer buffer, int maxResults, int pageDirection) {
                    this.f1046mz.loadMoreScores(listener, buffer, maxResults, pageDirection);
                }

                public void loadPlayer(OnPlayersLoadedListener listener, String playerId) {
                    this.f1046mz.loadPlayer(listener, playerId);
                }

                public void loadPlayerCenteredScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
                    this.f1046mz.loadPlayerCenteredScores(listener, leaderboardId, span, leaderboardCollection, maxResults, false);
                }

                public void loadPlayerCenteredScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
                    this.f1046mz.loadPlayerCenteredScores(listener, leaderboardId, span, leaderboardCollection, maxResults, forceReload);
                }

                public void loadTopScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults) {
                    this.f1046mz.loadTopScores(listener, leaderboardId, span, leaderboardCollection, maxResults, false);
                }

                public void loadTopScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
                    this.f1046mz.loadTopScores(listener, leaderboardId, span, leaderboardCollection, maxResults, forceReload);
                }

                public void reconnect() {
                    this.f1046mz.disconnect();
                    this.f1046mz.connect();
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f1046mz.registerConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f1046mz.registerConnectionFailedListener(listener);
                }

                public void registerInvitationListener(OnInvitationReceivedListener listener) {
                    this.f1046mz.registerInvitationListener(listener);
                }

                public void revealAchievement(String id) {
                    this.f1046mz.m1787a((OnAchievementUpdatedListener) null, id);
                }

                public void revealAchievementImmediate(OnAchievementUpdatedListener listener, String id) {
                    this.f1046mz.m1787a(listener, id);
                }

                public int sendReliableRealTimeMessage(RealTimeReliableMessageSentListener listener, byte[] messageData, String roomId, String recipientParticipantId) {
                    return this.f1046mz.sendReliableRealTimeMessage(listener, messageData, roomId, recipientParticipantId);
                }

                public int sendUnreliableRealTimeMessage(byte[] messageData, String roomId, String recipientParticipantId) {
                    return this.f1046mz.m1784a(messageData, roomId, new String[]{recipientParticipantId});
                }

                public int sendUnreliableRealTimeMessage(byte[] messageData, String roomId, List<String> recipientParticipantIds) {
                    return this.f1046mz.m1784a(messageData, roomId, (String[]) recipientParticipantIds.toArray(new String[recipientParticipantIds.size()]));
                }

                public int sendUnreliableRealTimeMessageToAll(byte[] messageData, String roomId) {
                    return this.f1046mz.sendUnreliableRealTimeMessageToAll(messageData, roomId);
                }

                public void setAchievementSteps(String id, int numSteps) {
                    this.f1046mz.m1791b(null, id, numSteps);
                }

                public void setAchievementStepsImmediate(OnAchievementUpdatedListener listener, String id, int numSteps) {
                    this.f1046mz.m1791b(listener, id, numSteps);
                }

                public void setGravityForPopups(int gravity) {
                    this.f1046mz.setGravityForPopups(gravity);
                }

                public void setUseNewPlayerNotificationsFirstParty(boolean newPlayerStyle) {
                    this.f1046mz.setUseNewPlayerNotificationsFirstParty(newPlayerStyle);
                }

                public void setViewForPopups(View gamesContentView) {
                    C1028dm.m1605e(gamesContentView);
                    this.f1046mz.setViewForPopups(gamesContentView);
                }

                public void signOut() {
                    this.f1046mz.signOut(null);
                }

                public void signOut(OnSignOutCompleteListener listener) {
                    this.f1046mz.signOut(listener);
                }

                public void submitScore(String leaderboardId, long score) {
                    this.f1046mz.m1789a((OnScoreSubmittedListener) null, leaderboardId, score, (String) null);
                }

                public void submitScore(String leaderboardId, long score, String scoreTag) {
                    this.f1046mz.m1789a((OnScoreSubmittedListener) null, leaderboardId, score, scoreTag);
                }

                public void submitScoreImmediate(OnScoreSubmittedListener listener, String leaderboardId, long score) {
                    this.f1046mz.m1789a(listener, leaderboardId, score, (String) null);
                }

                public void submitScoreImmediate(OnScoreSubmittedListener listener, String leaderboardId, long score, String scoreTag) {
                    this.f1046mz.m1789a(listener, leaderboardId, score, scoreTag);
                }

                public void unlockAchievement(String id) {
                    this.f1046mz.m1790b(null, id);
                }

                public void unlockAchievementImmediate(OnAchievementUpdatedListener listener, String id) {
                    this.f1046mz.m1790b(listener, id);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks listener) {
                    this.f1046mz.unregisterConnectionCallbacks(listener);
                }

                @Override // com.google.android.gms.common.GooglePlayServicesClient
                public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener listener) {
                    this.f1046mz.unregisterConnectionFailedListener(listener);
                }

                public void unregisterInvitationListener() {
                    this.f1046mz.unregisterInvitationListener();
                }
            }
