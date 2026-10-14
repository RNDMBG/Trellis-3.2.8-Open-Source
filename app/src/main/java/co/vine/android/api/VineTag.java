            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class VineTag implements Parcelable, Serializable {
                public static final Parcelable.Creator<VineTag> CREATOR = new C03591();
                public long tagId;
                public String tagName;

/* 15 */        public VineTag() {
                }

/* 18 */        public VineTag(String tagName, long tagId) {
/* 20 */            this.tagName = tagName;
/* 21 */            this.tagId = tagId;
                }

/* 23 */        public VineTag(Parcel in) {
/* 25 */            this.tagName = in.readString();
/* 26 */            this.tagId = in.readLong();
                }

                @Override // android.os.Parcelable
/* 30 */        public int describeContents() {
/* 31 */            return 0;
                }

                @Override // android.os.Parcelable
/* 35 */        public void writeToParcel(Parcel dest, int flags) {
/* 36 */            dest.writeString(this.tagName);
/* 37 */            dest.writeLong(this.tagId);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineTag$1 */
/* 38 */        static class C03591 implements Parcelable.Creator<VineTag> {
/* 39 */            C03591() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 41 */            public VineTag createFromParcel(Parcel in) {
/* 42 */                return new VineTag(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 45 */            public VineTag[] newArray(int size) {
/* 46 */                return new VineTag[size];
                    }
                }
            }
