            package com.google.android.gms.games;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import android.os.Parcelable;
            import com.google.android.gms.common.data.Freezable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Player extends Parcelable, Freezable<Player> {
                String getDisplayName();

                void getDisplayName(CharArrayBuffer charArrayBuffer);

                Uri getHiResImageUri();

                Uri getIconImageUri();

                String getPlayerId();

                long getRetrievedTimestamp();

                boolean hasHiResImage();

                boolean hasIconImage();
            }
