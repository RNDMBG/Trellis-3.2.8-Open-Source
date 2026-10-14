            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.util.Pair;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            
    public class VineNotificationSetting implements Parcelable {
                public static final Parcelable.Creator<VineNotificationSetting> CREATOR = new C03331();
                public ArrayList<Pair<String, String>> choices;
                public boolean isBooleanSetting;
                public String name;
                public String section;
                public String title;
                public String value;

        public VineNotificationSetting(boolean isBooleanSetting, String name, String title, String section, String value, ArrayList<Pair<String, String>> choices) {
            this.isBooleanSetting = isBooleanSetting;
            this.name = name;
            this.title = title;
            this.section = section;
            this.value = value;
            this.choices = choices;
                }

        public VineNotificationSetting(Parcel in) {
            this.isBooleanSetting = in.readInt() == 1;
            this.name = in.readString();
            this.title = in.readString();
            this.section = in.readString();
            this.value = in.readString();
            this.choices = (ArrayList) in.readSerializable();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.isBooleanSetting ? 1 : 0);
            out.writeString(this.name);
            out.writeString(this.title);
            out.writeString(this.section);
            out.writeString(this.value);
            out.writeSerializable(this.choices);
                }

                
        static class C03331 implements Parcelable.Creator<VineNotificationSetting> {
            C03331() {
                    }



                    
                    @Override 
            public VineNotificationSetting createFromParcel(Parcel in) {
                return new VineNotificationSetting(in);
                    }

                    
                    @Override 
            public VineNotificationSetting[] newArray(int size) {
                return new VineNotificationSetting[size];
                    }
                }

        public void addToMap(HashMap<String, String> values) {
            values.put(this.name, this.value);
                }
            }
