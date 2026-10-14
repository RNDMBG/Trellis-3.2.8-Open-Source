            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.io.Serializable;
            import java.util.Arrays;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 110 */   public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable {
                private static final long serialVersionUID = -547733176983104172L;
                private final long byteOffset;
                private final byte[] magicNumbers;

/* 111 */       public MagicNumberFileFilter(byte[] magicNumber) {
/* 112 */           this(magicNumber, 0L);
                }

/* 136 */       public MagicNumberFileFilter(String magicNumber) {
/* 137 */           this(magicNumber, 0L);
                }

/* 159 */       public MagicNumberFileFilter(String magicNumber, long offset) {
/* 161 */           if (magicNumber == null) {
/* 162 */               throw new IllegalArgumentException("The magic number cannot be null");
                    }
/* 164 */           if (magicNumber.length() == 0) {
/* 165 */               throw new IllegalArgumentException("The magic number must contain at least one byte");
                    }
                    if (offset < 0) {
/* 168 */               throw new IllegalArgumentException("The offset cannot be negative");
                    }
/* 171 */           this.magicNumbers = magicNumber.getBytes();
/* 172 */           this.byteOffset = offset;
                }

/* 204 */       public MagicNumberFileFilter(byte[] magicNumber, long offset) {
/* 206 */           if (magicNumber == null) {
/* 207 */               throw new IllegalArgumentException("The magic number cannot be null");
                    }
/* 209 */           if (magicNumber.length == 0) {
/* 210 */               throw new IllegalArgumentException("The magic number must contain at least one byte");
                    }
                    if (offset < 0) {
/* 213 */               throw new IllegalArgumentException("The offset cannot be negative");
                    }
/* 216 */           this.magicNumbers = new byte[magicNumber.length];
/* 217 */           System.arraycopy(magicNumber, 0, this.magicNumbers, 0, magicNumber.length);
/* 218 */           this.byteOffset = offset;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter, org.apache.commons.p005io.filefilter.IOFileFilter, java.io.FileFilter
/* 238 */       public boolean accept(File file) throws Throwable {
                    boolean zEquals = false;
/* 239 */           if (file != null && file.isFile() && file.canRead()) {
/* 240 */               RandomAccessFile randomAccessFile = null;
                        try {
/* 242 */                   byte[] fileBytes = new byte[this.magicNumbers.length];
/* 243 */                   RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                            try {
/* 244 */                       randomAccessFile2.seek(this.byteOffset);
/* 245 */                       int read = randomAccessFile2.read(fileBytes);
/* 246 */                       if (read == this.magicNumbers.length) {
/* 249 */                           zEquals = Arrays.equals(this.magicNumbers, fileBytes);
/* 253 */                           IOUtils.closeQuietly(randomAccessFile2);
                                } else {
/* 253 */                           IOUtils.closeQuietly(randomAccessFile2);
                                }
                            } catch (IOException e) {
                                randomAccessFile = randomAccessFile2;
/* 253 */                       IOUtils.closeQuietly(randomAccessFile);
                            } catch (Throwable th) {
                                th = th;
                                randomAccessFile = randomAccessFile2;
                                IOUtils.closeQuietly(randomAccessFile);
                                throw th;
                            }
                        } catch (IOException e2) {
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
/* 257 */           return zEquals;
                }

                @Override // org.apache.commons.p005io.filefilter.AbstractFileFilter
/* 267 */       public String toString() {
/* 274 */           return super.toString() + "(" + new String(this.magicNumbers) + "," + this.byteOffset + ")";
                }
            }
