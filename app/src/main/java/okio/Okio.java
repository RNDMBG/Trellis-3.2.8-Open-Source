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
            
            
    public final class Okio {
                private static final Logger logger = Logger.getLogger(Okio.class.getName());

        static  Logger access$000() {
            return logger;
                }

        private Okio() {
                }

        public static BufferedSource buffer(Source source) {
            if (source == null) {
                        throw new IllegalArgumentException("source == null");
                    }
            return new RealBufferedSource(source);
                }

        public static BufferedSink buffer(Sink sink) {
            if (sink == null) {
                        throw new IllegalArgumentException("sink == null");
                    }
            return new RealBufferedSink(sink);
                }

        public static Sink sink(OutputStream out) {
            return sink(out, new Timeout());
                }

        private static Sink sink(OutputStream out, Timeout timeout) {
            if (out == null) {
                        throw new IllegalArgumentException("out == null");
                    }
            if (timeout == null) {
                        throw new IllegalArgumentException("timeout == null");
                    }
            return new C12991(timeout, out);
                }

                
        static class C12991 implements Sink {
                    final  OutputStream val$out;
                    final  Timeout val$timeout;

            C12991(Timeout timeout, OutputStream outputStream) {
                this.val$timeout = timeout;
                        this.val$out = outputStream;
                    }

                    @Override 
            public void write(Buffer source, long byteCount) throws IOException {
                Util.checkOffsetAndCount(source.size, 0L, byteCount);
                        while (byteCount > 0) {
                    this.val$timeout.throwIfReached();
                    Segment head = source.head;
                    int toCopy = (int) Math.min(byteCount, head.limit - head.pos);
                    this.val$out.write(head.data, head.pos, toCopy);
                    head.pos += toCopy;
                    byteCount -= (long) toCopy;
                    source.size -= (long) toCopy;
                    if (head.pos == head.limit) {
                        source.head = head.pop();
                        SegmentPool.INSTANCE.recycle(head);
                            }
                        }
                    }

                    @Override 
            public void flush() throws IOException {
                this.val$out.flush();
                    }

                    @Override 
            public void close() throws IOException {
                this.val$out.close();
                    }

                    @Override 
            public Timeout timeout() {
               return this.val$timeout;
                    }

           public String toString() {
               return "sink(" + this.val$out + ")";
                    }
                }

       public static Sink sink(Socket socket) throws IOException {
           if (socket == null) {
                        throw new IllegalArgumentException("socket == null");
                    }
           AsyncTimeout timeout = timeout(socket);
           Sink sink = sink(socket.getOutputStream(), timeout);
           return timeout.sink(sink);
                }

       public static Source source(InputStream in) {
           return source(in, new Timeout());
                }

       private static Source source(InputStream in, Timeout timeout) {
           if (in == null) {
                        throw new IllegalArgumentException("in == null");
                    }
           if (timeout == null) {
                        throw new IllegalArgumentException("timeout == null");
                    }
           return new C13002(timeout, in);
                }

                
       static class C13002 implements Source {
                    final  InputStream val$in;
                    final  Timeout val$timeout;

           C13002(Timeout timeout, InputStream inputStream) {
               this.val$timeout = timeout;
                        this.val$in = inputStream;
                    }

                    @Override 
           public long read(Buffer sink, long byteCount) throws IOException {
                        if (byteCount < 0) {
                            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
                        }
               this.val$timeout.throwIfReached();
               Segment tail = sink.writableSegment(1);
               int maxToCopy = (int) Math.min(byteCount, 2048 - tail.limit);
               int bytesRead = this.val$in.read(tail.data, tail.limit, maxToCopy);
               if (bytesRead == -1) {
                            return -1L;
                        }
               tail.limit += bytesRead;
               sink.size += (long) bytesRead;
               return bytesRead;
                    }

                    @Override 
           public void close() throws IOException {
               this.val$in.close();
                    }

                    @Override 
           public Timeout timeout() {
               return this.val$timeout;
                    }

           public String toString() {
               return "source(" + this.val$in + ")";
                    }
                }

       public static Source source(File file) throws FileNotFoundException {
           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
           return source(new FileInputStream(file));
                }

       public static Source source(Path path, OpenOption... options) throws IOException {
           if (path == null) {
                        throw new IllegalArgumentException("path == null");
                    }
           return source(Files.newInputStream(path, options));
                }

       public static Sink sink(File file) throws FileNotFoundException {
           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
           return sink(new FileOutputStream(file));
                }

       public static Sink appendingSink(File file) throws FileNotFoundException {
           if (file == null) {
                        throw new IllegalArgumentException("file == null");
                    }
           return sink(new FileOutputStream(file, true));
                }

       public static Sink sink(Path path, OpenOption... options) throws IOException {
           if (path == null) {
                        throw new IllegalArgumentException("path == null");
                    }
           return sink(Files.newOutputStream(path, options));
                }

       public static Source source(Socket socket) throws IOException {
           if (socket == null) {
                        throw new IllegalArgumentException("socket == null");
                    }
           AsyncTimeout timeout = timeout(socket);
           Source source = source(socket.getInputStream(), timeout);
           return timeout.source(source);
                }

                
       static class C13013 extends AsyncTimeout {
                    final  Socket val$socket;

           C13013(Socket socket) {
               this.val$socket = socket;
                    }

                    @Override 
           protected void timedOut() {
                        try {
                   this.val$socket.close();
                        } catch (Exception e) {
                   Okio.access$000().log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, (Throwable) e);
                        }
                    }
                }

       private static AsyncTimeout timeout(Socket socket) {
           return new C13013(socket);
                }
            }
