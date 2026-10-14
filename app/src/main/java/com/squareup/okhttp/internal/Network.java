            package com.squareup.okhttp.internal;

            import java.net.InetAddress;
            import java.net.UnknownHostException;
            
            
    public interface Network {
                public static final Network DEFAULT = new C12711();

                InetAddress[] resolveInetAddresses(String str) throws UnknownHostException;

                
        static class C12711 implements Network {
            C12711() {
                    }

                    @Override 
            public InetAddress[] resolveInetAddresses(String host) throws UnknownHostException {
                if (host == null) {
                            throw new UnknownHostException("host == null");
                        }
                return InetAddress.getAllByName(host);
                    }
                }
            }
