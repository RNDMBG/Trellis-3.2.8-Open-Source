            package com.google.android.gms.internal;

            import android.net.LocalSocket;
            import android.os.Parcel;
            import android.os.ParcelFileDescriptor;
            import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.et */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            final class C1062et implements RealTimeSocket {

                /* JADX INFO: renamed from: jN */
                private ParcelFileDescriptor f1856jN;

                /* JADX INFO: renamed from: nd */
                private final String f1857nd;

                /* JADX INFO: renamed from: nt */
                private final LocalSocket f1858nt;

                C1062et(LocalSocket localSocket, String str) {
                    this.f1858nt = localSocket;
                    this.f1857nd = str;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
                public void close() throws IOException {
                    this.f1858nt.close();
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
                public InputStream getInputStream() throws IOException {
                    return this.f1858nt.getInputStream();
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
                public OutputStream getOutputStream() throws IOException {
                    return this.f1858nt.getOutputStream();
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
                public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
                    if (this.f1856jN == null && !isClosed()) {
                        Parcel parcelObtain = Parcel.obtain();
                        parcelObtain.writeFileDescriptor(this.f1858nt.getFileDescriptor());
                        parcelObtain.setDataPosition(0);
                        this.f1856jN = parcelObtain.readFileDescriptor();
                    }
                    return this.f1856jN;
                }

                @Override // com.google.android.gms.games.multiplayer.realtime.RealTimeSocket
                public boolean isClosed() {
                    return (this.f1858nt.isConnected() || this.f1858nt.isBound()) ? false : true;
                }
            }
