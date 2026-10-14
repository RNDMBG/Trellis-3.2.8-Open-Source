            package co.vine.android.api;

            import android.os.Bundle;
            import android.os.Parcelable;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import android.util.Pair;
            import co.vine.android.ReportingFragment;
            import co.vine.android.VineWebSocketClient;
            import co.vine.android.client.VineAPI;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.provider.Vine;
            import co.vine.android.service.VineService;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.DateTimeUtil;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonFactory;
            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import com.google.android.gms.plus.PlusShare;
            import java.io.IOException;
            import java.io.InputStream;
            import java.util.ArrayList;
            import java.util.List;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            
    public class VineParsers {
                private static final String PARAM_DATA = "data";
                private static final String PARAM_EDITION_ID = "editionId";
                private static final String PARAM_NAME = "name";
                private static final String PARAM_RECORDS = "records";
                private static final String PARAM_USERS = "users";
                private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";
                public static final JsonFactory JSON_FACTORY = new JsonFactory();
                private static final RecordListParser<VineUser> USERS_PARSER = new C03351();
                private static final RecordParser<VineUser> USER_PARSER = new C03402();
                private static final RecordParser<VineLike> LIKES_PARSER = new C03413();
                private static final RecordParser<VinePost> POST_PARSER = new C03424();
                private static final RecordParser<VineComment> COMMENT_PARSER = new C03435();
                private static final RecordParser<VineSingleNotification> ACTIVITY_PARSER = new C03446();
                private static final RecordParser<VineEverydayNotification> GROUPED_ACTIVITY_PARSER = new C03457();
                private static final RecordParser<VineTag> TAG_PARSER = new C03468();
                private static final RecordParser<VineChannel> CHANNELS_PARSER = new C03479();
                private static final RecordParser<VinePrivateMessage> MESSAGES_PARSER = new C033610();
                private static final RecordParser<VineConversation> CONVERSATIONS_PARSER = new C033711();
                private static final RecordParser<VineNotificationSetting> NOTIFICATION_SETTING_PARSER = new C033812();

                public enum API_RESPONSE {
                    SUCCESS,
                    FAILURE
                }

                public interface PagedDataParser<T> {
                }

                public static abstract class RecordListParser<T> implements PagedDataParser<T> {
                    public abstract ArrayList<T> parse(JsonParser jsonParser, Bundle bundle) throws IOException;
                }

                public static abstract class RecordParser<T> implements PagedDataParser<T> {
                    public abstract T parse(JsonParser jsonParser, Bundle bundle) throws IOException;
                }

        static  ArrayList access$000(JsonParser x0, Bundle x1) throws IOException {
            return parseUsersArray(x0, x1);
                }

        static  VineUser access$100(JsonParser x0) throws IOException {
            return parseUser(x0);
                }

        static  VineNotificationSetting access$1000(JsonParser x0) throws IOException {
            return parseNotificationSetting(x0);
                }

        static  VineLike access$200(JsonParser x0) throws IOException {
            return parseLike(x0);
                }

        static  VinePost access$300(JsonParser x0) throws IOException {
            return parsePost(x0);
                }

        static  VineComment access$400(JsonParser x0) throws IOException {
            return parseComment(x0);
                }

        static  VineSingleNotification access$500(JsonParser x0) throws IOException {
            return parseNotification(x0);
                }

        static  VineEverydayNotification access$600(JsonParser x0) throws IOException {
            return parseEverydayNotification(x0);
                }

        static  VineTag access$700(JsonParser x0) throws IOException {
            return parseTag(x0);
                }

        static  VineChannel access$800(JsonParser x0) throws IOException {
            return parseChannel(x0);
                }

        static  VineConversation access$900(JsonParser x0) throws IOException {
            return parseConversation(x0);
                }

                
        static class C03351 extends RecordListParser<VineUser> {
            C03351() {
                    }

                    @Override 
            public ArrayList<VineUser> parse(JsonParser parser, Bundle extras) throws IOException {
                return VineParsers.access$000(parser, extras);
                    }
                }

                
        static class C03402 extends RecordParser<VineUser> {
            C03402() {
                    }


                    
                    @Override 
            public VineUser parse(JsonParser parser, Bundle extras) throws IOException {
                return VineParsers.access$100(parser);
                    }
                }

                
        static class C03413 extends RecordParser<VineLike> {
            C03413() {
                    }


                    
                    @Override 
            public VineLike parse(JsonParser parser, Bundle extras) throws IOException {
                return VineParsers.access$200(parser);
                    }
                }

                
        static class C03424 extends RecordParser<VinePost> {
            C03424() {
                    }


                    
                    @Override 
            public VinePost parse(JsonParser parser, Bundle extras) throws IOException {
                return VineParsers.access$300(parser);
                    }
                }

                
        static class C03435 extends RecordParser<VineComment> {
            C03435() {
                    }


                    
                    @Override 
            public VineComment parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$400(parser);
                    }
                }

                
       static class C03446 extends RecordParser<VineSingleNotification> {
           C03446() {
                    }


                    
                    @Override 
           public VineSingleNotification parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$500(parser);
                    }
                }

                
       static class C03457 extends RecordParser<VineEverydayNotification> {
           C03457() {
                    }


                    
                    @Override 
           public VineEverydayNotification parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$600(parser);
                    }
                }

                
       static class C03468 extends RecordParser<VineTag> {
           C03468() {
                    }


                    
                    @Override 
           public VineTag parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$700(parser);
                    }
                }

                
       static class C03479 extends RecordParser<VineChannel> {
           C03479() {
                    }


                    
                    @Override 
           public VineChannel parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$800(parser);
                    }
                }

                
       static class C033610 extends RecordParser<VinePrivateMessage> {
           C033610() {
                    }


                    
                    @Override 
           public VinePrivateMessage parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.parsePrivateMessage(parser);
                    }
                }

                
       static class C033711 extends RecordParser<VineConversation> {
           C033711() {
                    }


                    
                    @Override 
           public VineConversation parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$900(parser);
                    }
                }

                
       static class C033812 extends RecordParser<VineNotificationSetting> {
           C033812() {
                    }


                    
                    @Override 
           public VineNotificationSetting parse(JsonParser parser, Bundle extras) throws IOException {
               return VineParsers.access$1000(parser);
                    }
                }

       public static Object parseVineResponse(JsonParser p, int type, Bundle extras) throws IOException {
           if (type == 16) {
               return null;
                    }
           if (type == 15) {
               return parseServerStatus(p);
                    }
           if (type == 1) {
               return parseGeneral(p);
                    }
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                       if (!"data".equals(p.getCurrentName())) {
                                    continue;
                                } else {
                                    switch (type) {
                                        case 2:
                                   return parseUser(p);
                                        case 3:
                                   return parsePagedData(p, USERS_PARSER, extras);
                                        case 4:
                                        case 15:
                                        case 16:
                                        case 17:
                                        case 20:
                                        case 21:
                                        case 31:
                                        case 32:
                                        case 33:
                                        case 34:
                                        default:
                                   p.skipChildren();
                                            break;
                                        case 5:
                                   return parseSignUp(p);
                                        case 6:
                                   return parsePagedData(p, COMMENT_PARSER);
                                        case 7:
                                   return parseComment(p);
                                        case 8:
                                   return parsePagedData(p, POST_PARSER);
                                        case 9:
                                   return parsePagedData(p, ACTIVITY_PARSER);
                                        case 10:
                                   return parseLikeId(p);
                                        case 11:
                                   return parsePagedData(p, LIKES_PARSER);
                                        case 12:
                                   return parseVinePostResponse(p);
                                        case 13:
                                   return parseClientProfile(p);
                                        case 14:
                                   return parsePagedData(p, TAG_PARSER);
                                        case 18:
                                   return parsePagedData(p, CHANNELS_PARSER);
                                        case 19:
                                   return parseRepostResponse(p);
                                        case 22:
                                   return parsePagedData(p, MESSAGES_PARSER);
                                        case 23:
                                   return parsePrivateMessage(p);
                                        case 24:
                                   return parsePrivateMessageResponse(p);
                                        case 25:
                                   return parsePagedData(p, CONVERSATIONS_PARSER);
                                        case 26:
                                   return parseActivityCounts(p);
                                        case 27:
                                   return parseLoopsSubmission(p);
                                        case 28:
                                   return parsePagedData(p, GROUPED_ACTIVITY_PARSER);
                                        case 29:
                                   return parseClientFlags(p);
                                        case 30:
                                   return parsePagedData(p, NOTIFICATION_SETTING_PARSER);
                                        case 35:
                                   return parseComplaintMenu(p);
                                    }
                                }
                                break;
                            case START_ARRAY:
                       if ("data".equals(p.getCurrentName())) {
                                    switch (type) {
                                        case 3:
                                   return parseUsersArray(p, extras);
                                        case 8:
                                   return parseRecordArray(p, POST_PARSER, extras);
                                        case 25:
                                   return parseRecordArray(p, CONVERSATIONS_PARSER, extras);
                                        case 28:
                                   return parseRecordArray(p, GROUPED_ACTIVITY_PARSER, extras);
                                        case 20:
                                   return parseEditions(p);
                                        default:
                                   p.skipChildren();
                                            break;
                                    }
                                } else {
                                    continue;
                                }
                                break;
                            case VALUE_NUMBER_INT:
                       if ("data".equals(p.getCurrentName())) {
                                    switch (type) {
                                        case 21:
                                   return Integer.valueOf(p.getIntValue());
                                    }
                                }
                                continue;
                        }
               t = p.nextToken();
                    }
           throw new IllegalArgumentException("Unhandled parse type " + type);
                }

       private static VineLoopSubmissionResponse parseLoopsSubmission(JsonParser p) throws IOException {
           int submissionInterval = 0;
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                       String cn = p.getCurrentName();
                       if ("nextAfter".equals(cn)) {
                           submissionInterval = p.getIntValue();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           return new VineLoopSubmissionResponse(submissionInterval);
                }

       private static Object parseEditions(JsonParser p) throws IOException {
           ArrayList<Pair<String, String>> editions = new ArrayList<>();
           JsonToken t = p.nextToken();
           String editionId = null;
           while (t != null && t != JsonToken.END_OBJECT) {
               if (t == JsonToken.START_OBJECT) {
                   JsonToken t2 = p.nextToken();
                   while (t2 != null && t2 != JsonToken.END_OBJECT) {
                       if (t2 == JsonToken.VALUE_STRING) {
                           if (PARAM_EDITION_ID.equals(p.getCurrentName())) {
                               editionId = p.getText();
                                    } else if ("name".equals(p.getCurrentName())) {
                               editions.add(new Pair<>(editionId, p.getText()));
                                    }
                                }
                       t2 = p.nextToken();
                            }
                        }
               t = p.nextToken();
                    }
           return editions;
                }

       private static Object parseRepostResponse(JsonParser p) throws IOException {
           long repostId = 0;
           long postId = 0;
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                       String cn = p.getCurrentName();
                       if ("repostId".equals(cn)) {
                           repostId = p.getLongValue();
                                } else if ("postId".equals(cn)) {
                           postId = p.getLongValue();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           VineRepost vr = new VineRepost();
           vr.repostId = repostId;
           vr.postId = postId;
           return vr;
                }

       private static VineActivityCounts parseActivityCounts(JsonParser p) throws IOException {
           int messages = 0;
           int notifications = 0;
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_NUMBER_INT:
                       String cn = p.getCurrentName();
                       if ("messages".equals(cn)) {
                           messages = p.getIntValue();
                                } else if ("notifications".equals(cn)) {
                           notifications = p.getIntValue();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           VineActivityCounts result = new VineActivityCounts(messages, notifications);
           return result;
                }

       private static <T extends Parcelable> VinePagedData<T> parsePagedData(JsonParser p, PagedDataParser<T> parser) throws IOException {
           return parsePagedData(p, parser, null);
                }

       private static <T extends Parcelable> VinePagedData<T> parsePagedData(JsonParser jsonParser, PagedDataParser<T> pagedDataParser, Bundle bundle) throws IOException {
           VinePagedData<T> vinePagedData = new VinePagedData<>();
           vinePagedData.items = new ArrayList<>();
           int i = 1;
           RecordParser<T> recordParser = null;
           RecordListParser<T> recordListParser = null;
           if (pagedDataParser instanceof RecordListParser) {
               recordListParser = (RecordListParser) pagedDataParser;
                    } else {
               recordParser = (RecordParser) pagedDataParser;
                    }
           JsonToken jsonTokenNextToken = jsonParser.nextToken();
           while (jsonTokenNextToken != null && jsonTokenNextToken != JsonToken.END_OBJECT) {
                        switch (jsonTokenNextToken) {
                            case START_OBJECT:
                       if ("channel".equals(jsonParser.getCurrentName())) {
                           vinePagedData.channel = parseChannel(jsonParser);
                                } else {
                           jsonParser.skipChildren();
                                }
                                break;
                            case START_ARRAY:
                       if (PARAM_RECORDS.equals(jsonParser.getCurrentName())) {
                           if (recordListParser != null) {
                               vinePagedData.items.addAll(recordListParser.parse(jsonParser, bundle));
                                    } else {
                               if (recordParser == null) {
                                   throw new RuntimeException("Invalid parser.");
                                        }
                               JsonToken jsonTokenNextToken2 = jsonParser.nextToken();
                                int recordIndex = 0;
                               while (jsonTokenNextToken2 != null && jsonTokenNextToken2 != JsonToken.END_ARRAY) {
                                   T t = pagedDataParser == POST_PARSER ? (T) parseFeedPost(jsonParser) : recordParser.parse(jsonParser, bundle);
                                   if (t != null) {
                                       vinePagedData.items.add(t);
                                        }
                                    recordIndex++;
                                   jsonTokenNextToken2 = jsonParser.nextToken();
                                        }
                                    }
                                }
                       if ("users".equals(jsonParser.getCurrentName())) {
                           JsonToken jsonTokenNextToken3 = jsonParser.nextToken();
                           if (vinePagedData.participants == null) {
                               vinePagedData.participants = new ArrayList<>();
                                    }
                           while (jsonTokenNextToken3 != null && jsonTokenNextToken3 != JsonToken.END_ARRAY) {
                               VineUser vineUser = USER_PARSER.parse(jsonParser, null);
                               if (vineUser != null) {
                                   vinePagedData.participants.add(vineUser);
                                        }
                               jsonTokenNextToken3 = jsonParser.nextToken();
                                    }
                                }
                                break;
                            case VALUE_NUMBER_INT:
                       String currentName = jsonParser.getCurrentName();
                       if ("count".equals(currentName)) {
                           vinePagedData.count = jsonParser.getIntValue();
                                } else if ("nextPage".equals(currentName)) {
                           vinePagedData.nextPage = jsonParser.getIntValue();
                                } else if ("previousPage".equals(currentName)) {
                           vinePagedData.previousPage = jsonParser.getIntValue();
                                } else if (VineService.EXTRA_SIZE.equals(currentName)) {
                           vinePagedData.size = jsonParser.getIntValue();
                                } else if ("anchor".equals(currentName)) {
                           vinePagedData.anchor = jsonParser.getLongValue();
                                } else if ("title".equals(currentName)) {
                           vinePagedData.title = jsonParser.getText();
                                } else if ("unreadMessageCount".equals(currentName)) {
                           vinePagedData.unreadMessageCount = jsonParser.getLongValue();
                                } else if ("lastMessageRead".equals(currentName)) {
                           vinePagedData.lastMessageRead = jsonParser.getLongValue();
                                } else if ("lastMessage".equals(currentName)) {
                           vinePagedData.lastMessage = jsonParser.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                       if ("anchor".equals(jsonParser.getCurrentName())) {
                           vinePagedData.stringAnchor = jsonParser.getText();
                                } else if ("inbox".equals(jsonParser.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(jsonParser.getValueAsString())) {
                           i = 2;
                                }
                                break;
                        }
               jsonTokenNextToken = jsonParser.nextToken();
                    }
           vinePagedData.networkType = i;
           return vinePagedData;
                }

      private static <T extends Parcelable> VinePagedData<T> parseRecordArray(JsonParser parser, RecordParser<T> recordParser, Bundle extras) throws IOException {
          VinePagedData<T> result = new VinePagedData<>();
          result.items = new ArrayList<>();
          JsonToken token = parser.nextToken();
          while (token != null && token != JsonToken.END_ARRAY) {
              T item = recordParser.parse(parser, extras);
              if (item != null) {
                  result.items.add(item);
                    }
              token = parser.nextToken();
                }
          result.count = result.items.size();
          result.size = result.items.size();
          return result;
            }

       private static VineLike parseLike(JsonParser p) throws IOException {
           VineLike vineLike = new VineLike();
           JsonToken t = p.nextToken();
           int following = -1;
           int blocked = 0;
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                       if ("user".equals(p.getCurrentName())) {
                           vineLike.user = parseUser(p);
                                }
                                break;
                            case START_ARRAY:
                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                       String cn = p.getCurrentName();
                       if ("likeId".equals(cn)) {
                           vineLike.likeId = p.getLongValue();
                                } else if ("userId".equals(cn)) {
                           vineLike.userId = p.getLongValue();
                                } else if ("verified".equals(cn)) {
                           vineLike.verified = p.getIntValue();
                                } else if ("following".equals(cn)) {
                           following = p.getIntValue();
                                } else if ("blocked".equals(cn)) {
                           blocked = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
                       String cn2 = p.getCurrentName();
                       if ("created".equals(cn2)) {
                           vineLike.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn2)) {
                           vineLike.location = p.getText();
                                } else if ("avatarUrl".equals(cn2)) {
                           vineLike.avatarUrl = p.getText();
                                } else if ("username".equals(cn2)) {
                           vineLike.username = p.getText();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           if (vineLike.user != null) {
               vineLike.user.following = following;
               vineLike.user.blocked = blocked;
                    }
           return vineLike;
                }

       private static VineByline parseByline(JsonParser p) throws IOException {
           String iconUrl = null;
           String body = null;
           ArrayList<VineEntity> entities = new ArrayList<>();
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                       p.skipChildren();
                                break;
                            case START_ARRAY:
                       if ("entities".equals(p.getCurrentName())) {
                           JsonToken t2 = p.nextToken();
                           while (t2 != null && t2 != JsonToken.END_ARRAY) {
                               VineEntity entity = parseEntity(p);
                               if (entity != null) {
                                   entities.add(entity);
                                        }
                               t2 = p.nextToken();
                                    }
                           Util.validateAndFixEntities(entities);
                                }
                                break;
                            case VALUE_STRING:
                       String cn = p.getCurrentName();
                       if ("iconUrl".equals(cn)) {
                           iconUrl = p.getText();
                                } else if ("body".equals(cn)) {
                           body = p.getText();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           return new VineByline(body, iconUrl, entities);
                }

       private static VineRepost parseRepost(JsonParser p) throws IOException {
           String username = null;
           String avatarUrl = null;
           String location = null;
           String description = null;
           long userId = 0;
           long postId = 0;
           long repostId = 0;
           int verified = 0;
           int priv = 0;
           int unflaggable = 0;
           JsonToken t = p.nextToken();
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                       if ("user".equals(p.getCurrentName())) {
                           JsonToken t2 = p.nextToken();
                           while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
                                            case START_ARRAY:
                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
                                       String cn = p.getCurrentName();
                                       if ("verified".equals(cn)) {
                                           verified = p.getIntValue();
                                                } else if ("private".equals(cn)) {
                                           priv = p.getIntValue();
                                                } else if ("unflaggable".equals(cn)) {
                                           unflaggable = p.getIntValue();
                                                } else if ("userId".equals(cn)) {
                                           userId = p.getLongValue();
                                                }
                                                break;
                                            case VALUE_STRING:
                                       String cn2 = p.getCurrentName();
                                       if ("description".equals(cn2)) {
                                           description = p.getText();
                                                } else if ("avatarUrl".equals(cn2)) {
                                           avatarUrl = p.getText();
                                                } else if ("location".equals(cn2)) {
                                           location = p.getText();
                                                } else if ("username".equals(cn2)) {
                                           username = p.getText();
                                                }
                                                break;
                                        }
                               t2 = p.nextToken();
                                    }
                                } else {
                           p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                       String cn3 = p.getCurrentName();
                       if ("repostId".equals(cn3)) {
                           repostId = p.getLongValue();
                                } else if ("postId".equals(cn3)) {
                           postId = p.getLongValue();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           return new VineRepost(username, avatarUrl, location, description, userId, postId, repostId, verified, priv, unflaggable);
                }

       private static VinePost parsePost(JsonParser p) throws IOException {
           VinePost vinePost = new VinePost();
           String address = null;
           String categoryIconUrl = null;
           String categoryId = null;
           String categoryShortName = null;
           String city = null;
           String countryCode = null;
           String venueName = null;
           String state = null;
           String videoWebmUrl = null;
           JsonToken t = p.nextToken();
           boolean explicitContent = false;
           boolean verified = false;
           boolean liked = false;
           boolean revined = false;
           boolean promoted = false;
           boolean postVerified = false;
           boolean priv = false;
           long loops = 0;
           double velocity = 0.0d;
           boolean onFire = false;
           VineByline byline = null;
           boolean following = false;
           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                       String cn = p.getCurrentName();
                       if ("comments".equals(cn)) {
                           vinePost.comments = parsePagedData(p, COMMENT_PARSER);
                        } else if ("likes".equals(cn)) {
                           vinePost.likes = parsePagedData(p, LIKES_PARSER);
                                } else if ("loops".equals(cn)) {
                           JsonToken t2 = p.nextToken();
                           while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
                                            case START_ARRAY:
                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
                                       if ("onFire".equals(p.getCurrentName())) {
                                           if (p.getIntValue() != 1) {
                                                        onFire = false;
                                                    } else {
                                                        onFire = true;
                                                    }
                                                }
                                                break;
                                            case VALUE_TRUE:
                                            case VALUE_FALSE:
                                       if ("onFire".equals(p.getCurrentName())) {
                                           onFire = p.getBooleanValue();
                                                }
                                                break;
                                            case VALUE_NUMBER_FLOAT:
                                       if ("count".equals(p.getCurrentName())) {
                                           loops = (long) p.getDoubleValue();
                                                } else if (Vine.PostColumns.VELOCITY.equals(p.getCurrentName())) {
                                           velocity = p.getDoubleValue();
                                                }
                                                break;
                                        }
                               t2 = p.nextToken();
                                    }
                        } else if ("user".equals(cn)) {
                           vinePost.user = parseUser(p);
                           vinePost.userBackgroundColor = vinePost.user.profileBackground;
                        } else if ("repost".equals(cn)) {
                           vinePost.repost = parseRepost(p);
                                } else if (VineAPI.RESOURCE_REPOSTS.equals(cn)) {
                           JsonToken t3 = p.nextToken();
                           while (t3 != null && t3 != JsonToken.END_OBJECT) {
                                        switch (t3) {
                                            case START_OBJECT:
                                            case START_ARRAY:
                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
                                       if ("count".equals(p.getCurrentName())) {
                                           vinePost.revinersCount = p.getIntValue();
                                                }
                                                break;
                                        }
                               t3 = p.nextToken();
                                    }
                        } else if ("byline".equals(cn)) {
                           byline = parseByline(p);
                                } else {
                           p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
                       String cn2 = p.getCurrentName();
                       if ("tags".equals(cn2)) {
                           vinePost.tags = parseTags(p);
                                } else if ("entities".equals(cn2)) {
                           JsonToken t4 = p.nextToken();
                           ArrayList<VineEntity> entities = null;
                           while (t4 != null && t4 != JsonToken.END_ARRAY) {
                               VineEntity entity = parseEntity(p);
                               if (entities == null) {
                                   entities = new ArrayList<>();
                                        }
                               if (entity != null) {
                                   entities.add(entity);
                                        }
                               t4 = p.nextToken();
                                    }
                           if (entities != null) {
                               Util.validateAndFixEntities(entities);
                               vinePost.entities = entities;
                                    }
                                } else if ("videoUrls".equals(cn2)) {
                           vinePost.setUpUrls(parseVideoUrls(p));
                                } else {
                           p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                       String cn3 = p.getCurrentName();
                       if ("explicitContent".equals(cn3)) {
                           explicitContent = p.getIntValue() == 1;
                                } else if ("postFlags".equals(cn3)) {
                           vinePost.postFlags = p.getIntValue();
                                } else if ("postVerified".equals(cn3)) {
                           postVerified = p.getIntValue() == 1;
                                } else if ("promoted".equals(cn3)) {
                           promoted = p.getIntValue() == 1;
                                } else if ("verified".equals(cn3)) {
                           verified = p.getIntValue() == 1;
                                } else if ("postId".equals(cn3)) {
                           vinePost.postId = p.getLongValue();
                                } else if ("userId".equals(cn3)) {
                           vinePost.userId = p.getLongValue();
                                } else if ("myRepostId".equals(cn3)) {
                           vinePost.myRepostId = p.getLongValue();
                                    if (vinePost.myRepostId > 0) {
                               revined = true;
                                    }
                                } else if (!"private".equals(cn3)) {
                           if (!VineEverydayNotification.TYPE_LIKED.equals(cn3)) {
                               if ("following".equals(cn3)) {
                                   if (p.getIntValue() != 1) {
                                                following = false;
                                            } else {
                                                following = true;
                                            }
                                        }
                                    } else if (p.getLongValue() != 1) {
                                        liked = false;
                                    } else {
                                        liked = true;
                                    }
                                } else if (p.getLongValue() != 1) {
                                    priv = false;
                                } else {
                                    priv = true;
                                }
                                break;
                            case VALUE_STRING:
                       String cn4 = p.getCurrentName();
                       if ("foursquareVenueId".equals(cn4)) {
                           vinePost.foursquareVenueId = p.getText();
                                } else if ("description".equals(cn4)) {
                           vinePost.description = p.getText();
                                } else if ("created".equals(cn4)) {
                           vinePost.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn4)) {
                           vinePost.location = p.getText();
                                } else if ("avatarUrl".equals(cn4)) {
                           vinePost.avatarUrl = p.getText();
                                } else if ("videoLowURL".equals(cn4)) {
                           vinePost.videoLowUrl = p.getText();
                                } else if ("videoUrl".equals(cn4)) {
                           vinePost.videoUrl = p.getText();
                                } else if ("videoWebmUrl".equals(cn4)) {
                           videoWebmUrl = p.getText();
                                } else if ("username".equals(cn4)) {
                           vinePost.username = p.getText();
                                } else if ("shareUrl".equals(cn4)) {
                           vinePost.shareUrl = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(cn4)) {
                           vinePost.thumbnailUrl = p.getText();
                                } else if ("venueAddress".equals(cn4)) {
                           address = p.getText();
                                } else if ("venueCategoryIconUrl".equals(cn4)) {
                           categoryIconUrl = p.getText();
                                } else if ("venueCategoryId".equals(cn4)) {
                           categoryId = p.getText();
                                } else if ("venueCategoryShortName".equals(cn4)) {
                           categoryShortName = p.getText();
                                } else if ("venueCity".equals(cn4)) {
                           city = p.getText();
                                } else if ("venueCountryCode".equals(cn4)) {
                           countryCode = p.getText();
                                } else if ("venueName".equals(cn4)) {
                           venueName = p.getText();
                                } else if ("state".equals(cn4)) {
                           state = p.getText();
                                } else if (VineAPI.RESOURCE_PROFILE_BACKGROUND.equals(cn4)) {
                           vinePost.userBackgroundColor = (int) Long.parseLong(p.getText().substring(2), 16);
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                       String cn5 = p.getCurrentName();
                       if (VineEverydayNotification.TYPE_LIKED.equals(cn5)) {
                           liked = p.getBooleanValue();
                                } else if ("revined".equals(cn5)) {
                           revined = p.getBooleanValue();
                                }
                                break;
                        }
               t = p.nextToken();
                    }
           if (TextUtils.isEmpty(vinePost.videoUrl) && videoWebmUrl != null) {
               vinePost.videoUrl = videoWebmUrl;
                    }
           if (vinePost.foursquareVenueId != null) {
               vinePost.venueData = new VineVenue(categoryIconUrl, categoryShortName, categoryId, city, countryCode, venueName, state, address);
                    }
           vinePost.setFlagExplicit(explicitContent);
           vinePost.setFlagVerified(verified);
           vinePost.setFlagRevined(revined);
           vinePost.setFlagLiked(liked);
           vinePost.setFlagPromoted(promoted);
           vinePost.setFlagPostVerified(postVerified);
           vinePost.setFlagPrivate(priv);
           vinePost.likesCount = vinePost.likes != null ? vinePost.likes.count : 0;
           vinePost.commentsCount = vinePost.comments != null ? vinePost.comments.count : 0;
           vinePost.loops = loops;
           vinePost.lastRefresh = System.currentTimeMillis();
           vinePost.velocity = velocity;
           vinePost.onFire = onFire;
           vinePost.byline = byline;
           vinePost.following = following;
                    vinePost.originalFollowingState = following;
           return vinePost;
                }

                




      private static VinePost parseFeedPost(JsonParser p) throws IOException {
                    VinePost post = new VinePost();
                    VinePagedData<VineComment> comments = new VinePagedData<>();
                    VinePagedData<VineLike> likes = new VinePagedData<>();
                    comments.items = new ArrayList<>();
                    likes.items = new ArrayList<>();
                    post.comments = comments;
                    post.likes = likes;
                    JsonToken token = p.nextToken();
                    while (token != null && token != JsonToken.END_OBJECT) {
                        String name = p.getCurrentName();
                        if (token == JsonToken.START_OBJECT) {
                            if ("user".equals(name)) {
                                post.user = parseUser(p);
                                if (post.user != null) {
                                    if (post.userId == 0) post.userId = post.user.userId;
                                    if (TextUtils.isEmpty(post.username)) post.username = post.user.username;
                                    if (TextUtils.isEmpty(post.avatarUrl)) post.avatarUrl = post.user.avatarUrl;
                                    post.userBackgroundColor = post.user.profileBackground;
                                }
                            } else if ("reposts".equals(name) || "reviners".equals(name)) {
                                post.revinersCount = parseCountObject(p);
                            } else if ("likes".equals(name)) {
                                post.likesCount = parseCountObject(p);
                            } else if ("comments".equals(name)) {
                                post.commentsCount = parseCountObject(p);
                            } else if ("loops".equals(name)) {
                                post.loops = parseCountObject(p);
                            } else {
                                p.skipChildren();
                            }
                        } else if (token == JsonToken.START_ARRAY) {
                            if ("videoUrls".equals(name)) {
                                post.setUpUrls(parseVideoUrls(p));
                            } else {
                                p.skipChildren();
                            }
                        } else if (token == JsonToken.VALUE_NUMBER_INT) {
                            if ("postId".equals(name)) post.postId = p.getLongValue();
                            else if ("userId".equals(name)) post.userId = p.getLongValue();
                            else if ("likes".equals(name) || "likesCount".equals(name) || "likeCount".equals(name)) post.likesCount = p.getIntValue();
                            else if ("comments".equals(name) || "commentsCount".equals(name) || "commentCount".equals(name)) post.commentsCount = p.getIntValue();
                            else if ("reposts".equals(name) || "reviners".equals(name) || "repostsCount".equals(name) || "revinersCount".equals(name) || "repostCount".equals(name) || "revineCount".equals(name)) post.revinersCount = p.getIntValue();
                            else if ("loopCount".equals(name) || "loops".equals(name)) post.loops = p.getLongValue();
                            else if ("myRepostId".equals(name)) post.myRepostId = p.getLongValue();
                            else if ("liked".equals(name)) post.setFlagLiked(p.getIntValue() == 1);
                            else if ("following".equals(name)) post.following = p.getIntValue() == 1;
                        } else if (token == JsonToken.VALUE_TRUE || token == JsonToken.VALUE_FALSE) {
                            if ("liked".equals(name)) post.setFlagLiked(p.getBooleanValue());
                            else if ("revined".equals(name)) post.setFlagRevined(p.getBooleanValue());
                            else if ("following".equals(name)) post.following = p.getBooleanValue();
                        } else if (token == JsonToken.VALUE_STRING) {
                            String value = p.getText();
                            if ("username".equals(name)) post.username = value;
                            else if ("avatarUrl".equals(name)) post.avatarUrl = value;
                            else if ("description".equals(name)) post.description = value;
                            else if ("videoUrl".equals(name) || "videoPreview".equals(name)) {
                                if (TextUtils.isEmpty(post.videoUrl)) post.videoUrl = value;
                            } else if ("videoLowURL".equals(name) || "videoLowUrl".equals(name)) post.videoLowUrl = value;
                            else if ("videoWebmUrl".equals(name) && TextUtils.isEmpty(post.videoUrl)) post.videoUrl = value;
                            else if ("thumbnailUrl".equals(name)) post.thumbnailUrl = value;
                            else if ("shareUrl".equals(name)) post.shareUrl = value;
                            else if ("created".equals(name)) {
                                try { post.created = DateTimeUtil.getTimeInMsFromString(value, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"); } catch (RuntimeException ignored) { }
                            }
                        }
                        token = p.nextToken();
                    }
                    post.avatarUrl = normalizeMediaUrl(post.avatarUrl);
                    post.thumbnailUrl = normalizeMediaUrl(post.thumbnailUrl);
                    post.videoUrl = normalizeMediaUrl(post.videoUrl);
                    post.videoLowUrl = normalizeMediaUrl(post.videoLowUrl);
                    if (post.likes != null) post.likes.count = post.likesCount;
                    if (post.comments != null) post.comments.count = post.commentsCount;
                    if (post.myRepostId > 0) post.setFlagRevined(true);
                    post.lastRefresh = System.currentTimeMillis();
                    post.originalFollowingState = post.following;
                    return post;
                }

                private static String normalizeMediaUrl(String url) {
                    if (url != null && url.startsWith("http://")) {
                        return "https://" + url.substring(7);
                    }
                    return url;
                }

                private static int parseCountObject(JsonParser p) throws IOException {
                    int count = 0;
                    JsonToken token = p.nextToken();
                    while (token != null && token != JsonToken.END_OBJECT) {
                        String name = p.getCurrentName();
                        if (token == JsonToken.VALUE_NUMBER_INT && "count".equals(name)) {
                            count = p.getIntValue();
                        } else if (token == JsonToken.START_OBJECT || token == JsonToken.START_ARRAY) {
                            p.skipChildren();
                        }
                        token = p.nextToken();
                    }
                    return count;
                }

       private static List<VineVideoUrlTier> parseVideoUrls(JsonParser p) throws IOException {
           ArrayList<VineVideoUrlTier> urls = new ArrayList<>();
           JsonToken t = p.getCurrentToken();
           while (t != null && t != JsonToken.END_ARRAY) {
               t = p.nextToken();
               if (t == JsonToken.START_OBJECT) {
                   String url = null;
                   float rate = 0.0f;
                   String format = null;
                   t = p.nextToken();
                   while (t != null && t != JsonToken.END_OBJECT) {
                       String currentName = p.getCurrentName();
                       if (t == JsonToken.VALUE_STRING) {
                           if ("videoUrl".equals(currentName)) {
                               url = p.getText();
                                    } else if ("format".equals(currentName)) {
                               format = p.getText();
                                    }
                                } else if (t == JsonToken.VALUE_NUMBER_FLOAT) {
                           if ("rate".equals(currentName)) {
                               rate = p.getFloatValue();
                                    }
                                } else if (t == JsonToken.VALUE_NUMBER_INT && "rate".equals(currentName)) {
                           rate = p.getIntValue();
                                }
                       t = p.nextToken();
                            }
                   if (!VineVideoUrlTier.FORMAT_DASH.equals(format)) {
                       VineVideoUrlTier vinePostUrl = new VineVideoUrlTier(url, rate, format);
                       urls.add(vinePostUrl);
                            }
                        }
                    }
           return urls;
                }

       private static ArrayList<VineTag> parseTags(JsonParser p) throws IOException {
           ArrayList<VineTag> tags = new ArrayList<>();
           JsonToken t = p.nextToken();
           String tagName = null;
           long tagId = 0;
           while (t != null && t != JsonToken.END_ARRAY) {
                        switch (t) {
                            case START_OBJECT:
                                break;
                            default:
                                continue;
                        }
               while (t != null && t != JsonToken.END_OBJECT) {
                            switch (t) {
                                case VALUE_NUMBER_INT:
                           tagId = p.getLongValue();
                                    break;
                                case VALUE_STRING:
                           tagName = p.getText();
                                    break;
                            }
                   t = p.nextToken();
                        }
               tags.add(new VineTag(tagName, tagId));
               t = p.nextToken();
                    }
           return tags;
                }

       private static synchronized VineChannel parseChannel(JsonParser p) throws IOException {
                    VineChannel vineChannel;
                    try {
                        vineChannel = new VineChannel();
              JsonToken t = p.nextToken();
              while (t != null && t != JsonToken.END_OBJECT) {
                            switch (t) {
                                case START_OBJECT:
                                case START_ARRAY:
                          p.skipChildren();
                                    break;
                                case VALUE_NUMBER_INT:
                          String cn = p.getCurrentName();
                          if ("channelId".equals(cn)) {
                              vineChannel.channelId = p.getLongValue();
                                    } else if ("following".equals(cn)) {
                              vineChannel.following = p.getIntValue() == 1;
                                    }
                                    break;
                                case VALUE_STRING:
                          String cn2 = p.getCurrentName();
                          if ("channel".equals(cn2)) {
                              vineChannel.channel = p.getText();
                                    } else if ("iconFullUrl".equals(cn2)) {
                              vineChannel.iconFullUrl = p.getText();
                                    } else if ("retinaIconFullUrl".equals(cn2)) {
                              vineChannel.retinaIconFullUrl = p.getText();
                                    } else if ("created".equals(cn2)) {
                              vineChannel.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                    } else if ("backgroundColor".equals(cn2)) {
                              String color = p.getText();
                              if (!color.startsWith("#")) {
                                  color = "#" + color;
                                        }
                              vineChannel.backgroundColor = color;
                                    } else if ("fontColor".equals(cn2)) {
                              String color2 = p.getText();
                              if (!color2.startsWith("#")) {
                                  color2 = "#" + color2;
                                        }
                              vineChannel.fontColor = color2;
                                    } else if ("exploreRetinaIconFullUrl".equals(cn2)) {
                              vineChannel.exploreRetinaIconFullUrl = p.getText();
                                    }
                                    break;
                            }
                  t = p.nextToken();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return vineChannel;
                }

      private static VineTag parseTag(JsonParser p) throws IOException {
          VineTag tag = new VineTag();
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      tag.tagId = p.getLongValue();
                                break;
                            case VALUE_STRING:
                      tag.tagName = p.getText();
                                break;
                        }
              t = p.nextToken();
                    }
          return tag;
                }

      private static VineComment parseComment(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          String avatarUrl = null;
          String comment = null;
          long timestamp = 0;
          long commentId = 0;
          String location = null;
          VineUser user = null;
          long userId = 0;
          String username = null;
          boolean verified = false;
          ArrayList<VineEntity> entities = null;
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      if ("user".equals(p.getCurrentName())) {
                          user = parseUser(p);
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
                      if ("entities".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                              VineEntity entity = parseEntity(p);
                              if (entities == null) {
                                  entities = new ArrayList<>();
                                        }
                              if (entity != null) {
                                  entities.add(entity);
                                        }
                              t2 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                      String cn = p.getCurrentName();
                      if (ReportingFragment.EXTRA_COMMENT_ID.equals(cn)) {
                          commentId = p.getLongValue();
                                } else if ("userId".equals(cn)) {
                          userId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                      String cn2 = p.getCurrentName();
                      if ("avatarUrl".equals(cn2)) {
                          avatarUrl = p.getText();
                                } else if ("comment".equals(cn2)) {
                          comment = p.getText();
                                } else if ("created".equals(cn2)) {
                          timestamp = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn2)) {
                          location = p.getText();
                                } else if ("username".equals(cn2)) {
                          username = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      if ("verified".equals(p.getCurrentName())) {
                          verified = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          if (entities != null) {
              Util.validateAndFixEntities(entities);
                    }
                    if (TextUtils.isEmpty(avatarUrl) && user != null) {
                        avatarUrl = user.avatarUrl;
                    }
                    avatarUrl = normalizeMediaUrl(avatarUrl);
          return new VineComment(commentId, comment, avatarUrl, user, timestamp, location, userId, username, verified, 0L, entities, false);
                }

      private static Object parseGeneral(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                continue;
                            case VALUE_NUMBER_INT:
                            case VALUE_STRING:
                            default:
                                continue;
                            case VALUE_TRUE:
                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
                          return API_RESPONSE.SUCCESS;
                                }
                                continue;
                            case VALUE_FALSE:
                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
                          return API_RESPONSE.FAILURE;
                                }
                                break;
                        }
              p.skipChildren();
              t = p.nextToken();
                    }
          return null;
                }

      private static Long parseLikeId(JsonParser p) throws IOException {
          long id = 0;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                                break;
                            case VALUE_NUMBER_INT:
                      if ("likeId".equals(p.getCurrentName())) {
                          id = p.getLongValue();
                                }
                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
              p.skipChildren();
              t = p.nextToken();
                    }
          return Long.valueOf(id);
                }

      private static ArrayList<VineUser> parseUsersArray(JsonParser p, Bundle extras) throws IOException {
          JsonToken t = p.nextToken();
          ArrayList<VineUser> users = new ArrayList<>();
          int sectionId = -1;
          int sectionIndex = 0;
          if (extras != null) {
              long lastIndex = extras.getLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, -1L);
                        if (lastIndex >= 0) {
                  sectionId = (int) (lastIndex >> 32);
                  sectionIndex = (int) ((lastIndex - (((long) sectionId) << 32)) + 1);
                        }
                    }
          while (t != null && t != JsonToken.END_ARRAY) {
              if (t == JsonToken.START_OBJECT) {
                  VineUser user = parseUser(p, sectionId);
                  if (user.sectionId >= 0) {
                      if (sectionId != user.sectionId) {
                          user.friendIndex = ((long) user.sectionId) << 32;
                          sectionIndex = 1;
                          sectionId = user.sectionId;
                                } else {
                          user.friendIndex = (((long) user.sectionId) << 32) + ((long) sectionIndex);
                          sectionIndex++;
                                }
                            }
                  if (user != null) {
                      users.add(user);
                            }
                        }
              t = p.nextToken();
                    }
          return users;
                }

      private static VineUser parseUser(JsonParser p) throws IOException {
          return parseUser(p, -1);
                }

                
                @Nullable
      private static VineUser parseUser(JsonParser p, int sectionId) throws IOException {
          String username = null;
          String avatarUrl = null;
          String description = null;
          String location = null;
          String phoneNumber = null;
          String email = null;
          String edition = null;
          String sectionTitle = null;
          long userId = 0;
          long orderId = 0;
          int blocked = 0;
          int blocking = 0;
          int explicit = 0;
          boolean isExternal = false;
          int followerCount = 0;
          int followingCount = 0;
          int following = -1;
          int likeCount = 0;
          int authoredPostCount = 0;
          int postCount = 0;
          int verified = 0;
          int twitterConnected = 0;
          int includePromoted = 0;
          int privateAccount = 0;
          boolean followApprovalPending = false;
          boolean followRequested = false;
          int repostsEnabled = 1;
          boolean acceptsOutOfNetworkConversations = false;
          int profileBackground = -1;
          boolean emailVerified = false;
          boolean phoneVerified = false;
          boolean disableAddressBook = false;
          boolean hiddenEmail = false;
          boolean hiddenPhoneNumber = false;
          boolean hiddenTwitter = false;
          long loopCount = 0;
          boolean notifyPosts = false;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      String name = p.getCurrentName();
                      if ("userId".equals(name) || "id".equals(name)) {
                          userId = p.getLongValue();
                                    break;
                                } else if ("blocked".equals(name)) {
                          blocked = p.getIntValue();
                                    break;
                                } else if (Vine.UserColumns.BLOCKING.equals(name)) {
                          blocking = p.getIntValue();
                                    break;
                                } else if ("explicit".equals(name)) {
                          explicit = p.getIntValue();
                                    break;
                                } else if ("externalUser".equals(name)) {
                          isExternal = p.getIntValue() == 1;
                                    break;
                                } else if ("followerCount".equals(name)) {
                          followerCount = p.getIntValue();
                                    break;
                                } else if ("followingCount".equals(name)) {
                          followingCount = p.getIntValue();
                                    break;
                                } else if ("following".equals(name)) {
                          following = p.getIntValue();
                                    break;
                                } else {
                          if ("likeCount".equals(name)) {
                              likeCount = p.getIntValue();
                                    } else if ("authoredPostCount".equals(name)) {
                              authoredPostCount = p.getIntValue();
                                    } else if ("postCount".equals(name)) {
                              postCount = p.getIntValue();
                                    } else if ("verified".equals(name)) {
                              verified = p.getIntValue();
                                    } else if ("followId".equals(name)) {
                              orderId = p.getLongValue();
                                    } else if ("twitterConnected".equals(name)) {
                              twitterConnected = p.getIntValue();
                                    } else if ("includePromoted".equals(name)) {
                              includePromoted = p.getIntValue();
                                    } else if ("private".equals(name)) {
                              privateAccount = p.getIntValue();
                                    } else if ("followApprovalPending".equals(name)) {
                              followApprovalPending = p.getIntValue() == 1;
                                    } else if ("followRequested".equals(name)) {
                              followRequested = p.getIntValue() == 1;
                                    } else if ("repostsEnabled".equals(name)) {
                              repostsEnabled = p.getIntValue();
                                    } else if ("verifiedEmail".equals(name)) {
                              emailVerified = p.getIntValue() == 1;
                                    } else if ("verifiedPhoneNumber".equals(name)) {
                              phoneVerified = p.getIntValue() == 1;
                                    } else if ("sectionId".equals(name)) {
                              sectionId = p.getIntValue();
                                    } else if ("disableAddressBook".equals(name)) {
                              disableAddressBook = p.getIntValue() == 1;
                                    } else if (VineAPI.RESOURCE_ACCEPT_OUT_OF_NETWORK_CONVERSATIONS.equals(name)) {
                              acceptsOutOfNetworkConversations = p.getIntValue() == 1;
                                    } else if ("hiddenEmail".equals(name)) {
                              hiddenEmail = p.getIntValue() == 1;
                                    } else if ("hiddenPhoneNumber".equals(name)) {
                              hiddenPhoneNumber = p.getIntValue() == 1;
                                    } else if ("hiddenTwitter".equals(name)) {
                              hiddenTwitter = p.getIntValue() == 1;
                                    } else if ("loopCount".equals(name)) {
                              loopCount = p.getLongValue();
                                    } else if ("notifyPosts".equals(name)) {
                              if (p.getIntValue() != 1) {
                                            notifyPosts = false;
                                        } else {
                                            notifyPosts = true;
                                        }
                                    }
                                    break;
                                }
                            case VALUE_STRING:
                      String name2 = p.getCurrentName();
                      if ("avatarUrl".equals(name2) || "avatar_url".equals(name2)) {
                          avatarUrl = p.getText();
                                } else if ("username".equals(name2)) {
                          username = p.getText();
                                } else if ("description".equals(name2)) {
                          description = p.getText();
                                } else if ("location".equals(name2)) {
                          location = p.getText();
                                } else if (VineRecipient.KEY_PHONE.equals(name2)) {
                          phoneNumber = p.getText();
                                } else if ("email".equals(name2)) {
                          email = p.getText();
                                } else if ("edition".equals(name2)) {
                          edition = p.getText();
                                } else if (VineAPI.RESOURCE_PROFILE_BACKGROUND.equals(name2)) {
                          profileBackground = (int) Long.parseLong(p.getText().substring(2), 16);
                                } else if ("section".equals(name2)) {
                          sectionTitle = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                                if ("following".equals(p.getCurrentName())) {
                                    following = p.getBooleanValue() ? 1 : 0;
                                }
                                break;
                        }
              t = p.nextToken();
                    }
                    if (userId <= 0) {
              return null;
                    }
          VineUser u = new VineUser(username, avatarUrl, description, location, userId, orderId, blocked, blocking, explicit, isExternal, followerCount, followingCount, following, likeCount, postCount, verified, email, phoneNumber, twitterConnected, includePromoted, privateAccount, repostsEnabled, edition, acceptsOutOfNetworkConversations, profileBackground, authoredPostCount, sectionTitle, disableAddressBook, hiddenEmail, hiddenPhoneNumber, hiddenTwitter, loopCount, notifyPosts);
          u.sectionId = sectionId;
          if (followApprovalPending) {
              u.setApprovalPending();
                    }
          if (followRequested) {
              u.setFollowRequested();
                    }
          if (emailVerified) {
              u.setEmailVerified();
                    }
          if (phoneVerified) {
              u.setPhoneVerified();
              return u;
                    }
          return u;
                }

      private static VineLogin parseSignUp(JsonParser p) throws IOException {
          long userId = 0;
          String edition = null;
          JsonToken t = p.nextToken();
          String sessionKey = null;
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                                break;
                            case VALUE_NUMBER_INT:
                      if ("userId".equals(p.getCurrentName())) {
                          userId = p.getLongValue();
                                } else {
                                    continue;
                                }
                      t = p.nextToken();
                                break;
                            case VALUE_STRING:
                      if (VineService.EXTRA_KEY.equals(p.getCurrentName())) {
                          sessionKey = p.getText();
                                    continue;
                                } else if ("edition".equals(p.getCurrentName())) {
                          edition = p.getText();
                                }
                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
              p.skipChildren();
              t = p.nextToken();
                    }
          VineLogin vl = new VineLogin(sessionKey, null, userId, edition);
          return vl;
                }

      public static TwitterUser parseTwitterUser(JsonParser p) throws IOException {
          String name = null;
          String screenName = null;
          String location = null;
          String description = null;
          String url = null;
          String profileUrl = null;
          boolean defaultProfileImage = false;
          long userId = 0;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      if ("id".equals(p.getCurrentName())) {
                          userId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                      String cn = p.getCurrentName();
                      if ("name".equals(cn)) {
                          name = p.getText();
                                } else if ("screen_name".equals(cn)) {
                          screenName = p.getText();
                                } else if ("profile_image_url".equals(cn)) {
                          String profileUrl2 = p.getText();
                          profileUrl = profileUrl2.replace("_normal", "_bigger");
                                } else if (PlusShare.KEY_CALL_TO_ACTION_URL.equals(cn)) {
                          url = p.getText();
                                } else if ("description".equals(cn)) {
                          description = p.getText();
                                } else if ("location".equals(cn)) {
                          location = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      if ("default_profile_image".equals(p.getCurrentName())) {
                          defaultProfileImage = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new TwitterUser(name, screenName, location, description, url, profileUrl, defaultProfileImage, userId);
                }

      public static JSONObject parseClientProfile(JsonParser p) throws IOException {
          SLog.m446d("Parse client profile.");
          JsonToken t = p.nextToken();
          JSONObject object = new JSONObject();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                                try {
                          object.put(p.getCurrentName(), p.getIntValue());
                                } catch (JSONException e) {
                          SLog.m447d("Failed to parse: {}.", p.getCurrentName());
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                                try {
                          object.put(p.getCurrentName(), p.getBooleanValue());
                                } catch (JSONException e2) {
                          SLog.m447d("Failed to parse: {}.", p.getCurrentName());
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          SLog.m447d("Parsed client profile: ", object);
          return object;
                }

      public static VineLogin parseLogin(JsonParser p) throws IOException {
          String key = null;
          String username = null;
          String edition = null;
          long userId = 0;
          boolean success = false;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      if ("data".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case VALUE_NUMBER_INT:
                                      if ("userId".equals(p.getCurrentName())) {
                                          userId = p.getLongValue();
                                                }
                                                break;
                                            case VALUE_STRING:
                                      String name = p.getCurrentName();
                                      if (VineService.EXTRA_KEY.equals(name)) {
                                          key = p.getText();
                                                } else if ("username".equals(name)) {
                                          username = p.getText();
                                                } else if ("edition".equals(name)) {
                                          edition = p.getText();
                                                }
                                                break;
                                        }
                              t2 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
                          success = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          if (success) {
              return new VineLogin(key, username, userId, edition);
                    }
                    return null;
                }

      public static VineError parseError(JsonParser p) throws IOException {
          int error = 0;
          String reason = null;
          String data = null;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      if ("code".equals(p.getCurrentName())) {
                          error = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
                      if ("error".equals(p.getCurrentName())) {
                          reason = p.getText();
                                } else if ("data".equals(p.getCurrentName())) {
                          data = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VineError(error, reason, data);
                }

      public static VineError parseMessageError(JsonParser p) throws IOException {
          int error = 0;
          String reason = null;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      if ("code".equals(p.getCurrentName())) {
                          error = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
                      if ("message".equals(p.getCurrentName())) {
                          reason = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VineError(error, reason);
                }

      public static VineSingleNotification parsePushNotification(JsonParser p) throws IOException {
          if (p.nextToken() == JsonToken.START_OBJECT) {
              return parseNotification(p);
                    }
                    return null;
                }

      public static ArrayList<VineRTCConversation> parseRTCEvent(JsonParser p) throws IOException {
          ArrayList<VineRTCConversation> event = null;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      if ("data".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
                                      if ("conversations".equals(p.getCurrentName())) {
                                          event = parseRTCConversations(p);
                                                }
                                                break;
                                        }
                              t2 = p.nextToken();
                                    }
                                }
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                        }
              t = p.nextToken();
                    }
          return event;
                }

      public static ArrayList<VineRTCConversation> parseRTCConversations(JsonParser p) throws IOException {
          ArrayList<VineRTCConversation> conversations = new ArrayList<>();
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      long conversationId = Long.parseLong(p.getCurrentName());
                      ArrayList<VineRTCParticipant> participants = parseRTCParticipants(p);
                      conversations.add(new VineRTCConversation(conversationId, participants));
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                        }
              t = p.nextToken();
                    }
          return conversations;
                }

      public static ArrayList<VineRTCParticipant> parseRTCParticipants(JsonParser p) throws IOException {
          ArrayList<VineRTCParticipant> participants = new ArrayList<>();
          boolean connected = false;
          boolean typing = false;
          long lastMessageId = -1;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      long userId = Long.parseLong(p.getCurrentName());
                      JsonToken t2 = p.nextToken();
                      while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                    switch (t2) {
                                        case VALUE_NUMBER_INT:
                                  if ("last_message_id".equals(p.getCurrentName())) {
                                      lastMessageId = p.getLongValue();
                                            }
                                            break;
                                        case VALUE_TRUE:
                                        case VALUE_FALSE:
                                  String cn = p.getCurrentName();
                                  if ("connected".equals(cn)) {
                                      connected = p.getBooleanValue();
                                            } else if (VineWebSocketClient.PARAM_TYPING.equals(cn)) {
                                      typing = p.getBooleanValue();
                                            }
                                            break;
                                    }
                          t2 = p.nextToken();
                                }
                      participants.add(new VineRTCParticipant(userId, connected, typing, lastMessageId));
                                break;
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                        }
              t = p.nextToken();
                    }
          return participants;
                }

      private static VineConversation parseConversation(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          long conversationId = 0;
          long createdBy = 0;
          long lastMessage = 0;
          long unreadMessageCount = 0;
          ArrayList<VinePrivateMessage> messages = null;
          ArrayList<Long> users = null;
          int networkType = 1;
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      String name = p.getCurrentName();
                      if ("messages".equals(name)) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                              VinePrivateMessage message = parsePrivateMessage(p);
                              if (messages == null) {
                                  messages = new ArrayList<>();
                                        }
                              if (messages != null) {
                                  messages.add(message);
                                        }
                              t2 = p.nextToken();
                                    }
                                } else if ("users".equals(name)) {
                          JsonToken t3 = p.nextToken();
                          while (t3 != null && t3 != JsonToken.END_ARRAY) {
                                        switch (t3) {
                                            case VALUE_NUMBER_INT:
                                      long userId = p.getLongValue();
                                      if (users == null) {
                                          users = new ArrayList<>();
                                                }
                                      if (users != null) {
                                          users.add(Long.valueOf(userId));
                                                }
                                                break;
                                        }
                              t3 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                      String cn = p.getCurrentName();
                      if ("conversationId".equals(cn)) {
                          conversationId = p.getLongValue();
                                } else if ("lastMessage".equals(cn)) {
                          lastMessage = p.getLongValue();
                                } else if ("lastMessageRead".equals(cn)) {
                          p.getLongValue();
                                } else if ("unreadMessageCount".equals(cn)) {
                          unreadMessageCount = p.getLongValue();
                                } else if ("createdBy".equals(cn)) {
                          createdBy = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                      if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
                          networkType = 2;
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      if (Vine.MessagesColumns.DELETED.equals(p.getCurrentName())) {
                          p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          VineConversation result = new VineConversation(conversationId, lastMessage, createdBy, messages, users, networkType, unreadMessageCount);
          return result;
                }

      private static VineNotificationSetting parseNotificationSetting(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          boolean isBooleanSetting = false;
          String name = null;
          String title = null;
          String section = null;
          String value = null;
          ArrayList<Pair<String, String>> choices = new ArrayList<>();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      if ("choices".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                              String choiceTitle = null;
                              String choiceValue = null;
                              if (t2 == JsonToken.START_OBJECT) {
                                  JsonToken t3 = p.nextToken();
                                  while (t3 != null && t3 != JsonToken.END_OBJECT) {
                                      if ("title".equals(p.getCurrentName())) {
                                          choiceTitle = p.getText();
                                                } else if (Vine.Settings.PROP_VALUE.equals(p.getCurrentName())) {
                                          choiceValue = p.getText();
                                                }
                                      t3 = p.nextToken();
                                            }
                                  if (TextUtils.isEmpty(choiceTitle) && !TextUtils.isEmpty(choiceValue)) {
                                      choiceTitle = choiceValue;
                                            }
                                  choices.add(new Pair<>(choiceTitle, choiceValue));
                                        }
                              t2 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                      if ("boolean".equals(p.getCurrentName())) {
                          if (p.getIntValue() != 1) {
                                        isBooleanSetting = false;
                                    } else {
                                        isBooleanSetting = true;
                                    }
                                }
                                break;
                            case VALUE_STRING:
                      if ("title".equals(p.getCurrentName())) {
                          title = p.getText();
                                } else if ("section".equals(p.getCurrentName())) {
                          section = p.getText();
                                } else if (Vine.Settings.PROP_VALUE.equals(p.getCurrentName())) {
                          value = p.getText();
                                } else if ("name".equals(p.getCurrentName())) {
                          name = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          VineNotificationSetting result = new VineNotificationSetting(isBooleanSetting, name, title, section, value, choices);
          return result;
                }

      public static VinePrivateMessage parsePrivateMessage(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          long conversationId = 0;
          long messageId = 0;
          long userId = 0;
          long created = 0;
          String message = null;
          String videoUrl = null;
          String thumbnailUrl = null;
          boolean vanished = false;
          boolean ephemeral = false;
          VinePost post = null;
          int maxLoops = Integer.MAX_VALUE;
          long vanishDate = -1;
          int networkType = 1;
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      String name = p.getCurrentName();
                      if ("post".equals(name)) {
                          post = parsePost(p);
                          videoUrl = post.videoUrl;
                          thumbnailUrl = post.thumbnailUrl;
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
                      String name2 = p.getCurrentName();
                      if ("videoUrls".equals(name2)) {
                          List<VineVideoUrlTier> urls = parseVideoUrls(p);
                          videoUrl = getHighestH264Url(urls);
                          if (TextUtils.isEmpty(videoUrl)) {
                              videoUrl = getHighestWebmUrl(urls);
                                    }
                                }
                                break;
                            case VALUE_NUMBER_INT:
                      String name3 = p.getCurrentName();
                      if ("conversationId".equals(name3)) {
                          conversationId = p.getLongValue();
                                } else if ("messageId".equals(name3)) {
                          messageId = p.getLongValue();
                                } else if ("userId".equals(name3)) {
                          userId = p.getLongValue();
                                } else if ("maxLoops".equals(name3)) {
                          maxLoops = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
                      String name4 = p.getCurrentName();
                      if ("message".equals(name4)) {
                          message = p.getText();
                                } else if ("videoUrl".equals(name4)) {
                          videoUrl = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name4)) {
                          thumbnailUrl = p.getText();
                                } else if ("created".equals(name4)) {
                          created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("vanishedDate".equals(name4)) {
                          vanishDate = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
                          networkType = 2;
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      String name5 = p.getCurrentName();
                      if (Vine.MessagesColumns.VANISHED.equals(name5)) {
                          vanished = p.getBooleanValue();
                                } else if (Vine.MessagesColumns.EPHEMERAL.equals(name5)) {
                          ephemeral = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VinePrivateMessage(-1L, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, false, vanished, maxLoops, vanishDate, ephemeral, 0L, post, 0, (String) null);
                }

      private static String getHighestH264Url(List<VineVideoUrlTier> urls) {
          if (urls != null && urls.size() > 0) {
              VineVideoUrlTier topTier = null;
                        for (int i = 0; i < urls.size(); i++) {
                  if (VineVideoUrlTier.FORMAT_H264.equals(urls.get(i).format) && (topTier == null || urls.get(i).rate > topTier.rate)) {
                      VineVideoUrlTier topTier2 = urls.get(i);
                                topTier = topTier2;
                            }
                        }
              if (topTier != null) {
                  SLog.m447d("ryango highest h264 {}", topTier.url);
                  return topTier.url;
                        }
                    }
                    return null;
                }

      private static String getHighestWebmUrl(List<VineVideoUrlTier> urls) {
          if (urls != null && urls.size() > 0) {
              VineVideoUrlTier topTier = null;
                        for (int i = 0; i < urls.size(); i++) {
                  if (VineVideoUrlTier.FORMAT_WEBM.equals(urls.get(i).format) && (topTier == null || urls.get(i).rate > topTier.rate)) {
                      VineVideoUrlTier topTier2 = urls.get(i);
                                topTier = topTier2;
                            }
                        }
              if (topTier != null) {
                  SLog.m447d("ryango highest webm {}", topTier.url);
                  return topTier.url;
                        }
                    }
                    return null;
                }

      private static VineSingleNotification parseNotification(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          String avatarUrl = null;
          String username = null;
          String thumbnailUrl = null;
          String comment = null;
          long notificationId = 0;
          long userId = 0;
          long postId = 0;
          long createdAt = 0;
          long conversationId = 0;
          long unreadMessageCount = 0;
          long recipientUserId = 0;
          boolean verified = false;
          int notificationTypeId = 0;
          boolean privateAccount = false;
          boolean followRequested = false;
          boolean following = false;
          ArrayList<VineEntity> entities = null;
          String onboard = null;
          String url = null;
          String title = null;
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      if ("entities".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                              VineEntity entity = parseEntity(p);
                              if (entities == null) {
                                  entities = new ArrayList<>();
                                        }
                              if (entity != null) {
                                  entities.add(entity);
                                        }
                              t2 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                      String name = p.getCurrentName();
                      if ("notificationTypeId".equals(name)) {
                          notificationTypeId = p.getIntValue();
                                } else if ("notificationId".equals(name)) {
                          notificationId = p.getLongValue();
                                } else if ("userId".equals(name)) {
                          userId = p.getLongValue();
                                } else if ("postId".equals(name)) {
                          postId = p.getLongValue();
                                } else if ("verified".equals(name)) {
                          verified = p.getIntValue() == 1;
                                } else if ("private".equals(name)) {
                          privateAccount = p.getIntValue() == 1;
                                } else if ("followRequested".equals(name)) {
                          followRequested = p.getIntValue() == 1;
                                } else if ("following".equals(name)) {
                          following = p.getIntValue() == 1;
                                } else if ("conversationId".equals(name)) {
                          conversationId = p.getLongValue();
                                } else if ("unreadMessageCount".equals(name)) {
                          unreadMessageCount = p.getLongValue();
                                } else if ("recipientUserId".equals(name)) {
                          recipientUserId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                      String name2 = p.getCurrentName();
                      if ("avatarUrl".equals(name2)) {
                          avatarUrl = p.getText();
                                } else if ("body".equals(name2)) {
                          username = Util.getUsernameFromActivity(p.getText());
                          comment = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name2)) {
                          thumbnailUrl = p.getText();
                                } else if ("created".equals(name2)) {
                          createdAt = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("onboard".equals(name2)) {
                          onboard = p.getText();
                                } else if (PlusShare.KEY_CALL_TO_ACTION_URL.equals(name2)) {
                          url = p.getText();
                                } else if ("title".equals(name2)) {
                          title = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      String name3 = p.getCurrentName();
                      if ("followRequested".equals(name3)) {
                          followRequested = p.getBooleanValue();
                                } else if ("following".equals(name3)) {
                          following = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          VineSingleNotification result = new VineSingleNotification(avatarUrl, username, thumbnailUrl, comment, notificationId, userId, postId, createdAt, verified, notificationTypeId, conversationId, entities, unreadMessageCount, recipientUserId, onboard, url, title);
          if (entities != null) {
              Util.validateAndFixEntities(entities);
                    }
          if (following) {
              result.setFollowing();
                    }
          if (followRequested) {
              result.setFollowRequested();
                    }
          if (privateAccount) {
              result.setPrivate();
                    }
          return result;
                }

      private static VineEverydayNotification parseEverydayNotification(JsonParser p) throws IOException {
          JsonToken t = p.nextToken();
          String body = null;
          String avatarUrl = null;
          String thumbnailUrl = null;
          long notificationId = 0;
          long userId = 0;
          long postId = 0;
          long createdAt = Long.MAX_VALUE;
          boolean isNew = false;
          boolean following = false;
          int notificationTypeId = 0;
          VineUser nestedUser = null;
          VinePost nestedPost = null;
          while (t != null && t != JsonToken.END_OBJECT) {
              if (t == JsonToken.START_OBJECT) {
                  String objectName = p.getCurrentName();
                  if ("user".equals(objectName)) {
                      nestedUser = parseUser(p);
                  } else if ("post".equals(objectName)) {
                      nestedPost = parsePost(p);
                  } else {
                      p.skipChildren();
                  }
              } else if (t == JsonToken.VALUE_STRING) {
                  String name = p.getCurrentName();
                  if ("body".equals(name)) body = p.getText();
                  else if ("avatarUrl".equals(name) || "avatar_url".equals(name)) avatarUrl = p.getText();
                  else if ("thumbnailUrl".equals(name)) thumbnailUrl = p.getText();
                  else if ("created".equals(name) || "lastActivityTime".equals(name)) {
                      try {
                          createdAt = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                      } catch (RuntimeException ignored) {
                          createdAt = Long.MAX_VALUE;
                      }
                  }
              } else if (t == JsonToken.VALUE_NUMBER_INT) {
                  String name = p.getCurrentName();
                  if ("notificationId".equals(name) || "activityId".equals(name)) notificationId = p.getLongValue();
                  else if ("userId".equals(name)) userId = p.getLongValue();
                  else if ("postId".equals(name)) postId = p.getLongValue();
                  else if ("notificationTypeId".equals(name)) notificationTypeId = p.getIntValue();
                  else if ("isNew".equals(name)) isNew = p.getIntValue() == 1;
                  else if ("following".equals(name)) following = p.getIntValue() == 1;
              } else if (t == JsonToken.VALUE_TRUE || t == JsonToken.VALUE_FALSE) {
                  if ("isNew".equals(p.getCurrentName())) isNew = p.getBooleanValue();
                  else if ("following".equals(p.getCurrentName())) following = p.getBooleanValue();
              } else if (t == JsonToken.START_ARRAY || t == JsonToken.START_OBJECT) {
                  p.skipChildren();
              }
              t = p.nextToken();
          }
          VineUser user = null;
          if (nestedUser != null) {
              user = nestedUser;
              if (user.userId == 0) user.userId = userId;
              if (android.text.TextUtils.isEmpty(user.avatarUrl)) user.avatarUrl = avatarUrl;
          } else if (userId != 0 || avatarUrl != null) {
              user = new VineUser();
              user.userId = userId;
              user.avatarUrl = avatarUrl;
          }
          if (user != null && following) user.following = 1;
          VinePost post = nestedPost;
          if (post == null && (postId != 0 || thumbnailUrl != null)) {
              post = new VinePost();
              post.postId = postId;
              post.thumbnailUrl = thumbnailUrl;
          }
          if (post != null) {
              if (post.postId == 0) post.postId = postId;
              if (android.text.TextUtils.isEmpty(post.thumbnailUrl)) post.thumbnailUrl = thumbnailUrl;
          }
          String type = null;
          if (body != null) {
              String lower = body.toLowerCase(java.util.Locale.US);
              if (lower.contains("liked your post")) type = VineEverydayNotification.TYPE_LIKED;
              else if (lower.contains("reposted your post") || lower.contains("revined your post")) type = VineEverydayNotification.TYPE_REVINED;
              else if (lower.contains("following you")) type = VineEverydayNotification.TYPE_FOLLOWED;
              else if (lower.contains("comment")) type = VineEverydayNotification.TYPE_COMMENTED;
          }
          return new VineEverydayNotification(body, notificationId, type, post, user, new ArrayList<VineEntity>(), null, createdAt, isNew, 0);
      }
      public static VineMilestone parseMilestone(JsonParser p) throws IOException {
          String backgroundImageUrl = null;
          String backgroundVideoUrl = null;
          String iconUrl = null;
          String title = null;
          String description = null;
          String milestoneUrl = null;
          int overlayColor = 0;
          int blurRadius = 0;
          float overlayAlpha = 0.0f;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_NUMBER_INT:
                      if ("gaussianBlurRadius".equals(p.getCurrentName())) {
                          blurRadius = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
                      if ("backgroundImageUrl".equals(p.getCurrentName())) {
                          backgroundImageUrl = p.getText();
                                } else if ("backgroundVideoUrl".equals(p.getCurrentName())) {
                          backgroundVideoUrl = p.getText();
                                } else if ("iconUrl".equals(p.getCurrentName())) {
                          iconUrl = p.getText();
                                } else if ("title".equals(p.getCurrentName())) {
                          title = p.getText();
                                } else if ("description".equals(p.getCurrentName())) {
                          description = p.getText();
                                } else if ("milestoneUrl".equals(p.getCurrentName())) {
                          milestoneUrl = p.getText();
                                } else if ("overlayAlpha".equals(p.getCurrentName())) {
                          overlayAlpha = Float.parseFloat(p.getText());
                                } else if ("overlayColor".equals(p.getCurrentName())) {
                          overlayColor = Integer.decode(p.getText()).intValue();
                                } else if ("gaussianBlurRadius".equals(p.getCurrentName())) {
                          blurRadius = Integer.parseInt(p.getText());
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VineMilestone(backgroundImageUrl, backgroundVideoUrl, iconUrl, title, description, milestoneUrl, overlayColor, overlayAlpha, blurRadius);
                }

      public static VineEntity parseEntity(JsonParser p) throws IOException {
          String type = null;
          String link = null;
          String title = null;
          long id = 0;
          int start = -1;
          int end = -1;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_ARRAY:
                      if ("range".equals(p.getCurrentName())) {
                          JsonToken t2 = p.nextToken();
                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                                        switch (t2) {
                                            case VALUE_NUMBER_INT:
                                      if (start == -1) {
                                          start = p.getIntValue();
                                                } else {
                                          end = p.getIntValue();
                                                }
                                                break;
                                        }
                              t2 = p.nextToken();
                                    }
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                                break;
                            case VALUE_STRING:
                      String cn = p.getCurrentName();
                      if ("type".equals(cn)) {
                          type = p.getText();
                                    continue;
                                } else if ("link".equals(cn)) {
                          link = p.getText();
                                } else if ("title".equals(cn)) {
                          title = p.getText();
                                }
                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
              if ("id".equals(p.getCurrentName())) {
                  id = p.getLongValue();
                        }
              t = p.nextToken();
                    }
          return new VineEntity(type, title, link, start, end, id);
                }

      private static ServerStatus parseServerStatus(JsonParser p) throws IOException {
          String uploadType = null;
          String status = null;
          String message = null;
          String staticTimelineUrl = null;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_STRING:
                      String cn = p.getCurrentName();
                      if ("uploadType".equals(cn)) {
                          uploadType = p.getText();
                                } else if ("status".equals(cn)) {
                          status = p.getText();
                                } else if ("message".equals(cn)) {
                          message = p.getText();
                                } else if ("staticTimelineUrl".equals(cn)) {
                          staticTimelineUrl = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new ServerStatus(uploadType, status, message, null, staticTimelineUrl);
                }

      private static VineClientFlags parseClientFlags(JsonParser p) throws IOException {
          String messageTitle = null;
          String messageText = null;
          String apiHost = null;
          String rtcHost = null;
          String mediaHost = null;
          String exploreHost = null;
          long ttlSeconds = 0;
          boolean doubleTapToLikeOnPause = true;
          boolean prefetchEnabled = false;
          boolean useVinePlayer = SdkVideoView.getDefaultGlobalVinePlayer();
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      String cn = p.getCurrentName();
                      if ("doubleTapToLikeOnPause".equals(cn)) {
                          doubleTapToLikeOnPause = p.getIntValue() == 1;
                                } else if ("prefetchEnabled".equals(cn)) {
                          prefetchEnabled = p.getIntValue() == 1;
                                } else if ("useVinePlayer".equals(cn)) {
                          useVinePlayer = p.getIntValue() == 1;
                                } else if ("ttl_s".equals(p.getCurrentName())) {
                          ttlSeconds = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
                      String cn2 = p.getCurrentName();
                      if ("messageTitle".equals(cn2)) {
                          messageTitle = p.getText();
                                } else if ("messageText".equals(cn2)) {
                          messageText = p.getText();
                                } else if ("apiHost".equals(cn2)) {
                          apiHost = p.getText();
                                } else if ("rtcHost".equals(cn2)) {
                          rtcHost = p.getText();
                                } else if ("mediaHost".equals(cn2)) {
                          mediaHost = p.getText();
                                } else if ("exploreHost".equals(cn2)) {
                          exploreHost = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                      String cn3 = p.getCurrentName();
                      if ("doubleTapToLikeOnPause".equals(cn3)) {
                          doubleTapToLikeOnPause = p.getBooleanValue();
                                } else if ("prefetchEnabled".equals(cn3)) {
                          prefetchEnabled = p.getBooleanValue();
                                } else if ("useVinePlayer".equals(cn3)) {
                          useVinePlayer = p.getBooleanValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VineClientFlags(messageTitle, messageText, apiHost, rtcHost, mediaHost, exploreHost, ttlSeconds, doubleTapToLikeOnPause, prefetchEnabled, useVinePlayer);
                }

      private static VinePostResponse parseVinePostResponse(JsonParser p) throws IOException {
          long postId = 0;
          long createdAt = 0;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                      String cn = p.getCurrentName();
                      if ("postId".equals(cn)) {
                          postId = p.getLongValue();
                                } else if ("createdAt".equals(cn)) {
                          createdAt = p.getLongValue();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VinePostResponse(postId, createdAt);
                }

      private static VinePrivateMessagePostResponseWithUsers parsePrivateMessageResponse(JsonParser p) throws IOException {
          ArrayList<VinePrivateMessageResponse> responses = new ArrayList<>();
          ArrayList<VineRecipient> recipients = new ArrayList<>();
          ArrayList<VineUser> users = null;
          VinePost post = null;
          int networkType = 1;
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                      p.skipChildren();
                                break;
                            case START_ARRAY:
                      if ("messages".equals(p.getCurrentName())) {
                          while (t != null && t != JsonToken.END_ARRAY) {
                                        switch (t) {
                                            case START_OBJECT:
                                      VineError error = null;
                                      String videoUrl = null;
                                      String thumbnailUrl = null;
                                      String shareUrl = null;
                                      String email = null;
                                      String phone = null;
                                      String recipientIdString = null;
                                      String videoWebmUrl = null;
                                      long conversationId = 0;
                                      long messageId = 0;
                                      long userId = -1;
                                      while (t != null && t != JsonToken.END_OBJECT) {
                                          String name = p.getCurrentName();
                                                    switch (t) {
                                                        case START_OBJECT:
                                                  String objName = p.getCurrentName();
                                                  if ("to".equals(objName)) {
                                                      while (t != null && t != JsonToken.END_OBJECT) {
                                                          String toName = p.getCurrentName();
                                                                    switch (t) {
                                                                        case VALUE_NUMBER_INT:
                                                                  if (toName.equals("userId")) {
                                                                      userId = p.getLongValue();
                                                                            }
                                                                            break;
                                                                        case VALUE_STRING:
                                                                  if (toName.equals("email")) {
                                                                      email = p.getText();
                                                                            } else if (toName.equals(VineRecipient.KEY_PHONE)) {
                                                                      phone = p.getText();
                                                                            } else if (toName.equals(VineRecipient.KEY_RECIPIENT_ID)) {
                                                                      recipientIdString = p.getText();
                                                                            }
                                                                            break;
                                                                    }
                                                          t = p.nextToken();
                                                                }
                                                            } else if ("error".equals(objName)) {
                                                      error = parseMessageError(p);
                                                            } else if ("post".equals(objName)) {
                                                      post = parsePost(p);
                                                            }
                                                            break;
                                                        case START_ARRAY:
                                                  if ("videoUrls".equals(p.getCurrentName())) {
                                                      List<VineVideoUrlTier> urls = parseVideoUrls(p);
                                                      videoUrl = getHighestH264Url(urls);
                                                      videoWebmUrl = getHighestWebmUrl(urls);
                                                      if (TextUtils.isEmpty(videoUrl)) {
                                                          videoUrl = videoWebmUrl;
                                                                }
                                                            }
                                                            break;
                                                        case VALUE_NUMBER_INT:
                                                  if ("conversationId".equals(name)) {
                                                      conversationId = p.getLongValue();
                                                            } else if ("messageId".equals(name)) {
                                                      messageId = p.getLongValue();
                                                            }
                                                            break;
                                                        case VALUE_STRING:
                                                  if ("videoUrl".equals(name)) {
                                                      videoUrl = p.getText();
                                                            } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name)) {
                                                      thumbnailUrl = p.getText();
                                                            } else if ("shareUrl".equals(name)) {
                                                      shareUrl = p.getText();
                                                            } else if ("videoWebmUrl".equals(name)) {
                                                      videoWebmUrl = p.getText();
                                                            }
                                                            break;
                                                    }
                                          t = p.nextToken();
                                                }
                                      long recipientId = -1;
                                      if (!TextUtils.isEmpty(recipientIdString)) {
                                          recipientId = Long.valueOf(recipientIdString).longValue();
                                                }
                                      VineRecipient recipient = null;
                                      if (email != null) {
                                          recipient = VineRecipient.fromEmail(null, userId, email, recipientId);
                                                } else if (phone != null) {
                                          recipient = VineRecipient.fromPhone(null, userId, phone, recipientId);
                                                } else if (userId > 0) {
                                          recipient = VineRecipient.fromUser(null, userId, 0, recipientId);
                                                } else {
                                          CrashUtil.log("No email, phone, or userId for recipient for message {}", Long.valueOf(messageId));
                                                }
                                      if (TextUtils.isEmpty(videoUrl)) {
                                          videoUrl = videoWebmUrl;
                                                }
                                      recipients.add(recipient);
                                      responses.add(new VinePrivateMessageResponse(recipient, conversationId, messageId, videoUrl, thumbnailUrl, shareUrl, error, networkType));
                                                break;
                                        }
                              t = p.nextToken();
                                    }
                                } else if ("users".equals(p.getCurrentName())) {
                          users = parseUsersArray(p, null);
                                } else {
                          p.skipChildren();
                                }
                                break;
                            case VALUE_STRING:
                      if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
                          networkType = 2;
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return new VinePrivateMessagePostResponseWithUsers(responses, recipients, users, post);
                }

      public static ArrayList<ComplaintMenuOption> parseComplaintMenu(JsonParser p) throws IOException {
          ArrayList<ComplaintMenuOption> complaintMenuOptions = new ArrayList<>();
          JsonToken t = p.nextToken();
          while (t != null && t != JsonToken.END_OBJECT) {
              p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
                      String type = p.getCurrentName();
                      ComplaintMenuOption c = parseComplaintMenuOption(p, type);
                      complaintMenuOptions.add(c);
                                break;
                        }
              t = p.nextToken();
                    }
          return complaintMenuOptions;
                }

      public static ComplaintMenuOption parseComplaintMenuOption(JsonParser p, String type) throws IOException {
          ComplaintMenuOption option = new ComplaintMenuOption();
          option.complaintType = type;
          JsonToken t = p.nextToken();
          while (t != JsonToken.END_OBJECT) {
              String tokenName = p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
                      if ("choices".equals(tokenName)) {
                          option.complaintChoices = parseComplaintChoiceArray(p);
                                } else if ("prompt".equals(tokenName)) {
                          p.nextToken();
                          option.prompt = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return option;
                }

      public static ComplaintMenuOption.ComplaintChoice[] parseComplaintChoiceArray(JsonParser p) throws IOException {
          ArrayList<ComplaintMenuOption.ComplaintChoice> choices = new ArrayList<>();
          JsonToken t = p.nextToken();
          while (t != JsonToken.END_ARRAY) {
              p.getCurrentName();
                        switch (t) {
                            case START_OBJECT:
                      ComplaintMenuOption.ComplaintChoice choice = parseComplaintChoice(p);
                      choices.add(choice);
                                break;
                        }
              t = p.nextToken();
                    }
          return (ComplaintMenuOption.ComplaintChoice[]) choices.toArray(new ComplaintMenuOption.ComplaintChoice[choices.size()]);
                }

      public static ComplaintMenuOption.ComplaintChoice parseComplaintChoice(JsonParser p) throws IOException {
          ComplaintMenuOption.ComplaintChoice choice = new ComplaintMenuOption.ComplaintChoice();
          JsonToken t = p.nextToken();
          while (t != JsonToken.END_OBJECT) {
              String tokenName = p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
                      if ("title".equals(tokenName)) {
                          p.nextToken();
                          choice.title = p.getText();
                                } else if (Vine.Settings.PROP_VALUE.equals(tokenName)) {
                          p.nextToken();
                          choice.value = p.getText();
                                } else if ("confirmation".equals(tokenName)) {
                          p.nextToken();
                          choice.confirmation = p.getText();
                                }
                                break;
                        }
              t = p.nextToken();
                    }
          return choice;
                }

      public static JsonParser createParser(InputStream inputStream) throws IOException {
          JsonParser jp = JSON_FACTORY.createJsonParser(inputStream);
          jp.nextToken();
          return jp;
                }

      public static JsonParser createParser(String inputString) throws IOException {
          JsonParser jp = JSON_FACTORY.createJsonParser(inputString);
          jp.nextToken();
          return jp;
                }
            }
