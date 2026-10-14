            package twitter4j.internal.http;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.File;
            import java.io.InputStream;
            import java.io.Serializable;
            import java.io.UnsupportedEncodingException;
            import java.net.URLEncoder;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public final class HttpParameter implements Comparable, Serializable {
                private static final String GIF = "image/gif";
                private static final String JPEG = "image/jpeg";
                private static final String OCTET = "application/octet-stream";
                private static final String PNG = "image/png";
                private static final long serialVersionUID = -8708108746980739212L;
                private File file;
                private InputStream fileBody;
                private String name;
                private String value;

/* 36 */        public HttpParameter(String name, String value) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 38 */            this.name = name;
/* 39 */            this.value = value;
                }

/* 41 */        public HttpParameter(String name, File file) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 43 */            this.name = name;
/* 44 */            this.file = file;
                }

/* 46 */        public HttpParameter(String name, String fileName, InputStream fileBody) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 48 */            this.name = name;
/* 49 */            this.file = new File(fileName);
/* 50 */            this.fileBody = fileBody;
                }

/* 52 */        public HttpParameter(String name, int value) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 54 */            this.name = name;
/* 55 */            this.value = String.valueOf(value);
                }

/* 57 */        public HttpParameter(String name, long value) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 59 */            this.name = name;
/* 60 */            this.value = String.valueOf(value);
                }

/* 62 */        public HttpParameter(String name, double value) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 64 */            this.name = name;
/* 65 */            this.value = String.valueOf(value);
                }

/* 67 */        public HttpParameter(String name, boolean value) {
/* 31 */            this.name = null;
/* 32 */            this.value = null;
/* 33 */            this.file = null;
/* 34 */            this.fileBody = null;
/* 69 */            this.name = name;
/* 70 */            this.value = String.valueOf(value);
                }

/* 73 */        public String getName() {
/* 74 */            return this.name;
                }

/* 77 */        public String getValue() {
/* 78 */            return this.value;
                }

/* 81 */        public File getFile() {
/* 82 */            return this.file;
                }

/* 85 */        public InputStream getFileBody() {
/* 86 */            return this.fileBody;
                }

/* 89 */        public boolean isFile() {
                    return this.file != null;
                }

/* 93 */        public boolean hasFileBody() {
                    return this.fileBody != null;
                }

/* 105 */       public String getContentType() {
/* 106 */           if (!isFile()) {
/* 107 */               throw new IllegalStateException("not a file");
                    }
/* 110 */           String extensions = this.file.getName();
/* 111 */           int index = extensions.lastIndexOf(".");
/* 112 */           if (-1 == index) {
/* 114 */               return OCTET;
                    }
/* 116 */           String extensions2 = extensions.substring(extensions.lastIndexOf(".") + 1).toLowerCase();
/* 117 */           if (extensions2.length() == 3) {
/* 118 */               if ("gif".equals(extensions2)) {
/* 119 */                   return GIF;
                        }
/* 120 */               if ("png".equals(extensions2)) {
/* 121 */                   return PNG;
                        }
/* 122 */               if ("jpg".equals(extensions2)) {
/* 123 */                   return JPEG;
                        }
/* 125 */               return OCTET;
                    }
/* 127 */           if (extensions2.length() == 4 && "jpeg".equals(extensions2)) {
/* 129 */               return JPEG;
                    }
/* 131 */           return OCTET;
                }

/* 141 */       public boolean equals(Object o) {
/* 142 */           if (this == o) {
                        return true;
                    }
/* 143 */           if (!(o instanceof HttpParameter)) {
                        return false;
                    }
/* 145 */           HttpParameter that = (HttpParameter) o;
/* 147 */           if (this.file == null ? that.file != null : !this.file.equals(that.file)) {
                        return false;
                    }
/* 149 */           if (this.fileBody == null ? that.fileBody != null : !this.fileBody.equals(that.fileBody)) {
                        return false;
                    }
/* 151 */           if (!this.name.equals(that.name)) {
                        return false;
                    }
/* 152 */           if (this.value != null) {
                        if (this.value.equals(that.value)) {
                            return true;
                        }
                    } else if (that.value == null) {
                        return true;
                    }
                    return false;
                }

/* 158 */       public static boolean containsFile(HttpParameter[] params) {
/* 159 */           boolean containsFile = false;
/* 160 */           if (params == null) {
/* 161 */               return false;
                    }
                    for (HttpParameter param : params) {
/* 164 */               if (param.isFile()) {
/* 165 */                   containsFile = true;
                            break;
                        }
                    }
                    return containsFile;
                }

/* 173 */       static boolean containsFile(List<HttpParameter> params) {
                    for (HttpParameter param : params) {
/* 176 */               if (param.isFile()) {
/* 177 */                   return true;
                        }
                    }
/* 174 */           return false;
                }

/* 184 */       public static HttpParameter[] getParameterArray(String name, String value) {
/* 185 */           return new HttpParameter[]{new HttpParameter(name, value)};
                }

/* 188 */       public static HttpParameter[] getParameterArray(String name, int value) {
/* 189 */           return getParameterArray(name, String.valueOf(value));
                }

/* 193 */       public static HttpParameter[] getParameterArray(String name1, String value1, String name2, String value2) {
/* 194 */           return new HttpParameter[]{new HttpParameter(name1, value1), new HttpParameter(name2, value2)};
                }

/* 199 */       public static HttpParameter[] getParameterArray(String name1, int value1, String name2, int value2) {
/* 200 */           return getParameterArray(name1, String.valueOf(value1), name2, String.valueOf(value2));
                }

/* 204 */       public int hashCode() {
/* 205 */           int result = this.name.hashCode();
/* 208 */           return (((((result * 31) + (this.value != null ? this.value.hashCode() : 0)) * 31) + (this.file != null ? this.file.hashCode() : 0)) * 31) + (this.fileBody != null ? this.fileBody.hashCode() : 0);
                }

/* 213 */       public String toString() {
/* 214 */           return "PostParameter{name='" + this.name + "', value='" + this.value + "', file=" + this.file + ", fileBody=" + this.fileBody + '}';
                }

                @Override // java.lang.Comparable
/* 224 */       public int compareTo(Object o) {
/* 225 */           HttpParameter that = (HttpParameter) o;
/* 226 */           int compared = this.name.compareTo(that.name);
/* 227 */           if (compared == 0) {
/* 228 */               return this.value.compareTo(that.value);
                    }
/* 230 */           return compared;
                }

/* 233 */       public static String encodeParameters(HttpParameter[] httpParams) {
/* 234 */           if (httpParams == null) {
/* 235 */               return "";
                    }
/* 237 */           StringBuilder buf = new StringBuilder();
                    for (int j = 0; j < httpParams.length; j++) {
/* 239 */               if (httpParams[j].isFile()) {
/* 240 */                   throw new IllegalArgumentException("parameter [" + httpParams[j].name + "]should be text");
                        }
/* 242 */               if (j != 0) {
/* 243 */                   buf.append("&");
                        }
/* 245 */               buf.append(encode(httpParams[j].name)).append("=").append(encode(httpParams[j].value));
                    }
/* 248 */           return buf.toString();
                }

/* 258 */       public static String encode(String value) {
/* 259 */           String encoded = null;
                    try {
/* 261 */               encoded = URLEncoder.encode(value, IoUtils.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                    }
/* 264 */           StringBuilder buf = new StringBuilder(encoded.length());
/* 266 */           int i = 0;
                    while (i < encoded.length()) {
/* 267 */               char focus = encoded.charAt(i);
/* 268 */               if (focus == '*') {
/* 269 */                   buf.append("%2A");
                        } else if (focus == '+') {
/* 271 */                   buf.append("%20");
                        } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
/* 274 */                   buf.append('~');
/* 275 */                   i += 2;
                        } else {
/* 277 */                   buf.append(focus);
                        }
/* 266 */               i++;
                    }
/* 280 */           return buf.toString();
                }
            }
