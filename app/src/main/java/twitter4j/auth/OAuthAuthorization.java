            package twitter4j.auth;

            import android.util.Log;
            import co.vine.android.client.VineAPI;
            import co.vine.android.embed.BuildConfig;
            import com.edisonwang.android.slog.SLog;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Serializable;
            import java.io.UnsupportedEncodingException;
            import java.net.URLDecoder;
            import java.security.InvalidKeyException;
            import java.security.NoSuchAlgorithmException;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.List;
            import java.util.Random;
            import javax.crypto.Mac;
            import javax.crypto.spec.SecretKeySpec;
            import org.scribe.model.OAuthConstants;
            import twitter4j.TwitterException;
            import twitter4j.conf.Configuration;
            import twitter4j.internal.http.BASE64Encoder;
            import twitter4j.internal.http.HttpClientWrapper;
            import twitter4j.internal.http.HttpParameter;
            import twitter4j.internal.http.HttpRequest;
            import twitter4j.internal.util.z_T4JInternalStringUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 66 */    public class OAuthAuthorization implements Authorization, Serializable, OAuthSupport {
                private static final String HMAC_SHA1 = "HmacSHA1";
                private static final HttpParameter OAUTH_SIGNATURE_METHOD = new HttpParameter(OAuthConstants.SIGN_METHOD, "HMAC-SHA1");
                private static Random RAND = new Random();
                private static final String TAG = "t4jOAuthAuth";
                private static transient HttpClientWrapper http = null;
                private static final long serialVersionUID = -4368426677157998618L;
                private final Configuration conf;
                private String consumerSecret;
                private String consumerKey = "";
                private String realm = null;
                private OAuthToken oauthToken = null;

/* 67 */        public OAuthAuthorization(Configuration conf) {
/* 69 */            this.conf = conf;
/* 70 */            http = new HttpClientWrapper(conf);
/* 71 */            setOAuthConsumer(conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret());
/* 72 */            if (conf.getOAuthAccessToken() != null && conf.getOAuthAccessTokenSecret() != null) {
/* 73 */                setOAuthAccessToken(new AccessToken(conf.getOAuthAccessToken(), conf.getOAuthAccessTokenSecret()));
                    }
                }

                @Override // twitter4j.auth.Authorization
/* 79 */        public String getAuthorizationHeader(HttpRequest req) {
/* 80 */            return generateAuthorizationHeader(req.getMethod().name(), req.getURL(), req.getParameters(), this.oauthToken);
                }

/* 83 */        private void ensureTokenIsAvailable() {
/* 84 */            if (this.oauthToken == null) {
/* 85 */                throw new IllegalStateException("No Token available.");
                    }
                }

                @Override // twitter4j.auth.Authorization
/* 93 */        public boolean isEnabled() {
                    return this.oauthToken != null && (this.oauthToken instanceof AccessToken);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 103 */       public RequestToken getOAuthRequestToken() throws TwitterException {
/* 104 */           return getOAuthRequestToken(null, null);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 111 */       public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException {
/* 112 */           return getOAuthRequestToken(callbackURL, null);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 119 */       public RequestToken getOAuthRequestToken(String callbackURL, String xAuthAccessType) throws TwitterException {
/* 120 */           if (this.oauthToken instanceof AccessToken) {
/* 121 */               throw new IllegalStateException("Access token already available.");
                    }
/* 123 */           List<HttpParameter> params = new ArrayList<>();
/* 124 */           if (callbackURL != null) {
/* 125 */               params.add(new HttpParameter(OAuthConstants.CALLBACK, callbackURL));
                    }
/* 127 */           if (xAuthAccessType != null) {
/* 128 */               params.add(new HttpParameter("x_auth_access_type", xAuthAccessType));
                    }
/* 130 */           this.oauthToken = new RequestToken(http.post(this.conf.getOAuthRequestTokenURL(), (HttpParameter[]) params.toArray(new HttpParameter[params.size()]), this), this);
/* 131 */           return (RequestToken) this.oauthToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 138 */       public AccessToken getOAuthAccessToken() throws TwitterException {
/* 139 */           ensureTokenIsAvailable();
/* 140 */           if (this.oauthToken instanceof AccessToken) {
/* 141 */               return (AccessToken) this.oauthToken;
                    }
/* 143 */           this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), this));
/* 144 */           return (AccessToken) this.oauthToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 151 */       public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
/* 152 */           ensureTokenIsAvailable();
/* 153 */           this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), new HttpParameter[]{new HttpParameter(OAuthConstants.VERIFIER, oauthVerifier)}, this));
/* 155 */           return (AccessToken) this.oauthToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 162 */       public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
/* 163 */           this.oauthToken = requestToken;
/* 164 */           return getOAuthAccessToken();
                }

                @Override // twitter4j.auth.OAuthSupport
/* 171 */       public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
/* 172 */           this.oauthToken = requestToken;
/* 173 */           return getOAuthAccessToken(oauthVerifier);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 181 */       public AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
                    try {
/* 182 */               String url = this.conf.getOAuthAccessTokenURL();
/* 183 */               if (url.indexOf(VineAPI.SCHEME_HTTP) == 0) {
/* 186 */                   url = VineAPI.SCHEME_HTTPS + url.substring(7);
                        }
/* 188 */               this.oauthToken = new AccessToken(http.post(url, new HttpParameter[]{new HttpParameter("x_auth_username", screenName), new HttpParameter("x_auth_password", password), new HttpParameter("x_auth_mode", "client_auth")}, this));
/* 193 */               return (AccessToken) this.oauthToken;
                    } catch (TwitterException te) {
/* 195 */               throw new TwitterException("The screen name / password combination seems to be invalid.", te, te.getStatusCode());
                    }
                }

                @Override // twitter4j.auth.OAuthSupport
/* 203 */       public void setOAuthAccessToken(AccessToken accessToken) {
/* 204 */           this.oauthToken = accessToken;
                }

/* 213 */       public void setOAuthRealm(String realm) {
/* 214 */           this.realm = realm;
                }

/* 218 */       String generateAuthorizationHeader(String method, String url, HttpParameter[] params, String nonce, String timestamp, OAuthToken otoken) {
/* 219 */           if (params == null) {
/* 220 */               params = new HttpParameter[0];
                    }
/* 222 */           ArrayList arrayList = new ArrayList(5);
/* 223 */           arrayList.add(new HttpParameter(OAuthConstants.CONSUMER_KEY, this.consumerKey));
/* 224 */           arrayList.add(OAUTH_SIGNATURE_METHOD);
/* 225 */           arrayList.add(new HttpParameter(OAuthConstants.TIMESTAMP, timestamp));
/* 226 */           arrayList.add(new HttpParameter(OAuthConstants.NONCE, nonce));
/* 227 */           arrayList.add(new HttpParameter(OAuthConstants.VERSION, BuildConfig.VERSION_NAME));
/* 228 */           if (otoken != null) {
/* 229 */               arrayList.add(new HttpParameter(OAuthConstants.TOKEN, otoken.getToken()));
                    }
/* 231 */           List<HttpParameter> signatureBaseParams = new ArrayList<>(arrayList.size() + params.length);
/* 232 */           signatureBaseParams.addAll(arrayList);
/* 233 */           if (!HttpParameter.containsFile(params)) {
/* 234 */               signatureBaseParams.addAll(toParamList(params));
                    }
/* 236 */           parseGetParameters(url, signatureBaseParams);
/* 237 */           StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
/* 239 */           base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
/* 240 */           String oauthBaseString = base.toString();
/* 241 */           if (SLog.sLogsOn) {
/* 242 */               Log.d(TAG, "OAuth base string: " + oauthBaseString);
                    }
/* 244 */           String signature = generateSignature(oauthBaseString, otoken);
/* 245 */           if (SLog.sLogsOn) {
/* 246 */               Log.d(TAG, "OAuth signature: " + signature);
                    }
/* 249 */           arrayList.add(new HttpParameter(OAuthConstants.SIGNATURE, signature));
/* 252 */           if (this.realm != null) {
/* 253 */               arrayList.add(new HttpParameter("realm", this.realm));
                    }
/* 255 */           return "OAuth " + encodeParameters(arrayList, ",", true);
                }

/* 258 */       private void parseGetParameters(String url, List<HttpParameter> signatureBaseParams) {
/* 259 */           int queryStart = url.indexOf("?");
/* 260 */           if (-1 != queryStart) {
/* 261 */               String[] queryStrs = z_T4JInternalStringUtil.split(url.substring(queryStart + 1), "&");
                        try {
                            for (String query : queryStrs) {
/* 264 */                       String[] split = z_T4JInternalStringUtil.split(query, "=");
/* 265 */                       if (split.length == 2) {
/* 266 */                           signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], IoUtils.UTF_8), URLDecoder.decode(split[1], IoUtils.UTF_8)));
                                } else {
/* 271 */                           signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], IoUtils.UTF_8), ""));
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                        }
                    }
                }

/* 289 */       String generateAuthorizationHeader(String method, String url, HttpParameter[] params, OAuthToken token) {
/* 290 */           long timestamp = System.currentTimeMillis() / 1000;
/* 291 */           long nonce = timestamp + ((long) RAND.nextInt());
/* 292 */           return generateAuthorizationHeader(method, url, params, String.valueOf(nonce), String.valueOf(timestamp), token);
                }

/* 295 */       public List<HttpParameter> generateOAuthSignatureHttpParams(String method, String url) {
/* 296 */           long timestamp = System.currentTimeMillis() / 1000;
/* 297 */           long nonce = timestamp + ((long) RAND.nextInt());
/* 299 */           ArrayList arrayList = new ArrayList(5);
/* 300 */           arrayList.add(new HttpParameter(OAuthConstants.CONSUMER_KEY, this.consumerKey));
/* 301 */           arrayList.add(OAUTH_SIGNATURE_METHOD);
/* 302 */           arrayList.add(new HttpParameter(OAuthConstants.TIMESTAMP, timestamp));
/* 303 */           arrayList.add(new HttpParameter(OAuthConstants.NONCE, nonce));
/* 304 */           arrayList.add(new HttpParameter(OAuthConstants.VERSION, BuildConfig.VERSION_NAME));
/* 305 */           if (this.oauthToken != null) {
/* 306 */               arrayList.add(new HttpParameter(OAuthConstants.TOKEN, this.oauthToken.getToken()));
                    }
/* 309 */           List<HttpParameter> signatureBaseParams = new ArrayList<>(arrayList.size());
/* 310 */           signatureBaseParams.addAll(arrayList);
/* 311 */           parseGetParameters(url, signatureBaseParams);
/* 313 */           StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
/* 315 */           base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
/* 317 */           String oauthBaseString = base.toString();
/* 318 */           String signature = generateSignature(oauthBaseString, this.oauthToken);
/* 320 */           arrayList.add(new HttpParameter(OAuthConstants.SIGNATURE, signature));
/* 322 */           return arrayList;
                }

/* 333 */       String generateSignature(String data, OAuthToken token) {
                    SecretKeySpec spec;
                    try {
/* 336 */               Mac mac = Mac.getInstance(HMAC_SHA1);
/* 338 */               if (token == null) {
/* 339 */                   String oauthSignature = HttpParameter.encode(this.consumerSecret) + "&";
/* 340 */                   spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
                        } else {
/* 342 */                   spec = token.getSecretKeySpec();
/* 343 */                   if (spec == null) {
/* 344 */                       String oauthSignature2 = HttpParameter.encode(this.consumerSecret) + "&" + HttpParameter.encode(token.getTokenSecret());
/* 345 */                       spec = new SecretKeySpec(oauthSignature2.getBytes(), HMAC_SHA1);
/* 346 */                       token.setSecretKeySpec(spec);
                            }
                        }
/* 349 */               mac.init(spec);
/* 350 */               byte[] byteHMAC = mac.doFinal(data.getBytes());
/* 362 */               return BASE64Encoder.encode(byteHMAC);
                    } catch (InvalidKeyException ike) {
/* 352 */               if (SLog.sLogsOn) {
/* 353 */                   Log.e(TAG, "Failed initialize \"Message Authentication Code\" (MAC)", ike);
                        }
/* 355 */               throw new AssertionError(ike);
                    } catch (NoSuchAlgorithmException nsae) {
/* 357 */               if (SLog.sLogsOn) {
/* 358 */                   Log.e(TAG, "Failed to get HmacSHA1 \"Message Authentication Code\" (MAC)", nsae);
                        }
/* 360 */               throw new AssertionError(nsae);
                    }
                }

/* 367 */       String generateSignature(String data) {
/* 368 */           return generateSignature(data, null);
                }

/* 391 */       static String normalizeRequestParameters(HttpParameter[] params) {
/* 392 */           return normalizeRequestParameters(toParamList(params));
                }

/* 395 */       static String normalizeRequestParameters(List<HttpParameter> params) {
/* 396 */           Collections.sort(params);
/* 397 */           return encodeParameters(params);
                }

/* 400 */       static String normalizeAuthorizationHeaders(List<HttpParameter> params) {
/* 401 */           Collections.sort(params);
/* 402 */           return encodeParameters(params);
                }

/* 405 */       static List<HttpParameter> toParamList(HttpParameter[] params) {
/* 406 */           List<HttpParameter> paramList = new ArrayList<>(params.length);
/* 407 */           paramList.addAll(Arrays.asList(params));
/* 408 */           return paramList;
                }

/* 417 */       public static String encodeParameters(List<HttpParameter> httpParams) {
/* 418 */           return encodeParameters(httpParams, "&", false);
                }

/* 421 */       public static String encodeParameters(List<HttpParameter> httpParams, String splitter, boolean quot) {
/* 422 */           StringBuilder buf = new StringBuilder();
                    for (HttpParameter param : httpParams) {
/* 424 */               if (!param.isFile()) {
/* 425 */                   if (buf.length() != 0) {
/* 426 */                       if (quot) {
/* 427 */                           buf.append("\"");
                                }
/* 429 */                       buf.append(splitter);
                            }
/* 431 */                   buf.append(HttpParameter.encode(param.getName())).append("=");
/* 432 */                   if (quot) {
/* 433 */                       buf.append("\"");
                            }
/* 435 */                   buf.append(HttpParameter.encode(param.getValue()));
                        }
                    }
/* 438 */           if (buf.length() != 0 && quot) {
/* 440 */               buf.append("\"");
                    }
/* 443 */           return buf.toString();
                }

/* 460 */       static String constructRequestURL(String url) {
/* 461 */           int index = url.indexOf("?");
/* 462 */           if (-1 != index) {
/* 463 */               url = url.substring(0, index);
                    }
/* 465 */           int slashIndex = url.indexOf("/", 8);
/* 466 */           String baseURL = url.substring(0, slashIndex).toLowerCase();
/* 467 */           int colonIndex = baseURL.indexOf(":", 8);
/* 468 */           if (-1 != colonIndex) {
/* 470 */               if (baseURL.startsWith(VineAPI.SCHEME_HTTP) && baseURL.endsWith(":80")) {
/* 472 */                   baseURL = baseURL.substring(0, colonIndex);
                        } else if (baseURL.startsWith(VineAPI.SCHEME_HTTPS) && baseURL.endsWith(":443")) {
/* 475 */                   baseURL = baseURL.substring(0, colonIndex);
                        }
                    }
/* 478 */           return baseURL + url.substring(slashIndex);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 484 */       public void setOAuthConsumer(String consumerKey, String consumerSecret) {
/* 485 */           if (consumerKey == null) {
/* 485 */               consumerKey = "";
                    }
                    this.consumerKey = consumerKey;
/* 486 */           if (consumerSecret == null) {
/* 486 */               consumerSecret = "";
                    }
                    this.consumerSecret = consumerSecret;
                }

/* 491 */       public boolean equals(Object o) {
/* 492 */           if (this == o) {
                        return true;
                    }
/* 493 */           if (!(o instanceof OAuthSupport)) {
                        return false;
                    }
/* 495 */           OAuthAuthorization that = (OAuthAuthorization) o;
/* 497 */           if (this.consumerKey == null ? that.consumerKey != null : !this.consumerKey.equals(that.consumerKey)) {
                        return false;
                    }
/* 499 */           if (this.consumerSecret == null ? that.consumerSecret != null : !this.consumerSecret.equals(that.consumerSecret)) {
                        return false;
                    }
/* 501 */           if (this.oauthToken != null) {
                        if (this.oauthToken.equals(that.oauthToken)) {
                            return true;
                        }
                    } else if (that.oauthToken == null) {
                        return true;
                    }
                    return false;
                }

/* 508 */       public int hashCode() {
/* 509 */           int result = this.consumerKey != null ? this.consumerKey.hashCode() : 0;
/* 511 */           return (((result * 31) + (this.consumerSecret != null ? this.consumerSecret.hashCode() : 0)) * 31) + (this.oauthToken != null ? this.oauthToken.hashCode() : 0);
                }

/* 516 */       public String toString() {
/* 517 */           return "OAuthAuthorization{consumerKey='" + this.consumerKey + "', consumerSecret='******************************************', oauthToken=" + this.oauthToken + '}';
                }
            }
