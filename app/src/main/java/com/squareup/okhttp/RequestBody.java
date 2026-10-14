            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.io.File;
            import java.io.IOException;
            import java.nio.charset.Charset;
            import okio.BufferedSink;
            import okio.Okio;
            import okio.Source;
            
            
    public abstract class RequestBody {
                public abstract MediaType contentType();

                public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

        public long contentLength() {
            return -1L;
                }

        public static RequestBody create(MediaType contentType, String content) {
            Charset charset = Util.UTF_8;
            if (contentType != null && (charset = contentType.charset()) == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
                    }
            byte[] bytes = content.getBytes(charset);
            return create(contentType, bytes);
                }

        public static RequestBody create(MediaType contentType, byte[] content) {
            if (content == null) {
                        throw new NullPointerException("content == null");
                    }
            return new C12661(contentType, content);
                }

                
        static class C12661 extends RequestBody {
                    final  byte[] val$content;
                    final  MediaType val$contentType;

            C12661(MediaType mediaType, byte[] bArr) {
                this.val$contentType = mediaType;
                        this.val$content = bArr;
                    }

                    @Override 
            public MediaType contentType() {
                return this.val$contentType;
                    }

                    @Override 
            public long contentLength() {
                return this.val$content.length;
                    }

                    @Override 
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(this.val$content);
                    }
                }

        public static RequestBody create(MediaType contentType, File file) {
            if (file == null) {
                        throw new NullPointerException("content == null");
                    }
            return new C12672(contentType, file);
                }

                
        static class C12672 extends RequestBody {
                    final  MediaType val$contentType;
                    final  File val$file;

            C12672(MediaType mediaType, File file) {
                this.val$contentType = mediaType;
                        this.val$file = file;
                    }

                    @Override 
            public MediaType contentType() {
                return this.val$contentType;
                    }

                    @Override 
            public long contentLength() {
                return this.val$file.length();
                    }

                    @Override 
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                        try {
                    source = Okio.source(this.val$file);
                    sink.writeAll(source);
                    Util.closeQuietly(source);
                        } catch (Throwable th) {
                            Util.closeQuietly(source);
                            throw th;
                        }
                    }
                }
            }
