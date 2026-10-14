            package co.vine.android.cache.video;

            import android.os.Parcel;
            import android.os.Parcelable;
            import co.vine.android.cache.CacheKey;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class VideoKey extends CacheKey implements Parcelable {
                public static final Parcelable.Creator<VideoKey> CREATOR = new C03691();
                public final String url;

/* 14 */        public VideoKey(String url) {
/* 16 */            this.url = url;
                }

/* 20 */        public boolean equals(Object o) {
/* 21 */            if (this == o) {
                        return true;
                    }
/* 24 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 27 */            if (this.url != null) {
/* 28 */                return this.url.equals(((VideoKey) o).url);
                    }
                    return ((VideoKey) o).url == null;
                }

                @Override // co.vine.android.cache.CacheKey
/* 35 */        public String toString() {
/* 36 */            return super.toString() + IOUtils.LINE_SEPARATOR_UNIX + this.url;
                }

/* 40 */        public int hashCode() {
/* 41 */            if (this.url != null) {
                        return this.url.hashCode() * 31;
                    }
                    return 0;
                }

                @Override // android.os.Parcelable
/* 45 */        public void writeToParcel(Parcel out, int flags) {
/* 46 */            out.writeString(this.url);
                }

/* 48 */        public VideoKey(Parcel in) {
/* 50 */            this.url = in.readString();
                }

                @Override // android.os.Parcelable
/* 54 */        public int describeContents() {
/* 55 */            return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.cache.video.VideoKey$1 */
/* 56 */        static class C03691 implements Parcelable.Creator<VideoKey> {
/* 57 */            C03691() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 59 */            public VideoKey createFromParcel(Parcel in) {
/* 60 */                return new VideoKey(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 63 */            public VideoKey[] newArray(int size) {
/* 64 */                return new VideoKey[size];
                    }
                }
            }
