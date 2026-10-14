            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class VineTypeAhead implements Parcelable {
                public static final Parcelable.Creator<VineTypeAhead> CREATOR = new C03601();

                /* JADX INFO: renamed from: id */
                public long f97id;
                public final String token;
                public final String type;

/* 15 */        public VineTypeAhead(String type, String token, long id) {
/* 17 */            this.f97id = id;
/* 18 */            this.token = token;
/* 19 */            this.type = type;
                }

/* 22 */        public static String getPlainTag(String tagWithHash) {
/* 23 */            return tagWithHash.substring(1, tagWithHash.length());
                }

/* 25 */        public VineTypeAhead(Parcel in) {
/* 27 */            this.f97id = in.readLong();
/* 28 */            this.token = in.readString();
/* 29 */            this.type = in.readString();
                }

                @Override // android.os.Parcelable
/* 32 */        public int describeContents() {
/* 33 */            return 0;
                }

                @Override // android.os.Parcelable
/* 36 */        public void writeToParcel(Parcel out, int flags) {
/* 37 */            out.writeLong(this.f97id);
/* 38 */            out.writeString(this.token);
/* 39 */            out.writeString(this.type);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineTypeAhead$1 */
/* 41 */        static class C03601 implements Parcelable.Creator<VineTypeAhead> {
/* 42 */            C03601() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 44 */            public VineTypeAhead createFromParcel(Parcel in) {
/* 45 */                return new VineTypeAhead(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 48 */            public VineTypeAhead[] newArray(int size) {
/* 49 */                return new VineTypeAhead[size];
                    }
                }
            }
