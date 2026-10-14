            package org.apache.commons.p005io.input;

            import com.flurry.android.Constants;
            import java.io.IOException;
            import java.io.InputStream;
            import java.nio.ByteBuffer;
            import java.nio.CharBuffer;
            import java.nio.charset.CharacterCodingException;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetEncoder;
            import java.nio.charset.CoderResult;
            import java.nio.charset.CodingErrorAction;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class CharSequenceInputStream extends InputStream {
                private final ByteBuffer bbuf;
                private final CharBuffer cbuf;
                private final CharsetEncoder encoder;
                private int mark;

/* 53 */        public CharSequenceInputStream(CharSequence s, Charset charset, int bufferSize) {
/* 55 */            this.encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
/* 58 */            this.bbuf = ByteBuffer.allocate(bufferSize);
/* 59 */            this.bbuf.flip();
/* 60 */            this.cbuf = CharBuffer.wrap(s);
/* 61 */            this.mark = -1;
                }

/* 71 */        public CharSequenceInputStream(CharSequence s, String charset, int bufferSize) {
/* 72 */            this(s, Charset.forName(charset), bufferSize);
                }

/* 82 */        public CharSequenceInputStream(CharSequence s, Charset charset) {
/* 83 */            this(s, charset, 2048);
                }

/* 93 */        public CharSequenceInputStream(CharSequence s, String charset) {
/* 94 */            this(s, charset, 2048);
                }

/* 103 */       private void fillBuffer() throws CharacterCodingException {
/* 104 */           this.bbuf.compact();
/* 105 */           CoderResult result = this.encoder.encode(this.cbuf, this.bbuf, true);
/* 106 */           if (result.isError()) {
/* 107 */               result.throwException();
                    }
/* 109 */           this.bbuf.flip();
                }

                @Override // java.io.InputStream
/* 113 */       public int read(byte[] b, int off, int len) throws IOException {
/* 114 */           if (b == null) {
/* 115 */               throw new NullPointerException("Byte array is null");
                    }
/* 117 */           if (len < 0 || off + len > b.length) {
/* 118 */               throw new IndexOutOfBoundsException("Array Size=" + b.length + ", offset=" + off + ", length=" + len);
                    }
/* 121 */           if (len == 0) {
/* 122 */               return 0;
                    }
/* 124 */           if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                        return -1;
                    }
/* 127 */           int bytesRead = 0;
/* 128 */           while (len > 0) {
/* 129 */               if (this.bbuf.hasRemaining()) {
/* 130 */                   int chunk = Math.min(this.bbuf.remaining(), len);
/* 131 */                   this.bbuf.get(b, off, chunk);
/* 132 */                   off += chunk;
/* 133 */                   len -= chunk;
/* 134 */                   bytesRead += chunk;
                        } else {
/* 136 */                   fillBuffer();
/* 137 */                   if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                                break;
                            }
                        }
                    }
/* 142 */           if (bytesRead == 0 && !this.cbuf.hasRemaining()) {
                        bytesRead = -1;
                    }
                    return bytesRead;
                }

                @Override // java.io.InputStream
/* 147 */       public int read() throws IOException {
/* 148 */           while (!this.bbuf.hasRemaining()) {
/* 151 */               fillBuffer();
/* 152 */               if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
/* 153 */                   return -1;
                        }
                    }
/* 149 */           return this.bbuf.get() & Constants.UNKNOWN;
                }

                @Override // java.io.InputStream
/* 160 */       public int read(byte[] b) throws IOException {
/* 161 */           return read(b, 0, b.length);
                }

                @Override // java.io.InputStream
/* 165 */       public long skip(long n) throws IOException {
/* 166 */           int skipped = 0;
                    while (n > 0 && this.cbuf.hasRemaining()) {
/* 168 */               this.cbuf.get();
/* 169 */               n--;
/* 170 */               skipped++;
                    }
/* 172 */           return skipped;
                }

                @Override // java.io.InputStream
/* 176 */       public int available() throws IOException {
/* 177 */           return this.cbuf.remaining();
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
/* 181 */       public void close() throws IOException {
                }

                @Override // java.io.InputStream
/* 189 */       public synchronized void mark(int readlimit) {
                    try {
                        this.mark = this.cbuf.position();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 194 */       public synchronized void reset() throws IOException {
                    try {
                        if (this.mark != -1) {
/* 196 */                   this.cbuf.position(this.mark);
/* 197 */                   this.mark = -1;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // java.io.InputStream
/* 202 */       public boolean markSupported() {
/* 203 */           return true;
                }
            }
