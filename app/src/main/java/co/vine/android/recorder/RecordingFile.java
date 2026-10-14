            package co.vine.android.recorder;

            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import org.apache.commons.io.FileUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class RecordingFile {
                public ArrayList<RecordSegment> editedSegments;
                public final File folder;
                public boolean isDirty;
                public final boolean isLastSession;
                public boolean isSavedSession;
                private String mHash;
                private RecordSession mSession;
                public final RecordSessionVersion version;

/* 26 */        public RecordingFile(File saveFolder, RecordSession session, boolean isSavedSession, boolean isDirty, boolean isLastSession) {
/* 28 */            if (SLog.sLogsOn) {
/* 29 */                SLog.m465i("[session] New file created: {}.", Integer.valueOf(hashCode()));
                    }
/* 31 */            this.mSession = session;
/* 32 */            this.version = this.mSession.getVersion();
/* 33 */            this.folder = saveFolder;
/* 34 */            this.isSavedSession = isSavedSession;
/* 35 */            this.isLastSession = isLastSession;
/* 36 */            this.isDirty = isDirty;
                    try {
/* 38 */                FileUtils.forceMkdir(this.folder);
                    } catch (IOException e) {
/* 40 */                throw new RuntimeException(e);
                    }
                }

/* 44 */        public boolean hasData() {
                    return this.mSession.getSegments().size() > 0;
                }

/* 48 */        public String getHash() {
/* 49 */            return this.mHash;
                }

/* 52 */        public RecordSession getSession() {
/* 53 */            return this.mSession;
                }

/* 56 */        public void setHash(String hash) {
/* 57 */            this.mHash = hash;
                }

/* 60 */        public String createSegmentVideoPath() {
/* 61 */            return RecordSessionManager.getSegmentVideoPath(this.version, this.folder);
                }

/* 64 */        public String getSegmentThumbnailPath() {
/* 65 */            return RecordSessionManager.getSegmentThumbnailPath(this.folder);
                }

/* 68 */        public String getPreviewVideoPath() {
/* 69 */            return RecordSessionManager.getPreviewVideoPath(this.version, this.folder);
                }

/* 72 */        public String getPreviewThumbnailPath() {
/* 73 */            return RecordSessionManager.getPreviewThumbnailPath(this.folder);
                }

/* 76 */        public String getVideoPath() {
/* 77 */            return RecordSessionManager.getVideoPath(this.version, this.folder);
                }

/* 80 */        public String getThumbnailPath() {
/* 81 */            return RecordSessionManager.getThumbnailPath(this.folder);
                }

/* 84 */        public static String getLastFramePathThumbnailFromThumbnailPath(String thumbnailPath, boolean isPreview) {
/* 85 */            return thumbnailPath + ".last." + String.valueOf(isPreview) + ".jpg";
                }

/* 88 */        public String getLastFramePath() {
/* 89 */            String thumbnailPath = getThumbnailPath();
/* 90 */            String lastThumbnailPath = getLastFramePathThumbnailFromThumbnailPath(thumbnailPath, false);
                    return !new File(lastThumbnailPath).exists() ? thumbnailPath : lastThumbnailPath;
                }

/* 98 */        public void setSession(RecordSession session) {
/* 99 */            this.mSession = session;
                }

/* 103 */       public String toString() {
/* 104 */           return MessageFormatter.format("Folder: {}, isLastSession: {}, isDirty: {}, isSavedSession: {}, Session: {}", new Object[]{this.folder, Boolean.valueOf(this.isLastSession), Boolean.valueOf(this.isDirty), Boolean.valueOf(this.isSavedSession), this.mSession}).getMessage();
                }

/* 108 */       public void invalidateGhostThumbnail() {
/* 109 */           File file = new File(getLastFramePathThumbnailFromThumbnailPath(getPreviewThumbnailPath(), true));
/* 110 */           if (file.exists()) {
/* 111 */               file.renameTo(new File(getLastFramePathThumbnailFromThumbnailPath(getThumbnailPath(), false)));
                    }
                }

/* 117 */       public boolean isValid() {
                    return this.mSession.getSegments().size() > 0;
                }
            }
