            package com.google.android.exoplayer;

            import java.io.IOException;
            
            
    public class ParserException extends IOException {
        public ParserException(String message) {
            super(message);
                }

        public ParserException(Exception cause) {
            super(cause);
                }
            }
