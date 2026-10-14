            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.io.Closeable;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            import java.nio.charset.Charset;
            import okio.BufferedSource;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public abstract class ResponseBody implements Closeable {
                private Reader reader;

                public abstract long contentLength();

                public abstract MediaType contentType();

                public abstract BufferedSource source();

/* 41 */        public final InputStream byteStream() {
/* 42 */            return source().inputStream();
                }

/* 47 */        public final byte[] bytes() throws IOException {
/* 48 */            long contentLength = contentLength();
                    if (contentLength > 2147483647L) {
/* 50 */                throw new IOException("Cannot buffer entire body for content length: " + contentLength);
                    }
/* 53 */            BufferedSource source = source();
                    try {
/* 56 */                byte[] bytes = source.readByteArray();
/* 58 */                Util.closeQuietly(source);
                        if (contentLength != -1 && contentLength != bytes.length) {
/* 61 */                    throw new IOException("Content-Length and stream length disagree");
                        }
/* 63 */                return bytes;
                    } catch (Throwable th) {
                        Util.closeQuietly(source);
                        throw th;
                    }
                }

/* 71 */        public final Reader charStream() {
/* 72 */            Reader r = this.reader;
/* 73 */            if (r != null) {
                        return r;
                    }
                    Reader r2 = new InputStreamReader(byteStream(), charset());
                    this.reader = r2;
                    return r2;
                }

/* 81 */        public final String string() throws IOException {
/* 82 */            return new String(bytes(), charset().name());
                }

/* 85 */        private Charset charset() {
/* 86 */            MediaType contentType = contentType();
                    return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 90 */        public void close() throws IOException {
/* 91 */            source().close();
                }
            }
