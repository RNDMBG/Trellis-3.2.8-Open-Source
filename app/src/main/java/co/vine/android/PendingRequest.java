            package co.vine.android;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class PendingRequest implements Parcelable {
                public static final Parcelable.Creator<PendingRequest> CREATOR = new C02481();
                public final int fetchType;
                public final long createdAt;

                /* JADX INFO: renamed from: id */
                public final String f81id;

                /* JADX INFO: renamed from: co.vine.android.PendingRequest$1 */
/* 14 */        static class C02481 implements Parcelable.Creator<PendingRequest> {
/* 15 */            C02481() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 17 */            public PendingRequest createFromParcel(Parcel in) {
/* 18 */                return new PendingRequest(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 21 */            public PendingRequest[] newArray(int i) {
/* 22 */                return new PendingRequest[i];
                    }
                }

/* 25 */        public PendingRequest(String id) {
/* 27 */            this.f81id = id;
/* 28 */            this.fetchType = 0;
                    this.createdAt = System.currentTimeMillis();
                }

/* 30 */        public PendingRequest(String id, int fetchType) {
/* 32 */            this.f81id = id;
/* 33 */            this.fetchType = fetchType;
                    this.createdAt = System.currentTimeMillis();
                }

/* 35 */        public PendingRequest(Parcel in) {
/* 37 */            this.f81id = in.readString();
/* 38 */            this.fetchType = in.readInt();
                    this.createdAt = in.readLong();
                }

                @Override // android.os.Parcelable
/* 41 */        public int describeContents() {
/* 42 */            return 0;
                }

                @Override // android.os.Parcelable
/* 45 */        public void writeToParcel(Parcel out, int flags) {
/* 46 */            out.writeString(this.f81id);
/* 47 */            out.writeInt(this.fetchType);
                out.writeLong(this.createdAt);
                }

/* 51 */        public boolean equals(Object o) {
/* 52 */            if (this == o) {
                        return true;
                    }
/* 53 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 55 */            PendingRequest request = (PendingRequest) o;
/* 56 */            if (this.f81id != null) {
                        if (this.f81id.equals(request.f81id)) {
                            return true;
                        }
                    } else if (request.f81id == null) {
                        return true;
                    }
                    return false;
                }

/* 60 */        public int hashCode() {
/* 61 */            if (this.f81id != null) {
                        return this.f81id.hashCode();
                    }
                    return 0;
                }
            }
