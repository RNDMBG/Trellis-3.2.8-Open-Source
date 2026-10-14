            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class VineMilestone implements Parcelable {
                public static final Parcelable.Creator<VineMilestone> CREATOR = new C03321();
                public String backgroundImageUrl;
                public String backgroundVideoUrl;
                public int blurRadius;
                public String description;
                public String iconUrl;
                public String milestoneUrl;
                public float overlayAlpha;
                public int overlayColor;
                public String title;

/* 21 */        public VineMilestone(String backgroundImageUrl, String backgroundVideoUrl, String iconUrl, String title, String description, String milestoneUrl, int overlayColor, float overlayAlpha, int blurRadius) {
/* 23 */            this.backgroundImageUrl = backgroundImageUrl;
/* 24 */            this.backgroundVideoUrl = backgroundVideoUrl;
/* 25 */            this.iconUrl = iconUrl;
/* 26 */            this.title = title;
/* 27 */            this.description = description;
/* 28 */            this.milestoneUrl = milestoneUrl;
/* 29 */            this.overlayColor = overlayColor;
/* 30 */            this.overlayAlpha = overlayAlpha;
/* 31 */            this.blurRadius = blurRadius;
                }

/* 33 */        public VineMilestone(Parcel in) {
/* 35 */            this.backgroundImageUrl = in.readString();
/* 36 */            this.backgroundVideoUrl = in.readString();
/* 37 */            this.iconUrl = in.readString();
/* 38 */            this.title = in.readString();
/* 39 */            this.description = in.readString();
/* 40 */            this.milestoneUrl = in.readString();
/* 41 */            this.overlayColor = in.readInt();
/* 42 */            this.overlayAlpha = in.readFloat();
/* 43 */            this.blurRadius = in.readInt();
                }

                @Override // android.os.Parcelable
/* 47 */        public void writeToParcel(Parcel dest, int flags) {
/* 48 */            dest.writeString(this.backgroundImageUrl);
/* 49 */            dest.writeString(this.backgroundVideoUrl);
/* 50 */            dest.writeString(this.iconUrl);
/* 51 */            dest.writeString(this.title);
/* 52 */            dest.writeString(this.description);
/* 53 */            dest.writeString(this.milestoneUrl);
/* 54 */            dest.writeInt(this.overlayColor);
/* 55 */            dest.writeFloat(this.overlayAlpha);
/* 56 */            dest.writeInt(this.blurRadius);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineMilestone$1 */
/* 57 */        static class C03321 implements Parcelable.Creator<VineMilestone> {
/* 58 */            C03321() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 60 */            public VineMilestone createFromParcel(Parcel in) {
/* 61 */                return new VineMilestone(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 64 */            public VineMilestone[] newArray(int size) {
/* 65 */                return new VineMilestone[size];
                    }
                }

                @Override // android.os.Parcelable
/* 70 */        public int describeContents() {
/* 71 */            return 0;
                }
            }
