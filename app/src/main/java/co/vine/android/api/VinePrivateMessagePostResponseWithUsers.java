            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class VinePrivateMessagePostResponseWithUsers implements Parcelable {
                public static final Parcelable.Creator<VinePrivateMessagePostResponseWithUsers> CREATOR = new C03521();
                public final VinePost post;
                public final ArrayList<VineRecipient> recipients;
                public final ArrayList<VinePrivateMessageResponse> responses;
                public final ArrayList<VineUser> users;

/* 17 */        public VinePrivateMessagePostResponseWithUsers(ArrayList<VinePrivateMessageResponse> responses, ArrayList<VineRecipient> recipients, ArrayList<VineUser> users, VinePost post) {
/* 19 */            this.responses = responses;
/* 20 */            this.recipients = recipients;
/* 21 */            this.users = users;
/* 22 */            this.post = post;
                }

/* 25 */        public VinePrivateMessagePostResponseWithUsers(Parcel in) {
/* 27 */            this.responses = (ArrayList) in.readSerializable();
/* 28 */            this.recipients = (ArrayList) in.readSerializable();
/* 29 */            this.users = (ArrayList) in.readSerializable();
/* 30 */            this.post = (VinePost) in.readParcelable(VinePost.class.getClassLoader());
                }

                @Override // android.os.Parcelable
/* 34 */        public int describeContents() {
/* 35 */            return 0;
                }

                @Override // android.os.Parcelable
/* 39 */        public void writeToParcel(Parcel out, int flags) {
/* 40 */            out.writeSerializable(this.responses);
/* 41 */            out.writeSerializable(this.recipients);
/* 42 */            out.writeSerializable(this.users);
/* 43 */            out.writeParcelable(this.post, flags);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePrivateMessagePostResponseWithUsers$1 */
/* 44 */        static class C03521 implements Parcelable.Creator<VinePrivateMessagePostResponseWithUsers> {
/* 45 */            C03521() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 47 */            public VinePrivateMessagePostResponseWithUsers createFromParcel(Parcel in) {
/* 48 */                return new VinePrivateMessagePostResponseWithUsers(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 51 */            public VinePrivateMessagePostResponseWithUsers[] newArray(int size) {
/* 52 */                return new VinePrivateMessagePostResponseWithUsers[size];
                    }
                }
            }
