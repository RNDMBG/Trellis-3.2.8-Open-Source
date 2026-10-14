            package co.vine.android.network.apache;

            import android.content.Context;
            import co.vine.android.client.VineAPI;
            import co.vine.android.network.FileNetworkEntity;
            import co.vine.android.network.NetworkOperation;
            import co.vine.android.network.NetworkOperationFactory;
            import co.vine.android.network.NetworkOperationReader;
            import co.vine.android.util.CommonUtil;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.util.List;
            import org.apache.http.HttpEntity;
            import org.apache.http.client.entity.UrlEncodedFormEntity;
            import org.apache.http.client.methods.HttpDelete;
            import org.apache.http.client.methods.HttpGet;
            import org.apache.http.client.methods.HttpPost;
            import org.apache.http.client.methods.HttpPut;
            import org.apache.http.entity.StringEntity;
            import org.apache.http.message.BasicNameValuePair;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class HttpOperationFactory implements NetworkOperationFactory<VineAPI> {
                private static final HttpOperationFactory INSTANCE = new HttpOperationFactory();












/* 36 */        public static HttpOperationFactory getInstance() {
/* 37 */            return INSTANCE;
                }

/* 39 */        private HttpOperationFactory() {
                }

/* 54 */        public HttpOperation createBasicAuthGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 55 */            HttpGet get = new HttpGet(url.toString());
/* 56 */            HttpOperation op = new HttpOperation(client, get, reader, api);
/* 57 */            api.addSessionKeyAuthHeader(op);
/* 58 */            return op;
                }

/* 63 */        public HttpOperation createBasicAuthGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
/* 64 */            HttpGet get = new HttpGet(url.toString());
/* 65 */            HttpOperation op = new HttpOperation(client, get, reader, api);
/* 66 */            VineAPI.addSessionKeyAuthHeader(op, key);
/* 67 */            return op;
                }

/* 83 */        public HttpOperation createBasicAuthPutRequest(HttpOperationClient client, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
/* 84 */            HttpPut put = new HttpPut(url.toString());
/* 87 */            if (params != null) {
                        try {
/* 89 */                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, IoUtils.UTF_8);
/* 90 */                    put.setEntity(entity);
                        } catch (UnsupportedEncodingException e) {
/* 92 */                    if (SLog.sLogsOn) {
/* 93 */                        e.printStackTrace();
                            }
                        }
                    }
/* 98 */            HttpOperation op = new HttpOperation(client, put, reader, api);
/* 99 */            api.addSessionKeyAuthHeader(op);
/* 100 */           return op;
                }

/* 118 */       public HttpOperation createBasicAuthPostRequest(HttpOperationClient client, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader, String... headers) {
/* 119 */           HttpOperation op = createPostRequest(client, url, params, reader, api);
/* 120 */           api.addSessionKeyAuthHeader(op);
                    for (String header : headers) {
/* 122 */               VineAPI.addContentTypeHeader(op, header);
                    }
/* 124 */           return op;
                }

/* 140 */       public HttpOperation createBasicAuthJsonPostRequest(HttpOperationClient client, StringBuilder url, VineAPI api, JSONObject body, NetworkOperationReader reader) {
/* 141 */           HttpOperation op = createPostRequest(client, url, body, reader, api);
/* 142 */           api.addSessionKeyAuthHeader(op);
/* 143 */           VineAPI.addContentTypeHeader(op, CommonUtil.ContentType.APPLICATION_JSON);
/* 144 */           return op;
                }

/* 158 */       public HttpOperation createBasicAuthDeleteRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 159 */           HttpDelete delete = new HttpDelete(url.toString());
/* 160 */           HttpOperation op = new HttpOperation(client, delete, reader, api);
/* 161 */           api.addSessionKeyAuthHeader(op);
/* 162 */           return op;
                }

/* 167 */       public HttpOperation createBasicAuthDeleteRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
/* 168 */           HttpDelete delete = new HttpDelete(url.toString());
/* 169 */           HttpOperation op = new HttpOperation(client, delete, reader, api);
/* 170 */           VineAPI.addSessionKeyAuthHeader(op, key);
/* 171 */           return op;
                }

/* 183 */       public HttpOperation createGetRequest(HttpOperationClient client, StringBuilder url, NetworkOperationReader reader, VineAPI api) {
/* 184 */           HttpGet get = new HttpGet(url.toString());
/* 185 */           return new HttpOperation(client, get, reader, api);
                }

/* 189 */       public HttpOperation createResourceGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 190 */           HttpGet get = new HttpGet(url.toString());
/* 191 */           HttpOperation op = new HttpOperation(client, get, reader, api);
/* 192 */           if (url.toString().startsWith("https")) {
/* 193 */               api.addSessionKeyAuthHeader(op);
                    }
/* 195 */           return op;
                }

/* 200 */       public HttpOperation createMediaPutRequest(HttpOperationClient client, StringBuilder url, NetworkOperationReader reader, HttpEntity entity, VineAPI api) {
/* 201 */           HttpPut put = new HttpPut(url.toString());
/* 202 */           if (entity != null) {
/* 203 */               put.setEntity(entity);
                    }
/* 205 */           HttpOperation op = new HttpOperation(client, put, reader, api);
/* 206 */           api.addSessionKeyAuthHeader(op);
/* 207 */           return op;
                }

/* 220 */       public HttpOperation createPostRequest(HttpOperationClient client, StringBuilder url, List<BasicNameValuePair> params, NetworkOperationReader reader, VineAPI api) {
/* 221 */           HttpPost post = new HttpPost(url.toString());
/* 224 */           if (params != null) {
                        try {
/* 226 */                   UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, IoUtils.UTF_8);
/* 227 */                   post.setEntity(entity);
                        } catch (UnsupportedEncodingException e) {
/* 229 */                   if (SLog.sLogsOn) {
/* 230 */                       e.printStackTrace();
                            }
                        }
                    }
/* 235 */           return new HttpOperation(client, post, reader, api);
                }

/* 248 */       private HttpOperation createPostRequest(HttpOperationClient client, CharSequence url, JSONObject body, NetworkOperationReader reader, VineAPI api) {
/* 249 */           HttpPost post = new HttpPost(url.toString());
/* 252 */           if (body != null) {
                        try {
/* 254 */                   String data = body.toString();
/* 255 */                   SLog.m465i("Posting Video:\n{}\n", data);
/* 256 */                   StringEntity entity = new StringEntity(data, IoUtils.UTF_8);
/* 257 */                   entity.setContentType(CommonUtil.ContentType.APPLICATION_JSON);
/* 258 */                   entity.setContentEncoding(IoUtils.UTF_8);
/* 259 */                   post.setEntity(entity);
                        } catch (IOException e) {
/* 261 */                   SLog.m459e("Failed to create Post Request.", (Throwable) e);
                        }
                    }
/* 265 */           return new HttpOperation(client, post, reader, api);
                }

/* 268 */       public HttpOperationClient getDefaultClient(Context context) {
/* 269 */           return ThreadedHttpOperationClient.getInstance(context);
                }

/* 273 */       public NetworkOperation createResourceGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 274 */           return createResourceGetRequest(getDefaultClient(context), url, api, reader);
                }

                /* JADX INFO: renamed from: createPostRequest, reason: avoid collision after fix types in other method */
/* 278 */       public NetworkOperation createPostRequest(Context context, StringBuilder url, List<BasicNameValuePair> params, NetworkOperationReader reader, VineAPI api) {
/* 279 */           return createPostRequest(getDefaultClient(context), url, params, reader, api);
                }

                /* JADX INFO: renamed from: createBasicAuthGetRequest, reason: avoid collision after fix types in other method */
/* 283 */       public NetworkOperation createBasicAuthGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
/* 284 */           return createBasicAuthGetRequest(getDefaultClient(context), url, api, reader, key);
                }

                /* JADX INFO: renamed from: createBasicAuthPutRequest, reason: avoid collision after fix types in other method */
/* 288 */       public NetworkOperation createBasicAuthPutRequest(Context context, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
/* 289 */           return createBasicAuthPutRequest(getDefaultClient(context), url, api, params, reader);
                }

                /* JADX INFO: renamed from: createBasicAuthGetRequest, reason: avoid collision after fix types in other method */
/* 293 */       public NetworkOperation createBasicAuthGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 294 */           return createBasicAuthGetRequest(getDefaultClient(context), url, api, reader);
                }

/* 298 */       public NetworkOperation createGetRequest(Context context, StringBuilder url, NetworkOperationReader vp, VineAPI api) {
/* 299 */           return createGetRequest(getDefaultClient(context), url, vp, api);
                }

                /* JADX INFO: renamed from: createBasicAuthPostRequest, reason: avoid collision after fix types in other method */
/* 303 */       public NetworkOperation createBasicAuthPostRequest(Context context, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
/* 304 */           return createBasicAuthPostRequest(getDefaultClient(context), url, api, params, reader, new String[0]);
                }

                /* JADX INFO: renamed from: createBasicAuthDeleteRequest, reason: avoid collision after fix types in other method */
/* 308 */       public NetworkOperation createBasicAuthDeleteRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
/* 309 */           return createBasicAuthDeleteRequest(getDefaultClient(context), url, api, reader);
                }

                /* JADX INFO: renamed from: createBasicAuthJsonPostRequest, reason: avoid collision after fix types in other method */
/* 313 */       public NetworkOperation createBasicAuthJsonPostRequest(Context context, StringBuilder url, VineAPI api, JSONObject postBody, NetworkOperationReader reader) {
/* 314 */           return createBasicAuthJsonPostRequest(getDefaultClient(context), url, api, postBody, reader);
                }

                /* JADX INFO: renamed from: createBasicAuthDeleteRequest, reason: avoid collision after fix types in other method */
/* 318 */       public NetworkOperation createBasicAuthDeleteRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader, String cachedKey) {
/* 319 */           return createBasicAuthDeleteRequest(getDefaultClient(context), url, api, reader, cachedKey);
                }

                /* JADX INFO: renamed from: createMediaPutRequest, reason: avoid collision after fix types in other method */
/* 323 */       public NetworkOperation createMediaPutRequest(Context context, StringBuilder url, NetworkOperationReader reader, FileNetworkEntity entity, VineAPI api) {
/* 324 */           return createMediaPutRequest(getDefaultClient(context), url, reader, (HttpEntity) entity, api);
                }
            }
