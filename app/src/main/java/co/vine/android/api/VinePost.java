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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 87 */    public class VinePost implements Parcelable {
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

/* 88 */        public VinePost() {
                }

/* 92 */        public void setUpUrls(List<VineVideoUrlTier> urls) {
/* 93 */            this.tieredUrls = urls;
/* 94 */            if (this.tieredUrls != null && this.tieredUrls.size() > 0) {
/* 95 */                Collections.sort(this.tieredUrls, new C03481());
                        for (VineVideoUrlTier tier : this.tieredUrls) {
/* 103 */                   if (VineVideoUrlTier.FORMAT_H264C.equals(tier.format)) {
/* 104 */                       this.videoLowUrl = tier.url;
                            }
                        }
/* 109 */               if (this.videoLowUrl == null) {
/* 110 */                   this.videoLowUrl = this.tieredUrls.get(0).url;
                        }
/* 114 */               this.videoUrl = this.tieredUrls.get(this.tieredUrls.size() - 1).url;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePost$1 */
/* 93 */        class C03481 implements Comparator<VineVideoUrlTier> {
/* 94 */            C03481() {
                    }


                    /* JADX INFO: renamed from: compare, reason: avoid collision after fix types in other method */
/* 97 */            public int compare(VineVideoUrlTier lhs, VineVideoUrlTier rhs) {
/* 98 */                return (int) (lhs.rate - rhs.rate);
                    }
                }

/* 118 */       public List<VineVideoUrlTier> getUrls() {
/* 119 */           return this.tieredUrls;
                }

/* 122 */       public VinePost(Parcel in) {
/* 124 */           ClassLoader cl = getClass().getClassLoader();
/* 125 */           this.created = in.readLong();
/* 126 */           this.metadataFlags = in.readInt();
/* 127 */           this.isLast = in.readInt() == 1;
/* 128 */           this.postFlags = in.readInt();
/* 129 */           this.likesCount = in.readInt();
/* 130 */           this.revinersCount = in.readInt();
/* 131 */           this.commentsCount = in.readInt();
/* 132 */           this.userId = in.readLong();
/* 133 */           this.postId = in.readLong();
/* 134 */           this.myRepostId = in.readLong();
/* 135 */           this.orderId = in.readString();
/* 136 */           this.tag = in.readString();
/* 137 */           this.foursquareVenueId = in.readString();
/* 138 */           this.description = in.readString();
/* 139 */           this.avatarUrl = in.readString();
/* 140 */           this.location = in.readString();
/* 141 */           this.videoLowUrl = in.readString();
/* 142 */           this.videoUrl = in.readString();
/* 143 */           this.username = in.readString();
/* 144 */           this.shareUrl = in.readString();
/* 145 */           this.thumbnailUrl = in.readString();
/* 146 */           this.comments = (VinePagedData) in.readParcelable(cl);
/* 147 */           this.likes = (VinePagedData) in.readParcelable(cl);
/* 148 */           this.user = (VineUser) in.readParcelable(cl);
/* 149 */           this.venueData = (VineVenue) in.readSerializable();
/* 150 */           this.entities = (ArrayList) in.readSerializable();
/* 151 */           this.repost = (VineRepost) in.readSerializable();
/* 152 */           this.userBackgroundColor = in.readInt();
/* 153 */           this.loops = in.readLong();
/* 154 */           this.velocity = in.readLong();
/* 155 */           this.lastRefresh = in.readLong();
/* 156 */           this.onFire = in.readInt() == 1;
/* 157 */           this.byline = (VineByline) in.readParcelable(cl);
/* 158 */           this.following = in.readInt() == 1;
/* 160 */           this.tieredUrls = new ArrayList();
/* 161 */           in.readTypedList(this.tieredUrls, VineVideoUrlTier.CREATOR);
                }

/* 173 */       public static VinePost from(Cursor c) {
/* 174 */           VinePost p = new VinePost();
/* 175 */           p.postId = c.getLong(1);
/* 176 */           p.orderId = c.getString(49);
/* 177 */           p.tag = c.getString(13);
/* 178 */           p.myRepostId = c.getLong(2);
/* 179 */           p.shareUrl = c.getString(5);
/* 180 */           p.videoUrl = c.getString(7);
/* 181 */           p.videoLowUrl = c.getString(6);
/* 182 */           p.description = c.getString(8);
/* 183 */           p.foursquareVenueId = c.getString(9);
/* 184 */           p.metadataFlags = c.getInt(10);
/* 185 */           p.isLast = c.getInt(14) == 1;
/* 186 */           p.postFlags = c.getInt(11);
/* 187 */           p.likesCount = c.getInt(24);
/* 188 */           p.revinersCount = c.getInt(25);
/* 189 */           p.commentsCount = c.getInt(26);
/* 190 */           p.userId = c.getLong(16);
/* 191 */           p.created = c.getLong(17);
/* 192 */           p.avatarUrl = c.getString(15);
/* 193 */           p.location = c.getString(18);
/* 194 */           p.username = c.getString(19);
/* 195 */           p.thumbnailUrl = c.getString(4);
/* 196 */           p.userBackgroundColor = c.getInt(44);
/* 197 */           p.comments = new VinePagedData<>();
/* 198 */           p.likes = new VinePagedData<>();
/* 199 */           p.venueData = (VineVenue) Util.fromByteArray(c.getBlob(20));
/* 201 */           p.entities = (ArrayList) Util.fromByteArray(c.getBlob(21));
/* 203 */           p.repost = (VineRepost) Util.fromByteArray(c.getBlob(22));
/* 205 */           p.loops = c.getLong(45);
/* 206 */           p.velocity = c.getLong(46);
/* 207 */           p.lastRefresh = c.getLong(48);
/* 208 */           p.onFire = c.getInt(47) == 1;
/* 209 */           return p;
                }

/* 212 */       public static byte[] getBytesFromTags(VinePost post) throws IOException {
/* 213 */           ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 214 */           ObjectOutput out = new ObjectOutputStream(bos);
/* 215 */           if (post.tags != null) {
                        for (VineTag tag : post.tags) {
/* 217 */                   out.writeLong(tag.tagId);
/* 218 */                   out.writeObject(tag.tagName);
                        }
                    }
/* 221 */           out.flush();
/* 222 */           out.close();
/* 223 */           bos.close();
/* 224 */           return bos.toByteArray();
                }

                @Override // android.os.Parcelable
/* 228 */       public int describeContents() {
/* 229 */           return 0;
                }

                @Override // android.os.Parcelable
/* 233 */       public void writeToParcel(Parcel out, int flags) {
/* 234 */           out.writeLong(this.created);
/* 235 */           out.writeInt(this.metadataFlags);
/* 236 */           out.writeInt(this.isLast ? 1 : 0);
/* 237 */           out.writeInt(this.postFlags);
/* 238 */           out.writeInt(this.likesCount);
/* 239 */           out.writeInt(this.revinersCount);
/* 240 */           out.writeInt(this.commentsCount);
/* 241 */           out.writeLong(this.userId);
/* 242 */           out.writeLong(this.postId);
/* 243 */           out.writeLong(this.myRepostId);
/* 244 */           out.writeString(this.orderId);
/* 245 */           out.writeString(this.tag);
/* 246 */           out.writeString(this.foursquareVenueId);
/* 247 */           out.writeString(this.description);
/* 248 */           out.writeString(this.avatarUrl);
/* 249 */           out.writeString(this.location);
/* 250 */           out.writeString(this.videoLowUrl);
/* 251 */           out.writeString(this.videoUrl);
/* 252 */           out.writeString(this.username);
/* 253 */           out.writeString(this.shareUrl);
/* 254 */           out.writeString(this.thumbnailUrl);
/* 255 */           out.writeParcelable(this.comments, flags);
/* 256 */           out.writeParcelable(this.likes, flags);
/* 257 */           out.writeParcelable(this.user, flags);
/* 258 */           out.writeSerializable(this.venueData);
/* 259 */           out.writeSerializable(this.entities);
/* 260 */           out.writeSerializable(this.repost);
/* 261 */           out.writeInt(this.userBackgroundColor);
/* 262 */           out.writeLong(this.loops);
/* 263 */           out.writeDouble(this.velocity);
/* 264 */           out.writeLong(this.lastRefresh);
/* 265 */           out.writeInt(this.onFire ? 1 : 0);
/* 266 */           out.writeParcelable(this.byline, flags);
/* 267 */           out.writeInt(this.following ? 1 : 0);
/* 268 */           out.writeTypedList(this.tieredUrls);
                }

/* 272 */       public boolean equals(Object o) {
/* 273 */           if (this == o) {
                        return true;
                    }
/* 274 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 276 */           VinePost vinePost = (VinePost) o;
/* 280 */           if (this.postId == vinePost.postId && this.myRepostId == vinePost.myRepostId && this.commentsCount == vinePost.commentsCount && this.created == vinePost.created && this.metadataFlags == vinePost.metadataFlags && this.isLast == vinePost.isLast && this.likesCount == vinePost.likesCount && this.revinersCount == vinePost.revinersCount && this.postFlags == vinePost.postFlags && this.userId == vinePost.userId) {
/* 288 */               if (this.avatarUrl == null ? vinePost.avatarUrl != null : !this.avatarUrl.equals(vinePost.avatarUrl)) {
                            return false;
                        }
/* 290 */               if (this.comments == null ? vinePost.comments != null : !this.comments.equals(vinePost.comments)) {
                            return false;
                        }
/* 292 */               if (this.description == null ? vinePost.description != null : !this.description.equals(vinePost.description)) {
                            return false;
                        }
/* 295 */               if (this.entities == null ? vinePost.entities != null : !this.entities.equals(vinePost.entities)) {
                            return false;
                        }
/* 297 */               if (this.foursquareVenueId == null ? vinePost.foursquareVenueId != null : !this.foursquareVenueId.equals(vinePost.foursquareVenueId)) {
                            return false;
                        }
/* 300 */               if (this.likes == null ? vinePost.likes != null : !this.likes.equals(vinePost.likes)) {
                            return false;
                        }
/* 301 */               if (this.location == null ? vinePost.location != null : !this.location.equals(vinePost.location)) {
                            return false;
                        }
/* 303 */               if (this.orderId == null ? vinePost.orderId != null : !this.orderId.equals(vinePost.orderId)) {
                            return false;
                        }
/* 305 */               if (this.tag == null ? vinePost.tag != null : !this.tag.equals(vinePost.tag)) {
                            return false;
                        }
/* 307 */               if (this.shareUrl == null ? vinePost.shareUrl != null : !this.shareUrl.equals(vinePost.shareUrl)) {
                            return false;
                        }
/* 309 */               if (this.tags == null ? vinePost.tags != null : !this.tags.equals(vinePost.tags)) {
                            return false;
                        }
/* 310 */               if (this.thumbnailUrl == null ? vinePost.thumbnailUrl != null : !this.thumbnailUrl.equals(vinePost.thumbnailUrl)) {
                            return false;
                        }
/* 313 */               if (this.user == null ? vinePost.user != null : !this.user.equals(vinePost.user)) {
                            return false;
                        }
/* 314 */               if (this.username == null ? vinePost.username != null : !this.username.equals(vinePost.username)) {
                            return false;
                        }
/* 316 */               if (this.venueData == null ? vinePost.venueData != null : !this.venueData.equals(vinePost.venueData)) {
                            return false;
                        }
/* 318 */               if (this.videoLowUrl == null ? vinePost.videoLowUrl != null : !this.videoLowUrl.equals(vinePost.videoLowUrl)) {
                            return false;
                        }
/* 321 */               if (this.videoUrl == null ? vinePost.videoUrl != null : !this.videoUrl.equals(vinePost.videoUrl)) {
                            return false;
                        }
/* 323 */               if (this.repost == null ? vinePost.repost != null : !this.repost.equals(vinePost.repost)) {
                            return false;
                        }
/* 325 */               if (this.userBackgroundColor == vinePost.userBackgroundColor && this.loops == vinePost.loops && this.velocity == vinePost.velocity && this.lastRefresh == vinePost.lastRefresh && this.onFire == vinePost.onFire && this.byline == vinePost.byline && this.following == vinePost.following) {
/* 338 */                   if (this.tieredUrls != null) {
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

/* 344 */       public int hashCode() {
/* 345 */           int result = (int) (this.created ^ (this.created >>> 32));
/* 380 */           return (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((result * 31) + this.metadataFlags) * 31) + (this.isLast ? 1 : 0)) * 31) + this.postFlags) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.postId ^ (this.postId >>> 32)))) * 31) + ((int) (this.myRepostId ^ (this.myRepostId >>> 32)))) * 31) + this.likesCount) * 31) + this.revinersCount) * 31) + this.commentsCount) * 31) + (this.orderId != null ? this.orderId.hashCode() : 0)) * 31) + (this.tag != null ? this.tag.hashCode() : 0)) * 31) + (this.foursquareVenueId != null ? this.foursquareVenueId.hashCode() : 0)) * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.avatarUrl != null ? this.avatarUrl.hashCode() : 0)) * 31) + (this.location != null ? this.location.hashCode() : 0)) * 31) + (this.videoLowUrl != null ? this.videoLowUrl.hashCode() : 0)) * 31) + (this.videoUrl != null ? this.videoUrl.hashCode() : 0)) * 31) + (this.username != null ? this.username.hashCode() : 0)) * 31) + (this.shareUrl != null ? this.shareUrl.hashCode() : 0)) * 31) + (this.thumbnailUrl != null ? this.thumbnailUrl.hashCode() : 0)) * 31) + (this.tags != null ? this.tags.hashCode() : 0)) * 31) + (this.entities != null ? this.entities.hashCode() : 0)) * 31) + (this.comments != null ? this.comments.hashCode() : 0)) * 31) + (this.likes != null ? this.likes.hashCode() : 0)) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + (this.venueData != null ? this.venueData.hashCode() : 0)) * 31) + (this.repost != null ? this.repost.hashCode() : 0)) * 31) + this.userBackgroundColor) * 31) + ((int) (this.loops ^ (this.loops >>> 32)))) * 31) + ((int) (this.velocity * 3600.0d))) * 31) + ((int) (this.lastRefresh ^ (this.lastRefresh >>> 32)))) * 31) + (this.onFire ? 1 : 0)) * 31) + (this.byline != null ? this.byline.hashCode() : 0)) * 31) + (this.following ? 1 : 0)) * 31) + (this.tieredUrls != null ? this.tieredUrls.hashCode() : 0);
                }

/* 384 */       public void setFlagVerified(boolean verified) {
/* 385 */           if (verified) {
/* 386 */               this.metadataFlags |= 1;
                    } else {
/* 388 */               this.metadataFlags &= -2;
                    }
                }

/* 392 */       public void setFlagExplicit(boolean explicit) {
/* 393 */           if (explicit) {
/* 394 */               this.metadataFlags |= 4;
                    } else {
/* 396 */               this.metadataFlags &= -5;
                    }
                }

/* 400 */       public void setFlagPromoted(boolean promoted) {
/* 401 */           if (promoted) {
/* 402 */               this.metadataFlags |= 2;
                    } else {
/* 404 */               this.metadataFlags &= -3;
                    }
                }

/* 408 */       public void setFlagLiked(boolean liked) {
/* 409 */           if (liked) {
/* 410 */               this.metadataFlags |= 8;
                    } else {
/* 412 */               this.metadataFlags &= -9;
                    }
                }

/* 416 */       public void setFlagRevined(boolean revined) {
/* 417 */           if (revined) {
/* 418 */               this.metadataFlags |= 16;
                    } else {
/* 420 */               this.metadataFlags &= -17;
                    }
                }

/* 424 */       public void setFlagPostVerified(boolean postVerified) {
/* 425 */           if (postVerified) {
/* 426 */               this.metadataFlags |= 32;
                    } else {
/* 428 */               this.metadataFlags &= -33;
                    }
                }

/* 432 */       public void setFlagPrivate(boolean priv) {
/* 433 */           if (priv) {
/* 434 */               this.metadataFlags |= 64;
                    } else {
/* 436 */               this.metadataFlags &= -65;
                    }
                }

/* 440 */       public boolean isVerified() {
                    return (this.metadataFlags & 1) != 0;
                }

/* 444 */       public boolean isExplicit() {
                    return (this.metadataFlags & 4) != 0;
                }

/* 448 */       public boolean isPromoted() {
                    return (this.metadataFlags & 2) != 0;
                }

/* 452 */       public boolean isLiked() {
                    return (this.metadataFlags & 8) != 0;
                }

/* 456 */       public boolean isRevined() {
                    return (this.metadataFlags & 16) != 0;
                }

/* 460 */       public boolean isPrivate() {
                    return (this.metadataFlags & 64) != 0;
                }

/* 464 */       public boolean isShareable() {
                    return this.shareUrl != null;
                }

/* 476 */       public long getVineRepostRepostId() {
/* 477 */           if (this.repost != null) {
/* 478 */               return this.repost.repostId;
                    }
                    return 0L;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VinePost$2 */
/* 481 */       static class C03492 implements Parcelable.Creator<VinePost> {
/* 482 */           C03492() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 484 */           public VinePost createFromParcel(Parcel in) {
/* 485 */               return new VinePost(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 488 */           public VinePost[] newArray(int size) {
/* 489 */               return new VinePost[size];
                    }
                }
            }
