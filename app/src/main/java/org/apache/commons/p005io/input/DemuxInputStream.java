            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class DemuxInputStream extends InputStream {
                private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal<>();

        public InputStream bindStream(InputStream input) {
            InputStream oldValue = this.m_streams.get();
            this.m_streams.set(input);
            return oldValue;
                }

                @Override 
        public void close() throws IOException {
            InputStream input = this.m_streams.get();
            if (input != null) {
                input.close();
                    }
                }

                @Override 
        public int read() throws IOException {
            InputStream input = this.m_streams.get();
            if (input != null) {
                return input.read();
                    }
                    return -1;
                }
            }
