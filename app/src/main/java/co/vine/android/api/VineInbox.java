            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class VineInbox implements Parcelable {
                public static final Parcelable.Creator<VineInbox> CREATOR = new C03281();
                public ArrayList<VineConversation> conversations;
                public ArrayList<VineUser> participants;

/* 16 */        public VineInbox() {
                }

/* 20 */        public VineInbox(ArrayList<VineConversation> conversations, ArrayList<VineUser> participants) {
/* 22 */            this.conversations = conversations;
/* 23 */            this.participants = participants;
                }

/* 25 */        public VineInbox(Parcel in) {
/* 27 */            ClassLoader cl = getClass().getClassLoader();
/* 28 */            this.conversations = in.readArrayList(cl);
/* 29 */            this.participants = in.readArrayList(cl);
                }

                @Override // android.os.Parcelable
/* 33 */        public void writeToParcel(Parcel out, int flags) {
/* 34 */            out.writeSerializable(this.conversations);
/* 35 */            out.writeSerializable(this.participants);
                }

                @Override // android.os.Parcelable
/* 39 */        public int describeContents() {
/* 40 */            return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineInbox$1 */
/* 41 */        static class C03281 implements Parcelable.Creator<VineInbox> {
/* 42 */            C03281() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 45 */            public VineInbox createFromParcel(Parcel in) {
/* 46 */                return new VineInbox(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 50 */            public VineInbox[] newArray(int size) {
/* 51 */                return new VineInbox[size];
                    }
                }

/* 56 */        public boolean equals(Object o) {
/* 57 */            if (this == o) {
                        return true;
                    }
/* 58 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 60 */            VineInbox that = (VineInbox) o;
/* 62 */            if (this.conversations == null ? that.conversations != null : !this.conversations.equals(that.conversations)) {
                        return false;
                    }
/* 64 */            if (this.participants != null) {
                        if (this.participants.equals(that.participants)) {
                            return true;
                        }
                    } else if (that.participants == null) {
                        return true;
                    }
                    return false;
                }

/* 71 */        public int hashCode() {
/* 72 */            int result = this.conversations != null ? this.conversations.hashCode() : 0;
/* 73 */            return (result * 31) + (this.participants != null ? this.participants.hashCode() : 0);
                }
            }
