            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineClientFlags implements Parcelable {
                public static final Parcelable.Creator<VineClientFlags> CREATOR = new C03211();
                public String apiHost;
                public boolean doubleTapToLikeOnPause;
                public String exploreHost;
                public String mediaHost;
                public String messageText;
                public String messageTitle;
                public boolean prefetchEnabled;
                public String rtcHost;
                public long ttlSeconds;
                public boolean useVinePlayer;

        public VineClientFlags(String messageTitle, String messageText, String apiHost, String rtcHost, String mediaHost, String exploreHost, long ttlSeconds, boolean doubleTapToLikeOnPause, boolean prefetchEnabled, boolean useVinePlayer) {
            this.messageTitle = null;
            this.messageText = null;
            this.apiHost = null;
            this.rtcHost = null;
            this.mediaHost = null;
            this.exploreHost = null;
            this.ttlSeconds = 0L;
            this.messageTitle = messageTitle;
            this.messageText = messageText;
            this.apiHost = apiHost;
            this.rtcHost = rtcHost;
            this.mediaHost = mediaHost;
            this.exploreHost = exploreHost;
            this.ttlSeconds = ttlSeconds;
            this.doubleTapToLikeOnPause = doubleTapToLikeOnPause;
            this.prefetchEnabled = prefetchEnabled;
            this.useVinePlayer = useVinePlayer;
                }

        public VineClientFlags(Parcel in) {
            this.messageTitle = null;
            this.messageText = null;
            this.apiHost = null;
            this.rtcHost = null;
            this.mediaHost = null;
            this.exploreHost = null;
            this.ttlSeconds = 0L;
            this.messageTitle = in.readString();
            this.messageText = in.readString();
            this.apiHost = in.readString();
            this.rtcHost = in.readString();
            this.mediaHost = in.readString();
            this.exploreHost = in.readString();
            this.ttlSeconds = in.readLong();
            this.doubleTapToLikeOnPause = in.readInt() != 0;
            this.prefetchEnabled = in.readInt() != 0;
            this.useVinePlayer = in.readInt() != 0;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.messageTitle);
            dest.writeString(this.messageText);
            dest.writeString(this.apiHost);
            dest.writeString(this.rtcHost);
            dest.writeString(this.mediaHost);
            dest.writeString(this.exploreHost);
            dest.writeLong(this.ttlSeconds);
            dest.writeInt(this.doubleTapToLikeOnPause ? 1 : 0);
            dest.writeInt(this.prefetchEnabled ? 1 : 0);
            dest.writeInt(this.useVinePlayer ? 1 : 0);
                }

                
        static class C03211 implements Parcelable.Creator<VineClientFlags> {
            C03211() {
                    }



                    
                    @Override 
            public VineClientFlags createFromParcel(Parcel in) {
                return new VineClientFlags(in);
                    }

                    
                    @Override 
            public VineClientFlags[] newArray(int size) {
                return new VineClientFlags[size];
                    }
                }
            }
