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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    class NameValueBlockReader {
                private int compressedLimit;
                private final InflaterSource inflaterSource;
                private final BufferedSource source;

/* 36 */        static /* synthetic */ int access$000(NameValueBlockReader x0) {
/* 37 */            return x0.compressedLimit;
                }

/* 36 */        static /* synthetic */ int access$022(NameValueBlockReader x0, long x1) {
/* 37 */            int i = (int) (((long) x0.compressedLimit) - x1);
                    x0.compressedLimit = i;
                    return i;
                }

/* 49 */        public NameValueBlockReader(BufferedSource source) {
/* 54 */            Source throttleSource = new C12801(source);
/* 65 */            Inflater inflater = new C12812();
/* 77 */            this.inflaterSource = new InflaterSource(throttleSource, inflater);
/* 78 */            this.source = Okio.buffer(this.inflaterSource);
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.NameValueBlockReader$1 */
/* 52 */        class C12801 extends ForwardingSource {
/* 53 */            C12801(Source x0) {
                        super(x0);
                    }

                    @Override // okio.ForwardingSource, okio.Source
/* 55 */            public long read(Buffer sink, long byteCount) throws IOException {
/* 56 */                if (NameValueBlockReader.access$000(NameValueBlockReader.this) == 0) {
                            return -1L;
                        }
/* 57 */                long read = super.read(sink, Math.min(byteCount, NameValueBlockReader.access$000(NameValueBlockReader.this)));
                        if (read == -1) {
                            return -1L;
                        }
/* 59 */                NameValueBlockReader.access$022(NameValueBlockReader.this, read);
/* 60 */                return read;
                    }
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.spdy.NameValueBlockReader$2 */
/* 63 */        class C12812 extends Inflater {
/* 64 */            C12812() {
                    }

                    @Override // java.util.zip.Inflater
/* 67 */            public int inflate(byte[] buffer, int offset, int count) throws DataFormatException {
/* 68 */                int result = super.inflate(buffer, offset, count);
/* 69 */                if (result == 0 && needsDictionary()) {
/* 70 */                    setDictionary(Spdy3.DICTIONARY);
/* 71 */                    return super.inflate(buffer, offset, count);
                        }
/* 73 */                return result;
                    }
                }

/* 81 */        public List<Header> readNameValueBlock(int length) throws IOException {
/* 82 */            this.compressedLimit += length;
/* 84 */            int numberOfPairs = this.source.readInt();
/* 85 */            if (numberOfPairs < 0) {
                        throw new IOException("numberOfPairs < 0: " + numberOfPairs);
                    }
/* 86 */            if (numberOfPairs > 1024) {
                        throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
                    }
/* 88 */            List<Header> entries = new ArrayList<>(numberOfPairs);
                    for (int i = 0; i < numberOfPairs; i++) {
/* 90 */                ByteString name = readByteString().toAsciiLowercase();
/* 91 */                ByteString values = readByteString();
/* 92 */                if (name.size() == 0) {
                            throw new IOException("name.size == 0");
                        }
/* 93 */                entries.add(new Header(name, values));
                    }
/* 96 */            doneReading();
/* 97 */            return entries;
                }

/* 100 */       private ByteString readByteString() throws IOException {
/* 101 */           int length = this.source.readInt();
/* 102 */           return this.source.readByteString(length);
                }

/* 108 */       private void doneReading() throws IOException {
/* 109 */           if (this.compressedLimit > 0) {
/* 110 */               this.inflaterSource.refill();
/* 111 */               if (this.compressedLimit != 0) {
                            throw new IOException("compressedLimit > 0: " + this.compressedLimit);
                        }
                    }
                }

/* 115 */       public void close() throws IOException {
/* 116 */           this.source.close();
                }
            }
