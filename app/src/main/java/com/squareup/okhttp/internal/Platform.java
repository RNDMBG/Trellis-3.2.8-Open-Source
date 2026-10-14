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
            
            
    public class Platform {
                private static final Platform PLATFORM = findPlatform();

        public static Platform get() {
            return PLATFORM;
                }

        public String getPrefix() {
            return "OkHttp";
                }

        public void logW(String warning) {
            System.out.println(warning);
                }

        public void tagSocket(Socket socket) throws SocketException {
                }

        public void untagSocket(Socket socket) throws SocketException {
                }

        public URI toUriLenient(URL url) throws URISyntaxException {
            return url.toURI();
                }

        public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
                }

        public String getSelectedProtocol(SSLSocket socket) {
           return null;
                }

       public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
           socket.connect(address, connectTimeout);
                }

       private static Platform findPlatform() {
                    Class<?> openSslSocketClass;
                    String negoClassName;
                    Class<?> negoClass;
                    try {
                        try {
                   openSslSocketClass = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
                        } catch (ClassNotFoundException e) {
                            try {
                       openSslSocketClass = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
                            } catch (ClassNotFoundException e2) {
                       negoClassName = "org.eclipse.jetty.alpn.ALPN";
                                try {
                                    try {
                               negoClass = Class.forName("org.eclipse.jetty.alpn.ALPN");
                                    } catch (ClassNotFoundException e3) {
                                        try {
                                   negoClassName = "org.eclipse.jetty.npn.NextProtoNego";
                                   negoClass = Class.forName("org.eclipse.jetty.npn.NextProtoNego");
                                        } catch (ClassNotFoundException e4) {
                                   return new Platform();
                                        }
                                    }
                           Class<?> providerClass = Class.forName(negoClassName + "$Provider");
                           Class<?> clientProviderClass = Class.forName(negoClassName + "$ClientProvider");
                           Class<?> serverProviderClass = Class.forName(negoClassName + "$ServerProvider");
                           Method putMethod = negoClass.getMethod("put", SSLSocket.class, providerClass);
                           Method getMethod = negoClass.getMethod("get", SSLSocket.class);
                           return new JdkWithJettyBootPlatform(putMethod, getMethod, clientProviderClass, serverProviderClass);
                                } catch (NoSuchMethodException e5) {
                           return new Platform();
                                }
                            }
                        }
               Method setUseSessionTickets = openSslSocketClass.getMethod("setUseSessionTickets", Boolean.TYPE);
               Method setHostname = openSslSocketClass.getMethod("setHostname", String.class);
               Method trafficStatsTagSocket = null;
               Method trafficStatsUntagSocket = null;
                        try {
                   Class<?> trafficStats = Class.forName("android.net.TrafficStats");
                   trafficStatsTagSocket = trafficStats.getMethod("tagSocket", Socket.class);
                   trafficStatsUntagSocket = trafficStats.getMethod("untagSocket", Socket.class);
                        } catch (ClassNotFoundException e6) {
                        } catch (NoSuchMethodException e7) {
                        }
               Method setNpnProtocols = null;
               Method getNpnSelectedProtocol = null;
                        try {
                   setNpnProtocols = openSslSocketClass.getMethod("setNpnProtocols", byte[].class);
                   getNpnSelectedProtocol = openSslSocketClass.getMethod("getNpnSelectedProtocol", new Class[0]);
                        } catch (NoSuchMethodException e8) {
                        }
               return new Android(openSslSocketClass, setUseSessionTickets, setHostname, trafficStatsTagSocket, trafficStatsUntagSocket, setNpnProtocols, getNpnSelectedProtocol, null);
                    } catch (ReflectiveOperationException e9) {
                        return new Platform();
                    }
                }

       private static class Android extends Platform {
                    private final Method getNpnSelectedProtocol;
                    protected final Class<?> openSslSocketClass;
                    private final Method setHostname;
                    private final Method setNpnProtocols;
                    private final Method setUseSessionTickets;
                    private final Method trafficStatsTagSocket;
                    private final Method trafficStatsUntagSocket;

            Android(Class x0, Method x1, Method x2, Method x3, Method x4, Method x5, Method x6, Object x7) {
               this(x0, x1, x2, x3, x4, x5, x6);
                    }

           private Android(Class<?> openSslSocketClass, Method setUseSessionTickets, Method setHostname, Method trafficStatsTagSocket, Method trafficStatsUntagSocket, Method setNpnProtocols, Method getNpnSelectedProtocol) {
               this.openSslSocketClass = openSslSocketClass;
               this.setUseSessionTickets = setUseSessionTickets;
               this.setHostname = setHostname;
               this.trafficStatsTagSocket = trafficStatsTagSocket;
               this.trafficStatsUntagSocket = trafficStatsUntagSocket;
               this.setNpnProtocols = setNpnProtocols;
               this.getNpnSelectedProtocol = getNpnSelectedProtocol;
                    }

                    @Override 
           public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
                        try {
                   socket.connect(address, connectTimeout);
                        } catch (SecurityException se) {
                   IOException ioException = new IOException("Exception in connect");
                   ioException.initCause(se);
                   throw ioException;
                        }
                    }

                    @Override 
           public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
               if (this.openSslSocketClass.isInstance(sslSocket)) {
                   if (hostname != null) {
                                try {
                           this.setUseSessionTickets.invoke(sslSocket, true);
                           this.setHostname.invoke(sslSocket, hostname);
                                } catch (IllegalAccessException e) {
                           throw new AssertionError(e);
                                } catch (InvocationTargetException e2) {
                           throw new RuntimeException(e2.getCause());
                                }
                            }
                   if (this.setNpnProtocols != null) {
                                try {
                           Object[] parameters = {concatLengthPrefixed(protocols)};
                           this.setNpnProtocols.invoke(sslSocket, parameters);
                                } catch (IllegalAccessException e3) {
                           throw new AssertionError(e3);
                                } catch (InvocationTargetException e4) {
                           throw new RuntimeException(e4.getCause());
                                }
                            }
                        }
                    }

                    @Override 
           public String getSelectedProtocol(SSLSocket socket) {
               if (this.getNpnSelectedProtocol != null && this.openSslSocketClass.isInstance(socket)) {
                            try {
                       byte[] npnResult = (byte[]) this.getNpnSelectedProtocol.invoke(socket, new Object[0]);
                       if (npnResult == null) {
                                    return null;
                                }
                       return new String(npnResult, Util.UTF_8);
                            } catch (IllegalAccessException e) {
                       throw new AssertionError(e);
                            } catch (InvocationTargetException e2) {
                       throw new RuntimeException(e2.getCause());
                            }
                        }
                        return null;
                    }

                    @Override 
           public void tagSocket(Socket socket) throws SocketException {
               if (this.trafficStatsTagSocket != null) {
                            try {
                       this.trafficStatsTagSocket.invoke(null, socket);
                            } catch (IllegalAccessException e) {
                       throw new RuntimeException(e);
                            } catch (InvocationTargetException e2) {
                       throw new RuntimeException(e2.getCause());
                            }
                        }
                    }

                    @Override 
           public void untagSocket(Socket socket) throws SocketException {
               if (this.trafficStatsUntagSocket != null) {
                            try {
                       this.trafficStatsUntagSocket.invoke(null, socket);
                            } catch (IllegalAccessException e) {
                       throw new RuntimeException(e);
                            } catch (InvocationTargetException e2) {
                       throw new RuntimeException(e2.getCause());
                            }
                        }
                    }
                }

       private static class JdkWithJettyBootPlatform extends Platform {
                    private final Class<?> clientProviderClass;
                    private final Method getMethod;
                    private final Method putMethod;
                    private final Class<?> serverProviderClass;

           public JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
               this.putMethod = putMethod;
               this.getMethod = getMethod;
               this.clientProviderClass = clientProviderClass;
               this.serverProviderClass = serverProviderClass;
                    }

                    @Override 
           public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
               List<String> names = new ArrayList<>(protocols.size());
                        int size = protocols.size();
                        for (int i = 0; i < size; i++) {
                   Protocol protocol = protocols.get(i);
                   if (protocol != Protocol.HTTP_1_0) {
                       names.add(protocol.toString());
                            }
                        }
                        try {
                   Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
                   this.putMethod.invoke(null, sslSocket, provider);
                        } catch (IllegalAccessException e) {
                   throw new AssertionError(e);
                        } catch (InvocationTargetException e2) {
                   throw new AssertionError(e2);
                        }
                    }

                    @Override 
           public String getSelectedProtocol(SSLSocket socket) {
                        try {
                            JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, socket));
                   if (!JettyNegoProvider.access$100(provider) && JettyNegoProvider.access$200(provider) == null) {
                       Internal.logger.log(Level.INFO, "NPN/ALPN callback dropped: SPDY and HTTP/2 are disabled. Is npn-boot or alpn-boot on the boot class path?");
                                return null;
                            }
                            if (JettyNegoProvider.access$100(provider)) {
                                return null;
                            }
                            return JettyNegoProvider.access$200(provider);
                        } catch (IllegalAccessException e) {
                   throw new AssertionError();
                        } catch (InvocationTargetException e2) {
                   throw new AssertionError();
                        }
                    }
                }

       private static class JettyNegoProvider implements InvocationHandler {
                    private final List<String> protocols;
                    private String selected;
                    private boolean unsupported;

           static  boolean access$100(JettyNegoProvider x0) {
               return x0.unsupported;
                    }

           static  String access$200(JettyNegoProvider x0) {
               return x0.selected;
                    }

           public JettyNegoProvider(List<String> protocols) {
               this.protocols = protocols;
                    }

                    @Override 
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               String methodName = method.getName();
               Class<?> returnType = method.getReturnType();
               if (args == null) {
                   args = Util.EMPTY_STRING_ARRAY;
                        }
               if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                   return true;
                        }
               if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                   this.unsupported = true;
                            return null;
                        }
               if (methodName.equals("protocols") && args.length == 0) {
                   return this.protocols;
                        }
               if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                   List<String> peerProtocols = (List) args[0];
                            int size = peerProtocols.size();
                            for (int i = 0; i < size; i++) {
                       if (this.protocols.contains(peerProtocols.get(i))) {
                           String str = peerProtocols.get(i);
                                    this.selected = str;
                           return str;
                                }
                            }
                   String str2 = this.protocols.get(0);
                            this.selected = str2;
                   return str2;
                        }
               if ((methodName.equals("protocolSelected") || methodName.equals("selected")) && args.length == 1) {
                   this.selected = (String) args[0];
                            return null;
                        }
               return method.invoke(this, args);
                    }
                }

       static byte[] concatLengthPrefixed(List<Protocol> protocols) {
           Buffer result = new Buffer();
                    int size = protocols.size();
                    for (int i = 0; i < size; i++) {
               Protocol protocol = protocols.get(i);
               if (protocol != Protocol.HTTP_1_0) {
                   result.writeByte(protocol.toString().length());
                   result.writeUtf8(protocol.toString());
                        }
                    }
           return result.readByteArray();
                }
            }
