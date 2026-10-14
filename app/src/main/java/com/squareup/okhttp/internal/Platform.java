            package com.squareup.okhttp.internal;

            import com.squareup.okhttp.Protocol;
            import java.io.IOException;
            import java.lang.reflect.InvocationHandler;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.lang.reflect.Proxy;
            import java.net.InetSocketAddress;
            import java.net.Socket;
            import java.net.SocketException;
            import java.net.URI;
            import java.net.URISyntaxException;
            import java.net.URL;
            import java.util.ArrayList;
            import java.util.List;
            import java.util.logging.Level;
            import javax.net.ssl.SSLSocket;
            import okio.Buffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 64 */    public class Platform {
                private static final Platform PLATFORM = findPlatform();

/* 65 */        public static Platform get() {
/* 66 */            return PLATFORM;
                }

/* 70 */        public String getPrefix() {
/* 71 */            return "OkHttp";
                }

/* 74 */        public void logW(String warning) {
/* 75 */            System.out.println(warning);
                }

/* 78 */        public void tagSocket(Socket socket) throws SocketException {
                }

/* 81 */        public void untagSocket(Socket socket) throws SocketException {
                }

/* 84 */        public URI toUriLenient(URL url) throws URISyntaxException {
/* 85 */            return url.toURI();
                }

/* 95 */        public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
                }

/* 99 */        public String getSelectedProtocol(SSLSocket socket) {
/* 100 */           return null;
                }

/* 104 */       public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
/* 105 */           socket.connect(address, connectTimeout);
                }

/* 115 */       private static Platform findPlatform() {
                    Class<?> openSslSocketClass;
                    String negoClassName;
                    Class<?> negoClass;
                    try {
                        try {
/* 116 */                   openSslSocketClass = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
                        } catch (ClassNotFoundException e) {
                            try {
/* 119 */                       openSslSocketClass = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
                            } catch (ClassNotFoundException e2) {
/* 156 */                       negoClassName = "org.eclipse.jetty.alpn.ALPN";
                                try {
                                    try {
/* 159 */                               negoClass = Class.forName("org.eclipse.jetty.alpn.ALPN");
                                    } catch (ClassNotFoundException e3) {
                                        try {
/* 161 */                                   negoClassName = "org.eclipse.jetty.npn.NextProtoNego";
/* 162 */                                   negoClass = Class.forName("org.eclipse.jetty.npn.NextProtoNego");
                                        } catch (ClassNotFoundException e4) {
/* 175 */                                   return new Platform();
                                        }
                                    }
/* 164 */                           Class<?> providerClass = Class.forName(negoClassName + "$Provider");
/* 165 */                           Class<?> clientProviderClass = Class.forName(negoClassName + "$ClientProvider");
/* 166 */                           Class<?> serverProviderClass = Class.forName(negoClassName + "$ServerProvider");
/* 167 */                           Method putMethod = negoClass.getMethod("put", SSLSocket.class, providerClass);
/* 168 */                           Method getMethod = negoClass.getMethod("get", SSLSocket.class);
/* 169 */                           return new JdkWithJettyBootPlatform(putMethod, getMethod, clientProviderClass, serverProviderClass);
                                } catch (NoSuchMethodException e5) {
/* 175 */                           return new Platform();
                                }
                            }
                        }
/* 123 */               Method setUseSessionTickets = openSslSocketClass.getMethod("setUseSessionTickets", Boolean.TYPE);
/* 124 */               Method setHostname = openSslSocketClass.getMethod("setHostname", String.class);
/* 127 */               Method trafficStatsTagSocket = null;
/* 128 */               Method trafficStatsUntagSocket = null;
                        try {
/* 130 */                   Class<?> trafficStats = Class.forName("android.net.TrafficStats");
/* 131 */                   trafficStatsTagSocket = trafficStats.getMethod("tagSocket", Socket.class);
/* 132 */                   trafficStatsUntagSocket = trafficStats.getMethod("untagSocket", Socket.class);
                        } catch (ClassNotFoundException e6) {
                        } catch (NoSuchMethodException e7) {
                        }
/* 138 */               Method setNpnProtocols = null;
/* 139 */               Method getNpnSelectedProtocol = null;
                        try {
/* 141 */                   setNpnProtocols = openSslSocketClass.getMethod("setNpnProtocols", byte[].class);
/* 142 */                   getNpnSelectedProtocol = openSslSocketClass.getMethod("getNpnSelectedProtocol", new Class[0]);
                        } catch (NoSuchMethodException e8) {
                        }
/* 146 */               return new Android(openSslSocketClass, setUseSessionTickets, setHostname, trafficStatsTagSocket, trafficStatsUntagSocket, setNpnProtocols, getNpnSelectedProtocol, null);
                    } catch (ReflectiveOperationException e9) {
                        return new Platform();
                    }
                }

/* 180 */       private static class Android extends Platform {
                    private final Method getNpnSelectedProtocol;
                    protected final Class<?> openSslSocketClass;
                    private final Method setHostname;
                    private final Method setNpnProtocols;
                    private final Method setUseSessionTickets;
                    private final Method trafficStatsTagSocket;
                    private final Method trafficStatsUntagSocket;

/* 181 */           /* synthetic */ Android(Class x0, Method x1, Method x2, Method x3, Method x4, Method x5, Method x6, Object x7) {
/* 182 */               this(x0, x1, x2, x3, x4, x5, x6);
                    }

/* 197 */           private Android(Class<?> openSslSocketClass, Method setUseSessionTickets, Method setHostname, Method trafficStatsTagSocket, Method trafficStatsUntagSocket, Method setNpnProtocols, Method getNpnSelectedProtocol) {
/* 199 */               this.openSslSocketClass = openSslSocketClass;
/* 200 */               this.setUseSessionTickets = setUseSessionTickets;
/* 201 */               this.setHostname = setHostname;
/* 202 */               this.trafficStatsTagSocket = trafficStatsTagSocket;
/* 203 */               this.trafficStatsUntagSocket = trafficStatsUntagSocket;
/* 204 */               this.setNpnProtocols = setNpnProtocols;
/* 205 */               this.getNpnSelectedProtocol = getNpnSelectedProtocol;
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 210 */           public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
                        try {
/* 211 */                   socket.connect(address, connectTimeout);
                        } catch (SecurityException se) {
/* 215 */                   IOException ioException = new IOException("Exception in connect");
/* 216 */                   ioException.initCause(se);
/* 217 */                   throw ioException;
                        }
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 222 */           public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
/* 223 */               if (this.openSslSocketClass.isInstance(sslSocket)) {
/* 226 */                   if (hostname != null) {
                                try {
/* 228 */                           this.setUseSessionTickets.invoke(sslSocket, true);
/* 229 */                           this.setHostname.invoke(sslSocket, hostname);
                                } catch (IllegalAccessException e) {
/* 233 */                           throw new AssertionError(e);
                                } catch (InvocationTargetException e2) {
/* 231 */                           throw new RuntimeException(e2.getCause());
                                }
                            }
/* 238 */                   if (this.setNpnProtocols != null) {
                                try {
/* 240 */                           Object[] parameters = {concatLengthPrefixed(protocols)};
/* 241 */                           this.setNpnProtocols.invoke(sslSocket, parameters);
                                } catch (IllegalAccessException e3) {
/* 243 */                           throw new AssertionError(e3);
                                } catch (InvocationTargetException e4) {
/* 245 */                           throw new RuntimeException(e4.getCause());
                                }
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 250 */           public String getSelectedProtocol(SSLSocket socket) {
/* 251 */               if (this.getNpnSelectedProtocol != null && this.openSslSocketClass.isInstance(socket)) {
                            try {
/* 254 */                       byte[] npnResult = (byte[]) this.getNpnSelectedProtocol.invoke(socket, new Object[0]);
/* 255 */                       if (npnResult == null) {
                                    return null;
                                }
/* 256 */                       return new String(npnResult, Util.UTF_8);
                            } catch (IllegalAccessException e) {
/* 260 */                       throw new AssertionError(e);
                            } catch (InvocationTargetException e2) {
/* 258 */                       throw new RuntimeException(e2.getCause());
                            }
                        }
                        return null;
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 264 */           public void tagSocket(Socket socket) throws SocketException {
/* 265 */               if (this.trafficStatsTagSocket != null) {
                            try {
/* 268 */                       this.trafficStatsTagSocket.invoke(null, socket);
                            } catch (IllegalAccessException e) {
/* 270 */                       throw new RuntimeException(e);
                            } catch (InvocationTargetException e2) {
/* 272 */                       throw new RuntimeException(e2.getCause());
                            }
                        }
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 276 */           public void untagSocket(Socket socket) throws SocketException {
/* 277 */               if (this.trafficStatsUntagSocket != null) {
                            try {
/* 280 */                       this.trafficStatsUntagSocket.invoke(null, socket);
                            } catch (IllegalAccessException e) {
/* 282 */                       throw new RuntimeException(e);
                            } catch (InvocationTargetException e2) {
/* 284 */                       throw new RuntimeException(e2.getCause());
                            }
                        }
                    }
                }

/* 298 */       private static class JdkWithJettyBootPlatform extends Platform {
                    private final Class<?> clientProviderClass;
                    private final Method getMethod;
                    private final Method putMethod;
                    private final Class<?> serverProviderClass;

/* 299 */           public JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
/* 301 */               this.putMethod = putMethod;
/* 302 */               this.getMethod = getMethod;
/* 303 */               this.clientProviderClass = clientProviderClass;
/* 304 */               this.serverProviderClass = serverProviderClass;
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 308 */           public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
/* 309 */               List<String> names = new ArrayList<>(protocols.size());
                        int size = protocols.size();
                        for (int i = 0; i < size; i++) {
/* 311 */                   Protocol protocol = protocols.get(i);
/* 312 */                   if (protocol != Protocol.HTTP_1_0) {
/* 313 */                       names.add(protocol.toString());
                            }
                        }
                        try {
/* 316 */                   Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
/* 318 */                   this.putMethod.invoke(null, sslSocket, provider);
                        } catch (IllegalAccessException e) {
/* 322 */                   throw new AssertionError(e);
                        } catch (InvocationTargetException e2) {
/* 320 */                   throw new AssertionError(e2);
                        }
                    }

                    @Override // com.squareup.okhttp.internal.Platform
/* 327 */           public String getSelectedProtocol(SSLSocket socket) {
                        try {
                            JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, socket));
/* 330 */                   if (!JettyNegoProvider.access$100(provider) && JettyNegoProvider.access$200(provider) == null) {
/* 331 */                       Internal.logger.log(Level.INFO, "NPN/ALPN callback dropped: SPDY and HTTP/2 are disabled. Is npn-boot or alpn-boot on the boot class path?");
                                return null;
                            }
                            if (JettyNegoProvider.access$100(provider)) {
                                return null;
                            }
                            return JettyNegoProvider.access$200(provider);
                        } catch (IllegalAccessException e) {
/* 339 */                   throw new AssertionError();
                        } catch (InvocationTargetException e2) {
/* 337 */                   throw new AssertionError();
                        }
                    }
                }

/* 346 */       private static class JettyNegoProvider implements InvocationHandler {
                    private final List<String> protocols;
                    private String selected;
                    private boolean unsupported;

/* 347 */           static /* synthetic */ boolean access$100(JettyNegoProvider x0) {
/* 348 */               return x0.unsupported;
                    }

/* 347 */           static /* synthetic */ String access$200(JettyNegoProvider x0) {
/* 348 */               return x0.selected;
                    }

/* 355 */           public JettyNegoProvider(List<String> protocols) {
/* 357 */               this.protocols = protocols;
                    }

                    @Override // java.lang.reflect.InvocationHandler
/* 360 */           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 361 */               String methodName = method.getName();
/* 362 */               Class<?> returnType = method.getReturnType();
/* 363 */               if (args == null) {
/* 364 */                   args = Util.EMPTY_STRING_ARRAY;
                        }
/* 366 */               if (methodName.equals("supports") && Boolean.TYPE == returnType) {
/* 367 */                   return true;
                        }
/* 368 */               if (methodName.equals("unsupported") && Void.TYPE == returnType) {
/* 369 */                   this.unsupported = true;
                            return null;
                        }
/* 371 */               if (methodName.equals("protocols") && args.length == 0) {
/* 372 */                   return this.protocols;
                        }
/* 373 */               if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
/* 375 */                   List<String> peerProtocols = (List) args[0];
                            int size = peerProtocols.size();
                            for (int i = 0; i < size; i++) {
/* 378 */                       if (this.protocols.contains(peerProtocols.get(i))) {
/* 379 */                           String str = peerProtocols.get(i);
                                    this.selected = str;
/* 388 */                           return str;
                                }
                            }
/* 382 */                   String str2 = this.protocols.get(0);
                            this.selected = str2;
/* 388 */                   return str2;
                        }
/* 383 */               if ((methodName.equals("protocolSelected") || methodName.equals("selected")) && args.length == 1) {
/* 385 */                   this.selected = (String) args[0];
                            return null;
                        }
/* 388 */               return method.invoke(this, args);
                    }
                }

/* 397 */       static byte[] concatLengthPrefixed(List<Protocol> protocols) {
/* 398 */           Buffer result = new Buffer();
                    int size = protocols.size();
                    for (int i = 0; i < size; i++) {
/* 400 */               Protocol protocol = protocols.get(i);
/* 401 */               if (protocol != Protocol.HTTP_1_0) {
/* 402 */                   result.writeByte(protocol.toString().length());
/* 403 */                   result.writeUtf8(protocol.toString());
                        }
                    }
/* 405 */           return result.readByteArray();
                }
            }
