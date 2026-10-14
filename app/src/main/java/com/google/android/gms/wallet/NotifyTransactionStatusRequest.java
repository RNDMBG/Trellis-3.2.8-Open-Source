            package com.google.android.gms.wallet;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1028dm;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class NotifyTransactionStatusRequest implements SafeParcelable {
                public static final Parcelable.Creator<NotifyTransactionStatusRequest> CREATOR = new C1215i();

                /* JADX INFO: renamed from: iM */
                final int f2515iM;
                int status;

                /* JADX INFO: renamed from: tH */
                String f2516tH;

                /* JADX INFO: renamed from: uj */
                String f2517uj;

                public final class Builder {
                    private Builder() {
                    }

                    /* synthetic */ Builder(NotifyTransactionStatusRequest x0, Object x1) {
                        this();
                    }

                    public NotifyTransactionStatusRequest build() {
                        C1028dm.m1604b(!TextUtils.isEmpty(NotifyTransactionStatusRequest.this.f2516tH), "googleTransactionId is required");
                        C1028dm.m1604b(NotifyTransactionStatusRequest.this.status >= 1 && NotifyTransactionStatusRequest.this.status <= 8, "status is an unrecognized value");
                        return NotifyTransactionStatusRequest.this;
                    }

                    public Builder setDetailedReason(String detailedReason) {
                        NotifyTransactionStatusRequest.this.f2517uj = detailedReason;
                        return this;
                    }

                    public Builder setGoogleTransactionId(String googleTransactionId) {
                        NotifyTransactionStatusRequest.this.f2516tH = googleTransactionId;
                        return this;
                    }

                    public Builder setStatus(int status) {
                        NotifyTransactionStatusRequest.this.status = status;
                        return this;
                    }
                }

                public interface Status {
                    public static final int SUCCESS = 1;

                    public interface Error {
                        public static final int AVS_DECLINE = 7;
                        public static final int BAD_CARD = 4;
                        public static final int BAD_CVC = 3;
                        public static final int DECLINED = 5;
                        public static final int FRAUD_DECLINE = 8;
                        public static final int OTHER = 6;
                        public static final int UNKNOWN = 2;
                    }
                }

                public NotifyTransactionStatusRequest() {
                    this.f2515iM = 1;
                }

                NotifyTransactionStatusRequest(int versionCode, String googleTransactionId, int status, String detailedReason) {
                    this.f2515iM = versionCode;
                    this.f2516tH = googleTransactionId;
                    this.status = status;
                    this.f2517uj = detailedReason;
                }

                public static Builder newBuilder() {
                    NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
                    notifyTransactionStatusRequest.getClass();
                    return new Builder(notifyTransactionStatusRequest, null);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                public String getDetailedReason() {
                    return this.f2517uj;
                }

                public String getGoogleTransactionId() {
                    return this.f2516tH;
                }

                public int getStatus() {
                    return this.status;
                }

                public int getVersionCode() {
                    return this.f2515iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    C1215i.m2366a(this, out, flags);
                }
            }
