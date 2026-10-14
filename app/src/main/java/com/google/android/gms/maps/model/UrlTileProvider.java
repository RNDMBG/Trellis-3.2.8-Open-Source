            package com.google.android.gms.maps.model;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            import java.net.URL;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class UrlTileProvider implements TileProvider {

                /* JADX INFO: renamed from: dP */
                private final int f2360dP;

                /* JADX INFO: renamed from: dQ */
                private final int f2361dQ;

                public UrlTileProvider(int width, int height) {
                    this.f2360dP = width;
                    this.f2361dQ = height;
                }

                /* JADX INFO: renamed from: a */
                private static long m2292a(InputStream inputStream, OutputStream outputStream) throws IOException {
                    byte[] bArr = new byte[4096];
                    long j = 0;
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            return j;
                        }
                        outputStream.write(bArr, 0, i);
                        j += (long) i;
                    }
                }

                /* JADX INFO: renamed from: a */
                private static byte[] m2293a(InputStream inputStream) throws IOException {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    m2292a(inputStream, byteArrayOutputStream);
                    return byteArrayOutputStream.toByteArray();
                }

                @Override // com.google.android.gms.maps.model.TileProvider
                public final Tile getTile(int x, int y, int zoom) {
                    URL tileUrl = getTileUrl(x, y, zoom);
                    if (tileUrl == null) {
                        return NO_TILE;
                    }
                    try {
                        return new Tile(this.f2360dP, this.f2361dQ, m2293a(tileUrl.openStream()));
                    } catch (IOException e) {
                        return null;
                    }
                }

                public abstract URL getTileUrl(int i, int i2, int i3);
            }
