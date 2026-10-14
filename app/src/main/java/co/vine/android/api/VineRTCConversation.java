            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class VineRTCConversation implements Parcelable {
                public static final Parcelable.Creator<VineRTCConversation> CREATOR = new C03541();
                public long conversationId;
                public ArrayList<VineRTCParticipant> participants;

/* 14 */        public VineRTCConversation(long conversationId, ArrayList<VineRTCParticipant> participants) {
/* 13 */            this.participants = new ArrayList<>();
/* 16 */            this.conversationId = conversationId;
/* 17 */            this.participants = participants;
                }

/* 20 */        public VineRTCConversation(Parcel in) {
/* 13 */            this.participants = new ArrayList<>();
/* 22 */            this.conversationId = in.readLong();
/* 23 */            in.readTypedList(this.participants, VineRTCParticipant.CREATOR);
                }

                @Override // android.os.Parcelable
/* 27 */        public int describeContents() {
/* 28 */            return 0;
                }

                @Override // android.os.Parcelable
/* 32 */        public void writeToParcel(Parcel dest, int flags) {
/* 33 */            dest.writeLong(this.conversationId);
/* 34 */            dest.writeTypedList(this.participants);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineRTCConversation$1 */
/* 35 */        static class C03541 implements Parcelable.Creator<VineRTCConversation> {
/* 36 */            C03541() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 38 */            public VineRTCConversation createFromParcel(Parcel in) {
/* 39 */                return new VineRTCConversation(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 42 */            public VineRTCConversation[] newArray(int size) {
/* 43 */                return new VineRTCConversation[size];
                    }
                }
            }
