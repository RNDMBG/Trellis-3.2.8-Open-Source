            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class VinePagedData<T extends Parcelable> implements Parcelable {
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

/* 27 */        public VinePagedData() {
/* 16 */            this.previousPage = -1;
/* 21 */            this.unreadMessageCount = 0L;
/* 22 */            this.lastMessageRead = 0L;
/* 23 */            this.lastMessage = 0L;
                }

/* 32 */        public VinePagedData(Parcel parcel) {
/* 16 */            this.previousPage = -1;
/* 21 */            this.unreadMessageCount = 0L;
/* 22 */            this.lastMessageRead = 0L;
/* 23 */            this.lastMessage = 0L;
/* 34 */            this.anchor = parcel.readLong();
/* 35 */            this.count = parcel.readInt();
/* 36 */            this.nextPage = parcel.readInt();
/* 37 */            this.previousPage = parcel.readInt();
/* 38 */            this.size = parcel.readInt();
/* 39 */            this.items = new ArrayList<>();
                    for (Parcelable parcelable : parcel.readParcelableArray(VinePagedData.class.getClassLoader())) {
/* 42 */                this.items.add((T) parcelable);
                    }
/* 44 */            this.title = parcel.readString();
                }

                @Override // android.os.Parcelable
/* 48 */        public int describeContents() {
/* 49 */            return 0;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.os.Parcelable
/* 53 */        public void writeToParcel(Parcel parcel, int flags) {
/* 54 */            parcel.writeLong(this.anchor);
/* 55 */            parcel.writeInt(this.count);
/* 56 */            parcel.writeInt(this.nextPage);
/* 57 */            parcel.writeInt(this.previousPage);
/* 58 */            parcel.writeInt(this.size);
/* 59 */            if (this.items == null) {
/* 60 */                parcel.writeParcelableArray((Parcelable[]) new ArrayList().toArray(new Parcelable[0]), flags);
                    } else {
/* 62 */                parcel.writeParcelableArray((Parcelable[]) this.items.toArray(new Parcelable[this.items.size()]), flags);
                    }
/* 64 */            parcel.writeString(this.title);
                }

/* 67 */        public boolean isEmpty() {
                    return this.items != null && this.items.isEmpty();
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePagedData$1 */
/* 70 */        static class C03341 implements Parcelable.Creator<VinePagedData> {
/* 71 */            C03341() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 73 */            public VinePagedData createFromParcel(Parcel in) {
/* 74 */                return new VinePagedData(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 77 */            public VinePagedData[] newArray(int size) {
/* 78 */                return new VinePagedData[size];
                    }
                }
            }
