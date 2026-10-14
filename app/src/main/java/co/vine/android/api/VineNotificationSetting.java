            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.Pair;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class VineNotificationSetting implements Parcelable {
                public static final Parcelable.Creator<VineNotificationSetting> CREATOR = new C03331();
                public ArrayList<Pair<String, String>> choices;
                public boolean isBooleanSetting;
                public String name;
                public String section;
                public String title;
                public String value;

/* 28 */        public VineNotificationSetting(boolean isBooleanSetting, String name, String title, String section, String value, ArrayList<Pair<String, String>> choices) {
/* 30 */            this.isBooleanSetting = isBooleanSetting;
/* 31 */            this.name = name;
/* 32 */            this.title = title;
/* 33 */            this.section = section;
/* 34 */            this.value = value;
/* 35 */            this.choices = choices;
                }

/* 37 */        public VineNotificationSetting(Parcel in) {
/* 39 */            this.isBooleanSetting = in.readInt() == 1;
/* 40 */            this.name = in.readString();
/* 41 */            this.title = in.readString();
/* 42 */            this.section = in.readString();
/* 43 */            this.value = in.readString();
/* 44 */            this.choices = (ArrayList) in.readSerializable();
                }

                @Override // android.os.Parcelable
/* 48 */        public int describeContents() {
/* 49 */            return 0;
                }

                @Override // android.os.Parcelable
/* 53 */        public void writeToParcel(Parcel out, int flags) {
/* 54 */            out.writeInt(this.isBooleanSetting ? 1 : 0);
/* 55 */            out.writeString(this.name);
/* 56 */            out.writeString(this.title);
/* 57 */            out.writeString(this.section);
/* 58 */            out.writeString(this.value);
/* 59 */            out.writeSerializable(this.choices);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineNotificationSetting$1 */
/* 60 */        static class C03331 implements Parcelable.Creator<VineNotificationSetting> {
/* 61 */            C03331() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 63 */            public VineNotificationSetting createFromParcel(Parcel in) {
/* 64 */                return new VineNotificationSetting(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 67 */            public VineNotificationSetting[] newArray(int size) {
/* 68 */                return new VineNotificationSetting[size];
                    }
                }

/* 72 */        public void addToMap(HashMap<String, String> values) {
/* 73 */            values.put(this.name, this.value);
                }
            }
