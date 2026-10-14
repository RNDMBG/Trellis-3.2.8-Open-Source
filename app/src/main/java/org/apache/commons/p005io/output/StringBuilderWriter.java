            package org.apache.commons.p005io.output;

            import java.io.IOException;
            import java.io.Serializable;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 32 */    public class StringBuilderWriter extends Writer implements Serializable {
                private final StringBuilder builder;




/* 40 */        public StringBuilderWriter() {
/* 42 */            this.builder = new StringBuilder();
                }

/* 49 */        public StringBuilderWriter(int capacity) {
/* 51 */            this.builder = new StringBuilder(capacity);
                }

/* 58 */        public StringBuilderWriter(StringBuilder builder) {
/* 60 */            this.builder = builder == null ? new StringBuilder() : builder;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 70 */        public Writer append(char value) {
/* 71 */            this.builder.append(value);
/* 72 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 82 */        public Writer append(CharSequence value) {
/* 83 */            this.builder.append(value);
/* 84 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 96 */        public Writer append(CharSequence value, int start, int end) {
/* 97 */            this.builder.append(value, start, end);
/* 98 */            return this;
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 105 */       public void close() {
                }

                @Override // java.io.Writer, java.io.Flushable
/* 112 */       public void flush() {
                }

                @Override // java.io.Writer
/* 122 */       public void write(String value) {
/* 123 */           if (value != null) {
/* 124 */               this.builder.append(value);
                    }
                }

                @Override // java.io.Writer
/* 136 */       public void write(char[] value, int offset, int length) {
/* 137 */           if (value != null) {
/* 138 */               this.builder.append(value, offset, length);
                    }
                }

/* 147 */       public StringBuilder getBuilder() {
/* 148 */           return this.builder;
                }

/* 157 */       public String toString() {
/* 158 */           return this.builder.toString();
                }
            }
