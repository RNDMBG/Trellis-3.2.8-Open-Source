            package co.vine.android.cache.image;

            import android.os.Parcel;
            import android.os.Parcelable;
            import co.vine.android.cache.CacheKey;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ImageKey extends CacheKey implements Parcelable {
                public static final Parcelable.Creator<ImageKey> CREATOR = new C03681();
                public final int blurRadius;
                public final boolean blurred;
                public final boolean circularCropped;
                public boolean desaturated;
                public final int height;
                public final boolean requestResize;
                public final String url;
                public final int width;

/* 19 */        public ImageKey(String url, boolean blurred, int blurRadius, boolean desaturated) {
/* 20 */            this(url, 0, 0, false, blurred, blurRadius, desaturated);
                }

/* 23 */        public ImageKey(String url, boolean circularCropped) {
/* 24 */            this(url, 0, 0, circularCropped, false, 0, false);
                }

/* 27 */        public ImageKey(String url, int w, int h, boolean circularCropped) {
/* 28 */            this(url, w, h, circularCropped, false, 0, false);
                }

/* 31 */        public ImageKey(String url) {
/* 32 */            this(url, 0, 0, false, false, 0, false);
                }

/* 44 */        public ImageKey(String url, int width, int height, boolean circularCropped, boolean blurred, int blurRadius, boolean desaturated) {
/* 46 */            this.url = url;
/* 47 */            this.width = width;
/* 48 */            this.height = height;
/* 49 */            this.circularCropped = circularCropped;
/* 50 */            this.requestResize = width > 0 && height > 0;
/* 51 */            this.blurred = blurred;
/* 52 */            this.blurRadius = blurRadius;
/* 53 */            this.desaturated = desaturated;
/* 55 */            if (url == null) {
/* 56 */                throw new IllegalStateException("Tried to create Image key with null url.");
                    }
                }

/* 60 */        public static ImageKey newDownloadOnlyKey(String url) {
/* 61 */            ImageKey key = new ImageKey(url, 1, 1, true);
/* 62 */            key.setDownloadOnly(true);
/* 63 */            return key;
                }

/* 67 */        public boolean equals(Object o) {
/* 68 */            if (this == o) {
                        return true;
                    }
/* 71 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 74 */            ImageKey imageKey = (ImageKey) o;
/* 75 */            if (this.height != imageKey.height || this.width != imageKey.width || this.blurred != imageKey.blurred || this.blurRadius != imageKey.blurRadius || this.circularCropped != imageKey.circularCropped || this.desaturated != imageKey.desaturated) {
                        return false;
                    }
/* 93 */            if (this.url != null) {
/* 94 */                return this.url.equals(imageKey.url);
                    }
/* 96 */            return imageKey.url == null;
                }

/* 101 */       public int hashCode() {
/* 102 */           int result = this.url != null ? this.url.hashCode() : 0;
/* 108 */           return (((((((((((result * 31) + this.width) * 31) + this.height) * 31) + (this.circularCropped ? 1 : 0)) * 31) + (this.blurred ? 1 : 0)) * 31) + this.blurRadius) * 31) + (this.desaturated ? 1 : 0);
                }

                @Override // android.os.Parcelable
/* 113 */       public void writeToParcel(Parcel out, int flags) {
/* 114 */           out.writeInt(this.width);
/* 115 */           out.writeInt(this.height);
/* 116 */           out.writeInt(this.blurRadius);
/* 117 */           out.writeInt(this.blurred ? 1 : 0);
/* 118 */           out.writeInt(this.circularCropped ? 1 : 0);
/* 119 */           out.writeInt(this.requestResize ? 1 : 0);
/* 120 */           out.writeInt(this.desaturated ? 1 : 0);
/* 121 */           out.writeString(this.url);
                }

/* 123 */       public ImageKey(Parcel in) {
/* 125 */           this.width = in.readInt();
/* 126 */           this.height = in.readInt();
/* 127 */           this.blurRadius = in.readInt();
/* 128 */           this.blurred = in.readInt() > 0;
/* 129 */           this.circularCropped = in.readInt() > 0;
/* 130 */           this.requestResize = in.readInt() > 0;
/* 131 */           this.desaturated = in.readInt() > 0;
/* 132 */           this.url = in.readString();
                }

                @Override // android.os.Parcelable
/* 136 */       public int describeContents() {
/* 137 */           return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.cache.image.ImageKey$1 */
/* 138 */       static class C03681 implements Parcelable.Creator<ImageKey> {
/* 139 */           C03681() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 141 */           public ImageKey createFromParcel(Parcel in) {
/* 142 */               return new ImageKey(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 145 */           public ImageKey[] newArray(int size) {
/* 146 */               return new ImageKey[size];
                    }
                }
            }
