            package com.google.android.exoplayer.util;

            import android.net.Uri;
            import android.os.Build;
            import co.vine.android.util.CommonUtil;
            import com.google.android.exoplayer.upstream.DataSource;
            import java.io.IOException;
            import java.net.URL;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.List;
            import java.util.Locale;
            import java.util.concurrent.ExecutorService;
            import java.util.concurrent.Executors;
            import java.util.concurrent.ScheduledExecutorService;
            import java.util.concurrent.ThreadFactory;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public final class Util {
                public static final int SDK_INT = Build.VERSION.SDK_INT;

/* 43 */        private Util() {
                }

/* 51 */        public static boolean isUrlLocalFile(URL url) {
/* 52 */            return url.getProtocol().equals(CommonUtil.FILE);
                }

/* 63 */        public static boolean areEqual(Object o1, Object o2) {
/* 64 */            if (o1 == null) {
                        return o2 == null;
                    }
                    return o1.equals(o2);
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.util.Util$1 */
/* 72 */        static class ThreadFactoryC08791 implements ThreadFactory {
                    final /* synthetic */ String val$threadName;

/* 73 */            ThreadFactoryC08791(String str) {
/* 74 */                this.val$threadName = str;
                    }

                    @Override // java.util.concurrent.ThreadFactory
/* 76 */            public Thread newThread(Runnable r) {
/* 77 */                return new Thread(r, this.val$threadName);
                    }
                }

/* 73 */        public static ExecutorService newSingleThreadExecutor(String threadName) {
/* 74 */            return Executors.newSingleThreadExecutor(new ThreadFactoryC08791(threadName));
                }

                /* JADX INFO: renamed from: com.google.android.exoplayer.util.Util$2 */
/* 87 */        static class ThreadFactoryC08802 implements ThreadFactory {
                    final /* synthetic */ String val$threadName;

/* 88 */            ThreadFactoryC08802(String str) {
/* 89 */                this.val$threadName = str;
                    }

                    @Override // java.util.concurrent.ThreadFactory
/* 91 */            public Thread newThread(Runnable r) {
/* 92 */                return new Thread(r, this.val$threadName);
                    }
                }

/* 88 */        public static ScheduledExecutorService newSingleThreadScheduledExecutor(String threadName) {
/* 89 */            return Executors.newSingleThreadScheduledExecutor(new ThreadFactoryC08802(threadName));
                }

/* 103 */       public static void closeQuietly(DataSource dataSource) {
                    try {
/* 104 */               dataSource.close();
                    } catch (IOException e) {
                    }
                }

/* 116 */       public static String toLowerInvariant(String text) {
/* 117 */           if (text == null) {
                        return null;
                    }
                    return text.toLowerCase(Locale.US);
                }

/* 127 */       public static Uri parseBaseUri(String uriString) {
/* 128 */           return Uri.parse(uriString.substring(0, uriString.lastIndexOf(47)));
                }

/* 145 */       public static int binarySearchFloor(long[] a, long key, boolean inclusive, boolean stayInBounds) {
/* 146 */           int index = Arrays.binarySearch(a, key);
/* 147 */           if (index < 0) {
                        index = -(index + 2);
                    } else if (!inclusive) {
                        index--;
                    }
                    return stayInBounds ? Math.max(0, index) : index;
                }

/* 166 */       public static int binarySearchCeil(long[] a, long key, boolean inclusive, boolean stayInBounds) {
/* 167 */           int index = Arrays.binarySearch(a, key);
/* 168 */           if (index < 0) {
                        index ^= -1;
                    } else if (!inclusive) {
                        index++;
                    }
                    return stayInBounds ? Math.min(a.length - 1, index) : index;
                }

/* 187 */       public static <T> int binarySearchFloor(List<? extends Comparable<? super T>> list, T key, boolean inclusive, boolean stayInBounds) {
/* 188 */           int index = Collections.binarySearch(list, key);
/* 189 */           if (index < 0) {
                        index = -(index + 2);
                    } else if (!inclusive) {
                        index--;
                    }
                    return stayInBounds ? Math.max(0, index) : index;
                }

/* 209 */       public static <T> int binarySearchCeil(List<? extends Comparable<? super T>> list, T key, boolean inclusive, boolean stayInBounds) {
/* 210 */           int index = Collections.binarySearch(list, key);
/* 211 */           if (index < 0) {
                        index ^= -1;
                    } else if (!inclusive) {
                        index++;
                    }
                    return stayInBounds ? Math.min(list.size() - 1, index) : index;
                }
            }
