            package com.twitter.android.sdk;

            import android.net.Uri;
            import android.text.TextUtils;
            import android.util.Base64;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.net.HttpURLConnection;
            import java.net.URI;
            import java.net.URISyntaxException;
            import java.net.URLDecoder;
            import java.net.URLEncoder;
            import java.security.InvalidKeyException;
            import java.security.NoSuchAlgorithmException;
            import java.security.SecureRandom;
            import java.util.Map;
            import java.util.TreeMap;
            import javax.crypto.Mac;
            import javax.crypto.SecretKey;
            import javax.crypto.spec.SecretKeySpec;
            import org.apache.http.Header;
            import org.apache.http.HttpEntity;
            import org.apache.http.client.methods.HttpRequestBase;
            import org.apache.http.util.EntityUtils;
            import org.scribe.model.OAuthConstants;
            
            
    public class TwitterAuthHelper {
                private static final String FORM_URLENCODED_HEADER = "application/x-www-form-urlencoded";
                private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
                private static final String OAUTH_VERSION = "1.0";
                private static final SecureRandom RAND = new SecureRandom();
                private static final String REALM = "https://api.twitter.com/";
                private static final String UTF8 = "UTF8";

        private TwitterAuthHelper() {
                }

        public static void attachHeader(HttpRequestBase httpReq, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) {
            String authHeader = makeAuthorizationHeaderValue(httpReq.getURI(), httpReq.getMethod(), token, tokenSecret, consumerKey, consumerSecret, timestampMillis, entity);
            httpReq.setHeader(OAuthConstants.HEADER, authHeader);
                }

        public static void attachHeader(HttpURLConnection conn, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) throws URISyntaxException {
            String authHeader = makeAuthorizationHeaderValue(conn.getURL().toURI(), conn.getRequestMethod(), token, tokenSecret, consumerKey, consumerSecret, timestampMillis, entity);
            conn.addRequestProperty(OAuthConstants.HEADER, authHeader);
                }

        public static String encode(String text) {
                    return text == null ? "" : Uri.encode(text);
                }

        public static String urlFromRequest(URI u) {
            return u.getScheme() + "://" + u.getHost() + u.getPath();
                }

        public static String makeSignatureBase(URI u, String httpMethod, String token, String nonce, String timestamp, String consumerKey, HttpEntity entity) {
                    Header contentType;
            String encodedQueryString = u.getRawQuery();
            StringBuilder sb = new StringBuilder();
            if (encodedQueryString != null) {
                sb.append(encodedQueryString);
                    }
            if (entity != null && (contentType = entity.getContentType()) != null && "application/x-www-form-urlencoded".equals(contentType.getValue())) {
                        try {
                    String entityVal = EntityUtils.toString(entity);
                    if (!TextUtils.isEmpty(sb)) {
                        sb.append("&");
                            }
                    sb.append(entityVal);
                        } catch (IOException e) {
                        }
                    }
           TreeMap<String, String> params = parseForm(sb.toString(), true);
           params.put(OAuthConstants.CONSUMER_KEY, consumerKey);
           params.put(OAuthConstants.NONCE, nonce);
           params.put(OAuthConstants.SIGN_METHOD, OAUTH_SIGNATURE_METHOD);
           params.put(OAuthConstants.TIMESTAMP, timestamp);
           params.put(OAuthConstants.VERSION, "1.0");
           if (token != null) {
               params.put(OAuthConstants.TOKEN, token);
                    }
           StringBuilder b = new StringBuilder(httpMethod);
           String url = urlFromRequest(u);
           b.append("&").append(encode(url)).append("&");
           int numParams = params.size();
           int current = 0;
                    for (Map.Entry<String, String> entry : params.entrySet()) {
               b.append(encode(encode(entry.getKey()))).append("%3D").append(encode(encode(entry.getValue())));
               current++;
               if (current < numParams) {
                   b.append("%26");
                        }
                    }
           return b.toString();
                }

       public static String makeAuthorizationHeaderValue(String signature, String consumerKey, String nonce, String timestamp, String token) {
           if (token == null) {
               return String.format("OAuth realm=\"%s\", oauth_version=\"%s\", oauth_nonce=\"%s\", oauth_timestamp=\"%s\", oauth_signature=\"%s\", oauth_consumer_key=\"%s\", oauth_signature_method=\"%s\"", REALM, "1.0", nonce, timestamp, signature, consumerKey, OAUTH_SIGNATURE_METHOD);
                    }
           return String.format("OAuth realm=\"%s\", oauth_version=\"%s\", oauth_token=\"%s\", oauth_nonce=\"%s\", oauth_timestamp=\"%s\", oauth_signature=\"%s\", oauth_consumer_key=\"%s\", oauth_signature_method=\"%s\"", REALM, "1.0", token, nonce, timestamp, signature, consumerKey, OAUTH_SIGNATURE_METHOD);
                }

       public static String sign(String base, String consumerSecret, String tokenSecret) {
           if (tokenSecret == null) {
               tokenSecret = "";
                    }
                    try {
               String key = urlencode(consumerSecret, UTF8) + '&' + urlencode(tokenSecret, UTF8);
               byte[] keyBytes = key.getBytes(UTF8);
               SecretKey skey = new SecretKeySpec(keyBytes, "HmacSHA1");
               Mac m = Mac.getInstance("HmacSHA1");
               m.init(skey);
               byte[] baseBytes = base.getBytes(UTF8);
               byte[] sigBytes = m.doFinal(baseBytes);
               String signature = new String(Base64.encode(sigBytes, 0), UTF8);
               return urlencode(signature, UTF8);
                    } catch (UnsupportedEncodingException e) {
               return "";
                    } catch (InvalidKeyException e2) {
               return "";
                    } catch (NoSuchAlgorithmException e3) {
               return "";
                    }
                }

       public static TreeMap<String, String> parseForm(String form, boolean decode) {
           TreeMap<String, String> m = new TreeMap<>();
           if (form != null) {
               String[] arr$ = form.split("&");
                        for (String nvps : arr$) {
                   String[] nvp = nvps.split("=");
                   if (nvp.length == 2) {
                       if (decode) {
                           m.put(urldecode(nvp[0], UTF8), urldecode(nvp[1], UTF8));
                                } else {
                           m.put(nvp[0], nvp[1]);
                                }
                            } else if (!TextUtils.isEmpty(nvp[0])) {
                       if (decode) {
                           m.put(urldecode(nvp[0], UTF8), "");
                                } else {
                           m.put(nvp[0], "");
                                }
                            }
                        }
                    }
           return m;
                }

       public static String makeAuthorizationHeaderValue(URI uri, String httpMethod, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) {
           String nonce = String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(RAND.nextLong()));
           long secondsFromEpoch = timestampMillis / 1000;
           String timestamp = String.valueOf(secondsFromEpoch);
           String sbs = makeSignatureBase(uri, httpMethod, token, nonce, timestamp, consumerKey, entity);
           String signature = sign(sbs, consumerSecret, tokenSecret);
           return makeAuthorizationHeaderValue(signature, consumerKey, nonce, timestamp, token);
                }

       public static String urlencode(String s, String encoding) {
           if (s == null) {
               return "";
                    }
                    try {
                        return URLEncoder.encode(s, encoding);
                    } catch (UnsupportedEncodingException unlikely) {
               throw new RuntimeException(unlikely.getMessage(), unlikely);
                    }
                }

       public static String urldecode(String s, String encoding) {
           if (s == null) {
               return "";
                    }
                    try {
                        return URLDecoder.decode(s, encoding);
                    } catch (UnsupportedEncodingException unlikely) {
               throw new RuntimeException(unlikely.getMessage(), unlikely);
                    }
                }
            }
