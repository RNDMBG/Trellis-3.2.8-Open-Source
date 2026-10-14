            package com.google.android.exoplayer.drm;

            import android.annotation.TargetApi;
            import android.media.MediaDrm;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(18)
            public interface MediaDrmCallback {
                byte[] executeKeyRequest(UUID uuid, MediaDrm.KeyRequest keyRequest) throws Exception;

                byte[] executeProvisionRequest(UUID uuid, MediaDrm.ProvisionRequest provisionRequest) throws Exception;
            }
