            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class VineVideoUrlTier implements Parcelable {
                public static final Parcelable.Creator<VineVideoUrlTier> CREATOR = new C03641();
                public static final String FORMAT_DASH = "dash";
                public static final String FORMAT_H264 = "h264";
                public static final String FORMAT_H264C = "h264c";
                public static final String FORMAT_WEBM = "webm";
                public final String format;
                public final float rate;
                public final String url;

/* 18 */        public VineVideoUrlTier(String url, float rate, String format) {
/* 20 */            this.url = url;
/* 21 */            this.rate = rate;
/* 22 */            this.format = format;
                }

/* 24 */        public VineVideoUrlTier(Parcel in) {
/* 26 */            this.url = in.readString();
/* 27 */            this.rate = in.readFloat();
/* 28 */            this.format = in.readString();
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineVideoUrlTier$1 */
/* 29 */        static class C03641 implements Parcelable.Creator<VineVideoUrlTier> {
/* 30 */            C03641() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 32 */            public VineVideoUrlTier createFromParcel(Parcel in) {
/* 33 */                return new VineVideoUrlTier(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 36 */            public VineVideoUrlTier[] newArray(int size) {
/* 37 */                return new VineVideoUrlTier[size];
                    }
                }

                @Override // android.os.Parcelable
/* 42 */        public int describeContents() {
/* 43 */            return 0;
                }

                @Override // android.os.Parcelable
/* 47 */        public void writeToParcel(Parcel out, int flags) {
/* 48 */            out.writeString(this.url);
/* 49 */            out.writeFloat(this.rate);
/* 50 */            out.writeString(this.format);
                }
            }
