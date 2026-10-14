            package com.google.android.gms.location;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class ActivityRecognitionResultCreator implements Parcelable.Creator<ActivityRecognitionResult> {
                public static final int CONTENT_DESCRIPTION = 0;

                /* JADX INFO: renamed from: a */
                static void m2184a(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1149b(parcel, 1, activityRecognitionResult.f2168ov, false);
                    C0902b.m1150c(parcel, 1000, activityRecognitionResult.getVersionCode());
                    C0902b.m1135a(parcel, 2, activityRecognitionResult.f2169ow);
                    C0902b.m1135a(parcel, 3, activityRecognitionResult.f2170ox);
                    C0902b.m1131C(parcel, iM1152k);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityRecognitionResult createFromParcel(Parcel parcel) {
                    long jM1106g = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f = 0;
                    ArrayList arrayListM1101c = null;
                    long jM1106g2 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                arrayListM1101c = C0901a.m1101c(parcel, iM1109i, DetectedActivity.CREATOR);
                                break;
                            case 2:
                                jM1106g2 = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 3:
                                jM1106g = C0901a.m1106g(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new ActivityRecognitionResult(iM1105f, arrayListM1101c, jM1106g2, jM1106g);
                }


                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ActivityRecognitionResult[] newArray(int size) {
                    return new ActivityRecognitionResult[size];
                }

            }
