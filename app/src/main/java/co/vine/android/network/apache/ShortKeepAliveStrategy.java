            package co.vine.android.network.apache;

            import org.apache.http.HeaderElement;
            import org.apache.http.HeaderElementIterator;
            import org.apache.http.HttpResponse;
            import org.apache.http.conn.ConnectionKeepAliveStrategy;
            import org.apache.http.message.BasicHeaderElementIterator;
            import org.apache.http.protocol.HttpContext;
            
            
    class ShortKeepAliveStrategy implements ConnectionKeepAliveStrategy {
                private static final long DEFAULT_KEEP_ALIVE_DURATION = 60000;

        ShortKeepAliveStrategy() {
                }

                @Override 
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Keep-Alive"));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                            try {
                        long timeout = Long.parseLong(value) * 1000;
                                if (timeout > 0) {
                            return timeout;
                                }
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                    return 60000L;
                }
            }
