            package com.google.android.exoplayer.util;

            import android.net.Uri;
            import android.os.AsyncTask;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public abstract class ManifestFetcher<T> extends AsyncTask<String, Void, T> {
                public static final int DEFAULT_HTTP_TIMEOUT_MILLIS = 8000;
                private final ManifestCallback<T> callback;
                private volatile String contentId;
                private volatile Exception exception;
                private final int timeoutMillis;

                public interface ManifestCallback<T> {
                    void onManifest(String str, T t);

                    void onManifestError(String str, Exception exc);
                }

                protected abstract T parse(InputStream inputStream, String str, String str2, Uri uri) throws IOException;


/* 71 */        public ManifestFetcher(ManifestCallback<T> callback) {
/* 72 */            this(callback, 8000);
                }

/* 78 */        public ManifestFetcher(ManifestCallback<T> callback, int timeoutMillis) {
/* 80 */            this.callback = callback;
/* 81 */            this.timeoutMillis = timeoutMillis;
                }

                /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 86 */        protected final T doInBackground(String... data) {
                    try {
/* 87 */                this.contentId = data.length > 1 ? data[1] : null;
/* 88 */                String urlString = data[0];
/* 90 */                InputStream inputStream = null;
                        try {
/* 92 */                    Uri baseUrl = Util.parseBaseUri(urlString);
/* 93 */                    HttpURLConnection connection = configureHttpConnection(new URL(urlString));
/* 94 */                    inputStream = connection.getInputStream();
/* 95 */                    String inputEncoding = connection.getContentEncoding();
/* 96 */                    T t = parse(inputStream, inputEncoding, this.contentId, baseUrl);
/* 98 */                    if (inputStream != null) {
/* 99 */                        inputStream.close();
/* 104 */                       return t;
                            }
/* 104 */                   return t;
                        } catch (Throwable th) {
                            if (inputStream != null) {
/* 99 */                        inputStream.close();
                            }
                            throw th;
                        }
                    } catch (Exception e) {
/* 103 */               this.exception = e;
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
/* 109 */       protected final void onPostExecute(T manifest) {
/* 110 */           if (this.exception != null) {
/* 111 */               this.callback.onManifestError(this.contentId, this.exception);
                    } else {
/* 113 */               this.callback.onManifest(this.contentId, manifest);
                    }
                }

/* 132 */       private HttpURLConnection configureHttpConnection(URL url) throws IOException {
/* 133 */           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
/* 134 */           connection.setConnectTimeout(this.timeoutMillis);
/* 135 */           connection.setReadTimeout(this.timeoutMillis);
/* 136 */           connection.setDoOutput(false);
/* 137 */           connection.connect();
/* 138 */           return connection;
                }
            }
