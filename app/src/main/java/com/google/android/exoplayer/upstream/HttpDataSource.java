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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class HttpDataSource implements DataSource {
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

                /* JADX INFO: renamed from: com.google.android.exoplayer.upstream.HttpDataSource$1 */
/* 42 */        static class C08751 implements Predicate<String> {
/* 43 */            C08751() {
                    }


                    /* JADX INFO: renamed from: evaluate, reason: avoid collision after fix types in other method */
/* 47 */            public boolean evaluate(String contentType) {
/* 48 */                String contentType2 = Util.toLowerInvariant(contentType);
                        return (TextUtils.isEmpty(contentType2) || (contentType2.contains(MimeTypes.BASE_TYPE_TEXT) && !contentType2.contains(MimeTypes.TEXT_VTT)) || contentType2.contains("html") || contentType2.contains("xml")) ? false : true;
                    }
                }

/* 65 */        public static class HttpDataSourceException extends IOException {
                    public final DataSpec dataSpec;

/* 66 */            public HttpDataSourceException(DataSpec dataSpec) {
/* 68 */                this.dataSpec = dataSpec;
                    }

/* 71 */            public HttpDataSourceException(String message, DataSpec dataSpec) {
/* 72 */                super(message);
/* 73 */                this.dataSpec = dataSpec;
                    }

/* 76 */            public HttpDataSourceException(IOException cause, DataSpec dataSpec) {
/* 77 */                super(cause);
/* 78 */                this.dataSpec = dataSpec;
                    }

/* 81 */            public HttpDataSourceException(String message, IOException cause, DataSpec dataSpec) {
/* 82 */                super(message, cause);
/* 83 */                this.dataSpec = dataSpec;
                    }
                }

/* 94 */        public static final class InvalidContentTypeException extends HttpDataSourceException {
                    public final String contentType;

/* 95 */            public InvalidContentTypeException(String contentType, DataSpec dataSpec) {
/* 96 */                super("Invalid content type: " + contentType, dataSpec);
/* 97 */                this.contentType = contentType;
                    }
                }

/* 117 */       public static final class InvalidResponseCodeException extends HttpDataSourceException {
                    public final Map<String, List<String>> headerFields;
                    public final int responseCode;

/* 118 */           public InvalidResponseCodeException(int responseCode, Map<String, List<String>> headerFields, DataSpec dataSpec) {
/* 119 */               super("Response code: " + responseCode, dataSpec);
/* 120 */               this.responseCode = responseCode;
/* 121 */               this.headerFields = headerFields;
                    }
                }

/* 154 */       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate) {
/* 155 */           this(userAgent, contentTypePredicate, null);
                }

/* 166 */       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate, TransferListener listener) {
/* 167 */           this(userAgent, contentTypePredicate, listener, 8000, 8000);
                }

/* 182 */       public HttpDataSource(String userAgent, Predicate<String> contentTypePredicate, TransferListener listener, int connectTimeoutMillis, int readTimeoutMillis) {
/* 184 */           this.userAgent = Assertions.checkNotEmpty(userAgent);
/* 185 */           this.contentTypePredicate = contentTypePredicate;
/* 186 */           this.listener = listener;
/* 187 */           this.requestProperties = new HashMap<>();
/* 188 */           this.connectTimeoutMillis = connectTimeoutMillis;
/* 189 */           this.readTimeoutMillis = readTimeoutMillis;
                }

/* 199 */       public void setRequestProperty(String name, String value) {
/* 200 */           Assertions.checkNotNull(name);
/* 201 */           Assertions.checkNotNull(value);
                    synchronized (this.requestProperties) {
                        try {
/* 203 */                   this.requestProperties.put(name, value);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 213 */       public void clearRequestProperty(String name) {
/* 214 */           Assertions.checkNotNull(name);
                    synchronized (this.requestProperties) {
                        try {
/* 216 */                   this.requestProperties.remove(name);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 223 */       public void clearAllRequestProperties() {
                    synchronized (this.requestProperties) {
                        try {
/* 225 */                   this.requestProperties.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 230 */       public long open(DataSpec dataSpec) throws HttpDataSourceException {
/* 231 */           this.dataSpec = dataSpec;
/* 232 */           this.bytesRead = 0L;
                    try {
/* 234 */               this.connection = makeConnection(dataSpec);
                        try {
/* 243 */                   int responseCode = this.connection.getResponseCode();
/* 248 */                   if (responseCode < 200 || responseCode > 299) {
/* 249 */                       Map<String, List<String>> headers = this.connection.getHeaderFields();
/* 250 */                       closeConnection();
/* 251 */                       throw new InvalidResponseCodeException(responseCode, headers, dataSpec);
                            }
/* 255 */                   String contentType = this.connection.getContentType();
/* 256 */                   if (this.contentTypePredicate != null && !this.contentTypePredicate.evaluate(contentType)) {
/* 257 */                       closeConnection();
/* 258 */                       throw new InvalidContentTypeException(contentType, dataSpec);
                            }
/* 261 */                   long contentLength = getContentLength(this.connection);
/* 262 */                   this.dataLength = dataSpec.length == -1 ? contentLength : dataSpec.length;
                            if (dataSpec.length != -1 && contentLength != -1 && contentLength != dataSpec.length) {
/* 268 */                       closeConnection();
/* 269 */                       throw new HttpDataSourceException(new UnexpectedLengthException(dataSpec.length, contentLength), dataSpec);
                            }
                            try {
/* 274 */                       this.inputStream = this.connection.getInputStream();
/* 280 */                       this.opened = true;
/* 281 */                       if (this.listener != null) {
/* 282 */                           this.listener.onTransferStart();
                                }
/* 285 */                       return this.dataLength;
                            } catch (IOException e) {
/* 276 */                       closeConnection();
/* 277 */                       throw new HttpDataSourceException(e, dataSpec);
                            }
                        } catch (IOException e2) {
/* 245 */                   throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e2, dataSpec);
                        }
                    } catch (IOException e3) {
/* 236 */               throw new HttpDataSourceException("Unable to connect to " + dataSpec.uri.toString(), e3, dataSpec);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 289 */       public int read(byte[] buffer, int offset, int readLength) throws HttpDataSourceException {
                    try {
/* 292 */               int read = this.inputStream.read(buffer, offset, readLength);
/* 297 */               if (read > 0) {
/* 298 */                   this.bytesRead += (long) read;
/* 299 */                   if (this.listener != null) {
/* 300 */                       this.listener.onBytesTransferred(read);
                            }
                        } else if (this.dataLength != -1 && this.dataLength != this.bytesRead) {
/* 305 */                   throw new HttpDataSourceException(new UnexpectedLengthException(this.dataLength, this.bytesRead), this.dataSpec);
                        }
/* 309 */               return read;
                    } catch (IOException e) {
/* 294 */               throw new HttpDataSourceException(e, this.dataSpec);
                    }
                }

                @Override // com.google.android.exoplayer.upstream.DataSource
/* 314 */       public void close() throws HttpDataSourceException {
                    try {
/* 315 */               if (this.inputStream != null) {
                            try {
/* 317 */                       this.inputStream.close();
/* 321 */                       this.inputStream = null;
                            } catch (IOException e) {
/* 319 */                       throw new HttpDataSourceException(e, this.dataSpec);
                            }
                        }
/* 324 */               if (this.opened) {
/* 325 */                   this.opened = false;
/* 326 */                   if (this.listener != null) {
/* 327 */                       this.listener.onTransferEnd();
                            }
/* 329 */                   closeConnection();
                        }
                    } catch (Throwable th) {
                        if (this.opened) {
/* 325 */                   this.opened = false;
/* 326 */                   if (this.listener != null) {
/* 327 */                       this.listener.onTransferEnd();
                            }
/* 329 */                   closeConnection();
                        }
                        throw th;
                    }
                }

/* 334 */       private void closeConnection() {
/* 335 */           if (this.connection != null) {
/* 336 */               this.connection.disconnect();
/* 337 */               this.connection = null;
                    }
                }

/* 346 */       protected final HttpURLConnection getConnection() {
/* 347 */           return this.connection;
                }

/* 356 */       protected final long bytesRead() {
/* 357 */           return this.bytesRead;
                }

/* 368 */       protected final long bytesRemaining() {
                    return this.dataLength == -1 ? this.dataLength : this.dataLength - this.bytesRead;
                }

/* 372 */       private HttpURLConnection makeConnection(DataSpec dataSpec) throws IOException {
/* 373 */           URL url = new URL(dataSpec.uri.toString());
/* 374 */           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
/* 375 */           connection.setConnectTimeout(this.connectTimeoutMillis);
/* 376 */           connection.setReadTimeout(this.readTimeoutMillis);
/* 377 */           connection.setDoOutput(false);
                    synchronized (this.requestProperties) {
                        try {
                            for (Map.Entry<String, String> property : this.requestProperties.entrySet()) {
/* 380 */                       connection.setRequestProperty(property.getKey(), property.getValue());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 383 */           connection.setRequestProperty("Accept-Encoding", "deflate");
/* 384 */           connection.setRequestProperty("User-Agent", this.userAgent);
/* 385 */           connection.setRequestProperty("Range", buildRangeHeader(dataSpec));
/* 386 */           connection.connect();
/* 387 */           return connection;
                }

/* 390 */       private String buildRangeHeader(DataSpec dataSpec) {
/* 391 */           String rangeRequest = "bytes=" + dataSpec.position + "-";
                    if (dataSpec.length != -1) {
/* 393 */               return rangeRequest + ((dataSpec.position + dataSpec.length) - 1);
                    }
/* 395 */           return rangeRequest;
                }

/* 398 */       private long getContentLength(HttpURLConnection connection) {
/* 399 */           long contentLength = -1;
/* 400 */           String contentLengthHeader = connection.getHeaderField("Content-Length");
/* 401 */           if (!TextUtils.isEmpty(contentLengthHeader)) {
                        try {
/* 403 */                   contentLength = Long.parseLong(contentLengthHeader);
                        } catch (NumberFormatException e) {
/* 405 */                   Log.e(TAG, "Unexpected Content-Length [" + contentLengthHeader + "]");
                        }
                    }
/* 408 */           String contentRangeHeader = connection.getHeaderField("Content-Range");
/* 409 */           if (!TextUtils.isEmpty(contentRangeHeader)) {
/* 410 */               Matcher matcher = CONTENT_RANGE_HEADER.matcher(contentRangeHeader);
/* 411 */               if (matcher.find()) {
                            try {
/* 413 */                       long contentLengthFromRange = (Long.parseLong(matcher.group(2)) - Long.parseLong(matcher.group(1))) + 1;
                                if (contentLength < 0) {
/* 418 */                           return contentLengthFromRange;
                                }
                                if (contentLength != contentLengthFromRange) {
/* 424 */                           Log.w(TAG, "Inconsistent headers [" + contentLengthHeader + "] [" + contentRangeHeader + "]");
/* 426 */                           return Math.max(contentLength, contentLengthFromRange);
                                }
/* 433 */                       return contentLength;
                            } catch (NumberFormatException e2) {
/* 429 */                       Log.e(TAG, "Unexpected Content-Range [" + contentRangeHeader + "]");
/* 433 */                       return contentLength;
                            }
                        }
/* 433 */               return contentLength;
                    }
/* 433 */           return contentLength;
                }
            }
