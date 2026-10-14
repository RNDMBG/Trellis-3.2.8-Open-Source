            package com.google.android.exoplayer.drm;

            import android.annotation.TargetApi;
            import android.media.MediaDrm;
            import java.util.UUID;
            
            
            @TargetApi(18)
            public interface MediaDrmCallback {
                byte[] executeKeyRequest(UUID uuid, MediaDrm.KeyRequest keyRequest) throws Exception;

                byte[] executeProvisionRequest(UUID uuid, MediaDrm.ProvisionRequest provisionRequest) throws Exception;
            }
