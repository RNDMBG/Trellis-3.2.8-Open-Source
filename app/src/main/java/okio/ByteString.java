            package okio;

            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutputStream;
            import java.io.OutputStream;
            import java.io.Serializable;
            import java.lang.reflect.Field;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.Arrays;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public final class ByteString implements Serializable {
                private static final long serialVersionUID = 1;
                final byte[] data;
                private transient int hashCode;
                private transient String utf8;
                private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                public static final ByteString EMPTY = m2468of(new byte[0]);

/* 54 */        ByteString(byte[] data) {
/* 56 */            this.data = data;
                }

                /* JADX INFO: renamed from: of */
/* 62 */        public static ByteString m2468of(byte... data) {
/* 63 */            if (data == null) {
                        throw new IllegalArgumentException("data == null");
                    }
/* 64 */            return new ByteString((byte[]) data.clone());
                }

                /* JADX INFO: renamed from: of */
/* 71 */        public static ByteString m2469of(byte[] data, int offset, int byteCount) {
/* 72 */            if (data == null) {
                        throw new IllegalArgumentException("data == null");
                    }
/* 73 */            Util.checkOffsetAndCount(data.length, offset, byteCount);
/* 75 */            byte[] copy = new byte[byteCount];
/* 76 */            System.arraycopy(data, offset, copy, 0, byteCount);
/* 77 */            return new ByteString(copy);
                }

/* 81 */        public static ByteString encodeUtf8(String s) {
/* 82 */            if (s == null) {
                        throw new IllegalArgumentException("s == null");
                    }
/* 83 */            ByteString byteString = new ByteString(s.getBytes(Util.UTF_8));
/* 84 */            byteString.utf8 = s;
/* 85 */            return byteString;
                }

/* 89 */        public String utf8() {
/* 90 */            String result = this.utf8;
/* 92 */            if (result != null) {
                        return result;
                    }
                    String result2 = new String(this.data, Util.UTF_8);
                    this.utf8 = result2;
                    return result2;
                }

/* 100 */       public String base64() {
/* 101 */           return Base64.encode(this.data);
                }

/* 108 */       public static ByteString decodeBase64(String base64) {
/* 109 */           if (base64 == null) {
                        throw new IllegalArgumentException("base64 == null");
                    }
/* 110 */           byte[] decoded = Base64.decode(base64);
/* 111 */           if (decoded != null) {
                        return new ByteString(decoded);
                    }
                    return null;
                }

/* 115 */       public String hex() {
/* 116 */           char[] result = new char[this.data.length * 2];
/* 117 */           int c = 0;
                    for (byte b : this.data) {
/* 119 */               int c2 = c + 1;
                        result[c] = HEX_DIGITS[(b >> 4) & 15];
/* 120 */               c = c2 + 1;
                        result[c2] = HEX_DIGITS[b & 15];
                    }
/* 122 */           return new String(result);
                }

/* 126 */       public static ByteString decodeHex(String hex) {
/* 127 */           if (hex == null) {
                        throw new IllegalArgumentException("hex == null");
                    }
/* 128 */           if (hex.length() % 2 != 0) {
                        throw new IllegalArgumentException("Unexpected hex string: " + hex);
                    }
/* 130 */           byte[] result = new byte[hex.length() / 2];
                    for (int i = 0; i < result.length; i++) {
/* 132 */               int d1 = decodeHexDigit(hex.charAt(i * 2)) << 4;
/* 133 */               int d2 = decodeHexDigit(hex.charAt((i * 2) + 1));
/* 134 */               result[i] = (byte) (d1 + d2);
                    }
/* 136 */           return m2468of(result);
                }

/* 139 */       private static int decodeHexDigit(char c) {
/* 140 */           if (c >= '0' && c <= '9') {
                        return c - '0';
                    }
/* 141 */           if (c >= 'a' && c <= 'f') {
                        return (c - 'a') + 10;
                    }
/* 142 */           if (c < 'A' || c > 'F') {
/* 143 */               throw new IllegalArgumentException("Unexpected hex digit: " + c);
                    }
                    return (c - 'A') + 10;
                }

/* 152 */       public static ByteString read(InputStream in, int byteCount) throws IOException {
/* 153 */           if (in == null) {
                        throw new IllegalArgumentException("in == null");
                    }
/* 154 */           if (byteCount < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                    }
/* 156 */           byte[] result = new byte[byteCount];
/* 157 */           int offset = 0;
                    while (offset < byteCount) {
/* 158 */               int read = in.read(result, offset, byteCount - offset);
/* 159 */               if (read == -1) {
                            throw new EOFException();
                        }
/* 157 */               offset += read;
                    }
/* 161 */           return new ByteString(result);
                }

/* 170 */       public ByteString toAsciiLowercase() {
                    for (int i = 0; i < this.data.length; i++) {
/* 172 */               byte c = this.data[i];
/* 173 */               if (c >= 65 && c <= 90) {
/* 177 */                   byte[] lowercase = (byte[]) this.data.clone();
                            lowercase[i] = (byte) (c + 32);
/* 179 */                   for (int i2 = i + 1; i2 < lowercase.length; i2++) {
/* 180 */                       byte c2 = lowercase[i2];
/* 181 */                       if (c2 >= 65 && c2 <= 90) {
/* 182 */                           lowercase[i2] = (byte) (c2 + 32);
                                }
                            }
/* 184 */                   return new ByteString(lowercase);
                        }
                    }
/* 186 */           return this;
                }

/* 195 */       public ByteString toAsciiUppercase() {
                    for (int i = 0; i < this.data.length; i++) {
/* 197 */               byte c = this.data[i];
/* 198 */               if (c >= 97 && c <= 122) {
/* 202 */                   byte[] lowercase = (byte[]) this.data.clone();
                            lowercase[i] = (byte) (c - 32);
/* 204 */                   for (int i2 = i + 1; i2 < lowercase.length; i2++) {
/* 205 */                       byte c2 = lowercase[i2];
/* 206 */                       if (c2 >= 97 && c2 <= 122) {
/* 207 */                           lowercase[i2] = (byte) (c2 - 32);
                                }
                            }
/* 209 */                   return new ByteString(lowercase);
                        }
                    }
/* 211 */           return this;
                }

/* 215 */       public byte getByte(int pos) {
/* 216 */           return this.data[pos];
                }

/* 222 */       public int size() {
/* 223 */           return this.data.length;
                }

/* 229 */       public byte[] toByteArray() {
/* 230 */           return (byte[]) this.data.clone();
                }

/* 234 */       public void write(OutputStream out) throws IOException {
/* 235 */           if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
/* 236 */           out.write(this.data);
                }

/* 239 */       public boolean equals(Object o) {
                    return o == this || ((o instanceof ByteString) && Arrays.equals(((ByteString) o).data, this.data));
                }

/* 243 */       public int hashCode() {
/* 244 */           int result = this.hashCode;
/* 245 */           if (result != 0) {
                        return result;
                    }
                    int result2 = Arrays.hashCode(this.data);
                    this.hashCode = result2;
                    return result2;
                }

/* 248 */       public String toString() {
/* 249 */           if (this.data.length == 0) {
/* 250 */               return "ByteString[size=0]";
                    }
/* 253 */           if (this.data.length <= 16) {
/* 254 */               return String.format("ByteString[size=%s data=%s]", Integer.valueOf(this.data.length), hex());
                    }
                    try {
/* 258 */               return String.format("ByteString[size=%s md5=%s]", Integer.valueOf(this.data.length), m2468of(MessageDigest.getInstance("MD5").digest(this.data)).hex());
                    } catch (NoSuchAlgorithmException e) {
/* 261 */               throw new AssertionError();
                    }
                }

/* 265 */       private void readObject(ObjectInputStream in) throws IOException {
/* 266 */           int dataLength = in.readInt();
/* 267 */           ByteString byteString = read(in, dataLength);
                    try {
/* 269 */               Field field = ByteString.class.getDeclaredField("data");
/* 270 */               field.setAccessible(true);
/* 271 */               field.set(this, byteString.data);
                    } catch (IllegalAccessException e) {
/* 275 */               throw new AssertionError();
                    } catch (NoSuchFieldException e2) {
/* 273 */               throw new AssertionError();
                    }
                }

/* 279 */       private void writeObject(ObjectOutputStream out) throws IOException {
/* 280 */           out.writeInt(this.data.length);
/* 281 */           out.write(this.data);
                }
            }
