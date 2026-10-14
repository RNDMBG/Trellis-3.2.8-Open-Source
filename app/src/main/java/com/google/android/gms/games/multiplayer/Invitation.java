            package com.google.android.gms.games.multiplayer;

            import android.os.Parcelable;
            import com.google.android.gms.common.data.Freezable;
            import com.google.android.gms.games.Game;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Invitation extends Parcelable, Freezable<Invitation>, Participatable {
                /* JADX INFO: renamed from: ch */
                int mo1198ch();

                long getCreationTimestamp();

                Game getGame();

                String getInvitationId();

                Participant getInviter();

                int getVariant();
            }
