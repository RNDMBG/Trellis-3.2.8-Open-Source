            package twitter4j.internal.http;

            import java.lang.reflect.Constructor;
            import java.lang.reflect.InvocationTargetException;
            
            
    public final class HttpClientFactory {
                private static final Constructor HTTP_CLIENT_CONSTRUCTOR;
                private static final String HTTP_CLIENT_IMPLEMENTATION = "twitter4j.http.httpClient";

                static {
            Class<?> cls = null;
            String httpClientImpl = System.getProperty(HTTP_CLIENT_IMPLEMENTATION);
            if (httpClientImpl != null) {
                        try {
                    cls = Class.forName(httpClientImpl);
                        } catch (ClassNotFoundException e) {
                        }
                    }
            if (cls == null) {
                        try {
                    cls = Class.forName("twitter4j.internal.http.alternative.HttpClientImpl");
                        } catch (ClassNotFoundException e2) {
                        }
                    }
            if (cls == null) {
                        try {
                    cls = Class.forName("twitter4j.internal.http.HttpClientImpl");
                        } catch (ClassNotFoundException cnfe) {
                    throw new AssertionError(cnfe);
                        }
                    }
                    try {
                        HTTP_CLIENT_CONSTRUCTOR = cls.getConstructor(HttpClientConfiguration.class);
                    } catch (NoSuchMethodException nsme) {
                throw new AssertionError(nsme);
                    }
                }

        public static HttpClient getInstance(HttpClientConfiguration conf) {
                    try {
                        return (HttpClient) HTTP_CLIENT_CONSTRUCTOR.newInstance(conf);
                    } catch (IllegalAccessException e) {
                throw new AssertionError(e);
                    } catch (InstantiationException e2) {
                throw new AssertionError(e2);
                    } catch (InvocationTargetException e3) {
                throw new AssertionError(e3);
                    }
                }
            }
