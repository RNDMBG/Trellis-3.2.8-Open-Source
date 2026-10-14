            package co.vine.android.api;

            import android.text.TextUtils;
            import java.util.ArrayList;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class PostInfo {
                public static final String AG_CHANNEL_ID = "channelId";
                public static final String AG_DESCRIPTION = "description";
                public static final String AG_ENTITIES = "entities";
                public static final String AG_MAX_LOOPS = "maxLoops";
                public static final String AG_MESSAGE = "message";
                public static final String AG_POST_ID = "postId";
                public static final String AG_POST_TO_FACEBOOK = "postToFacebook";
                public static final String AG_POST_TO_TWITTER = "postToTwitter";
                public static final String AG_RECIPIENTS = "recipients";
                public static final String AG_THUMB_URL = "thumbUrl";
                public static final String AG_TIMESTAMP = "created";
                public static final String AG_VIDEO_URL = "videoUrl";
                public final String caption;
                public long channelId;
                public long created;
                public final ArrayList<VineEntity> entities;
                public final int maxLoops;
                public final String message;
                public final long postId;
                public boolean postToFacebook;
                public boolean postToTwitter;
                public ArrayList<VineRecipient> recipients;
                public final String sharedPostThumbUrl;
                public final String sharedPostVideoUrl;

/* 47 */        public PostInfo(String caption, boolean postToTwitter, boolean postToFacebook, long channelId, ArrayList<VineEntity> entities, String message, long postId, String videoUrl, String thumbUrl, long created, ArrayList<VineRecipient> recipients, int maxLoops) {
/* 52 */            this.caption = caption == null ? "" : caption;
/* 53 */            this.postToTwitter = postToTwitter;
/* 54 */            this.postToFacebook = postToFacebook;
/* 55 */            this.entities = entities;
/* 56 */            this.channelId = channelId;
/* 57 */            this.created = created;
/* 58 */            this.message = message;
/* 59 */            this.postId = postId;
/* 60 */            this.recipients = recipients;
/* 61 */            this.maxLoops = maxLoops;
/* 62 */            this.sharedPostVideoUrl = videoUrl;
/* 63 */            this.sharedPostThumbUrl = thumbUrl;
                }

/* 68 */        public String toString() {
                    try {
/* 69 */                return toJson().toString();
                    } catch (JSONException e) {
/* 71 */                throw new RuntimeException(e);
                    }
                }

/* 75 */        private JSONObject toJson() throws JSONException {
/* 76 */            JSONObject jsonObject = new JSONObject();
/* 77 */            jsonObject.put("description", this.caption);
/* 78 */            jsonObject.put("postToTwitter", this.postToTwitter);
/* 79 */            jsonObject.put("postToFacebook", this.postToFacebook);
/* 80 */            jsonObject.put("channelId", this.channelId);
/* 81 */            jsonObject.put("maxLoops", this.maxLoops);
/* 82 */            if (!TextUtils.isEmpty(this.sharedPostVideoUrl)) {
/* 83 */                jsonObject.put("videoUrl", this.sharedPostVideoUrl);
                    }
/* 85 */            if (!TextUtils.isEmpty(this.sharedPostThumbUrl)) {
/* 86 */                jsonObject.put("thumbUrl", this.sharedPostThumbUrl);
                    }
/* 88 */            if (this.entities != null && !this.entities.isEmpty()) {
/* 89 */                jsonObject.put("entities", entitiesToJsonArray(this.entities));
                    }
/* 91 */            if (this.recipients != null && !this.recipients.isEmpty()) {
/* 92 */                jsonObject.put("recipients", recipientsToJsonArray(this.recipients));
                    }
/* 94 */            jsonObject.put("message", this.message);
/* 95 */            jsonObject.put("postId", this.postId);
/* 96 */            jsonObject.put("created", this.created);
/* 97 */            return jsonObject;
                }

/* 100 */       public static JSONArray entitiesToJsonArray(ArrayList<VineEntity> entities) throws JSONException {
/* 101 */           JSONArray array = new JSONArray();
                    for (VineEntity entity : entities) {
/* 103 */               array.put(entity.toJsonObject());
                    }
/* 105 */           return array;
                }

/* 108 */       public static JSONArray recipientsToJsonArray(ArrayList<VineRecipient> recipients) throws JSONException {
/* 109 */           JSONArray array = new JSONArray();
                    for (VineRecipient recipient : recipients) {
/* 111 */               array.put(recipient.toJsonObject());
                    }
/* 113 */           return array;
                }
            }
