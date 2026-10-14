            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.util.BufferRecycler;
            import com.fasterxml.jackson.core.util.TextBuffer;
            import java.io.IOException;
            import java.io.Writer;
            
            
    public final class SegmentedStringWriter extends Writer {
                protected final TextBuffer _buffer;




        public SegmentedStringWriter(BufferRecycler bufferRecycler) {
            this._buffer = new TextBuffer(bufferRecycler);
                }

                @Override 
        public Writer append(char c) {
            write(c);
            return this;
                }

                @Override 
        public Writer append(CharSequence charSequence) {
            String string = charSequence.toString();
            this._buffer.append(string, 0, string.length());
            return this;
                }

                @Override 
        public Writer append(CharSequence charSequence, int i, int i2) {
            String string = charSequence.subSequence(i, i2).toString();
            this._buffer.append(string, 0, string.length());
            return this;
                }

                @Override 
        public void close() {
                }

                @Override 
        public void flush() {
                }

                @Override 
        public void write(char[] cArr) {
            this._buffer.append(cArr, 0, cArr.length);
                }

                @Override 
        public void write(char[] cArr, int i, int i2) {
            this._buffer.append(cArr, i, i2);
                }

                @Override 
        public void write(int i) {
            this._buffer.append((char) i);
                }

                @Override 
        public void write(String str) {
            this._buffer.append(str, 0, str.length());
                }

                @Override 
        public void write(String str, int i, int i2) {
            this._buffer.append(str, i, i2);
                }

        public String getAndClear() {
            String strContentsAsString = this._buffer.contentsAsString();
            this._buffer.releaseBuffers();
           return strContentsAsString;
                }
            }
