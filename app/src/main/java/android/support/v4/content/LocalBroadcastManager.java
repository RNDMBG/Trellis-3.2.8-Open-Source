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
            
            
    public class LocalBroadcastManager {
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

        static  void access$000(LocalBroadcastManager x0) {
            x0.executePendingBroadcasts();
                }

        private static class ReceiverRecord {
                    boolean broadcasting;
                    final IntentFilter filter;
                    final BroadcastReceiver receiver;

            ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
                this.filter = _filter;
                this.receiver = _receiver;
                    }

            public String toString() {
                StringBuilder builder = new StringBuilder(128);
                builder.append("Receiver{");
                builder.append(this.receiver);
                builder.append(" filter=");
                builder.append(this.filter);
                builder.append("}");
                return builder.toString();
                    }
                }

        private static class BroadcastRecord {
                    final Intent intent;
                    final ArrayList<ReceiverRecord> receivers;

            BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
                this.intent = _intent;
                this.receivers = _receivers;
                    }
                }

        public static LocalBroadcastManager getInstance(Context context) {
                    LocalBroadcastManager localBroadcastManager;
                    synchronized (mLock) {
                        try {
                   if (mInstance == null) {
                       mInstance = new LocalBroadcastManager(context.getApplicationContext());
                            }
                   localBroadcastManager = mInstance;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return localBroadcastManager;
                }

       private LocalBroadcastManager(Context context) {
           this.mAppContext = context;
           this.mHandler = new HandlerC00201(context.getMainLooper());
                }

                
       class HandlerC00201 extends Handler {
           HandlerC00201(Looper x0) {
                        super(x0);
                    }

                    @Override 
           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 1:
                       LocalBroadcastManager.access$000(LocalBroadcastManager.this);
                                break;
                            default:
                       super.handleMessage(msg);
                                break;
                        }
                    }
                }

       public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
                    synchronized (this.mReceivers) {
                        try {
                   ReceiverRecord entry = new ReceiverRecord(filter, receiver);
                   ArrayList<IntentFilter> filters = this.mReceivers.get(receiver);
                   if (filters == null) {
                       filters = new ArrayList<>(1);
                       this.mReceivers.put(receiver, filters);
                            }
                   filters.add(filter);
                            for (int i = 0; i < filter.countActions(); i++) {
                       String action = filter.getAction(i);
                       ArrayList<ReceiverRecord> entries = this.mActions.get(action);
                       if (entries == null) {
                           entries = new ArrayList<>(1);
                           this.mActions.put(action, entries);
                                }
                       entries.add(entry);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void unregisterReceiver(BroadcastReceiver receiver) {
                    synchronized (this.mReceivers) {
                        try {
                   ArrayList<IntentFilter> filters = this.mReceivers.remove(receiver);
                   if (filters != null) {
                                for (int i = 0; i < filters.size(); i++) {
                           IntentFilter filter = filters.get(i);
                                    for (int j = 0; j < filter.countActions(); j++) {
                               String action = filter.getAction(j);
                               ArrayList<ReceiverRecord> receivers = this.mActions.get(action);
                               if (receivers != null) {
                                   int k = 0;
                                            while (k < receivers.size()) {
                                       if (receivers.get(k).receiver == receiver) {
                                           receivers.remove(k);
                                           k--;
                                                }
                                       k++;
                                            }
                                   if (receivers.size() <= 0) {
                                       this.mActions.remove(action);
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

       public boolean sendBroadcast(Intent intent) {
                    String reason;
                    synchronized (this.mReceivers) {
                        try {
                   String action = intent.getAction();
                   String type = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                   Uri data = intent.getData();
                   String scheme = intent.getScheme();
                   Set<String> categories = intent.getCategories();
                   boolean debug = (intent.getFlags() & 8) != 0;
                   if (debug) {
                                Log.v(TAG, "Resolving type " + type + " scheme " + scheme + " of intent " + intent);
                            }
                   ArrayList<ReceiverRecord> entries = this.mActions.get(intent.getAction());
                   if (entries != null) {
                       if (debug) {
                                    Log.v(TAG, "Action list: " + entries);
                                }
                       ArrayList<ReceiverRecord> receivers = null;
                                for (int i = 0; i < entries.size(); i++) {
                           ReceiverRecord receiver = entries.get(i);
                           if (debug) {
                                        Log.v(TAG, "Matching against filter " + receiver.filter);
                                    }
                           if (receiver.broadcasting) {
                               if (debug) {
                                   Log.v(TAG, "  Filter's target already added");
                                        }
                                    } else {
                               int match = receiver.filter.match(action, type, scheme, data, categories, TAG);
                               if (match >= 0) {
                                   if (debug) {
                                                Log.v(TAG, "  Filter matched!  match=0x" + Integer.toHexString(match));
                                            }
                                   if (receivers == null) {
                                       receivers = new ArrayList<>();
                                            }
                                   receivers.add(receiver);
                                   receiver.broadcasting = true;
                                        } else if (debug) {
                                            switch (match) {
                                                case -4:
                                           reason = ExploreTimelineFragment.ARG_CATEGORY;
                                                    break;
                                                case -3:
                                           reason = BaseFragment.ARG_ACTION;
                                                    break;
                                                case -2:
                                           reason = "data";
                                                    break;
                                                case -1:
                                           reason = "type";
                                                    break;
                                                default:
                                           reason = "unknown reason";
                                                    break;
                                            }
                                   Log.v(TAG, "  Filter did not match: " + reason);
                                        }
                                    }
                                }
                       if (receivers != null) {
                                    for (int i2 = 0; i2 < receivers.size(); i2++) {
                               receivers.get(i2).broadcasting = false;
                                    }
                           this.mPendingBroadcasts.add(new BroadcastRecord(intent, receivers));
                           if (!this.mHandler.hasMessages(1)) {
                               this.mHandler.sendEmptyMessage(1);
                                    }
                           return true;
                                }
                            }
                   return false;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void sendBroadcastSync(Intent intent) {
           if (sendBroadcast(intent)) {
               executePendingBroadcasts();
                    }
                }

       private void executePendingBroadcasts() {
                    BroadcastRecord[] brs;
                    while (true) {
                        synchronized (this.mReceivers) {
                            try {
                       int N = this.mPendingBroadcasts.size();
                       if (N <= 0) {
                                    return;
                                }
                       brs = new BroadcastRecord[N];
                       this.mPendingBroadcasts.toArray(brs);
                       this.mPendingBroadcasts.clear();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        for (BroadcastRecord br : brs) {
                            for (int j = 0; j < br.receivers.size(); j++) {
                       br.receivers.get(j).receiver.onReceive(this.mAppContext, br.intent);
                            }
                        }
                    }
                }
            }
