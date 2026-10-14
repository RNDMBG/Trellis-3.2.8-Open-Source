            package co.vine.android.widget.tabs;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class TabSavedState implements Parcelable {
                public static final Parcelable.Creator<TabSavedState> CREATOR = new C05801();
                public final String[] tags;

/* 13 */        public TabSavedState(ArrayList<TabInfo> infos) {
/* 15 */            int size = infos.size();
/* 16 */            String[] tabs = new String[size];
                    for (int i = 0; i < size; i++) {
/* 18 */                TabInfo info = infos.get(i);
/* 19 */                tabs[i] = info.mTag;
                    }
/* 21 */            this.tags = tabs;
                }

/* 23 */        public TabSavedState(Parcel in) {
/* 25 */            this.tags = in.createStringArray();
                }

                @Override // android.os.Parcelable
/* 29 */        public int describeContents() {
/* 30 */            return 0;
                }

                @Override // android.os.Parcelable
/* 34 */        public void writeToParcel(Parcel out, int flags) {
/* 35 */            out.writeStringArray(this.tags);
                }

                /* JADX INFO: renamed from: co.vine.android.widget.tabs.TabSavedState$1 */
/* 37 */        static class C05801 implements Parcelable.Creator<TabSavedState> {
/* 38 */            C05801() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 40 */            public TabSavedState createFromParcel(Parcel in) {
/* 41 */                return new TabSavedState(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 44 */            public TabSavedState[] newArray(int size) {
/* 45 */                return new TabSavedState[size];
                    }
                }
            }
