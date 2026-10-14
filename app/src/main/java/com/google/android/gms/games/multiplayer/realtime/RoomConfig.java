            package com.google.android.gms.games.multiplayer.realtime;

            import android.os.Bundle;
            import com.google.android.gms.games.GamesClient;
            import com.google.android.gms.internal.C1028dm;
            import java.util.ArrayList;
            import java.util.Arrays;
            
            
            public final class RoomConfig {

                
                private final String f1104nM;

                
                private final int f1105nR;

                
                private final RoomUpdateListener f1106od;

                
                private final RoomStatusUpdateListener f1107oe;

                
                private final RealTimeMessageReceivedListener f1108of;

                
                private final String[] f1109og;

                
                private final Bundle f1110oh;

                
                private final boolean f1111oi;

                public static final class Builder {

                    
                    int f1112nR;

                    
                    final RoomUpdateListener f1113od;

                    
                    RoomStatusUpdateListener f1114oe;

                    
                    RealTimeMessageReceivedListener f1115of;

                    
                    Bundle f1116oh;

                    
                    boolean f1117oi;

                    
                    String f1118oj;

                    
                    ArrayList<String> f1119ok;

                    private Builder(RoomUpdateListener updateListener) {
                        this.f1118oj = null;
                        this.f1112nR = -1;
                        this.f1119ok = new ArrayList<>();
                        this.f1117oi = false;
                        this.f1113od = (RoomUpdateListener) C1028dm.m1601a(updateListener, "Must provide a RoomUpdateListener");
                    }

                     Builder(RoomUpdateListener x0, Object x1) {
                        this(x0);
                    }

                    public Builder addPlayersToInvite(ArrayList<String> playerIds) {
                        C1028dm.m1605e(playerIds);
                        this.f1119ok.addAll(playerIds);
                        return this;
                    }

                    public Builder addPlayersToInvite(String... playerIds) {
                        C1028dm.m1605e(playerIds);
                        this.f1119ok.addAll(Arrays.asList(playerIds));
                        return this;
                    }

                    public RoomConfig build() {
                        return new RoomConfig(this, null);
                    }

                    public Builder setAutoMatchCriteria(Bundle autoMatchCriteria) {
                        this.f1116oh = autoMatchCriteria;
                        return this;
                    }

                    public Builder setInvitationIdToAccept(String invitationId) {
                        C1028dm.m1605e(invitationId);
                        this.f1118oj = invitationId;
                        return this;
                    }

                    public Builder setMessageReceivedListener(RealTimeMessageReceivedListener listener) {
                        this.f1115of = listener;
                        return this;
                    }

                    public Builder setRoomStatusUpdateListener(RoomStatusUpdateListener listener) {
                        this.f1114oe = listener;
                        return this;
                    }

                    public Builder setSocketCommunicationEnabled(boolean enableSockets) {
                        this.f1117oi = enableSockets;
                        return this;
                    }

                    public Builder setVariant(int variant) {
                        this.f1112nR = variant;
                        return this;
                    }
                }

                private RoomConfig(Builder builder) {
                    this.f1106od = builder.f1113od;
                    this.f1107oe = builder.f1114oe;
                    this.f1108of = builder.f1115of;
                    this.f1104nM = builder.f1118oj;
                    this.f1105nR = builder.f1112nR;
                    this.f1110oh = builder.f1116oh;
                    this.f1111oi = builder.f1117oi;
                    this.f1109og = (String[]) builder.f1119ok.toArray(new String[builder.f1119ok.size()]);
                    if (this.f1108of == null) {
                        C1028dm.m1602a(this.f1111oi, "Must either enable sockets OR specify a message listener");
                    }
                }

                 RoomConfig(Builder x0, Object x1) {
                    this(x0);
                }

                public static Builder builder(RoomUpdateListener listener) {
                    return new Builder(listener, null);
                }

                public static Bundle createAutoMatchCriteria(int minAutoMatchPlayers, int maxAutoMatchPlayers, long exclusiveBitMask) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, minAutoMatchPlayers);
                    bundle.putInt(GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, maxAutoMatchPlayers);
                    bundle.putLong(GamesClient.EXTRA_EXCLUSIVE_BIT_MASK, exclusiveBitMask);
                    return bundle;
                }

                public Bundle getAutoMatchCriteria() {
                    return this.f1110oh;
                }

                public String getInvitationId() {
                    return this.f1104nM;
                }

                public String[] getInvitedPlayerIds() {
                    return this.f1109og;
                }

                public RealTimeMessageReceivedListener getMessageReceivedListener() {
                    return this.f1108of;
                }

                public RoomStatusUpdateListener getRoomStatusUpdateListener() {
                    return this.f1107oe;
                }

                public RoomUpdateListener getRoomUpdateListener() {
                    return this.f1106od;
                }

                public int getVariant() {
                    return this.f1105nR;
                }

                public boolean isSocketEnabled() {
                    return this.f1111oi;
                }
            }
