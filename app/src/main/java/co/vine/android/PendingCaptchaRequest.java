            package co.vine.android;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class PendingCaptchaRequest implements Parcelable {
                public static final int CAPTCHA_STATE_FAIL_REQUEST = 3;
                public static final int CAPTCHA_STATE_RETRY_REQUEST = 2;
                public static final int CAPTCHA_STATE_UNHANDLED = 1;
                public static final Parcelable.Creator<PendingCaptchaRequest> CREATOR = new C02471();
                public final int actionCode;
                public final Bundle bundle;
                public final String reqId;
                public int state;

                
        static class C02471 implements Parcelable.Creator<PendingCaptchaRequest> {
            C02471() {
                    }



                    
                    @Override 
            public PendingCaptchaRequest createFromParcel(Parcel in) {
                return new PendingCaptchaRequest(in);
                    }

                    
                    @Override 
            public PendingCaptchaRequest[] newArray(int i) {
                return new PendingCaptchaRequest[i];
                    }
                }

        public PendingCaptchaRequest(String reqId, int actionCode, Bundle b) {
            this.reqId = reqId;
            this.actionCode = actionCode;
            this.bundle = b;
            this.state = 1;
                }

        public PendingCaptchaRequest(Parcel in) {
            this.reqId = in.readString();
            this.actionCode = in.readInt();
            this.bundle = in.readBundle();
            this.state = in.readInt();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.reqId);
            out.writeInt(this.actionCode);
            out.writeBundle(this.bundle);
            out.writeInt(this.state);
                }
            }
