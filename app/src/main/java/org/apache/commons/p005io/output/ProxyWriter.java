            package org.apache.commons.p005io.output;

            import java.io.FilterWriter;
            import java.io.IOException;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ProxyWriter extends FilterWriter {



/* 40 */        public ProxyWriter(Writer proxy) {
/* 41 */            super(proxy);
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 54 */        public Writer append(char c) throws IOException {
                    try {
/* 55 */                beforeWrite(1);
/* 56 */                this.out.append(c);
/* 57 */                afterWrite(1);
                    } catch (IOException e) {
/* 59 */                handleIOException(e);
                    }
/* 61 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 75 */        public Writer append(CharSequence csq, int start, int end) throws IOException {
                    try {
/* 76 */                beforeWrite(end - start);
/* 77 */                this.out.append(csq, start, end);
/* 78 */                afterWrite(end - start);
                    } catch (IOException e) {
/* 80 */                handleIOException(e);
                    }
/* 82 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 94 */        public Writer append(CharSequence csq) throws IOException {
/* 95 */            int len = 0;
/* 96 */            if (csq != null) {
                        try {
/* 97 */                    len = csq.length();
                        } catch (IOException e) {
/* 104 */                   handleIOException(e);
                        }
                    }
/* 100 */           beforeWrite(len);
/* 101 */           this.out.append(csq);
/* 102 */           afterWrite(len);
/* 106 */           return this;
                }

                @Override // java.io.FilterWriter, java.io.Writer
/* 116 */       public void write(int idx) throws IOException {
                    try {
/* 117 */               beforeWrite(1);
/* 118 */               this.out.write(idx);
/* 119 */               afterWrite(1);
                    } catch (IOException e) {
/* 121 */               handleIOException(e);
                    }
                }

                @Override // java.io.Writer
/* 132 */       public void write(char[] chr) throws IOException {
/* 133 */           int len = 0;
/* 134 */           if (chr != null) {
                        try {
/* 135 */                   len = chr.length;
                        } catch (IOException e) {
/* 142 */                   handleIOException(e);
/* 144 */                   return;
                        }
                    }
/* 138 */           beforeWrite(len);
/* 139 */           this.out.write(chr);
/* 140 */           afterWrite(len);
                }

                @Override // java.io.FilterWriter, java.io.Writer
/* 155 */       public void write(char[] chr, int st, int len) throws IOException {
                    try {
/* 156 */               beforeWrite(len);
/* 157 */               this.out.write(chr, st, len);
/* 158 */               afterWrite(len);
                    } catch (IOException e) {
/* 160 */               handleIOException(e);
                    }
                }

                @Override // java.io.Writer
/* 171 */       public void write(String str) throws IOException {
/* 172 */           int len = 0;
/* 173 */           if (str != null) {
                        try {
/* 174 */                   len = str.length();
                        } catch (IOException e) {
/* 181 */                   handleIOException(e);
/* 183 */                   return;
                        }
                    }
/* 177 */           beforeWrite(len);
/* 178 */           this.out.write(str);
/* 179 */           afterWrite(len);
                }

                @Override // java.io.FilterWriter, java.io.Writer
/* 194 */       public void write(String str, int st, int len) throws IOException {
                    try {
/* 195 */               beforeWrite(len);
/* 196 */               this.out.write(str, st, len);
/* 197 */               afterWrite(len);
                    } catch (IOException e) {
/* 199 */               handleIOException(e);
                    }
                }

                @Override // java.io.FilterWriter, java.io.Writer, java.io.Flushable
/* 209 */       public void flush() throws IOException {
                    try {
/* 210 */               this.out.flush();
                    } catch (IOException e) {
/* 212 */               handleIOException(e);
                    }
                }

                @Override // java.io.FilterWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 222 */       public void close() throws IOException {
                    try {
/* 223 */               this.out.close();
                    } catch (IOException e) {
/* 225 */               handleIOException(e);
                    }
                }

/* 242 */       protected void beforeWrite(int n) throws IOException {
                }

/* 259 */       protected void afterWrite(int n) throws IOException {
                }

/* 271 */       protected void handleIOException(IOException e) throws IOException {
/* 272 */           throw e;
                }
            }
