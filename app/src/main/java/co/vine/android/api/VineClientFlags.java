            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class VineClientFlags implements Parcelable {
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

/* 24 */        public VineClientFlags(String messageTitle, String messageText, String apiHost, String rtcHost, String mediaHost, String exploreHost, long ttlSeconds, boolean doubleTapToLikeOnPause, boolean prefetchEnabled, boolean useVinePlayer) {
/* 11 */            this.messageTitle = null;
/* 12 */            this.messageText = null;
/* 13 */            this.apiHost = null;
/* 14 */            this.rtcHost = null;
/* 15 */            this.mediaHost = null;
/* 16 */            this.exploreHost = null;
/* 18 */            this.ttlSeconds = 0L;
/* 26 */            this.messageTitle = messageTitle;
/* 27 */            this.messageText = messageText;
/* 28 */            this.apiHost = apiHost;
/* 29 */            this.rtcHost = rtcHost;
/* 30 */            this.mediaHost = mediaHost;
/* 31 */            this.exploreHost = exploreHost;
/* 32 */            this.ttlSeconds = ttlSeconds;
/* 33 */            this.doubleTapToLikeOnPause = doubleTapToLikeOnPause;
/* 34 */            this.prefetchEnabled = prefetchEnabled;
/* 35 */            this.useVinePlayer = useVinePlayer;
                }

/* 37 */        public VineClientFlags(Parcel in) {
/* 11 */            this.messageTitle = null;
/* 12 */            this.messageText = null;
/* 13 */            this.apiHost = null;
/* 14 */            this.rtcHost = null;
/* 15 */            this.mediaHost = null;
/* 16 */            this.exploreHost = null;
/* 18 */            this.ttlSeconds = 0L;
/* 39 */            this.messageTitle = in.readString();
/* 40 */            this.messageText = in.readString();
/* 41 */            this.apiHost = in.readString();
/* 42 */            this.rtcHost = in.readString();
/* 43 */            this.mediaHost = in.readString();
/* 44 */            this.exploreHost = in.readString();
/* 45 */            this.ttlSeconds = in.readLong();
/* 46 */            this.doubleTapToLikeOnPause = in.readInt() != 0;
/* 47 */            this.prefetchEnabled = in.readInt() != 0;
/* 48 */            this.useVinePlayer = in.readInt() != 0;
                }

                @Override // android.os.Parcelable
/* 52 */        public int describeContents() {
/* 53 */            return 0;
                }

                @Override // android.os.Parcelable
/* 57 */        public void writeToParcel(Parcel dest, int flags) {
/* 58 */            dest.writeString(this.messageTitle);
/* 59 */            dest.writeString(this.messageText);
/* 60 */            dest.writeString(this.apiHost);
/* 61 */            dest.writeString(this.rtcHost);
/* 62 */            dest.writeString(this.mediaHost);
/* 63 */            dest.writeString(this.exploreHost);
/* 64 */            dest.writeLong(this.ttlSeconds);
/* 65 */            dest.writeInt(this.doubleTapToLikeOnPause ? 1 : 0);
/* 66 */            dest.writeInt(this.prefetchEnabled ? 1 : 0);
/* 67 */            dest.writeInt(this.useVinePlayer ? 1 : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineClientFlags$1 */
/* 68 */        static class C03211 implements Parcelable.Creator<VineClientFlags> {
/* 69 */            C03211() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 71 */            public VineClientFlags createFromParcel(Parcel in) {
/* 72 */                return new VineClientFlags(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 75 */            public VineClientFlags[] newArray(int size) {
/* 76 */                return new VineClientFlags[size];
                    }
                }
            }
