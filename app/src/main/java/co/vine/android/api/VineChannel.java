            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.annotation.NonNull;
            
            
    public class VineChannel implements Parcelable, Comparable<VineChannel> {
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


        public VineChannel() {
                }

        public VineChannel(Parcel in) {
            this.isLast = in.readInt() == 1;
            this.following = in.readInt() == 1;
            this.channelId = in.readLong();
            this.created = in.readLong();
            this.lastUsedTimestamp = in.readLong();
            this.channel = in.readString();
            this.backgroundColor = in.readString();
            this.fontColor = in.readString();
            this.iconFullUrl = in.readString();
            this.retinaIconFullUrl = in.readString();
            this.exploreRetinaIconFullUrl = in.readString();
                }

        public static VineChannel from(Cursor c) {
            VineChannel p = new VineChannel();
            p.isLast = c.getInt(7) == 1;
            p.following = c.getInt(10) == 1;
            p.channelId = c.getLong(1);
            p.created = c.getLong(3);
            p.lastUsedTimestamp = c.getLong(6);
            p.channel = c.getString(2);
            p.backgroundColor = c.getString(4);
            p.fontColor = c.getString(5);
            p.iconFullUrl = c.getString(8);
            p.retinaIconFullUrl = c.getString(9);
            p.exploreRetinaIconFullUrl = c.getString(11);
            return p;
                }

                @Override 
        public int describeContents() {
            return 0;
                }

                @Override 
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.isLast ? 1 : 0);
            out.writeInt(this.following ? 1 : 0);
            out.writeLong(this.channelId);
            out.writeLong(this.created);
            out.writeLong(this.lastUsedTimestamp);
            out.writeString(this.channel);
            out.writeString(this.backgroundColor);
            out.writeString(this.fontColor);
            out.writeString(this.iconFullUrl);
            out.writeString(this.retinaIconFullUrl);
            out.writeString(this.exploreRetinaIconFullUrl);
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VineChannel that = (VineChannel) o;
           if (this.isLast == that.isLast && this.following == that.following && this.channelId == that.channelId && this.created == that.created && this.lastUsedTimestamp == that.lastUsedTimestamp) {
               if (this.channel == null ? that.channel != null : !this.channel.equals(that.channel)) {
                            return false;
                        }
               if (this.iconFullUrl == null ? that.iconFullUrl != null : !this.iconFullUrl.equals(that.iconFullUrl)) {
                            return false;
                        }
               if (this.retinaIconFullUrl == null ? that.retinaIconFullUrl != null : !this.retinaIconFullUrl.equals(that.retinaIconFullUrl)) {
                            return false;
                        }
               if (this.backgroundColor == null ? that.backgroundColor != null : !this.backgroundColor.equals(that.backgroundColor)) {
                            return false;
                        }
               if (this.fontColor == null ? that.fontColor != null : !this.fontColor.equals(that.fontColor)) {
                            return false;
                        }
               if (this.exploreRetinaIconFullUrl != null) {
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

       public int hashCode() {
           int result = (int) (this.channelId ^ (this.channelId >>> 32));
           return (((((((((((((((((((result * 31) + ((int) (this.created ^ (this.created >>> 32)))) * 31) + ((int) (this.lastUsedTimestamp ^ (this.lastUsedTimestamp >>> 32)))) * 31) + (this.isLast ? 1 : 0)) * 31) + (this.following ? 1 : 0)) * 31) + (this.channel != null ? this.channel.hashCode() : 0)) * 31) + (this.iconFullUrl != null ? this.iconFullUrl.hashCode() : 0)) * 31) + (this.retinaIconFullUrl != null ? this.retinaIconFullUrl.hashCode() : 0)) * 31) + (this.backgroundColor != null ? this.backgroundColor.hashCode() : 0)) * 31) + (this.fontColor != null ? this.fontColor.hashCode() : 0)) * 31) + (this.exploreRetinaIconFullUrl != null ? this.exploreRetinaIconFullUrl.hashCode() : 0);
                }

                
       public int compareTo(@NonNull VineChannel vineChannel) {
           return Long.valueOf(vineChannel.channelId).compareTo(Long.valueOf(this.channelId));
                }

                
       static class C03201 implements Parcelable.Creator<VineChannel> {
           C03201() {
                    }



                    
                    @Override 
           public VineChannel createFromParcel(Parcel in) {
               return new VineChannel(in);
                    }

                    
                    @Override 
           public VineChannel[] newArray(int size) {
               return new VineChannel[size];
                    }
                }
            }
