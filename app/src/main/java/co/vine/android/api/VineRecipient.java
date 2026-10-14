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
            
            
    public class VineRecipient implements Externalizable, Parcelable {
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

        private VineRecipient(String key, String value, long userId, String display, int color, long recipientId) {
            this.key = key;
            this.value = value;
            this.userId = userId;
            this.display = display;
            this.color = color;
            this.recipientId = recipientId;
                }

        public static VineRecipient fromJson(JsonParser p) throws IOException {
            String key = null;
            String value = null;
            String display = null;
            String recipientIdString = null;
            long userId = 0;
            JsonToken t = p.nextToken();
            while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_STRING:
                        String cn = p.getCurrentName();
                        if (KEY_PHONE.equals(cn) || "email".equals(cn)) {
                            key = cn;
                            value = p.getText();
                                } else if (KEY_RECIPIENT_ID.equals(cn)) {
                            recipientIdString = p.getText();
                                } else if ("display".equals(cn)) {
                            display = p.getText();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                        String cn2 = p.getCurrentName();
                        if ("userId".equals(cn2)) {
                            key = cn2;
                            userId = p.getLongValue();
                                }
                                break;
                        }
                t = p.nextToken();
                    }
            long recipientId = -1;
            if (!TextUtils.isEmpty(recipientIdString)) {
                recipientId = Long.valueOf(recipientIdString).longValue();
                    }
            return new VineRecipient(key, value, userId, display, 0, recipientId);
                }

       public static VineRecipient fromUser(long userId) {
           return fromUser(null, userId, 0, -1L);
                }

       public static VineRecipient fromUser(String display, long userId, int color, long recipientId) {
           return new VineRecipient("userId", null, userId, display, color, recipientId);
                }

       public static VineRecipient fromEmail(String display, long userId, String email, long recipientId) {
           return new VineRecipient("email", email, userId, display, -1, recipientId);
                }

       public static VineRecipient fromPhone(String display, long userId, String phone, long recipientId) {
           return new VineRecipient(KEY_PHONE, phone, userId, display, -1, recipientId);
                }

       public static VineRecipient fromRecipientId(String display, long userId, String phone, long recipientId) {
           return new VineRecipient(KEY_PHONE, phone, userId, display, -1, recipientId);
                }

       public boolean isFromUser() {
           return "userId".equals(this.key);
                }

       public boolean isFromPhone() {
           return KEY_PHONE.equals(this.key);
                }

       public boolean isFromEmail() {
           return "email".equals(this.key);
                }

       public VineRecipient(Parcel in) {
           this.key = in.readString();
           this.value = in.readString();
           this.userId = in.readLong();
           this.recipientId = in.readLong();
           this.display = in.readString();
                }

       public HashMap<String, Object> toMap() {
           HashMap<String, Object> map = new HashMap<>();
           if ("userId".equals(this.key)) {
               map.put(this.key, Long.valueOf(this.userId));
                    } else {
               map.put(this.key, this.value);
                    }
                    if (this.recipientId > 0) {
               map.put(KEY_RECIPIENT_ID, String.valueOf(this.recipientId));
                    }
           if (this.display != null) {
               map.put("display", this.display);
                    }
           return map;
                }

       public JSONObject toJsonObject() throws JSONException {
           JSONObject jsonObject = new JSONObject();
           if ("userId".equals(this.key)) {
               jsonObject.put(this.key, this.userId);
                    } else {
               jsonObject.put(this.key, this.value);
                    }
                    if (this.recipientId > 0) {
               jsonObject.put(KEY_RECIPIENT_ID, String.valueOf(this.recipientId));
                    }
           if (this.display != null) {
               jsonObject.put("display", this.display);
                    }
           return jsonObject;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeString(this.key);
           out.writeString(this.value);
           out.writeLong(this.userId);
           out.writeLong(this.recipientId);
           out.writeString(this.display);
                }

                @Override 
       public int describeContents() {
           return 0;
                }

                @Override 
       public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
           this.key = (String) objectInput.readObject();
           this.value = (String) objectInput.readObject();
           this.userId = objectInput.readLong();
           this.recipientId = objectInput.readLong();
           this.display = objectInput.readLine();
                }

                @Override 
       public void writeExternal(ObjectOutput objectOutput) throws IOException {
           objectOutput.writeObject(this.key);
           objectOutput.writeObject(this.value);
           objectOutput.writeLong(this.userId);
           objectOutput.writeLong(this.recipientId);
           objectOutput.writeChars(this.display);
                }

       public String getTextSortKey() {
           return this.display.substring(0, 1).toUpperCase();
                }

                
       static class C03561 implements Parcelable.Creator<VineRecipient> {
           C03561() {
                    }



                    
                    @Override 
           public VineRecipient createFromParcel(Parcel in) {
               return new VineRecipient(in);
                    }

                    
                    @Override 
           public VineRecipient[] newArray(int size) {
               return new VineRecipient[size];
                    }
                }

       public boolean equals(Object o) {
           if (this == o) {
                        return true;
                    }
           if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
           VineRecipient that = (VineRecipient) o;
           if (this.display == null ? that.display != null : !this.display.equals(that.display)) {
                        return false;
                    }
           if (this.key == null ? that.key != null : !this.key.equals(that.key)) {
                        return false;
                    }
           if (this.value == null ? that.value != null : !this.value.equals(that.value)) {
                        return false;
                    }
                    return this.userId == that.userId && this.recipientId == that.recipientId;
                }

       public int hashCode() {
           int result = this.key != null ? this.key.hashCode() : 0;
           return (((((result * 31) + (this.value != null ? this.value.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)))) * 31) + ((int) (this.recipientId ^ (this.recipientId >>> 32)));
                }
            }
