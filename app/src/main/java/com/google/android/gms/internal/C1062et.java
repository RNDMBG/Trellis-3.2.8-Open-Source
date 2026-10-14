            package com.google.android.gms.internal;

            import android.net.LocalSocket;
            import android.os.Parcel;
            import android.os.ParcelFileDescriptor;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
            final class C1062et implements RealTimeSocket {

                
                private ParcelFileDescriptor f1856jN;

                
                private final String f1857nd;

                
                private final LocalSocket f1858nt;

                C1062et(LocalSocket localSocket, String str) {
                    this.f1858nt = localSocket;
                    this.f1857nd = str;
                }

                @Override 
                public void close() throws IOException {
                    this.f1858nt.close();
                }

                @Override 
                public InputStream getInputStream() throws IOException {
                    return this.f1858nt.getInputStream();
                }

                @Override 
                public OutputStream getOutputStream() throws IOException {
                    return this.f1858nt.getOutputStream();
                }

                @Override 
                public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
                    if (this.f1856jN == null && !isClosed()) {
                        Parcel parcelObtain = Parcel.obtain();
                        parcelObtain.writeFileDescriptor(this.f1858nt.getFileDescriptor());
                        parcelObtain.setDataPosition(0);
                        this.f1856jN = parcelObtain.readFileDescriptor();
                    }
                    return this.f1856jN;
                }

                @Override 
                public boolean isClosed() {
                    return (this.f1858nt.isConnected() || this.f1858nt.isBound()) ? false : true;
                }
            }
