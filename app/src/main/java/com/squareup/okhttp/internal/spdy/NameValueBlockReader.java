            package com.squareup.okhttp.internal.spdy;

            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.zip.DataFormatException;
            import java.util.zip.Inflater;
            import okio.Buffer;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.ForwardingSource;
            import okio.InflaterSource;
            import okio.Okio;
            import okio.Source;
            
            
    class NameValueBlockReader {
                private int compressedLimit;
                private final InflaterSource inflaterSource;
                private final BufferedSource source;

        static  int access$000(NameValueBlockReader x0) {
            return x0.compressedLimit;
                }

        static  int access$022(NameValueBlockReader x0, long x1) {
            int i = (int) (((long) x0.compressedLimit) - x1);
                    x0.compressedLimit = i;
                    return i;
                }

        public NameValueBlockReader(BufferedSource source) {
            Source throttleSource = new C12801(source);
            Inflater inflater = new C12812();
            this.inflaterSource = new InflaterSource(throttleSource, inflater);
            this.source = Okio.buffer(this.inflaterSource);
                }

                
        class C12801 extends ForwardingSource {
            C12801(Source x0) {
                        super(x0);
                    }

                    @Override 
            public long read(Buffer sink, long byteCount) throws IOException {
                if (NameValueBlockReader.access$000(NameValueBlockReader.this) == 0) {
                            return -1L;
                        }
                long read = super.read(sink, Math.min(byteCount, NameValueBlockReader.access$000(NameValueBlockReader.this)));
                        if (read == -1) {
                            return -1L;
                        }
                NameValueBlockReader.access$022(NameValueBlockReader.this, read);
                return read;
                    }
                }

                
        class C12812 extends Inflater {
            C12812() {
                    }

                    @Override 
            public int inflate(byte[] buffer, int offset, int count) throws DataFormatException {
                int result = super.inflate(buffer, offset, count);
                if (result == 0 && needsDictionary()) {
                    setDictionary(Spdy3.DICTIONARY);
                    return super.inflate(buffer, offset, count);
                        }
                return result;
                    }
                }

        public List<Header> readNameValueBlock(int length) throws IOException {
            this.compressedLimit += length;
            int numberOfPairs = this.source.readInt();
            if (numberOfPairs < 0) {
                        throw new IOException("numberOfPairs < 0: " + numberOfPairs);
                    }
            if (numberOfPairs > 1024) {
                        throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
                    }
            List<Header> entries = new ArrayList<>(numberOfPairs);
                    for (int i = 0; i < numberOfPairs; i++) {
                ByteString name = readByteString().toAsciiLowercase();
                ByteString values = readByteString();
                if (name.size() == 0) {
                            throw new IOException("name.size == 0");
                        }
                entries.add(new Header(name, values));
                    }
            doneReading();
            return entries;
                }

       private ByteString readByteString() throws IOException {
           int length = this.source.readInt();
           return this.source.readByteString(length);
                }

       private void doneReading() throws IOException {
           if (this.compressedLimit > 0) {
               this.inflaterSource.refill();
               if (this.compressedLimit != 0) {
                            throw new IOException("compressedLimit > 0: " + this.compressedLimit);
                        }
                    }
                }

       public void close() throws IOException {
           this.source.close();
                }
            }
