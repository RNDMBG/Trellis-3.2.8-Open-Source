            package com.google.android.exoplayer.drm;

            import android.annotation.TargetApi;
            import android.media.MediaCrypto;
            import java.util.Map;
            import java.util.UUID;
            
            
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
