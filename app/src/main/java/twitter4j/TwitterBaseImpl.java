            package twitter4j;

            import java.io.IOException;
            import java.io.ObjectInputStream;
            import java.io.ObjectOutputStream;
            import java.io.Serializable;
            import twitter4j.auth.AccessToken;
            import twitter4j.auth.Authorization;
            import twitter4j.auth.AuthorizationFactory;
            import twitter4j.auth.BasicAuthorization;
            import twitter4j.auth.OAuthAuthorization;
            import twitter4j.auth.OAuthSupport;
            import twitter4j.auth.RequestToken;
            import twitter4j.conf.Configuration;
            import twitter4j.internal.http.HttpClientWrapper;
            import twitter4j.internal.http.HttpResponseEvent;
            import twitter4j.internal.http.HttpResponseListener;
            import twitter4j.internal.http.XAuthAuthorization;
            import twitter4j.internal.json.z_T4JInternalFactory;
            import twitter4j.internal.json.z_T4JInternalJSONImplFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    abstract class TwitterBaseImpl implements TwitterBase, Serializable, OAuthSupport, HttpResponseListener {
                private static final long serialVersionUID = -3812176145960812140L;
                protected Authorization auth;
                protected Configuration conf;
                protected z_T4JInternalFactory factory;
                protected transient HttpClientWrapper http;
                protected transient String screenName = null;

                /* JADX INFO: renamed from: id */
                protected transient long f2541id = 0;

/* 45 */        TwitterBaseImpl(Configuration conf, Authorization auth) {
/* 47 */            this.conf = conf;
/* 48 */            this.auth = auth;
/* 49 */            init();
                }

/* 52 */        private void init() {
/* 53 */            if (this.auth == null) {
/* 55 */                String consumerKey = this.conf.getOAuthConsumerKey();
/* 56 */                String consumerSecret = this.conf.getOAuthConsumerSecret();
/* 58 */                if (consumerKey != null && consumerSecret != null && !this.conf.isApplicationOnlyAuthEnabled()) {
/* 69 */                    OAuthAuthorization oauth = new OAuthAuthorization(this.conf);
/* 70 */                    String accessToken = this.conf.getOAuthAccessToken();
/* 71 */                    String accessTokenSecret = this.conf.getOAuthAccessTokenSecret();
/* 72 */                    if (accessToken != null && accessTokenSecret != null) {
/* 73 */                        oauth.setOAuthAccessToken(new AccessToken(accessToken, accessTokenSecret));
                            }
/* 75 */                    this.auth = oauth;
                        }
                    }
/* 81 */            this.http = new HttpClientWrapper(this.conf);
/* 82 */            this.http.setHttpResponseListener(this);
/* 83 */            setFactory();
                }

/* 86 */        protected void setFactory() {
/* 87 */            this.factory = new z_T4JInternalJSONImplFactory(this.conf);
                }

                @Override // twitter4j.internal.http.HttpResponseListener
/* 177 */       public void httpResponseReceived(HttpResponseEvent event) {
                }

                @Override // twitter4j.TwitterBase
/* 184 */       public final Authorization getAuthorization() {
/* 185 */           return this.auth;
                }

                @Override // twitter4j.TwitterBase
/* 192 */       public Configuration getConfiguration() {
/* 193 */           return this.conf;
                }

                @Override // twitter4j.TwitterBase
/* 200 */       public void shutdown() {
/* 201 */           if (this.http != null) {
                        this.http.shutdown();
                    }
                }

/* 204 */       protected final void ensureAuthorizationEnabled() {
/* 205 */           if (!this.auth.isEnabled()) {
/* 206 */               throw new IllegalStateException("Authentication credentials are missing. See http://twitter4j.org/en/configuration.html for the detail.");
                    }
                }

/* 211 */       protected final void ensureOAuthEnabled() {
/* 212 */           if (!(this.auth instanceof OAuthAuthorization)) {
/* 213 */               throw new IllegalStateException("OAuth required. Authentication credentials are missing. See http://twitter4j.org/en/configuration.html for the detail.");
                    }
                }

/* 219 */       private void writeObject(ObjectOutputStream out) throws IOException {
/* 220 */           out.putFields();
/* 221 */           out.writeFields();
/* 223 */           out.writeObject(this.conf);
/* 224 */           out.writeObject(this.auth);
                }

/* 236 */       private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
/* 237 */           stream.readFields();
/* 239 */           this.conf = (Configuration) stream.readObject();
/* 240 */           this.auth = (Authorization) stream.readObject();
/* 242 */           this.http = new HttpClientWrapper(this.conf);
/* 243 */           this.http.setHttpResponseListener(this);
/* 244 */           setFactory();
                }

                @Override // twitter4j.auth.OAuthSupport
/* 254 */       public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
                    try {
                        if (consumerKey == null) {
/* 256 */                   throw new NullPointerException("consumer key is null");
                        }
/* 258 */               if (consumerSecret == null) {
/* 259 */                   throw new NullPointerException("consumer secret is null");
                        }
/* 262 */               if (this.auth == null) {
/* 263 */                   if (!this.conf.isApplicationOnlyAuthEnabled()) {
/* 268 */                       OAuthAuthorization oauth = new OAuthAuthorization(this.conf);
/* 269 */                       oauth.setOAuthConsumer(consumerKey, consumerSecret);
/* 270 */                       this.auth = oauth;
                            }
                        } else if (this.auth instanceof BasicAuthorization) {
/* 273 */                   XAuthAuthorization xauth = new XAuthAuthorization((BasicAuthorization) this.auth);
/* 274 */                   xauth.setOAuthConsumer(consumerKey, consumerSecret);
/* 275 */                   this.auth = xauth;
                        } else if (this.auth instanceof OAuthAuthorization) {
/* 278 */                   throw new IllegalStateException("consumer key/secret pair already set.");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // twitter4j.auth.OAuthSupport
/* 286 */       public RequestToken getOAuthRequestToken() throws TwitterException {
/* 287 */           return getOAuthRequestToken(null);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 294 */       public RequestToken getOAuthRequestToken(String callbackUrl) throws TwitterException {
/* 295 */           return getOAuth().getOAuthRequestToken(callbackUrl);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 302 */       public RequestToken getOAuthRequestToken(String callbackUrl, String xAuthAccessType) throws TwitterException {
/* 303 */           return getOAuth().getOAuthRequestToken(callbackUrl, xAuthAccessType);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 316 */       public synchronized AccessToken getOAuthAccessToken() throws TwitterException {
                    AccessToken oauthAccessToken;
                    try {
                        Authorization auth = getAuthorization();
/* 319 */               if (auth instanceof BasicAuthorization) {
/* 320 */                   BasicAuthorization basicAuth = (BasicAuthorization) auth;
/* 321 */                   Authorization auth2 = AuthorizationFactory.getInstance(this.conf);
/* 322 */                   if (auth2 instanceof OAuthAuthorization) {
/* 323 */                       this.auth = auth2;
/* 325 */                       oauthAccessToken = ((OAuthAuthorization) auth2).getOAuthAccessToken(basicAuth.getUserId(), basicAuth.getPassword());
                            } else {
/* 327 */                       throw new IllegalStateException("consumer key / secret combination not supplied.");
                            }
                        } else if (auth instanceof XAuthAuthorization) {
/* 331 */                   XAuthAuthorization xauth = (XAuthAuthorization) auth;
/* 332 */                   this.auth = xauth;
/* 333 */                   OAuthAuthorization oauthAuth = new OAuthAuthorization(this.conf);
/* 334 */                   oauthAuth.setOAuthConsumer(xauth.getConsumerKey(), xauth.getConsumerSecret());
/* 335 */                   oauthAccessToken = oauthAuth.getOAuthAccessToken(xauth.getUserId(), xauth.getPassword());
                        } else {
/* 337 */                   oauthAccessToken = getOAuth().getOAuthAccessToken();
                        }
/* 340 */               this.screenName = oauthAccessToken.getScreenName();
/* 341 */               this.f2541id = oauthAccessToken.getUserId();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return oauthAccessToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 352 */       public synchronized AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException {
                    AccessToken oauthAccessToken;
                    try {
                        oauthAccessToken = getOAuth().getOAuthAccessToken(oauthVerifier);
/* 354 */               this.screenName = oauthAccessToken.getScreenName();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return oauthAccessToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 364 */       public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
                    AccessToken oauthAccessToken;
                    try {
                        OAuthSupport oauth = getOAuth();
/* 366 */               oauthAccessToken = oauth.getOAuthAccessToken(requestToken);
/* 367 */               this.screenName = oauthAccessToken.getScreenName();
                    } catch (Throwable th) {
                        throw th;
                    }
                    return oauthAccessToken;
                }

                @Override // twitter4j.auth.OAuthSupport
/* 377 */       public synchronized AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return getOAuth().getOAuthAccessToken(requestToken, oauthVerifier);
                }

                @Override // twitter4j.auth.OAuthSupport
/* 385 */       public synchronized void setOAuthAccessToken(AccessToken accessToken) {
                    try {
                        getOAuth().setOAuthAccessToken(accessToken);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

                @Override // twitter4j.auth.OAuthSupport
/* 393 */       public synchronized AccessToken getOAuthAccessToken(String screenName, String password) throws TwitterException {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return getOAuth().getOAuthAccessToken(screenName, password);
                }

/* 398 */       private OAuthSupport getOAuth() {
/* 399 */           if (!(this.auth instanceof OAuthSupport)) {
/* 400 */               throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
                    }
/* 403 */           return (OAuthSupport) this.auth;
                }

/* 430 */       public boolean equals(Object o) {
/* 431 */           if (this == o) {
                        return true;
                    }
/* 432 */           if (!(o instanceof TwitterBaseImpl)) {
                        return false;
                    }
/* 434 */           TwitterBaseImpl that = (TwitterBaseImpl) o;
/* 436 */           if (this.auth == null ? that.auth != null : !this.auth.equals(that.auth)) {
                        return false;
                    }
/* 438 */           if (!this.conf.equals(that.conf)) {
                        return false;
                    }
/* 439 */           if (this.http != null) {
                        if (this.http.equals(that.http)) {
                            return true;
                        }
                    } else if (that.http == null) {
                        return true;
                    }
                    return false;
                }

/* 448 */       public int hashCode() {
/* 449 */           int result = this.conf.hashCode();
/* 452 */           return (((result * 31) + (this.http != null ? this.http.hashCode() : 0)) * 31) + (this.auth != null ? this.auth.hashCode() : 0);
                }

/* 457 */       public String toString() {
/* 458 */           return "TwitterBase{conf=" + this.conf + ", http=" + this.http + ", auth=" + this.auth + '}';
                }
            }
