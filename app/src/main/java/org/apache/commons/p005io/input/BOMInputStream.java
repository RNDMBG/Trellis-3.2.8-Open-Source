            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            import java.util.Arrays;
            import java.util.Comparator;
            import java.util.List;
            import org.apache.commons.p005io.ByteOrderMark;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 105 */   public class BOMInputStream extends ProxyInputStream {
                private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator = new C13051();
                private final List<ByteOrderMark> boms;
                private ByteOrderMark byteOrderMark;
                private int fbIndex;
                private int fbLength;
                private int[] firstBytes;
                private final boolean include;
                private int markFbIndex;
                private boolean markedAtStart;

/* 106 */       public BOMInputStream(InputStream delegate) {
/* 107 */           this(delegate, false, ByteOrderMark.UTF_8);
                }

/* 118 */       public BOMInputStream(InputStream delegate, boolean include) {
/* 119 */           this(delegate, include, ByteOrderMark.UTF_8);
                }

/* 130 */       public BOMInputStream(InputStream delegate, ByteOrderMark... boms) {
/* 131 */           this(delegate, false, boms);
                }

                /* JADX INFO: renamed from: org.apache.commons.io.input.BOMInputStream$1 */
/* 135 */       static class C13051 implements Comparator<ByteOrderMark> {
/* 136 */           C13051() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 139 */           public int compare2(ByteOrderMark bom1, ByteOrderMark bom2) {
/* 140 */               int len1 = bom1.length();
/* 141 */               int len2 = bom2.length();
/* 142 */               if (len1 > len2) {
/* 143 */                   return -1;
                        }
/* 145 */               if (len2 > len1) {
/* 146 */                   return 1;
                        }
/* 148 */               return 0;
                    }
                }

/* 162 */       public BOMInputStream(InputStream delegate, boolean include, ByteOrderMark... boms) {
/* 163 */           super(delegate);
/* 164 */           if (boms == null || boms.length == 0) {
/* 165 */               throw new IllegalArgumentException("No BOMs specified");
                    }
/* 167 */           this.include = include;
/* 169 */           Arrays.sort(boms, ByteOrderMarkLengthComparator);
/* 170 */           this.boms = Arrays.asList(boms);
                }

/* 181 */       public boolean hasBOM() throws IOException {
                    return getBOM() != null;
                }

/* 196 */       public boolean hasBOM(ByteOrderMark bom) throws IOException {
/* 197 */           if (this.boms.contains(bom)) {
                        return this.byteOrderMark != null && getBOM().equals(bom);
                    }
/* 198 */           throw new IllegalArgumentException("Stream not configure to detect " + bom);
                }

/* 210 */       public ByteOrderMark getBOM() throws IOException {
/* 211 */           if (this.firstBytes == null) {
/* 212 */               this.fbLength = 0;
/* 214 */               int maxBomSize = this.boms.get(0).length();
/* 215 */               this.firstBytes = new int[maxBomSize];
                        for (int i = 0; i < this.firstBytes.length; i++) {
/* 218 */                   this.firstBytes[i] = this.in.read();
/* 219 */                   this.fbLength++;
/* 220 */                   if (this.firstBytes[i] < 0) {
                                break;
                            }
                        }
/* 225 */               this.byteOrderMark = find();
/* 226 */               if (this.byteOrderMark != null && !this.include) {
/* 228 */                   if (this.byteOrderMark.length() < this.firstBytes.length) {
/* 229 */                       this.fbIndex = this.byteOrderMark.length();
                            } else {
/* 231 */                       this.fbLength = 0;
                            }
                        }
                    }
/* 236 */           return this.byteOrderMark;
                }

/* 247 */       public String getBOMCharsetName() throws IOException {
/* 248 */           getBOM();
/* 249 */           if (this.byteOrderMark == null) {
                        return null;
                    }
                    return this.byteOrderMark.getCharsetName();
                }

/* 261 */       private int readFirstBytes() throws IOException {
/* 262 */           getBOM();
/* 263 */           if (this.fbIndex >= this.fbLength) {
                        return -1;
                    }
                    int[] iArr = this.firstBytes;
                    int i = this.fbIndex;
                    this.fbIndex = i + 1;
                    return iArr[i];
                }

/* 271 */       private ByteOrderMark find() {
                    for (ByteOrderMark bom : this.boms) {
/* 273 */               if (matches(bom)) {
/* 277 */                   return bom;
                        }
                    }
                    return null;
                }

/* 291 */       private boolean matches(ByteOrderMark bom) {
                    for (int i = 0; i < bom.length(); i++) {
/* 293 */               if (bom.get(i) != this.firstBytes[i]) {
/* 294 */                   return false;
                        }
                    }
/* 297 */           return true;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 312 */       public int read() throws IOException {
/* 313 */           int b = readFirstBytes();
                    return b >= 0 ? b : this.in.read();
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 331 */       public int read(byte[] buf, int off, int len) throws IOException {
/* 332 */           int firstCount = 0;
/* 333 */           int b = 0;
                    int off2 = off;
/* 334 */           while (len > 0 && b >= 0) {
/* 335 */               b = readFirstBytes();
/* 336 */               if (b >= 0) {
                            buf[off2] = (byte) (b & 255);
/* 338 */                   len--;
/* 339 */                   firstCount++;
/* 337 */                   off2++;
                        }
                    }
/* 342 */           int secondCount = this.in.read(buf, off2, len);
/* 343 */           if (secondCount >= 0) {
                        return firstCount + secondCount;
                    }
                    if (firstCount > 0) {
                        return firstCount;
                    }
                    return -1;
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 356 */       public int read(byte[] buf) throws IOException {
/* 357 */           return read(buf, 0, buf.length);
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 367 */       public synchronized void mark(int readlimit) {
                    try {
                        this.markFbIndex = this.fbIndex;
/* 369 */               this.markedAtStart = this.firstBytes == null;
/* 370 */               this.in.mark(readlimit);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 380 */       public synchronized void reset() throws IOException {
                    try {
                        this.fbIndex = this.markFbIndex;
/* 382 */               if (this.markedAtStart) {
/* 383 */                   this.firstBytes = null;
                        }
/* 386 */               this.in.reset();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream, java.io.FilterInputStream, java.io.InputStream
/* 399 */       public long skip(long n) throws IOException {
                    while (n > 0 && readFirstBytes() >= 0) {
/* 401 */               n--;
                    }
/* 403 */           return this.in.skip(n);
                }
            }
