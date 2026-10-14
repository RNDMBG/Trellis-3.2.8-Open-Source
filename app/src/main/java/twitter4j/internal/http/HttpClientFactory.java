            package twitter4j.internal.http;

            import java.lang.reflect.Constructor;
            import java.lang.reflect.InvocationTargetException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 29 */    public final class HttpClientFactory {
                private static final Constructor HTTP_CLIENT_CONSTRUCTOR;
                private static final String HTTP_CLIENT_IMPLEMENTATION = "twitter4j.http.httpClient";

                static {
/* 31 */            Class<?> cls = null;
/* 33 */            String httpClientImpl = System.getProperty(HTTP_CLIENT_IMPLEMENTATION);
/* 34 */            if (httpClientImpl != null) {
                        try {
/* 36 */                    cls = Class.forName(httpClientImpl);
                        } catch (ClassNotFoundException e) {
                        }
                    }
/* 40 */            if (cls == null) {
                        try {
/* 42 */                    cls = Class.forName("twitter4j.internal.http.alternative.HttpClientImpl");
                        } catch (ClassNotFoundException e2) {
                        }
                    }
/* 46 */            if (cls == null) {
                        try {
/* 48 */                    cls = Class.forName("twitter4j.internal.http.HttpClientImpl");
                        } catch (ClassNotFoundException cnfe) {
/* 50 */                    throw new AssertionError(cnfe);
                        }
                    }
                    try {
                        HTTP_CLIENT_CONSTRUCTOR = cls.getConstructor(HttpClientConfiguration.class);
                    } catch (NoSuchMethodException nsme) {
/* 56 */                throw new AssertionError(nsme);
                    }
                }

/* 61 */        public static HttpClient getInstance(HttpClientConfiguration conf) {
                    try {
                        return (HttpClient) HTTP_CLIENT_CONSTRUCTOR.newInstance(conf);
                    } catch (IllegalAccessException e) {
/* 66 */                throw new AssertionError(e);
                    } catch (InstantiationException e2) {
/* 64 */                throw new AssertionError(e2);
                    } catch (InvocationTargetException e3) {
/* 68 */                throw new AssertionError(e3);
                    }
                }
            }
