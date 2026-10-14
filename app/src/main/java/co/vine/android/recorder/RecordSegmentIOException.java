            package co.vine.android.recorder;

            import java.io.IOException;
            
            
     public class RecordSegmentIOException extends RuntimeException {
        public RecordSegmentIOException(String s) {
            super(s);
                }

        public RecordSegmentIOException(IOException e) {
            super(e);
                }
            }
