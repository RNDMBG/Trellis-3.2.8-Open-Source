            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class VineActivityCounts implements Parcelable {
                public static final Parcelable.Creator<VineActivityCounts> CREATOR = new C03181();
                public int messages;
                public int notifications;

/* 13 */        public VineActivityCounts(int messages, int notifications) {
/* 15 */            this.messages = messages;
/* 16 */            this.notifications = notifications;
                }

/* 18 */        public VineActivityCounts(Parcel in) {
/* 20 */            this.messages = in.readInt();
/* 21 */            this.notifications = in.readInt();
                }

                @Override // android.os.Parcelable
/* 25 */        public void writeToParcel(Parcel out, int flags) {
/* 26 */            out.writeInt(this.messages);
/* 27 */            out.writeInt(this.notifications);
                }

                @Override // android.os.Parcelable
/* 31 */        public int describeContents() {
/* 32 */            return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineActivityCounts$1 */
/* 33 */        static class C03181 implements Parcelable.Creator<VineActivityCounts> {
/* 34 */            C03181() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 36 */            public VineActivityCounts createFromParcel(Parcel in) {
/* 37 */                return new VineActivityCounts(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 40 */            public VineActivityCounts[] newArray(int size) {
/* 41 */                return new VineActivityCounts[size];
                    }
                }
            }
