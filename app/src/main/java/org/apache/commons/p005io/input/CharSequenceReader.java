            package org.apache.commons.p005io.input;

            import java.io.Reader;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 40 */    public class CharSequenceReader extends Reader implements Serializable {
                private final CharSequence charSequence;
                private int idx;
                private int mark;

/* 41 */        public CharSequenceReader(String charSequence) {
/* 43 */            this.charSequence = charSequence == null ? "" : charSequence;
                }

                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
/* 50 */        public void close() {
/* 51 */            this.idx = 0;
/* 52 */            this.mark = 0;
                }

                @Override // java.io.Reader
/* 61 */        public void mark(int readAheadLimit) {
/* 62 */            this.mark = this.idx;
                }

                @Override // java.io.Reader
/* 71 */        public boolean markSupported() {
/* 72 */            return true;
                }

                @Override // java.io.Reader
/* 82 */        public int read() {
/* 83 */            if (this.idx >= this.charSequence.length()) {
/* 84 */                return -1;
                    }
                    CharSequence charSequence = this.charSequence;
                    int i = this.idx;
                    this.idx = i + 1;
                    return charSequence.charAt(i);
                }

                @Override // java.io.Reader
/* 100 */       public int read(char[] array, int offset, int length) {
/* 101 */           if (this.idx >= this.charSequence.length()) {
                        return -1;
                    }
/* 104 */           if (array == null) {
/* 105 */               throw new NullPointerException("Character array is missing");
                    }
/* 107 */           if (length < 0 || offset < 0 || offset + length > array.length) {
/* 108 */               throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + offset + ", length=" + length);
                    }
/* 111 */           int count = 0;
                    for (int i = 0; i < length; i++) {
/* 113 */               int c = read();
/* 114 */               if (c != -1) {
/* 117 */                   array[offset + i] = (char) c;
/* 118 */                   count++;
                        } else {
/* 120 */                   return count;
                        }
                    }
/* 120 */           return count;
                }

                @Override // java.io.Reader
/* 128 */       public void reset() {
/* 129 */           this.idx = this.mark;
                }

                @Override // java.io.Reader
/* 139 */       public long skip(long n) {
                    if (n < 0) {
/* 141 */               throw new IllegalArgumentException("Number of characters to skip is less than zero: " + n);
                    }
/* 144 */           if (this.idx >= this.charSequence.length()) {
/* 145 */               return -1L;
                    }
/* 147 */           int dest = (int) Math.min(this.charSequence.length(), ((long) this.idx) + n);
/* 148 */           int count = dest - this.idx;
/* 149 */           this.idx = dest;
/* 150 */           return count;
                }

/* 160 */       public String toString() {
/* 161 */           return this.charSequence.toString();
                }
            }
