            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineMilestone implements Parcelable {
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

        public VineMilestone(String backgroundImageUrl, String backgroundVideoUrl, String iconUrl, String title, String description, String milestoneUrl, int overlayColor, float overlayAlpha, int blurRadius) {
            this.backgroundImageUrl = backgroundImageUrl;
            this.backgroundVideoUrl = backgroundVideoUrl;
            this.iconUrl = iconUrl;
            this.title = title;
            this.description = description;
            this.milestoneUrl = milestoneUrl;
            this.overlayColor = overlayColor;
            this.overlayAlpha = overlayAlpha;
            this.blurRadius = blurRadius;
                }

        public VineMilestone(Parcel in) {
            this.backgroundImageUrl = in.readString();
            this.backgroundVideoUrl = in.readString();
            this.iconUrl = in.readString();
            this.title = in.readString();
            this.description = in.readString();
            this.milestoneUrl = in.readString();
            this.overlayColor = in.readInt();
            this.overlayAlpha = in.readFloat();
            this.blurRadius = in.readInt();
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.backgroundImageUrl);
            dest.writeString(this.backgroundVideoUrl);
            dest.writeString(this.iconUrl);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeString(this.milestoneUrl);
            dest.writeInt(this.overlayColor);
            dest.writeFloat(this.overlayAlpha);
            dest.writeInt(this.blurRadius);
                }

                
        static class C03321 implements Parcelable.Creator<VineMilestone> {
            C03321() {
                    }



                    
                    @Override 
            public VineMilestone createFromParcel(Parcel in) {
                return new VineMilestone(in);
                    }

                    
                    @Override 
            public VineMilestone[] newArray(int size) {
                return new VineMilestone[size];
                    }
                }

                @Override 
        public int describeContents() {
            return 0;
                }
            }
