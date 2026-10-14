            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            
    public class VinePagedData<T extends Parcelable> implements Parcelable {
                public static final Parcelable.Creator<VinePagedData> CREATOR = new C03341();
                public long anchor;
                public VineChannel channel;
                public int count;
                public ArrayList<T> items;
                public long lastMessage;
                public long lastMessageRead;
                public int networkType;
                public int nextPage;
                public ArrayList<VineUser> participants;
                public int previousPage;
                public int size;
                public String stringAnchor;
                public String title;
                public long unreadMessageCount;

        public VinePagedData() {
            this.previousPage = -1;
            this.unreadMessageCount = 0L;
            this.lastMessageRead = 0L;
            this.lastMessage = 0L;
                }

        public VinePagedData(Parcel parcel) {
            this.previousPage = -1;
            this.unreadMessageCount = 0L;
            this.lastMessageRead = 0L;
            this.lastMessage = 0L;
            this.anchor = parcel.readLong();
            this.count = parcel.readInt();
            this.nextPage = parcel.readInt();
            this.previousPage = parcel.readInt();
            this.size = parcel.readInt();
            this.items = new ArrayList<>();
                    for (Parcelable parcelable : parcel.readParcelableArray(VinePagedData.class.getClassLoader())) {
                this.items.add((T) parcelable);
                    }
            this.title = parcel.readString();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                
                @Override 
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeLong(this.anchor);
            parcel.writeInt(this.count);
            parcel.writeInt(this.nextPage);
            parcel.writeInt(this.previousPage);
            parcel.writeInt(this.size);
            if (this.items == null) {
                parcel.writeParcelableArray((Parcelable[]) new ArrayList().toArray(new Parcelable[0]), flags);
                    } else {
                parcel.writeParcelableArray((Parcelable[]) this.items.toArray(new Parcelable[this.items.size()]), flags);
                    }
            parcel.writeString(this.title);
                }

        public boolean isEmpty() {
                    return this.items != null && this.items.isEmpty();
                }

                
        static class C03341 implements Parcelable.Creator<VinePagedData> {
            C03341() {
                    }



                    
                    @Override 
            public VinePagedData createFromParcel(Parcel in) {
                return new VinePagedData(in);
                    }

                    
                    @Override 
            public VinePagedData[] newArray(int size) {
                return new VinePagedData[size];
                    }
                }
            }
