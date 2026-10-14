            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class VineError implements Parcelable {
                public static final Parcelable.Creator<VineError> CREATOR = new C03261();
                public final String data;
                public final int errorCode;
                public final String message;

/* 15 */        public VineError(int errorCode, String message) {
/* 16 */            this(errorCode, message, null);
                }

/* 18 */        public VineError(Parcel in) {
/* 20 */            this.errorCode = in.readInt();
/* 21 */            this.message = in.readString();
/* 22 */            this.data = in.readString();
                }

/* 24 */        public VineError(int errorCode, String message, String data) {
/* 26 */            this.errorCode = errorCode;
/* 27 */            this.message = message;
/* 28 */            this.data = data;
                }

                @Override // android.os.Parcelable
/* 32 */        public int describeContents() {
/* 33 */            return 0;
                }

                @Override // android.os.Parcelable
/* 37 */        public void writeToParcel(Parcel out, int flags) {
/* 38 */            out.writeInt(this.errorCode);
/* 39 */            out.writeString(this.message);
/* 40 */            out.writeString(this.data);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineError$1 */
/* 41 */        static class C03261 implements Parcelable.Creator<VineError> {
/* 42 */            C03261() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 44 */            public VineError createFromParcel(Parcel in) {
/* 45 */                return new VineError(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 48 */            public VineError[] newArray(int size) {
/* 49 */                return new VineError[size];
                    }
                }

/* 58 */        public boolean equals(Object o) {
/* 59 */            if (o == null) {
                        return false;
                    }
/* 62 */            if (o.getClass() == getClass()) {
/* 63 */                return this.errorCode == ((VineError) o).errorCode;
                    }
/* 65 */            if (o instanceof VineKnownErrors) {
/* 66 */                return this.errorCode == ((VineKnownErrors) o).code;
                    }
                    return false;
                }
            }
