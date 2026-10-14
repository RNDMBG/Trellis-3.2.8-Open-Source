            package com.google.android.exoplayer.util;

            import android.net.Uri;
            import android.os.AsyncTask;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.URL;
            
            
    public abstract class ManifestFetcher<T> extends AsyncTask<String, Void, T> {
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


        public ManifestFetcher(ManifestCallback<T> callback) {
            this(callback, 8000);
                }

        public ManifestFetcher(ManifestCallback<T> callback, int timeoutMillis) {
            this.callback = callback;
            this.timeoutMillis = timeoutMillis;
                }

                
        protected final T doInBackground(String... data) {
                    try {
                this.contentId = data.length > 1 ? data[1] : null;
                String urlString = data[0];
                InputStream inputStream = null;
                        try {
                    Uri baseUrl = Util.parseBaseUri(urlString);
                    HttpURLConnection connection = configureHttpConnection(new URL(urlString));
                    inputStream = connection.getInputStream();
                    String inputEncoding = connection.getContentEncoding();
                    T t = parse(inputStream, inputEncoding, this.contentId, baseUrl);
                    if (inputStream != null) {
                        inputStream.close();
                       return t;
                            }
                   return t;
                        } catch (Throwable th) {
                            if (inputStream != null) {
                        inputStream.close();
                            }
                            throw th;
                        }
                    } catch (Exception e) {
               this.exception = e;
                        return null;
                    }
                }

                @Override 
       protected final void onPostExecute(T manifest) {
           if (this.exception != null) {
               this.callback.onManifestError(this.contentId, this.exception);
                    } else {
               this.callback.onManifest(this.contentId, manifest);
                    }
                }

       private HttpURLConnection configureHttpConnection(URL url) throws IOException {
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setConnectTimeout(this.timeoutMillis);
           connection.setReadTimeout(this.timeoutMillis);
           connection.setDoOutput(false);
           connection.connect();
           return connection;
                }
            }
