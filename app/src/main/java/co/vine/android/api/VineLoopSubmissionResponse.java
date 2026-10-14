            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class VineLoopSubmissionResponse implements Parcelable {
                public static final Parcelable.Creator<VineLoopSubmissionResponse> CREATOR = new C03311();
                public int submissionInterval;

/* 11 */        public VineLoopSubmissionResponse(int submissionInterval) {
/* 13 */            this.submissionInterval = submissionInterval;
                }

/* 15 */        public VineLoopSubmissionResponse(Parcel in) {
/* 17 */            this.submissionInterval = in.readInt();
                }

                @Override // android.os.Parcelable
/* 21 */        public void writeToParcel(Parcel out, int flags) {
/* 22 */            out.writeInt(this.submissionInterval);
                }

                @Override // android.os.Parcelable
/* 26 */        public int describeContents() {
/* 27 */            return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineLoopSubmissionResponse$1 */
/* 28 */        static class C03311 implements Parcelable.Creator<VineLoopSubmissionResponse> {
/* 29 */            C03311() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 31 */            public VineLoopSubmissionResponse createFromParcel(Parcel in) {
/* 32 */                return new VineLoopSubmissionResponse(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 35 */            public VineLoopSubmissionResponse[] newArray(int size) {
/* 36 */                return new VineLoopSubmissionResponse[size];
                    }
                }
            }
