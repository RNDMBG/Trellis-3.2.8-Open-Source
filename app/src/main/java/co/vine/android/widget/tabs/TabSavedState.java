            package co.vine.android.widget.tabs;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class TabSavedState implements Parcelable {
                public static final Parcelable.Creator<TabSavedState> CREATOR = new C05801();
                public final String[] tags;

        public TabSavedState(ArrayList<TabInfo> infos) {
            int size = infos.size();
            String[] tabs = new String[size];
                    for (int i = 0; i < size; i++) {
                TabInfo info = infos.get(i);
                tabs[i] = info.mTag;
                    }
            this.tags = tabs;
                }

        public TabSavedState(Parcel in) {
            this.tags = in.createStringArray();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeStringArray(this.tags);
                }

                
        static class C05801 implements Parcelable.Creator<TabSavedState> {
            C05801() {
                    }



                    
                    @Override 
            public TabSavedState createFromParcel(Parcel in) {
                return new TabSavedState(in);
                    }

                    
                    @Override 
            public TabSavedState[] newArray(int size) {
                return new TabSavedState[size];
                    }
                }
            }
