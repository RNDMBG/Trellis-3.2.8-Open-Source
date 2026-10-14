            package co.vine.android.recorder;

            import java.io.IOException;
            
            
     public class NotEnoughSpaceException extends IOException {
         public NotEnoughSpaceException() {
                }

        public NotEnoughSpaceException(Throwable e) {
            super(e);
                }
            }
