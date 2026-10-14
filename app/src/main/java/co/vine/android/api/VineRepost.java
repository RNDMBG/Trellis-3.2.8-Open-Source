            package co.vine.android.api;

            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class VineRepost implements Externalizable {
                private static final long serialVersionUID = -6283845278265871065L;
                public String avatarUrl;
                public String description;
                public String location;
                public long postId;
                public int priv;
                public long repostId;
                public int unflaggable;
                public long userId;
                public String username;
                public int verified;

/* 25 */        public VineRepost() {
                }

/* 31 */        public VineRepost(String username, String avatarUrl, String location, String description, long userId, long postId, long repostId, int verified, int priv, int unflaggable) {
/* 33 */            this.username = username;
/* 34 */            this.avatarUrl = avatarUrl;
/* 35 */            this.location = location;
/* 36 */            this.description = description;
/* 37 */            this.userId = userId;
/* 38 */            this.postId = postId;
/* 39 */            this.repostId = repostId;
/* 40 */            this.verified = verified;
/* 41 */            this.priv = priv;
/* 42 */            this.unflaggable = unflaggable;
                }

                @Override // java.io.Externalizable
/* 46 */        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 47 */            this.username = (String) objectInput.readObject();
/* 48 */            this.avatarUrl = (String) objectInput.readObject();
/* 49 */            this.location = (String) objectInput.readObject();
/* 50 */            this.description = (String) objectInput.readObject();
/* 51 */            this.userId = objectInput.readLong();
/* 52 */            this.postId = objectInput.readLong();
/* 53 */            this.repostId = objectInput.readLong();
/* 54 */            this.verified = objectInput.readInt();
/* 55 */            this.priv = objectInput.readInt();
/* 56 */            this.unflaggable = objectInput.readInt();
                }

                @Override // java.io.Externalizable
/* 60 */        public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 61 */            objectOutput.writeObject(this.username);
/* 62 */            objectOutput.writeObject(this.avatarUrl);
/* 63 */            objectOutput.writeObject(this.location);
/* 64 */            objectOutput.writeObject(this.description);
/* 65 */            objectOutput.writeLong(this.userId);
/* 66 */            objectOutput.writeLong(this.postId);
/* 67 */            objectOutput.writeLong(this.repostId);
/* 68 */            objectOutput.writeInt(this.verified);
/* 69 */            objectOutput.writeInt(this.priv);
/* 70 */            objectOutput.writeInt(this.unflaggable);
                }

/* 74 */        public boolean equals(Object o) {
/* 75 */            if (this == o) {
                        return true;
                    }
/* 76 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 78 */            VineRepost that = (VineRepost) o;
/* 81 */            if (this.postId == that.postId && this.priv == that.priv && this.repostId == that.repostId && this.unflaggable == that.unflaggable && this.userId == that.userId && this.verified == that.verified) {
/* 86 */                if (this.avatarUrl == null ? that.avatarUrl != null : !this.avatarUrl.equals(that.avatarUrl)) {
                            return false;
                        }
/* 88 */                if (this.description == null ? that.description != null : !this.description.equals(that.description)) {
                            return false;
                        }
/* 90 */                if (this.location == null ? that.location != null : !this.location.equals(that.location)) {
                            return false;
                        }
/* 92 */                if (this.username != null) {
                            if (this.username.equals(that.username)) {
                                return true;
                            }
                        } else if (that.username == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 99 */        public int hashCode() {
/* 100 */           int result = this.username != null ? this.username.hashCode() : 0;
/* 109 */           return (((((((((((((((((result * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + ((int) (this.repostId ^ (this.repostId >>> 32)))) * 31) + this.verified) * 31) + this.priv) * 31) + this.unflaggable;
                }
            }
