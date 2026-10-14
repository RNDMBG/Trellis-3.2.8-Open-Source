            package co.vine.android.network.apache;

            import org.apache.http.HeaderElement;
            import org.apache.http.HeaderElementIterator;
            import org.apache.http.HttpResponse;
            import org.apache.http.conn.ConnectionKeepAliveStrategy;
            import org.apache.http.message.BasicHeaderElementIterator;
            import org.apache.http.protocol.HttpContext;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    class ShortKeepAliveStrategy implements ConnectionKeepAliveStrategy {
                private static final long DEFAULT_KEEP_ALIVE_DURATION = 60000;

/* 19 */        ShortKeepAliveStrategy() {
                }

                @Override // org.apache.http.conn.ConnectionKeepAliveStrategy
/* 27 */        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
/* 28 */            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
/* 35 */            while (it.hasNext()) {
/* 36 */                HeaderElement he = it.nextElement();
/* 37 */                String param = he.getName();
/* 38 */                String value = he.getValue();
/* 39 */                if (value != null && "timeout".equalsIgnoreCase(param)) {
                            try {
/* 41 */                        long timeout = Long.parseLong(value) * 1000;
                                if (timeout > 0) {
/* 50 */                            return timeout;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                    return 60000L;
                }
            }
