            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import java.io.File;
            import java.io.IOException;
            import java.nio.charset.Charset;
            import okio.BufferedSink;
            import okio.Okio;
            import okio.Source;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 33 */    public abstract class RequestBody {
                public abstract MediaType contentType();

                public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

/* 34 */        public long contentLength() {
/* 35 */            return -1L;
                }

/* 45 */        public static RequestBody create(MediaType contentType, String content) {
/* 46 */            Charset charset = Util.UTF_8;
/* 47 */            if (contentType != null && (charset = contentType.charset()) == null) {
/* 50 */                charset = Util.UTF_8;
/* 51 */                contentType = MediaType.parse(contentType + "; charset=utf-8");
                    }
/* 54 */            byte[] bytes = content.getBytes(charset);
/* 55 */            return create(contentType, bytes);
                }

/* 59 */        public static RequestBody create(MediaType contentType, byte[] content) {
/* 60 */            if (content == null) {
                        throw new NullPointerException("content == null");
                    }
/* 62 */            return new C12661(contentType, content);
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.RequestBody$1 */
/* 60 */        static class C12661 extends RequestBody {
                    final /* synthetic */ byte[] val$content;
                    final /* synthetic */ MediaType val$contentType;

/* 61 */            C12661(MediaType mediaType, byte[] bArr) {
/* 62 */                this.val$contentType = mediaType;
                        this.val$content = bArr;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 63 */            public MediaType contentType() {
/* 64 */                return this.val$contentType;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 67 */            public long contentLength() {
/* 68 */                return this.val$content.length;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 71 */            public void writeTo(BufferedSink sink) throws IOException {
/* 72 */                sink.write(this.val$content);
                    }
                }

/* 78 */        public static RequestBody create(MediaType contentType, File file) {
/* 79 */            if (file == null) {
                        throw new NullPointerException("content == null");
                    }
/* 81 */            return new C12672(contentType, file);
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.RequestBody$2 */
/* 79 */        static class C12672 extends RequestBody {
                    final /* synthetic */ MediaType val$contentType;
                    final /* synthetic */ File val$file;

/* 80 */            C12672(MediaType mediaType, File file) {
/* 81 */                this.val$contentType = mediaType;
                        this.val$file = file;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 82 */            public MediaType contentType() {
/* 83 */                return this.val$contentType;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 86 */            public long contentLength() {
/* 87 */                return this.val$file.length();
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 90 */            public void writeTo(BufferedSink sink) throws IOException {
/* 91 */                Source source = null;
                        try {
/* 93 */                    source = Okio.source(this.val$file);
/* 94 */                    sink.writeAll(source);
/* 96 */                    Util.closeQuietly(source);
                        } catch (Throwable th) {
                            Util.closeQuietly(source);
                            throw th;
                        }
                    }
                }
            }
