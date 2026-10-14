            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import java.io.IOException;
            
            
    public class VineUpload implements Parcelable {
                public static final Parcelable.Creator<VineUpload> CREATOR = new C03611();
                public String captchaUrl;
                public long conversationRowId;
                public String hash;

                
                public long f98id;
                public boolean isPrivate;
                public long mergedMessageId;
                public String path;
                public String postInfo;
                public String reference;
                public int status;
                public String thumbnailPath;
                public String thumbnailUrl;
                public long uploadTime;
                public String videoUrl;

        public VineUpload(String path, String hash, String postInfo, String videoUrl, String thumbnailPath, String thumbnailUrl, String uploadTime, String reference, int status, String captchaUrl, boolean isPrivate, long conversationRowId, long mergedMessageId) {
            this.path = path;
            this.hash = hash;
            this.postInfo = postInfo;
            this.videoUrl = videoUrl;
            this.thumbnailPath = thumbnailPath;
            this.thumbnailUrl = thumbnailUrl;
            this.reference = reference;
            this.status = status;
            this.isPrivate = isPrivate;
            this.conversationRowId = conversationRowId;
            if (!TextUtils.isEmpty(uploadTime)) {
                this.uploadTime = Long.parseLong(uploadTime);
                    } else {
                this.uploadTime = 0L;
                    }
            this.captchaUrl = captchaUrl;
            this.mergedMessageId = mergedMessageId;
                }

        public VineUpload() {
            this.status = 0;
                }

        public VineUpload(Parcel in) {
            this.status = in.readInt();
            this.isPrivate = in.readInt() > 0;
            this.f98id = in.readLong();
            this.uploadTime = in.readLong();
            this.conversationRowId = in.readLong();
            this.path = in.readString();
            this.hash = in.readString();
            this.postInfo = in.readString();
            this.thumbnailPath = in.readString();
            this.thumbnailUrl = in.readString();
            this.reference = in.readString();
            this.captchaUrl = in.readString();
            this.mergedMessageId = in.readLong();
            this.videoUrl = in.readString();
                }

                @Override 
        public int describeContents() {
            return 0;
                }

        public static synchronized VineUpload fromCursor(String path, Cursor c) {
                    String hash;
                    String postInfo;
                    String videoUrl;
                    String thumbnailPath;
                    String thumbnailUrl;
                    String uploadTime;
                    String reference;
                    int status;
                    boolean isPrivate;
                    long conversationRowId;
                    String captchaUrl;
                    long mergedMessageId;
                    try {
                        hash = c.getString(2);
                postInfo = c.getString(4);
                videoUrl = c.getString(5);
                thumbnailPath = c.getString(6);
                thumbnailUrl = c.getString(7);
                uploadTime = c.getString(8);
                reference = c.getString(11);
                status = c.getInt(3);
                isPrivate = c.getInt(9) > 0;
               conversationRowId = c.getLong(10);
               captchaUrl = c.getString(13);
               mergedMessageId = c.getLong(15);
                    } catch (Throwable th) {
                        throw th;
                    }
           return new VineUpload(path, hash, postInfo, videoUrl, thumbnailPath, thumbnailUrl, uploadTime, reference, status, captchaUrl, isPrivate, conversationRowId, mergedMessageId);
                }

       public boolean isExpired() {
                    return System.currentTimeMillis() - this.uploadTime > 86400000;
                }

       public PostInfo getPostInfo() {
           SLog.m447d("Post info: {}.", this.postInfo);
           if (TextUtils.isEmpty(this.postInfo)) {
               return null;
                    }
                    try {
               JsonParser parser = VineParsers.createParser(this.postInfo);
               return VineUploadParsers.parsePostInfo(parser);
                    } catch (IOException e) {
               throw new RuntimeException("This should never happen.", e);
                    }
                }

       public String[] getUris() {
           return new String[]{this.videoUrl, this.thumbnailUrl};
                }

       public void setUris(String[] uris) {
           this.videoUrl = uris[0];
           this.thumbnailUrl = uris[1];
                }

       public void setCaptchaUrl(String captchaUrl) {
           this.captchaUrl = captchaUrl;
                }

                @Override 
       public void writeToParcel(Parcel out, int flags) {
           out.writeInt(this.status);
           out.writeInt(this.isPrivate ? 1 : 0);
           out.writeLong(this.f98id);
           out.writeLong(this.uploadTime);
           out.writeLong(this.conversationRowId);
           out.writeString(this.path);
           out.writeString(this.hash);
           out.writeString(this.postInfo);
           out.writeString(this.thumbnailPath);
           out.writeString(this.thumbnailUrl);
           out.writeString(this.reference);
           out.writeString(this.captchaUrl);
           out.writeLong(this.mergedMessageId);
           out.writeString(this.videoUrl);
                }

                
       static class C03611 implements Parcelable.Creator<VineUpload> {
           C03611() {
                    }



                    
                    @Override 
           public VineUpload createFromParcel(Parcel in) {
               return new VineUpload(in);
                    }

                    
                    @Override 
           public VineUpload[] newArray(int size) {
               return new VineUpload[size];
                    }
                }
            }
