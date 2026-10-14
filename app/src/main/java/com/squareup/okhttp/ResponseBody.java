            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            import java.nio.charset.Charset;
            import okio.BufferedSource;
            
            
    public abstract class ResponseBody implements Closeable {
                private Reader reader;

                public abstract long contentLength();

                public abstract MediaType contentType();

                public abstract BufferedSource source();

        public final InputStream byteStream() {
            return source().inputStream();
                }

        public final byte[] bytes() throws IOException {
            long contentLength = contentLength();
                    if (contentLength > 2147483647L) {
                throw new IOException("Cannot buffer entire body for content length: " + contentLength);
                    }
            BufferedSource source = source();
                    try {
                byte[] bytes = source.readByteArray();
                Util.closeQuietly(source);
                        if (contentLength != -1 && contentLength != bytes.length) {
                    throw new IOException("Content-Length and stream length disagree");
                        }
                return bytes;
                    } catch (Throwable th) {
                        Util.closeQuietly(source);
                        throw th;
                    }
                }

        public final Reader charStream() {
            Reader r = this.reader;
            if (r != null) {
                        return r;
                    }
                    Reader r2 = new InputStreamReader(byteStream(), charset());
                    this.reader = r2;
                    return r2;
                }

        public final String string() throws IOException {
            return new String(bytes(), charset().name());
                }

        private Charset charset() {
            MediaType contentType = contentType();
                    return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
                }

                @Override 
        public void close() throws IOException {
            source().close();
                }
            }
