            package okio;

            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.net.Socket;
            import java.nio.file.Files;
            import java.nio.file.OpenOption;
            import java.nio.file.Path;
            import java.util.logging.Level;
            import java.util.logging.Logger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 34 */    public final class Okio {
                private static final Logger logger = Logger.getLogger(Okio.class.getName());

/* 35 */        static /* synthetic */ Logger access$000() {
/* 36 */            return logger;
                }

/* 38 */        private Okio() {
                }

/* 47 */        public static BufferedSource buffer(Source source) {
/* 48 */            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
/* 49 */            return new RealBufferedSource(source);
                }

/* 57 */        public static BufferedSink buffer(Sink sink) {
/* 58 */            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
/* 59 */            return new RealBufferedSink(sink);
                }

/* 63 */        public static Sink sink(OutputStream out) {
/* 64 */            return sink(out, new Timeout());
                }

/* 67 */        private static Sink sink(OutputStream out, Timeout timeout) {
/* 68 */            if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
/* 69 */            if (timeout == null) {
                        throw new IllegalArgumentException("timeout == null");
                    }
/* 71 */            return new C12991(timeout, out);
                }

                /* JADX INFO: renamed from: okio.Okio$1 */
/* 69 */        static class C12991 implements Sink {
                    final /* synthetic */ OutputStream val$out;
                    final /* synthetic */ Timeout val$timeout;

/* 70 */            C12991(Timeout timeout, OutputStream outputStream) {
/* 71 */                this.val$timeout = timeout;
                        this.val$out = outputStream;
                    }

                    @Override // okio.Sink
/* 72 */            public void write(Buffer source, long byteCount) throws IOException {
/* 73 */                Util.checkOffsetAndCount(source.size, 0L, byteCount);
                        while (byteCount > 0) {
/* 75 */                    this.val$timeout.throwIfReached();
/* 76 */                    Segment head = source.head;
/* 77 */                    int toCopy = (int) Math.min(byteCount, head.limit - head.pos);
/* 78 */                    this.val$out.write(head.data, head.pos, toCopy);
/* 80 */                    head.pos += toCopy;
/* 81 */                    byteCount -= (long) toCopy;
/* 82 */                    source.size -= (long) toCopy;
/* 84 */                    if (head.pos == head.limit) {
/* 85 */                        source.head = head.pop();
/* 86 */                        SegmentPool.INSTANCE.recycle(head);
                            }
                        }
                    }

                    @Override // okio.Sink
/* 91 */            public void flush() throws IOException {
/* 92 */                this.val$out.flush();
                    }

                    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
/* 95 */            public void close() throws IOException {
/* 96 */                this.val$out.close();
                    }

                    @Override // okio.Sink
/* 99 */            public Timeout timeout() {
/* 100 */               return this.val$timeout;
                    }

/* 103 */           public String toString() {
/* 104 */               return "sink(" + this.val$out + ")";
                    }
                }

/* 114 */       public static Sink sink(Socket socket) throws IOException {
/* 115 */           if (socket == null) {
                        throw new IllegalArgumentException("socket == null");
                    }
/* 116 */           AsyncTimeout timeout = timeout(socket);
/* 117 */           Sink sink = sink(socket.getOutputStream(), timeout);
/* 118 */           return timeout.sink(sink);
                }

/* 122 */       public static Source source(InputStream in) {
/* 123 */           return source(in, new Timeout());
                }

/* 126 */       private static Source source(InputStream in, Timeout timeout) {
/* 127 */           if (in == null) {
                        throw new IllegalArgumentException("in == null");
                    }
/* 128 */           if (timeout == null) {
                        throw new IllegalArgumentException("timeout == null");
                    }
/* 130 */           return new C13002(timeout, in);
                }

                /* JADX INFO: renamed from: okio.Okio$2 */
/* 128 */       static class C13002 implements Source {
                    final /* synthetic */ InputStream val$in;
                    final /* synthetic */ Timeout val$timeout;

/* 129 */           C13002(Timeout timeout, InputStream inputStream) {
/* 130 */               this.val$timeout = timeout;
                        this.val$in = inputStream;
                    }

                    @Override // okio.Source
/* 131 */           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
/* 133 */               this.val$timeout.throwIfReached();
/* 134 */               Segment tail = sink.writableSegment(1);
/* 135 */               int maxToCopy = (int) Math.min(byteCount, 2048 - tail.limit);
/* 136 */               int bytesRead = this.val$in.read(tail.data, tail.limit, maxToCopy);
/* 137 */               if (bytesRead == -1) {
                            return -1L;
                        }
/* 138 */               tail.limit += bytesRead;
/* 139 */               sink.size += (long) bytesRead;
/* 140 */               return bytesRead;
                    }

                    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
/* 143 */           public void close() throws IOException {
/* 144 */               this.val$in.close();
                    }

                    @Override // okio.Source
/* 147 */           public Timeout timeout() {
/* 148 */               return this.val$timeout;
                    }

/* 151 */           public String toString() {
/* 152 */               return "source(" + this.val$in + ")";
                    }
                }

/* 158 */       public static Source source(File file) throws FileNotFoundException {
/* 159 */           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
/* 160 */           return source(new FileInputStream(file));
                }

/* 165 */       public static Source source(Path path, OpenOption... options) throws IOException {
/* 166 */           if (path == null) {
                        throw new IllegalArgumentException("path == null");
                    }
/* 167 */           return source(Files.newInputStream(path, options));
                }

/* 171 */       public static Sink sink(File file) throws FileNotFoundException {
/* 172 */           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
/* 173 */           return sink(new FileOutputStream(file));
                }

/* 177 */       public static Sink appendingSink(File file) throws FileNotFoundException {
/* 178 */           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
/* 179 */           return sink(new FileOutputStream(file, true));
                }

/* 184 */       public static Sink sink(Path path, OpenOption... options) throws IOException {
/* 185 */           if (path == null) {
                        throw new IllegalArgumentException("path == null");
                    }
/* 186 */           return sink(Files.newOutputStream(path, options));
                }

/* 194 */       public static Source source(Socket socket) throws IOException {
/* 195 */           if (socket == null) {
                        throw new IllegalArgumentException("socket == null");
                    }
/* 196 */           AsyncTimeout timeout = timeout(socket);
/* 197 */           Source source = source(socket.getInputStream(), timeout);
/* 198 */           return timeout.source(source);
                }

                /* JADX INFO: renamed from: okio.Okio$3 */
/* 200 */       static class C13013 extends AsyncTimeout {
                    final /* synthetic */ Socket val$socket;

/* 201 */           C13013(Socket socket) {
/* 202 */               this.val$socket = socket;
                    }

                    @Override // okio.AsyncTimeout
/* 204 */           protected void timedOut() {
                        try {
/* 205 */                   this.val$socket.close();
                        } catch (Exception e) {
/* 207 */                   Okio.access$000().log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, (Throwable) e);
                        }
                    }
                }

/* 201 */       private static AsyncTimeout timeout(Socket socket) {
/* 202 */           return new C13013(socket);
                }
            }
