            package co.vine.android.util.analytics;

            import android.app.Activity;
            import android.content.Context;
            import android.content.SharedPreferences;
            import android.net.Uri;
            import android.view.ViewGroup;
            import android.widget.LinearLayout;
            import co.vine.android.util.Util;
            import java.util.ArrayList;
            import java.util.Calendar;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.HashMap;
            import java.util.Map;
            
            
    public class BehaviorManager {
                private static final String FIRST_DATE = "first_date";
                private static final int HOUR_DIVIDER = 15;
                private static BehaviorManager sInstance;
                private final SharedPreferences mPref;
                private final SharedPreferences mPrefUrls;
                private final SharedPreferences mPrefUrlsRecents;
                private final BehaviorStorage mStorage = new C05404();

                public interface BehaviorStorage {
                    long getCount(String str, long j);
                }

        static  SharedPreferences access$000(BehaviorManager x0) {
            return x0.mPref;
                }

        public static synchronized BehaviorManager getInstance(Context context) {
                    try {
                        if (sInstance == null) {
                    sInstance = new BehaviorManager(context.getApplicationContext());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
            return sInstance;
                }

        private BehaviorManager(Context context) {
            this.mPref = Util.getSharedPrefs(context, "BehaviorManager");
            long val = this.mPref.getLong(FIRST_DATE, 0L);
                    if (val == 0) {
                this.mPref.edit().putLong(FIRST_DATE, System.currentTimeMillis()).apply();
                    }
            this.mPrefUrls = Util.getSharedPrefs(context, "BehaviorManager_urls");
            this.mPrefUrlsRecents = Util.getSharedPrefs(context, "BehaviorManager_url_recents");
                }

        public void onVideoLoaded() {
            Calendar c = Calendar.getInstance();
            String key = getKey(c.get(7), c.get(11), c.get(12) / 15);
            long val = this.mPref.getLong(key, 0L);
            this.mPref.edit().putLong(key, 1 + val).apply();
                }

        public void onFetchPosts(String url, int page) {
            truncateIfNeeded();
            String path = Uri.parse(url).getPath();
            String key = path + "?page=" + page;
            long oldValue = this.mPrefUrls.getLong(path, 0L);
            SharedPreferences.Editor edit = this.mPrefUrls.edit();
            edit.putLong(key, oldValue + 1);
            long oldValue2 = this.mPrefUrls.getLong(path, 0L);
            edit.putLong(path, oldValue2 + 1);
            edit.apply();
            this.mPrefUrlsRecents.edit().putLong(key, System.currentTimeMillis()).apply();
                }

        private void truncateIfNeeded() {
            Map<String, ?> all = this.mPrefUrlsRecents.getAll();
            if (all.size() > 100) {
                ArrayList<String> keys = new ArrayList<>(all.keySet());
                Collections.sort(keys, new C05371(all));
                SharedPreferences.Editor editRecents = this.mPrefUrlsRecents.edit();
                SharedPreferences.Editor edit = this.mPrefUrls.edit();
                        for (int i = 0; i < 50; i++) {
                    String key = keys.get(i);
                    edit.remove(key);
                    editRecents.remove(key);
                        }
                edit.apply();
                editRecents.apply();
                    }
                }

                
        class C05371 implements Comparator<String> {
                    final  Map val$all;

            C05371(Map map) {
                        this.val$all = map;
                    }


                    
            public int compare(String lhs, String rhs) {
                long diff = ((Long) this.val$all.get(lhs)).longValue() - ((Long) this.val$all.get(rhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }

       private static String getKey(int dayOfWeek, int hourOfDay, int minuteByFifteenth) {
           return String.valueOf((dayOfWeek * 1000) + (hourOfDay * 10) + minuteByFifteenth);
                }

       public String[] getUsagesStringsByTimelineForStatView() {
           Map<String, ?> all = this.mPrefUrls.getAll();
           ArrayList<String> keys = new ArrayList<>(all.keySet());
           Collections.sort(keys, new C05382(all));
           int size = Math.min(keys.size(), 10);
           String[] r = new String[size];
                    for (int i = 0; i < size; i++) {
               String key = keys.get(i);
               r[i] = key + " (" + all.get(keys.get(i)) + ")";
                    }
           return r;
                }

                
       class C05382 implements Comparator<String> {
                    final  Map val$all;

           C05382(Map map) {
                        this.val$all = map;
                    }


                    
           public int compare(String lhs, String rhs) {
               long diff = ((Long) this.val$all.get(rhs)).longValue() - ((Long) this.val$all.get(lhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }

       public String[] getTopUsagesTimelineEndpoints() {
           Map<String, ?> all = this.mPrefUrls.getAll();
           ArrayList<String> keys = new ArrayList<>(all.keySet());
           Collections.sort(keys, new C05393(all));
           int size = Math.min(keys.size(), 10);
           String[] r = new String[size];
                    for (int i = 0; i < size; i++) {
               r[i] = keys.get(i);
                    }
           return r;
                }

                
       class C05393 implements Comparator<String> {
                    final  Map val$all;

           C05393(Map map) {
                        this.val$all = map;
                    }


                    
           public int compare(String lhs, String rhs) {
               long diff = ((Long) this.val$all.get(rhs)).longValue() - ((Long) this.val$all.get(lhs)).longValue();
                        if (diff > 0) {
                            if (diff >= 2147483647L) {
                                return Integer.MAX_VALUE;
                            }
                   return (int) diff;
                        }
                        if (diff == 0) {
                   return 0;
                        }
                        if (diff <= -2147483648L) {
                            return Integer.MIN_VALUE;
                        }
                        return (int) diff;
                    }
                }

       public long[] getUsagesByTimeOfDay(int dayOfWeek) {
           long[] usages = new long[240];
                    for (int j = 0; j <= 23; j++) {
                        for (int k = 0; k <= 3; k++) {
                   int i = (j * 10) + k;
                            usages[i] = usages[i] + this.mPref.getLong(getKey(dayOfWeek, j, k), 0L);
                        }
                    }
           return usages;
                }

       public long[] getUsagesByHourOfDay(int dayOfWeek) {
           long[] usages = new long[240];
                    for (int j = 0; j <= 23; j++) {
                        for (int k = 0; k <= 3; k++) {
                   usages[j] = usages[j] + this.mPref.getLong(getKey(dayOfWeek, j, k), 0L);
                        }
                    }
           return usages;
                }

       public long[] getUsagesByTimeOfDay() {
           long[] usages = new long[240];
                    for (int i = 1; i <= 7; i++) {
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
                       int i2 = (j * 10) + k;
                                usages[i2] = usages[i2] + this.mPref.getLong(getKey(i, j, k), 0L);
                            }
                        }
                    }
           return usages;
                }

       public long[] getUsagesByHourOfDay() {
           long[] usages = new long[24];
                    for (int i = 1; i <= 7; i++) {
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
                       usages[j] = usages[j] + this.mPref.getLong(getKey(i, j, k), 0L);
                            }
                        }
                    }
           return usages;
                }

       public long[] getUsagesByDateOfWeek() {
           long[] usages = new long[7];
                    for (int i = 1; i <= 7; i++) {
               int sum = 0;
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
                       sum = (int) (((long) sum) + this.mPref.getLong(getKey(i, j, k), 0L));
                            }
                        }
               usages[i - 1] = sum;
                    }
           return usages;
                }

       public void showUsageView(Activity activity) {
           LinearLayout view = new LinearLayout(activity);
           view.setGravity(17);
           BehaviorUsageView behaviorView = new BehaviorUsageView(activity);
           view.addView(behaviorView);
           activity.getWindow().addContentView(view, new ViewGroup.LayoutParams(-1, -1));
                }

       public long searchForBestDelay(long start, long end) {
           return searchForBestDelay(start, end, this.mStorage);
                }

                
       class C05404 implements BehaviorStorage {
           C05404() {
                    }

                    @Override 
           public long getCount(String key, long defaultValue) {
               return BehaviorManager.access$000(BehaviorManager.this).getLong(key, defaultValue);
                    }
                }

       public static long searchForBestDelay(long start, long end, BehaviorStorage storage) {
           Calendar cal = Calendar.getInstance();
           HashMap<Long, Long> counts = new HashMap<>();
           ArrayList<Long> times = new ArrayList<>();
                    for (long time = start; time < end; time += 900000) {
               cal.setTimeInMillis(time);
               int hourOfDay = cal.get(11);
               int minute = cal.get(12) / 15;
               int dayOfWeek = cal.get(7);
               long count = storage.getCount(getKey(dayOfWeek, hourOfDay, minute), 0L);
               counts.put(Long.valueOf(time), Long.valueOf(count));
               times.add(Long.valueOf(time));
                    }
           Collections.sort(times, new C05415(counts));
           long time2 = times.get(0).longValue() - 900000;
           return Math.max(time2, start);
                }

                
       static class C05415 implements Comparator<Long> {
                    final  HashMap val$counts;

           C05415(HashMap map) {
               this.val$counts = map;
                    }


                    
           public int compare(Long lhs, Long rhs) {
               long diff = ((Long) this.val$counts.get(rhs)).longValue() - ((Long) this.val$counts.get(lhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }
            }
