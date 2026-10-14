            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.annotation.NonNull;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class VineChannel implements Parcelable, Comparable<VineChannel> {
                public static final Parcelable.Creator<VineChannel> CREATOR = new C03201();
                public String backgroundColor;
                public String channel;
                public long channelId;
                public long created;
                public String exploreRetinaIconFullUrl;
                public boolean following;
                public String fontColor;
                public String iconFullUrl;
                public boolean isLast;
                public long lastUsedTimestamp;
                public String retinaIconFullUrl;


/* 38 */        public VineChannel() {
                }

/* 43 */        public VineChannel(Parcel in) {
/* 45 */            this.isLast = in.readInt() == 1;
/* 46 */            this.following = in.readInt() == 1;
/* 47 */            this.channelId = in.readLong();
/* 48 */            this.created = in.readLong();
/* 49 */            this.lastUsedTimestamp = in.readLong();
/* 50 */            this.channel = in.readString();
/* 51 */            this.backgroundColor = in.readString();
/* 52 */            this.fontColor = in.readString();
/* 53 */            this.iconFullUrl = in.readString();
/* 54 */            this.retinaIconFullUrl = in.readString();
/* 55 */            this.exploreRetinaIconFullUrl = in.readString();
                }

/* 65 */        public static VineChannel from(Cursor c) {
/* 66 */            VineChannel p = new VineChannel();
/* 68 */            p.isLast = c.getInt(7) == 1;
/* 69 */            p.following = c.getInt(10) == 1;
/* 70 */            p.channelId = c.getLong(1);
/* 71 */            p.created = c.getLong(3);
/* 72 */            p.lastUsedTimestamp = c.getLong(6);
/* 73 */            p.channel = c.getString(2);
/* 74 */            p.backgroundColor = c.getString(4);
/* 75 */            p.fontColor = c.getString(5);
/* 76 */            p.iconFullUrl = c.getString(8);
/* 77 */            p.retinaIconFullUrl = c.getString(9);
/* 78 */            p.exploreRetinaIconFullUrl = c.getString(11);
/* 79 */            return p;
                }

                @Override // android.os.Parcelable
/* 83 */        public int describeContents() {
/* 84 */            return 0;
                }

                @Override // android.os.Parcelable
/* 88 */        public void writeToParcel(Parcel out, int flags) {
/* 89 */            out.writeInt(this.isLast ? 1 : 0);
/* 90 */            out.writeInt(this.following ? 1 : 0);
/* 91 */            out.writeLong(this.channelId);
/* 92 */            out.writeLong(this.created);
/* 93 */            out.writeLong(this.lastUsedTimestamp);
/* 94 */            out.writeString(this.channel);
/* 95 */            out.writeString(this.backgroundColor);
/* 96 */            out.writeString(this.fontColor);
/* 97 */            out.writeString(this.iconFullUrl);
/* 98 */            out.writeString(this.retinaIconFullUrl);
/* 99 */            out.writeString(this.exploreRetinaIconFullUrl);
                }

/* 103 */       public boolean equals(Object o) {
/* 104 */           if (this == o) {
                        return true;
                    }
/* 105 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 107 */           VineChannel that = (VineChannel) o;
/* 109 */           if (this.isLast == that.isLast && this.following == that.following && this.channelId == that.channelId && this.created == that.created && this.lastUsedTimestamp == that.lastUsedTimestamp) {
/* 114 */               if (this.channel == null ? that.channel != null : !this.channel.equals(that.channel)) {
                            return false;
                        }
/* 116 */               if (this.iconFullUrl == null ? that.iconFullUrl != null : !this.iconFullUrl.equals(that.iconFullUrl)) {
                            return false;
                        }
/* 118 */               if (this.retinaIconFullUrl == null ? that.retinaIconFullUrl != null : !this.retinaIconFullUrl.equals(that.retinaIconFullUrl)) {
                            return false;
                        }
/* 120 */               if (this.backgroundColor == null ? that.backgroundColor != null : !this.backgroundColor.equals(that.backgroundColor)) {
                            return false;
                        }
/* 122 */               if (this.fontColor == null ? that.fontColor != null : !this.fontColor.equals(that.fontColor)) {
                            return false;
                        }
/* 124 */               if (this.exploreRetinaIconFullUrl != null) {
                            if (this.exploreRetinaIconFullUrl.equals(that.exploreRetinaIconFullUrl)) {
                                return true;
                            }
                        } else if (that.exploreRetinaIconFullUrl == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

/* 132 */       public int hashCode() {
/* 133 */           int result = (int) (this.channelId ^ (this.channelId >>> 32));
/* 143 */           return (((((((((((((((((((result * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + ((int) (this.lastUsedTimestamp ^ (this.lastUsedTimestamp >>> 32)))) * 31) + (this.isLast ? 1 : 0)) * 31) + (this.following ? 1 : 0)) * 31) + (this.channel != null ? this.channel.hashCode() : 0)) * 31) + (this.iconFullUrl != null ? this.iconFullUrl.hashCode() : 0)) * 31) + (this.retinaIconFullUrl != null ? this.retinaIconFullUrl.hashCode() : 0)) * 31) + (this.backgroundColor != null ? this.backgroundColor.hashCode() : 0)) * 31) + (this.fontColor != null ? this.fontColor.hashCode() : 0)) * 31) + (this.exploreRetinaIconFullUrl != null ? this.exploreRetinaIconFullUrl.hashCode() : 0);
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 149 */       public int compareTo(@NonNull VineChannel vineChannel) {
/* 150 */           return Long.valueOf(vineChannel.channelId).compareTo(Long.valueOf(this.channelId));
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineChannel$1 */
/* 151 */       static class C03201 implements Parcelable.Creator<VineChannel> {
/* 152 */           C03201() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 154 */           public VineChannel createFromParcel(Parcel in) {
/* 155 */               return new VineChannel(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 158 */           public VineChannel[] newArray(int size) {
/* 159 */               return new VineChannel[size];
                    }
                }
            }
