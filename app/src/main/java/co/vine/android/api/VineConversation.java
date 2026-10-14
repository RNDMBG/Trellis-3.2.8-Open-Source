            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class VineConversation implements Externalizable, Parcelable {
                public static final Parcelable.Creator<VineConversation> CREATOR = new C03241();
                public long conversationId;
                public long conversationObjectId;
                public long createdBy;
                public long lastMessage;
                public ArrayList<VinePrivateMessage> messages;
                public int networkType;
                public long unreadMessageCount;
                public ArrayList<Long> users;

/* 26 */        public VineConversation() {
                }

/* 31 */        public VineConversation(long conversationId, long lastMessage, long createdBy, ArrayList<VinePrivateMessage> messages, ArrayList<Long> users, int networkType, long unreadMessageCount) {
/* 33 */            this.conversationId = conversationId;
/* 34 */            this.lastMessage = lastMessage;
/* 35 */            this.createdBy = createdBy;
/* 36 */            this.unreadMessageCount = unreadMessageCount;
/* 37 */            this.messages = messages;
/* 38 */            this.users = users;
/* 39 */            this.networkType = networkType;
                }

/* 42 */        public VineConversation(long conversationId, long lastMessage, ArrayList<VinePrivateMessage> messages, long unreadMessageCount) {
/* 44 */            this.conversationId = conversationId;
/* 45 */            this.lastMessage = lastMessage;
/* 46 */            this.messages = messages;
/* 47 */            this.unreadMessageCount = unreadMessageCount;
                }

/* 50 */        public VinePrivateMessage lastMessage() {
/* 51 */            if (this.messages.isEmpty()) {
/* 52 */                return null;
                    }
                    for (VinePrivateMessage message : this.messages) {
                        if (message.messageId == this.lastMessage) {
/* 59 */                    return message;
                        }
                    }
/* 59 */            return this.messages.get(0);
                }

                @Override // android.os.Parcelable
/* 63 */        public int describeContents() {
/* 64 */            return 0;
                }

/* 67 */        public VineConversation(Parcel in) {
/* 69 */            this.conversationId = in.readLong();
/* 70 */            this.lastMessage = in.readLong();
/* 71 */            this.createdBy = in.readLong();
/* 72 */            this.unreadMessageCount = in.readLong();
/* 73 */            this.messages = (ArrayList) in.readSerializable();
/* 74 */            this.users = (ArrayList) in.readSerializable();
/* 75 */            this.networkType = in.readInt();
/* 76 */            this.conversationObjectId = in.readLong();
                }

                @Override // android.os.Parcelable
/* 80 */        public void writeToParcel(Parcel out, int flags) {
/* 81 */            out.writeLong(this.conversationId);
/* 82 */            out.writeLong(this.lastMessage);
/* 83 */            out.writeLong(this.createdBy);
/* 84 */            out.writeLong(this.unreadMessageCount);
/* 85 */            out.writeSerializable(this.messages);
/* 86 */            out.writeSerializable(this.users);
/* 87 */            out.writeInt(this.networkType);
/* 88 */            out.writeLong(this.conversationObjectId);
                }

                @Override // java.io.Externalizable
/* 93 */        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 94 */            this.conversationId = objectInput.readLong();
/* 95 */            this.lastMessage = objectInput.readLong();
/* 96 */            this.createdBy = objectInput.readLong();
/* 97 */            this.unreadMessageCount = objectInput.readLong();
/* 98 */            this.messages = (ArrayList) objectInput.readObject();
/* 99 */            this.users = (ArrayList) objectInput.readObject();
/* 100 */           this.networkType = objectInput.readInt();
/* 101 */           this.conversationObjectId = objectInput.readLong();
                }

                @Override // java.io.Externalizable
/* 105 */       public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 106 */           objectOutput.writeLong(this.conversationId);
/* 107 */           objectOutput.writeLong(this.lastMessage);
/* 108 */           objectOutput.writeLong(this.createdBy);
/* 109 */           objectOutput.writeLong(this.unreadMessageCount);
/* 110 */           objectOutput.writeObject(this.messages);
/* 111 */           objectOutput.writeObject(this.users);
/* 112 */           objectOutput.writeInt(this.networkType);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineConversation$1 */
/* 113 */       static class C03241 implements Parcelable.Creator<VineConversation> {
/* 114 */           C03241() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 117 */           public VineConversation createFromParcel(Parcel in) {
/* 118 */               return new VineConversation(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 122 */           public VineConversation[] newArray(int size) {
/* 123 */               return new VineConversation[size];
                    }
                }
            }
