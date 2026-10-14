            package com.squareup.okhttp;

            import java.nio.charset.Charset;
            import java.util.Locale;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public final class MediaType {
                private static final String QUOTED = "\"([^\"]*)\"";
                private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
                private final String charset;
                private final String mediaType;
                private final String subtype;
                private final String type;
                private static final Pattern TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
                private static final Pattern PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");

/* 38 */        private MediaType(String mediaType, String type, String subtype, String charset) {
/* 40 */            this.mediaType = mediaType;
/* 41 */            this.type = type;
/* 42 */            this.subtype = subtype;
/* 43 */            this.charset = charset;
                }

/* 50 */        public static MediaType parse(String string) {
/* 51 */            Matcher typeSubtype = TYPE_SUBTYPE.matcher(string);
/* 52 */            if (!typeSubtype.lookingAt()) {
                        return null;
                    }
/* 53 */            String type = typeSubtype.group(1).toLowerCase(Locale.US);
/* 54 */            String subtype = typeSubtype.group(2).toLowerCase(Locale.US);
/* 56 */            String charset = null;
/* 57 */            Matcher parameter = PARAMETER.matcher(string);
                    for (int s = typeSubtype.end(); s < string.length(); s = parameter.end()) {
/* 59 */                parameter.region(s, string.length());
/* 60 */                if (!parameter.lookingAt()) {
                            return null;
                        }
/* 62 */                String name = parameter.group(1);
/* 63 */                if (name != null && name.equalsIgnoreCase("charset")) {
/* 64 */                    String charsetParameter = parameter.group(2) != null ? parameter.group(2) : parameter.group(3);
/* 67 */                    if (charset != null && !charsetParameter.equalsIgnoreCase(charset)) {
/* 68 */                        throw new IllegalArgumentException("Multiple different charsets: " + string);
                            }
/* 70 */                    charset = charsetParameter;
                        }
                    }
/* 73 */            return new MediaType(string, type, subtype, charset);
                }

/* 80 */        public String type() {
/* 81 */            return this.type;
                }

/* 88 */        public String subtype() {
/* 89 */            return this.subtype;
                }

/* 96 */        public Charset charset() {
/* 97 */            if (this.charset != null) {
                        return Charset.forName(this.charset);
                    }
                    return null;
                }

/* 104 */       public Charset charset(Charset defaultValue) {
/* 105 */           if (this.charset == null) {
                        return defaultValue;
                    }
                    Charset defaultValue2 = Charset.forName(this.charset);
                    return defaultValue2;
                }

/* 112 */       public String toString() {
/* 113 */           return this.mediaType;
                }

/* 116 */       public boolean equals(Object o) {
                    return (o instanceof MediaType) && ((MediaType) o).mediaType.equals(this.mediaType);
                }

/* 120 */       public int hashCode() {
/* 121 */           return this.mediaType.hashCode();
                }
            }
