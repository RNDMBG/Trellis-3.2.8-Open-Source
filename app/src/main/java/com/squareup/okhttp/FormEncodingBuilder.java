            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Util;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.UnsupportedEncodingException;
            import java.net.URLEncoder;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public final class FormEncodingBuilder {
                private static final MediaType CONTENT_TYPE = MediaType.parse(org.scribe.model.Request.DEFAULT_CONTENT_TYPE);
                private final StringBuilder content = new StringBuilder();

/* 33 */        public FormEncodingBuilder add(String name, String value) {
/* 34 */            if (this.content.length() > 0) {
/* 35 */                this.content.append('&');
                    }
                    try {
/* 38 */                this.content.append(URLEncoder.encode(name, IoUtils.UTF_8)).append('=').append(URLEncoder.encode(value, IoUtils.UTF_8));
/* 44 */                return this;
                    } catch (UnsupportedEncodingException e) {
/* 42 */                throw new AssertionError(e);
                    }
                }

/* 47 */        public RequestBody build() {
/* 48 */            if (this.content.length() == 0) {
/* 49 */                throw new IllegalStateException("Form encoded body must have at least one part.");
                    }
/* 53 */            byte[] contentBytes = this.content.toString().getBytes(Util.UTF_8);
/* 54 */            return RequestBody.create(CONTENT_TYPE, contentBytes);
                }
            }
