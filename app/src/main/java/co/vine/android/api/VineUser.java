            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            
            
    public class VineUser implements Parcelable {
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

        public VineUser() {
                }

        public VineUser(String username, String avatarUrl, String description, String location, long userId, long orderId, int blocked, int blocking, int explicit, boolean external, int followerCount, int followingCount, int following, int likeCount, int postCount, int verified, String email, String phoneNumber, int twitterConnected, int includePromoted, int privateAccount, int repostsEnabled, String edition, boolean acceptsOutOfNetworkConversations, int profileBackground, int authoredPostCount, String sectionTitle, boolean disableAddressBook, boolean hiddenEmail, boolean hiddenPhoneNumber, boolean hiddenTwitter, long loopCount, boolean notifyPosts) {
            if (TextUtils.isEmpty(username)) {
                if (!TextUtils.isEmpty(phoneNumber)) {
                    username = phoneNumber;
                        } else if (!TextUtils.isEmpty(email)) {
                    username = email;
                        }
                    }
            this.username = username;
            this.avatarUrl = avatarUrl;
            this.description = description;
           this.location = location;
           this.userId = userId;
           this.orderId = orderId;
           this.blocked = blocked;
           this.blocking = blocking;
           this.explicit = explicit;
           this.external = external;
           this.followerCount = followerCount;
           this.followingCount = followingCount;
           this.following = following;
           this.likeCount = likeCount;
           this.postCount = postCount;
           this.verified = verified;
           this.email = email;
           this.phoneNumber = phoneNumber;
           this.twitterConnected = twitterConnected;
           this.includePromoted = includePromoted;
           this.privateAccount = privateAccount;
           this.repostsEnabled = repostsEnabled;
           this.edition = edition;
           this.acceptsOutOfNetworkConversations = acceptsOutOfNetworkConversations;
           this.profileBackground = profileBackground;
           this.authoredPostCount = authoredPostCount;
           this.sectionTitle = sectionTitle;
           this.disableAddressBook = disableAddressBook;
           this.hiddenEmail = hiddenEmail;
           this.hiddenPhoneNumber = hiddenPhoneNumber;
           this.hiddenTwitter = hiddenTwitter;
           this.loopCount = loopCount;
           this.notifyPosts = notifyPosts;
                }

       public VineUser(Parcel in) {
           this.username = in.readString();
           this.avatarUrl = in.readString();
           this.description = in.readString();
           this.location = in.readString();
           this.email = in.readString();
           this.phoneNumber = in.readString();
           this.userId = in.readLong();
           this.orderId = in.readLong();
           this.blocked = in.readInt();
           this.blocking = in.readInt();
           this.explicit = in.readInt();
           this.external = in.readInt() == 1;
           this.followerCount = in.readInt();
           this.followingCount = in.readInt();
           this.following = in.readInt();
           this.likeCount = in.readInt();
           this.postCount = in.readInt();
           this.verified = in.readInt();
           this.twitterConnected = in.readInt();
           this.includePromoted = in.readInt();
           this.privateAccount = in.readInt();
           this.followStatus = in.readInt();
           this.repostsEnabled = in.readInt();
           this.edition = in.readString();
           this.acceptsOutOfNetworkConversations = in.readInt() == 1;
           this.profileBackground = in.readInt();
           this.authoredPostCount = in.readInt();
           this.verifiedInformation = in.readInt();
           this.disableAddressBook = in.readInt() == 1;
           this.hiddenEmail = in.readInt() == 1;
           this.hiddenPhoneNumber = in.readInt() == 1;
           this.hiddenTwitter = in.readInt() == 1;
           this.loopCount = in.readLong();
           this.notifyPosts = in.readInt() == 1;
           this.sectionId = in.readInt();
           this.sectionTitle = in.readString();
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeString(this.username);
           out.writeString(this.avatarUrl);
           out.writeString(this.description);
           out.writeString(this.location);
           out.writeString(this.email);
           out.writeString(this.phoneNumber);
           out.writeLong(this.userId);
           out.writeLong(this.orderId);
           out.writeInt(this.blocked);
           out.writeInt(this.blocking);
           out.writeInt(this.explicit);
           out.writeInt(this.external ? 1 : 0);
           out.writeInt(this.followerCount);
           out.writeInt(this.followingCount);
           out.writeInt(this.following);
           out.writeInt(this.likeCount);
           out.writeInt(this.postCount);
           out.writeInt(this.verified);
           out.writeInt(this.twitterConnected);
           out.writeInt(this.includePromoted);
           out.writeInt(this.privateAccount);
           out.writeInt(this.followStatus);
           out.writeInt(this.repostsEnabled);
           out.writeString(this.edition);
           out.writeInt(this.acceptsOutOfNetworkConversations ? 1 : 0);
           out.writeInt(this.profileBackground);
           out.writeInt(this.authoredPostCount);
           out.writeInt(this.verifiedInformation);
           out.writeInt(this.disableAddressBook ? 1 : 0);
           out.writeInt(this.hiddenEmail ? 1 : 0);
           out.writeInt(this.hiddenPhoneNumber ? 1 : 0);
           out.writeInt(this.hiddenTwitter ? 1 : 0);
           out.writeLong(this.loopCount);
           out.writeInt(this.notifyPosts ? 1 : 0);
           out.writeInt(this.sectionId);
           out.writeString(this.sectionTitle);
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VineUser vineUser = (VineUser) o;
           if (this.blocked == vineUser.blocked && this.blocking == vineUser.blocking && this.explicit == vineUser.explicit && this.external == vineUser.external && this.followerCount == vineUser.followerCount && this.following == vineUser.following && this.followingCount == vineUser.followingCount && this.likeCount == vineUser.likeCount && this.orderId == vineUser.orderId && this.postCount == vineUser.postCount && this.twitterConnected == vineUser.twitterConnected && this.includePromoted == vineUser.includePromoted && this.userId == vineUser.userId && this.verified == vineUser.verified && this.privateAccount == vineUser.privateAccount && this.followStatus == vineUser.followStatus) {
               if (this.avatarUrl == null ? vineUser.avatarUrl != null : !this.avatarUrl.equals(vineUser.avatarUrl)) {
                            return false;
                        }
               if (this.description == null ? vineUser.description != null : !this.description.equals(vineUser.description)) {
                            return false;
                        }
               if (this.email == null ? vineUser.email != null : !this.email.equals(vineUser.email)) {
                            return false;
                        }
               if (this.location == null ? vineUser.location != null : !this.location.equals(vineUser.location)) {
                            return false;
                        }
               if (this.phoneNumber == null ? vineUser.phoneNumber != null : !this.phoneNumber.equals(vineUser.phoneNumber)) {
                            return false;
                        }
               if (this.username == null ? vineUser.username != null : !this.username.equals(vineUser.username)) {
                            return false;
                        }
               if (this.repostsEnabled != vineUser.repostsEnabled) {
                            return false;
                        }
               if (this.edition == null ? vineUser.edition != null : !this.edition.equals(vineUser.edition)) {
                            return false;
                        }
               if (this.acceptsOutOfNetworkConversations == vineUser.acceptsOutOfNetworkConversations && this.profileBackground == vineUser.profileBackground && this.authoredPostCount == vineUser.authoredPostCount && this.verifiedInformation == vineUser.verifiedInformation && this.disableAddressBook == vineUser.disableAddressBook && this.hiddenEmail == vineUser.hiddenEmail && this.hiddenPhoneNumber == vineUser.hiddenPhoneNumber && this.hiddenTwitter == vineUser.hiddenTwitter && this.loopCount == vineUser.loopCount && this.notifyPosts == vineUser.notifyPosts && this.sectionId == vineUser.sectionId) {
                   if (this.sectionTitle != null) {
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

       public boolean isPrivate() {
                    return this.privateAccount == 1;
                }

       public boolean isFollowing() {
                    return this.following == 1;
                }

       public boolean isPrivateLocked() {
                    return isPrivate() && (!isFollowing() || hasFollowRequested());
                }

       public boolean isBlocked() {
                    return this.blocked == 1;
                }

       public boolean isBlocking() {
                    return this.blocking == 1;
                }

       public boolean areRepostsEnabled() {
                    return this.repostsEnabled == 1;
                }

       public boolean hasFollowApprovalPending() {
                    return (this.followStatus & 1) != 0;
                }

       public boolean hasFollowRequested() {
                    return (this.followStatus & 2) != 0;
                }

       public void setApprovalPending() {
           this.followStatus |= 1;
                }

       public void setFollowRequested() {
           this.followStatus |= 2;
                }

       public void setEmailVerified() {
           this.verifiedInformation |= 1;
                }

       public void setPhoneVerified() {
           this.verifiedInformation |= 2;
                }

       public void unsetEmailVerified() {
           this.verifiedInformation &= -2;
                }

       public void unsetPhoneVerified() {
           this.verifiedInformation &= -3;
                }

       public boolean isVerified() {
                    return this.verified == 1;
                }

       public boolean isPhoneVerified() {
                    return (this.verifiedInformation & 2) != 0;
                }

       public boolean isEmailVerified() {
                    return (this.verifiedInformation & 1) != 0;
                }

       public int getProfileBackgroundForShowing() {
           return this.profileBackground | (-16777216);
                }

       public boolean acceptsOutOfNetworkConversations() {
           return this.acceptsOutOfNetworkConversations;
                }

       public int hashCode() {
           int result = this.username != null ? this.username.hashCode() : 0;
           return (((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + (this.edition != null ? this.edition.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0)) * 31) + (this.email != null ? this.email.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.orderId ^ (this.orderId >>> 32)))) * 31) + ((int) (this.loopCount ^ (this.loopCount >>> 32)))) * 31) + this.blocked) * 31) + this.blocking) * 31) + this.explicit) * 31) + (this.external ? 1 : 0)) * 31) + this.followerCount) * 31) + this.followingCount) * 31) + this.following) * 31) + this.likeCount) * 31) + this.postCount) * 31) + this.verified) * 31) + this.twitterConnected) * 31) + this.includePromoted) * 31) + this.privateAccount) * 31) + this.followStatus) * 31) + this.repostsEnabled) * 31) + (this.acceptsOutOfNetworkConversations ? 1 : 0)) * 31) + this.profileBackground) * 31) + this.verifiedInformation) * 31) + (this.notifyPosts ? 1 : 0);
                }

                
       static class C03631 implements Parcelable.Creator<VineUser> {
           C03631() {
                    }



                    
                    @Override 
           public VineUser createFromParcel(Parcel in) {
               return new VineUser(in);
                    }

                    
                    @Override 
           public VineUser[] newArray(int size) {
               return new VineUser[size];
                    }
                }
            }
