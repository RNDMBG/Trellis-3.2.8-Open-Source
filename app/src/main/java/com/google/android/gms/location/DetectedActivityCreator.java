            package com.google.android.gms.location;

            import android.os.Parcel;
            import android.os.Parcelable;
            import com.google.android.gms.common.internal.safeparcel.C0901a;
            import com.google.android.gms.common.internal.safeparcel.C0902b;
            
            
            public class DetectedActivityCreator implements Parcelable.Creator<DetectedActivity> {
                public static final int CONTENT_DESCRIPTION = 0;

                
                static void m2186a(DetectedActivity detectedActivity, Parcel parcel, int i) {
                    int iM1152k = C0902b.m1152k(parcel);
                    C0902b.m1150c(parcel, 1, detectedActivity.f2172oy);
                    C0902b.m1150c(parcel, 1000, detectedActivity.getVersionCode());
                    C0902b.m1150c(parcel, 2, detectedActivity.f2173oz);
                    C0902b.m1131C(parcel, iM1152k);
                }

                
                @Override 
                public DetectedActivity createFromParcel(Parcel parcel) {
                    int iM1105f = 0;
                    int iM1111j = C0901a.m1111j(parcel);
                    int iM1105f2 = 0;
                    int iM1105f3 = 0;
                    while (parcel.dataPosition() < iM1111j) {
                        int iM1109i = C0901a.m1109i(parcel);
                        switch (C0901a.m1126y(iM1109i)) {
                            case 1:
                                iM1105f2 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 2:
                                iM1105f = C0901a.m1105f(parcel, iM1109i);
                                break;
                            case 1000:
                                iM1105f3 = C0901a.m1105f(parcel, iM1109i);
                                break;
                            default:
                                C0901a.m1099b(parcel, iM1109i);
                                break;
                        }
                    }
                    if (parcel.dataPosition() != iM1111j) {
                        throw new C0901a.a("Overread allowed size end=" + iM1111j, parcel);
                    }
                    return new DetectedActivity(iM1105f3, iM1105f2, iM1105f);
                }


                
                @Override 
                public DetectedActivity[] newArray(int size) {
                    return new DetectedActivity[size];
                }

            }
