            package co.vine.android.api;

            import android.net.Uri;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.style.ForegroundColorSpan;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            import java.util.HashMap;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public class VineEntity implements Externalizable, Comparable<VineEntity>, Parcelable {
                public static final Parcelable.Creator<VineEntity> CREATOR = new C03251();
                public static final String TYPE_COMMENT_LIST = "commentList";
                public static final String TYPE_MENTION = "mention";
                public static final String TYPE_POST = "post";
                public static final String TYPE_TAG = "tag";
                public static final String TYPE_USER = "user";
                public static final String TYPE_USER_LIST = "userList";
                private static final long serialVersionUID = -4017215936161446091L;
                public boolean adjusted;
                public int end;

                
                public long f96id;
                public String link;
                public int start;
                public String title;
                public String type;


        public VineEntity() {
            this.adjusted = false;
                }

        public VineEntity(String type, String title, String link, int start, int end, long id) {
            this.adjusted = false;
            this.type = type;
            this.title = title;
            this.link = link;
            this.start = start;
            this.end = end;
            this.f96id = id;
            this.adjusted = false;
                }

        public VineEntity(VineEntity entity) {
            this(entity.type, entity.title, entity.link, entity.start, entity.end, entity.f96id);
                }

                @Override 
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            this.type = (String) objectInput.readObject();
            this.title = (String) objectInput.readObject();
            this.link = (String) objectInput.readObject();
            this.start = objectInput.readInt();
            this.end = objectInput.readInt();
            this.f96id = objectInput.readLong();
            this.adjusted = objectInput.readBoolean();
                }

        public VineEntity duplicate() {
            return new VineEntity(this.type, this.title, this.link, this.start, this.end, this.f96id);
                }

                @Override 
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeObject(this.type);
            objectOutput.writeObject(this.title);
            objectOutput.writeObject(this.link);
            objectOutput.writeInt(this.start);
            objectOutput.writeInt(this.end);
            objectOutput.writeLong(this.f96id);
            objectOutput.writeBoolean(this.adjusted);
                }

        public boolean equals(Object o) {
            if (this == o) {
                        return true;
                    }
            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
            VineEntity entity = (VineEntity) o;
            if (this.adjusted == entity.adjusted && this.end == entity.end && this.f96id == entity.f96id && this.start == entity.start) {
                if (this.link == null ? entity.link != null : !this.link.equals(entity.link)) {
                            return false;
                        }
               if (this.title == null ? entity.title != null : !this.title.equals(entity.title)) {
                            return false;
                        }
               if (this.type != null) {
                            if (this.type.equals(entity.type)) {
                                return true;
                            }
                        } else if (entity.type == null) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                }

       public int hashCode() {
           int result = this.type != null ? this.type.hashCode() : 0;
           return (((((((((((result * 31) + (this.title != null ? this.title.hashCode() : 0)) * 31) + (this.link != null ? this.link.hashCode() : 0)) * 31) + this.start) * 31) + this.end) * 31) + ((int) (this.f96id ^ (this.f96id >>> 32)))) * 31) + (this.adjusted ? 1 : 0);
                }

                
       public int compareTo(VineEntity vineEntity) {
           return Integer.valueOf(this.end).compareTo(Integer.valueOf(vineEntity.end));
                }

       public boolean isUserType() {
                    return "user".equals(this.type) || TYPE_MENTION.equals(this.type) || "post".equals(this.type);
                }

       public boolean isTagType() {
           return "tag".equals(this.type);
                }

       public boolean isUserListType() {
           return TYPE_USER_LIST.equals(this.type);
                }

       public boolean isCommentListType() {
           return TYPE_COMMENT_LIST.equals(this.type);
                }

       public HashMap<String, Object> toMap() {
           HashMap<String, Object> objectHashMap = new HashMap<>();
           objectHashMap.put("id", Long.valueOf(this.f96id));
           objectHashMap.put("type", this.type);
           objectHashMap.put(MimeTypes.BASE_TYPE_TEXT, this.title);
           ArrayList<Integer> range = new ArrayList<>();
           range.add(Integer.valueOf(this.start));
           range.add(Integer.valueOf(this.end));
           objectHashMap.put("range", range);
           return objectHashMap;
                }

       public JSONObject toJsonObject() throws JSONException {
           JSONObject object = new JSONObject();
           object.put("id", this.f96id);
           object.put("type", this.type);
           object.put(MimeTypes.BASE_TYPE_TEXT, this.title);
           JSONArray range = new JSONArray();
           range.put(this.start);
           range.put(this.end);
           object.put("range", range);
           return object;
                }

       public VineEntity(Parcel in) {
            this.adjusted = false;
           this.type = in.readString();
           this.title = in.readString();
           this.link = in.readString();
           this.start = in.readInt();
           this.end = in.readInt();
           this.f96id = in.readLong();
           this.adjusted = in.readInt() == 1;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeString(this.type);
           out.writeString(this.title);
           out.writeString(this.link);
           out.writeInt(this.start);
           out.writeInt(this.end);
           out.writeLong(this.f96id);
           out.writeInt(this.adjusted ? 1 : 0);
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                
       static class C03251 implements Parcelable.Creator<VineEntity> {
           C03251() {
                    }



                    
                    @Override 
           public VineEntity createFromParcel(Parcel in) {
               return new VineEntity(in);
                    }

                    
                    @Override 
           public VineEntity[] newArray(int size) {
               return new VineEntity[size];
                    }
                }

       public long getPostId() {
           Uri uri = Uri.parse(this.link);
           String postIdString = uri.getPathSegments().get(0);
           return Long.parseLong(postIdString);
                }

       public static class Range {
                    public VineEntity entity;
                    public ForegroundColorSpan span;
                    public int upper;

           public Range(int upper, VineEntity entity, ForegroundColorSpan span) {
               this.upper = upper;
               this.entity = entity;
               this.span = span;
                    }
                }
            }
