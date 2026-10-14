            package okio;

            import java.io.IOException;
            
            
    public abstract class ForwardingSink implements Sink {
                private final Sink delegate;

        public ForwardingSink(Sink delegate) {
            if (delegate == null) {
                        throw new IllegalArgumentException("delegate == null");
                    }
            this.delegate = delegate;
                }

        public final Sink delegate() {
            return this.delegate;
                }

                @Override 
        public void write(Buffer source, long byteCount) throws IOException {
            this.delegate.write(source, byteCount);
                }

                @Override 
        public void flush() throws IOException {
            this.delegate.flush();
                }

                @Override 
        public Timeout timeout() {
            return this.delegate.timeout();
                }

                @Override 
        public void close() throws IOException {
            this.delegate.close();
                }

        public String toString() {
            return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
                }
            }
