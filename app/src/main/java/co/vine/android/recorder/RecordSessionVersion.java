            package co.vine.android.recorder;

            import android.content.Context;
            import co.vine.android.recorder.audio.AudioArrays;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.File;
            import java.io.IOException;
            
            
    public enum RecordSessionVersion {
                HW("drafts_hw", ".mp4", MimeTypes.VIDEO_MP4, false),
                SW_MP4("drafts", ".mp4", MimeTypes.VIDEO_MP4, true),
                SW_WEBM("drafts_webm", ".webm", MimeTypes.VIDEO_WEBM, true);

                public final String folder;
                private RecordSessionManager manager;
                public final String mimeType;
                public final String videoOutputExtension;
                public final boolean willEventuallyTranscoded;

        RecordSessionVersion(String folder, String videoOutputExtension, String mimeType, boolean willEventuallyTranscoded) {
            this.folder = folder;
            this.videoOutputExtension = videoOutputExtension;
            this.mimeType = mimeType;
            this.willEventuallyTranscoded = willEventuallyTranscoded;
                }

        public synchronized RecordSessionManager getManager(Context context) throws IOException {
                    try {
                        if (this.manager == null) {
                    this.manager = new RecordSessionManager(context, this);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return this.manager;
                }

        public static void deleteSessionWithName(Context context, String parentName) throws IOException {
            RecordSessionVersion[] arr$ = values();
                    for (RecordSessionVersion version : arr$) {
                File parentFolder = version.getManager(context).getFolderFromName(parentName);
                if (parentFolder != null && parentFolder.exists()) {
                    RecordSessionManager.deleteSession(parentFolder, "Done with session.");
                        }
                    }
                }

        public static RecordSessionVersion[] getValuesWithManagers(Context context) throws IOException {
            RecordSessionVersion[] arr$ = values();
                    for (RecordSessionVersion version : arr$) {
                version.getManager(context);
                    }
            return values();
                }

        public AudioArrays.AudioArrayType getAudioArrayType() {
                    return this == HW ? AudioArrays.AudioArrayType.BYTE : AudioArrays.AudioArrayType.SHORT;
                }
            }
