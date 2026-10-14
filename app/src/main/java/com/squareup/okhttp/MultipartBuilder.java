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
            
            
    public final class MultipartBuilder {
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

        static  byte[] access$000() {
            return CRLF;
                }

        static  byte[] access$100() {
            return DASHDASH;
                }

        public MultipartBuilder() {
            this(UUID.randomUUID().toString());
                }

        public MultipartBuilder(String boundary) {
            this.type = MIXED;
            this.length = 0L;
            this.partHeadings = new ArrayList();
            this.partBodies = new ArrayList();
            this.boundary = ByteString.encodeUtf8(boundary);
                }

       public MultipartBuilder type(MediaType type) {
           if (type == null) {
               throw new NullPointerException("type == null");
                    }
           if (!type.type().equals("multipart")) {
               throw new IllegalArgumentException("multipart != " + type);
                    }
           this.type = type;
           return this;
                }

       public MultipartBuilder addPart(RequestBody body) {
           return addPart(null, body);
                }

       public MultipartBuilder addPart(Headers headers, RequestBody body) {
           if (body == null) {
               throw new NullPointerException("body == null");
                    }
           if (headers != null && headers.get("Content-Type") != null) {
               throw new IllegalArgumentException("Unexpected header: Content-Type");
                    }
           if (headers != null && headers.get("Content-Length") != null) {
               throw new IllegalArgumentException("Unexpected header: Content-Length");
                    }
           Buffer heading = createPartHeading(headers, body, this.partHeadings.isEmpty());
           this.partHeadings.add(heading);
           this.partBodies.add(body);
           long bodyContentLength = body.contentLength();
                    if (bodyContentLength == -1) {
               this.length = -1L;
                    } else if (this.length != -1) {
               this.length += heading.size() + bodyContentLength;
                    }
           return this;
                }

       private static StringBuilder appendQuotedString(StringBuilder target, String key) {
           target.append(CommonUtil.MARKER_QUOTES);
                    int len = key.length();
                    for (int i = 0; i < len; i++) {
               char ch = key.charAt(i);
                        switch (ch) {
                            case '\n':
                       target.append("%0A");
                                break;
                            case '\r':
                       target.append("%0D");
                                break;
                            case '\"':
                       target.append("%22");
                                break;
                            default:
                       target.append(ch);
                                break;
                        }
                    }
           target.append(CommonUtil.MARKER_QUOTES);
           return target;
                }

       public MultipartBuilder addFormDataPart(String name, String value) {
           return addFormDataPart(name, null, RequestBody.create((MediaType) null, value));
                }

       public MultipartBuilder addFormDataPart(String name, String filename, RequestBody value) {
           if (name == null) {
               throw new NullPointerException("name == null");
                    }
           StringBuilder disposition = new StringBuilder("form-data; name=");
           appendQuotedString(disposition, name);
           if (filename != null) {
               disposition.append("; filename=");
               appendQuotedString(disposition, filename);
                    }
                    return addPart(Headers.m2467of("Content-Disposition", disposition.toString()), value);
                }

       private Buffer createPartHeading(Headers headers, RequestBody body, boolean isFirst) {
           Buffer sink = new Buffer();
           if (!isFirst) {
               sink.write(CRLF);
                    }
           sink.write(DASHDASH);
           sink.write(this.boundary);
           sink.write(CRLF);
           if (headers != null) {
                        for (int i = 0; i < headers.size(); i++) {
                   sink.writeUtf8(headers.name(i)).write(COLONSPACE).writeUtf8(headers.value(i)).write(CRLF);
                        }
                    }
           MediaType contentType = body.contentType();
           if (contentType != null) {
               sink.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).write(CRLF);
                    }
           long contentLength = body.contentLength();
                    if (contentLength != -1) {
               sink.writeUtf8("Content-Length: ").writeUtf8(Long.toString(contentLength)).write(CRLF);
                    }
           sink.write(CRLF);
           return sink;
                }

       public RequestBody build() {
           if (this.partHeadings.isEmpty()) {
               throw new IllegalStateException("Multipart body must have at least one part.");
                    }
           return new MultipartRequestBody(this.type, this.boundary, this.partHeadings, this.partBodies, this.length);
                }

       private static final class MultipartRequestBody extends RequestBody {
                    private final ByteString boundary;
                    private final MediaType contentType;
                    private final long length;
                    private final List<RequestBody> partBodies;
                    private final List<Buffer> partHeadings;

           public MultipartRequestBody(MediaType type, ByteString boundary, List<Buffer> partHeadings, List<RequestBody> partBodies, long length) {
               if (type == null) {
                            throw new NullPointerException("type == null");
                        }
               this.boundary = boundary;
               this.contentType = MediaType.parse(type + "; boundary=" + boundary.utf8());
               this.partHeadings = Util.immutableList(partHeadings);
               this.partBodies = Util.immutableList(partBodies);
               this.length = length != -1 ? length + ((long) (MultipartBuilder.access$000().length + MultipartBuilder.access$100().length + boundary.size() + MultipartBuilder.access$100().length + MultipartBuilder.access$000().length)) : length;
                    }

                    @Override 
           public long contentLength() {
               return this.length;
                    }

                    @Override 
           public MediaType contentType() {
               return this.contentType;
                    }

                    @Override 
           public void writeTo(BufferedSink sink) throws IOException {
                        int size = this.partHeadings.size();
                        for (int i = 0; i < size; i++) {
                   sink.writeAll(this.partHeadings.get(i).m2481clone());
                   this.partBodies.get(i).writeTo(sink);
                        }
               sink.write(MultipartBuilder.access$000());
               sink.write(MultipartBuilder.access$100());
               sink.write(this.boundary);
               sink.write(MultipartBuilder.access$100());
               sink.write(MultipartBuilder.access$000());
                    }
                }
            }
