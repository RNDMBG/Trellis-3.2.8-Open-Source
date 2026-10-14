            package android.support.v4.content;

            import android.content.BroadcastReceiver;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.os.PowerManager;
            import android.util.Log;
            import android.util.SparseArray;
            import co.vine.android.util.CommonUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 79 */    public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
                private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
                private static final SparseArray<PowerManager.WakeLock> mActiveWakeLocks = new SparseArray<>();
                private static int mNextId = 1;

/* 80 */        public static ComponentName startWakefulService(Context context, Intent intent) {
                    ComponentName comp;
                    synchronized (mActiveWakeLocks) {
                        try {
/* 82 */                    int id = mNextId;
/* 83 */                    mNextId++;
/* 84 */                    if (mNextId <= 0) {
/* 85 */                        mNextId = 1;
                            }
/* 88 */                    intent.putExtra(EXTRA_WAKE_LOCK_ID, id);
/* 89 */                    comp = context.startService(intent);
/* 90 */                    if (comp == null) {
/* 91 */                        comp = null;
                            } else {
/* 94 */                        PowerManager pm = (PowerManager) context.getSystemService("power");
/* 95 */                        PowerManager.WakeLock wl = pm.newWakeLock(1, "wake:" + comp.flattenToShortString());
/* 97 */                        wl.setReferenceCounted(false);
/* 98 */                        wl.acquire(CommonUtil.MINUTE_MILLIS);
/* 99 */                        mActiveWakeLocks.put(id, wl);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 100 */           return comp;
                }

/* 112 */       public static boolean completeWakefulIntent(Intent intent) {
                    boolean z = true;
                    z = false;
/* 113 */           int id = intent.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
/* 114 */           if (id != 0) {
                        synchronized (mActiveWakeLocks) {
                            try {
/* 118 */                       PowerManager.WakeLock wl = mActiveWakeLocks.get(id);
/* 119 */                       if (wl != null) {
/* 120 */                           wl.release();
/* 121 */                           mActiveWakeLocks.remove(id);
                                } else {
/* 130 */                           Log.w("WakefulBroadcastReceiver", "No active wake lock id #" + id);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
/* 131 */           return z;
                }
            }
