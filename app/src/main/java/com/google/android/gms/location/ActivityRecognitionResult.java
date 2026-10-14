            package com.google.android.gms.location;

            import android.content.Intent;
            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1028dm;
            import java.util.Collections;
            import java.util.List;
            
            
            public class ActivityRecognitionResult implements SafeParcelable {
                public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
                public static final String EXTRA_ACTIVITY_RESULT = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT";

                
                private final int f2167iM;

                
                List<DetectedActivity> f2168ov;

                
                long f2169ow;

                
                long f2170ox;

                public ActivityRecognitionResult(int versionCode, List<DetectedActivity> probableActivities, long timeMillis, long elapsedRealtimeMillis) {
                    this.f2167iM = 1;
                    this.f2168ov = probableActivities;
                    this.f2169ow = timeMillis;
                    this.f2170ox = elapsedRealtimeMillis;
                }

                public ActivityRecognitionResult(DetectedActivity mostProbableActivity, long time, long elapsedRealtimeMillis) {
                    this((List<DetectedActivity>) Collections.singletonList(mostProbableActivity), time, elapsedRealtimeMillis);
                }

                public ActivityRecognitionResult(List<DetectedActivity> probableActivities, long time, long elapsedRealtimeMillis) {
                    C1028dm.m1604b(probableActivities != null && probableActivities.size() > 0, "Must have at least 1 detected activity");
                    this.f2167iM = 1;
                    this.f2168ov = probableActivities;
                    this.f2169ow = time;
                    this.f2170ox = elapsedRealtimeMillis;
                }

                public static ActivityRecognitionResult extractResult(Intent intent) {
                    if (hasResult(intent)) {
                        return (ActivityRecognitionResult) intent.getExtras().get(EXTRA_ACTIVITY_RESULT);
                    }
                    return null;
                }

                public static boolean hasResult(Intent intent) {
                    if (intent == null) {
                        return false;
                    }
                    return intent.hasExtra(EXTRA_ACTIVITY_RESULT);
                }

                @Override 
                public int describeContents() {
                    return 0;
                }

                public int getActivityConfidence(int activityType) {
                    for (DetectedActivity detectedActivity : this.f2168ov) {
                        if (detectedActivity.getType() == activityType) {
                            return detectedActivity.getConfidence();
                        }
                    }
                    return 0;
                }

                public long getElapsedRealtimeMillis() {
                    return this.f2170ox;
                }

                public DetectedActivity getMostProbableActivity() {
                    return this.f2168ov.get(0);
                }

                public List<DetectedActivity> getProbableActivities() {
                    return this.f2168ov;
                }

                public long getTime() {
                    return this.f2169ow;
                }

                public int getVersionCode() {
                    return this.f2167iM;
                }

                public String toString() {
                    return "ActivityRecognitionResult [probableActivities=" + this.f2168ov + ", timeMillis=" + this.f2169ow + ", elapsedRealtimeMillis=" + this.f2170ox + "]";
                }

                @Override 
                public void writeToParcel(Parcel out, int flags) {
                    ActivityRecognitionResultCreator.m2184a(this, out, flags);
                }
            }
