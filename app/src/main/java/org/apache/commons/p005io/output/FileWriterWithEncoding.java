            package org.apache.commons.p005io.output;

            import java.io.File;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.Writer;
            import java.nio.charset.Charset;
            import java.nio.charset.CharsetEncoder;
            import org.apache.commons.p005io.FileUtils;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class FileWriterWithEncoding extends Writer {
                private final Writer out;

/* 65 */        public FileWriterWithEncoding(String filename, String encoding) throws IOException {
/* 66 */            this(new File(filename), encoding, false);
                }

/* 78 */        public FileWriterWithEncoding(String filename, String encoding, boolean append) throws IOException {
/* 79 */            this(new File(filename), encoding, append);
                }

/* 90 */        public FileWriterWithEncoding(String filename, Charset encoding) throws IOException {
/* 91 */            this(new File(filename), encoding, false);
                }

/* 103 */       public FileWriterWithEncoding(String filename, Charset encoding, boolean append) throws IOException {
/* 104 */           this(new File(filename), encoding, append);
                }

/* 115 */       public FileWriterWithEncoding(String filename, CharsetEncoder encoding) throws IOException {
/* 116 */           this(new File(filename), encoding, false);
                }

/* 128 */       public FileWriterWithEncoding(String filename, CharsetEncoder encoding, boolean append) throws IOException {
/* 129 */           this(new File(filename), encoding, append);
                }

/* 140 */       public FileWriterWithEncoding(File file, String encoding) throws IOException {
/* 141 */           this(file, encoding, false);
                }

/* 153 */       public FileWriterWithEncoding(File file, String encoding, boolean append) throws IOException {
/* 155 */           this.out = initWriter(file, encoding, append);
                }

/* 166 */       public FileWriterWithEncoding(File file, Charset encoding) throws IOException {
/* 167 */           this(file, encoding, false);
                }

/* 179 */       public FileWriterWithEncoding(File file, Charset encoding, boolean append) throws IOException {
/* 181 */           this.out = initWriter(file, encoding, append);
                }

/* 192 */       public FileWriterWithEncoding(File file, CharsetEncoder encoding) throws IOException {
/* 193 */           this(file, encoding, false);
                }

/* 205 */       public FileWriterWithEncoding(File file, CharsetEncoder encoding, boolean append) throws IOException {
/* 207 */           this.out = initWriter(file, encoding, append);
                }

/* 222 */       private static Writer initWriter(File file, Object encoding, boolean append) throws IOException {
/* 223 */           if (file == null) {
/* 224 */               throw new NullPointerException("File is missing");
                    }
/* 226 */           if (encoding == null) {
/* 227 */               throw new NullPointerException("Encoding is missing");
                    }
/* 229 */           boolean fileExistedAlready = file.exists();
/* 230 */           OutputStream stream = null;
                    try {
/* 233 */               OutputStream stream2 = new FileOutputStream(file, append);
                        try {
/* 234 */                   if (encoding instanceof Charset) {
/* 235 */                       Writer writer = new OutputStreamWriter(stream2, (Charset) encoding);
                                return writer;
                            }
/* 236 */                   if (encoding instanceof CharsetEncoder) {
/* 237 */                       Writer writer2 = new OutputStreamWriter(stream2, (CharsetEncoder) encoding);
                                return writer2;
                            }
/* 239 */                   Writer writer3 = new OutputStreamWriter(stream2, (String) encoding);
                            return writer3;
                        } catch (IOException e) {
/* 241 */                   ex = e;
                            stream = stream2;
/* 242 */                   IOUtils.closeQuietly((Writer) null);
/* 243 */                   IOUtils.closeQuietly(stream);
/* 244 */                   if (!fileExistedAlready) {
/* 245 */                       FileUtils.deleteQuietly(file);
                            }
/* 247 */                   throw ex;
                        } catch (RuntimeException e2) {
/* 248 */                   ex = e2;
                            stream = stream2;
/* 249 */                   IOUtils.closeQuietly((Writer) null);
/* 250 */                   IOUtils.closeQuietly(stream);
/* 251 */                   if (!fileExistedAlready) {
/* 252 */                       FileUtils.deleteQuietly(file);
                            }
/* 254 */                   throw ex;
                        }
                    } catch (IOException e3) {
/* 241 */               ex = e3;
                    } catch (RuntimeException e4) {
/* 248 */               ex = e4;
                    }
                }

                @Override // java.io.Writer
/* 266 */       public void write(int idx) throws IOException {
/* 267 */           this.out.write(idx);
                }

                @Override // java.io.Writer
/* 276 */       public void write(char[] chr) throws IOException {
/* 277 */           this.out.write(chr);
                }

                @Override // java.io.Writer
/* 288 */       public void write(char[] chr, int st, int end) throws IOException {
/* 289 */           this.out.write(chr, st, end);
                }

                @Override // java.io.Writer
/* 298 */       public void write(String str) throws IOException {
/* 299 */           this.out.write(str);
                }

                @Override // java.io.Writer
/* 310 */       public void write(String str, int st, int end) throws IOException {
/* 311 */           this.out.write(str, st, end);
                }

                @Override // java.io.Writer, java.io.Flushable
/* 319 */       public void flush() throws IOException {
/* 320 */           this.out.flush();
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 328 */       public void close() throws IOException {
/* 329 */           this.out.close();
                }
            }
