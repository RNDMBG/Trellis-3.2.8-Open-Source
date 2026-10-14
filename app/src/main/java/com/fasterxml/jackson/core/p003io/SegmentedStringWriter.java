            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.TextBuffer;
            import java.io.IOException;
            import java.io.Writer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public final class SegmentedStringWriter extends Writer {
                protected final TextBuffer _buffer;




/* 22 */        public SegmentedStringWriter(BufferRecycler bufferRecycler) {
/* 24 */            this._buffer = new TextBuffer(bufferRecycler);
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 35 */        public Writer append(char c) {
/* 36 */            write(c);
/* 37 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 42 */        public Writer append(CharSequence charSequence) {
/* 43 */            String string = charSequence.toString();
/* 44 */            this._buffer.append(string, 0, string.length());
/* 45 */            return this;
                }

                @Override // java.io.Writer, java.lang.Appendable
/* 50 */        public Writer append(CharSequence charSequence, int i, int i2) {
/* 51 */            String string = charSequence.subSequence(i, i2).toString();
/* 52 */            this._buffer.append(string, 0, string.length());
/* 53 */            return this;
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 55 */        public void close() {
                }

                @Override // java.io.Writer, java.io.Flushable
/* 57 */        public void flush() {
                }

                @Override // java.io.Writer
/* 61 */        public void write(char[] cArr) {
/* 62 */            this._buffer.append(cArr, 0, cArr.length);
                }

                @Override // java.io.Writer
/* 66 */        public void write(char[] cArr, int i, int i2) {
/* 67 */            this._buffer.append(cArr, i, i2);
                }

                @Override // java.io.Writer
/* 71 */        public void write(int i) {
/* 72 */            this._buffer.append((char) i);
                }

                @Override // java.io.Writer
/* 75 */        public void write(String str) {
/* 76 */            this._buffer.append(str, 0, str.length());
                }

                @Override // java.io.Writer
/* 79 */        public void write(String str, int i, int i2) {
/* 80 */            this._buffer.append(str, i, i2);
                }

/* 97 */        public String getAndClear() {
/* 98 */            String strContentsAsString = this._buffer.contentsAsString();
/* 99 */            this._buffer.releaseBuffers();
/* 100 */           return strContentsAsString;
                }
            }
