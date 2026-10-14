            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class NullWriter extends Writer {
                public static final NullWriter NULL_WRITER = new NullWriter();




                @Override // java.io.Writer, java.lang.Appendable
/* 50 */        public Writer append(char c) {
/* 51 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 64 */        public Writer append(CharSequence csq, int start, int end) {
/* 65 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 76 */        public Writer append(CharSequence csq) {
/* 77 */            return this;
                }

                @Override // java.io.Writer
/* 86 */        public void write(int idx) {
                }

                @Override // java.io.Writer
/* 95 */        public void write(char[] chr) {
                }

                @Override // java.io.Writer
/* 106 */       public void write(char[] chr, int st, int end) {
                }

                @Override // java.io.Writer
/* 115 */       public void write(String str) {
                }

                @Override // java.io.Writer
/* 126 */       public void write(String str, int st, int end) {
                }

                @Override // java.io.Writer, java.io.Flushable
/* 132 */       public void flush() {
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 138 */       public void close() {
                }
            }
