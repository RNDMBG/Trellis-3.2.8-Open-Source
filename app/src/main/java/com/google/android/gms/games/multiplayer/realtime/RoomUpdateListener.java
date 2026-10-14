            package com.google.android.gms.games.multiplayer.realtime;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RoomUpdateListener {
                void onJoinedRoom(int i, Room room);

                void onLeftRoom(int i, String str);

                void onRoomConnected(int i, Room room);

                void onRoomCreated(int i, Room room);
            }
