            package com.squareup.okhttp;

            import co.vine.android.util.CommonUtil;
            import com.squareup.okhttp.internal.Util;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.UUID;
            import okio.Buffer;
            import okio.BufferedSink;
            import okio.ByteString;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public final class MultipartBuilder {
                private final ByteString boundary;
                private long length;
                private final List<RequestBody> partBodies;
                private final List<Buffer> partHeadings;
                private MediaType type;
                public static final MediaType MIXED = MediaType.parse("multipart/mixed");
                public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
                public static final MediaType DIGEST = MediaType.parse("multipart/digest");
                public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
                public static final MediaType FORM = MediaType.parse("multipart/form-data");
                private static final byte[] COLONSPACE = {58, 32};
                private static final byte[] CRLF = {13, 10};
                private static final byte[] DASHDASH = {45, 45};

/* 30 */        static /* synthetic */ byte[] access$000() {
/* 31 */            return CRLF;
                }

/* 30 */        static /* synthetic */ byte[] access$100() {
/* 31 */            return DASHDASH;
                }

/* 83 */        public MultipartBuilder() {
/* 84 */            this(UUID.randomUUID().toString());
                }

/* 91 */        public MultipartBuilder(String boundary) {
/* 75 */            this.type = MIXED;
/* 76 */            this.length = 0L;
/* 79 */            this.partHeadings = new ArrayList();
/* 80 */            this.partBodies = new ArrayList();
/* 93 */            this.boundary = ByteString.encodeUtf8(boundary);
                }

/* 101 */       public MultipartBuilder type(MediaType type) {
/* 102 */           if (type == null) {
/* 103 */               throw new NullPointerException("type == null");
                    }
/* 105 */           if (!type.type().equals("multipart")) {
/* 106 */               throw new IllegalArgumentException("multipart != " + type);
                    }
/* 108 */           this.type = type;
/* 109 */           return this;
                }

/* 113 */       public MultipartBuilder addPart(RequestBody body) {
/* 114 */           return addPart(null, body);
                }

/* 118 */       public MultipartBuilder addPart(Headers headers, RequestBody body) {
/* 119 */           if (body == null) {
/* 120 */               throw new NullPointerException("body == null");
                    }
/* 122 */           if (headers != null && headers.get("Content-Type") != null) {
/* 123 */               throw new IllegalArgumentException("Unexpected header: Content-Type");
                    }
/* 125 */           if (headers != null && headers.get("Content-Length") != null) {
/* 126 */               throw new IllegalArgumentException("Unexpected header: Content-Length");
                    }
/* 129 */           Buffer heading = createPartHeading(headers, body, this.partHeadings.isEmpty());
/* 130 */           this.partHeadings.add(heading);
/* 131 */           this.partBodies.add(body);
/* 133 */           long bodyContentLength = body.contentLength();
                    if (bodyContentLength == -1) {
/* 135 */               this.length = -1L;
                    } else if (this.length != -1) {
/* 137 */               this.length += heading.size() + bodyContentLength;
                    }
/* 140 */           return this;
                }

/* 154 */       private static StringBuilder appendQuotedString(StringBuilder target, String key) {
/* 155 */           target.append(CommonUtil.MARKER_QUOTES);
                    int len = key.length();
                    for (int i = 0; i < len; i++) {
/* 157 */               char ch = key.charAt(i);
                        switch (ch) {
                            case '\n':
/* 160 */                       target.append("%0A");
                                break;
                            case '\r':
/* 163 */                       target.append("%0D");
                                break;
                            case '\"':
/* 166 */                       target.append("%22");
                                break;
                            default:
/* 169 */                       target.append(ch);
                                break;
                        }
                    }
/* 173 */           target.append(CommonUtil.MARKER_QUOTES);
/* 174 */           return target;
                }

/* 178 */       public MultipartBuilder addFormDataPart(String name, String value) {
/* 179 */           return addFormDataPart(name, null, RequestBody.create((MediaType) null, value));
                }

/* 183 */       public MultipartBuilder addFormDataPart(String name, String filename, RequestBody value) {
/* 184 */           if (name == null) {
/* 185 */               throw new NullPointerException("name == null");
                    }
/* 187 */           StringBuilder disposition = new StringBuilder("form-data; name=");
/* 188 */           appendQuotedString(disposition, name);
/* 190 */           if (filename != null) {
/* 191 */               disposition.append("; filename=");
/* 192 */               appendQuotedString(disposition, filename);
                    }
                    return addPart(Headers.m2467of("Content-Disposition", disposition.toString()), value);
                }

/* 199 */       private Buffer createPartHeading(Headers headers, RequestBody body, boolean isFirst) {
/* 200 */           Buffer sink = new Buffer();
/* 202 */           if (!isFirst) {
/* 203 */               sink.write(CRLF);
                    }
/* 205 */           sink.write(DASHDASH);
/* 206 */           sink.write(this.boundary);
/* 207 */           sink.write(CRLF);
/* 209 */           if (headers != null) {
                        for (int i = 0; i < headers.size(); i++) {
/* 211 */                   sink.writeUtf8(headers.name(i)).write(COLONSPACE).writeUtf8(headers.value(i)).write(CRLF);
                        }
                    }
/* 218 */           MediaType contentType = body.contentType();
/* 219 */           if (contentType != null) {
/* 220 */               sink.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).write(CRLF);
                    }
/* 225 */           long contentLength = body.contentLength();
                    if (contentLength != -1) {
/* 227 */               sink.writeUtf8("Content-Length: ").writeUtf8(Long.toString(contentLength)).write(CRLF);
                    }
/* 232 */           sink.write(CRLF);
/* 234 */           return sink;
                }

/* 238 */       public RequestBody build() {
/* 239 */           if (this.partHeadings.isEmpty()) {
/* 240 */               throw new IllegalStateException("Multipart body must have at least one part.");
                    }
/* 242 */           return new MultipartRequestBody(this.type, this.boundary, this.partHeadings, this.partBodies, this.length);
                }

/* 251 */       private static final class MultipartRequestBody extends RequestBody {
                    private final ByteString boundary;
                    private final MediaType contentType;
                    private final long length;
                    private final List<RequestBody> partBodies;
                    private final List<Buffer> partHeadings;

/* 252 */           public MultipartRequestBody(MediaType type, ByteString boundary, List<Buffer> partHeadings, List<RequestBody> partBodies, long length) {
/* 254 */               if (type == null) {
                            throw new NullPointerException("type == null");
                        }
/* 256 */               this.boundary = boundary;
/* 257 */               this.contentType = MediaType.parse(type + "; boundary=" + boundary.utf8());
/* 258 */               this.partHeadings = Util.immutableList(partHeadings);
/* 259 */               this.partBodies = Util.immutableList(partBodies);
/* 264 */               this.length = length != -1 ? length + ((long) (MultipartBuilder.access$000().length + MultipartBuilder.access$100().length + boundary.size() + MultipartBuilder.access$100().length + MultipartBuilder.access$000().length)) : length;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 267 */           public long contentLength() {
/* 268 */               return this.length;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 271 */           public MediaType contentType() {
/* 272 */               return this.contentType;
                    }

                    @Override // com.squareup.okhttp.RequestBody
/* 275 */           public void writeTo(BufferedSink sink) throws IOException {
                        int size = this.partHeadings.size();
                        for (int i = 0; i < size; i++) {
/* 277 */                   sink.writeAll(this.partHeadings.get(i).m2481clone());
/* 278 */                   this.partBodies.get(i).writeTo(sink);
                        }
/* 281 */               sink.write(MultipartBuilder.access$000());
/* 282 */               sink.write(MultipartBuilder.access$100());
/* 283 */               sink.write(this.boundary);
/* 284 */               sink.write(MultipartBuilder.access$100());
/* 285 */               sink.write(MultipartBuilder.access$000());
                    }
                }
            }
