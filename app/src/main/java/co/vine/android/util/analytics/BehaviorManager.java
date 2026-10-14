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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 22 */    public class BehaviorManager {
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

/* 23 */        static /* synthetic */ SharedPreferences access$000(BehaviorManager x0) {
/* 24 */            return x0.mPref;
                }

/* 33 */        public static synchronized BehaviorManager getInstance(Context context) {
                    try {
                        if (sInstance == null) {
/* 35 */                    sInstance = new BehaviorManager(context.getApplicationContext());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
/* 37 */            return sInstance;
                }

/* 39 */        private BehaviorManager(Context context) {
/* 41 */            this.mPref = Util.getSharedPrefs(context, "BehaviorManager");
/* 42 */            long val = this.mPref.getLong(FIRST_DATE, 0L);
                    if (val == 0) {
/* 44 */                this.mPref.edit().putLong(FIRST_DATE, System.currentTimeMillis()).apply();
                    }
/* 46 */            this.mPrefUrls = Util.getSharedPrefs(context, "BehaviorManager_urls");
/* 47 */            this.mPrefUrlsRecents = Util.getSharedPrefs(context, "BehaviorManager_url_recents");
                }

/* 50 */        public void onVideoLoaded() {
/* 51 */            Calendar c = Calendar.getInstance();
/* 52 */            String key = getKey(c.get(7), c.get(11), c.get(12) / 15);
/* 55 */            long val = this.mPref.getLong(key, 0L);
/* 56 */            this.mPref.edit().putLong(key, 1 + val).apply();
                }

/* 59 */        public void onFetchPosts(String url, int page) {
/* 60 */            truncateIfNeeded();
/* 61 */            String path = Uri.parse(url).getPath();
/* 62 */            String key = path + "?page=" + page;
/* 63 */            long oldValue = this.mPrefUrls.getLong(path, 0L);
/* 64 */            SharedPreferences.Editor edit = this.mPrefUrls.edit();
/* 65 */            edit.putLong(key, oldValue + 1);
/* 66 */            long oldValue2 = this.mPrefUrls.getLong(path, 0L);
/* 67 */            edit.putLong(path, oldValue2 + 1);
/* 68 */            edit.apply();
/* 69 */            this.mPrefUrlsRecents.edit().putLong(key, System.currentTimeMillis()).apply();
                }

/* 72 */        private void truncateIfNeeded() {
/* 73 */            Map<String, ?> all = this.mPrefUrlsRecents.getAll();
/* 74 */            if (all.size() > 100) {
/* 75 */                ArrayList<String> keys = new ArrayList<>(all.keySet());
/* 76 */                Collections.sort(keys, new C05371(all));
/* 88 */                SharedPreferences.Editor editRecents = this.mPrefUrlsRecents.edit();
/* 89 */                SharedPreferences.Editor edit = this.mPrefUrls.edit();
                        for (int i = 0; i < 50; i++) {
/* 91 */                    String key = keys.get(i);
/* 92 */                    edit.remove(key);
/* 93 */                    editRecents.remove(key);
                        }
/* 95 */                edit.apply();
/* 96 */                editRecents.apply();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorManager$1 */
/* 74 */        class C05371 implements Comparator<String> {
                    final /* synthetic */ Map val$all;

/* 75 */            C05371(Map map) {
                        this.val$all = map;
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 79 */            public int compare(String lhs, String rhs) {
/* 80 */                long diff = ((Long) this.val$all.get(lhs)).longValue() - ((Long) this.val$all.get(rhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }

/* 100 */       private static String getKey(int dayOfWeek, int hourOfDay, int minuteByFifteenth) {
/* 101 */           return String.valueOf((dayOfWeek * 1000) + (hourOfDay * 10) + minuteByFifteenth);
                }

/* 104 */       public String[] getUsagesStringsByTimelineForStatView() {
/* 105 */           Map<String, ?> all = this.mPrefUrls.getAll();
/* 106 */           ArrayList<String> keys = new ArrayList<>(all.keySet());
/* 107 */           Collections.sort(keys, new C05382(all));
/* 120 */           int size = Math.min(keys.size(), 10);
/* 121 */           String[] r = new String[size];
                    for (int i = 0; i < size; i++) {
/* 123 */               String key = keys.get(i);
/* 124 */               r[i] = key + " (" + all.get(keys.get(i)) + ")";
                    }
/* 126 */           return r;
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorManager$2 */
/* 105 */       class C05382 implements Comparator<String> {
                    final /* synthetic */ Map val$all;

/* 106 */           C05382(Map map) {
                        this.val$all = map;
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 110 */           public int compare(String lhs, String rhs) {
/* 111 */               long diff = ((Long) this.val$all.get(rhs)).longValue() - ((Long) this.val$all.get(lhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }

/* 129 */       public String[] getTopUsagesTimelineEndpoints() {
/* 130 */           Map<String, ?> all = this.mPrefUrls.getAll();
/* 131 */           ArrayList<String> keys = new ArrayList<>(all.keySet());
/* 132 */           Collections.sort(keys, new C05393(all));
/* 147 */           int size = Math.min(keys.size(), 10);
/* 148 */           String[] r = new String[size];
                    for (int i = 0; i < size; i++) {
/* 150 */               r[i] = keys.get(i);
                    }
/* 152 */           return r;
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorManager$3 */
/* 130 */       class C05393 implements Comparator<String> {
                    final /* synthetic */ Map val$all;

/* 131 */           C05393(Map map) {
                        this.val$all = map;
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 135 */           public int compare(String lhs, String rhs) {
/* 136 */               long diff = ((Long) this.val$all.get(rhs)).longValue() - ((Long) this.val$all.get(lhs)).longValue();
                        if (diff > 0) {
                            if (diff >= 2147483647L) {
                                return Integer.MAX_VALUE;
                            }
/* 138 */                   return (int) diff;
                        }
                        if (diff == 0) {
/* 140 */                   return 0;
                        }
                        if (diff <= -2147483648L) {
                            return Integer.MIN_VALUE;
                        }
                        return (int) diff;
                    }
                }

/* 155 */       public long[] getUsagesByTimeOfDay(int dayOfWeek) {
/* 156 */           long[] usages = new long[240];
                    for (int j = 0; j <= 23; j++) {
                        for (int k = 0; k <= 3; k++) {
/* 159 */                   int i = (j * 10) + k;
                            usages[i] = usages[i] + this.mPref.getLong(getKey(dayOfWeek, j, k), 0L);
                        }
                    }
/* 162 */           return usages;
                }

/* 165 */       public long[] getUsagesByHourOfDay(int dayOfWeek) {
/* 166 */           long[] usages = new long[240];
                    for (int j = 0; j <= 23; j++) {
                        for (int k = 0; k <= 3; k++) {
/* 169 */                   usages[j] = usages[j] + this.mPref.getLong(getKey(dayOfWeek, j, k), 0L);
                        }
                    }
/* 172 */           return usages;
                }

/* 175 */       public long[] getUsagesByTimeOfDay() {
/* 176 */           long[] usages = new long[240];
                    for (int i = 1; i <= 7; i++) {
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
/* 180 */                       int i2 = (j * 10) + k;
                                usages[i2] = usages[i2] + this.mPref.getLong(getKey(i, j, k), 0L);
                            }
                        }
                    }
/* 184 */           return usages;
                }

/* 187 */       public long[] getUsagesByHourOfDay() {
/* 188 */           long[] usages = new long[24];
                    for (int i = 1; i <= 7; i++) {
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
/* 192 */                       usages[j] = usages[j] + this.mPref.getLong(getKey(i, j, k), 0L);
                            }
                        }
                    }
/* 196 */           return usages;
                }

/* 199 */       public long[] getUsagesByDateOfWeek() {
/* 200 */           long[] usages = new long[7];
                    for (int i = 1; i <= 7; i++) {
/* 202 */               int sum = 0;
                        for (int j = 0; j <= 23; j++) {
                            for (int k = 0; k <= 3; k++) {
/* 205 */                       sum = (int) (((long) sum) + this.mPref.getLong(getKey(i, j, k), 0L));
                            }
                        }
/* 208 */               usages[i - 1] = sum;
                    }
/* 210 */           return usages;
                }

/* 213 */       public void showUsageView(Activity activity) {
/* 214 */           LinearLayout view = new LinearLayout(activity);
/* 215 */           view.setGravity(17);
/* 216 */           BehaviorUsageView behaviorView = new BehaviorUsageView(activity);
/* 217 */           view.addView(behaviorView);
/* 218 */           activity.getWindow().addContentView(view, new ViewGroup.LayoutParams(-1, -1));
                }

/* 224 */       public long searchForBestDelay(long start, long end) {
/* 225 */           return searchForBestDelay(start, end, this.mStorage);
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorManager$4 */
/* 226 */       class C05404 implements BehaviorStorage {
/* 227 */           C05404() {
                    }

                    @Override // co.vine.android.util.analytics.BehaviorManager.BehaviorStorage
/* 230 */           public long getCount(String key, long defaultValue) {
/* 231 */               return BehaviorManager.access$000(BehaviorManager.this).getLong(key, defaultValue);
                    }
                }

/* 242 */       public static long searchForBestDelay(long start, long end, BehaviorStorage storage) {
/* 243 */           Calendar cal = Calendar.getInstance();
/* 244 */           HashMap<Long, Long> counts = new HashMap<>();
/* 245 */           ArrayList<Long> times = new ArrayList<>();
                    for (long time = start; time < end; time += 900000) {
/* 248 */               cal.setTimeInMillis(time);
/* 249 */               int hourOfDay = cal.get(11);
/* 250 */               int minute = cal.get(12) / 15;
/* 251 */               int dayOfWeek = cal.get(7);
/* 252 */               long count = storage.getCount(getKey(dayOfWeek, hourOfDay, minute), 0L);
/* 253 */               counts.put(Long.valueOf(time), Long.valueOf(count));
/* 254 */               times.add(Long.valueOf(time));
                    }
/* 257 */           Collections.sort(times, new C05415(counts));
/* 268 */           long time2 = times.get(0).longValue() - 900000;
/* 269 */           return Math.max(time2, start);
                }

                /* JADX INFO: renamed from: co.vine.android.util.analytics.BehaviorManager$5 */
/* 255 */       static class C05415 implements Comparator<Long> {
                    final /* synthetic */ HashMap val$counts;

/* 256 */           C05415(HashMap map) {
/* 257 */               this.val$counts = map;
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 259 */           public int compare(Long lhs, Long rhs) {
/* 260 */               long diff = ((Long) this.val$counts.get(rhs)).longValue() - ((Long) this.val$counts.get(lhs)).longValue();
                        return diff > 0 ? 1 : -1;
                    }
                }
            }
