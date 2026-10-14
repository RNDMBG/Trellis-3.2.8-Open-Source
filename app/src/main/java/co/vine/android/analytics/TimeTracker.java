            package co.vine.android.analytics;

            import android.os.SystemClock;
            import co.vine.android.util.SparseArray;
            import java.util.HashMap;
            
            
    public class TimeTracker {
                private static final SparseArray<HashMap<String, Long>> sTimeTrackers = new SparseArray<>();
                private static final int[] sLOCK = new int[0];

        public static void onEventHappened(String event) {
            onEventHappened(0, event);
                }

        public static void onEventHappened(Object key, String event) {
            onEventHappened(key.hashCode(), event);
                }

        public static void onEventHappened(int key, String event) {
                    synchronized (sLOCK) {
                        try {
                    HashMap<String, Long> sp = sTimeTrackers.get(key);
                    if (sp == null) {
                        sp = new HashMap<>();
                        sTimeTrackers.put(key, sp);
                            }
                    sp.put(event, Long.valueOf(SystemClock.elapsedRealtime()));
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public static boolean onFirstEventHappened(String event) {
            return onFirstEventHappened(0, event);
                }

        public static boolean onFirstEventHappened(Object key, String event) {
            return onFirstEventHappened(key.hashCode(), event);
                }

        public static boolean onFirstEventHappened(int key, String event) {
                    boolean z;
                    synchronized (sLOCK) {
                        try {
                    if (hasEventHappened(key, event)) {
                        z = false;
                            } else {
                        HashMap<String, Long> sp = sTimeTrackers.get(key);
                        if (sp == null) {
                            sp = new HashMap<>();
                            sTimeTrackers.put(key, sp);
                                }
                        sp.put(event, Long.valueOf(SystemClock.elapsedRealtime()));
                        z = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            return z;
                }

        public static boolean hasEventHappened(String event) {
            return hasEventHappened(0, event);
                }

        public static boolean hasEventHappened(Object key, String event) {
            return hasEventHappened(key.hashCode(), event);
                }

        public static boolean hasEventHappened(int key, String event) {
                    boolean z;
                    synchronized (sLOCK) {
                        try {
                    HashMap<String, Long> sp = sTimeTrackers.get(key);
                    z = sp != null && sp.containsKey(event);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

        public static void clear(Object key) {
            clear(key.hashCode());
                }

        public static void clear(int key) {
                    synchronized (sLOCK) {
                        try {
                    sTimeTrackers.remove(key);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public static long timeBetween(String event1, String event2) {
            return timeBetween(0, event1, event2);
                }

        public static long timeBetween(Object key, String event1, String event2) {
            return timeBetween(key.hashCode(), event1, event2);
                }

        public static long timeBetween(int key, String event1, String event2) {
                    long jLongValue = -1;
                    synchronized (sLOCK) {
                        try {
                    HashMap<String, Long> sp = sTimeTrackers.get(key);
                    if (sp != null) {
                       Long t1 = sp.get(event1);
                       if (t1 != null) {
                           Long t2 = sp.get(event2);
                           if (t2 == null) {
                               jLongValue = -2;
                                    } else {
                               jLongValue = t2.longValue() - t1.longValue();
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return jLongValue;
                }
            }
