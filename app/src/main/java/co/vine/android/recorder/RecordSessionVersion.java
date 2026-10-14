            package co.vine.android.recorder;

            import android.content.Context;
            import co.vine.android.recorder.audio.AudioArrays;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.File;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public enum RecordSessionVersion {
                HW("drafts_hw", ".mp4", MimeTypes.VIDEO_MP4, false),
                SW_MP4("drafts", ".mp4", MimeTypes.VIDEO_MP4, true),
                SW_WEBM("drafts_webm", ".webm", MimeTypes.VIDEO_WEBM, true);

                public final String folder;
                private RecordSessionManager manager;
                public final String mimeType;
                public final String videoOutputExtension;
                public final boolean willEventuallyTranscoded;

/* 23 */        RecordSessionVersion(String folder, String videoOutputExtension, String mimeType, boolean willEventuallyTranscoded) {
/* 25 */            this.folder = folder;
/* 26 */            this.videoOutputExtension = videoOutputExtension;
/* 27 */            this.mimeType = mimeType;
/* 28 */            this.willEventuallyTranscoded = willEventuallyTranscoded;
                }

/* 31 */        public synchronized RecordSessionManager getManager(Context context) throws IOException {
                    try {
                        if (this.manager == null) {
/* 33 */                    this.manager = new RecordSessionManager(context, this);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 35 */            return this.manager;
                }

/* 38 */        public static void deleteSessionWithName(Context context, String parentName) throws IOException {
/* 39 */            RecordSessionVersion[] arr$ = values();
                    for (RecordSessionVersion version : arr$) {
/* 40 */                File parentFolder = version.getManager(context).getFolderFromName(parentName);
/* 41 */                if (parentFolder != null && parentFolder.exists()) {
/* 42 */                    RecordSessionManager.deleteSession(parentFolder, "Done with session.");
                        }
                    }
                }

/* 47 */        public static RecordSessionVersion[] getValuesWithManagers(Context context) throws IOException {
/* 48 */            RecordSessionVersion[] arr$ = values();
                    for (RecordSessionVersion version : arr$) {
/* 49 */                version.getManager(context);
                    }
/* 51 */            return values();
                }

/* 54 */        public AudioArrays.AudioArrayType getAudioArrayType() {
                    return this == HW ? AudioArrays.AudioArrayType.BYTE : AudioArrays.AudioArrayType.SHORT;
                }
            }
