            package org.apache.commons.p005io;

            import java.io.BufferedReader;
            import java.io.IOException;
            import java.io.Reader;
            import java.util.Iterator;
            import java.util.NoSuchElementException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public class LineIterator implements Iterator<String> {
                private final BufferedReader bufferedReader;
                private String cachedLine;
                private boolean finished = false;


/* 66 */        public LineIterator(Reader reader) throws IllegalArgumentException {
/* 68 */            if (reader == null) {
/* 69 */                throw new IllegalArgumentException("Reader must not be null");
                    }
/* 71 */            if (reader instanceof BufferedReader) {
/* 72 */                this.bufferedReader = (BufferedReader) reader;
                    } else {
/* 74 */                this.bufferedReader = new BufferedReader(reader);
                    }
                }

                @Override // java.util.Iterator
/* 87 */        public boolean hasNext() {
                    String line;
                    boolean z = true;
/* 88 */            if (this.cachedLine != null) {
                        return true;
                    }
/* 90 */            if (this.finished) {
                        return false;
                    }
/* 99 */            do {
                        try {
/* 95 */                    line = this.bufferedReader.readLine();
/* 96 */                    if (line == null) {
/* 97 */                        this.finished = true;
                                z = false;
                            }
/* 101 */                   return z;
                        } catch (IOException ioe) {
/* 105 */                   close();
/* 106 */                   throw new IllegalStateException(ioe);
                        }
/* 99 */            } while (!isValidLine(line));
/* 100 */           this.cachedLine = line;
/* 101 */           return z;
                }

/* 117 */       protected boolean isValidLine(String line) {
/* 118 */           return true;
                }

                @Override // java.util.Iterator
                /* JADX INFO: renamed from: next, reason: avoid collision after fix types in other method */
/* 127 */       public String next2() {
/* 128 */           return nextLine();
                }

/* 137 */       public String nextLine() {
/* 138 */           if (!hasNext()) {
/* 139 */               throw new NoSuchElementException("No more lines");
                    }
/* 141 */           String currentLine = this.cachedLine;
/* 142 */           this.cachedLine = null;
/* 143 */           return currentLine;
                }

/* 153 */       public void close() {
/* 154 */           this.finished = true;
/* 155 */           IOUtils.closeQuietly((Reader) this.bufferedReader);
/* 156 */           this.cachedLine = null;
                }

                @Override // java.util.Iterator
/* 164 */       public void remove() {
/* 165 */           throw new UnsupportedOperationException("Remove unsupported on LineIterator");
                }

/* 174 */       public static void closeQuietly(LineIterator iterator) {
/* 175 */           if (iterator != null) {
/* 176 */               iterator.close();
                    }
                }
            }
