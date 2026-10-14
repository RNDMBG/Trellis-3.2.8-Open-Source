            package com.google.android.gms.games.multiplayer;

            import com.google.android.gms.games.Player;
            import com.google.android.gms.internal.C1028dm;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class ParticipantUtils {
                private ParticipantUtils() {
                }

                /* JADX INFO: renamed from: Q */
                public static boolean m1214Q(String str) {
                    C1028dm.m1601a(str, "Participant ID must not be null");
                    return str.startsWith("p_");
                }

                public static String getParticipantId(ArrayList<Participant> participants, String playerId) {
                    int size = participants.size();
                    for (int i = 0; i < size; i++) {
                        Participant participant = participants.get(i);
                        Player player = participant.getPlayer();
                        if (player != null && player.getPlayerId().equals(playerId)) {
                            return participant.getParticipantId();
                        }
                    }
                    return null;
                }
            }
