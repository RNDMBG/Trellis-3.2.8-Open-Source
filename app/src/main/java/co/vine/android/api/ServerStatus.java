            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class ServerStatus implements Parcelable {
                public static final Parcelable.Creator<ServerStatus> CREATOR = new C03161();
                public String message;
                public String staticTimelineUrl;
                public String status;
                public String title;
                public String uploadType;

/* 16 */        public ServerStatus(String uploadType, String status, String title, String staticTimelineUrl, String message) {
/* 18 */            this.uploadType = uploadType;
/* 19 */            this.status = status;
/* 20 */            this.title = title;
/* 21 */            this.staticTimelineUrl = staticTimelineUrl;
/* 22 */            this.message = message;
                }

/* 24 */        public ServerStatus(Parcel in) {
/* 26 */            this.uploadType = in.readString();
/* 27 */            this.status = in.readString();
/* 28 */            this.title = in.readString();
/* 29 */            this.staticTimelineUrl = in.readString();
/* 30 */            this.message = in.readString();
                }

                @Override // android.os.Parcelable
/* 34 */        public int describeContents() {
/* 35 */            return 0;
                }

                @Override // android.os.Parcelable
/* 39 */        public void writeToParcel(Parcel out, int flags) {
/* 40 */            out.writeString(this.uploadType);
/* 41 */            out.writeString(this.status);
/* 42 */            out.writeString(this.title);
/* 43 */            out.writeString(this.staticTimelineUrl);
/* 44 */            out.writeString(this.message);
                }

/* 48 */        public boolean equals(Object o) {
/* 49 */            if (this == o) {
                        return true;
                    }
/* 50 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 52 */            ServerStatus that = (ServerStatus) o;
/* 54 */            if (this.message == null ? that.message != null : !this.message.equals(that.message)) {
                        return false;
                    }
/* 55 */            if (this.staticTimelineUrl == null ? that.staticTimelineUrl != null : !this.staticTimelineUrl.equals(that.staticTimelineUrl)) {
                        return false;
                    }
/* 58 */            if (this.status == null ? that.status != null : !this.status.equals(that.status)) {
                        return false;
                    }
/* 59 */            if (this.title == null ? that.title != null : !this.title.equals(that.title)) {
                        return false;
                    }
/* 60 */            if (this.uploadType != null) {
                        if (this.uploadType.equals(that.uploadType)) {
                            return true;
                        }
                    } else if (that.uploadType == null) {
                        return true;
                    }
                    return false;
                }

/* 67 */        public int hashCode() {
/* 68 */            int result = this.uploadType != null ? this.uploadType.hashCode() : 0;
/* 72 */            return (((((((result * 31) + (this.status != null ? this.status.hashCode() : 0)) * 31) + (this.title != null ? this.title.hashCode() : 0)) * 31) + (this.staticTimelineUrl != null ? this.staticTimelineUrl.hashCode() : 0)) * 31) + (this.message != null ? this.message.hashCode() : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.ServerStatus$1 */
/* 74 */        static class C03161 implements Parcelable.Creator<ServerStatus> {
/* 75 */            C03161() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 77 */            public ServerStatus createFromParcel(Parcel in) {
/* 78 */                return new ServerStatus(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 81 */            public ServerStatus[] newArray(int size) {
/* 82 */                return new ServerStatus[size];
                    }
                }
            }
