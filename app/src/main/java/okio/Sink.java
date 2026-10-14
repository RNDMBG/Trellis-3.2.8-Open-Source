            package okio;

            import java.io.Closeable;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface Sink extends Closeable {
                @Override // java.io.Closeable, java.lang.AutoCloseable
                void close() throws IOException;

                void flush() throws IOException;

                Timeout timeout();

                void write(Buffer buffer, long j) throws IOException;
            }
