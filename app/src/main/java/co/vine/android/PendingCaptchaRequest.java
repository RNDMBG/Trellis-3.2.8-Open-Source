            package co.vine.android;

            import android.os.Bundle;
            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class PendingCaptchaRequest implements Parcelable {
                public static final int CAPTCHA_STATE_FAIL_REQUEST = 3;
                public static final int CAPTCHA_STATE_RETRY_REQUEST = 2;
                public static final int CAPTCHA_STATE_UNHANDLED = 1;
                public static final Parcelable.Creator<PendingCaptchaRequest> CREATOR = new C02471();
                public final int actionCode;
                public final Bundle bundle;
                public final String reqId;
                public int state;

                /* JADX INFO: renamed from: co.vine.android.PendingCaptchaRequest$1 */
/* 21 */        static class C02471 implements Parcelable.Creator<PendingCaptchaRequest> {
/* 22 */            C02471() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 24 */            public PendingCaptchaRequest createFromParcel(Parcel in) {
/* 25 */                return new PendingCaptchaRequest(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 28 */            public PendingCaptchaRequest[] newArray(int i) {
/* 29 */                return new PendingCaptchaRequest[i];
                    }
                }

/* 32 */        public PendingCaptchaRequest(String reqId, int actionCode, Bundle b) {
/* 34 */            this.reqId = reqId;
/* 35 */            this.actionCode = actionCode;
/* 36 */            this.bundle = b;
/* 37 */            this.state = 1;
                }

/* 39 */        public PendingCaptchaRequest(Parcel in) {
/* 41 */            this.reqId = in.readString();
/* 42 */            this.actionCode = in.readInt();
/* 43 */            this.bundle = in.readBundle();
/* 44 */            this.state = in.readInt();
                }

                @Override // android.os.Parcelable
/* 47 */        public int describeContents() {
/* 48 */            return 0;
                }

                @Override // android.os.Parcelable
/* 51 */        public void writeToParcel(Parcel out, int flags) {
/* 52 */            out.writeString(this.reqId);
/* 53 */            out.writeInt(this.actionCode);
/* 54 */            out.writeBundle(this.bundle);
/* 55 */            out.writeInt(this.state);
                }
            }
