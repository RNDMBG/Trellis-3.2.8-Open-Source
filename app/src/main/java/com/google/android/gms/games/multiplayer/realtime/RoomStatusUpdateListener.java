            package com.google.android.gms.games.multiplayer.realtime;

            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RoomStatusUpdateListener {
                void onConnectedToRoom(Room room);

                void onDisconnectedFromRoom(Room room);

                void onP2PConnected(String str);

                void onP2PDisconnected(String str);

                void onPeerDeclined(Room room, List<String> list);

                void onPeerInvitedToRoom(Room room, List<String> list);

                void onPeerJoined(Room room, List<String> list);

                void onPeerLeft(Room room, List<String> list);

                void onPeersConnected(Room room, List<String> list);

                void onPeersDisconnected(Room room, List<String> list);

                void onRoomAutoMatching(Room room);

                void onRoomConnecting(Room room);
            }
