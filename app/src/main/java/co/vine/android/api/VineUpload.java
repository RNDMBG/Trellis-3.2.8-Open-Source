            package co.vine.android.api;

            import android.database.Cursor;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.text.TextUtils;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class VineUpload implements Parcelable {
                public static final Parcelable.Creator<VineUpload> CREATOR = new C03611();
                public String captchaUrl;
                public long conversationRowId;
                public String hash;

                /* JADX INFO: renamed from: id */
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

/* 40 */        public VineUpload(String path, String hash, String postInfo, String videoUrl, String thumbnailPath, String thumbnailUrl, String uploadTime, String reference, int status, String captchaUrl, boolean isPrivate, long conversationRowId, long mergedMessageId) {
/* 42 */            this.path = path;
/* 43 */            this.hash = hash;
/* 44 */            this.postInfo = postInfo;
/* 45 */            this.videoUrl = videoUrl;
/* 46 */            this.thumbnailPath = thumbnailPath;
/* 47 */            this.thumbnailUrl = thumbnailUrl;
/* 48 */            this.reference = reference;
/* 49 */            this.status = status;
/* 50 */            this.isPrivate = isPrivate;
/* 51 */            this.conversationRowId = conversationRowId;
/* 54 */            if (!TextUtils.isEmpty(uploadTime)) {
/* 55 */                this.uploadTime = Long.parseLong(uploadTime);
                    } else {
/* 57 */                this.uploadTime = 0L;
                    }
/* 60 */            this.captchaUrl = captchaUrl;
/* 61 */            this.mergedMessageId = mergedMessageId;
                }

/* 63 */        public VineUpload() {
/* 65 */            this.status = 0;
                }

/* 67 */        public VineUpload(Parcel in) {
/* 69 */            this.status = in.readInt();
/* 70 */            this.isPrivate = in.readInt() > 0;
/* 71 */            this.f98id = in.readLong();
/* 72 */            this.uploadTime = in.readLong();
/* 73 */            this.conversationRowId = in.readLong();
/* 74 */            this.path = in.readString();
/* 75 */            this.hash = in.readString();
/* 76 */            this.postInfo = in.readString();
/* 77 */            this.thumbnailPath = in.readString();
/* 78 */            this.thumbnailUrl = in.readString();
/* 79 */            this.reference = in.readString();
/* 80 */            this.captchaUrl = in.readString();
/* 81 */            this.mergedMessageId = in.readLong();
/* 82 */            this.videoUrl = in.readString();
                }

                @Override // android.os.Parcelable
/* 86 */        public int describeContents() {
/* 87 */            return 0;
                }

/* 90 */        public static synchronized VineUpload fromCursor(String path, Cursor c) {
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
/* 92 */                postInfo = c.getString(4);
/* 93 */                videoUrl = c.getString(5);
/* 94 */                thumbnailPath = c.getString(6);
/* 95 */                thumbnailUrl = c.getString(7);
/* 96 */                uploadTime = c.getString(8);
/* 97 */                reference = c.getString(11);
/* 98 */                status = c.getInt(3);
/* 99 */                isPrivate = c.getInt(9) > 0;
/* 100 */               conversationRowId = c.getLong(10);
/* 101 */               captchaUrl = c.getString(13);
/* 102 */               mergedMessageId = c.getLong(15);
                    } catch (Throwable th) {
                        throw th;
                    }
/* 104 */           return new VineUpload(path, hash, postInfo, videoUrl, thumbnailPath, thumbnailUrl, uploadTime, reference, status, captchaUrl, isPrivate, conversationRowId, mergedMessageId);
                }

/* 108 */       public boolean isExpired() {
                    return System.currentTimeMillis() - this.uploadTime > 86400000;
                }

/* 112 */       public PostInfo getPostInfo() {
/* 113 */           SLog.m447d("Post info: {}.", this.postInfo);
/* 114 */           if (TextUtils.isEmpty(this.postInfo)) {
/* 115 */               return null;
                    }
                    try {
/* 118 */               JsonParser parser = VineParsers.createParser(this.postInfo);
/* 119 */               return VineUploadParsers.parsePostInfo(parser);
                    } catch (IOException e) {
/* 121 */               throw new RuntimeException("This should never happen.", e);
                    }
                }

/* 126 */       public String[] getUris() {
/* 127 */           return new String[]{this.videoUrl, this.thumbnailUrl};
                }

/* 130 */       public void setUris(String[] uris) {
/* 131 */           this.videoUrl = uris[0];
/* 132 */           this.thumbnailUrl = uris[1];
                }

/* 135 */       public void setCaptchaUrl(String captchaUrl) {
/* 136 */           this.captchaUrl = captchaUrl;
                }

                @Override // android.os.Parcelable
/* 140 */       public void writeToParcel(Parcel out, int flags) {
/* 141 */           out.writeInt(this.status);
/* 142 */           out.writeInt(this.isPrivate ? 1 : 0);
/* 143 */           out.writeLong(this.f98id);
/* 144 */           out.writeLong(this.uploadTime);
/* 145 */           out.writeLong(this.conversationRowId);
/* 146 */           out.writeString(this.path);
/* 147 */           out.writeString(this.hash);
/* 148 */           out.writeString(this.postInfo);
/* 149 */           out.writeString(this.thumbnailPath);
/* 150 */           out.writeString(this.thumbnailUrl);
/* 151 */           out.writeString(this.reference);
/* 152 */           out.writeString(this.captchaUrl);
/* 153 */           out.writeLong(this.mergedMessageId);
/* 154 */           out.writeString(this.videoUrl);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineUpload$1 */
/* 155 */       static class C03611 implements Parcelable.Creator<VineUpload> {
/* 156 */           C03611() {
                    }



                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 158 */           public VineUpload createFromParcel(Parcel in) {
/* 159 */               return new VineUpload(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
/* 162 */           public VineUpload[] newArray(int size) {
/* 163 */               return new VineUpload[size];
                    }
                }
            }
