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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public final class Util {
                public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
                public static final String[] EMPTY_STRING_ARRAY = new String[0];
                public static final Charset US_ASCII = Charset.forName("US-ASCII");
                public static final Charset UTF_8 = Charset.forName(IoUtils.UTF_8);

/* 55 */        private Util() {
                }

/* 59 */        public static int getEffectivePort(URI uri) {
/* 60 */            return getEffectivePort(uri.getScheme(), uri.getPort());
                }

/* 63 */        public static int getEffectivePort(URL url) {
/* 64 */            return getEffectivePort(url.getProtocol(), url.getPort());
                }

/* 67 */        private static int getEffectivePort(String scheme, int specifiedPort) {
                    return specifiedPort != -1 ? specifiedPort : getDefaultPort(scheme);
                }

/* 71 */        public static int getDefaultPort(String protocol) {
/* 72 */            if ("http".equals(protocol)) {
                        return 80;
                    }
                    return "https".equals(protocol) ? 443 : -1;
                }

/* 77 */        public static void checkOffsetAndCount(long arrayLength, long offset, long count) {
                    if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
/* 79 */                throw new ArrayIndexOutOfBoundsException();
                    }
                }

/* 84 */        public static boolean equal(Object a, Object b) {
                    return a == b || (a != null && a.equals(b));
                }

/* 92 */        public static void closeQuietly(Closeable closeable) {
/* 93 */            if (closeable != null) {
                        try {
/* 95 */                    closeable.close();
                        } catch (RuntimeException rethrown) {
/* 97 */                    throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

/* 107 */       public static void closeQuietly(Socket socket) {
/* 108 */           if (socket != null) {
                        try {
/* 110 */                   socket.close();
                        } catch (RuntimeException rethrown) {
/* 112 */                   throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

/* 122 */       public static void closeQuietly(ServerSocket serverSocket) {
/* 123 */           if (serverSocket != null) {
                        try {
/* 125 */                   serverSocket.close();
                        } catch (RuntimeException rethrown) {
/* 127 */                   throw rethrown;
                        } catch (Exception e) {
                        }
                    }
                }

/* 137 */       public static void closeAll(Closeable a, Closeable b) throws Throwable {
/* 138 */           Throwable thrown = null;
                    try {
/* 140 */               a.close();
                    } catch (Throwable e) {
/* 142 */               thrown = e;
                    }
                    try {
/* 145 */               b.close();
                    } catch (Throwable e2) {
/* 147 */               if (thrown == null) {
                            thrown = e2;
                        }
                    }
/* 149 */           if (thrown == null) {
                        return;
                    }
/* 150 */           if (thrown instanceof IOException) {
                        throw ((IOException) thrown);
                    }
/* 151 */           if (thrown instanceof RuntimeException) {
                        throw ((RuntimeException) thrown);
                    }
/* 152 */           if (!(thrown instanceof Error)) {
/* 153 */               throw new AssertionError(thrown);
                    }
                    throw ((Error) thrown);
                }

/* 160 */       public static void deleteContents(File dir) throws IOException {
/* 161 */           File[] files = dir.listFiles();
/* 162 */           if (files == null) {
/* 163 */               throw new IOException("not a readable directory: " + dir);
                    }
                    for (File file : files) {
/* 166 */               if (file.isDirectory()) {
/* 167 */                   deleteContents(file);
                        }
/* 169 */               if (!file.delete()) {
/* 170 */                   throw new IOException("failed to delete file: " + file);
                        }
                    }
                }

/* 177 */       public static boolean skipAll(Source in, int timeoutMillis) throws IOException {
/* 178 */           long startNanos = System.nanoTime();
/* 179 */           Buffer skipBuffer = new Buffer();
                    while (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos) < timeoutMillis) {
/* 181 */               long read = in.read(skipBuffer, 2048L);
                        if (read == -1) {
                            return true;
                        }
/* 183 */               skipBuffer.clear();
                    }
/* 185 */           return false;
                }

/* 190 */       public static String hash(String s) {
                    try {
/* 191 */               MessageDigest messageDigest = MessageDigest.getInstance("MD5");
/* 192 */               byte[] md5bytes = messageDigest.digest(s.getBytes(IoUtils.UTF_8));
/* 193 */               return ByteString.m2468of(md5bytes).hex();
                    } catch (UnsupportedEncodingException e) {
/* 197 */               throw new AssertionError(e);
                    } catch (NoSuchAlgorithmException e2) {
/* 195 */               throw new AssertionError(e2);
                    }
                }

/* 203 */       public static ByteString sha1(ByteString s) {
                    try {
/* 204 */               MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
/* 205 */               byte[] sha1Bytes = messageDigest.digest(s.toByteArray());
/* 206 */               return ByteString.m2468of(sha1Bytes);
                    } catch (NoSuchAlgorithmException e) {
/* 208 */               throw new AssertionError(e);
                    }
                }

/* 213 */       public static <T> List<T> immutableList(List<T> list) {
/* 214 */           return Collections.unmodifiableList(new ArrayList(list));
                }

/* 218 */       public static <T> List<T> immutableList(T... elements) {
/* 219 */           return Collections.unmodifiableList(Arrays.asList((T[]) elements.clone()));
                }

/* 223 */       public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
/* 224 */           return Collections.unmodifiableMap(new LinkedHashMap(map));
                }

                /* JADX INFO: renamed from: com.squareup.okhttp.internal.Util$1 */
/* 226 */       static class ThreadFactoryC12731 implements ThreadFactory {
                    final /* synthetic */ boolean val$daemon;
                    final /* synthetic */ String val$name;

/* 227 */           ThreadFactoryC12731(String str, boolean z) {
/* 228 */               this.val$name = str;
                        this.val$daemon = z;
                    }

                    @Override // java.util.concurrent.ThreadFactory
/* 229 */           public Thread newThread(Runnable runnable) {
/* 230 */               Thread result = new Thread(runnable, this.val$name);
/* 231 */               result.setDaemon(this.val$daemon);
/* 232 */               return result;
                    }
                }

/* 227 */       public static ThreadFactory threadFactory(String name, boolean daemon) {
/* 228 */           return new ThreadFactoryC12731(name, daemon);
                }

/* 237 */       public static List<Header> headerEntries(String... elements) {
/* 238 */           List<Header> result = new ArrayList<>(elements.length / 2);
                    for (int i = 0; i < elements.length; i += 2) {
/* 240 */               result.add(new Header(elements[i], elements[i + 1]));
                    }
/* 242 */           return result;
                }

/* 249 */       public static <T> List<T> intersect(Collection<T> a, Collection<T> b) {
/* 250 */           List<T> result = new ArrayList<>();
                    for (T t : a) {
/* 252 */               if (b.contains(t)) {
/* 253 */                   result.add(t);
                        }
                    }
/* 256 */           return Collections.unmodifiableList(result);
                }
            }
