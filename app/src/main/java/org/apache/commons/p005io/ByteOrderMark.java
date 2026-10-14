            package org.apache.commons.p005io;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 67 */    public class ByteOrderMark implements Serializable {
                private static final long serialVersionUID = 1;
                private final int[] bytes;
                private final String charsetName;
                public static final ByteOrderMark UTF_8 = new ByteOrderMark(IoUtils.UTF_8, 239, 187, 191);
                public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", 254, 255);
                public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", 255, 254);
                public static final ByteOrderMark UTF_32BE = new ByteOrderMark("UTF-32BE", 0, 0, 254, 255);
                public static final ByteOrderMark UTF_32LE = new ByteOrderMark("UTF-32LE", 255, 254, 0, 0);

/* 68 */        public ByteOrderMark(String charsetName, int... bytes) {
/* 70 */            if (charsetName == null || charsetName.length() == 0) {
/* 71 */                throw new IllegalArgumentException("No charsetName specified");
                    }
/* 73 */            if (bytes == null || bytes.length == 0) {
/* 74 */                throw new IllegalArgumentException("No bytes specified");
                    }
/* 76 */            this.charsetName = charsetName;
/* 77 */            this.bytes = new int[bytes.length];
/* 78 */            System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
                }

/* 86 */        public String getCharsetName() {
/* 87 */            return this.charsetName;
                }

/* 95 */        public int length() {
/* 96 */            return this.bytes.length;
                }

/* 105 */       public int get(int pos) {
/* 106 */           return this.bytes[pos];
                }

/* 114 */       public byte[] getBytes() {
/* 115 */           byte[] copy = new byte[this.bytes.length];
                    for (int i = 0; i < this.bytes.length; i++) {
/* 117 */               copy[i] = (byte) this.bytes[i];
                    }
/* 119 */           return copy;
                }

/* 130 */       public boolean equals(Object obj) {
/* 131 */           if (!(obj instanceof ByteOrderMark)) {
                        return false;
                    }
/* 134 */           ByteOrderMark bom = (ByteOrderMark) obj;
/* 135 */           if (this.bytes.length != bom.length()) {
                        return false;
                    }
                    for (int i = 0; i < this.bytes.length; i++) {
/* 139 */               if (this.bytes[i] != bom.get(i)) {
                            return false;
                        }
                    }
/* 143 */           return true;
                }

/* 153 */       public int hashCode() {
/* 154 */           int hashCode = getClass().hashCode();
/* 155 */           int[] arr$ = this.bytes;
                    for (int b : arr$) {
/* 156 */               hashCode += b;
                    }
/* 158 */           return hashCode;
                }

/* 167 */       public String toString() {
/* 168 */           StringBuilder builder = new StringBuilder();
/* 169 */           builder.append(getClass().getSimpleName());
/* 170 */           builder.append('[');
/* 171 */           builder.append(this.charsetName);
/* 172 */           builder.append(": ");
                    for (int i = 0; i < this.bytes.length; i++) {
/* 174 */               if (i > 0) {
/* 175 */                   builder.append(",");
                        }
/* 177 */               builder.append("0x");
/* 178 */               builder.append(Integer.toHexString(this.bytes[i] & 255).toUpperCase());
                    }
/* 180 */           builder.append(']');
/* 181 */           return builder.toString();
                }
            }
