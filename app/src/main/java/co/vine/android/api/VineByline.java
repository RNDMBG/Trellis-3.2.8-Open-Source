            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VineByline implements Parcelable {
                public static final Parcelable.Creator<VineByline> CREATOR = new C03191();
                public String body;
                public ArrayList<VineEntity> entities;
                public String iconUrl;

        public VineByline(String body, String iconUrl, ArrayList<VineEntity> entities) {
            this.body = body;
            this.iconUrl = iconUrl;
            this.entities = entities;
                }

        public VineByline(Parcel in) {
            this.body = in.readString();
            this.iconUrl = in.readString();
            this.entities = new ArrayList<>();
            in.readTypedList(this.entities, VineEntity.CREATOR);
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.body);
            out.writeString(this.iconUrl);
            if (this.entities != null) {
                out.writeTypedList(this.entities);
                    }
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
        static class C03191 implements Parcelable.Creator<VineByline> {
            C03191() {
                    }



                    
                    @Override 
            public VineByline createFromParcel(Parcel in) {
                return new VineByline(in);
                    }

                    
                    @Override 
            public VineByline[] newArray(int size) {
                return new VineByline[size];
                    }
                }
            }
