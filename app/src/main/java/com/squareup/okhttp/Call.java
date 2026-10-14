            package com.squareup.okhttp;

            import com.squareup.okhttp.internal.Internal;
            import com.squareup.okhttp.internal.NamedRunnable;
            import com.squareup.okhttp.internal.http.HttpEngine;
            import com.squareup.okhttp.internal.http.OkHeaders;
            import java.io.IOException;
            import java.net.MalformedURLException;
            import java.net.ProtocolException;
            import java.net.URL;
            import java.util.logging.Level;
            import okio.BufferedSink;
            import okio.BufferedSource;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class Call {
                volatile boolean canceled;
                private final OkHttpClient client;
                HttpEngine engine;
                private boolean executed;
                private int redirectionCount;
                private Request request;

/* 37 */        static /* synthetic */ Request access$100(Call x0) {
/* 38 */            return x0.request;
                }

/* 37 */        static /* synthetic */ Response access$200(Call x0) throws IOException {
/* 38 */            return x0.getResponse();
                }

/* 37 */        static /* synthetic */ String access$300(Call x0) {
/* 38 */            return x0.toLoggableString();
                }

/* 37 */        static /* synthetic */ OkHttpClient access$400(Call x0) {
/* 38 */            return x0.client;
                }

/* 49 */        protected Call(OkHttpClient client, Request request) {
/* 53 */            this.client = client.copyWithDefaults();
/* 54 */            this.request = request;
                }

/* 77 */        public Response execute() throws IOException {
                    synchronized (this) {
                        try {
/* 79 */                    if (this.executed) {
                                throw new IllegalStateException("Already Executed");
                            }
/* 80 */                    this.executed = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    try {
/* 83 */                this.client.getDispatcher().executed(this);
/* 84 */                Response result = getResponse();
/* 85 */                this.engine.releaseConnection();
/* 86 */                if (result == null) {
                            throw new IOException("Canceled");
                        }
                        this.client.getDispatcher().finished(this);
                        return result;
                    } catch (Throwable th2) {
                        this.client.getDispatcher().finished(this);
                        throw th2;
                    }
                }

/* 93 */        Object tag() {
/* 94 */            return this.request.tag();
                }

/* 110 */       public void enqueue(Callback responseCallback) {
                    synchronized (this) {
                        try {
/* 112 */                   if (this.executed) {
                                throw new IllegalStateException("Already Executed");
                            }
/* 113 */                   this.executed = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 115 */           this.client.getDispatcher().enqueue(new AsyncCall(this, responseCallback, null));
                }

/* 122 */       public void cancel() {
/* 123 */           this.canceled = true;
/* 124 */           if (this.engine != null) {
                        this.engine.disconnect();
                    }
                }

/* 127 */       public boolean isCanceled() {
/* 128 */           return this.canceled;
                }

/* 129 */       final class AsyncCall extends NamedRunnable {
                    private final Callback responseCallback;

/* 130 */           /* synthetic */ AsyncCall(Call x0, Callback x1, Object x2) {
/* 131 */               this(x1);
                    }

/* 133 */           private AsyncCall(Callback responseCallback) {
/* 135 */               super("OkHttp %s", Call.access$100(Call.this).urlString());
/* 136 */               this.responseCallback = responseCallback;
                    }

/* 139 */           String host() {
/* 140 */               return Call.access$100(Call.this).url().getHost();
                    }

/* 143 */           Request request() {
/* 144 */               return Call.access$100(Call.this);
                    }

/* 147 */           Object tag() {
/* 148 */               return Call.access$100(Call.this).tag();
                    }

/* 151 */           void cancel() {
/* 152 */               Call.this.cancel();
                    }

/* 155 */           Call get() {
/* 156 */               return Call.this;
                    }

                    @Override // com.squareup.okhttp.internal.NamedRunnable
/* 159 */           protected void execute() {
                        try {
                            try {
/* 162 */                       Response response = Call.access$200(Call.this);
/* 163 */                       if (Call.this.canceled) {
/* 165 */                           this.responseCallback.onFailure(Call.access$100(Call.this), new IOException("Canceled"));
                                } else {
/* 168 */                           Call.this.engine.releaseConnection();
/* 169 */                           this.responseCallback.onResponse(response);
                                }
/* 179 */                       Call.access$400(Call.this).getDispatcher().finished(this);
                            } catch (IOException e) {
/* 172 */                       if (0 != 0) {
/* 174 */                           Internal.logger.log(Level.INFO, "Callback failure for " + Call.access$300(Call.this), (Throwable) e);
                                } else {
/* 176 */                           this.responseCallback.onFailure(Call.access$100(Call.this), e);
                                }
/* 179 */                       Call.access$400(Call.this).getDispatcher().finished(this);
                            }
                        } catch (Throwable th) {
                            Call.access$400(Call.this).getDispatcher().finished(this);
                            throw th;
                        }
                    }
                }

/* 188 */       private String toLoggableString() {
/* 189 */           String string = this.canceled ? "canceled call" : "call";
                    try {
/* 191 */               String redactedUrl = new URL(this.request.url(), "/...").toString();
/* 192 */               return string + " to " + redactedUrl;
                    } catch (MalformedURLException e) {
/* 194 */               return string;
                    }
                }

/* 203 */       private Response getResponse() throws IOException {
/* 204 */           RequestBody body = this.request.body();
/* 206 */           if (body != null) {
/* 207 */               Request.Builder requestBuilder = this.request.newBuilder();
/* 209 */               MediaType contentType = body.contentType();
/* 210 */               if (contentType != null) {
/* 211 */                   requestBuilder.header("Content-Type", contentType.toString());
                        }
/* 214 */               long contentLength = body.contentLength();
                        if (contentLength != -1) {
/* 216 */                   requestBuilder.header("Content-Length", Long.toString(contentLength));
/* 217 */                   requestBuilder.removeHeader("Transfer-Encoding");
                        } else {
/* 219 */                   requestBuilder.header("Transfer-Encoding", "chunked");
/* 220 */                   requestBuilder.removeHeader("Content-Length");
                        }
/* 223 */               this.request = requestBuilder.build();
                    }
/* 227 */           this.engine = new HttpEngine(this.client, this.request, false, null, null, null, null);
/* 230 */           while (!this.canceled) {
                        try {
/* 233 */                   this.engine.sendRequest();
/* 235 */                   if (this.request.body() != null) {
/* 236 */                       BufferedSink sink = this.engine.getBufferedRequestBody();
/* 237 */                       this.request.body().writeTo(sink);
                            }
/* 240 */                   this.engine.readResponse();
/* 252 */                   Response response = this.engine.getResponse();
/* 253 */                   Request followUp = this.engine.followUpRequest();
/* 255 */                   if (followUp == null) {
/* 256 */                       this.engine.releaseConnection();
/* 257 */                       return response.newBuilder().body(new RealResponseBody(response, this.engine.getResponseBody())).build();
                            }
/* 262 */                   if (this.engine.getResponse().isRedirect()) {
                                int i = this.redirectionCount + 1;
                                this.redirectionCount = i;
                                if (i > 20) {
/* 263 */                           throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                                }
                            }
/* 266 */                   if (!this.engine.sameConnection(followUp.url())) {
/* 267 */                       this.engine.releaseConnection();
                            }
/* 270 */                   Connection connection = this.engine.close();
/* 271 */                   this.request = followUp;
/* 272 */                   this.engine = new HttpEngine(this.client, this.request, false, connection, null, null, response);
                        } catch (IOException e) {
/* 242 */                   HttpEngine retryEngine = this.engine.recover(e, null);
/* 243 */                   if (retryEngine != null) {
/* 244 */                       this.engine = retryEngine;
                            } else {
/* 249 */                       throw e;
                            }
                        }
                    }
                    return null;
                }

/* 278 */       private static class RealResponseBody extends ResponseBody {
                    private final Response response;
                    private final BufferedSource source;

/* 279 */           RealResponseBody(Response response, BufferedSource source) {
/* 281 */               this.response = response;
/* 282 */               this.source = source;
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 285 */           public MediaType contentType() {
/* 286 */               String contentType = this.response.header("Content-Type");
/* 287 */               if (contentType != null) {
                            return MediaType.parse(contentType);
                        }
                        return null;
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 290 */           public long contentLength() {
/* 291 */               return OkHeaders.contentLength(this.response);
                    }

                    @Override // com.squareup.okhttp.ResponseBody
/* 294 */           public BufferedSource source() {
/* 295 */               return this.source;
                    }
                }
            }
