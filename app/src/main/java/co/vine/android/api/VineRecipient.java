            package co.vine.android.api;

            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.HashMap;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 49 */    public class VineRecipient implements Externalizable, Parcelable {
                public static final Parcelable.Creator<VineRecipient> CREATOR = new C03561();
                public static final String KEY_DISPLAY = "display";
                public static final String KEY_EMAIL = "email";
                public static final String KEY_PHONE = "phoneNumber";
                public static final String KEY_RECIPIENT_ID = "recipientId";
                public static final String KEY_USER_ID = "userId";
                public String avatarUrl;
                public int color;
                public long contactId;
                public String display;
                public long friendIndex;
                public String key;
                public long lastFriendRefresh = -1;
                public long recipientId;
                public int sectionIndex;
                public String sectionTitle;
                public long userId;
                public String value;

/* 50 */        private VineRecipient(String key, String value, long userId, String display, int color, long recipientId) {
/* 52 */            this.key = key;
/* 53 */            this.value = value;
/* 54 */            this.userId = userId;
/* 55 */            this.display = display;
/* 56 */            this.color = color;
/* 57 */            this.recipientId = recipientId;
                }

/* 60 */        public static VineRecipient fromJson(JsonParser p) throws IOException {
/* 61 */            String key = null;
/* 62 */            String value = null;
/* 63 */            String display = null;
/* 64 */            String recipientIdString = null;
/* 65 */            long userId = 0;
/* 67 */            JsonToken t = p.nextToken();
/* 68 */            while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_STRING:
/* 71 */                        String cn = p.getCurrentName();
/* 72 */                        if (KEY_PHONE.equals(cn) || "email".equals(cn)) {
/* 74 */                            key = cn;
/* 75 */                            value = p.getText();
                                } else if (KEY_RECIPIENT_ID.equals(cn)) {
/* 77 */                            recipientIdString = p.getText();
                                } else if ("display".equals(cn)) {
/* 79 */                            display = p.getText();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 84 */                        String cn2 = p.getCurrentName();
/* 85 */                        if ("userId".equals(cn2)) {
/* 86 */                            key = cn2;
/* 87 */                            userId = p.getLongValue();
                                }
                                break;
                        }
/* 92 */                t = p.nextToken();
                    }
/* 94 */            long recipientId = -1;
/* 95 */            if (!TextUtils.isEmpty(recipientIdString)) {
/* 96 */                recipientId = Long.valueOf(recipientIdString).longValue();
                    }
/* 98 */            return new VineRecipient(key, value, userId, display, 0, recipientId);
                }

/* 101 */       public static VineRecipient fromUser(long userId) {
/* 102 */           return fromUser(null, userId, 0, -1L);
                }

/* 105 */       public static VineRecipient fromUser(String display, long userId, int color, long recipientId) {
/* 106 */           return new VineRecipient("userId", null, userId, display, color, recipientId);
                }

/* 109 */       public static VineRecipient fromEmail(String display, long userId, String email, long recipientId) {
/* 110 */           return new VineRecipient("email", email, userId, display, -1, recipientId);
                }

/* 113 */       public static VineRecipient fromPhone(String display, long userId, String phone, long recipientId) {
/* 114 */           return new VineRecipient(KEY_PHONE, phone, userId, display, -1, recipientId);
                }

/* 117 */       public static VineRecipient fromRecipientId(String display, long userId, String phone, long recipientId) {
/* 118 */           return new VineRecipient(KEY_PHONE, phone, userId, display, -1, recipientId);
                }

/* 121 */       public boolean isFromUser() {
/* 122 */           return "userId".equals(this.key);
                }

/* 125 */       public boolean isFromPhone() {
/* 126 */           return KEY_PHONE.equals(this.key);
                }

/* 129 */       public boolean isFromEmail() {
/* 130 */           return "email".equals(this.key);
                }

/* 132 */       public VineRecipient(Parcel in) {
/* 134 */           this.key = in.readString();
/* 135 */           this.value = in.readString();
/* 136 */           this.userId = in.readLong();
/* 137 */           this.recipientId = in.readLong();
/* 138 */           this.display = in.readString();
                }

/* 141 */       public HashMap<String, Object> toMap() {
/* 142 */           HashMap<String, Object> map = new HashMap<>();
/* 143 */           if ("userId".equals(this.key)) {
/* 144 */               map.put(this.key, Long.valueOf(this.userId));
                    } else {
/* 146 */               map.put(this.key, this.value);
                    }
                    if (this.recipientId > 0) {
/* 149 */               map.put(KEY_RECIPIENT_ID, String.valueOf(this.recipientId));
                    }
/* 151 */           if (this.display != null) {
/* 152 */               map.put("display", this.display);
                    }
/* 154 */           return map;
                }

/* 157 */       public JSONObject toJsonObject() throws JSONException {
/* 158 */           JSONObject jsonObject = new JSONObject();
/* 159 */           if ("userId".equals(this.key)) {
/* 160 */               jsonObject.put(this.key, this.userId);
                    } else {
/* 162 */               jsonObject.put(this.key, this.value);
                    }
                    if (this.recipientId > 0) {
/* 165 */               jsonObject.put(KEY_RECIPIENT_ID, String.valueOf(this.recipientId));
                    }
/* 167 */           if (this.display != null) {
/* 168 */               jsonObject.put("display", this.display);
                    }
/* 170 */           return jsonObject;
                }

                @Override // android.os.Parcelable
/* 174 */       public void writeToParcel(Parcel out, int flags) {
/* 175 */           out.writeString(this.key);
/* 176 */           out.writeString(this.value);
/* 177 */           out.writeLong(this.userId);
/* 178 */           out.writeLong(this.recipientId);
/* 179 */           out.writeString(this.display);
                }

                @Override // android.os.Parcelable
/* 183 */       public int describeContents() {
/* 184 */           return 0;
                }

                @Override // java.io.Externalizable
/* 188 */       public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
/* 189 */           this.key = (String) objectInput.readObject();
/* 190 */           this.value = (String) objectInput.readObject();
/* 191 */           this.userId = objectInput.readLong();
/* 192 */           this.recipientId = objectInput.readLong();
/* 193 */           this.display = objectInput.readLine();
                }

                @Override // java.io.Externalizable
/* 197 */       public void writeExternal(ObjectOutput objectOutput) throws IOException {
/* 198 */           objectOutput.writeObject(this.key);
/* 199 */           objectOutput.writeObject(this.value);
/* 200 */           objectOutput.writeLong(this.userId);
/* 201 */           objectOutput.writeLong(this.recipientId);
/* 202 */           objectOutput.writeChars(this.display);
                }

/* 205 */       public String getTextSortKey() {
/* 206 */           return this.display.substring(0, 1).toUpperCase();
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineRecipient$1 */
/* 207 */       static class C03561 implements Parcelable.Creator<VineRecipient> {
/* 208 */           C03561() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 211 */           public VineRecipient createFromParcel(Parcel in) {
/* 212 */               return new VineRecipient(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 216 */           public VineRecipient[] newArray(int size) {
/* 217 */               return new VineRecipient[size];
                    }
                }

/* 222 */       public boolean equals(Object o) {
/* 223 */           if (this == o) {
                        return true;
                    }
/* 224 */           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
/* 226 */           VineRecipient that = (VineRecipient) o;
/* 228 */           if (this.display == null ? that.display != null : !this.display.equals(that.display)) {
                        return false;
                    }
/* 229 */           if (this.key == null ? that.key != null : !this.key.equals(that.key)) {
                        return false;
                    }
/* 230 */           if (this.value == null ? that.value != null : !this.value.equals(that.value)) {
                        return false;
                    }
                    return this.userId == that.userId && this.recipientId == that.recipientId;
                }

/* 239 */       public int hashCode() {
/* 240 */           int result = this.key != null ? this.key.hashCode() : 0;
/* 243 */           return (((((result * 31) + (this.value != null ? this.value.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.recipientId ^ (this.recipientId >>> 32)));
                }
            }
