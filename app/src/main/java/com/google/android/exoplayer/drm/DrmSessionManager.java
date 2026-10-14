            package com.google.android.exoplayer.drm;

            import android.annotation.TargetApi;
            import android.media.MediaCrypto;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(16)
            public interface DrmSessionManager {
                public static final int STATE_CLOSED = 1;
                public static final int STATE_ERROR = 0;
                public static final int STATE_OPENED = 3;
                public static final int STATE_OPENED_WITH_KEYS = 4;
                public static final int STATE_OPENING = 2;

                void close();

                Exception getError();

                MediaCrypto getMediaCrypto();

                int getState();

                void open(Map<UUID, byte[]> map, String str);

                boolean requiresSecureDecoderComponent(String str);
            }
