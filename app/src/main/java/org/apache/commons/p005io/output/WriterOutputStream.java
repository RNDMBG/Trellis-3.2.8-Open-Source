            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.Writer;
            import java.nio.ByteBuffer;
            import java.nio.CharBuffer;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetDecoder;
            import java.nio.charset.CoderResult;
            import java.nio.charset.CodingErrorAction;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 102 */   public class WriterOutputStream extends OutputStream {
                private static final int DEFAULT_BUFFER_SIZE = 1024;
                private final CharsetDecoder decoder;
                private final ByteBuffer decoderIn;
                private final CharBuffer decoderOut;
                private final boolean writeImmediately;
                private final Writer writer;

/* 103 */       public WriterOutputStream(Writer writer, CharsetDecoder decoder) {
/* 104 */           this(writer, decoder, 1024, false);
                }

/* 119 */       public WriterOutputStream(Writer writer, CharsetDecoder decoder, int bufferSize, boolean writeImmediately) {
/* 85 */            this.decoderIn = ByteBuffer.allocate(128);
/* 121 */           this.writer = writer;
/* 122 */           this.decoder = decoder;
/* 123 */           this.writeImmediately = writeImmediately;
/* 124 */           this.decoderOut = CharBuffer.allocate(bufferSize);
                }

/* 139 */       public WriterOutputStream(Writer writer, Charset charset, int bufferSize, boolean writeImmediately) {
/* 140 */           this(writer, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), bufferSize, writeImmediately);
                }

/* 157 */       public WriterOutputStream(Writer writer, Charset charset) {
/* 158 */           this(writer, charset, 1024, false);
                }

/* 173 */       public WriterOutputStream(Writer writer, String charsetName, int bufferSize, boolean writeImmediately) {
/* 174 */           this(writer, Charset.forName(charsetName), bufferSize, writeImmediately);
                }

/* 185 */       public WriterOutputStream(Writer writer, String charsetName) {
/* 186 */           this(writer, charsetName, 1024, false);
                }

/* 196 */       public WriterOutputStream(Writer writer) {
/* 197 */           this(writer, Charset.defaultCharset(), 1024, false);
                }

                @Override // java.io.OutputStream
/* 209 */       public void write(byte[] b, int off, int len) throws IOException {
/* 210 */           while (len > 0) {
/* 211 */               int c = Math.min(len, this.decoderIn.remaining());
/* 212 */               this.decoderIn.put(b, off, c);
/* 213 */               processInput(false);
/* 214 */               len -= c;
/* 215 */               off += c;
                    }
/* 217 */           if (this.writeImmediately) {
/* 218 */               flushOutput();
                    }
                }

                @Override // java.io.OutputStream
/* 229 */       public void write(byte[] b) throws IOException {
/* 230 */           write(b, 0, b.length);
                }

                @Override // java.io.OutputStream
/* 240 */       public void write(int b) throws IOException {
/* 241 */           write(new byte[]{(byte) b}, 0, 1);
                }

                @Override // java.io.OutputStream, java.io.Flushable
/* 251 */       public void flush() throws IOException {
/* 252 */           flushOutput();
/* 253 */           this.writer.flush();
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
/* 263 */       public void close() throws IOException {
/* 264 */           processInput(true);
/* 265 */           flushOutput();
/* 266 */           this.writer.close();
                }

/* 276 */       private void processInput(boolean endOfInput) throws IOException {
                    CoderResult coderResult;
/* 277 */           this.decoderIn.flip();
                    while (true) {
/* 280 */               coderResult = this.decoder.decode(this.decoderIn, this.decoderOut, endOfInput);
/* 281 */               if (!coderResult.isOverflow()) {
                            break;
                        } else {
/* 282 */                   flushOutput();
                        }
                    }
/* 283 */           if (!coderResult.isUnderflow()) {
/* 288 */               throw new IOException("Unexpected coder result");
                    }
/* 292 */           this.decoderIn.compact();
                }

/* 300 */       private void flushOutput() throws IOException {
/* 301 */           if (this.decoderOut.position() > 0) {
/* 302 */               this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
/* 303 */               this.decoderOut.rewind();
                    }
                }
            }
