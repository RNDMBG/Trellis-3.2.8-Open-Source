            package org.apache.commons.p005io.input;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Closeable;
            import java.io.File;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import java.io.UnsupportedEncodingException;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetEncoder;
            import org.apache.commons.p005io.Charsets;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public class ReversedLinesFileReader implements Closeable {
                private final int avoidNewlineSplitBufferSize;
                private final int blockSize;
                private final int byteDecrement;
                private FilePart currentFilePart;
                private final Charset encoding;
                private final byte[][] newLineSequences;
                private final RandomAccessFile randomAccessFile;
                private final long totalBlockCount;
                private final long totalByteLength;
                private boolean trailingNewlineOfFileSkipped;

/* 35 */        static /* synthetic */ int access$300(ReversedLinesFileReader x0) {
/* 36 */            return x0.blockSize;
                }

/* 35 */        static /* synthetic */ RandomAccessFile access$400(ReversedLinesFileReader x0) {
/* 36 */            return x0.randomAccessFile;
                }

/* 35 */        static /* synthetic */ Charset access$500(ReversedLinesFileReader x0) {
/* 36 */            return x0.encoding;
                }

/* 35 */        static /* synthetic */ int access$600(ReversedLinesFileReader x0) {
/* 36 */            return x0.avoidNewlineSplitBufferSize;
                }

/* 35 */        static /* synthetic */ int access$700(ReversedLinesFileReader x0) {
/* 36 */            return x0.byteDecrement;
                }

/* 35 */        static /* synthetic */ byte[][] access$800(ReversedLinesFileReader x0) {
/* 36 */            return x0.newLineSequences;
                }

/* 62 */        public ReversedLinesFileReader(File file) throws IOException {
/* 63 */            this(file, 4096, Charset.defaultCharset().toString());
                }

/* 78 */        public ReversedLinesFileReader(File file, int blockSize, Charset encoding) throws IOException {
/* 52 */            this.trailingNewlineOfFileSkipped = false;
/* 80 */            this.blockSize = blockSize;
/* 81 */            this.encoding = encoding;
/* 83 */            this.randomAccessFile = new RandomAccessFile(file, "r");
/* 84 */            this.totalByteLength = this.randomAccessFile.length();
/* 85 */            int lastBlockLength = (int) (this.totalByteLength % ((long) blockSize));
/* 86 */            if (lastBlockLength > 0) {
/* 87 */                this.totalBlockCount = (this.totalByteLength / ((long) blockSize)) + 1;
                    } else {
/* 89 */                this.totalBlockCount = this.totalByteLength / ((long) blockSize);
                        if (this.totalByteLength > 0) {
/* 91 */                    lastBlockLength = blockSize;
                        }
                    }
/* 94 */            this.currentFilePart = new FilePart(this, this.totalBlockCount, lastBlockLength, null, null);
/* 97 */            Charset charset = Charsets.toCharset(encoding);
/* 98 */            CharsetEncoder charsetEncoder = charset.newEncoder();
/* 99 */            float maxBytesPerChar = charsetEncoder.maxBytesPerChar();
/* 103 */           if (maxBytesPerChar == 1.0f || charset == Charset.forName(IoUtils.UTF_8) || charset == Charset.forName("Shift_JIS")) {
/* 110 */               this.byteDecrement = 1;
                    } else if (charset == Charset.forName("UTF-16BE") || charset == Charset.forName("UTF-16LE")) {
/* 114 */               this.byteDecrement = 2;
                    } else {
/* 115 */               if (charset == Charset.forName("UTF-16")) {
/* 116 */                   throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
                        }
/* 119 */               throw new UnsupportedEncodingException("Encoding " + encoding + " is not supported yet (feel free to submit a patch)");
                    }
/* 123 */           this.newLineSequences = new byte[][]{"\r\n".getBytes(encoding), IOUtils.LINE_SEPARATOR_UNIX.getBytes(encoding), "\r".getBytes(encoding)};
/* 125 */           this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
                }

/* 143 */       public ReversedLinesFileReader(File file, int blockSize, String encoding) throws IOException {
/* 144 */           this(file, blockSize, Charsets.toCharset(encoding));
                }

/* 154 */       public String readLine() throws IOException {
/* 155 */           String line = FilePart.access$100(this.currentFilePart);
/* 156 */           while (line == null) {
/* 157 */               this.currentFilePart = FilePart.access$200(this.currentFilePart);
/* 158 */               if (this.currentFilePart == null) {
                            break;
                        }
/* 159 */               line = FilePart.access$100(this.currentFilePart);
                    }
/* 167 */           if ("".equals(line) && !this.trailingNewlineOfFileSkipped) {
/* 168 */               this.trailingNewlineOfFileSkipped = true;
/* 169 */               String line2 = readLine();
/* 172 */               return line2;
                    }
/* 172 */           return line;
                }

                @Override // java.io.Closeable, java.lang.AutoCloseable
/* 180 */       public void close() throws IOException {
/* 181 */           this.randomAccessFile.close();
                }

/* 182 */       private class FilePart {
                    private int currentLastBytePos;
                    private final byte[] data;
                    private byte[] leftOver;

                    /* JADX INFO: renamed from: no */
                    private final long f2539no;

/* 183 */           /* synthetic */ FilePart(ReversedLinesFileReader x0, long x1, int x2, byte[] x3, Object x4) throws IOException {
/* 184 */               this(x1, x2, x3);
                    }

/* 183 */           static /* synthetic */ String access$100(FilePart x0) throws IOException {
/* 184 */               return x0.readLine();
                    }

/* 183 */           static /* synthetic */ FilePart access$200(FilePart x0) throws IOException {
/* 184 */               return x0.rollOver();
                    }

/* 199 */           private FilePart(long no, int length, byte[] leftOverOfLastFilePart) throws IOException {
/* 201 */               this.f2539no = no;
/* 202 */               int dataLength = length + (leftOverOfLastFilePart != null ? leftOverOfLastFilePart.length : 0);
/* 203 */               this.data = new byte[dataLength];
/* 204 */               long off = (no - 1) * ((long) ReversedLinesFileReader.access$300(ReversedLinesFileReader.this));
                        if (no > 0) {
/* 208 */                   ReversedLinesFileReader.access$400(ReversedLinesFileReader.this).seek(off);
/* 209 */                   int countRead = ReversedLinesFileReader.access$400(ReversedLinesFileReader.this).read(this.data, 0, length);
/* 210 */                   if (countRead != length) {
/* 211 */                       throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                            }
                        }
/* 215 */               if (leftOverOfLastFilePart != null) {
/* 216 */                   System.arraycopy(leftOverOfLastFilePart, 0, this.data, length, leftOverOfLastFilePart.length);
                        }
/* 218 */               this.currentLastBytePos = this.data.length - 1;
/* 219 */               this.leftOver = null;
                    }

/* 229 */           private FilePart rollOver() throws IOException {
/* 230 */               if (this.currentLastBytePos > -1) {
/* 231 */                   throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
                        }
                        if (this.f2539no > 1) {
/* 236 */                   return ReversedLinesFileReader.this.new FilePart(this.f2539no - 1, ReversedLinesFileReader.access$300(ReversedLinesFileReader.this), this.leftOver);
                        }
/* 239 */               if (this.leftOver != null) {
/* 240 */                   throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.access$500(ReversedLinesFileReader.this)));
                        }
/* 243 */               return null;
                    }

/* 254 */           private String readLine() throws IOException {
/* 255 */               String line = null;
                        boolean isLastFilePart = this.f2539no == 1;
/* 260 */               int i = this.currentLastBytePos;
/* 261 */               while (i > -1) {
/* 263 */                   if (!isLastFilePart && i < ReversedLinesFileReader.access$600(ReversedLinesFileReader.this)) {
/* 266 */                       createLeftOver();
                                break;
                            }
/* 271 */                   int newLineMatchByteCount = getNewLineMatchByteCount(this.data, i);
                            if (newLineMatchByteCount > 0) {
/* 272 */                       int lineStart = i + 1;
/* 273 */                       int lineLengthBytes = (this.currentLastBytePos - lineStart) + 1;
/* 275 */                       if (lineLengthBytes < 0) {
/* 276 */                           throw new IllegalStateException("Unexpected negative line length=" + lineLengthBytes);
                                }
/* 278 */                       byte[] lineData = new byte[lineLengthBytes];
/* 279 */                       System.arraycopy(this.data, lineStart, lineData, 0, lineLengthBytes);
/* 281 */                       line = new String(lineData, ReversedLinesFileReader.access$500(ReversedLinesFileReader.this));
/* 283 */                       this.currentLastBytePos = i - newLineMatchByteCount;
                                break;
                            }
/* 288 */                   i -= ReversedLinesFileReader.access$700(ReversedLinesFileReader.this);
/* 291 */                   if (i < 0) {
/* 292 */                       createLeftOver();
                                break;
                            }
                        }
/* 298 */               if (isLastFilePart && this.leftOver != null) {
/* 300 */                   String line2 = new String(this.leftOver, ReversedLinesFileReader.access$500(ReversedLinesFileReader.this));
/* 301 */                   this.leftOver = null;
/* 304 */                   return line2;
                        }
/* 304 */               return line;
                    }

/* 310 */           private void createLeftOver() {
/* 311 */               int lineLengthBytes = this.currentLastBytePos + 1;
/* 312 */               if (lineLengthBytes > 0) {
/* 314 */                   this.leftOver = new byte[lineLengthBytes];
/* 315 */                   System.arraycopy(this.data, 0, this.leftOver, 0, lineLengthBytes);
                        } else {
/* 317 */                   this.leftOver = null;
                        }
/* 319 */               this.currentLastBytePos = -1;
                    }

/* 329 */           private int getNewLineMatchByteCount(byte[] data, int i) {
/* 330 */               byte[][] arr$ = ReversedLinesFileReader.access$800(ReversedLinesFileReader.this);
                        for (byte[] newLineSequence : arr$) {
/* 331 */                   boolean match = true;
                            for (int j = newLineSequence.length - 1; j >= 0; j--) {
/* 333 */                       int k = (i + j) - (newLineSequence.length - 1);
/* 334 */                       match &= k >= 0 && data[k] == newLineSequence[j];
                            }
/* 336 */                   if (match) {
/* 337 */                       return newLineSequence.length;
                            }
                        }
                        return 0;
                    }
                }
            }
