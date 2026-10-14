            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class ServerStatus implements Parcelable {
                public static final Parcelable.Creator<ServerStatus> CREATOR = new C03161();
                public String message;
                public String staticTimelineUrl;
                public String status;
                public String title;
                public String uploadType;

        public ServerStatus(String uploadType, String status, String title, String staticTimelineUrl, String message) {
            this.uploadType = uploadType;
            this.status = status;
            this.title = title;
            this.staticTimelineUrl = staticTimelineUrl;
            this.message = message;
                }

        public ServerStatus(Parcel in) {
            this.uploadType = in.readString();
            this.status = in.readString();
            this.title = in.readString();
            this.staticTimelineUrl = in.readString();
            this.message = in.readString();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.uploadType);
            out.writeString(this.status);
            out.writeString(this.title);
            out.writeString(this.staticTimelineUrl);
            out.writeString(this.message);
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            ServerStatus that = (ServerStatus) o;
            if (this.message == null ? that.message != null : !this.message.equals(that.message)) {
                        return false;
                    }
            if (this.staticTimelineUrl == null ? that.staticTimelineUrl != null : !this.staticTimelineUrl.equals(that.staticTimelineUrl)) {
                        return false;
                    }
            if (this.status == null ? that.status != null : !this.status.equals(that.status)) {
                        return false;
                    }
            if (this.title == null ? that.title != null : !this.title.equals(that.title)) {
                        return false;
                    }
            if (this.uploadType != null) {
                        if (this.uploadType.equals(that.uploadType)) {
                            return true;
                        }
                    } else if (that.uploadType == null) {
                        return true;
                    }
                    return false;
                }

        public int hashCode() {
            int result = this.uploadType != null ? this.uploadType.hashCode() : 0;
            return (((((((result * 31) + (this.status != null ? this.status.hashCode() : 0)) * 31) + (this.title != null ? this.title.hashCode() : 0)) * 31) + (this.staticTimelineUrl != null ? this.staticTimelineUrl.hashCode() : 0)) * 31) + (this.message != null ? this.message.hashCode() : 0);
                }

                
        static class C03161 implements Parcelable.Creator<ServerStatus> {
            C03161() {
                    }



                    
                    @Override 
            public ServerStatus createFromParcel(Parcel in) {
                return new ServerStatus(in);
                    }

                    
                    @Override 
            public ServerStatus[] newArray(int size) {
                return new ServerStatus[size];
                    }
                }
            }
