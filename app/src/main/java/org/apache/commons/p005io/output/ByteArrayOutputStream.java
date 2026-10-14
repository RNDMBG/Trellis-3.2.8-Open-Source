            package org.apache.commons.p005io.output;

            import java.io.ByteArrayInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.io.SequenceInputStream;
            import java.io.UnsupportedEncodingException;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            import org.apache.commons.p005io.input.ClosedInputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 73 */    public class ByteArrayOutputStream extends OutputStream {
                private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
                private final List<byte[]> buffers;
                private int count;
                private byte[] currentBuffer;
                private int currentBufferIndex;
                private int filledBufferSum;

/* 74 */        public ByteArrayOutputStream() {
/* 75 */            this(1024);
                }

/* 84 */        public ByteArrayOutputStream(int size) {
/* 60 */            this.buffers = new ArrayList();
/* 86 */            if (size < 0) {
/* 87 */                throw new IllegalArgumentException("Negative initial size: " + size);
                    }
                    synchronized (this) {
                        try {
/* 91 */                    needNewBuffer(size);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 101 */       private void needNewBuffer(int newcount) {
                    int newBufferSize;
/* 102 */           if (this.currentBufferIndex < this.buffers.size() - 1) {
/* 104 */               this.filledBufferSum += this.currentBuffer.length;
/* 106 */               this.currentBufferIndex++;
/* 107 */               this.currentBuffer = this.buffers.get(this.currentBufferIndex);
/* 125 */               return;
                    }
/* 111 */           if (this.currentBuffer == null) {
/* 112 */               newBufferSize = newcount;
/* 113 */               this.filledBufferSum = 0;
                    } else {
/* 115 */               newBufferSize = Math.max(this.currentBuffer.length << 1, newcount - this.filledBufferSum);
/* 118 */               this.filledBufferSum += this.currentBuffer.length;
                    }
/* 121 */           this.currentBufferIndex++;
/* 122 */           this.currentBuffer = new byte[newBufferSize];
/* 123 */           this.buffers.add(this.currentBuffer);
                }

                @Override // java.io.OutputStream
/* 134 */       public void write(byte[] b, int off, int len) {
/* 135 */           if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
/* 140 */               throw new IndexOutOfBoundsException();
                    }
/* 141 */           if (len != 0) {
                        synchronized (this) {
                            try {
/* 145 */                       int newcount = this.count + len;
/* 146 */                       int remaining = len;
/* 147 */                       int inBufferPos = this.count - this.filledBufferSum;
/* 148 */                       while (remaining > 0) {
/* 149 */                           int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
/* 150 */                           System.arraycopy(b, (off + len) - remaining, this.currentBuffer, inBufferPos, part);
/* 151 */                           remaining -= part;
/* 152 */                           if (remaining > 0) {
/* 153 */                               needNewBuffer(newcount);
/* 154 */                               inBufferPos = 0;
                                    }
                                }
/* 157 */                       this.count = newcount;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }

                @Override // java.io.OutputStream
/* 166 */       public synchronized void write(int b) {
                    try {
                        int inBufferPos = this.count - this.filledBufferSum;
/* 168 */               if (inBufferPos == this.currentBuffer.length) {
/* 169 */                   needNewBuffer(this.count + 1);
/* 170 */                   inBufferPos = 0;
                        }
/* 172 */               this.currentBuffer[inBufferPos] = (byte) b;
/* 173 */               this.count++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 187 */       public synchronized int write(InputStream in) throws IOException {
                    int readCount;
                    readCount = 0;
                    try {
/* 189 */               int inBufferPos = this.count - this.filledBufferSum;
/* 190 */               int n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
/* 191 */               while (n != -1) {
/* 192 */                   readCount += n;
/* 193 */                   inBufferPos += n;
/* 194 */                   this.count += n;
/* 195 */                   if (inBufferPos == this.currentBuffer.length) {
/* 196 */                       needNewBuffer(this.currentBuffer.length);
/* 197 */                       inBufferPos = 0;
                            }
/* 199 */                   n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return readCount;
                }

/* 208 */       public synchronized int size() {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return this.count;
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 222 */       public void close() throws IOException {
                }

/* 228 */       public synchronized void reset() {
                    try {
                        this.count = 0;
/* 230 */               this.filledBufferSum = 0;
/* 231 */               this.currentBufferIndex = 0;
/* 232 */               this.currentBuffer = this.buffers.get(this.currentBufferIndex);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 243 */       public synchronized void writeTo(OutputStream out) throws IOException {
                    try {
                        int remaining = this.count;
                        for (byte[] buf : this.buffers) {
/* 246 */                   int c = Math.min(buf.length, remaining);
/* 247 */                   out.write(buf, 0, c);
/* 248 */                   remaining -= c;
/* 249 */                   if (remaining == 0) {
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 277 */       public static InputStream toBufferedInputStream(InputStream input) throws IOException {
/* 278 */           ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 279 */           output.write(input);
/* 280 */           return output.toBufferedInputStream();
                }

/* 293 */       private InputStream toBufferedInputStream() {
/* 294 */           int remaining = this.count;
/* 295 */           if (remaining == 0) {
/* 296 */               return new ClosedInputStream();
                    }
/* 298 */           List<ByteArrayInputStream> list = new ArrayList<>(this.buffers.size());
                    for (byte[] buf : this.buffers) {
/* 300 */               int c = Math.min(buf.length, remaining);
/* 301 */               list.add(new ByteArrayInputStream(buf, 0, c));
/* 302 */               remaining -= c;
/* 303 */               if (remaining == 0) {
                            break;
                        }
                    }
/* 307 */           return new SequenceInputStream(Collections.enumeration(list));
                }

/* 317 */       public synchronized byte[] toByteArray() {
                    byte[] newbuf;
                    try {
                        int remaining = this.count;
/* 319 */               if (remaining == 0) {
/* 320 */                   newbuf = EMPTY_BYTE_ARRAY;
                        } else {
/* 322 */                   newbuf = new byte[remaining];
/* 323 */                   int pos = 0;
                            for (byte[] buf : this.buffers) {
/* 325 */                       int c = Math.min(buf.length, remaining);
/* 326 */                       System.arraycopy(buf, 0, newbuf, pos, c);
/* 327 */                       pos += c;
/* 328 */                       remaining -= c;
/* 329 */                       if (remaining == 0) {
                                    break;
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return newbuf;
                }

/* 342 */       public String toString() {
/* 343 */           return new String(toByteArray());
                }

/* 355 */       public String toString(String enc) throws UnsupportedEncodingException {
/* 356 */           return new String(toByteArray(), enc);
                }
            }
