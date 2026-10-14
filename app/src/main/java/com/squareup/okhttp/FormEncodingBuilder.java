            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLEncoder;
            
            
    public final class FormEncodingBuilder {
                private static final MediaType CONTENT_TYPE = MediaType.parse(org.scribe.model.Request.DEFAULT_CONTENT_TYPE);
                private final StringBuilder content = new StringBuilder();

        public FormEncodingBuilder add(String name, String value) {
            if (this.content.length() > 0) {
                this.content.append('&');
                    }
                    try {
                this.content.append(URLEncoder.encode(name, IoUtils.UTF_8)).append('=').append(URLEncoder.encode(value, IoUtils.UTF_8));
                return this;
                    } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
                    }
                }

        public RequestBody build() {
            if (this.content.length() == 0) {
                throw new IllegalStateException("Form encoded body must have at least one part.");
                    }
            byte[] contentBytes = this.content.toString().getBytes(Util.UTF_8);
            return RequestBody.create(CONTENT_TYPE, contentBytes);
                }
            }
