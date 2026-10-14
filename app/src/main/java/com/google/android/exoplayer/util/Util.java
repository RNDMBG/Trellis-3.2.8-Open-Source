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
            
            
    public final class Util {
                public static final int SDK_INT = Build.VERSION.SDK_INT;

        private Util() {
                }

        public static boolean isUrlLocalFile(URL url) {
            return url.getProtocol().equals(CommonUtil.FILE);
                }

        public static boolean areEqual(Object o1, Object o2) {
            if (o1 == null) {
                        return o2 == null;
                    }
                    return o1.equals(o2);
                }

                
        static class ThreadFactoryC08791 implements ThreadFactory {
                    final  String val$threadName;

            ThreadFactoryC08791(String str) {
                this.val$threadName = str;
                    }

                    @Override 
            public Thread newThread(Runnable r) {
                return new Thread(r, this.val$threadName);
                    }
                }

        public static ExecutorService newSingleThreadExecutor(String threadName) {
            return Executors.newSingleThreadExecutor(new ThreadFactoryC08791(threadName));
                }

                
        static class ThreadFactoryC08802 implements ThreadFactory {
                    final  String val$threadName;

            ThreadFactoryC08802(String str) {
                this.val$threadName = str;
                    }

                    @Override 
            public Thread newThread(Runnable r) {
                return new Thread(r, this.val$threadName);
                    }
                }

        public static ScheduledExecutorService newSingleThreadScheduledExecutor(String threadName) {
            return Executors.newSingleThreadScheduledExecutor(new ThreadFactoryC08802(threadName));
                }

       public static void closeQuietly(DataSource dataSource) {
                    try {
               dataSource.close();
                    } catch (IOException e) {
                    }
                }

       public static String toLowerInvariant(String text) {
           if (text == null) {
                        return null;
                    }
                    return text.toLowerCase(Locale.US);
                }

       public static Uri parseBaseUri(String uriString) {
           return Uri.parse(uriString.substring(0, uriString.lastIndexOf(47)));
                }

       public static int binarySearchFloor(long[] a, long key, boolean inclusive, boolean stayInBounds) {
           int index = Arrays.binarySearch(a, key);
           if (index < 0) {
                        index = -(index + 2);
                    } else if (!inclusive) {
                        index--;
                    }
                    return stayInBounds ? Math.max(0, index) : index;
                }

       public static int binarySearchCeil(long[] a, long key, boolean inclusive, boolean stayInBounds) {
           int index = Arrays.binarySearch(a, key);
           if (index < 0) {
                        index ^= -1;
                    } else if (!inclusive) {
                        index++;
                    }
                    return stayInBounds ? Math.min(a.length - 1, index) : index;
                }

       public static <T> int binarySearchFloor(List<? extends Comparable<? super T>> list, T key, boolean inclusive, boolean stayInBounds) {
           int index = Collections.binarySearch(list, key);
           if (index < 0) {
                        index = -(index + 2);
                    } else if (!inclusive) {
                        index--;
                    }
                    return stayInBounds ? Math.max(0, index) : index;
                }

       public static <T> int binarySearchCeil(List<? extends Comparable<? super T>> list, T key, boolean inclusive, boolean stayInBounds) {
           int index = Collections.binarySearch(list, key);
           if (index < 0) {
                        index ^= -1;
                    } else if (!inclusive) {
                        index++;
                    }
                    return stayInBounds ? Math.min(list.size() - 1, index) : index;
                }
            }
