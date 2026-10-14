            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineError implements Parcelable {
                public static final Parcelable.Creator<VineError> CREATOR = new C03261();
                public final String data;
                public final int errorCode;
                public final String message;

        public VineError(int errorCode, String message) {
            this(errorCode, message, null);
                }

        public VineError(Parcel in) {
            this.errorCode = in.readInt();
            this.message = in.readString();
            this.data = in.readString();
                }

        public VineError(int errorCode, String message, String data) {
            this.errorCode = errorCode;
            this.message = message;
            this.data = data;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.errorCode);
            out.writeString(this.message);
            out.writeString(this.data);
                }

                
        static class C03261 implements Parcelable.Creator<VineError> {
            C03261() {
                    }



                    
                    @Override 
            public VineError createFromParcel(Parcel in) {
                return new VineError(in);
                    }

                    
                    @Override 
            public VineError[] newArray(int size) {
                return new VineError[size];
                    }
                }

        public boolean equals(Object o) {
            if (o == null) {
                        return false;
                    }
            if (o.getClass() == getClass()) {
                return this.errorCode == ((VineError) o).errorCode;
                    }
            if (o instanceof VineKnownErrors) {
                return this.errorCode == ((VineKnownErrors) o).code;
                    }
                    return false;
                }
            }
