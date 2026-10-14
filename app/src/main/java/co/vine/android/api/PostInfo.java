            package co.vine.android.api;

            import android.text.TextUtils;
            import java.util.ArrayList;
            import org.json.JSONArray;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public class PostInfo {
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

        public PostInfo(String caption, boolean postToTwitter, boolean postToFacebook, long channelId, ArrayList<VineEntity> entities, String message, long postId, String videoUrl, String thumbUrl, long created, ArrayList<VineRecipient> recipients, int maxLoops) {
            this.caption = caption == null ? "" : caption;
            this.postToTwitter = postToTwitter;
            this.postToFacebook = postToFacebook;
            this.entities = entities;
            this.channelId = channelId;
            this.created = created;
            this.message = message;
            this.postId = postId;
            this.recipients = recipients;
            this.maxLoops = maxLoops;
            this.sharedPostVideoUrl = videoUrl;
            this.sharedPostThumbUrl = thumbUrl;
                }

        public String toString() {
                    try {
                return toJson().toString();
                    } catch (JSONException e) {
                throw new RuntimeException(e);
                    }
                }

        private JSONObject toJson() throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("description", this.caption);
            jsonObject.put("postToTwitter", this.postToTwitter);
            jsonObject.put("postToFacebook", this.postToFacebook);
            jsonObject.put("channelId", this.channelId);
            jsonObject.put("maxLoops", this.maxLoops);
            if (!TextUtils.isEmpty(this.sharedPostVideoUrl)) {
                jsonObject.put("videoUrl", this.sharedPostVideoUrl);
                    }
            if (!TextUtils.isEmpty(this.sharedPostThumbUrl)) {
                jsonObject.put("thumbUrl", this.sharedPostThumbUrl);
                    }
            if (this.entities != null && !this.entities.isEmpty()) {
                jsonObject.put("entities", entitiesToJsonArray(this.entities));
                    }
            if (this.recipients != null && !this.recipients.isEmpty()) {
                jsonObject.put("recipients", recipientsToJsonArray(this.recipients));
                    }
            jsonObject.put("message", this.message);
            jsonObject.put("postId", this.postId);
            jsonObject.put("created", this.created);
            return jsonObject;
                }

       public static JSONArray entitiesToJsonArray(ArrayList<VineEntity> entities) throws JSONException {
           JSONArray array = new JSONArray();
                    for (VineEntity entity : entities) {
               array.put(entity.toJsonObject());
                    }
           return array;
                }

       public static JSONArray recipientsToJsonArray(ArrayList<VineRecipient> recipients) throws JSONException {
           JSONArray array = new JSONArray();
                    for (VineRecipient recipient : recipients) {
               array.put(recipient.toJsonObject());
                    }
           return array;
                }
            }
