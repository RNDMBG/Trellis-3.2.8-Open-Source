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
/* 66 */    public class VineOauthAuthorization implements Authorization, Serializable, OAuthSupport {
                private static final String HMAC_SHA1 = "HmacSHA1";
                private static final HttpParameter OAUTH_SIGNATURE_METHOD = new HttpParameter(OAuthConstants.SIGN_METHOD, "HMAC-SHA1");
                private static Random RAND = new Random();
                private static final String TAG = "t4jVineOauth";
                private static transient HttpClientWrapper http = null;
                private static final long serialVersionUID = -4368426677157998618L;
                private final Configuration conf;
                private String consumerSecret;
                private String consumerKey = "";
                private String realm = null;
                private OAuthToken oauthToken = null;

/* 67 */        public VineOauthAuthorization(Configuration conf) {
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
/* 188 */               this.oauthToken = new AccessToken(http.post(url, new HttpParameter[]{new HttpParameter("x_auth_username", screenName), new HttpParameter("x_auth_password", password), new HttpParameter("x_auth_mode", "client_auth"), new HttpParameter("send_error_codes", true)}, this));
/* 194 */               return (AccessToken) this.oauthToken;
                    } catch (TwitterException te) {
/* 196 */               throw new TwitterException("The screen name / password combination seems to be invalid.", te, te.getStatusCode());
                    }
                }

                @Override // twitter4j.auth.OAuthSupport
/* 204 */       public void setOAuthAccessToken(AccessToken accessToken) {
/* 205 */           this.oauthToken = accessToken;
                }

/* 210 */       String generateAuthorizationHeader(String method, String url, HttpParameter[] params, String nonce, String timestamp, OAuthToken otoken) {
/* 211 */           if (params == null) {
/* 212 */               params = new HttpParameter[0];
                    }
/* 214 */           ArrayList arrayList = new ArrayList(5);
/* 215 */           arrayList.add(new HttpParameter(OAuthConstants.CONSUMER_KEY, this.consumerKey));
/* 216 */           arrayList.add(OAUTH_SIGNATURE_METHOD);
/* 217 */           arrayList.add(new HttpParameter(OAuthConstants.TIMESTAMP, timestamp));
/* 218 */           arrayList.add(new HttpParameter(OAuthConstants.NONCE, nonce));
/* 219 */           arrayList.add(new HttpParameter(OAuthConstants.VERSION, BuildConfig.VERSION_NAME));
/* 220 */           if (otoken != null) {
/* 221 */               arrayList.add(new HttpParameter(OAuthConstants.TOKEN, otoken.getToken()));
                    }
/* 223 */           List<HttpParameter> signatureBaseParams = new ArrayList<>(arrayList.size() + params.length);
/* 225 */           signatureBaseParams.addAll(arrayList);
/* 226 */           if (!HttpParameter.containsFile(params)) {
/* 227 */               signatureBaseParams.addAll(toParamList(params));
                    }
/* 229 */           parseGetParameters(url, signatureBaseParams);
/* 230 */           StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
/* 232 */           base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
/* 233 */           String oauthBaseString = base.toString();
/* 234 */           if (SLog.sLogsOn) {
/* 235 */               Log.v(TAG, "OAuth base string: " + oauthBaseString);
                    }
/* 237 */           String signature = generateSignature(oauthBaseString, otoken);
/* 238 */           if (SLog.sLogsOn) {
/* 239 */               Log.v(TAG, "OAuth signature: " + signature);
                    }
/* 242 */           arrayList.add(new HttpParameter(OAuthConstants.SIGNATURE, signature));
/* 245 */           if (this.realm != null) {
/* 246 */               arrayList.add(new HttpParameter("realm", this.realm));
                    }
/* 248 */           return "OAuth " + encodeParameters(arrayList, ",", true);
                }

/* 251 */       private void parseGetParameters(String url, List<HttpParameter> signatureBaseParams) {
/* 252 */           int queryStart = url.indexOf("?");
/* 253 */           if (-1 != queryStart) {
/* 254 */               String[] queryStrs = z_T4JInternalStringUtil.split(url.substring(queryStart + 1), "&");
                        try {
                            for (String query : queryStrs) {
/* 257 */                       String[] split = z_T4JInternalStringUtil.split(query, "=");
/* 258 */                       if (split.length == 2) {
/* 259 */                           signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], IoUtils.UTF_8), URLDecoder.decode(split[1], IoUtils.UTF_8)));
                                } else {
/* 264 */                           signatureBaseParams.add(new HttpParameter(URLDecoder.decode(split[0], IoUtils.UTF_8), ""));
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                        }
                    }
                }

/* 282 */       String generateAuthorizationHeader(String method, String url, HttpParameter[] params, OAuthToken token) {
/* 283 */           long timestamp = System.currentTimeMillis() / 1000;
/* 284 */           long nonce = timestamp + ((long) RAND.nextInt());
/* 285 */           return generateAuthorizationHeader(method, url, params, String.valueOf(nonce), String.valueOf(timestamp), token);
                }

/* 288 */       public List<HttpParameter> generateOAuthSignatureHttpParams(String method, String url) {
/* 289 */           long timestamp = System.currentTimeMillis() / 1000;
/* 290 */           long nonce = timestamp + ((long) RAND.nextInt());
/* 292 */           ArrayList arrayList = new ArrayList(5);
/* 293 */           arrayList.add(new HttpParameter(OAuthConstants.CONSUMER_KEY, this.consumerKey));
/* 294 */           arrayList.add(OAUTH_SIGNATURE_METHOD);
/* 295 */           arrayList.add(new HttpParameter(OAuthConstants.TIMESTAMP, timestamp));
/* 296 */           arrayList.add(new HttpParameter(OAuthConstants.NONCE, nonce));
/* 297 */           arrayList.add(new HttpParameter(OAuthConstants.VERSION, BuildConfig.VERSION_NAME));
/* 298 */           if (this.oauthToken != null) {
/* 299 */               arrayList.add(new HttpParameter(OAuthConstants.TOKEN, this.oauthToken.getToken()));
                    }
/* 302 */           List<HttpParameter> signatureBaseParams = new ArrayList<>(arrayList.size());
/* 303 */           signatureBaseParams.addAll(arrayList);
/* 304 */           parseGetParameters(url, signatureBaseParams);
/* 306 */           StringBuilder base = new StringBuilder(method).append("&").append(HttpParameter.encode(constructRequestURL(url))).append("&");
/* 308 */           base.append(HttpParameter.encode(normalizeRequestParameters(signatureBaseParams)));
/* 310 */           String oauthBaseString = base.toString();
/* 311 */           String signature = generateSignature(oauthBaseString, this.oauthToken);
/* 313 */           arrayList.add(new HttpParameter(OAuthConstants.SIGNATURE, signature));
/* 315 */           return arrayList;
                }

/* 326 */       String generateSignature(String data, OAuthToken token) {
                    SecretKeySpec spec;
                    try {
/* 329 */               Mac mac = Mac.getInstance(HMAC_SHA1);
/* 331 */               if (token == null) {
/* 332 */                   String oauthSignature = HttpParameter.encode(this.consumerSecret) + "&";
/* 333 */                   spec = new SecretKeySpec(oauthSignature.getBytes(), HMAC_SHA1);
                        } else {
/* 335 */                   spec = token.getSecretKeySpec();
/* 336 */                   if (spec == null) {
/* 337 */                       String oauthSignature2 = HttpParameter.encode(this.consumerSecret) + "&" + HttpParameter.encode(token.getTokenSecret());
/* 338 */                       spec = new SecretKeySpec(oauthSignature2.getBytes(), HMAC_SHA1);
/* 339 */                       token.setSecretKeySpec(spec);
                            }
                        }
/* 342 */               mac.init(spec);
/* 343 */               byte[] byteHMAC = mac.doFinal(data.getBytes());
/* 355 */               return BASE64Encoder.encode(byteHMAC);
                    } catch (InvalidKeyException ike) {
/* 345 */               if (SLog.sLogsOn) {
/* 346 */                   Log.v(TAG, "Failed initialize \"Message Authentication Code\" (MAC)", ike);
                        }
/* 348 */               throw new AssertionError(ike);
                    } catch (NoSuchAlgorithmException nsae) {
/* 350 */               if (SLog.sLogsOn) {
/* 351 */                   Log.v(TAG, "Failed to get HmacSHA1 \"Message Authentication Code\" (MAC)", nsae);
                        }
/* 353 */               throw new AssertionError(nsae);
                    }
                }

/* 360 */       String generateSignature(String data) {
/* 361 */           return generateSignature(data, null);
                }

/* 384 */       static String normalizeRequestParameters(HttpParameter[] params) {
/* 385 */           return normalizeRequestParameters(toParamList(params));
                }

/* 388 */       static String normalizeRequestParameters(List<HttpParameter> params) {
/* 389 */           Collections.sort(params);
/* 390 */           return encodeParameters(params);
                }

/* 393 */       static String normalizeAuthorizationHeaders(List<HttpParameter> params) {
/* 394 */           Collections.sort(params);
/* 395 */           return encodeParameters(params);
                }

/* 398 */       static List<HttpParameter> toParamList(HttpParameter[] params) {
/* 399 */           List<HttpParameter> paramList = new ArrayList<>(params.length);
/* 400 */           paramList.addAll(Arrays.asList(params));
/* 401 */           return paramList;
                }

/* 410 */       public static String encodeParameters(List<HttpParameter> httpParams) {
/* 411 */           return encodeParameters(httpParams, "&", false);
                }

/* 414 */       public static String encodeParameters(List<HttpParameter> httpParams, String splitter, boolean quot) {
/* 415 */           StringBuilder buf = new StringBuilder();
                    for (HttpParameter param : httpParams) {
/* 417 */               if (!param.isFile()) {
/* 418 */                   if (buf.length() != 0) {
/* 419 */                       if (quot) {
/* 420 */                           buf.append("\"");
                                }
/* 422 */                       buf.append(splitter);
                            }
/* 424 */                   buf.append(HttpParameter.encode(param.getName())).append("=");
/* 425 */                   if (quot) {
/* 426 */                       buf.append("\"");
                            }
/* 428 */                   buf.append(HttpParameter.encode(param.getValue()));
                        }
                    }
/* 431 */           if (buf.length() != 0 && quot) {
/* 433 */               buf.append("\"");
                    }
/* 436 */           return buf.toString();
                }

/* 453 */       static String constructRequestURL(String url) {
/* 454 */           int index = url.indexOf("?");
/* 455 */           if (-1 != index) {
/* 456 */               url = url.substring(0, index);
                    }
/* 458 */           int slashIndex = url.indexOf("/", 8);
/* 459 */           String baseURL = url.substring(0, slashIndex).toLowerCase();
/* 460 */           int colonIndex = baseURL.indexOf(":", 8);
/* 461 */           if (-1 != colonIndex) {
/* 463 */               if (baseURL.startsWith(VineAPI.SCHEME_HTTP) && baseURL.endsWith(":80")) {
/* 465 */                   baseURL = baseURL.substring(0, colonIndex);
                        } else if (baseURL.startsWith(VineAPI.SCHEME_HTTPS) && baseURL.endsWith(":443")) {
/* 468 */                   baseURL = baseURL.substring(0, colonIndex);
                        }
                    }
/* 471 */           return baseURL + url.substring(slashIndex);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 477 */       public void setOAuthConsumer(String consumerKey, String consumerSecret) {
/* 478 */           if (consumerKey == null) {
/* 478 */               consumerKey = "";
                    }
                    this.consumerKey = consumerKey;
/* 479 */           if (consumerSecret == null) {
/* 479 */               consumerSecret = "";
                    }
                    this.consumerSecret = consumerSecret;
                }

/* 484 */       public boolean equals(Object o) {
/* 485 */           if (this == o) {
                        return true;
                    }
/* 486 */           if (!(o instanceof OAuthSupport)) {
                        return false;
                    }
/* 488 */           VineOauthAuthorization that = (VineOauthAuthorization) o;
/* 490 */           if (this.consumerKey == null ? that.consumerKey != null : !this.consumerKey.equals(that.consumerKey)) {
                        return false;
                    }
/* 492 */           if (this.consumerSecret == null ? that.consumerSecret != null : !this.consumerSecret.equals(that.consumerSecret)) {
                        return false;
                    }
/* 494 */           if (this.oauthToken != null) {
                        if (this.oauthToken.equals(that.oauthToken)) {
                            return true;
                        }
                    } else if (that.oauthToken == null) {
                        return true;
                    }
                    return false;
                }

/* 501 */       public int hashCode() {
/* 502 */           int result = this.consumerKey != null ? this.consumerKey.hashCode() : 0;
/* 504 */           return (((result * 31) + (this.consumerSecret != null ? this.consumerSecret.hashCode() : 0)) * 31) + (this.oauthToken != null ? this.oauthToken.hashCode() : 0);
                }

/* 509 */       public String toString() {
/* 510 */           return "OAuthAuthorization{consumerKey='" + this.consumerKey + "', consumerSecret='******************************************', oauthToken=" + this.oauthToken + '}';
                }
            }
