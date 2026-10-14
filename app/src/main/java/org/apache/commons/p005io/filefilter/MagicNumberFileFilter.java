            package org.apache.commons.p005io.filefilter;

            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.io.Serializable;
            import java.util.Arrays;
            import org.apache.commons.p005io.IOUtils;
            
            
   public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable {
                private static final long serialVersionUID = -547733176983104172L;
                private final long byteOffset;
                private final byte[] magicNumbers;

       public MagicNumberFileFilter(byte[] magicNumber) {
           this(magicNumber, 0L);
                }

       public MagicNumberFileFilter(String magicNumber) {
           this(magicNumber, 0L);
                }

       public MagicNumberFileFilter(String magicNumber, long offset) {
           if (magicNumber == null) {
               throw new IllegalArgumentException("The magic number cannot be null");
                    }
           if (magicNumber.length() == 0) {
               throw new IllegalArgumentException("The magic number must contain at least one byte");
                    }
                    if (offset < 0) {
               throw new IllegalArgumentException("The offset cannot be negative");
                    }
           this.magicNumbers = magicNumber.getBytes();
           this.byteOffset = offset;
                }

       public MagicNumberFileFilter(byte[] magicNumber, long offset) {
           if (magicNumber == null) {
               throw new IllegalArgumentException("The magic number cannot be null");
                    }
           if (magicNumber.length == 0) {
               throw new IllegalArgumentException("The magic number must contain at least one byte");
                    }
                    if (offset < 0) {
               throw new IllegalArgumentException("The offset cannot be negative");
                    }
           this.magicNumbers = new byte[magicNumber.length];
           System.arraycopy(magicNumber, 0, this.magicNumbers, 0, magicNumber.length);
           this.byteOffset = offset;
                }

                @Override 
       public boolean accept(File file) throws Throwable {
                    boolean zEquals = false;
           if (file != null && file.isFile() && file.canRead()) {
               RandomAccessFile randomAccessFile = null;
                        try {
                   byte[] fileBytes = new byte[this.magicNumbers.length];
                   RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                            try {
                       randomAccessFile2.seek(this.byteOffset);
                       int read = randomAccessFile2.read(fileBytes);
                       if (read == this.magicNumbers.length) {
                           zEquals = Arrays.equals(this.magicNumbers, fileBytes);
                           IOUtils.closeQuietly(randomAccessFile2);
                                } else {
                           IOUtils.closeQuietly(randomAccessFile2);
                                }
                            } catch (IOException e) {
                                randomAccessFile = randomAccessFile2;
                       IOUtils.closeQuietly(randomAccessFile);
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
           return zEquals;
                }

                @Override 
       public String toString() {
           return super.toString() + "(" + new String(this.magicNumbers) + "," + this.byteOffset + ")";
                }
            }
