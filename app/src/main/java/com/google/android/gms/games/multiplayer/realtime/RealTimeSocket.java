            package com.google.android.gms.games.multiplayer.realtime;

            import android.os.ParcelFileDescriptor;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RealTimeSocket {
                void close() throws IOException;

                InputStream getInputStream() throws IOException;

                OutputStream getOutputStream() throws IOException;

                ParcelFileDescriptor getParcelFileDescriptor() throws IOException;

                boolean isClosed();
            }
