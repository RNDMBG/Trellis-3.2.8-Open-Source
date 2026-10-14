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
            
            
    public class Call {
                volatile boolean canceled;
                private final OkHttpClient client;
                HttpEngine engine;
                private boolean executed;
                private int redirectionCount;
                private Request request;

        static  Request access$100(Call x0) {
            return x0.request;
                }

        static  Response access$200(Call x0) throws IOException {
            return x0.getResponse();
                }

        static  String access$300(Call x0) {
            return x0.toLoggableString();
                }

        static  OkHttpClient access$400(Call x0) {
            return x0.client;
                }

        protected Call(OkHttpClient client, Request request) {
            this.client = client.copyWithDefaults();
            this.request = request;
                }

        public Response execute() throws IOException {
                    synchronized (this) {
                        try {
                    if (this.executed) {
                                throw new IllegalStateException("Already Executed");
                            }
                    this.executed = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    try {
                this.client.getDispatcher().executed(this);
                Response result = getResponse();
                this.engine.releaseConnection();
                if (result == null) {
                            throw new IOException("Canceled");
                        }
                        this.client.getDispatcher().finished(this);
                        return result;
                    } catch (Throwable th2) {
                        this.client.getDispatcher().finished(this);
                        throw th2;
                    }
                }

        Object tag() {
            return this.request.tag();
                }

       public void enqueue(Callback responseCallback) {
                    synchronized (this) {
                        try {
                   if (this.executed) {
                                throw new IllegalStateException("Already Executed");
                            }
                   this.executed = true;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           this.client.getDispatcher().enqueue(new AsyncCall(this, responseCallback, null));
                }

       public void cancel() {
           this.canceled = true;
           if (this.engine != null) {
                        this.engine.disconnect();
                    }
                }

       public boolean isCanceled() {
           return this.canceled;
                }

       final class AsyncCall extends NamedRunnable {
                    private final Callback responseCallback;

            AsyncCall(Call x0, Callback x1, Object x2) {
               this(x1);
                    }

           private AsyncCall(Callback responseCallback) {
               super("OkHttp %s", Call.access$100(Call.this).urlString());
               this.responseCallback = responseCallback;
                    }

           String host() {
               return Call.access$100(Call.this).url().getHost();
                    }

           Request request() {
               return Call.access$100(Call.this);
                    }

           Object tag() {
               return Call.access$100(Call.this).tag();
                    }

           void cancel() {
               Call.this.cancel();
                    }

           Call get() {
               return Call.this;
                    }

                    @Override 
           protected void execute() {
                        try {
                            try {
                       Response response = Call.access$200(Call.this);
                       if (Call.this.canceled) {
                           this.responseCallback.onFailure(Call.access$100(Call.this), new IOException("Canceled"));
                                } else {
                           Call.this.engine.releaseConnection();
                           this.responseCallback.onResponse(response);
                                }
                       Call.access$400(Call.this).getDispatcher().finished(this);
                            } catch (IOException e) {
                       if (0 != 0) {
                           Internal.logger.log(Level.INFO, "Callback failure for " + Call.access$300(Call.this), (Throwable) e);
                                } else {
                           this.responseCallback.onFailure(Call.access$100(Call.this), e);
                                }
                       Call.access$400(Call.this).getDispatcher().finished(this);
                            }
                        } catch (Throwable th) {
                            Call.access$400(Call.this).getDispatcher().finished(this);
                            throw th;
                        }
                    }
                }

       private String toLoggableString() {
           String string = this.canceled ? "canceled call" : "call";
                    try {
               String redactedUrl = new URL(this.request.url(), "/...").toString();
               return string + " to " + redactedUrl;
                    } catch (MalformedURLException e) {
               return string;
                    }
                }

       private Response getResponse() throws IOException {
           RequestBody body = this.request.body();
           if (body != null) {
               Request.Builder requestBuilder = this.request.newBuilder();
               MediaType contentType = body.contentType();
               if (contentType != null) {
                   requestBuilder.header("Content-Type", contentType.toString());
                        }
               long contentLength = body.contentLength();
                        if (contentLength != -1) {
                   requestBuilder.header("Content-Length", Long.toString(contentLength));
                   requestBuilder.removeHeader("Transfer-Encoding");
                        } else {
                   requestBuilder.header("Transfer-Encoding", "chunked");
                   requestBuilder.removeHeader("Content-Length");
                        }
               this.request = requestBuilder.build();
                    }
           this.engine = new HttpEngine(this.client, this.request, false, null, null, null, null);
           while (!this.canceled) {
                        try {
                   this.engine.sendRequest();
                   if (this.request.body() != null) {
                       BufferedSink sink = this.engine.getBufferedRequestBody();
                       this.request.body().writeTo(sink);
                            }
                   this.engine.readResponse();
                   Response response = this.engine.getResponse();
                   Request followUp = this.engine.followUpRequest();
                   if (followUp == null) {
                       this.engine.releaseConnection();
                       return response.newBuilder().body(new RealResponseBody(response, this.engine.getResponseBody())).build();
                            }
                   if (this.engine.getResponse().isRedirect()) {
                                int i = this.redirectionCount + 1;
                                this.redirectionCount = i;
                                if (i > 20) {
                           throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                                }
                            }
                   if (!this.engine.sameConnection(followUp.url())) {
                       this.engine.releaseConnection();
                            }
                   Connection connection = this.engine.close();
                   this.request = followUp;
                   this.engine = new HttpEngine(this.client, this.request, false, connection, null, null, response);
                        } catch (IOException e) {
                   HttpEngine retryEngine = this.engine.recover(e, null);
                   if (retryEngine != null) {
                       this.engine = retryEngine;
                            } else {
                       throw e;
                            }
                        }
                    }
                    return null;
                }

       private static class RealResponseBody extends ResponseBody {
                    private final Response response;
                    private final BufferedSource source;

           RealResponseBody(Response response, BufferedSource source) {
               this.response = response;
               this.source = source;
                    }

                    @Override 
           public MediaType contentType() {
               String contentType = this.response.header("Content-Type");
               if (contentType != null) {
                            return MediaType.parse(contentType);
                        }
                        return null;
                    }

                    @Override 
           public long contentLength() {
               return OkHeaders.contentLength(this.response);
                    }

                    @Override 
           public BufferedSource source() {
               return this.source;
                    }
                }
            }
