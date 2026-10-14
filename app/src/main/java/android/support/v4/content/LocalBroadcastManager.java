            package android.support.v4.content;

            import android.content.BroadcastReceiver;
            import android.content.Context;
            import android.content.Intent;
            import android.content.IntentFilter;
            import android.net.Uri;
            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.util.Log;
            import co.vine.android.BaseFragment;
            import co.vine.android.ExploreTimelineFragment;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class LocalBroadcastManager {
                private static final boolean DEBUG = false;
                static final int MSG_EXEC_PENDING_BROADCASTS = 1;
                private static final String TAG = "LocalBroadcastManager";
                private static LocalBroadcastManager mInstance;
                private static final Object mLock = new Object();
                private final Context mAppContext;
                private final Handler mHandler;
                private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
                private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
                private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();

/* 45 */        static /* synthetic */ void access$000(LocalBroadcastManager x0) {
/* 46 */            x0.executePendingBroadcasts();
                }

/* 50 */        private static class ReceiverRecord {
                    boolean broadcasting;
                    final IntentFilter filter;
                    final BroadcastReceiver receiver;

/* 51 */            ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
/* 53 */                this.filter = _filter;
/* 54 */                this.receiver = _receiver;
                    }

/* 58 */            public String toString() {
/* 59 */                StringBuilder builder = new StringBuilder(128);
/* 60 */                builder.append("Receiver{");
/* 61 */                builder.append(this.receiver);
/* 62 */                builder.append(" filter=");
/* 63 */                builder.append(this.filter);
/* 64 */                builder.append("}");
/* 65 */                return builder.toString();
                    }
                }

/* 71 */        private static class BroadcastRecord {
                    final Intent intent;
                    final ArrayList<ReceiverRecord> receivers;

/* 72 */            BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
/* 74 */                this.intent = _intent;
/* 75 */                this.receivers = _receivers;
                    }
                }

/* 99 */        public static LocalBroadcastManager getInstance(Context context) {
                    LocalBroadcastManager localBroadcastManager;
                    synchronized (mLock) {
                        try {
/* 101 */                   if (mInstance == null) {
/* 102 */                       mInstance = new LocalBroadcastManager(context.getApplicationContext());
                            }
/* 104 */                   localBroadcastManager = mInstance;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return localBroadcastManager;
                }

/* 107 */       private LocalBroadcastManager(Context context) {
/* 109 */           this.mAppContext = context;
/* 110 */           this.mHandler = new HandlerC00201(context.getMainLooper());
                }

                /* JADX INFO: renamed from: android.support.v4.content.LocalBroadcastManager$1 */
/* 108 */       class HandlerC00201 extends Handler {
/* 109 */           HandlerC00201(Looper x0) {
                        super(x0);
                    }

                    @Override // android.os.Handler
/* 113 */           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
/* 116 */                       LocalBroadcastManager.access$000(LocalBroadcastManager.this);
                                break;
                            default:
/* 119 */                       super.handleMessage(msg);
                                break;
                        }
                    }
                }

/* 133 */       public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
                    synchronized (this.mReceivers) {
                        try {
/* 135 */                   ReceiverRecord entry = new ReceiverRecord(filter, receiver);
/* 136 */                   ArrayList<IntentFilter> filters = this.mReceivers.get(receiver);
/* 137 */                   if (filters == null) {
/* 138 */                       filters = new ArrayList<>(1);
/* 139 */                       this.mReceivers.put(receiver, filters);
                            }
/* 141 */                   filters.add(filter);
                            for (int i = 0; i < filter.countActions(); i++) {
/* 143 */                       String action = filter.getAction(i);
/* 144 */                       ArrayList<ReceiverRecord> entries = this.mActions.get(action);
/* 145 */                       if (entries == null) {
/* 146 */                           entries = new ArrayList<>(1);
/* 147 */                           this.mActions.put(action, entries);
                                }
/* 149 */                       entries.add(entry);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 163 */       public void unregisterReceiver(BroadcastReceiver receiver) {
                    synchronized (this.mReceivers) {
                        try {
/* 165 */                   ArrayList<IntentFilter> filters = this.mReceivers.remove(receiver);
/* 166 */                   if (filters != null) {
                                for (int i = 0; i < filters.size(); i++) {
/* 170 */                           IntentFilter filter = filters.get(i);
                                    for (int j = 0; j < filter.countActions(); j++) {
/* 172 */                               String action = filter.getAction(j);
/* 173 */                               ArrayList<ReceiverRecord> receivers = this.mActions.get(action);
/* 174 */                               if (receivers != null) {
/* 175 */                                   int k = 0;
                                            while (k < receivers.size()) {
/* 176 */                                       if (receivers.get(k).receiver == receiver) {
/* 177 */                                           receivers.remove(k);
/* 178 */                                           k--;
                                                }
/* 175 */                                       k++;
                                            }
/* 181 */                                   if (receivers.size() <= 0) {
/* 182 */                                       this.mActions.remove(action);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 200 */       public boolean sendBroadcast(Intent intent) {
                    String reason;
                    synchronized (this.mReceivers) {
                        try {
/* 202 */                   String action = intent.getAction();
/* 203 */                   String type = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
/* 205 */                   Uri data = intent.getData();
/* 206 */                   String scheme = intent.getScheme();
/* 207 */                   Set<String> categories = intent.getCategories();
/* 209 */                   boolean debug = (intent.getFlags() & 8) != 0;
/* 211 */                   if (debug) {
                                Log.v(TAG, "Resolving type " + type + " scheme " + scheme + " of intent " + intent);
                            }
/* 215 */                   ArrayList<ReceiverRecord> entries = this.mActions.get(intent.getAction());
/* 216 */                   if (entries != null) {
/* 217 */                       if (debug) {
                                    Log.v(TAG, "Action list: " + entries);
                                }
/* 219 */                       ArrayList<ReceiverRecord> receivers = null;
                                for (int i = 0; i < entries.size(); i++) {
/* 221 */                           ReceiverRecord receiver = entries.get(i);
/* 222 */                           if (debug) {
                                        Log.v(TAG, "Matching against filter " + receiver.filter);
                                    }
/* 224 */                           if (receiver.broadcasting) {
/* 225 */                               if (debug) {
/* 226 */                                   Log.v(TAG, "  Filter's target already added");
                                        }
                                    } else {
/* 231 */                               int match = receiver.filter.match(action, type, scheme, data, categories, TAG);
/* 233 */                               if (match >= 0) {
/* 234 */                                   if (debug) {
                                                Log.v(TAG, "  Filter matched!  match=0x" + Integer.toHexString(match));
                                            }
/* 236 */                                   if (receivers == null) {
/* 237 */                                       receivers = new ArrayList<>();
                                            }
/* 239 */                                   receivers.add(receiver);
/* 240 */                                   receiver.broadcasting = true;
                                        } else if (debug) {
                                            switch (match) {
                                                case -4:
/* 246 */                                           reason = ExploreTimelineFragment.ARG_CATEGORY;
                                                    break;
                                                case -3:
/* 245 */                                           reason = BaseFragment.ARG_ACTION;
                                                    break;
                                                case -2:
/* 247 */                                           reason = "data";
                                                    break;
                                                case -1:
/* 248 */                                           reason = "type";
                                                    break;
                                                default:
/* 249 */                                           reason = "unknown reason";
                                                    break;
                                            }
/* 251 */                                   Log.v(TAG, "  Filter did not match: " + reason);
                                        }
                                    }
                                }
/* 256 */                       if (receivers != null) {
                                    for (int i2 = 0; i2 < receivers.size(); i2++) {
/* 258 */                               receivers.get(i2).broadcasting = false;
                                    }
/* 260 */                           this.mPendingBroadcasts.add(new BroadcastRecord(intent, receivers));
/* 261 */                           if (!this.mHandler.hasMessages(1)) {
/* 262 */                               this.mHandler.sendEmptyMessage(1);
                                    }
/* 264 */                           return true;
                                }
                            }
/* 268 */                   return false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 276 */       public void sendBroadcastSync(Intent intent) {
/* 277 */           if (sendBroadcast(intent)) {
/* 278 */               executePendingBroadcasts();
                    }
                }

/* 283 */       private void executePendingBroadcasts() {
                    BroadcastRecord[] brs;
                    while (true) {
                        synchronized (this.mReceivers) {
                            try {
/* 286 */                       int N = this.mPendingBroadcasts.size();
/* 287 */                       if (N <= 0) {
                                    return;
                                }
/* 290 */                       brs = new BroadcastRecord[N];
/* 291 */                       this.mPendingBroadcasts.toArray(brs);
/* 292 */                       this.mPendingBroadcasts.clear();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (BroadcastRecord br : brs) {
                            for (int j = 0; j < br.receivers.size(); j++) {
/* 297 */                       br.receivers.get(j).receiver.onReceive(this.mAppContext, br.intent);
                            }
                        }
                    }
                }
            }
