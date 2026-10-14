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
            
            
    public class ActivityChooserModel extends DataSetObservable {
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

        static  Context access$200(ActivityChooserModel x0) {
            return x0.mContext;
                }

        static  String access$300() {
            return LOG_TAG;
                }

        static  String access$400(ActivityChooserModel x0) {
            return x0.mHistoryFileName;
                }

        static  boolean access$502(ActivityChooserModel x0, boolean x1) {
            x0.mCanReadHistoricalData = x1;
                    return x1;
                }

       public static ActivityChooserModel get(Context context, String historyFileName) {
                    ActivityChooserModel dataModel;
                    synchronized (sRegistryLock) {
                        try {
                   dataModel = sDataModelRegistry.get(historyFileName);
                   if (dataModel == null) {
                       dataModel = new ActivityChooserModel(context, historyFileName);
                       sDataModelRegistry.put(historyFileName, dataModel);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return dataModel;
                }

       private ActivityChooserModel(Context context, String historyFileName) {
           this.mContext = context.getApplicationContext();
           if (!TextUtils.isEmpty(historyFileName) && !historyFileName.endsWith(HISTORY_FILE_EXTENSION)) {
               this.mHistoryFileName = historyFileName + HISTORY_FILE_EXTENSION;
                    } else {
               this.mHistoryFileName = historyFileName;
                    }
                }

       public void setIntent(Intent intent) {
                    synchronized (this.mInstanceLock) {
                        try {
                   if (this.mIntent != intent) {
                       this.mIntent = intent;
                       this.mReloadActivities = true;
                       ensureConsistentState();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public Intent getIntent() {
                    Intent intent;
                    synchronized (this.mInstanceLock) {
                        try {
                   intent = this.mIntent;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return intent;
                }

       public int getActivityCount() {
                    int size;
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   size = this.mActivities.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

       public ResolveInfo getActivity(int index) {
                    ResolveInfo resolveInfo;
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   resolveInfo = this.mActivities.get(index).resolveInfo;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return resolveInfo;
                }

       public int getActivityIndex(ResolveInfo activity) {
                    int i;
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   List<ActivityResolveInfo> activities = this.mActivities;
                   int activityCount = activities.size();
                   i = 0;
                            while (i < activityCount) {
                       ActivityResolveInfo currentActivity = activities.get(i);
                       if (currentActivity.resolveInfo != activity) {
                           i++;
                                }
                            }
                   i = -1;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
           return i;
                }

       public Intent chooseActivity(int index) {
                    synchronized (this.mInstanceLock) {
                        try {
                   if (this.mIntent == null) {
                                return null;
                            }
                   ensureConsistentState();
                   ActivityResolveInfo chosenActivity = this.mActivities.get(index);
                   ComponentName chosenName = new ComponentName(chosenActivity.resolveInfo.activityInfo.packageName, chosenActivity.resolveInfo.activityInfo.name);
                   Intent choiceIntent = new Intent(this.mIntent);
                   choiceIntent.setComponent(chosenName);
                   if (this.mActivityChoserModelPolicy != null) {
                       Intent choiceIntentCopy = new Intent(choiceIntent);
                       boolean handled = this.mActivityChoserModelPolicy.onChooseActivity(this, choiceIntentCopy);
                       if (handled) {
                                    return null;
                                }
                            }
                   HistoricalRecord historicalRecord = new HistoricalRecord(chosenName, System.currentTimeMillis(), 1.0f);
                   addHisoricalRecord(historicalRecord);
                   return choiceIntent;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void setOnChooseActivityListener(OnChooseActivityListener listener) {
                    synchronized (this.mInstanceLock) {
                        try {
                   this.mActivityChoserModelPolicy = listener;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public ResolveInfo getDefaultActivity() {
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   if (!this.mActivities.isEmpty()) {
                       return this.mActivities.get(0).resolveInfo;
                            }
                   return null;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void setDefaultActivity(int index) {
                    float weight;
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   ActivityResolveInfo newDefaultActivity = this.mActivities.get(index);
                   ActivityResolveInfo oldDefaultActivity = this.mActivities.get(0);
                   if (oldDefaultActivity != null) {
                       weight = (oldDefaultActivity.weight - newDefaultActivity.weight) + 5.0f;
                            } else {
                       weight = 1.0f;
                            }
                   ComponentName defaultName = new ComponentName(newDefaultActivity.resolveInfo.activityInfo.packageName, newDefaultActivity.resolveInfo.activityInfo.name);
                   HistoricalRecord historicalRecord = new HistoricalRecord(defaultName, System.currentTimeMillis(), weight);
                   addHisoricalRecord(historicalRecord);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       private void persistHistoricalDataIfNeeded() {
           if (!this.mReadShareHistoryCalled) {
               throw new IllegalStateException("No preceding call to #readHistoricalData");
                    }
           if (this.mHistoricalRecordsChanged) {
               this.mHistoricalRecordsChanged = false;
               if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                   if (Build.VERSION.SDK_INT >= 11) {
                       executePersistHistoryAsyncTaskSDK11();
                            } else {
                       executePersistHistoryAsyncTaskBase();
                            }
                        }
                    }
                }

       private void executePersistHistoryAsyncTaskBase() {
           new PersistHistoryAsyncTask(this, null).execute(new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
                }

       private void executePersistHistoryAsyncTaskSDK11() {
           new PersistHistoryAsyncTask(this, null).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
                }

       public void setActivitySorter(ActivitySorter activitySorter) {
                    synchronized (this.mInstanceLock) {
                        try {
                   if (this.mActivitySorter != activitySorter) {
                       this.mActivitySorter = activitySorter;
                       if (sortActivitiesIfNeeded()) {
                           notifyChanged();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public void setHistoryMaxSize(int historyMaxSize) {
                    synchronized (this.mInstanceLock) {
                        try {
                   if (this.mHistoryMaxSize != historyMaxSize) {
                       this.mHistoryMaxSize = historyMaxSize;
                       pruneExcessiveHistoricalRecordsIfNeeded();
                       if (sortActivitiesIfNeeded()) {
                           notifyChanged();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public int getHistoryMaxSize() {
                    int i;
                    synchronized (this.mInstanceLock) {
                        try {
                   i = this.mHistoryMaxSize;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

       public int getHistorySize() {
                    int size;
                    synchronized (this.mInstanceLock) {
                        try {
                   ensureConsistentState();
                   size = this.mHistoricalRecords.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

       private void ensureConsistentState() {
           boolean stateChanged = loadActivitiesIfNeeded();
           boolean stateChanged2 = stateChanged | readHistoricalDataIfNeeded();
           pruneExcessiveHistoricalRecordsIfNeeded();
           if (stateChanged2) {
               sortActivitiesIfNeeded();
               notifyChanged();
                    }
                }

       private boolean sortActivitiesIfNeeded() {
           if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
                        return false;
                    }
           this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
           return true;
                }

       private boolean loadActivitiesIfNeeded() {
           if (!this.mReloadActivities || this.mIntent == null) {
                        return false;
                    }
           this.mReloadActivities = false;
           this.mActivities.clear();
           List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
           int resolveInfoCount = resolveInfos.size();
                    for (int i = 0; i < resolveInfoCount; i++) {
               ResolveInfo resolveInfo = resolveInfos.get(i);
               this.mActivities.add(new ActivityResolveInfo(resolveInfo));
                    }
           return true;
                }

       private boolean readHistoricalDataIfNeeded() {
           if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
                        return false;
                    }
           this.mCanReadHistoricalData = false;
           this.mReadShareHistoryCalled = true;
           readHistoricalDataImpl();
                    return true;
                }

       private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
           boolean added = this.mHistoricalRecords.add(historicalRecord);
           if (added) {
               this.mHistoricalRecordsChanged = true;
               pruneExcessiveHistoricalRecordsIfNeeded();
               persistHistoricalDataIfNeeded();
               sortActivitiesIfNeeded();
               notifyChanged();
                    }
           return added;
                }

       private void pruneExcessiveHistoricalRecordsIfNeeded() {
           int pruneCount = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
           if (pruneCount > 0) {
               this.mHistoricalRecordsChanged = true;
                        for (int i = 0; i < pruneCount; i++) {
                   this.mHistoricalRecords.remove(0);
                        }
                    }
                }

       public static final class HistoricalRecord {
                    public final ComponentName activity;
                    public final long time;
                    public final float weight;

           public HistoricalRecord(String activityName, long time, float weight) {
               this(ComponentName.unflattenFromString(activityName), time, weight);
                    }

           public HistoricalRecord(ComponentName activityName, long time, float weight) {
               this.activity = activityName;
               this.time = time;
               this.weight = weight;
                    }

           public int hashCode() {
               int result = (this.activity == null ? 0 : this.activity.hashCode()) + 31;
               return (((result * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
                    }

           public boolean equals(Object obj) {
               if (this == obj) {
                            return true;
                        }
               if (obj != null && getClass() == obj.getClass()) {
                   HistoricalRecord other = (HistoricalRecord) obj;
                   if (this.activity == null) {
                       if (other.activity != null) {
                                    return false;
                                }
                            } else if (!this.activity.equals(other.activity)) {
                                return false;
                            }
                            return this.time == other.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
                        }
                        return false;
                    }

           public String toString() {
               StringBuilder builder = new StringBuilder();
               builder.append("[");
               builder.append("; activity:").append(this.activity);
               builder.append("; time:").append(this.time);
               builder.append("; weight:").append(new BigDecimal(this.weight));
               builder.append("]");
               return builder.toString();
                    }
                }

       public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
                    public final ResolveInfo resolveInfo;
                    public float weight;

                    @Override 
           public   int compareTo(ActivityResolveInfo activityResolveInfo) {
               return compareTo2(activityResolveInfo);
                    }

           public ActivityResolveInfo(ResolveInfo resolveInfo) {
               this.resolveInfo = resolveInfo;
                    }

           public int hashCode() {
               return Float.floatToIntBits(this.weight) + 31;
                    }

           public boolean equals(Object obj) {
               if (this == obj) {
                            return true;
                        }
               if (obj != null && getClass() == obj.getClass()) {
                   ActivityResolveInfo other = (ActivityResolveInfo) obj;
                            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(other.weight);
                        }
                        return false;
                    }

                    
           public int compareTo2(ActivityResolveInfo another) {
               return Float.floatToIntBits(another.weight) - Float.floatToIntBits(this.weight);
                    }

           public String toString() {
               StringBuilder builder = new StringBuilder();
               builder.append("[");
               builder.append("resolveInfo:").append(this.resolveInfo.toString());
               builder.append("; weight:").append(new BigDecimal(this.weight));
               builder.append("]");
               return builder.toString();
                    }
                }

       private final class DefaultSorter implements ActivitySorter {
                    private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
                    private final Map<String, ActivityResolveInfo> mPackageNameToActivityMap;

           private DefaultSorter() {
               this.mPackageNameToActivityMap = new HashMap();
                    }

            DefaultSorter(ActivityChooserModel x0, C00971 x1) {
               this();
                    }

                    @Override 
           public void sort(Intent intent, List<ActivityResolveInfo> activities, List<HistoricalRecord> historicalRecords) {
               Map<String, ActivityResolveInfo> packageNameToActivityMap = this.mPackageNameToActivityMap;
               packageNameToActivityMap.clear();
               int activityCount = activities.size();
                        for (int i = 0; i < activityCount; i++) {
                   ActivityResolveInfo activity = activities.get(i);
                   activity.weight = 0.0f;
                   String packageName = activity.resolveInfo.activityInfo.packageName;
                   packageNameToActivityMap.put(packageName, activity);
                        }
               int lastShareIndex = historicalRecords.size() - 1;
               float nextRecordWeight = 1.0f;
                        for (int i2 = lastShareIndex; i2 >= 0; i2--) {
                   HistoricalRecord historicalRecord = historicalRecords.get(i2);
                   String packageName2 = historicalRecord.activity.getPackageName();
                   ActivityResolveInfo activity2 = packageNameToActivityMap.get(packageName2);
                   if (activity2 != null) {
                       activity2.weight += historicalRecord.weight * nextRecordWeight;
                       nextRecordWeight *= WEIGHT_DECAY_COEFFICIENT;
                            }
                        }
               Collections.sort(activities);
                    }
                }

       private void readHistoricalDataImpl() {
           FileInputStream fis = null;
                    try {
                        try {
                   fis = this.mContext.openFileInput(this.mHistoryFileName);
                            try {
                       XmlPullParser parser = Xml.newPullParser();
                       parser.setInput(fis, null);
                       for (int type = 0; type != 1 && type != 2; type = parser.next()) {
                                }
                       if (!TAG_HISTORICAL_RECORDS.equals(parser.getName())) {
                           throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                                }
                       List<HistoricalRecord> historicalRecords = this.mHistoricalRecords;
                      historicalRecords.clear();
                                while (true) {
                          int type2 = parser.next();
                          if (type2 == 1) {
                              if (fis != null) {
                                            try {
                                      fis.close();
                                      return;
                                            } catch (IOException e) {
                                      return;
                                            }
                                        }
                              return;
                                    }
                          if (type2 != 3 && type2 != 4) {
                              String nodeName = parser.getName();
                              if (!TAG_HISTORICAL_RECORD.equals(nodeName)) {
                                  throw new XmlPullParserException("Share records file not well-formed.");
                                        }
                              String activity = parser.getAttributeValue(null, "activity");
                              long time = Long.parseLong(parser.getAttributeValue(null, "time"));
                              float weight = Float.parseFloat(parser.getAttributeValue(null, ATTRIBUTE_WEIGHT));
                              HistoricalRecord readRecord = new HistoricalRecord(activity, time, weight);
                              historicalRecords.add(readRecord);
                                    }
                                }
                            } catch (IOException ioe) {
                      Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, ioe);
                      if (fis != null) {
                                    try {
                              fis.close();
                                    } catch (IOException e2) {
                                    }
                                }
                            } catch (XmlPullParserException xppe) {
                      Log.e(LOG_TAG, "Error reading historical recrod file: " + this.mHistoryFileName, xppe);
                      if (fis != null) {
                                    try {
                              fis.close();
                                    } catch (IOException e3) {
                                    }
                                }
                            }
                        } catch (FileNotFoundException e4) {
                        }
                    } catch (Throwable th) {
                        if (fis != null) {
                            try {
                      fis.close();
                            } catch (IOException e5) {
                            }
                        }
              throw th;
                    }
                }

      private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
          private PersistHistoryAsyncTask() {
                    }

           PersistHistoryAsyncTask(ActivityChooserModel x0, C00971 x1) {
              this();
                    }

                    @Override 
          public   Void doInBackground(Object[] x0) {
              return doInBackground2(x0);
                    }

                    @Override 
                    
          public Void doInBackground2(Object... args) {
              List<HistoricalRecord> historicalRecords = (List) args[0];
              String hostoryFileName = (String) args[1];
                        try {
                  FileOutputStream fos = ActivityChooserModel.access$200(ActivityChooserModel.this).openFileOutput(hostoryFileName, 0);
                  XmlSerializer serializer = Xml.newSerializer();
                            try {
                                try {
                                    try {
                                        try {
                                  serializer.setOutput(fos, null);
                                  serializer.startDocument(IoUtils.UTF_8, true);
                                  serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                                  int recordCount = historicalRecords.size();
                                            for (int i = 0; i < recordCount; i++) {
                                      HistoricalRecord record = historicalRecords.remove(0);
                                      serializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                      serializer.attribute(null, "activity", record.activity.flattenToString());
                                      serializer.attribute(null, "time", String.valueOf(record.time));
                                      serializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(record.weight));
                                      serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                            }
                                  serializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                                  serializer.endDocument();
                                  ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                                  if (fos != null) {
                                                try {
                                          fos.close();
                                                } catch (IOException e) {
                                                }
                                            }
                                        } catch (Throwable th) {
                                            ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                                  if (fos != null) {
                                                try {
                                          fos.close();
                                                } catch (IOException e2) {
                                                }
                                            }
                                  throw th;
                                        }
                                    } catch (IOException ioe) {
                              Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), ioe);
                              ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                              if (fos != null) {
                                            try {
                                      fos.close();
                                            } catch (IOException e3) {
                                            }
                                        }
                                    }
                                } catch (IllegalStateException ise) {
                          Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), ise);
                          ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                          if (fos != null) {
                                        try {
                                  fos.close();
                                        } catch (IOException e4) {
                                        }
                                    }
                                }
                            } catch (IllegalArgumentException iae) {
                      Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + ActivityChooserModel.access$400(ActivityChooserModel.this), iae);
                      ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                      if (fos != null) {
                                    try {
                              fos.close();
                                    } catch (IOException e5) {
                                    }
                                }
                            }
                  return null;
                        } catch (FileNotFoundException fnfe) {
                  Log.e(ActivityChooserModel.access$300(), "Error writing historical recrod file: " + hostoryFileName, fnfe);
                  return null;
                        }
                    }
                }
            }
