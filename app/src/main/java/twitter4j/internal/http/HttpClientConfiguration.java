            package twitter4j.internal.http;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface HttpClientConfiguration {
                int getHttpConnectionTimeout();

                int getHttpDefaultMaxPerRoute();

                int getHttpMaxTotalConnections();

                String getHttpProxyHost();

                String getHttpProxyPassword();

                int getHttpProxyPort();

                String getHttpProxyUser();

                int getHttpReadTimeout();

                int getHttpRetryCount();

                int getHttpRetryIntervalSeconds();

                boolean isGZIPEnabled();

                boolean isPrettyDebugEnabled();
            }
