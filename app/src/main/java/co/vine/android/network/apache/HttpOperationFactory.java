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
            
            
    public class HttpOperationFactory implements NetworkOperationFactory<VineAPI> {
                private static final HttpOperationFactory INSTANCE = new HttpOperationFactory();












        public static HttpOperationFactory getInstance() {
            return INSTANCE;
                }

        private HttpOperationFactory() {
                }

        public HttpOperation createBasicAuthGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
            HttpGet get = new HttpGet(url.toString());
            HttpOperation op = new HttpOperation(client, get, reader, api);
            api.addSessionKeyAuthHeader(op);
            return op;
                }

        public HttpOperation createBasicAuthGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
            HttpGet get = new HttpGet(url.toString());
            HttpOperation op = new HttpOperation(client, get, reader, api);
            VineAPI.addSessionKeyAuthHeader(op, key);
            return op;
                }

        public HttpOperation createBasicAuthPutRequest(HttpOperationClient client, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
            HttpPut put = new HttpPut(url.toString());
            if (params != null) {
                        try {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, IoUtils.UTF_8);
                    put.setEntity(entity);
                        } catch (UnsupportedEncodingException e) {
                    if (SLog.sLogsOn) {
                        e.printStackTrace();
                            }
                        }
                    }
            HttpOperation op = new HttpOperation(client, put, reader, api);
            api.addSessionKeyAuthHeader(op);
           return op;
                }

       public HttpOperation createBasicAuthPostRequest(HttpOperationClient client, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader, String... headers) {
           HttpOperation op = createPostRequest(client, url, params, reader, api);
           api.addSessionKeyAuthHeader(op);
                    for (String header : headers) {
               VineAPI.addContentTypeHeader(op, header);
                    }
           return op;
                }

       public HttpOperation createBasicAuthJsonPostRequest(HttpOperationClient client, StringBuilder url, VineAPI api, JSONObject body, NetworkOperationReader reader) {
           HttpOperation op = createPostRequest(client, url, body, reader, api);
           api.addSessionKeyAuthHeader(op);
           VineAPI.addContentTypeHeader(op, CommonUtil.ContentType.APPLICATION_JSON);
           return op;
                }

       public HttpOperation createBasicAuthDeleteRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
           HttpDelete delete = new HttpDelete(url.toString());
           HttpOperation op = new HttpOperation(client, delete, reader, api);
           api.addSessionKeyAuthHeader(op);
           return op;
                }

       public HttpOperation createBasicAuthDeleteRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
           HttpDelete delete = new HttpDelete(url.toString());
           HttpOperation op = new HttpOperation(client, delete, reader, api);
           VineAPI.addSessionKeyAuthHeader(op, key);
           return op;
                }

       public HttpOperation createGetRequest(HttpOperationClient client, StringBuilder url, NetworkOperationReader reader, VineAPI api) {
           HttpGet get = new HttpGet(url.toString());
           return new HttpOperation(client, get, reader, api);
                }

       public HttpOperation createResourceGetRequest(HttpOperationClient client, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
           HttpGet get = new HttpGet(url.toString());
           HttpOperation op = new HttpOperation(client, get, reader, api);
           if (url.toString().startsWith("https")) {
               api.addSessionKeyAuthHeader(op);
                    }
           return op;
                }

       public HttpOperation createMediaPutRequest(HttpOperationClient client, StringBuilder url, NetworkOperationReader reader, HttpEntity entity, VineAPI api) {
           HttpPut put = new HttpPut(url.toString());
           if (entity != null) {
               put.setEntity(entity);
                    }
           HttpOperation op = new HttpOperation(client, put, reader, api);
           api.addSessionKeyAuthHeader(op);
           return op;
                }

       public HttpOperation createPostRequest(HttpOperationClient client, StringBuilder url, List<BasicNameValuePair> params, NetworkOperationReader reader, VineAPI api) {
           HttpPost post = new HttpPost(url.toString());
           if (params != null) {
                        try {
                   UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, IoUtils.UTF_8);
                   post.setEntity(entity);
                        } catch (UnsupportedEncodingException e) {
                   if (SLog.sLogsOn) {
                       e.printStackTrace();
                            }
                        }
                    }
           return new HttpOperation(client, post, reader, api);
                }

       private HttpOperation createPostRequest(HttpOperationClient client, CharSequence url, JSONObject body, NetworkOperationReader reader, VineAPI api) {
           HttpPost post = new HttpPost(url.toString());
           if (body != null) {
                        try {
                   String data = body.toString();
                   SLog.m465i("Posting Video:\n{}\n", data);
                   StringEntity entity = new StringEntity(data, IoUtils.UTF_8);
                   entity.setContentType(CommonUtil.ContentType.APPLICATION_JSON);
                   entity.setContentEncoding(IoUtils.UTF_8);
                   post.setEntity(entity);
                        } catch (IOException e) {
                   SLog.m459e("Failed to create Post Request.", (Throwable) e);
                        }
                    }
           return new HttpOperation(client, post, reader, api);
                }

       public HttpOperationClient getDefaultClient(Context context) {
           return ThreadedHttpOperationClient.getInstance(context);
                }

       public NetworkOperation createResourceGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
           return createResourceGetRequest(getDefaultClient(context), url, api, reader);
                }

                
       public NetworkOperation createPostRequest(Context context, StringBuilder url, List<BasicNameValuePair> params, NetworkOperationReader reader, VineAPI api) {
           return createPostRequest(getDefaultClient(context), url, params, reader, api);
                }

                
       public NetworkOperation createBasicAuthGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader, String key) {
           return createBasicAuthGetRequest(getDefaultClient(context), url, api, reader, key);
                }

                
       public NetworkOperation createBasicAuthPutRequest(Context context, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
           return createBasicAuthPutRequest(getDefaultClient(context), url, api, params, reader);
                }

                
       public NetworkOperation createBasicAuthGetRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
           return createBasicAuthGetRequest(getDefaultClient(context), url, api, reader);
                }

       public NetworkOperation createGetRequest(Context context, StringBuilder url, NetworkOperationReader vp, VineAPI api) {
           return createGetRequest(getDefaultClient(context), url, vp, api);
                }

                
       public NetworkOperation createBasicAuthPostRequest(Context context, StringBuilder url, VineAPI api, List<BasicNameValuePair> params, NetworkOperationReader reader) {
           return createBasicAuthPostRequest(getDefaultClient(context), url, api, params, reader, new String[0]);
                }

                
       public NetworkOperation createBasicAuthDeleteRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader) {
           return createBasicAuthDeleteRequest(getDefaultClient(context), url, api, reader);
                }

                
       public NetworkOperation createBasicAuthJsonPostRequest(Context context, StringBuilder url, VineAPI api, JSONObject postBody, NetworkOperationReader reader) {
           return createBasicAuthJsonPostRequest(getDefaultClient(context), url, api, postBody, reader);
                }

                
       public NetworkOperation createBasicAuthDeleteRequest(Context context, StringBuilder url, VineAPI api, NetworkOperationReader reader, String cachedKey) {
           return createBasicAuthDeleteRequest(getDefaultClient(context), url, api, reader, cachedKey);
                }

                
       public NetworkOperation createMediaPutRequest(Context context, StringBuilder url, NetworkOperationReader reader, FileNetworkEntity entity, VineAPI api) {
           return createMediaPutRequest(getDefaultClient(context), url, reader, (HttpEntity) entity, api);
                }
            }
