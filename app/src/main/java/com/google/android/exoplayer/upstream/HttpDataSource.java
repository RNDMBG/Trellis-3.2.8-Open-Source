            package com.google.android.exoplayer.upstream;

            import android.text.TextUtils;
            import android.util.Log;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.MimeTypes;
            import com.google.android.exoplayer.util.Predicate;
            import com.google.android.exoplayer.util.Util;
            import java.io.IOException;
            import java.io.InputStream;
            import java.net.HttpURLConnection;
            import java.net.URL;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            
            
    public class HttpDataSource implements DataSource {
                public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
                public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
                private static final String TAG = "HttpDataSource";
                private long bytesRead;
                private final int connectTimeoutMillis;
                private HttpURLConnection connection;
                private final Predicate<String> contentTypePredicate;
                private long dataLength;
                private DataSpec dataSpec;
                private InputStream inputStream;
                private final TransferListener listener;
                private boolean opened;
                private final int readTimeoutMillis;
                private final HashMap<String, String> requestProperties;
                private final String userAgent;
                public static final Predicate<String> REJECT_PAYWALL_TYPES = new C08751();
                private static final Pattern CONTENT_RANGE_HEADER = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");

                
        static class C08751 implements Predicate<String> {
            C08751() {
                    }


                    
            public boolean evaluate(String contentType) {
                String contentType2 = Util.toLowerInvariant(contentType);
                        return (TextUtils.isEmpty(contentType2) || (contentType2.contains(MimeTypes.BASE_TYPE_TEXT) && !contentType2.contains(MimeTypes.TEXT_VTT)) || contentType2.contains("html") || contentType2.contains("xml")) ? false : true;
                    }
                }

        public static class HttpDataSourceException extends IOException {
                    public final DataSpec dataSpec;

            public HttpDataSourceException(DataSpec dataSpec) {
                this.dataSpec = dataSpec;
                    }

            public HttpDataSourceException(String message, DataSpec dataSpec) {
                super(message);
                this.dataSpec = dataSpec;
                    }

            public HttpDataSourceException(IOException cause, DataSpec dataSpec) {
                super(cause);
                this.dataSpec = dataSpec;
                    }

            public HttpDataSourceException(String message, IOException cause, DataSpec dataSpec) {
                super(message, cause);
                this.dataSpec = dataSpec;
                    }
                }

        public static final class InvalidContentTypeException extends HttpDataSourceException {
                    public final String contentType;

            public InvalidContentTypeException(String contentType, DataSpec dataSpec) {
                super("Invalid content type: " + contentType, dataSpec);
                this.contentType = contentType;
                    }
                }

       public static final class InvalidResponseCodeException extends HttpDataSourceException {
                    public final Map<String, List<String>> headerFields;
                    public final int responseCode;

           public InvalidResponseCodeException(int responseCode, Map<String, List<String>> headerFields, DataSpec dataSpec) {
               super("Response code: " + responseCode, dataSpec);
               this.responseCode = responseCode;
               this.headerFields = headerFields;
                    }
                }

       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate) {
           this(userAgent, contentTypePredicate, null);
                }

       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate, TransferListener listener) {
           this(userAgent, contentTypePredicate, listener, 8000, 8000);
                }

       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate, TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis) {
           this.userAgent = Assertions.checkNotEmpty(userAgent);
           this.contentTypePredicate = contentTypePredicate;
           this.listener = listener;
           this.requestProperties = new HashMap<>();
           this.connectTimeoutMillis = connectTimeoutMillis;
           this.readTimeoutMillis = readTimeoutMillis;
                }

       public void setRequestProperty(String name, String value) {
           Assertions.checkNotNull(name);
           Assertions.checkNotNull(value);
                    synchronized (this.requestProperties) {
                        try {
                   this.requestProperties.put(name, value);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void clearRequestProperty(String name) {
           Assertions.checkNotNull(name);
                    synchronized (this.requestProperties) {
                        try {
                   this.requestProperties.remove(name);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void clearAllRequestProperties() {
                    synchronized (this.requestProperties) {
                        try {
                   this.requestProperties.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override 
       public long open(DataSpec dataSpec) throws HttpDataSourceException {
           this.dataSpec = dataSpec;
           this.bytesRead = 0L;
                    try {
               this.connection = makeConnection(dataSpec);
                        try {
                   int responseCode = this.connection.getResponseCode();
                   if (responseCode < 200 || responseCode > 299) {
                       Map<String, List<String>> headers = this.connection.getHeaderFields();
                       closeConnection();
                       throw new InvalidResponseCodeException(responseCode, headers, dataSpec);
                            }
                   String contentType = this.connection.getContentType();
                   if (this.contentTypePredicate != null && !this.contentTypePredicate.evaluate(contentType)) {
                       closeConnection();
                       throw new InvalidContentTypeException(contentType, dataSpec);
                            }
                   long contentLength = getContentLength(this.connection);
                   this.dataLength = dataSpec.length == -1 ? contentLength : dataSpec.length;
                            if (dataSpec.length != -1 && contentLength != -1 && contentLength != dataSpec.length) {
                       closeConnection();
                       throw new HttpDataSourceException(new UnexpectedLengthException(dataSpec.length, contentLength), dataSpec);
                            }
                            try {
                       this.inputStream = this.connection.getInputStream();
                       this.opened = true;
                       if (this.listener != null) {
                           this.listener.onTransferStart();
                                }
                       return this.dataLength;
                            } catch (IOException e) {
                       closeConnection();
                       throw new HttpDataSourceException(e, dataSpec);
                            }
                        } catch (IOException e2) {
                   throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e2, dataSpec);
                        }
                    } catch (IOException e3) {
               throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e3, dataSpec);
                    }
                }

                @Override 
       public int read(byte[] buffer, int offset, int readLength) throws HttpDataSourceException {
                    try {
               int read = this.inputStream.read(buffer, offset, readLength);
               if (read > 0) {
                   this.bytesRead += (long) read;
                   if (this.listener != null) {
                       this.listener.onBytesTransferred(read);
                            }
                        } else if (this.dataLength != -1 && this.dataLength != this.bytesRead) {
                   throw new HttpDataSourceException(new UnexpectedLengthException(this.dataLength, this.bytesRead), this.dataSpec);
                        }
               return read;
                    } catch (IOException e) {
               throw new HttpDataSourceException(e, this.dataSpec);
                    }
                }

                @Override 
       public void close() throws HttpDataSourceException {
                    try {
               if (this.inputStream != null) {
                            try {
                       this.inputStream.close();
                       this.inputStream = null;
                            } catch (IOException e) {
                       throw new HttpDataSourceException(e, this.dataSpec);
                            }
                        }
               if (this.opened) {
                   this.opened = false;
                   if (this.listener != null) {
                       this.listener.onTransferEnd();
                            }
                   closeConnection();
                        }
                    } catch (Throwable th) {
                        if (this.opened) {
                   this.opened = false;
                   if (this.listener != null) {
                       this.listener.onTransferEnd();
                            }
                   closeConnection();
                        }
                        throw th;
                    }
                }

       private void closeConnection() {
           if (this.connection != null) {
               this.connection.disconnect();
               this.connection = null;
                    }
                }

       protected final HttpURLConnection getConnection() {
           return this.connection;
                }

       protected final long bytesRead() {
           return this.bytesRead;
                }

       protected final long bytesRemaining() {
                    return this.dataLength == -1 ? this.dataLength : this.dataLength - this.bytesRead;
                }

       private HttpURLConnection makeConnection(DataSpec dataSpec) throws IOException {
           URL url = new URL(dataSpec.uri.toString());
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setConnectTimeout(this.connectTimeoutMillis);
           connection.setReadTimeout(this.readTimeoutMillis);
           connection.setDoOutput(false);
                    synchronized (this.requestProperties) {
                        try {
                            for (Map.Entry<String, String> property : this.requestProperties.entrySet()) {
                       connection.setRequestProperty(property.getKey(), property.getValue());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           connection.setRequestProperty("Accept-Encoding", "deflate");
           connection.setRequestProperty("User-Agent", this.userAgent);
           connection.setRequestProperty("Range", buildRangeHeader(dataSpec));
           connection.connect();
           return connection;
                }

       private String buildRangeHeader(DataSpec dataSpec) {
           String rangeRequest = "bytes=" + dataSpec.position + "-";
                    if (dataSpec.length != -1) {
               return rangeRequest + ((dataSpec.position + dataSpec.length) - 1);
                    }
           return rangeRequest;
                }

       private long getContentLength(HttpURLConnection connection) {
           long contentLength = -1;
           String contentLengthHeader = connection.getHeaderField("Content-Length");
           if (!TextUtils.isEmpty(contentLengthHeader)) {
                        try {
                   contentLength = Long.parseLong(contentLengthHeader);
                        } catch (NumberFormatException e) {
                   Log.e(TAG, "Unexpected Content-Length [" + contentLengthHeader + "]");
                        }
                    }
           String contentRangeHeader = connection.getHeaderField("Content-Range");
           if (!TextUtils.isEmpty(contentRangeHeader)) {
               Matcher matcher = CONTENT_RANGE_HEADER.matcher(contentRangeHeader);
               if (matcher.find()) {
                            try {
                       long contentLengthFromRange = (Long.parseLong(matcher.group(2)) - Long.parseLong(matcher.group(1))) + 1;
                                if (contentLength < 0) {
                           return contentLengthFromRange;
                                }
                                if (contentLength != contentLengthFromRange) {
                           Log.w(TAG, "Inconsistent headers [" + contentLengthHeader + "] [" + contentRangeHeader + "]");
                           return Math.max(contentLength, contentLengthFromRange);
                                }
                       return contentLength;
                            } catch (NumberFormatException e2) {
                       Log.e(TAG, "Unexpected Content-Range [" + contentRangeHeader + "]");
                       return contentLength;
                            }
                        }
               return contentLength;
                    }
           return contentLength;
                }
            }
