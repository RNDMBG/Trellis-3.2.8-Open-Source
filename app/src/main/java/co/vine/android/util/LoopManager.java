            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.os.SystemClock;
            import android.util.Log;
            import co.vine.android.Settings;
            import co.vine.android.client.AppController;
            import com.edisonwang.android.slog.SLog;
            import java.util.Collections;
            import java.util.HashSet;
            import java.util.LinkedList;
            import java.util.List;
            import java.util.ListIterator;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class LoopManager {
                private static final String TAG = "VineLoopManager";
                private static final long VIEWING_ID_EXPIRATION = 120000;
                private static LoopManager sInstance;
                private final Context mContext;
                private final String mLoopPreferenceName;
                private final SharedPreferences mPrefs;
                private LinkedList<Record> mRecords = new LinkedList<>();
                private long mViewingId = 0;
                private long mViewingIdSetTime;
                private static final int[] LOCK = new int[0];
                private static final boolean DEBUGABLE = BuildUtil.isLogsOn();
                private static final LongSparseArray<Integer> sCounts = new LongSparseArray<>();

/* 24 */        static /* synthetic */ LongSparseArray access$000() {
/* 25 */            return sCounts;
                }

/* 41 */        public static int getLocalLoopCount(long postId) {
/* 42 */            return sCounts.get(postId, 0).intValue();
                }

/* 43 */        public static class Record implements Parcelable {
                    public static final Parcelable.Creator<Record> CREATOR = new C05291();
                    public int loopCount;
                    public long postId;
                    public long timeStamp;
                    public long userId;

/* 44 */            /* synthetic */ Record(Parcel x0, Object x1) {
/* 45 */                this(x0);
                    }

/* 51 */            public Record(long userId, long postId) {
/* 53 */                this.userId = userId;
/* 54 */                this.postId = postId;
/* 55 */                this.loopCount = 0;
                    }

/* 57 */            private Record() {
                    }

                    /* JADX WARN: Multi-variable type inference failed */
/* 60 */            public void increment() {
/* 61 */                this.loopCount++;
/* 62 */                this.timeStamp = System.currentTimeMillis();
/* 63 */                int currentCount = ((Integer) LoopManager.access$000().get(this.postId, -1)).intValue();
/* 64 */                if (currentCount == -1) {
/* 65 */                    LoopManager.access$000().put(this.postId, 1);
                        } else {
/* 67 */                    LoopManager.access$000().put(this.postId, Integer.valueOf(currentCount + 1));
                        }
                    }

/* 71 */            public String toSerializedString() {
/* 72 */                return String.valueOf(this.userId) + CommonUtil.MARKER_COLON + this.postId + CommonUtil.MARKER_COLON + this.loopCount + CommonUtil.MARKER_COLON + this.timeStamp;
                    }

/* 76 */            public static Record fromSerializedString(String s) {
/* 77 */                String[] spl = s.split(":");
/* 78 */                if (spl.length != 4) {
/* 79 */                    return null;
                        }
/* 81 */                Record r = new Record();
/* 82 */                r.userId = Long.parseLong(spl[0]);
/* 83 */                r.postId = Long.parseLong(spl[1]);
/* 84 */                r.loopCount = Integer.parseInt(spl[2]);
/* 85 */                r.timeStamp = Long.parseLong(spl[3]);
/* 86 */                return r;
                    }

/* 90 */            public String toString() {
/* 91 */                return toSerializedString();
                    }

                    @Override // android.os.Parcelable
/* 95 */            public int describeContents() {
/* 96 */                return 0;
                    }

                    @Override // android.os.Parcelable
/* 100 */           public void writeToParcel(Parcel parcel, int i) {
/* 101 */               parcel.writeLong(this.userId);
/* 102 */               parcel.writeLong(this.postId);
/* 103 */               parcel.writeInt(this.loopCount);
/* 104 */               parcel.writeLong(this.timeStamp);
                    }

/* 106 */           private Record(Parcel in) {
/* 108 */               this.userId = in.readLong();
/* 109 */               this.postId = in.readLong();
/* 110 */               this.loopCount = in.readInt();
/* 111 */               this.timeStamp = in.readLong();
                    }

                    /* JADX INFO: renamed from: co.vine.android.util.LoopManager$Record$1 */
/* 113 */           static class C05291 implements Parcelable.Creator<Record> {
/* 114 */               C05291() {
                        }



                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 116 */               public Record createFromParcel(Parcel in) {
/* 117 */                   return new Record(in, (Object) null);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // android.os.Parcelable.Creator
/* 120 */               public Record[] newArray(int size) {
/* 121 */                   return new Record[size];
                        }
                    }
                }

/* 125 */       private LoopManager(Context context) {
/* 127 */           this.mPrefs = Util.getDefaultSharedPrefs(context);
/* 129 */           this.mContext = context;
/* 131 */           this.mLoopPreferenceName = SystemUtil.isRunningOnServiceProcess(context) ? Settings.PREF_LOOP_COUNT_RECORDS : Settings.PREF_LOOP_COUNT_RECORDS + SystemUtil.getSubProcessName(context);
/* 134 */           Set<String> savedLoops = this.mPrefs.getStringSet(this.mLoopPreferenceName, Collections.emptySet());
/* 136 */           if (savedLoops != null) {
/* 137 */               if (SLog.sLogsOn) {
/* 138 */                   SLog.m466i("LoopManager loaded {} loops from {}", Integer.valueOf(savedLoops.size()), SystemUtil.getSubProcessName(context));
                        }
                        for (String s : savedLoops) {
/* 142 */                   addRecord(Record.fromSerializedString(s));
                        }
                    }
                }

/* 147 */       public static LoopManager get(Context context) {
                    synchronized (LOCK) {
                        try {
/* 149 */                   if (sInstance == null) {
/* 150 */                       sInstance = new LoopManager(context);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 153 */           return sInstance;
                }

/* 156 */       public void increment(long postId) {
                    Record record;
                    synchronized (LOCK) {
                        try {
/* 158 */                   resetViewingIdIfNeeded();
                            if (this.mViewingId != postId) {
/* 162 */                       record = new Record(AppController.getInstance(this.mContext).getActiveId(), postId);
/* 163 */                       addRecord(record);
                            } else {
/* 165 */                       record = this.mRecords.getLast();
                            }
/* 167 */                   this.mViewingId = postId;
/* 168 */                   this.mViewingIdSetTime = SystemClock.elapsedRealtime();
/* 169 */                   record.increment();
/* 170 */                   Log.d(TAG, "Incremented " + record.postId + " with count " + record.loopCount);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 174 */       private void resetViewingIdIfNeeded() {
                    if (SystemClock.elapsedRealtime() - this.mViewingIdSetTime > VIEWING_ID_EXPIRATION) {
/* 177 */               this.mViewingId = 0L;
/* 178 */               this.mViewingIdSetTime = 0L;
                    }
                }

/* 182 */       public void addRecord(Record record) {
                    synchronized (LOCK) {
                        try {
/* 184 */                   this.mRecords.add(record);
/* 185 */                   if (record.loopCount > 0) {
/* 186 */                       int currentCount = sCounts.get(record.postId, -1).intValue();
/* 187 */                       if (currentCount == -1) {
/* 188 */                           sCounts.put(record.postId, Integer.valueOf(record.loopCount));
                                } else {
/* 190 */                           sCounts.put(record.postId, Integer.valueOf(record.loopCount + currentCount));
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 196 */       public void save() {
/* 197 */           Set<String> recordsToSave = new HashSet<>();
                    synchronized (LOCK) {
                        try {
                            for (Record r : this.mRecords) {
/* 200 */                       recordsToSave.add(r.toSerializedString());
                            }
/* 202 */                   if (DEBUGABLE) {
/* 203 */                       Log.v(TAG, "Committing these loop records:\n" + toString());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 206 */           this.mPrefs.edit().putStringSet(this.mLoopPreferenceName, recordsToSave).apply();
                }

/* 210 */       public List<Record> popPendingLoops() {
                    LinkedList<Record> pendingLoops;
/* 211 */           LinkedList<Record> substitute = new LinkedList<>();
                    synchronized (LOCK) {
                        try {
/* 213 */                   pendingLoops = this.mRecords;
/* 214 */                   this.mRecords = substitute;
/* 216 */                   resetViewingIdIfNeeded();
/* 218 */                   if (!pendingLoops.isEmpty() && this.mViewingId == pendingLoops.getLast().postId) {
/* 219 */                       this.mRecords.add(pendingLoops.removeLast());
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 224 */           long userId = AppController.getInstance(this.mContext).getActiveId();
/* 225 */           ListIterator<Record> it = pendingLoops.listIterator();
                    while (it.hasNext()) {
/* 226 */               Record r = it.next();
                        if (r.userId != userId) {
                            synchronized (LOCK) {
                                try {
/* 229 */                           this.mRecords.addFirst(r);
                                } catch (Throwable th2) {
                                    throw th2;
                                }
                            }
/* 231 */                   it.remove();
                        }
                    }
/* 234 */           return pendingLoops;
                }

/* 237 */       public void onServerSucess() {
                    synchronized (LOCK) {
                        try {
/* 239 */                   save();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 243 */       public void onServerFailure(List<Record> poppedRecords) {
                    synchronized (LOCK) {
                        try {
/* 245 */                   this.mRecords.addAll(0, poppedRecords);
/* 246 */                   save();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 251 */       public String toString() {
                    String string;
                    synchronized (LOCK) {
                        try {
/* 253 */                   StringBuilder sb = new StringBuilder();
                            for (Record r : this.mRecords) {
/* 255 */                       sb.append(r.toString()).append('\n');
                            }
/* 257 */                   string = sb.toString();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }
            }
