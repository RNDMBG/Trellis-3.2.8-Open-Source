            package co.vine.android.network;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class DefaultHttpOperationReader implements NetworkOperationReader {
                private final byte[] mBuffer;
                private final OutputStream mOutputStream;
                private final ProgressListener mProgressListener;

/* 12 */        public DefaultHttpOperationReader() {
/* 13 */            this(null, null);
                }

/* 16 */        public DefaultHttpOperationReader(OutputStream outputStream, ProgressListener progressListener) {
/* 8 */             this.mBuffer = new byte[2048];
/* 18 */            this.mOutputStream = outputStream;
/* 19 */            this.mProgressListener = progressListener;
                }

                @Override // co.vine.android.network.NetworkOperationReader
/* 24 */        public final void readInput(int statusCode, long contentLength, InputStream in) throws IOException {
                    OutputStream out;
/* 25 */            if (this.mOutputStream != null && this.mProgressListener != null) {
/* 26 */                out = new CountingOutputStream(this.mOutputStream, contentLength, this.mProgressListener);
                    } else {
/* 28 */                out = this.mOutputStream;
                    }
/* 30 */            byte[] buffer = this.mBuffer;
                    try {
                        if (contentLength < 0) {
                            while (true) {
/* 34 */                        int bytesRead = in.read(buffer, 0, buffer.length);
                                if (bytesRead == -1) {
                                    break;
                                } else if (out != null) {
/* 36 */                            out.write(buffer, 0, bytesRead);
                                }
                            }
                        } else {
/* 40 */                    long bytesRemaining = contentLength;
                            while (bytesRemaining > 0) {
/* 42 */                        int bytesRead2 = in.read(buffer, 0, (int) Math.min(bytesRemaining, buffer.length));
/* 43 */                        if (bytesRead2 == -1) {
/* 44 */                            throw new IOException("Invalid content length: " + bytesRemaining);
                                }
/* 45 */                        if (bytesRead2 > 0) {
/* 46 */                            bytesRemaining -= (long) bytesRead2;
/* 47 */                            if (out != null) {
/* 48 */                                out.write(buffer, 0, bytesRead2);
                                    }
                                }
                            }
                        }
/* 55 */                while (in.read(buffer, 0, buffer.length) != -1) {
                        }
/* 59 */                if (out != null) {
                            try {
/* 60 */                        out.flush();
/* 61 */                        out.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (Throwable th) {
                        while (in.read(buffer, 0, buffer.length) != -1) {
                        }
/* 59 */                if (out != null) {
                            try {
/* 60 */                        out.flush();
/* 61 */                        out.close();
                            } catch (IOException e2) {
                            }
                        }
/* 64 */                throw th;
                    }
                }

                @Override // co.vine.android.network.NetworkOperationReader
/* 68 */        public void onHandleError(HttpResult result) {
                }
            }
