            package co.vine.android.service;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            
    public class VineServiceCallback implements Parcelable {
                public static final Parcelable.Creator<VineServiceCallback> CREATOR = new C05231();
                private final VineServiceResponder mResponder;

                
        static class C05231 implements Parcelable.Creator<VineServiceCallback> {
            C05231() {
                    }



                    
                    @Override 
            public VineServiceCallback createFromParcel(Parcel source) {
                return new VineServiceCallback(source);
                    }

                    
                    @Override 
            public VineServiceCallback[] newArray(int size) {
                return new VineServiceCallback[size];
                    }
                }

        public VineServiceCallback(VineServiceResponder responder) {
            this.mResponder = responder;
                }

        public VineServiceCallback(Parcel parcel) {
            this.mResponder = (VineServiceResponder) parcel.readStrongBinder();
                }

        public VineServiceResponder getResponder() {
            return this.mResponder;
                }

        public void onServiceResponse(Bundle bundle) {
            this.mResponder.onServiceResponse(bundle.getInt("action_code", 0), bundle.getInt(VineService.EXTRA_RESPONSE_CODE, 0), bundle.getString(VineService.EXTRA_REASON_PHRASE), bundle);
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStrongBinder(this.mResponder);
                }
            }
