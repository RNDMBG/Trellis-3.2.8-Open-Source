            package co.vine.android.analytics;

            import android.os.SystemClock;
            import co.vine.android.util.SparseArray;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class TimeTracker {
                private static final SparseArray<HashMap<String, Long>> sTimeTrackers = new SparseArray<>();
                private static final int[] sLOCK = new int[0];

/* 18 */        public static void onEventHappened(String event) {
/* 19 */            onEventHappened(0, event);
                }

/* 22 */        public static void onEventHappened(Object key, String event) {
/* 23 */            onEventHappened(key.hashCode(), event);
                }

/* 26 */        public static void onEventHappened(int key, String event) {
                    synchronized (sLOCK) {
                        try {
/* 28 */                    HashMap<String, Long> sp = sTimeTrackers.get(key);
/* 29 */                    if (sp == null) {
/* 30 */                        sp = new HashMap<>();
/* 31 */                        sTimeTrackers.put(key, sp);
                            }
/* 33 */                    sp.put(event, Long.valueOf(SystemClock.elapsedRealtime()));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 37 */        public static boolean onFirstEventHappened(String event) {
/* 38 */            return onFirstEventHappened(0, event);
                }

/* 41 */        public static boolean onFirstEventHappened(Object key, String event) {
/* 42 */            return onFirstEventHappened(key.hashCode(), event);
                }

/* 45 */        public static boolean onFirstEventHappened(int key, String event) {
                    boolean z;
                    synchronized (sLOCK) {
                        try {
/* 47 */                    if (hasEventHappened(key, event)) {
/* 48 */                        z = false;
                            } else {
/* 50 */                        HashMap<String, Long> sp = sTimeTrackers.get(key);
/* 51 */                        if (sp == null) {
/* 52 */                            sp = new HashMap<>();
/* 53 */                            sTimeTrackers.put(key, sp);
                                }
/* 55 */                        sp.put(event, Long.valueOf(SystemClock.elapsedRealtime()));
/* 56 */                        z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 56 */            return z;
                }

/* 61 */        public static boolean hasEventHappened(String event) {
/* 62 */            return hasEventHappened(0, event);
                }

/* 65 */        public static boolean hasEventHappened(Object key, String event) {
/* 66 */            return hasEventHappened(key.hashCode(), event);
                }

/* 69 */        public static boolean hasEventHappened(int key, String event) {
                    boolean z;
                    synchronized (sLOCK) {
                        try {
/* 71 */                    HashMap<String, Long> sp = sTimeTrackers.get(key);
/* 72 */                    z = sp != null && sp.containsKey(event);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

/* 76 */        public static void clear(Object key) {
/* 77 */            clear(key.hashCode());
                }

/* 80 */        public static void clear(int key) {
                    synchronized (sLOCK) {
                        try {
/* 82 */                    sTimeTrackers.remove(key);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 86 */        public static long timeBetween(String event1, String event2) {
/* 87 */            return timeBetween(0, event1, event2);
                }

/* 90 */        public static long timeBetween(Object key, String event1, String event2) {
/* 91 */            return timeBetween(key.hashCode(), event1, event2);
                }

/* 94 */        public static long timeBetween(int key, String event1, String event2) {
                    long jLongValue = -1;
                    synchronized (sLOCK) {
                        try {
/* 96 */                    HashMap<String, Long> sp = sTimeTrackers.get(key);
/* 97 */                    if (sp != null) {
/* 100 */                       Long t1 = sp.get(event1);
/* 101 */                       if (t1 != null) {
/* 104 */                           Long t2 = sp.get(event2);
/* 105 */                           if (t2 == null) {
/* 106 */                               jLongValue = -2;
                                    } else {
/* 108 */                               jLongValue = t2.longValue() - t1.longValue();
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 108 */           return jLongValue;
                }
            }
