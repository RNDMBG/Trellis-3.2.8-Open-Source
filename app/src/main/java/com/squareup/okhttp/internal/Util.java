            package com.squareup.okhttp.internal;

            import com.squareup.okhttp.internal.spdy.Header;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.Closeable;
            import java.io.File;
            import java.io.IOException;
            import java.io.UnsupportedEncodingException;
            import java.net.ServerSocket;
            import java.net.Socket;
            import java.net.URI;
            import java.net.URL;
            import java.nio.charset.Charset;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.Collections;
            import java.util.LinkedHashMap;
            import java.util.List;
            import java.util.Map;
            import java.util.concurrent.ThreadFactory;
            import java.util.concurrent.TimeUnit;
            import okio.Buffer;
            import okio.ByteString;
            import okio.Source;
            
            
    public final class Util {
                public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
                public static final String[] EMPTY_STRING_ARRAY = new String[0];
                public static final Charset US_ASCII = Charset.forName("US-ASCII");
                public static final Charset UTF_8 = Charset.forName(IoUtils.UTF_8);

        private Util() {
                }

        public static int getEffectivePort(URI uri) {
            return getEffectivePort(uri.getScheme(), uri.getPort());
                }

        public static int getEffectivePort(URL url) {
            return getEffectivePort(url.getProtocol(), url.getPort());
                }

        private static int getEffectivePort(String scheme, int specifiedPort) {
                    return specifiedPort != -1 ? specifiedPort : getDefaultPort(scheme);
                }

        public static int getDefaultPort(String protocol) {
            if ("http".equals(protocol)) {
                        return 80;
                    }
                    return "https".equals(protocol) ? 443 : -1;
                }

        public static void checkOffsetAndCount(long arrayLength, long offset, long count) {
                    if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
                throw new ArrayIndexOutOfBoundsException();
                    }
                }

        public static boolean equal(Object a, Object b) {
                    return a == b || (a != null && a.equals(b));
                }

        public static void closeQuietly(Closeable closeable) {
            if (closeable != null) {
                        try {
                    closeable.close();
                        } catch (RuntimeException rethrown) {
                    throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

       public static void closeQuietly(Socket socket) {
           if (socket != null) {
                        try {
                   socket.close();
                        } catch (RuntimeException rethrown) {
                   throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

       public static void closeQuietly(ServerSocket serverSocket) {
           if (serverSocket != null) {
                        try {
                   serverSocket.close();
                        } catch (RuntimeException rethrown) {
                   throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

       public static void closeAll(Closeable a, Closeable b) throws Throwable {
           Throwable thrown = null;
                    try {
               a.close();
                    } catch (Throwable e) {
               thrown = e;
                    }
                    try {
               b.close();
                    } catch (Throwable e2) {
               if (thrown == null) {
                            thrown = e2;
                        }
                    }
           if (thrown == null) {
                        return;
                    }
           if (thrown instanceof IOException) {
                        throw ((IOException) thrown);
                    }
           if (thrown instanceof RuntimeException) {
                        throw ((RuntimeException) thrown);
                    }
           if (!(thrown instanceof Error)) {
               throw new AssertionError(thrown);
                    }
                    throw ((Error) thrown);
                }

       public static void deleteContents(File dir) throws IOException {
           File[] files = dir.listFiles();
           if (files == null) {
               throw new IOException("not a readable directory: " + dir);
                    }
                    for (File file : files) {
               if (file.isDirectory()) {
                   deleteContents(file);
                        }
               if (!file.delete()) {
                   throw new IOException("failed to delete file: " + file);
                        }
                    }
                }

       public static boolean skipAll(Source in, int timeoutMillis) throws IOException {
           long startNanos = System.nanoTime();
           Buffer skipBuffer = new Buffer();
                    while (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos) < timeoutMillis) {
               long read = in.read(skipBuffer, 2048L);
                        if (read == -1) {
                            return true;
                        }
               skipBuffer.clear();
                    }
           return false;
                }

       public static String hash(String s) {
                    try {
               MessageDigest messageDigest = MessageDigest.getInstance("MD5");
               byte[] md5bytes = messageDigest.digest(s.getBytes(IoUtils.UTF_8));
               return ByteString.m2468of(md5bytes).hex();
                    } catch (UnsupportedEncodingException e) {
               throw new AssertionError(e);
                    } catch (NoSuchAlgorithmException e2) {
               throw new AssertionError(e2);
                    }
                }

       public static ByteString sha1(ByteString s) {
                    try {
               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
               byte[] sha1Bytes = messageDigest.digest(s.toByteArray());
               return ByteString.m2468of(sha1Bytes);
                    } catch (NoSuchAlgorithmException e) {
               throw new AssertionError(e);
                    }
                }

       public static <T> List<T> immutableList(List<T> list) {
           return Collections.unmodifiableList(new ArrayList(list));
                }

       public static <T> List<T> immutableList(T... elements) {
           return Collections.unmodifiableList(Arrays.asList((T[]) elements.clone()));
                }

       public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
           return Collections.unmodifiableMap(new LinkedHashMap(map));
                }

                
       static class ThreadFactoryC12731 implements ThreadFactory {
                    final  boolean val$daemon;
                    final  String val$name;

           ThreadFactoryC12731(String str, boolean z) {
               this.val$name = str;
                        this.val$daemon = z;
                    }

                    @Override 
           public Thread newThread(Runnable runnable) {
               Thread result = new Thread(runnable, this.val$name);
               result.setDaemon(this.val$daemon);
               return result;
                    }
                }

       public static ThreadFactory threadFactory(String name, boolean daemon) {
           return new ThreadFactoryC12731(name, daemon);
                }

       public static List<Header> headerEntries(String... elements) {
           List<Header> result = new ArrayList<>(elements.length / 2);
                    for (int i = 0; i < elements.length; i += 2) {
               result.add(new Header(elements[i], elements[i + 1]));
                    }
           return result;
                }

       public static <T> List<T> intersect(Collection<T> a, Collection<T> b) {
           List<T> result = new ArrayList<>();
                    for (T t : a) {
               if (b.contains(t)) {
                   result.add(t);
                        }
                    }
           return Collections.unmodifiableList(result);
                }
            }
