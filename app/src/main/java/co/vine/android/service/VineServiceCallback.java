            package co.vine.android.service;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class VineServiceCallback implements Parcelable {
                public static final Parcelable.Creator<VineServiceCallback> CREATOR = new C05231();
                private final VineServiceResponder mResponder;

                /* JADX INFO: renamed from: co.vine.android.service.VineServiceCallback$1 */
/* 28 */        static class C05231 implements Parcelable.Creator<VineServiceCallback> {
/* 29 */            C05231() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 31 */            public VineServiceCallback createFromParcel(Parcel source) {
/* 32 */                return new VineServiceCallback(source);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 35 */            public VineServiceCallback[] newArray(int size) {
/* 36 */                return new VineServiceCallback[size];
                    }
                }

/* 47 */        public VineServiceCallback(VineServiceResponder responder) {
/* 49 */            this.mResponder = responder;
                }

/* 56 */        public VineServiceCallback(Parcel parcel) {
/* 58 */            this.mResponder = (VineServiceResponder) parcel.readStrongBinder();
                }

/* 61 */        public VineServiceResponder getResponder() {
/* 62 */            return this.mResponder;
                }

/* 70 */        public void onServiceResponse(Bundle bundle) {
/* 71 */            this.mResponder.onServiceResponse(bundle.getInt("action_code", 0), bundle.getInt(VineService.EXTRA_RESPONSE_CODE, 0), bundle.getString(VineService.EXTRA_REASON_PHRASE), bundle);
                }

                @Override // android.os.Parcelable
/* 77 */        public int describeContents() {
/* 78 */            return 0;
                }

                @Override // android.os.Parcelable
/* 81 */        public void writeToParcel(Parcel dest, int flags) {
/* 82 */            dest.writeStrongBinder(this.mResponder);
                }
            }
