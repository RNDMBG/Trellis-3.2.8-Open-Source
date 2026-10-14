            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            
            
    public class DemuxOutputStream extends OutputStream {
                private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal<>();

        public OutputStream bindStream(OutputStream output) {
            OutputStream stream = this.m_streams.get();
            this.m_streams.set(output);
            return stream;
                }

                @Override 
        public void close() throws IOException {
            OutputStream output = this.m_streams.get();
            if (output != null) {
                output.close();
                    }
                }

                @Override 
        public void flush() throws IOException {
            OutputStream output = this.m_streams.get();
            if (output != null) {
                output.flush();
                    }
                }

                @Override 
        public void write(int ch) throws IOException {
            OutputStream output = this.m_streams.get();
            if (output != null) {
                output.write(ch);
                    }
                }
            }
