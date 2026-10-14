            package com.google.android.gms.internal;

            import android.content.Context;
            import android.content.Intent;
            import android.net.LocalSocket;
            import android.net.LocalSocketAddress;
            import android.os.Binder;
            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.IInterface;
            import android.os.RemoteException;
            import android.view.View;
            import com.google.android.gms.common.ConnectionResult;
            import com.google.android.gms.common.GooglePlayServicesClient;
            import com.google.android.gms.common.GooglePlayServicesUtil;
            import com.google.android.gms.common.Scopes;
            import com.google.android.gms.common.data.C0891d;
            import com.google.android.gms.games.Game;
            import com.google.android.gms.games.GameBuffer;
            import com.google.android.gms.games.GameEntity;
            import com.google.android.gms.games.GamesClient;
            import com.google.android.gms.games.OnGamesLoadedListener;
            import com.google.android.gms.games.OnPlayersLoadedListener;
            import com.google.android.gms.games.OnSignOutCompleteListener;
            import com.google.android.gms.games.Player;
            import com.google.android.gms.games.PlayerBuffer;
            import com.google.android.gms.games.PlayerEntity;
            import com.google.android.gms.games.achievement.AchievementBuffer;
            import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
            import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
            import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
            import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
            import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
            import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
            import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
            import com.google.android.gms.games.leaderboard.SubmitScoreResult;
            import com.google.android.gms.games.multiplayer.Invitation;
            import com.google.android.gms.games.multiplayer.InvitationBuffer;
            import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
            import com.google.android.gms.games.multiplayer.OnInvitationsLoadedListener;
            import com.google.android.gms.games.multiplayer.ParticipantUtils;
            import com.google.android.gms.games.multiplayer.realtime.C0930a;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
            import com.google.android.gms.games.multiplayer.realtime.Room;
            import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
            import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
            import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
            import com.googlecode.javacv.cpp.avformat;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Iterator;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.em */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C1055em extends AbstractC1020de<InterfaceC1060er> {

                /* JADX INFO: renamed from: it */
                private final String f1765it;

                /* JADX INFO: renamed from: mF */
                private final String f1766mF;

                /* JADX INFO: renamed from: mG */
                private final Map<String, C1062et> f1767mG;

                /* JADX INFO: renamed from: mH */
                private PlayerEntity f1768mH;

                /* JADX INFO: renamed from: mI */
                private GameEntity f1769mI;

                /* JADX INFO: renamed from: mJ */
                private final C1061es f1770mJ;

                /* JADX INFO: renamed from: mK */
                private boolean f1771mK;

                /* JADX INFO: renamed from: mL */
                private final Binder f1772mL;

                /* JADX INFO: renamed from: mM */
                private final long f1773mM;

                /* JADX INFO: renamed from: mN */
                private final boolean f1774mN;

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$a */
                abstract class a extends c {

                    /* JADX INFO: renamed from: mO */
                    private final ArrayList<String> f1775mO;

                    a(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d);
                        this.f1775mO = new ArrayList<>();
                        for (String str : strArr) {
                            this.f1775mO.add(str);
                        }
                    }

                    @Override // com.google.android.gms.internal.C1055em.c
                    /* JADX INFO: renamed from: a */
                    protected void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
                        mo1796a(roomStatusUpdateListener, room, this.f1775mO);
                    }

                    /* JADX INFO: renamed from: a */
                    protected abstract void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList);
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$aa */
                final class aa extends a {
                    aa(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeersDisconnected(room, arrayList);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ab */
                final class ab extends a {
                    ab(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeerInvitedToRoom(room, arrayList);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ac */
                final class ac extends a {
                    ac(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeerJoined(room, arrayList);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ad */
                final class ad extends a {
                    ad(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeerLeft(room, arrayList);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ae */
                final class ae extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: ne */
                    private final OnPlayersLoadedListener f1782ne;

                    ae(OnPlayersLoadedListener onPlayersLoadedListener) {
                        this.f1782ne = (OnPlayersLoadedListener) C1028dm.m1601a(onPlayersLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: e */
                    public void mo1757e(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new af(this.f1782ne, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$af */
                final class af extends AbstractC1020de<InterfaceC1060er>.c<OnPlayersLoadedListener> {
                    af(OnPlayersLoadedListener onPlayersLoadedListener, C0891d c0891d) {
                        super(onPlayersLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnPlayersLoadedListener onPlayersLoadedListener, C0891d c0891d) {
                        onPlayersLoadedListener.onPlayersLoaded(c0891d.getStatusCode(), new PlayerBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ag */
                final class ag extends AbstractC1020de<InterfaceC1060er>.b<RealTimeReliableMessageSentListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1784iC;

                    /* JADX INFO: renamed from: nf */
                    private final String f1786nf;

                    /* JADX INFO: renamed from: ng */
                    private final int f1787ng;

                    ag(RealTimeReliableMessageSentListener realTimeReliableMessageSentListener, int i, int i2, String str) {
                        super(realTimeReliableMessageSentListener);
                        this.f1784iC = i;
                        this.f1787ng = i2;
                        this.f1786nf = str;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(RealTimeReliableMessageSentListener realTimeReliableMessageSentListener) {
                        if (realTimeReliableMessageSentListener != null) {
                            realTimeReliableMessageSentListener.onRealTimeMessageSent(this.f1784iC, this.f1787ng, this.f1786nf);
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ah */
                final class ah extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: nh */
                    final RealTimeReliableMessageSentListener f1789nh;

                    public ah(RealTimeReliableMessageSentListener realTimeReliableMessageSentListener) {
                        this.f1789nh = realTimeReliableMessageSentListener;
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: a */
                    public void mo1746a(int i, int i2, String str) {
                        C1055em.this.m1543a(C1055em.this.new ag(this.f1789nh, i, i2, str));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ai */
                final class ai extends c {
                    ai(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        super(roomStatusUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.c
                    /* JADX INFO: renamed from: a */
                    public void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
                        roomStatusUpdateListener.onRoomAutoMatching(room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$aj */
                final class aj extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: ni */
                    private final RoomUpdateListener f1792ni;

                    /* JADX INFO: renamed from: nj */
                    private final RoomStatusUpdateListener f1793nj;

                    /* JADX INFO: renamed from: nk */
                    private final RealTimeMessageReceivedListener f1794nk;

                    public aj(RoomUpdateListener roomUpdateListener) {
                        this.f1792ni = (RoomUpdateListener) C1028dm.m1601a(roomUpdateListener, "Callbacks must not be null");
                        this.f1793nj = null;
                        this.f1794nk = null;
                    }

                    public aj(RoomUpdateListener roomUpdateListener, RoomStatusUpdateListener roomStatusUpdateListener, RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
                        this.f1792ni = (RoomUpdateListener) C1028dm.m1601a(roomUpdateListener, "Callbacks must not be null");
                        this.f1793nj = roomStatusUpdateListener;
                        this.f1794nk = realTimeMessageReceivedListener;
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: a */
                    public void mo1749a(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new ab(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: b */
                    public void mo1751b(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new ac(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: c */
                    public void mo1754c(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new ad(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: d */
                    public void mo1756d(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new z(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: e */
                    public void mo1758e(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new y(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: f */
                    public void mo1760f(C0891d c0891d, String[] strArr) {
                        C1055em.this.m1543a(C1055em.this.new aa(this.f1793nj, c0891d, strArr));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: n */
                    public void mo1768n(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new am(this.f1792ni, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: o */
                    public void mo1769o(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new p(this.f1792ni, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onLeftRoom(int statusCode, String externalRoomId) {
                        C1055em.this.m1543a(C1055em.this.new u(this.f1792ni, statusCode, externalRoomId));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onP2PConnected(String participantId) {
                        C1055em.this.m1543a(C1055em.this.new w(this.f1793nj, participantId));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onP2PDisconnected(String participantId) {
                        C1055em.this.m1543a(C1055em.this.new x(this.f1793nj, participantId));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onRealTimeMessageReceived(RealTimeMessage message) {
                        C1058ep.m1801b("GamesClient", "RoomBinderCallbacks: onRealTimeMessageReceived");
                        C1055em.this.m1543a(C1055em.this.new v(this.f1794nk, message));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: p */
                    public void mo1770p(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new al(this.f1793nj, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: q */
                    public void mo1771q(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new ai(this.f1793nj, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: r */
                    public void mo1772r(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new ak(this.f1792ni, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: s */
                    public void mo1773s(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new h(this.f1793nj, c0891d));
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: t */
                    public void mo1774t(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new i(this.f1793nj, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ak */
                final class ak extends b {
                    ak(RoomUpdateListener roomUpdateListener, C0891d c0891d) {
                        super(roomUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.b
                    /* JADX INFO: renamed from: a */
                    public void mo1797a(RoomUpdateListener roomUpdateListener, Room room, int i) {
                        roomUpdateListener.onRoomConnected(i, room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$al */
                final class al extends c {
                    al(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        super(roomStatusUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.c
                    /* JADX INFO: renamed from: a */
                    public void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
                        roomStatusUpdateListener.onRoomConnecting(room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$am */
                final class am extends b {
                    public am(RoomUpdateListener roomUpdateListener, C0891d c0891d) {
                        super(roomUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.b
                    /* JADX INFO: renamed from: a */
                    public void mo1797a(RoomUpdateListener roomUpdateListener, Room room, int i) {
                        roomUpdateListener.onRoomCreated(i, room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$an */
                final class an extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: nl */
                    private final OnSignOutCompleteListener f1799nl;

                    public an(OnSignOutCompleteListener onSignOutCompleteListener) {
                        this.f1799nl = (OnSignOutCompleteListener) C1028dm.m1601a(onSignOutCompleteListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onSignOutComplete() {
                        C1055em.this.m1543a(C1055em.this.new ao(this.f1799nl));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ao */
                final class ao extends AbstractC1020de<InterfaceC1060er>.b<OnSignOutCompleteListener> {
                    public ao(OnSignOutCompleteListener onSignOutCompleteListener) {
                        super(onSignOutCompleteListener);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnSignOutCompleteListener onSignOutCompleteListener) {
                        onSignOutCompleteListener.onSignOutComplete();
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$ap */
                final class ap extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: nm */
                    private final OnScoreSubmittedListener f1802nm;

                    public ap(OnScoreSubmittedListener onScoreSubmittedListener) {
                        this.f1802nm = (OnScoreSubmittedListener) C1028dm.m1601a(onScoreSubmittedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: d */
                    public void mo1755d(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new aq(this.f1802nm, new SubmitScoreResult(c0891d)));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$aq */
                final class aq extends AbstractC1020de<InterfaceC1060er>.b<OnScoreSubmittedListener> {

                    /* JADX INFO: renamed from: nn */
                    private final SubmitScoreResult f1804nn;

                    public aq(OnScoreSubmittedListener onScoreSubmittedListener, SubmitScoreResult submitScoreResult) {
                        super(onScoreSubmittedListener);
                        this.f1804nn = submitScoreResult;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(OnScoreSubmittedListener onScoreSubmittedListener) {
                        onScoreSubmittedListener.onScoreSubmitted(this.f1804nn.getStatusCode(), this.f1804nn);
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$b */
                abstract class b extends AbstractC1020de<InterfaceC1060er>.c<RoomUpdateListener> {
                    b(RoomUpdateListener roomUpdateListener, C0891d c0891d) {
                        super(roomUpdateListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(RoomUpdateListener roomUpdateListener, C0891d c0891d) {
                        mo1797a(roomUpdateListener, C1055em.m1779a(C1055em.this, c0891d), c0891d.getStatusCode());
                    }

                    /* JADX INFO: renamed from: a */
                    protected abstract void mo1797a(RoomUpdateListener roomUpdateListener, Room room, int i);

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$c */
                abstract class c extends AbstractC1020de<InterfaceC1060er>.c<RoomStatusUpdateListener> {
                    c(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        super(roomStatusUpdateListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        mo1795a(roomStatusUpdateListener, C1055em.m1779a(C1055em.this, c0891d));
                    }

                    /* JADX INFO: renamed from: a */
                    protected abstract void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room);

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$d */
                final class d extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mQ */
                    private final OnAchievementUpdatedListener f1808mQ;

                    d(OnAchievementUpdatedListener onAchievementUpdatedListener) {
                        this.f1808mQ = (OnAchievementUpdatedListener) C1028dm.m1601a(onAchievementUpdatedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    public void onAchievementUpdated(int statusCode, String achievementId) {
                        C1055em.this.m1543a(C1055em.this.new e(this.f1808mQ, statusCode, achievementId));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$e */
                final class e extends AbstractC1020de<InterfaceC1060er>.b<OnAchievementUpdatedListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1809iC;

                    /* JADX INFO: renamed from: mR */
                    private final String f1811mR;

                    e(OnAchievementUpdatedListener onAchievementUpdatedListener, int i, String str) {
                        super(onAchievementUpdatedListener);
                        this.f1809iC = i;
                        this.f1811mR = str;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnAchievementUpdatedListener onAchievementUpdatedListener) {
                        onAchievementUpdatedListener.onAchievementUpdated(this.f1809iC, this.f1811mR);
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$f */
                final class f extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mS */
                    private final OnAchievementsLoadedListener f1813mS;

                    f(OnAchievementsLoadedListener onAchievementsLoadedListener) {
                        this.f1813mS = (OnAchievementsLoadedListener) C1028dm.m1601a(onAchievementsLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: b */
                    public void mo1750b(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new g(this.f1813mS, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$g */
                final class g extends AbstractC1020de<InterfaceC1060er>.c<OnAchievementsLoadedListener> {
                    g(OnAchievementsLoadedListener onAchievementsLoadedListener, C0891d c0891d) {
                        super(onAchievementsLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnAchievementsLoadedListener onAchievementsLoadedListener, C0891d c0891d) {
                        onAchievementsLoadedListener.onAchievementsLoaded(c0891d.getStatusCode(), new AchievementBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$h */
                final class h extends c {
                    h(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        super(roomStatusUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.c
                    /* JADX INFO: renamed from: a */
                    public void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
                        roomStatusUpdateListener.onConnectedToRoom(room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$i */
                final class i extends c {
                    i(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d) {
                        super(roomStatusUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.c
                    /* JADX INFO: renamed from: a */
                    public void mo1795a(RoomStatusUpdateListener roomStatusUpdateListener, Room room) {
                        roomStatusUpdateListener.onDisconnectedFromRoom(room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$j */
                final class j extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mT */
                    private final OnGamesLoadedListener f1818mT;

                    j(OnGamesLoadedListener onGamesLoadedListener) {
                        this.f1818mT = (OnGamesLoadedListener) C1028dm.m1601a(onGamesLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: g */
                    public void mo1761g(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new k(this.f1818mT, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$k */
                final class k extends AbstractC1020de<InterfaceC1060er>.c<OnGamesLoadedListener> {
                    k(OnGamesLoadedListener onGamesLoadedListener, C0891d c0891d) {
                        super(onGamesLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnGamesLoadedListener onGamesLoadedListener, C0891d c0891d) {
                        onGamesLoadedListener.onGamesLoaded(c0891d.getStatusCode(), new GameBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$l */
                final class l extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mU */
                    private final OnInvitationReceivedListener f1821mU;

                    l(OnInvitationReceivedListener onInvitationReceivedListener) {
                        this.f1821mU = onInvitationReceivedListener;
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: k */
                    public void mo1765k(C0891d c0891d) {
                        InvitationBuffer invitationBuffer = new InvitationBuffer(c0891d);
                        try {
                            Invitation invitationFreeze = invitationBuffer.getCount() > 0 ? invitationBuffer.get(0).freeze() : null;
                            invitationBuffer.close();
                            if (invitationFreeze != null) {
                                C1055em.this.m1543a(C1055em.this.new m(this.f1821mU, invitationFreeze));
                            }
                        } catch (Throwable th) {
                            invitationBuffer.close();
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$m */
                final class m extends AbstractC1020de<InterfaceC1060er>.b<OnInvitationReceivedListener> {

                    /* JADX INFO: renamed from: mV */
                    private final Invitation f1823mV;

                    m(OnInvitationReceivedListener onInvitationReceivedListener, Invitation invitation) {
                        super(onInvitationReceivedListener);
                        this.f1823mV = invitation;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnInvitationReceivedListener onInvitationReceivedListener) {
                        onInvitationReceivedListener.onInvitationReceived(this.f1823mV);
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$n */
                final class n extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mW */
                    private final OnInvitationsLoadedListener f1825mW;

                    n(OnInvitationsLoadedListener onInvitationsLoadedListener) {
                        this.f1825mW = onInvitationsLoadedListener;
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: j */
                    public void mo1764j(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new o(this.f1825mW, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$o */
                final class o extends AbstractC1020de<InterfaceC1060er>.c<OnInvitationsLoadedListener> {
                    o(OnInvitationsLoadedListener onInvitationsLoadedListener, C0891d c0891d) {
                        super(onInvitationsLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnInvitationsLoadedListener onInvitationsLoadedListener, C0891d c0891d) {
                        onInvitationsLoadedListener.onInvitationsLoaded(c0891d.getStatusCode(), new InvitationBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$p */
                final class p extends b {
                    public p(RoomUpdateListener roomUpdateListener, C0891d c0891d) {
                        super(roomUpdateListener, c0891d);
                    }

                    @Override // com.google.android.gms.internal.C1055em.b
                    /* JADX INFO: renamed from: a */
                    public void mo1797a(RoomUpdateListener roomUpdateListener, Room room, int i) {
                        roomUpdateListener.onJoinedRoom(i, room);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$q */
                final class q extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: mX */
                    private final OnLeaderboardScoresLoadedListener f1829mX;

                    q(OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener) {
                        this.f1829mX = (OnLeaderboardScoresLoadedListener) C1028dm.m1601a(onLeaderboardScoresLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: a */
                    public void mo1748a(C0891d c0891d, C0891d c0891d2) {
                        C1055em.this.m1543a(C1055em.this.new r(this.f1829mX, c0891d, c0891d2));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$r */
                final class r extends AbstractC1020de<InterfaceC1060er>.b<OnLeaderboardScoresLoadedListener> {

                    /* JADX INFO: renamed from: mY */
                    private final C0891d f1831mY;

                    /* JADX INFO: renamed from: mZ */
                    private final C0891d f1832mZ;

                    r(OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener, C0891d c0891d, C0891d c0891d2) {
                        super(onLeaderboardScoresLoadedListener);
                        this.f1831mY = c0891d;
                        this.f1832mZ = c0891d2;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnLeaderboardScoresLoadedListener onLeaderboardScoresLoadedListener) {
                        C0891d c0891d;
                        C0891d c0891d2 = null;
                        C0891d c0891d3 = this.f1831mY;
                        C0891d c0891d4 = this.f1832mZ;
                        if (onLeaderboardScoresLoadedListener != null) {
                            try {
                                onLeaderboardScoresLoadedListener.onLeaderboardScoresLoaded(c0891d4.getStatusCode(), new LeaderboardBuffer(c0891d3), new LeaderboardScoreBuffer(c0891d4));
                                c0891d = null;
                            } catch (Throwable th) {
                                if (c0891d3 != null) {
                                    c0891d3.close();
                                }
                                if (c0891d4 != null) {
                                    c0891d4.close();
                                }
                                throw th;
                            }
                        } else {
                            c0891d2 = c0891d4;
                            c0891d = c0891d3;
                        }
                        if (c0891d != null) {
                            c0891d.close();
                        }
                        if (c0891d2 != null) {
                            c0891d2.close();
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                        if (this.f1831mY != null) {
                            this.f1831mY.close();
                        }
                        if (this.f1832mZ != null) {
                            this.f1832mZ.close();
                        }
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$s */
                final class s extends AbstractBinderC1054el {

                    /* JADX INFO: renamed from: na */
                    private final OnLeaderboardMetadataLoadedListener f1834na;

                    s(OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener) {
                        this.f1834na = (OnLeaderboardMetadataLoadedListener) C1028dm.m1601a(onLeaderboardMetadataLoadedListener, "Listener must not be null");
                    }

                    @Override // com.google.android.gms.internal.AbstractBinderC1054el, com.google.android.gms.internal.InterfaceC1059eq
                    /* JADX INFO: renamed from: c */
                    public void mo1753c(C0891d c0891d) {
                        C1055em.this.m1543a(C1055em.this.new t(this.f1834na, c0891d));
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$t */
                final class t extends AbstractC1020de<InterfaceC1060er>.c<OnLeaderboardMetadataLoadedListener> {
                    t(OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, C0891d c0891d) {
                        super(onLeaderboardMetadataLoadedListener, c0891d);
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    protected void a2(OnLeaderboardMetadataLoadedListener onLeaderboardMetadataLoadedListener, C0891d c0891d) {
                        onLeaderboardMetadataLoadedListener.onLeaderboardMetadataLoaded(c0891d.getStatusCode(), new LeaderboardBuffer(c0891d));
                    }

                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$u */
                final class u extends AbstractC1020de<InterfaceC1060er>.b<RoomUpdateListener> {

                    /* JADX INFO: renamed from: iC */
                    private final int f1836iC;

                    /* JADX INFO: renamed from: nb */
                    private final String f1838nb;

                    u(RoomUpdateListener roomUpdateListener, int i, String str) {
                        super(roomUpdateListener);
                        this.f1836iC = i;
                        this.f1838nb = str;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(RoomUpdateListener roomUpdateListener) {
                        roomUpdateListener.onLeftRoom(this.f1836iC, this.f1838nb);
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$v */
                final class v extends AbstractC1020de<InterfaceC1060er>.b<RealTimeMessageReceivedListener> {

                    /* JADX INFO: renamed from: nc */
                    private final RealTimeMessage f1840nc;

                    v(RealTimeMessageReceivedListener realTimeMessageReceivedListener, RealTimeMessage realTimeMessage) {
                        super(realTimeMessageReceivedListener);
                        this.f1840nc = realTimeMessage;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
                        C1058ep.m1801b("GamesClient", "Deliver Message received callback");
                        if (realTimeMessageReceivedListener != null) {
                            realTimeMessageReceivedListener.onRealTimeMessageReceived(this.f1840nc);
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$w */
                final class w extends AbstractC1020de<InterfaceC1060er>.b<RoomStatusUpdateListener> {

                    /* JADX INFO: renamed from: nd */
                    private final String f1842nd;

                    w(RoomStatusUpdateListener roomStatusUpdateListener, String str) {
                        super(roomStatusUpdateListener);
                        this.f1842nd = str;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(RoomStatusUpdateListener roomStatusUpdateListener) {
                        if (roomStatusUpdateListener != null) {
                            roomStatusUpdateListener.onP2PConnected(this.f1842nd);
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$x */
                final class x extends AbstractC1020de<InterfaceC1060er>.b<RoomStatusUpdateListener> {

                    /* JADX INFO: renamed from: nd */
                    private final String f1844nd;

                    x(RoomStatusUpdateListener roomStatusUpdateListener, String str) {
                        super(roomStatusUpdateListener);
                        this.f1844nd = str;
                    }

                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    public void a2(RoomStatusUpdateListener roomStatusUpdateListener) {
                        if (roomStatusUpdateListener != null) {
                            roomStatusUpdateListener.onP2PDisconnected(this.f1844nd);
                        }
                    }

                    @Override // com.google.android.gms.internal.AbstractC1020de.b
                    /* JADX INFO: renamed from: aF */
                    protected void mo1503aF() {
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$y */
                final class y extends a {
                    y(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeersConnected(room, arrayList);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.internal.em$z */
                final class z extends a {
                    z(RoomStatusUpdateListener roomStatusUpdateListener, C0891d c0891d, String[] strArr) {
                        super(roomStatusUpdateListener, c0891d, strArr);
                    }

                    @Override // com.google.android.gms.internal.C1055em.a
                    /* JADX INFO: renamed from: a */
                    protected void mo1796a(RoomStatusUpdateListener roomStatusUpdateListener, Room room, ArrayList<String> arrayList) {
                        roomStatusUpdateListener.onPeerDeclined(room, arrayList);
                    }
                }

                public C1055em(Context context, String str, String str2, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, String[] strArr, int i2, View view, boolean z2) {
                    super(context, connectionCallbacks, onConnectionFailedListener, strArr);
                    this.f1771mK = false;
                    this.f1766mF = str;
                    this.f1765it = (String) C1028dm.m1605e(str2);
                    this.f1772mL = new Binder();
                    this.f1767mG = new HashMap();
                    this.f1770mJ = C1061es.m1886a(this, i2);
                    setViewForPopups(view);
                    this.f1773mM = hashCode();
                    this.f1774mN = z2;
                }

                /* JADX INFO: renamed from: K */
                private C1062et m1778K(String str) {
                    C1062et c1062et;
                    try {
                        String strMo1807M = m1549bd().mo1807M(str);
                        if (strMo1807M == null) {
                            c1062et = null;
                        } else {
                            C1058ep.m1804e("GamesClient", "Creating a socket to bind to:" + strMo1807M);
                            LocalSocket localSocket = new LocalSocket();
                            try {
                                localSocket.connect(new LocalSocketAddress(strMo1807M));
                                c1062et = new C1062et(localSocket, str);
                                this.f1767mG.put(str, c1062et);
                            } catch (IOException e2) {
                                C1058ep.m1803d("GamesClient", "connect() call failed on socket: " + e2.getMessage());
                                c1062et = null;
                            }
                        }
                        return c1062et;
                    } catch (RemoteException e3) {
                        C1058ep.m1803d("GamesClient", "Unable to create socket. Service died.");
                        return null;
                    }
                }

                /* JADX INFO: renamed from: a */
                static /* synthetic */ Room m1779a(C1055em c1055em, C0891d c0891d) {
                    return c1055em.m1782x(c0891d);
                }

                /* JADX INFO: renamed from: bR */
                private void m1780bR() {
                    this.f1768mH = null;
                }

                /* JADX INFO: renamed from: bS */
                private void m1781bS() {
                    Iterator<C1062et> it = this.f1767mG.values().iterator();
                    while (it.hasNext()) {
                        try {
                            it.next().close();
                        } catch (IOException e2) {
                            C1058ep.m1800a("GamesClient", "IOException:", e2);
                        }
                    }
                    this.f1767mG.clear();
                }

                /* JADX INFO: renamed from: x */
                private Room m1782x(C0891d c0891d) {
                    C0930a c0930a = new C0930a(c0891d);
                    try {
                        Room roomFreeze = c0930a.getCount() > 0 ? c0930a.get(0).freeze() : null;
                        c0930a.close();
                        return roomFreeze;
                    } catch (Throwable th) {
                        c0930a.close();
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: A */
                protected InterfaceC1060er m1783A(IBinder iBinder) {
                    return InterfaceC1060er.a.m1885C(iBinder);
                }

                /* JADX INFO: renamed from: a */
                public int m1784a(byte[] bArr, String str, String[] strArr) {
                    C1028dm.m1601a(strArr, "Participant IDs must not be null");
                    try {
                        return m1549bd().mo1835b(bArr, str, strArr);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return -1;
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1541a(int i2, IBinder iBinder, Bundle bundle) {
                    if (i2 == 0 && bundle != null) {
                        this.f1771mK = bundle.getBoolean("show_welcome_popup");
                    }
                    super.mo1541a(i2, iBinder, bundle);
                }

                /* JADX INFO: renamed from: a */
                public void m1785a(IBinder iBinder, Bundle bundle) {
                    if (isConnected()) {
                        try {
                            m1549bd().mo1812a(iBinder, bundle);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                        }
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1542a(ConnectionResult connectionResult) {
                    super.mo1542a(connectionResult);
                    this.f1771mK = false;
                }

                /* JADX INFO: renamed from: a */
                public void m1786a(OnPlayersLoadedListener onPlayersLoadedListener, int i2, boolean z2, boolean z3) {
                    try {
                        m1549bd().mo1815a(new ae(onPlayersLoadedListener), i2, z2, z3);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                /* JADX INFO: renamed from: a */
                public void m1787a(OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
                    d dVar;
                    if (onAchievementUpdatedListener == null) {
                        dVar = null;
                    } else {
                        try {
                            dVar = new d(onAchievementUpdatedListener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1827a(dVar, str, this.f1770mJ.m1890bZ(), this.f1770mJ.m1889bY());
                }

                /* JADX INFO: renamed from: a */
                public void m1788a(OnAchievementUpdatedListener onAchievementUpdatedListener, String str, int i2) {
                    d dVar;
                    if (onAchievementUpdatedListener == null) {
                        dVar = null;
                    } else {
                        try {
                            dVar = new d(onAchievementUpdatedListener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1822a(dVar, str, i2, this.f1770mJ.m1890bZ(), this.f1770mJ.m1889bY());
                }

                /* JADX INFO: renamed from: a */
                public void m1789a(OnScoreSubmittedListener onScoreSubmittedListener, String str, long j2, String str2) {
                    ap apVar;
                    if (onScoreSubmittedListener == null) {
                        apVar = null;
                    } else {
                        try {
                            apVar = new ap(onScoreSubmittedListener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1826a(apVar, str, j2, str2);
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1365a(InterfaceC1025dj interfaceC1025dj, AbstractC1020de.d dVar) throws RemoteException {
                    String string = getContext().getResources().getConfiguration().locale.toString();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("com.google.android.gms.games.key.isHeadless", this.f1774mN);
                    interfaceC1025dj.mo1586a(dVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.f1765it, m1544aY(), this.f1766mF, this.f1770mJ.m1890bZ(), string, bundle);
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: a */
                protected void mo1500a(String... strArr) {
                    boolean z2 = false;
                    boolean z3 = false;
                    for (String str : strArr) {
                        if (str.equals(Scopes.GAMES)) {
                            z3 = true;
                        } else if (str.equals("https://www.googleapis.com/auth/games.firstparty")) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        C1028dm.m1602a(!z3, String.format("Cannot have both %s and %s!", Scopes.GAMES, "https://www.googleapis.com/auth/games.firstparty"));
                    } else {
                        C1028dm.m1602a(z3, String.format("GamesClient requires %s to function.", Scopes.GAMES));
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: aZ */
                protected void mo1545aZ() {
                    super.mo1545aZ();
                    if (this.f1771mK) {
                        this.f1770mJ.mo1888bX();
                        this.f1771mK = false;
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ag */
                protected String mo1366ag() {
                    return "com.google.android.gms.games.service.START";
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ah */
                protected String mo1367ah() {
                    return "com.google.android.gms.games.internal.IGamesService";
                }

                /* JADX INFO: renamed from: b */
                public void m1790b(OnAchievementUpdatedListener onAchievementUpdatedListener, String str) {
                    d dVar;
                    if (onAchievementUpdatedListener == null) {
                        dVar = null;
                    } else {
                        try {
                            dVar = new d(onAchievementUpdatedListener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1842b(dVar, str, this.f1770mJ.m1890bZ(), this.f1770mJ.m1889bY());
                }

                /* JADX INFO: renamed from: b */
                public void m1791b(OnAchievementUpdatedListener onAchievementUpdatedListener, String str, int i2) {
                    d dVar;
                    if (onAchievementUpdatedListener == null) {
                        dVar = null;
                    } else {
                        try {
                            dVar = new d(onAchievementUpdatedListener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1840b(dVar, str, i2, this.f1770mJ.m1890bZ(), this.f1770mJ.m1889bY());
                }

                /* JADX INFO: renamed from: bT */
                public void m1792bT() {
                    if (isConnected()) {
                        try {
                            m1549bd().mo1848bT();
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                        }
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: ba */
                protected Bundle mo1546ba() {
                    try {
                        Bundle bundleMo1852ba = m1549bd().mo1852ba();
                        if (bundleMo1852ba == null) {
                            return bundleMo1852ba;
                        }
                        bundleMo1852ba.setClassLoader(C1055em.class.getClassLoader());
                        return bundleMo1852ba;
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return null;
                    }
                }

                public void clearNotifications(int notificationTypes) {
                    try {
                        m1549bd().clearNotifications(notificationTypes);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de, com.google.android.gms.common.GooglePlayServicesClient
                public void connect() {
                    m1780bR();
                    super.connect();
                }

                public void createRoom(RoomConfig config) {
                    try {
                        m1549bd().mo1818a(new aj(config.getRoomUpdateListener(), config.getRoomStatusUpdateListener(), config.getMessageReceivedListener()), this.f1772mL, config.getVariant(), config.getInvitedPlayerIds(), config.getAutoMatchCriteria(), config.isSocketEnabled(), this.f1773mM);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de, com.google.android.gms.common.GooglePlayServicesClient
                public void disconnect() {
                    this.f1771mK = false;
                    if (isConnected()) {
                        try {
                            InterfaceC1060er interfaceC1060erBd = m1549bd();
                            interfaceC1060erBd.mo1848bT();
                            interfaceC1060erBd.mo1871g(this.f1773mM);
                            interfaceC1060erBd.mo1867f(this.f1773mM);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "Failed to notify client disconnect.");
                        }
                    }
                    m1781bS();
                    super.disconnect();
                }

                public Intent getAchievementsIntent() {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.VIEW_ACHIEVEMENTS");
                    intent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
                    return C1057eo.m1799c(intent);
                }

                public Intent getAllLeaderboardsIntent() {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.VIEW_LEADERBOARDS");
                    intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.f1766mF);
                    intent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
                    return C1057eo.m1799c(intent);
                }

                public String getAppId() {
                    try {
                        return m1549bd().getAppId();
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return null;
                    }
                }

                public String getCurrentAccountName() {
                    try {
                        return m1549bd().getCurrentAccountName();
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return null;
                    }
                }

                public Game getCurrentGame() {
                    m1548bc();
                    synchronized (this) {
                        try {
                            if (this.f1769mI == null) {
                                try {
                                    GameBuffer gameBuffer = new GameBuffer(m1549bd().mo1851bW());
                                    try {
                                        if (gameBuffer.getCount() > 0) {
                                            this.f1769mI = (GameEntity) gameBuffer.get(0).freeze();
                                        }
                                        gameBuffer.close();
                                    } catch (Throwable th) {
                                        gameBuffer.close();
                                        throw th;
                                    }
                                } catch (RemoteException e2) {
                                    C1058ep.m1802c("GamesClient", "service died");
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    return this.f1769mI;
                }

                public Player getCurrentPlayer() {
                    m1548bc();
                    synchronized (this) {
                        try {
                            if (this.f1768mH == null) {
                                try {
                                    PlayerBuffer playerBuffer = new PlayerBuffer(m1549bd().mo1849bU());
                                    try {
                                        if (playerBuffer.getCount() > 0) {
                                            this.f1768mH = (PlayerEntity) playerBuffer.get(0).freeze();
                                        }
                                        playerBuffer.close();
                                    } catch (Throwable th) {
                                        playerBuffer.close();
                                        throw th;
                                    }
                                } catch (RemoteException e2) {
                                    C1058ep.m1802c("GamesClient", "service died");
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    return this.f1768mH;
                }

                public String getCurrentPlayerId() {
                    try {
                        return m1549bd().getCurrentPlayerId();
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return null;
                    }
                }

                public Intent getInvitationInboxIntent() {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.SHOW_INVITATIONS");
                    intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.f1766mF);
                    return C1057eo.m1799c(intent);
                }

                public Intent getLeaderboardIntent(String leaderboardId) {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.VIEW_LEADERBOARD_SCORES");
                    intent.putExtra("com.google.android.gms.games.LEADERBOARD_ID", leaderboardId);
                    intent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
                    return C1057eo.m1799c(intent);
                }

                public RealTimeSocket getRealTimeSocketForParticipant(String roomId, String participantId) {
                    if (participantId == null || !ParticipantUtils.m1214Q(participantId)) {
                        throw new IllegalArgumentException("Bad participant ID");
                    }
                    C1062et c1062et = this.f1767mG.get(participantId);
                    return (c1062et == null || c1062et.isClosed()) ? m1778K(participantId) : c1062et;
                }

                public Intent getRealTimeWaitingRoomIntent(Room room, int minParticipantsToStart) {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.SHOW_REAL_TIME_WAITING_ROOM");
                    C1028dm.m1601a(room, "Room parameter must not be null");
                    intent.putExtra(GamesClient.EXTRA_ROOM, room.freeze());
                    C1028dm.m1602a(minParticipantsToStart >= 0, "minParticipantsToStart must be >= 0");
                    intent.putExtra("com.google.android.gms.games.MIN_PARTICIPANTS_TO_START", minParticipantsToStart);
                    return C1057eo.m1799c(intent);
                }

                public Intent getSelectPlayersIntent(int minPlayers, int maxPlayers) {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.SELECT_PLAYERS");
                    intent.putExtra("com.google.android.gms.games.MIN_SELECTIONS", minPlayers);
                    intent.putExtra("com.google.android.gms.games.MAX_SELECTIONS", maxPlayers);
                    return C1057eo.m1799c(intent);
                }

                public Intent getSettingsIntent() {
                    m1548bc();
                    Intent intent = new Intent("com.google.android.gms.games.SHOW_SETTINGS");
                    intent.putExtra("com.google.android.gms.games.GAME_PACKAGE_NAME", this.f1766mF);
                    intent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
                    return C1057eo.m1799c(intent);
                }

                /* JADX INFO: renamed from: i */
                public void m1793i(String str, int i2) {
                    try {
                        m1549bd().mo1878i(str, i2);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                /* JADX INFO: renamed from: j */
                public void m1794j(String str, int i2) {
                    try {
                        m1549bd().mo1880j(str, i2);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void joinRoom(RoomConfig config) {
                    try {
                        m1549bd().mo1819a(new aj(config.getRoomUpdateListener(), config.getRoomStatusUpdateListener(), config.getMessageReceivedListener()), this.f1772mL, config.getInvitationId(), config.isSocketEnabled(), this.f1773mM);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void leaveRoom(RoomUpdateListener listener, String roomId) {
                    try {
                        m1549bd().mo1866e(new aj(listener), roomId);
                        m1781bS();
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadAchievements(OnAchievementsLoadedListener listener, boolean forceReload) {
                    try {
                        m1549bd().mo1847b(new f(listener), forceReload);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadGame(OnGamesLoadedListener listener) {
                    try {
                        m1549bd().mo1859d(new j(listener));
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadInvitations(OnInvitationsLoadedListener listener) {
                    try {
                        m1549bd().mo1864e(new n(listener));
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, String leaderboardId, boolean forceReload) {
                    try {
                        m1549bd().mo1857c(new s(listener), leaderboardId, forceReload);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadLeaderboardMetadata(OnLeaderboardMetadataLoadedListener listener, boolean forceReload) {
                    try {
                        m1549bd().mo1858c(new s(listener), forceReload);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadMoreScores(OnLeaderboardScoresLoadedListener listener, LeaderboardScoreBuffer buffer, int maxResults, int pageDirection) {
                    try {
                        m1549bd().mo1817a(new q(listener), buffer.m1187cb().m1189cc(), maxResults, pageDirection);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadPlayer(OnPlayersLoadedListener listener, String playerId) {
                    try {
                        m1549bd().mo1855c(new ae(listener), playerId);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadPlayerCenteredScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
                    try {
                        m1549bd().mo1839b(new q(listener), leaderboardId, span, leaderboardCollection, maxResults, forceReload);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void loadTopScores(OnLeaderboardScoresLoadedListener listener, String leaderboardId, int span, int leaderboardCollection, int maxResults, boolean forceReload) {
                    try {
                        m1549bd().mo1821a(new q(listener), leaderboardId, span, leaderboardCollection, maxResults, forceReload);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                @Override // com.google.android.gms.internal.AbstractC1020de
                /* JADX INFO: renamed from: p */
                protected /* synthetic */ IInterface mo1370p(IBinder iBinder) {
                    return m1783A(iBinder);
                }

                public void registerInvitationListener(OnInvitationReceivedListener listener) {
                    try {
                        m1549bd().mo1816a(new l(listener), this.f1773mM);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public int sendReliableRealTimeMessage(RealTimeReliableMessageSentListener listener, byte[] messageData, String roomId, String recipientParticipantId) {
                    try {
                        return m1549bd().mo1811a(new ah(listener), messageData, roomId, recipientParticipantId);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return -1;
                    }
                }

                public int sendUnreliableRealTimeMessageToAll(byte[] messageData, String roomId) {
                    try {
                        return m1549bd().mo1835b(messageData, roomId, (String[]) null);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                        return -1;
                    }
                }

                public void setGravityForPopups(int gravity) {
                    this.f1770mJ.setGravity(gravity);
                }

                public void setUseNewPlayerNotificationsFirstParty(boolean newPlayerStyle) {
                    try {
                        m1549bd().setUseNewPlayerNotificationsFirstParty(newPlayerStyle);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }

                public void setViewForPopups(View gamesContentView) {
                    this.f1770mJ.mo1891e(gamesContentView);
                }

                public void signOut(OnSignOutCompleteListener listener) {
                    an anVar;
                    if (listener == null) {
                        anVar = null;
                    } else {
                        try {
                            anVar = new an(listener);
                        } catch (RemoteException e2) {
                            C1058ep.m1802c("GamesClient", "service died");
                            return;
                        }
                    }
                    m1549bd().mo1813a(anVar);
                }

                public void unregisterInvitationListener() {
                    try {
                        m1549bd().mo1871g(this.f1773mM);
                    } catch (RemoteException e2) {
                        C1058ep.m1802c("GamesClient", "service died");
                    }
                }
            }
