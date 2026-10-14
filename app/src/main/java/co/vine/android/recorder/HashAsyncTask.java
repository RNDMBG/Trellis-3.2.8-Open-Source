            package co.vine.android.recorder;

            import android.os.AsyncTask;
            import com.flurry.android.Constants;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            
            
    public abstract class HashAsyncTask extends AsyncTask<RecordingFile, Void, RecordingFile> implements RecordCompleteConsumer {
                private final RecordCompleteConsumer mParent;

                @Override 
                public abstract void onComplete(RecordingFile recordingFile, RecordCompleteConsumer recordCompleteConsumer);

                @Override 
                public abstract void onError(RecordingFile recordingFile, Exception exc, RecordCompleteConsumer recordCompleteConsumer);



        private static byte[] createChecksum(String fileName) throws NoSuchAlgorithmException, IOException {
                    int numRead;
            if (!new File(fileName).exists()) {
                return new byte[0];
                    }
            InputStream fis = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            MessageDigest complete = MessageDigest.getInstance("MD5");
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                        }
            } while (numRead != -1);
            return complete.digest();
                }

        public static void setMD5Checksum(RecordingFile filename) throws Exception {
            filename.setHash(getMD5Checksum(filename.getVideoPath()));
                }

        public static String getMD5Checksum(String filename) throws Exception {
            byte[] bytes = createChecksum(filename);
            String result = "";
                    for (byte b : bytes) {
                result = result + Integer.toString((b & Constants.UNKNOWN) + 256, 16).substring(1);
                    }
            return result;
                }

        public HashAsyncTask(RecordCompleteConsumer parent) {
            this.mParent = parent;
                }

        public final void onError(RecordingFile recordingFile, Exception e) {
            onError(recordingFile, e, this.mParent);
                }

        public final void onComplete(RecordingFile recordingFile) {
            onComplete(recordingFile, this.mParent);
                }

                
        protected final void onPostExecute(RecordingFile recordingFiles) {
            onComplete(recordingFiles, this.mParent);
                }

                
        protected RecordingFile doInBackground(RecordingFile... params) {
            if (params == null || params.length == 0) {
                        return null;
                    }
            if (params.length > 1) {
                throw new IllegalArgumentException("You can only throw on file per execute.");
                    }
            RecordingFile file = params[0];
                    try {
                file.setHash(getMD5Checksum(file.getVideoPath()));
                return file;
                    } catch (Exception e) {
                onError(file, e);
                        return null;
                    }
                }
            }
