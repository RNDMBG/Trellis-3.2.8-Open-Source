            package co.vine.android;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class PendingRequest implements Parcelable {
                public static final Parcelable.Creator<PendingRequest> CREATOR = new C02481();
                public final int fetchType;
                public final long createdAt;

                
                public final String f81id;

                
        static class C02481 implements Parcelable.Creator<PendingRequest> {
            C02481() {
                    }



                    
                    @Override 
            public PendingRequest createFromParcel(Parcel in) {
                return new PendingRequest(in);
                    }

                    
                    @Override 
            public PendingRequest[] newArray(int i) {
                return new PendingRequest[i];
                    }
                }

        public PendingRequest(String id) {
            this.f81id = id;
            this.fetchType = 0;
                    this.createdAt = System.currentTimeMillis();
                }

        public PendingRequest(String id, int fetchType) {
            this.f81id = id;
            this.fetchType = fetchType;
                    this.createdAt = System.currentTimeMillis();
                }

        public PendingRequest(Parcel in) {
            this.f81id = in.readString();
            this.fetchType = in.readInt();
                    this.createdAt = in.readLong();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.f81id);
            out.writeInt(this.fetchType);
                out.writeLong(this.createdAt);
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            PendingRequest request = (PendingRequest) o;
            if (this.f81id != null) {
                        if (this.f81id.equals(request.f81id)) {
                            return true;
                        }
                    } else if (request.f81id == null) {
                        return true;
                    }
                    return false;
                }

        public int hashCode() {
            if (this.f81id != null) {
                        return this.f81id.hashCode();
                    }
                    return 0;
                }
            }
