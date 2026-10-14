            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 11 */    public class VinePostResponse implements Parcelable {
                public static final Parcelable.Creator<VinePostResponse> CREATOR = new C03501();
                public final long createdAt;
                public final long postId;

/* 12 */        public VinePostResponse(long postId, long createdAt) {
/* 14 */            this.postId = postId;
/* 15 */            this.createdAt = createdAt;
                }

/* 17 */        public VinePostResponse(Parcel in) {
/* 19 */            this.postId = in.readLong();
/* 20 */            this.createdAt = in.readLong();
                }

                @Override // android.os.Parcelable
/* 24 */        public int describeContents() {
/* 25 */            return 0;
                }

                @Override // android.os.Parcelable
/* 29 */        public void writeToParcel(Parcel out, int flags) {
/* 30 */            out.writeLong(this.postId);
/* 31 */            out.writeLong(this.createdAt);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePostResponse$1 */
/* 32 */        static class C03501 implements Parcelable.Creator<VinePostResponse> {
/* 33 */            C03501() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 35 */            public VinePostResponse createFromParcel(Parcel in) {
/* 36 */                return new VinePostResponse(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 39 */            public VinePostResponse[] newArray(int size) {
/* 40 */                return new VinePostResponse[size];
                    }
                }
            }
