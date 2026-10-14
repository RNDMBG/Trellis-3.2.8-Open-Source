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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class VineEntity implements Externalizable, Comparable<VineEntity>, Parcelable {
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

                /* JADX INFO: renamed from: id */
                public long f96id;
                public String link;
                public int start;
                public String title;
                public String type;


/* 43 */        public VineEntity() {
/* 42 */            this.adjusted = false;
                }

/* 47 */        public VineEntity(String type, String title, String link, int start, int end, long id) {
/* 42 */            this.adjusted = false;
/* 49 */            this.type = type;
/* 50 */            this.title = title;
/* 51 */            this.link = link;
/* 52 */            this.start = start;
/* 53 */            this.end = end;
/* 54 */            this.f96id = id;
/* 55 */            this.adjusted = false;
                }

/* 58 */        public VineEntity(VineEntity entity) {
/* 59 */            this(entity.type, entity.title, entity.link, entity.start, entity.end, entity.f96id);
                }

                @Override // java.io.Externalizable
/* 63 */        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 64 */            this.type = (String) objectInput.readObject();
/* 65 */            this.title = (String) objectInput.readObject();
/* 66 */            this.link = (String) objectInput.readObject();
/* 67 */            this.start = objectInput.readInt();
/* 68 */            this.end = objectInput.readInt();
/* 69 */            this.f96id = objectInput.readLong();
/* 70 */            this.adjusted = objectInput.readBoolean();
                }

/* 73 */        public VineEntity duplicate() {
/* 74 */            return new VineEntity(this.type, this.title, this.link, this.start, this.end, this.f96id);
                }

                @Override // java.io.Externalizable
/* 78 */        public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 79 */            objectOutput.writeObject(this.type);
/* 80 */            objectOutput.writeObject(this.title);
/* 81 */            objectOutput.writeObject(this.link);
/* 82 */            objectOutput.writeInt(this.start);
/* 83 */            objectOutput.writeInt(this.end);
/* 84 */            objectOutput.writeLong(this.f96id);
/* 85 */            objectOutput.writeBoolean(this.adjusted);
                }

/* 89 */        public boolean equals(Object o) {
/* 90 */            if (this == o) {
                        return true;
                    }
/* 91 */            if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 93 */            VineEntity entity = (VineEntity) o;
/* 95 */            if (this.adjusted == entity.adjusted && this.end == entity.end && this.f96id == entity.f96id && this.start == entity.start) {
/* 99 */                if (this.link == null ? entity.link != null : !this.link.equals(entity.link)) {
                            return false;
                        }
/* 100 */               if (this.title == null ? entity.title != null : !this.title.equals(entity.title)) {
                            return false;
                        }
/* 101 */               if (this.type != null) {
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

/* 107 */       public int hashCode() {
/* 108 */           int result = this.type != null ? this.type.hashCode() : 0;
/* 114 */           return (((((((((((result * 31) + (this.title != null ? this.title.hashCode() : 0)) * 31) + (this.link != null ? this.link.hashCode() : 0)) * 31) + this.start) * 31) + this.end) * 31) + ((int) (this.f96id ^ (this.f96id >>> 32)))) * 31) + (this.adjusted ? 1 : 0);
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 119 */       public int compareTo(VineEntity vineEntity) {
/* 120 */           return Integer.valueOf(this.end).compareTo(Integer.valueOf(vineEntity.end));
                }

/* 123 */       public boolean isUserType() {
                    return "user".equals(this.type) || TYPE_MENTION.equals(this.type) || "post".equals(this.type);
                }

/* 128 */       public boolean isTagType() {
/* 129 */           return "tag".equals(this.type);
                }

/* 132 */       public boolean isUserListType() {
/* 133 */           return TYPE_USER_LIST.equals(this.type);
                }

/* 136 */       public boolean isCommentListType() {
/* 137 */           return TYPE_COMMENT_LIST.equals(this.type);
                }

/* 140 */       public HashMap<String, Object> toMap() {
/* 141 */           HashMap<String, Object> objectHashMap = new HashMap<>();
/* 142 */           objectHashMap.put("id", Long.valueOf(this.f96id));
/* 143 */           objectHashMap.put("type", this.type);
/* 144 */           objectHashMap.put(MimeTypes.BASE_TYPE_TEXT, this.title);
/* 146 */           ArrayList<Integer> range = new ArrayList<>();
/* 147 */           range.add(Integer.valueOf(this.start));
/* 148 */           range.add(Integer.valueOf(this.end));
/* 149 */           objectHashMap.put("range", range);
/* 151 */           return objectHashMap;
                }

/* 154 */       public JSONObject toJsonObject() throws JSONException {
/* 155 */           JSONObject object = new JSONObject();
/* 156 */           object.put("id", this.f96id);
/* 157 */           object.put("type", this.type);
/* 158 */           object.put(MimeTypes.BASE_TYPE_TEXT, this.title);
/* 160 */           JSONArray range = new JSONArray();
/* 161 */           range.put(this.start);
/* 162 */           range.put(this.end);
/* 163 */           object.put("range", range);
/* 164 */           return object;
                }

/* 168 */       public VineEntity(Parcel in) {
/* 42 */            this.adjusted = false;
/* 170 */           this.type = in.readString();
/* 171 */           this.title = in.readString();
/* 172 */           this.link = in.readString();
/* 173 */           this.start = in.readInt();
/* 174 */           this.end = in.readInt();
/* 175 */           this.f96id = in.readLong();
/* 176 */           this.adjusted = in.readInt() == 1;
                }

                @Override // android.os.Parcelable
/* 180 */       public void writeToParcel(Parcel out, int flags) {
/* 181 */           out.writeString(this.type);
/* 182 */           out.writeString(this.title);
/* 183 */           out.writeString(this.link);
/* 184 */           out.writeInt(this.start);
/* 185 */           out.writeInt(this.end);
/* 186 */           out.writeLong(this.f96id);
/* 187 */           out.writeInt(this.adjusted ? 1 : 0);
                }

                @Override // android.os.Parcelable
/* 191 */       public int describeContents() {
/* 192 */           return 0;
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineEntity$1 */
/* 193 */       static class C03251 implements Parcelable.Creator<VineEntity> {
/* 194 */           C03251() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 197 */           public VineEntity createFromParcel(Parcel in) {
/* 198 */               return new VineEntity(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 202 */           public VineEntity[] newArray(int size) {
/* 203 */               return new VineEntity[size];
                    }
                }

/* 207 */       public long getPostId() {
/* 208 */           Uri uri = Uri.parse(this.link);
/* 209 */           String postIdString = uri.getPathSegments().get(0);
/* 210 */           return Long.parseLong(postIdString);
                }

/* 216 */       public static class Range {
                    public VineEntity entity;
                    public ForegroundColorSpan span;
                    public int upper;

/* 217 */           public Range(int upper, VineEntity entity, ForegroundColorSpan span) {
/* 219 */               this.upper = upper;
/* 220 */               this.entity = entity;
/* 221 */               this.span = span;
                    }
                }
            }
