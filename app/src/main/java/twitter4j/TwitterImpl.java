            package twitter4j;

            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            import twitter4j.auth.Authorization;
            import twitter4j.conf.Configuration;
            import twitter4j.internal.http.HttpParameter;
            import twitter4j.internal.http.HttpResponse;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    class TwitterImpl extends TwitterBaseImpl implements Twitter {
                private static final Map<Configuration, HttpParameter[]> implicitParamsMap = new HashMap();
                private static final Map<Configuration, String> implicitParamsStrMap = new HashMap();
                private static final long serialVersionUID = -1486360080128882436L;
                private final HttpParameter[] IMPLICIT_PARAMS;
                private final String IMPLICIT_PARAMS_STR;
                private final HttpParameter INCLUDE_MY_RETWEET;

/* 47 */        TwitterImpl(Configuration conf, Authorization auth) {
/* 48 */            super(conf, auth);
/* 49 */            this.INCLUDE_MY_RETWEET = new HttpParameter("include_my_retweet", conf.isIncludeMyRetweetEnabled());
/* 50 */            HttpParameter[] implicitParams = implicitParamsMap.get(conf);
/* 51 */            String implicitParamsStr = implicitParamsStrMap.get(conf);
/* 52 */            if (implicitParams == null) {
/* 53 */                String includeEntities = conf.isIncludeEntitiesEnabled() ? "1" : "0";
/* 54 */                String includeRTs = conf.isIncludeRTsEnabled() ? "1" : "0";
/* 55 */                boolean contributorsEnabled = conf.getContributingTo() != -1;
                        implicitParamsStr = "include_entities=" + includeEntities + "&include_rts=" + includeRTs + (contributorsEnabled ? "&contributingto=" + conf.getContributingTo() : "");
/* 58 */                implicitParamsStrMap.put(conf, implicitParamsStr);
/* 60 */                List<HttpParameter> params = new ArrayList<>(4);
/* 61 */                params.add(new HttpParameter("include_entities", includeEntities));
/* 62 */                params.add(new HttpParameter("include_rts", includeRTs));
/* 63 */                if (contributorsEnabled) {
/* 64 */                    params.add(new HttpParameter("contributingto", conf.getContributingTo()));
                        }
/* 66 */                if (conf.isTrimUserEnabled()) {
/* 67 */                    params.add(new HttpParameter("trim_user", "1"));
                        }
/* 69 */                implicitParams = (HttpParameter[]) params.toArray(new HttpParameter[params.size()]);
/* 70 */                implicitParamsMap.put(conf, implicitParams);
                    }
/* 72 */            this.IMPLICIT_PARAMS = implicitParams;
/* 73 */            this.IMPLICIT_PARAMS_STR = implicitParamsStr;
                }

/* 1899 */      private HttpResponse get(String url) throws TwitterException {
                    String url2;
/* 1900 */          ensureAuthorizationEnabled();
/* 1901 */          if (url.contains("?")) {
/* 1902 */              url2 = url + "&" + this.IMPLICIT_PARAMS_STR;
                    } else {
/* 1904 */              url2 = url + "?" + this.IMPLICIT_PARAMS_STR;
                    }
/* 1906 */          if (!this.conf.isMBeanEnabled()) {
/* 1907 */              return this.http.get(url2, this.auth);
                    }
/* 1911 */          long start = System.currentTimeMillis();
                    try {
/* 1913 */              HttpResponse response = this.http.get(url2, this.auth);
/* 1915 */              long jCurrentTimeMillis = System.currentTimeMillis() - start;
/* 1918 */              return response;
                    } catch (Throwable th) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - start;
/* 1917 */              throw th;
                    }
                }

/* 1922 */      private HttpResponse get(String url, HttpParameter[] params) throws TwitterException {
/* 1923 */          ensureAuthorizationEnabled();
/* 1924 */          if (!this.conf.isMBeanEnabled()) {
/* 1925 */              return this.http.get(url, mergeImplicitParams(params), this.auth);
                    }
/* 1929 */          long start = System.currentTimeMillis();
                    try {
/* 1931 */              HttpResponse response = this.http.get(url, mergeImplicitParams(params), this.auth);
/* 1933 */              long jCurrentTimeMillis = System.currentTimeMillis() - start;
/* 1936 */              return response;
                    } catch (Throwable th) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - start;
/* 1935 */              throw th;
                    }
                }

/* 1940 */      private HttpResponse post(String url) throws TwitterException {
/* 1941 */          ensureAuthorizationEnabled();
/* 1942 */          if (!this.conf.isMBeanEnabled()) {
/* 1943 */              return this.http.post(url, this.IMPLICIT_PARAMS, this.auth);
                    }
/* 1947 */          long start = System.currentTimeMillis();
                    try {
/* 1949 */              HttpResponse response = this.http.post(url, this.IMPLICIT_PARAMS, this.auth);
/* 1951 */              long jCurrentTimeMillis = System.currentTimeMillis() - start;
/* 1954 */              return response;
                    } catch (Throwable th) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - start;
/* 1953 */              throw th;
                    }
                }

/* 1958 */      private HttpResponse post(String url, HttpParameter[] params) throws TwitterException {
/* 1959 */          ensureAuthorizationEnabled();
/* 1960 */          if (!this.conf.isMBeanEnabled()) {
/* 1961 */              return this.http.post(url, mergeImplicitParams(params), this.auth);
                    }
/* 1965 */          long start = System.currentTimeMillis();
                    try {
/* 1967 */              HttpResponse response = this.http.post(url, mergeImplicitParams(params), this.auth);
/* 1969 */              long jCurrentTimeMillis = System.currentTimeMillis() - start;
/* 1972 */              return response;
                    } catch (Throwable th) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - start;
/* 1971 */              throw th;
                    }
                }

/* 1976 */      private HttpParameter[] mergeParameters(HttpParameter[] params1, HttpParameter[] params2) {
/* 1977 */          if (params1 != null && params2 != null) {
/* 1978 */              HttpParameter[] params = new HttpParameter[params1.length + params2.length];
/* 1979 */              System.arraycopy(params1, 0, params, 0, params1.length);
/* 1980 */              System.arraycopy(params2, 0, params, params1.length, params2.length);
/* 1989 */              return params;
                    }
/* 1983 */          if (params1 == null && params2 == null) {
/* 1984 */              return new HttpParameter[0];
                    }
                    return params1 != null ? params1 : params2;
                }

/* 1993 */      private HttpParameter[] mergeParameters(HttpParameter[] params1, HttpParameter params2) {
/* 1994 */          if (params1 != null && params2 != null) {
/* 1995 */              HttpParameter[] params = new HttpParameter[params1.length + 1];
/* 1996 */              System.arraycopy(params1, 0, params, 0, params1.length);
/* 1997 */              params[params.length - 1] = params2;
/* 2006 */              return params;
                    }
/* 2000 */          if (params1 == null && params2 == null) {
/* 2001 */              return new HttpParameter[0];
                    }
                    return params1 != null ? params1 : new HttpParameter[]{params2};
                }

/* 2010 */      private HttpParameter[] mergeImplicitParams(HttpParameter[] params) {
/* 2011 */          return mergeParameters(params, this.IMPLICIT_PARAMS);
                }

/* 2014 */      private boolean isOk(HttpResponse response) {
                    return response != null && response.getStatusCode() < 300;
                }

                @Override // twitter4j.TwitterBase
/* 2019 */      public void createFriendship(String screenName) throws TwitterException {
/* 2020 */          post(this.conf.getRestBaseURL() + "friendships/create.json?screen_name=" + screenName);
                }

                @Override // twitter4j.TwitterBase
/* 2027 */      public void createFriendship(long userId) throws TwitterException {
/* 2028 */          post(this.conf.getRestBaseURL() + "friendships/create.json?user_id=" + userId);
                }

                @Override // twitter4j.TwitterBaseImpl
/* 2033 */      public String toString() {
/* 2034 */          return "TwitterImpl{INCLUDE_MY_RETWEET=" + this.INCLUDE_MY_RETWEET + '}';
                }
            }
