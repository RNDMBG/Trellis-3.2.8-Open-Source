            package co.vine.android.recorder;

            import android.os.AsyncTask;
            import com.flurry.android.Constants;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public abstract class HashAsyncTask extends AsyncTask<RecordingFile, Void, RecordingFile> implements RecordCompleteConsumer {
                private final RecordCompleteConsumer mParent;

                @Override // co.vine.android.recorder.RecordCompleteConsumer
                public abstract void onComplete(RecordingFile recordingFile, RecordCompleteConsumer recordCompleteConsumer);

                @Override // co.vine.android.recorder.RecordCompleteConsumer
                public abstract void onError(RecordingFile recordingFile, Exception exc, RecordCompleteConsumer recordCompleteConsumer);



/* 21 */        private static byte[] createChecksum(String fileName) throws NoSuchAlgorithmException, IOException {
                    int numRead;
/* 22 */            if (!new File(fileName).exists()) {
/* 23 */                return new byte[0];
                    }
/* 25 */            InputStream fis = new FileInputStream(fileName);
/* 26 */            byte[] buffer = new byte[1024];
/* 27 */            MessageDigest complete = MessageDigest.getInstance("MD5");
/* 35 */            do {
/* 31 */                numRead = fis.read(buffer);
/* 32 */                if (numRead > 0) {
/* 33 */                    complete.update(buffer, 0, numRead);
                        }
/* 35 */            } while (numRead != -1);
/* 36 */            return complete.digest();
                }

/* 39 */        public static void setMD5Checksum(RecordingFile filename) throws Exception {
/* 40 */            filename.setHash(getMD5Checksum(filename.getVideoPath()));
                }

/* 43 */        public static String getMD5Checksum(String filename) throws Exception {
/* 44 */            byte[] bytes = createChecksum(filename);
/* 45 */            String result = "";
                    for (byte b : bytes) {
/* 47 */                result = result + Integer.toString((b & Constants.UNKNOWN) + 256, 16).substring(1);
                    }
/* 49 */            return result;
                }

/* 51 */        public HashAsyncTask(RecordCompleteConsumer parent) {
/* 53 */            this.mParent = parent;
                }

/* 56 */        public final void onError(RecordingFile recordingFile, Exception e) {
/* 57 */            onError(recordingFile, e, this.mParent);
                }

/* 60 */        public final void onComplete(RecordingFile recordingFile) {
/* 61 */            onComplete(recordingFile, this.mParent);
                }

                /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 69 */        protected final void onPostExecute(RecordingFile recordingFiles) {
/* 70 */            onComplete(recordingFiles, this.mParent);
                }

                /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 74 */        protected RecordingFile doInBackground(RecordingFile... params) {
/* 75 */            if (params == null || params.length == 0) {
                        return null;
                    }
/* 78 */            if (params.length > 1) {
/* 79 */                throw new IllegalArgumentException("You can only throw on file per execute.");
                    }
/* 81 */            RecordingFile file = params[0];
                    try {
/* 83 */                file.setHash(getMD5Checksum(file.getVideoPath()));
/* 87 */                return file;
                    } catch (Exception e) {
/* 86 */                onError(file, e);
                        return null;
                    }
                }
            }
