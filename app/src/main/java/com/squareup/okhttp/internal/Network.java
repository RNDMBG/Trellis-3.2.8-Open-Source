            package com.squareup.okhttp.internal;

            import java.net.InetAddress;
            import java.net.UnknownHostException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public interface Network {
                public static final Network DEFAULT = new C12711();

                InetAddress[] resolveInetAddresses(String str) throws UnknownHostException;

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.Network$1 */
/* 24 */        static class C12711 implements Network {
/* 25 */            C12711() {
                    }

                    @Override // com.squareup.okhttp.internal.Network
/* 27 */            public InetAddress[] resolveInetAddresses(String host) throws UnknownHostException {
/* 28 */                if (host == null) {
                            throw new UnknownHostException("host == null");
                        }
/* 29 */                return InetAddress.getAllByName(host);
                    }
                }
            }
