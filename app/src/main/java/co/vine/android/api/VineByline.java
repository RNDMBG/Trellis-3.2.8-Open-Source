            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class VineByline implements Parcelable {
                public static final Parcelable.Creator<VineByline> CREATOR = new C03191();
                public String body;
                public ArrayList<VineEntity> entities;
                public String iconUrl;

/* 17 */        public VineByline(String body, String iconUrl, ArrayList<VineEntity> entities) {
/* 19 */            this.body = body;
/* 20 */            this.iconUrl = iconUrl;
/* 21 */            this.entities = entities;
                }

/* 23 */        public VineByline(Parcel in) {
/* 25 */            this.body = in.readString();
/* 26 */            this.iconUrl = in.readString();
/* 27 */            this.entities = new ArrayList<>();
/* 28 */            in.readTypedList(this.entities, VineEntity.CREATOR);
                }

                @Override // android.os.Parcelable
/* 32 */        public void writeToParcel(Parcel out, int flags) {
/* 33 */            out.writeString(this.body);
/* 34 */            out.writeString(this.iconUrl);
/* 35 */            if (this.entities != null) {
/* 36 */                out.writeTypedList(this.entities);
                    }
                }

                @Override // android.os.Parcelable
/* 41 */        public int describeContents() {
/* 42 */            return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineByline$1 */
/* 44 */        static class C03191 implements Parcelable.Creator<VineByline> {
/* 45 */            C03191() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 48 */            public VineByline createFromParcel(Parcel in) {
/* 49 */                return new VineByline(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 53 */            public VineByline[] newArray(int size) {
/* 54 */                return new VineByline[size];
                    }
                }
            }
