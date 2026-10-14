            package android.support.v7.internal.widget;

            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.ResolveInfo;
            import android.database.DataSetObservable;
            import android.os.AsyncTask;
            import android.os.Build;
            import android.text.TextUtils;
            import android.util.Log;
            import android.util.Xml;
            import com.vandalsoftware.p004io.IoUtils;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.math.BigDecimal;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.List;
            import java.util.Map;
            import org.xmlpull.v1.XmlPullParser;
            import org.xmlpull.v1.XmlPullParserException;
            import org.xmlpull.v1.XmlSerializer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 91 */    public class ActivityChooserModel extends DataSetObservable {
                private static final String ATTRIBUTE_ACTIVITY = "activity";
                private static final String ATTRIBUTE_TIME = "time";
                private static final String ATTRIBUTE_WEIGHT = "weight";
                private static final boolean DEBUG = false;
                private static final int DEFAULT_ACTIVITY_INFLATION = 5;
                private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
                public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
                public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
                private static final String HISTORY_FILE_EXTENSION = ".xml";
                private static final int INVALID_INDEX = -1;
                private static final String TAG_HISTORICAL_RECORD = "historical-record";
                private static final String TAG_HISTORICAL_RECORDS = "historical-records";
                private OnChooseActivityListener mActivityChoserModelPolicy;
                private final Context mContext;
                private final String mHistoryFileName;
                private Intent mIntent;
                private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
                private static final Object sRegistryLock = new Object();
                private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
                private final Object mInstanceLock = new Object();
                private final List<ActivityResolveInfo> mActivities = new ArrayList();
                private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
                private ActivitySorter mActivitySorter = new DefaultSorter(this, null);
                private int mHistoryMaxSize = 50;
                private boolean mCanReadHistoricalData = true;
                private boolean mReadShareHistoryCalled = false;
                private boolean mHistoricalRecordsChanged = true;
                private boolean mReloadActivities = false;

                public interface ActivityChooserModelClient {
                    void setActivityChooserModel(ActivityChooserModel activityChooserModel);
                }

                public interface ActivitySorter {
                    void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
                }

                public interface OnChooseActivityListener {
                    boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
                }

/* 92 */        static /* synthetic */ Context access$200(ActivityChooserModel x0) {
/* 93 */            return x0.mContext;
                }

/* 92 */        static /* synthetic */ String access$300() {
/* 93 */            return LOG_TAG;
                }

/* 92 */        static /* synthetic */ String access$400(ActivityChooserModel x0) {
/* 93 */            return x0.mHistoryFileName;
                }

/* 92 */        static /* synthetic */ boolean access$502(ActivityChooserModel x0, boolean x1) {
/* 93 */            x0.mCanReadHistoricalData = x1;
                    return x1;
                }

/* 332 */       public static ActivityChooserModel get(Context context, String historyFileName) {
                    ActivityChooserModel dataModel;
                    synchronized (sRegistryLock) {
                        try {
/* 334 */                   dataModel = sDataModelRegistry.get(historyFileName);
/* 335 */                   if (dataModel == null) {
/* 336 */                       dataModel = new ActivityChooserModel(context, historyFileName);
/* 337 */                       sDataModelRegistry.put(historyFileName, dataModel);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return dataModel;
                }

/* 348 */       private ActivityChooserModel(Context context, String historyFileName) {
/* 350 */           this.mContext = context.getApplicationContext();
/* 351 */           if (!TextUtils.isEmpty(historyFileName) && !historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
/* 353 */               this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
                    } else {
/* 355 */               this.mHistoryFileName = historyFileName;
                    }
                }

/* 368 */       public void setIntent(Intent intent) {
                    synchronized (this.mInstanceLock) {
                        try {
/* 370 */                   if (this.mIntent != intent) {
/* 373 */                       this.mIntent = intent;
/* 374 */                       this.mReloadActivities = true;
/* 375 */                       ensureConsistentState();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 384 */       public Intent getIntent() {
                    Intent intent;
                    synchronized (this.mInstanceLock) {
                        try {
/* 386 */                   intent = this.mIntent;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return intent;
                }

/* 397 */       public int getActivityCount() {
                    int size;
                    synchronized (this.mInstanceLock) {
                        try {
/* 399 */                   ensureConsistentState();
/* 400 */                   size = this.mActivities.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

/* 412 */       public ResolveInfo getActivity(int index) {
                    ResolveInfo resolveInfo;
                    synchronized (this.mInstanceLock) {
                        try {
/* 414 */                   ensureConsistentState();
/* 415 */                   resolveInfo = this.mActivities.get(index).resolveInfo;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return resolveInfo;
                }

/* 426 */       public int getActivityIndex(ResolveInfo activity) {
                    int i;
                    synchronized (this.mInstanceLock) {
                        try {
/* 428 */                   ensureConsistentState();
/* 429 */                   List<ActivityResolveInfo> activities = this.mActivities;
/* 430 */                   int activityCount = activities.size();
/* 431 */                   i = 0;
                            while (i < activityCount) {
/* 432 */                       ActivityResolveInfo currentActivity = activities.get(i);
/* 433 */                       if (currentActivity.resolveInfo != activity) {
/* 431 */                           i++;
                                }
                            }
/* 437 */                   i = -1;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 437 */           return i;
                }

/* 459 */       public Intent chooseActivity(int index) {
                    synchronized (this.mInstanceLock) {
                        try {
/* 461 */                   if (this.mIntent == null) {
                                return null;
                            }
/* 465 */                   ensureConsistentState();
/* 467 */                   ActivityResolveInfo chosenActivity = this.mActivities.get(index);
/* 469 */                   ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
/* 473 */                   Intent choiceIntent = new Intent(this.mIntent);
/* 474 */                   choiceIntent.setComponent(chosenName);
/* 476 */                   if (this.mActivityChoserModelPolicy != null) {
/* 478 */                       Intent choiceIntentCopy = new Intent(choiceIntent);
/* 479 */                       boolean handled = this.mActivityChoserModelPolicy.onChooseActivity(this, choiceIntentCopy);
/* 481 */                       if (handled) {
                                    return null;
                                }
                            }
/* 486 */                   HistoricalRecord historicalRecord = new HistoricalRecord(chosenName, System.currentTimeMillis(), 1.0f);
/* 488 */                   addHisoricalRecord(historicalRecord);
/* 490 */                   return choiceIntent;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 499 */       public void setOnChooseActivityListener(OnChooseActivityListener listener) {
                    synchronized (this.mInstanceLock) {
                        try {
/* 501 */                   this.mActivityChoserModelPolicy = listener;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 514 */       public ResolveInfo getDefaultActivity() {
                    synchronized (this.mInstanceLock) {
                        try {
/* 516 */                   ensureConsistentState();
/* 517 */                   if (!this.mActivities.isEmpty()) {
/* 518 */                       return this.mActivities.get(0).resolveInfo;
                            }
/* 521 */                   return null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 534 */       public void setDefaultActivity(int index) {
                    float weight;
                    synchronized (this.mInstanceLock) {
                        try {
/* 536 */                   ensureConsistentState();
/* 538 */                   ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
/* 539 */                   ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
/* 542 */                   if (oldDefaultActivity != null) {
/* 544 */                       weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
                            } else {
/* 547 */                       weight = 1.0f;
                            }
/* 550 */                   ComponentName defaultName = new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name);
/* 553 */                   HistoricalRecord historicalRecord = new HistoricalRecord(defaultName, System.currentTimeMillis(), weight);
/* 555 */                   addHisoricalRecord(historicalRecord);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 568 */       private void persistHistoricalDataIfNeeded() {
/* 569 */           if (!this.mReadShareHistoryCalled) {
/* 570 */               throw new IllegalStateException("No preceding call to #readHistoricalData");
                    }
/* 572 */           if (this.mHistoricalRecordsChanged) {
/* 575 */               this.mHistoricalRecordsChanged = false;
/* 576 */               if (!TextUtils.isEmpty(this.mHistoryFileName)) {
/* 577 */                   if (Build.VERSION.SDK_INT >= 11) {
/* 578 */                       executePersistHistoryAsyncTaskSDK11();
                            } else {
/* 580 */                       executePersistHistoryAsyncTaskBase();
                            }
                        }
                    }
                }

/* 585 */       private void executePersistHistoryAsyncTaskBase() {
/* 586 */           new PersistHistoryAsyncTask(this, null).execute(new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
                }

/* 590 */       private void executePersistHistoryAsyncTaskSDK11() {
/* 591 */           new PersistHistoryAsyncTask(this, null).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
                }

/* 602 */       public void setActivitySorter(ActivitySorter activitySorter) {
                    synchronized (this.mInstanceLock) {
                        try {
/* 604 */                   if (this.mActivitySorter != activitySorter) {
/* 607 */                       this.mActivitySorter = activitySorter;
/* 608 */                       if (sortActivitiesIfNeeded()) {
/* 609 */                           notifyChanged();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 627 */       public void setHistoryMaxSize(int historyMaxSize) {
                    synchronized (this.mInstanceLock) {
                        try {
/* 629 */                   if (this.mHistoryMaxSize != historyMaxSize) {
/* 632 */                       this.mHistoryMaxSize = historyMaxSize;
/* 633 */                       pruneExcessiveHistoricalRecordsIfNeeded();
/* 634 */                       if (sortActivitiesIfNeeded()) {
/* 635 */                           notifyChanged();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 645 */       public int getHistoryMaxSize() {
                    int i;
                    synchronized (this.mInstanceLock) {
                        try {
/* 647 */                   i = this.mHistoryMaxSize;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

/* 656 */       public int getHistorySize() {
                    int size;
                    synchronized (this.mInstanceLock) {
                        try {
/* 658 */                   ensureConsistentState();
/* 659 */                   size = this.mHistoricalRecords.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

/* 669 */       private void ensureConsistentState() {
/* 670 */           boolean stateChanged = loadActivitiesIfNeeded();
/* 671 */           boolean stateChanged2 = stateChanged | readHistoricalDataIfNeeded();
/* 672 */           pruneExcessiveHistoricalRecordsIfNeeded();
/* 673 */           if (stateChanged2) {
/* 674 */               sortActivitiesIfNeeded();
/* 675 */               notifyChanged();
                    }
                }

/* 686 */       private boolean sortActivitiesIfNeeded() {
/* 687 */           if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
                        return false;
                    }
/* 689 */           this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
/* 691 */           return true;
                }

/* 702 */       private boolean loadActivitiesIfNeeded() {
/* 703 */           if (!this.mReloadActivities || this.mIntent == null) {
                        return false;
                    }
/* 704 */           this.mReloadActivities = false;
/* 705 */           this.mActivities.clear();
/* 706 */           List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
/* 708 */           int resolveInfoCount = resolveInfos.size();
                    for (int i = 0; i < resolveInfoCount; i++) {
/* 710 */               ResolveInfo resolveInfo = resolveInfos.get(i);
/* 711 */               this.mActivities.add(new ActivityResolveInfo(resolveInfo));
                    }
/* 713 */           return true;
                }

/* 725 */       private boolean readHistoricalDataIfNeeded() {
/* 726 */           if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
                        return false;
                    }
/* 728 */           this.mCanReadHistoricalData = false;
/* 729 */           this.mReadShareHistoryCalled = true;
/* 730 */           readHistoricalDataImpl();
                    return true;
                }

/* 742 */       private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
/* 743 */           boolean added = this.mHistoricalRecords.add(historicalRecord);
/* 744 */           if (added) {
/* 745 */               this.mHistoricalRecordsChanged = true;
/* 746 */               pruneExcessiveHistoricalRecordsIfNeeded();
/* 747 */               persistHistoricalDataIfNeeded();
/* 748 */               sortActivitiesIfNeeded();
/* 749 */               notifyChanged();
                    }
/* 751 */           return added;
                }

/* 757 */       private void pruneExcessiveHistoricalRecordsIfNeeded() {
/* 758 */           int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
/* 759 */           if (pruneCount > 0) {
/* 762 */               this.mHistoricalRecordsChanged = true;
                        for (int i = 0; i < pruneCount; i++) {
/* 764 */                   this.mHistoricalRecords.remove(0);
                        }
                    }
                }

/* 797 */       public static final class HistoricalRecord {
                    public final ComponentName activity;
                    public final long time;
                    public final float weight;

/* 798 */           public HistoricalRecord(String activityName, long time, float weight) {
/* 799 */               this(ComponentName.unflattenFromString(activityName), time, weight);
                    }

/* 808 */           public HistoricalRecord(ComponentName activityName, long time, float weight) {
/* 810 */               this.activity = activityName;
/* 811 */               this.time = time;
/* 812 */               this.weight = weight;
                    }

/* 816 */           public int hashCode() {
/* 819 */               int result = (this.activity == null ? 0 : this.activity.hashCode()) + 31;
/* 821 */               return (((result * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
                    }

/* 826 */           public boolean equals(Object obj) {
/* 827 */               if (this == obj) {
                            return true;
                        }
/* 830 */               if (obj != null && getClass() == obj.getClass()) {
/* 836 */                   HistoricalRecord other = (HistoricalRecord) obj;
/* 837 */                   if (this.activity == null) {
/* 838 */                       if (other.activity != null) {
                                    return false;
                                }
                            } else if (!this.activity.equals(other.activity)) {
                                return false;
                            }
                            return this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
                        }
                        return false;
                    }

/* 854 */           public String toString() {
/* 855 */               StringBuilder builder = new StringBuilder();
/* 856 */               builder.append("[");
/* 857 */               builder.append("; activity:").append(this.activity);
/* 858 */               builder.append("; time:").append(this.time);
/* 859 */               builder.append("; weight:").append(new BigDecimal(this.weight));
/* 860 */               builder.append("]");
/* 861 */               return builder.toString();
                    }
                }

/* 866 */       public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
                    public final ResolveInfo resolveInfo;
                    public float weight;

                    @Override // java.lang.Comparable
/* 867 */           public /* bridge */ /* synthetic */ int compareTo(ActivityResolveInfo activityResolveInfo) {
/* 868 */               return compareTo2(activityResolveInfo);
                    }

/* 884 */           public ActivityResolveInfo(ResolveInfo resolveInfo) {
/* 886 */               this.resolveInfo = resolveInfo;
                    }

/* 890 */           public int hashCode() {
/* 891 */               return Float.floatToIntBits(this.weight) + 31;
                    }

/* 895 */           public boolean equals(Object obj) {
/* 896 */               if (this == obj) {
                            return true;
                        }
/* 899 */               if (obj != null && getClass() == obj.getClass()) {
/* 905 */                   ActivityResolveInfo other = (ActivityResolveInfo) obj;
                            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
                        }
                        return false;
                    }

                    /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 912 */           public int compareTo2(ActivityResolveInfo another) {
/* 913 */               return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
                    }

/* 917 */           public String toString() {
/* 918 */               StringBuilder builder = new StringBuilder();
/* 919 */               builder.append("[");
/* 920 */               builder.append("resolveInfo:").append(this.resolveInfo.toString());
/* 921 */               builder.append("; weight:").append(new BigDecimal(this.weight));
/* 922 */               builder.append("]");
/* 923 */               return builder.toString();
                    }
                }

/* 928 */       private final class DefaultSorter implements ActivitySorter {
                    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
                    private final Map<String, ActivityResolveInfo> mPackageNameToActivityMap;

/* 929 */           private DefaultSorter() {
/* 933 */               this.mPackageNameToActivityMap = new HashMap();
                    }

/* 929 */           /* synthetic */ DefaultSorter(ActivityChooserModel x0, C00971 x1) {
/* 930 */               this();
                    }

                    @Override // android.support.v7.internal.widget.ActivityChooserModel.ActivitySorter
/* 937 */           public void sort(Intent intent, List<ActivityResolveInfo> activities, List<HistoricalRecord> historicalRecords) {
/* 938 */               Map<String, ActivityResolveInfo> packageNameToActivityMap = this.mPackageNameToActivityMap;
/* 940 */               packageNameToActivityMap.clear();
/* 942 */               int activityCount = activities.size();
                        for (int i = 0; i < activityCount; i++) {
/* 944 */                   ActivityResolveInfo activity = activities.get(i);
/* 945 */                   activity.weight = 0.0f;
/* 946 */                   String packageName = activity.resolveInfo.activityInfo.packageName;
/* 947 */                   packageNameToActivityMap.put(packageName, activity);
                        }
/* 950 */               int lastShareIndex = historicalRecords.size() - 1;
/* 951 */               float nextRecordWeight = 1.0f;
                        for (int i2 = lastShareIndex; i2 >= 0; i2--) {
/* 953 */                   HistoricalRecord historicalRecord = historicalRecords.get(i2);
/* 954 */                   String packageName2 = historicalRecord.activity.getPackageName();
/* 955 */                   ActivityResolveInfo activity2 = packageNameToActivityMap.get(packageName2);
/* 956 */                   if (activity2 != null) {
/* 957 */                       activity2.weight += historicalRecord.weight * nextRecordWeight;
/* 958 */                       nextRecordWeight *= WEIGHT_DECAY_COEFFICIENT;
                            }
                        }
/* 962 */               Collections.sort(activities);
                    }
                }

/* 975 */       private void readHistoricalDataImpl() {
/* 976 */           FileInputStream fis = null;
                    try {
                        try {
/* 978 */                   fis = this.mContext.openFileInput(this.mHistoryFileName);
                            try {
/* 986 */                       XmlPullParser parser = Xml.newPullParser();
/* 987 */                       parser.setInput(fis, null);
/* 990 */                       for (int type = 0; type != 1 && type != 2; type = parser.next()) {
                                }
/* 994 */                       if (!TAG_HISTORICAL_RECORDS.equals(parser.getName())) {
/* 995 */                           throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                                }
/* 999 */                       List<HistoricalRecord> historicalRecords = this.mHistoricalRecords;
/* 1000 */                      historicalRecords.clear();
                                while (true) {
/* 1003 */                          int type2 = parser.next();
/* 1004 */                          if (type2 == 1) {
/* 1036 */                              if (fis != null) {
                                            try {
/* 1038 */                                      fis.close();
/* 1044 */                                      return;
                                            } catch (IOException e) {
/* 1044 */                                      return;
                                            }
                                        }
/* 1044 */                              return;
                                    }
/* 1007 */                          if (type2 != 3 && type2 != 4) {
/* 1010 */                              String nodeName = parser.getName();
/* 1011 */                              if (!TAG_HISTORICAL_RECORD.equals(nodeName)) {
/* 1012 */                                  throw new XmlPullParserException("Share records file not well-formed.");
                                        }
/* 1015 */                              String activity = parser.getAttributeValue(null, "activity");
/* 1016 */                              long time = Long.parseLong(parser.getAttributeValue(null, "time"));
/* 1018 */                              float weight = Float.parseFloat(parser.getAttributeValue(null, ATTRIBUTE_WEIGHT));
/* 1020 */                              HistoricalRecord readRecord = new HistoricalRecord(activity, time, weight);
/* 1021 */                              historicalRecords.add(readRecord);
                                    }
                                }
                            } catch (IOException ioe) {
/* 1034 */                      Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, ioe);
/* 1036 */                      if (fis != null) {
                                    try {
/* 1038 */                              fis.close();
                                    } catch (IOException e2) {
                                    }
                                }
                            } catch (XmlPullParserException xppe) {
/* 1032 */                      Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, xppe);
/* 1036 */                      if (fis != null) {
                                    try {
/* 1038 */                              fis.close();
                                    } catch (IOException e3) {
                                    }
                                }
                            }
                        } catch (FileNotFoundException e4) {
                        }
                    } catch (Throwable th) {
                        if (fis != null) {
                            try {
/* 1038 */                      fis.close();
                            } catch (IOException e5) {
                            }
                        }
/* 1041 */              throw th;
                    }
                }

/* 1047 */      private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
/* 1048 */          private PersistHistoryAsyncTask() {
                    }

/* 1048 */          /* synthetic */ PersistHistoryAsyncTask(ActivityChooserModel x0, C00971 x1) {
/* 1049 */              this();
                    }

                    @Override // android.os.AsyncTask
/* 1048 */          public /* bridge */ /* synthetic */ Void doInBackground(Object[] x0) {
/* 1049 */              return doInBackground2(x0);
                    }

                    @Override // android.os.AsyncTask
                    /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 1053 */          public Void doInBackground2(Object... args) {
/* 1054 */              List<HistoricalRecord> historicalRecords = (List) args[0];
/* 1055 */              String hostoryFileName = (String) args[1];
                        try {
/* 1060 */                  FileOutputStream fos = ActivityChooserModel.access$200(ActivityChooserModel.this).openFileOutput(hostoryFileName, 0);
/* 1066 */                  XmlSerializer serializer = Xml.newSerializer();
                            try {
                                try {
                                    try {
                                        try {
/* 1069 */                                  serializer.setOutput(fos, null);
/* 1070 */                                  serializer.startDocument(IoUtils.UTF_8, true);
/* 1071 */                                  serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
/* 1073 */                                  int recordCount = historicalRecords.size();
                                            for (int i = 0; i < recordCount; i++) {
/* 1075 */                                      HistoricalRecord record = historicalRecords.remove(0);
/* 1076 */                                      serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
/* 1077 */                                      serializer.attribute(null, "activity", record.activity.flattenToString());
/* 1079 */                                      serializer.attribute(null, "time", String.valueOf(record.time));
/* 1080 */                                      serializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(record.weight));
/* 1081 */                                      serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                            }
/* 1087 */                                  serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
/* 1088 */                                  serializer.endDocument();
/* 1100 */                                  ActivityChooserModel.access$502(ActivityChooserModel.this, true);
/* 1101 */                                  if (fos != null) {
                                                try {
/* 1103 */                                          fos.close();
                                                } catch (IOException e) {
                                                }
                                            }
                                        } catch (Throwable th) {
                                            ActivityChooserModel.access$502(ActivityChooserModel.this, true);
/* 1101 */                                  if (fos != null) {
                                                try {
/* 1103 */                                          fos.close();
                                                } catch (IOException e2) {
                                                }
                                            }
/* 1106 */                                  throw th;
                                        }
                                    } catch (IOException ioe) {
/* 1098 */                              Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), ioe);
/* 1100 */                              ActivityChooserModel.access$502(ActivityChooserModel.this, true);
/* 1101 */                              if (fos != null) {
                                            try {
/* 1103 */                                      fos.close();
                                            } catch (IOException e3) {
                                            }
                                        }
                                    }
                                } catch (IllegalStateException ise) {
/* 1096 */                          Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), ise);
/* 1100 */                          ActivityChooserModel.access$502(ActivityChooserModel.this, true);
/* 1101 */                          if (fos != null) {
                                        try {
/* 1103 */                                  fos.close();
                                        } catch (IOException e4) {
                                        }
                                    }
                                }
                            } catch (IllegalArgumentException iae) {
/* 1094 */                      Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), iae);
/* 1100 */                      ActivityChooserModel.access$502(ActivityChooserModel.this, true);
/* 1101 */                      if (fos != null) {
                                    try {
/* 1103 */                              fos.close();
                                    } catch (IOException e5) {
                                    }
                                }
                            }
/* 1109 */                  return null;
                        } catch (FileNotFoundException fnfe) {
/* 1062 */                  Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + hostoryFileName, fnfe);
/* 1063 */                  return null;
                        }
                    }
                }
            }
