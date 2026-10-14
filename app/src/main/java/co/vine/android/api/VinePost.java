            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.annotation.Nullable;
            import android.text.SpannableStringBuilder;
            import co.vine.android.util.Util;
            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.ObjectOutput;
            import java.io.ObjectOutputStream;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.Comparator;
            import java.util.List;
            
            
    public class VinePost implements Parcelable {
                public static final Parcelable.Creator<VinePost> CREATOR = new C03492();
                public static final int FLAG_EXPLICIT = 4;
                public static final int FLAG_LIKED = 8;
                public static final int FLAG_POST_VERIFIED = 32;
                public static final int FLAG_PRIVATE = 64;
                public static final int FLAG_PROMOTED = 2;
                public static final int FLAG_REVINED = 16;
                public static final int FLAG_VERIFIED = 1;
                public String avatarUrl;
                public VineByline byline;
                public VinePagedData<VineComment> comments;
                public int commentsCount;
                public long created;
                public String dateString;
                public String description;
                public SpannableStringBuilder descriptionSb;
                public ArrayList<VineEntity> entities;
                public boolean following;
                public String foursquareVenueId;
                public boolean isLast;

                @Nullable
                public Boolean isRTL;
                public long lastRefresh;
                public VinePagedData<VineLike> likes;
                public int likesCount;
                public String location;
                public long loops;
                public int metadataFlags;
                public long myRepostId;
                public boolean onFire;
                public String orderId;
                public boolean originalFollowingState;
                public int postFlags;
                public long postId;
                public VineRepost repost;
                public int revinersCount;
                public String shareUrl;
                public SpannableStringBuilder sharedVmSb;
                public SpannableStringBuilder styledCompactLikesComments;
                public SpannableStringBuilder styledUserName;
                public String tag;
                public ArrayList<VineTag> tags;
                public String thumbnailUrl;
                private List<VineVideoUrlTier> tieredUrls;
                public ArrayList<VineEntity> transientEntities;
                public VineUser user;
                public int userBackgroundColor;
                public long userId;
                public String username;
                public double velocity;
                public VineVenue venueData;
                public String videoLowUrl;
                public String videoUrl;

        public VinePost() {
                }

        public void setUpUrls(List<VineVideoUrlTier> urls) {
            this.tieredUrls = urls;
            if (this.tieredUrls != null && this.tieredUrls.size() > 0) {
                Collections.sort(this.tieredUrls, new C03481());
                        for (VineVideoUrlTier tier : this.tieredUrls) {
                   if (VineVideoUrlTier.FORMAT_H264C.equals(tier.format)) {
                       this.videoLowUrl = tier.url;
                            }
                        }
               if (this.videoLowUrl == null) {
                   this.videoLowUrl = this.tieredUrls.get(0).url;
                        }
               this.videoUrl = this.tieredUrls.get(this.tieredUrls.size() - 1).url;
                    }
                }

                
        class C03481 implements Comparator<VineVideoUrlTier> {
            C03481() {
                    }


                    
            public int compare(VineVideoUrlTier lhs, VineVideoUrlTier rhs) {
                return (int) (lhs.rate - rhs.rate);
                    }
                }

       public List<VineVideoUrlTier> getUrls() {
           return this.tieredUrls;
                }

       public VinePost(Parcel in) {
           ClassLoader cl = getClass().getClassLoader();
           this.created = in.readLong();
           this.metadataFlags = in.readInt();
           this.isLast = in.readInt() == 1;
           this.postFlags = in.readInt();
           this.likesCount = in.readInt();
           this.revinersCount = in.readInt();
           this.commentsCount = in.readInt();
           this.userId = in.readLong();
           this.postId = in.readLong();
           this.myRepostId = in.readLong();
           this.orderId = in.readString();
           this.tag = in.readString();
           this.foursquareVenueId = in.readString();
           this.description = in.readString();
           this.avatarUrl = in.readString();
           this.location = in.readString();
           this.videoLowUrl = in.readString();
           this.videoUrl = in.readString();
           this.username = in.readString();
           this.shareUrl = in.readString();
           this.thumbnailUrl = in.readString();
           this.comments = (VinePagedData) in.readParcelable(cl);
           this.likes = (VinePagedData) in.readParcelable(cl);
           this.user = (VineUser) in.readParcelable(cl);
           this.venueData = (VineVenue) in.readSerializable();
           this.entities = (ArrayList) in.readSerializable();
           this.repost = (VineRepost) in.readSerializable();
           this.userBackgroundColor = in.readInt();
           this.loops = in.readLong();
           this.velocity = in.readLong();
           this.lastRefresh = in.readLong();
           this.onFire = in.readInt() == 1;
           this.byline = (VineByline) in.readParcelable(cl);
           this.following = in.readInt() == 1;
           this.tieredUrls = new ArrayList();
           in.readTypedList(this.tieredUrls, VineVideoUrlTier.CREATOR);
                }

       public static VinePost from(Cursor c) {
           VinePost p = new VinePost();
           p.postId = c.getLong(1);
           p.orderId = c.getString(49);
           p.tag = c.getString(13);
           p.myRepostId = c.getLong(2);
           p.shareUrl = c.getString(5);
           p.videoUrl = c.getString(7);
           p.videoLowUrl = c.getString(6);
           p.description = c.getString(8);
           p.foursquareVenueId = c.getString(9);
           p.metadataFlags = c.getInt(10);
           p.isLast = c.getInt(14) == 1;
           p.postFlags = c.getInt(11);
           p.likesCount = c.getInt(24);
           p.revinersCount = c.getInt(25);
           p.commentsCount = c.getInt(26);
           p.userId = c.getLong(16);
           p.created = c.getLong(17);
           p.avatarUrl = c.getString(15);
           p.location = c.getString(18);
           p.username = c.getString(19);
           p.thumbnailUrl = c.getString(4);
           p.userBackgroundColor = c.getInt(44);
           p.comments = new VinePagedData<>();
           p.likes = new VinePagedData<>();
           p.venueData = (VineVenue) Util.fromByteArray(c.getBlob(20));
           p.entities = (ArrayList) Util.fromByteArray(c.getBlob(21));
           p.repost = (VineRepost) Util.fromByteArray(c.getBlob(22));
           p.loops = c.getLong(45);
           p.velocity = c.getLong(46);
           p.lastRefresh = c.getLong(48);
           p.onFire = c.getInt(47) == 1;
           return p;
                }

       public static byte[] getBytesFromTags(VinePost post) throws IOException {
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           ObjectOutput out = new ObjectOutputStream(bos);
           if (post.tags != null) {
                        for (VineTag tag : post.tags) {
                   out.writeLong(tag.tagId);
                   out.writeObject(tag.tagName);
                        }
                    }
           out.flush();
           out.close();
           bos.close();
           return bos.toByteArray();
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeLong(this.created);
           out.writeInt(this.metadataFlags);
           out.writeInt(this.isLast ? 1 : 0);
           out.writeInt(this.postFlags);
           out.writeInt(this.likesCount);
           out.writeInt(this.revinersCount);
           out.writeInt(this.commentsCount);
           out.writeLong(this.userId);
           out.writeLong(this.postId);
           out.writeLong(this.myRepostId);
           out.writeString(this.orderId);
           out.writeString(this.tag);
           out.writeString(this.foursquareVenueId);
           out.writeString(this.description);
           out.writeString(this.avatarUrl);
           out.writeString(this.location);
           out.writeString(this.videoLowUrl);
           out.writeString(this.videoUrl);
           out.writeString(this.username);
           out.writeString(this.shareUrl);
           out.writeString(this.thumbnailUrl);
           out.writeParcelable(this.comments, flags);
           out.writeParcelable(this.likes, flags);
           out.writeParcelable(this.user, flags);
           out.writeSerializable(this.venueData);
           out.writeSerializable(this.entities);
           out.writeSerializable(this.repost);
           out.writeInt(this.userBackgroundColor);
           out.writeLong(this.loops);
           out.writeDouble(this.velocity);
           out.writeLong(this.lastRefresh);
           out.writeInt(this.onFire ? 1 : 0);
           out.writeParcelable(this.byline, flags);
           out.writeInt(this.following ? 1 : 0);
           out.writeTypedList(this.tieredUrls);
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VinePost vinePost = (VinePost) o;
           if (this.postId == vinePost.postId && this.myRepostId == vinePost.myRepostId && this.commentsCount == vinePost.commentsCount && this.created == vinePost.created && this.metadataFlags == vinePost.metadataFlags && this.isLast == vinePost.isLast && this.likesCount == vinePost.likesCount && this.revinersCount == vinePost.revinersCount && this.postFlags == vinePost.postFlags && this.userId == vinePost.userId) {
               if (this.avatarUrl == null ? vinePost.avatarUrl != null : !this.avatarUrl.equals(vinePost.avatarUrl)) {
                            return false;
                        }
               if (this.comments == null ? vinePost.comments != null : !this.comments.equals(vinePost.comments)) {
                            return false;
                        }
               if (this.description == null ? vinePost.description != null : !this.description.equals(vinePost.description)) {
                            return false;
                        }
               if (this.entities == null ? vinePost.entities != null : !this.entities.equals(vinePost.entities)) {
                            return false;
                        }
               if (this.foursquareVenueId == null ? vinePost.foursquareVenueId != null : !this.foursquareVenueId.equals(vinePost.foursquareVenueId)) {
                            return false;
                        }
               if (this.likes == null ? vinePost.likes != null : !this.likes.equals(vinePost.likes)) {
                            return false;
                        }
               if (this.location == null ? vinePost.location != null : !this.location.equals(vinePost.location)) {
                            return false;
                        }
               if (this.orderId == null ? vinePost.orderId != null : !this.orderId.equals(vinePost.orderId)) {
                            return false;
                        }
               if (this.tag == null ? vinePost.tag != null : !this.tag.equals(vinePost.tag)) {
                            return false;
                        }
               if (this.shareUrl == null ? vinePost.shareUrl != null : !this.shareUrl.equals(vinePost.shareUrl)) {
                            return false;
                        }
               if (this.tags == null ? vinePost.tags != null : !this.tags.equals(vinePost.tags)) {
                            return false;
                        }
               if (this.thumbnailUrl == null ? vinePost.thumbnailUrl != null : !this.thumbnailUrl.equals(vinePost.thumbnailUrl)) {
                            return false;
                        }
               if (this.user == null ? vinePost.user != null : !this.user.equals(vinePost.user)) {
                            return false;
                        }
               if (this.username == null ? vinePost.username != null : !this.username.equals(vinePost.username)) {
                            return false;
                        }
               if (this.venueData == null ? vinePost.venueData != null : !this.venueData.equals(vinePost.venueData)) {
                            return false;
                        }
               if (this.videoLowUrl == null ? vinePost.videoLowUrl != null : !this.videoLowUrl.equals(vinePost.videoLowUrl)) {
                            return false;
                        }
               if (this.videoUrl == null ? vinePost.videoUrl != null : !this.videoUrl.equals(vinePost.videoUrl)) {
                            return false;
                        }
               if (this.repost == null ? vinePost.repost != null : !this.repost.equals(vinePost.repost)) {
                            return false;
                        }
               if (this.userBackgroundColor == vinePost.userBackgroundColor && this.loops == vinePost.loops && this.velocity == vinePost.velocity && this.lastRefresh == vinePost.lastRefresh && this.onFire == vinePost.onFire && this.byline == vinePost.byline && this.following == vinePost.following) {
                   if (this.tieredUrls != null) {
                                if (this.tieredUrls.equals(vinePost.tieredUrls)) {
                                    return true;
                                }
                            } else if (vinePost.tieredUrls == null) {
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }

       public int hashCode() {
           int result = (int) (this.created ^ (this.created >>> 32));
           return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + this.metadataFlags) * 31) + (this.isLast ? 1 : 0)) * 31) + this.postFlags) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + ((int) (this.myRepostId ^ (this.myRepostId >>> 32)))) * 31) + this.likesCount) * 31) + this.revinersCount) * 31) + this.commentsCount) * 31) + (this.orderId != null ? this.orderId.hashCode() : 0)) * 31) + (this.tag != null ? this.tag.hashCode() : 0)) * 31) + (this.foursquareVenueId != null ? this.foursquareVenueId.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.videoLowUrl != null ? this.videoLowUrl.hashCode() : 0)) * 31) + (this.videoUrl != null ? this.videoUrl.hashCode() : 0)) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + (this.shareUrl != null ? this.shareUrl.hashCode() : 0)) * 31) + (this.thumbnailUrl != null ? this.thumbnailUrl.hashCode() : 0)) * 31) + (this.tags != null ? this.tags.hashCode() : 0)) * 31) + (this.entities != null ? this.entities.hashCode() : 0)) * 31) + (this.comments != null ? this.comments.hashCode() : 0)) * 31) + (this.likes != null ? this.likes.hashCode() : 0)) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + (this.venueData != null ? this.venueData.hashCode() : 0)) * 31) + (this.repost != null ? this.repost.hashCode() : 0)) * 31) + this.userBackgroundColor) * 31) + ((int) (this.loops ^ (this.loops >>> 32)))) * 31) + ((int) (this.velocity * 3600.0d))) * 31) + ((int) (this.lastRefresh ^ (this.lastRefresh >>> 32)))) * 31) + (this.onFire ? 1 : 0)) * 31) + (this.byline != null ? this.byline.hashCode() : 0)) * 31) + (this.following ? 1 : 0)) * 31) + (this.tieredUrls != null ? this.tieredUrls.hashCode() : 0);
                }

       public void setFlagVerified(boolean verified) {
           if (verified) {
               this.metadataFlags |= 1;
                    } else {
               this.metadataFlags &= -2;
                    }
                }

       public void setFlagExplicit(boolean explicit) {
           if (explicit) {
               this.metadataFlags |= 4;
                    } else {
               this.metadataFlags &= -5;
                    }
                }

       public void setFlagPromoted(boolean promoted) {
           if (promoted) {
               this.metadataFlags |= 2;
                    } else {
               this.metadataFlags &= -3;
                    }
                }

       public void setFlagLiked(boolean liked) {
           if (liked) {
               this.metadataFlags |= 8;
                    } else {
               this.metadataFlags &= -9;
                    }
                }

       public void setFlagRevined(boolean revined) {
           if (revined) {
               this.metadataFlags |= 16;
                    } else {
               this.metadataFlags &= -17;
                    }
                }

       public void setFlagPostVerified(boolean postVerified) {
           if (postVerified) {
               this.metadataFlags |= 32;
                    } else {
               this.metadataFlags &= -33;
                    }
                }

       public void setFlagPrivate(boolean priv) {
           if (priv) {
               this.metadataFlags |= 64;
                    } else {
               this.metadataFlags &= -65;
                    }
                }

       public boolean isVerified() {
                    return (this.metadataFlags & 1) != 0;
                }

       public boolean isExplicit() {
                    return (this.metadataFlags & 4) != 0;
                }

       public boolean isPromoted() {
                    return (this.metadataFlags & 2) != 0;
                }

       public boolean isLiked() {
                    return (this.metadataFlags & 8) != 0;
                }

       public boolean isRevined() {
                    return (this.metadataFlags & 16) != 0;
                }

       public boolean isPrivate() {
                    return (this.metadataFlags & 64) != 0;
                }

       public boolean isShareable() {
                    return this.shareUrl != null;
                }

       public long getVineRepostRepostId() {
           if (this.repost != null) {
               return this.repost.repostId;
                    }
                    return 0L;
                }

                
       static class C03492 implements Parcelable.Creator<VinePost> {
           C03492() {
                    }



                    
                    @Override 
           public VinePost createFromParcel(Parcel in) {
               return new VinePost(in);
                    }

                    
                    @Override 
           public VinePost[] newArray(int size) {
               return new VinePost[size];
                    }
                }
            }
