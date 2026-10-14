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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 37 */    public class TwitterAuthHelper {
                private static final String FORM_URLENCODED_HEADER = "application/x-www-form-urlencoded";
                private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
                private static final String OAUTH_VERSION = "1.0";
                private static final SecureRandom RAND = new SecureRandom();
                private static final String REALM = "https://api.twitter.com/";
                private static final String UTF8 = "UTF8";

/* 38 */        private TwitterAuthHelper() {
                }

/* 51 */        public static void attachHeader(HttpRequestBase httpReq, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) {
/* 52 */            String authHeader = makeAuthorizationHeaderValue(httpReq.getURI(), httpReq.getMethod(), token, tokenSecret, consumerKey, consumerSecret, timestampMillis, entity);
/* 55 */            httpReq.setHeader(OAuthConstants.HEADER, authHeader);
                }

/* 60 */        public static void attachHeader(HttpURLConnection conn, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) throws URISyntaxException {
/* 61 */            String authHeader = makeAuthorizationHeaderValue(conn.getURL().toURI(), conn.getRequestMethod(), token, tokenSecret, consumerKey, consumerSecret, timestampMillis, entity);
/* 64 */            conn.addRequestProperty(OAuthConstants.HEADER, authHeader);
                }

/* 67 */        public static String encode(String text) {
                    return text == null ? "" : Uri.encode(text);
                }

/* 74 */        public static String urlFromRequest(URI u) {
/* 75 */            return u.getScheme() + "://" + u.getHost() + u.getPath();
                }

/* 80 */        public static String makeSignatureBase(URI u, String httpMethod, String token, String nonce, String timestamp, String consumerKey, HttpEntity entity) {
                    Header contentType;
/* 81 */            String encodedQueryString = u.getRawQuery();
/* 82 */            StringBuilder sb = new StringBuilder();
/* 84 */            if (encodedQueryString != null) {
/* 85 */                sb.append(encodedQueryString);
                    }
/* 88 */            if (entity != null && (contentType = entity.getContentType()) != null && "application/x-www-form-urlencoded".equals(contentType.getValue())) {
                        try {
/* 93 */                    String entityVal = EntityUtils.toString(entity);
/* 94 */                    if (!TextUtils.isEmpty(sb)) {
/* 95 */                        sb.append("&");
                            }
/* 97 */                    sb.append(entityVal);
                        } catch (IOException e) {
                        }
                    }
/* 103 */           TreeMap<String, String> params = parseForm(sb.toString(), true);
/* 104 */           params.put(OAuthConstants.CONSUMER_KEY, consumerKey);
/* 105 */           params.put(OAuthConstants.NONCE, nonce);
/* 106 */           params.put(OAuthConstants.SIGN_METHOD, OAUTH_SIGNATURE_METHOD);
/* 107 */           params.put(OAuthConstants.TIMESTAMP, timestamp);
/* 108 */           params.put(OAuthConstants.VERSION, "1.0");
/* 109 */           if (token != null) {
/* 110 */               params.put(OAuthConstants.TOKEN, token);
                    }
/* 113 */           StringBuilder b = new StringBuilder(httpMethod);
/* 114 */           String url = urlFromRequest(u);
/* 115 */           b.append("&").append(encode(url)).append("&");
/* 116 */           int numParams = params.size();
/* 117 */           int current = 0;
                    for (Map.Entry<String, String> entry : params.entrySet()) {
/* 119 */               b.append(encode(encode(entry.getKey()))).append("%3D").append(encode(encode(entry.getValue())));
/* 121 */               current++;
/* 122 */               if (current < numParams) {
/* 123 */                   b.append("%26");
                        }
                    }
/* 126 */           return b.toString();
                }

/* 130 */       public static String makeAuthorizationHeaderValue(String signature, String consumerKey, String nonce, String timestamp, String token) {
/* 131 */           if (token == null) {
/* 133 */               return String.format("OAuth realm=\"%s\", oauth_version=\"%s\", oauth_nonce=\"%s\", oauth_timestamp=\"%s\", oauth_signature=\"%s\", oauth_consumer_key=\"%s\", oauth_signature_method=\"%s\"", REALM, "1.0", nonce, timestamp, signature, consumerKey, OAUTH_SIGNATURE_METHOD);
                    }
/* 138 */           return String.format("OAuth realm=\"%s\", oauth_version=\"%s\", oauth_token=\"%s\", oauth_nonce=\"%s\", oauth_timestamp=\"%s\", oauth_signature=\"%s\", oauth_consumer_key=\"%s\", oauth_signature_method=\"%s\"", REALM, "1.0", token, nonce, timestamp, signature, consumerKey, OAUTH_SIGNATURE_METHOD);
                }

/* 145 */       public static String sign(String base, String consumerSecret, String tokenSecret) {
/* 146 */           if (tokenSecret == null) {
/* 147 */               tokenSecret = "";
                    }
                    try {
/* 149 */               String key = urlencode(consumerSecret, UTF8) + '&' + urlencode(tokenSecret, UTF8);
/* 150 */               byte[] keyBytes = key.getBytes(UTF8);
/* 151 */               SecretKey skey = new SecretKeySpec(keyBytes, "HmacSHA1");
/* 152 */               Mac m = Mac.getInstance("HmacSHA1");
/* 153 */               m.init(skey);
/* 155 */               byte[] baseBytes = base.getBytes(UTF8);
/* 156 */               byte[] sigBytes = m.doFinal(baseBytes);
/* 157 */               String signature = new String(Base64.encode(sigBytes, 0), UTF8);
/* 159 */               return urlencode(signature, UTF8);
                    } catch (UnsupportedEncodingException e) {
/* 165 */               return "";
                    } catch (InvalidKeyException e2) {
/* 161 */               return "";
                    } catch (NoSuchAlgorithmException e3) {
/* 163 */               return "";
                    }
                }

/* 169 */       public static TreeMap<String, String> parseForm(String form, boolean decode) {
/* 170 */           TreeMap<String, String> m = new TreeMap<>();
/* 171 */           if (form != null) {
/* 172 */               String[] arr$ = form.split("&");
                        for (String nvps : arr$) {
/* 173 */                   String[] nvp = nvps.split("=");
/* 174 */                   if (nvp.length == 2) {
/* 175 */                       if (decode) {
/* 176 */                           m.put(urldecode(nvp[0], UTF8), urldecode(nvp[1], UTF8));
                                } else {
/* 178 */                           m.put(nvp[0], nvp[1]);
                                }
                            } else if (!TextUtils.isEmpty(nvp[0])) {
/* 182 */                       if (decode) {
/* 183 */                           m.put(urldecode(nvp[0], UTF8), "");
                                } else {
/* 185 */                           m.put(nvp[0], "");
                                }
                            }
                        }
                    }
/* 191 */           return m;
                }

/* 197 */       public static String makeAuthorizationHeaderValue(URI uri, String httpMethod, String token, String tokenSecret, String consumerKey, String consumerSecret, long timestampMillis, HttpEntity entity) {
/* 198 */           String nonce = String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(RAND.nextLong()));
/* 200 */           long secondsFromEpoch = timestampMillis / 1000;
/* 201 */           String timestamp = String.valueOf(secondsFromEpoch);
/* 202 */           String sbs = makeSignatureBase(uri, httpMethod, token, nonce, timestamp, consumerKey, entity);
/* 204 */           String signature = sign(sbs, consumerSecret, tokenSecret);
/* 205 */           return makeAuthorizationHeaderValue(signature, consumerKey, nonce, timestamp, token);
                }

/* 209 */       public static String urlencode(String s, String encoding) {
/* 210 */           if (s == null) {
/* 211 */               return "";
                    }
                    try {
                        return URLEncoder.encode(s, encoding);
                    } catch (UnsupportedEncodingException unlikely) {
/* 215 */               throw new RuntimeException(unlikely.getMessage(), unlikely);
                    }
                }

/* 220 */       public static String urldecode(String s, String encoding) {
/* 221 */           if (s == null) {
/* 222 */               return "";
                    }
                    try {
                        return URLDecoder.decode(s, encoding);
                    } catch (UnsupportedEncodingException unlikely) {
/* 226 */               throw new RuntimeException(unlikely.getMessage(), unlikely);
                    }
                }
            }
