            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 75 */    public class VineUser implements Parcelable {
                public static final Parcelable.Creator<VineUser> CREATOR = new C03631();
                public static final int FOLLOW_STATUS_APPROVAL_PENDING = 1;
                public static final int FOLLOW_STATUS_REQUESTED = 2;
                public static final int VERIFIED_EMAIL = 1;
                public static final int VERIFIED_PHONE = 2;
                public boolean acceptsOutOfNetworkConversations;
                public int authoredPostCount;
                public String avatarUrl;
                public int blocked;
                public int blocking;
                public String description;
                public boolean disableAddressBook;
                public String edition;
                public String email;
                public int explicit;
                public boolean external;
                public int followStatus;
                public int followerCount;
                public int following;
                public int followingCount;
                public long friendIndex;
                public boolean hiddenEmail;
                public boolean hiddenPhoneNumber;
                public boolean hiddenTwitter;

                /* JADX INFO: renamed from: id */
                public long f99id;
                public int includePromoted;
                public int likeCount;
                public String location;
                public long loopCount;
                public boolean notifyPosts;
                public long orderId;
                public String phoneNumber;
                public int postCount;
                public int privateAccount;
                public int profileBackground;
                public int repostsEnabled;
                public int sectionId;
                public String sectionTitle;
                public int twitterConnected;
                public long userId;
                public String username;
                public int verified;
                public int verifiedInformation;

/* 76 */        public VineUser() {
                }

/* 88 */        public VineUser(String username, String avatarUrl, String description, String location, long userId, long orderId, int blocked, int blocking, int explicit, boolean external, int followerCount, int followingCount, int following, int likeCount, int postCount, int verified, String email, String phoneNumber, int twitterConnected, int includePromoted, int privateAccount, int repostsEnabled, String edition, boolean acceptsOutOfNetworkConversations, int profileBackground, int authoredPostCount, String sectionTitle, boolean disableAddressBook, boolean hiddenEmail, boolean hiddenPhoneNumber, boolean hiddenTwitter, long loopCount, boolean notifyPosts) {
/* 90 */            if (TextUtils.isEmpty(username)) {
/* 91 */                if (!TextUtils.isEmpty(phoneNumber)) {
/* 92 */                    username = phoneNumber;
                        } else if (!TextUtils.isEmpty(email)) {
/* 94 */                    username = email;
                        }
                    }
/* 97 */            this.username = username;
/* 98 */            this.avatarUrl = avatarUrl;
/* 99 */            this.description = description;
/* 100 */           this.location = location;
/* 101 */           this.userId = userId;
/* 102 */           this.orderId = orderId;
/* 103 */           this.blocked = blocked;
/* 104 */           this.blocking = blocking;
/* 105 */           this.explicit = explicit;
/* 106 */           this.external = external;
/* 107 */           this.followerCount = followerCount;
/* 108 */           this.followingCount = followingCount;
/* 109 */           this.following = following;
/* 110 */           this.likeCount = likeCount;
/* 111 */           this.postCount = postCount;
/* 112 */           this.verified = verified;
/* 113 */           this.email = email;
/* 114 */           this.phoneNumber = phoneNumber;
/* 115 */           this.twitterConnected = twitterConnected;
/* 116 */           this.includePromoted = includePromoted;
/* 117 */           this.privateAccount = privateAccount;
/* 118 */           this.repostsEnabled = repostsEnabled;
/* 119 */           this.edition = edition;
/* 120 */           this.acceptsOutOfNetworkConversations = acceptsOutOfNetworkConversations;
/* 121 */           this.profileBackground = profileBackground;
/* 122 */           this.authoredPostCount = authoredPostCount;
/* 123 */           this.sectionTitle = sectionTitle;
/* 124 */           this.disableAddressBook = disableAddressBook;
/* 125 */           this.hiddenEmail = hiddenEmail;
/* 126 */           this.hiddenPhoneNumber = hiddenPhoneNumber;
/* 127 */           this.hiddenTwitter = hiddenTwitter;
/* 128 */           this.loopCount = loopCount;
/* 129 */           this.notifyPosts = notifyPosts;
                }

/* 131 */       public VineUser(Parcel in) {
/* 133 */           this.username = in.readString();
/* 134 */           this.avatarUrl = in.readString();
/* 135 */           this.description = in.readString();
/* 136 */           this.location = in.readString();
/* 137 */           this.email = in.readString();
/* 138 */           this.phoneNumber = in.readString();
/* 139 */           this.userId = in.readLong();
/* 140 */           this.orderId = in.readLong();
/* 141 */           this.blocked = in.readInt();
/* 142 */           this.blocking = in.readInt();
/* 143 */           this.explicit = in.readInt();
/* 144 */           this.external = in.readInt() == 1;
/* 145 */           this.followerCount = in.readInt();
/* 146 */           this.followingCount = in.readInt();
/* 147 */           this.following = in.readInt();
/* 148 */           this.likeCount = in.readInt();
/* 149 */           this.postCount = in.readInt();
/* 150 */           this.verified = in.readInt();
/* 151 */           this.twitterConnected = in.readInt();
/* 152 */           this.includePromoted = in.readInt();
/* 153 */           this.privateAccount = in.readInt();
/* 154 */           this.followStatus = in.readInt();
/* 155 */           this.repostsEnabled = in.readInt();
/* 156 */           this.edition = in.readString();
/* 157 */           this.acceptsOutOfNetworkConversations = in.readInt() == 1;
/* 158 */           this.profileBackground = in.readInt();
/* 159 */           this.authoredPostCount = in.readInt();
/* 160 */           this.verifiedInformation = in.readInt();
/* 161 */           this.disableAddressBook = in.readInt() == 1;
/* 162 */           this.hiddenEmail = in.readInt() == 1;
/* 163 */           this.hiddenPhoneNumber = in.readInt() == 1;
/* 164 */           this.hiddenTwitter = in.readInt() == 1;
/* 165 */           this.loopCount = in.readLong();
/* 166 */           this.notifyPosts = in.readInt() == 1;
/* 167 */           this.sectionId = in.readInt();
/* 168 */           this.sectionTitle = in.readString();
                }

                @Override // android.os.Parcelable
/* 172 */       public int describeContents() {
/* 173 */           return 0;
                }

                @Override // android.os.Parcelable
/* 177 */       public void writeToParcel(Parcel out, int flags) {
/* 178 */           out.writeString(this.username);
/* 179 */           out.writeString(this.avatarUrl);
/* 180 */           out.writeString(this.description);
/* 181 */           out.writeString(this.location);
/* 182 */           out.writeString(this.email);
/* 183 */           out.writeString(this.phoneNumber);
/* 184 */           out.writeLong(this.userId);
/* 185 */           out.writeLong(this.orderId);
/* 186 */           out.writeInt(this.blocked);
/* 187 */           out.writeInt(this.blocking);
/* 188 */           out.writeInt(this.explicit);
/* 189 */           out.writeInt(this.external ? 1 : 0);
/* 190 */           out.writeInt(this.followerCount);
/* 191 */           out.writeInt(this.followingCount);
/* 192 */           out.writeInt(this.following);
/* 193 */           out.writeInt(this.likeCount);
/* 194 */           out.writeInt(this.postCount);
/* 195 */           out.writeInt(this.verified);
/* 196 */           out.writeInt(this.twitterConnected);
/* 197 */           out.writeInt(this.includePromoted);
/* 198 */           out.writeInt(this.privateAccount);
/* 199 */           out.writeInt(this.followStatus);
/* 200 */           out.writeInt(this.repostsEnabled);
/* 201 */           out.writeString(this.edition);
/* 202 */           out.writeInt(this.acceptsOutOfNetworkConversations ? 1 : 0);
/* 203 */           out.writeInt(this.profileBackground);
/* 204 */           out.writeInt(this.authoredPostCount);
/* 205 */           out.writeInt(this.verifiedInformation);
/* 206 */           out.writeInt(this.disableAddressBook ? 1 : 0);
/* 207 */           out.writeInt(this.hiddenEmail ? 1 : 0);
/* 208 */           out.writeInt(this.hiddenPhoneNumber ? 1 : 0);
/* 209 */           out.writeInt(this.hiddenTwitter ? 1 : 0);
/* 210 */           out.writeLong(this.loopCount);
/* 211 */           out.writeInt(this.notifyPosts ? 1 : 0);
/* 212 */           out.writeInt(this.sectionId);
/* 213 */           out.writeString(this.sectionTitle);
                }

/* 217 */       public boolean equals(Object o) {
/* 218 */           if (this == o) {
                        return true;
                    }
/* 219 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 221 */           VineUser vineUser = (VineUser) o;
/* 223 */           if (this.blocked == vineUser.blocked && this.blocking == vineUser.blocking && this.explicit == vineUser.explicit && this.external == vineUser.external && this.followerCount == vineUser.followerCount && this.following == vineUser.following && this.followingCount == vineUser.followingCount && this.likeCount == vineUser.likeCount && this.orderId == vineUser.orderId && this.postCount == vineUser.postCount && this.twitterConnected == vineUser.twitterConnected && this.includePromoted == vineUser.includePromoted && this.userId == vineUser.userId && this.verified == vineUser.verified && this.privateAccount == vineUser.privateAccount && this.followStatus == vineUser.followStatus) {
/* 239 */               if (this.avatarUrl == null ? vineUser.avatarUrl != null : !this.avatarUrl.equals(vineUser.avatarUrl)) {
                            return false;
                        }
/* 241 */               if (this.description == null ? vineUser.description != null : !this.description.equals(vineUser.description)) {
                            return false;
                        }
/* 244 */               if (this.email == null ? vineUser.email != null : !this.email.equals(vineUser.email)) {
                            return false;
                        }
/* 245 */               if (this.location == null ? vineUser.location != null : !this.location.equals(vineUser.location)) {
                            return false;
                        }
/* 247 */               if (this.phoneNumber == null ? vineUser.phoneNumber != null : !this.phoneNumber.equals(vineUser.phoneNumber)) {
                            return false;
                        }
/* 250 */               if (this.username == null ? vineUser.username != null : !this.username.equals(vineUser.username)) {
                            return false;
                        }
/* 252 */               if (this.repostsEnabled != vineUser.repostsEnabled) {
                            return false;
                        }
/* 253 */               if (this.edition == null ? vineUser.edition != null : !this.edition.equals(vineUser.edition)) {
                            return false;
                        }
/* 255 */               if (this.acceptsOutOfNetworkConversations == vineUser.acceptsOutOfNetworkConversations && this.profileBackground == vineUser.profileBackground && this.authoredPostCount == vineUser.authoredPostCount && this.verifiedInformation == vineUser.verifiedInformation && this.disableAddressBook == vineUser.disableAddressBook && this.hiddenEmail == vineUser.hiddenEmail && this.hiddenPhoneNumber == vineUser.hiddenPhoneNumber && this.hiddenTwitter == vineUser.hiddenTwitter && this.loopCount == vineUser.loopCount && this.notifyPosts == vineUser.notifyPosts && this.sectionId == vineUser.sectionId) {
/* 270 */                   if (this.sectionTitle != null) {
                                if (this.sectionTitle.equals(vineUser.sectionTitle)) {
                                    return true;
                                }
                            } else if (vineUser.sectionTitle == null) {
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }

/* 277 */       public boolean isPrivate() {
                    return this.privateAccount == 1;
                }

/* 281 */       public boolean isFollowing() {
                    return this.following == 1;
                }

/* 285 */       public boolean isPrivateLocked() {
                    return isPrivate() && (!isFollowing() || hasFollowRequested());
                }

/* 292 */       public boolean isBlocked() {
                    return this.blocked == 1;
                }

/* 299 */       public boolean isBlocking() {
                    return this.blocking == 1;
                }

/* 303 */       public boolean areRepostsEnabled() {
                    return this.repostsEnabled == 1;
                }

/* 310 */       public boolean hasFollowApprovalPending() {
                    return (this.followStatus & 1) != 0;
                }

/* 317 */       public boolean hasFollowRequested() {
                    return (this.followStatus & 2) != 0;
                }

/* 321 */       public void setApprovalPending() {
/* 322 */           this.followStatus |= 1;
                }

/* 325 */       public void setFollowRequested() {
/* 326 */           this.followStatus |= 2;
                }

/* 329 */       public void setEmailVerified() {
/* 330 */           this.verifiedInformation |= 1;
                }

/* 333 */       public void setPhoneVerified() {
/* 334 */           this.verifiedInformation |= 2;
                }

/* 337 */       public void unsetEmailVerified() {
/* 338 */           this.verifiedInformation &= -2;
                }

/* 341 */       public void unsetPhoneVerified() {
/* 342 */           this.verifiedInformation &= -3;
                }

/* 345 */       public boolean isVerified() {
                    return this.verified == 1;
                }

/* 349 */       public boolean isPhoneVerified() {
                    return (this.verifiedInformation & 2) != 0;
                }

/* 353 */       public boolean isEmailVerified() {
                    return (this.verifiedInformation & 1) != 0;
                }

/* 357 */       public int getProfileBackgroundForShowing() {
/* 358 */           return this.profileBackground | (-16777216);
                }

/* 361 */       public boolean acceptsOutOfNetworkConversations() {
/* 362 */           return this.acceptsOutOfNetworkConversations;
                }

/* 366 */       public int hashCode() {
/* 367 */           int result = this.username != null ? this.username.hashCode() : 0;
/* 395 */           return (((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + (this.edition != null ? this.edition.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0)) * 31) + (this.email != null ? this.email.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.orderId ^ (this.orderId >>> 32)))) * 31) + ((int) (this.loopCount ^ (this.loopCount >>> 32)))) * 31) + this.blocked) * 31) + this.blocking) * 31) + this.explicit) * 31) + (this.external ? 1 : 0)) * 31) + this.followerCount) * 31) + this.followingCount) * 31) + this.following) * 31) + this.likeCount) * 31) + this.postCount) * 31) + this.verified) * 31) + this.twitterConnected) * 31) + this.includePromoted) * 31) + this.privateAccount) * 31) + this.followStatus) * 31) + this.repostsEnabled) * 31) + (this.acceptsOutOfNetworkConversations ? 1 : 0)) * 31) + this.profileBackground) * 31) + this.verifiedInformation) * 31) + (this.notifyPosts ? 1 : 0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineUser$1 */
/* 397 */       static class C03631 implements Parcelable.Creator<VineUser> {
/* 398 */           C03631() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 400 */           public VineUser createFromParcel(Parcel in) {
/* 401 */               return new VineUser(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 404 */           public VineUser[] newArray(int size) {
/* 405 */               return new VineUser[size];
                    }
                }
            }
