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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class VineParsers {
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

/* 32 */        static /* synthetic */ ArrayList access$000(JsonParser x0, Bundle x1) throws IOException {
/* 33 */            return parseUsersArray(x0, x1);
                }

/* 32 */        static /* synthetic */ VineUser access$100(JsonParser x0) throws IOException {
/* 33 */            return parseUser(x0);
                }

/* 32 */        static /* synthetic */ VineNotificationSetting access$1000(JsonParser x0) throws IOException {
/* 33 */            return parseNotificationSetting(x0);
                }

/* 32 */        static /* synthetic */ VineLike access$200(JsonParser x0) throws IOException {
/* 33 */            return parseLike(x0);
                }

/* 32 */        static /* synthetic */ VinePost access$300(JsonParser x0) throws IOException {
/* 33 */            return parsePost(x0);
                }

/* 32 */        static /* synthetic */ VineComment access$400(JsonParser x0) throws IOException {
/* 33 */            return parseComment(x0);
                }

/* 32 */        static /* synthetic */ VineSingleNotification access$500(JsonParser x0) throws IOException {
/* 33 */            return parseNotification(x0);
                }

/* 32 */        static /* synthetic */ VineEverydayNotification access$600(JsonParser x0) throws IOException {
/* 33 */            return parseEverydayNotification(x0);
                }

/* 32 */        static /* synthetic */ VineTag access$700(JsonParser x0) throws IOException {
/* 33 */            return parseTag(x0);
                }

/* 32 */        static /* synthetic */ VineChannel access$800(JsonParser x0) throws IOException {
/* 33 */            return parseChannel(x0);
                }

/* 32 */        static /* synthetic */ VineConversation access$900(JsonParser x0) throws IOException {
/* 33 */            return parseConversation(x0);
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$1 */
/* 61 */        static class C03351 extends RecordListParser<VineUser> {
/* 62 */            C03351() {
                    }

                    @Override // co.vine.android.api.VineParsers.RecordListParser
/* 66 */            public ArrayList<VineUser> parse(JsonParser parser, Bundle extras) throws IOException {
/* 67 */                return VineParsers.access$000(parser, extras);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$2 */
/* 70 */        static class C03402 extends RecordParser<VineUser> {
/* 71 */            C03402() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 75 */            public VineUser parse(JsonParser parser, Bundle extras) throws IOException {
/* 76 */                return VineParsers.access$100(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$3 */
/* 78 */        static class C03413 extends RecordParser<VineLike> {
/* 79 */            C03413() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 83 */            public VineLike parse(JsonParser parser, Bundle extras) throws IOException {
/* 84 */                return VineParsers.access$200(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$4 */
/* 86 */        static class C03424 extends RecordParser<VinePost> {
/* 87 */            C03424() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 91 */            public VinePost parse(JsonParser parser, Bundle extras) throws IOException {
/* 92 */                return VineParsers.access$300(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$5 */
/* 94 */        static class C03435 extends RecordParser<VineComment> {
/* 95 */            C03435() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 99 */            public VineComment parse(JsonParser parser, Bundle extras) throws IOException {
/* 100 */               return VineParsers.access$400(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$6 */
/* 103 */       static class C03446 extends RecordParser<VineSingleNotification> {
/* 104 */           C03446() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 107 */           public VineSingleNotification parse(JsonParser parser, Bundle extras) throws IOException {
/* 108 */               return VineParsers.access$500(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$7 */
/* 111 */       static class C03457 extends RecordParser<VineEverydayNotification> {
/* 112 */           C03457() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 115 */           public VineEverydayNotification parse(JsonParser parser, Bundle extras) throws IOException {
/* 116 */               return VineParsers.access$600(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$8 */
/* 118 */       static class C03468 extends RecordParser<VineTag> {
/* 119 */           C03468() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 123 */           public VineTag parse(JsonParser parser, Bundle extras) throws IOException {
/* 124 */               return VineParsers.access$700(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$9 */
/* 126 */       static class C03479 extends RecordParser<VineChannel> {
/* 127 */           C03479() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 131 */           public VineChannel parse(JsonParser parser, Bundle extras) throws IOException {
/* 132 */               return VineParsers.access$800(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$10 */
/* 134 */       static class C033610 extends RecordParser<VinePrivateMessage> {
/* 135 */           C033610() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 138 */           public VinePrivateMessage parse(JsonParser parser, Bundle extras) throws IOException {
/* 139 */               return VineParsers.parsePrivateMessage(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$11 */
/* 141 */       static class C033711 extends RecordParser<VineConversation> {
/* 142 */           C033711() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 145 */           public VineConversation parse(JsonParser parser, Bundle extras) throws IOException {
/* 146 */               return VineParsers.access$900(parser);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.api.VineParsers$12 */
/* 148 */       static class C033812 extends RecordParser<VineNotificationSetting> {
/* 149 */           C033812() {
                    }


                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // co.vine.android.api.VineParsers.RecordParser
/* 152 */           public VineNotificationSetting parse(JsonParser parser, Bundle extras) throws IOException {
/* 153 */               return VineParsers.access$1000(parser);
                    }
                }

/* 157 */       public static Object parseVineResponse(JsonParser p, int type, Bundle extras) throws IOException {
/* 158 */           if (type == 16) {
/* 159 */               return null;
                    }
/* 161 */           if (type == 15) {
/* 162 */               return parseServerStatus(p);
                    }
/* 164 */           if (type == 1) {
/* 165 */               return parseGeneral(p);
                    }
/* 168 */           JsonToken t = p.nextToken();
/* 169 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 172 */                       if (!"data".equals(p.getCurrentName())) {
                                    continue;
                                } else {
                                    switch (type) {
                                        case 2:
/* 175 */                                   return parseUser(p);
                                        case 3:
/* 178 */                                   return parsePagedData(p, USERS_PARSER, extras);
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
/* 247 */                                   p.skipChildren();
                                            break;
                                        case 5:
/* 184 */                                   return parseSignUp(p);
                                        case 6:
/* 187 */                                   return parsePagedData(p, COMMENT_PARSER);
                                        case 7:
/* 193 */                                   return parseComment(p);
                                        case 8:
/* 190 */                                   return parsePagedData(p, POST_PARSER);
                                        case 9:
/* 196 */                                   return parsePagedData(p, ACTIVITY_PARSER);
                                        case 10:
/* 202 */                                   return parseLikeId(p);
                                        case 11:
/* 205 */                                   return parsePagedData(p, LIKES_PARSER);
                                        case 12:
/* 208 */                                   return parseVinePostResponse(p);
                                        case 13:
/* 181 */                                   return parseClientProfile(p);
                                        case 14:
/* 214 */                                   return parsePagedData(p, TAG_PARSER);
                                        case 18:
/* 217 */                                   return parsePagedData(p, CHANNELS_PARSER);
                                        case 19:
/* 220 */                                   return parseRepostResponse(p);
                                        case 22:
/* 229 */                                   return parsePagedData(p, MESSAGES_PARSER);
                                        case 23:
/* 226 */                                   return parsePrivateMessage(p);
                                        case 24:
/* 211 */                                   return parsePrivateMessageResponse(p);
                                        case 25:
/* 223 */                                   return parsePagedData(p, CONVERSATIONS_PARSER);
                                        case 26:
/* 232 */                                   return parseActivityCounts(p);
                                        case 27:
/* 235 */                                   return parseLoopsSubmission(p);
                                        case 28:
/* 199 */                                   return parsePagedData(p, GROUPED_ACTIVITY_PARSER);
                                        case 29:
/* 238 */                                   return parseClientFlags(p);
                                        case 30:
/* 241 */                                   return parsePagedData(p, NOTIFICATION_SETTING_PARSER);
                                        case 35:
/* 244 */                                   return parseComplaintMenu(p);
                                    }
                                }
                                break;
                            case START_ARRAY:
/* 254 */                       if ("data".equals(p.getCurrentName())) {
                                    switch (type) {
                                        case 3:
/* 257 */                                   return parseUsersArray(p, extras);
                                        case 8:
/* 258 */                                   return parseRecordArray(p, POST_PARSER, extras);
                                        case 25:
/* 259 */                                   return parseRecordArray(p, CONVERSATIONS_PARSER, extras);
                                        case 28:
/* 260 */                                   return parseRecordArray(p, GROUPED_ACTIVITY_PARSER, extras);
                                        case 20:
/* 261 */                                   return parseEditions(p);
                                        default:
/* 263 */                                   p.skipChildren();
                                            break;
                                    }
                                } else {
                                    continue;
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 270 */                       if ("data".equals(p.getCurrentName())) {
                                    switch (type) {
                                        case 21:
/* 273 */                                   return Integer.valueOf(p.getIntValue());
                                    }
                                }
                                continue;
                        }
/* 280 */               t = p.nextToken();
                    }
/* 282 */           throw new IllegalArgumentException("Unhandled parse type " + type);
                }

/* 285 */       private static VineLoopSubmissionResponse parseLoopsSubmission(JsonParser p) throws IOException {
/* 286 */           int submissionInterval = 0;
/* 288 */           JsonToken t = p.nextToken();
/* 289 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 301 */                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 293 */                       String cn = p.getCurrentName();
/* 294 */                       if ("nextAfter".equals(cn)) {
/* 295 */                           submissionInterval = p.getIntValue();
                                }
                                break;
                        }
/* 290 */               t = p.nextToken();
                    }
/* 307 */           return new VineLoopSubmissionResponse(submissionInterval);
                }

/* 310 */       private static Object parseEditions(JsonParser p) throws IOException {
/* 311 */           ArrayList<Pair<String, String>> editions = new ArrayList<>();
/* 313 */           JsonToken t = p.nextToken();
/* 314 */           String editionId = null;
/* 315 */           while (t != null && t != JsonToken.END_OBJECT) {
/* 316 */               if (t == JsonToken.START_OBJECT) {
/* 317 */                   JsonToken t2 = p.nextToken();
/* 318 */                   while (t2 != null && t2 != JsonToken.END_OBJECT) {
/* 319 */                       if (t2 == JsonToken.VALUE_STRING) {
/* 320 */                           if (PARAM_EDITION_ID.equals(p.getCurrentName())) {
/* 321 */                               editionId = p.getText();
                                    } else if ("name".equals(p.getCurrentName())) {
/* 323 */                               editions.add(new Pair<>(editionId, p.getText()));
                                    }
                                }
/* 326 */                       t2 = p.nextToken();
                            }
                        }
/* 329 */               t = p.nextToken();
                    }
/* 331 */           return editions;
                }

/* 334 */       private static Object parseRepostResponse(JsonParser p) throws IOException {
/* 335 */           long repostId = 0;
/* 336 */           long postId = 0;
/* 338 */           JsonToken t = p.nextToken();
/* 339 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 352 */                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 342 */                       String cn = p.getCurrentName();
/* 343 */                       if ("repostId".equals(cn)) {
/* 344 */                           repostId = p.getLongValue();
                                } else if ("postId".equals(cn)) {
/* 346 */                           postId = p.getLongValue();
                                }
                                break;
                        }
/* 356 */               t = p.nextToken();
                    }
/* 359 */           VineRepost vr = new VineRepost();
/* 360 */           vr.repostId = repostId;
/* 361 */           vr.postId = postId;
/* 362 */           return vr;
                }

/* 365 */       private static VineActivityCounts parseActivityCounts(JsonParser p) throws IOException {
/* 366 */           int messages = 0;
/* 367 */           int notifications = 0;
/* 369 */           JsonToken t = p.nextToken();
/* 370 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_NUMBER_INT:
/* 373 */                       String cn = p.getCurrentName();
/* 374 */                       if ("messages".equals(cn)) {
/* 375 */                           messages = p.getIntValue();
                                } else if ("notifications".equals(cn)) {
/* 377 */                           notifications = p.getIntValue();
                                }
                                break;
                        }
/* 382 */               t = p.nextToken();
                    }
/* 385 */           VineActivityCounts result = new VineActivityCounts(messages, notifications);
/* 386 */           return result;
                }

/* 391 */       private static <T extends Parcelable> VinePagedData<T> parsePagedData(JsonParser p, PagedDataParser<T> parser) throws IOException {
/* 392 */           return parsePagedData(p, parser, null);
                }

/* 398 */       private static <T extends Parcelable> VinePagedData<T> parsePagedData(JsonParser jsonParser, PagedDataParser<T> pagedDataParser, Bundle bundle) throws IOException {
/* 399 */           VinePagedData<T> vinePagedData = new VinePagedData<>();
/* 400 */           vinePagedData.items = new ArrayList<>();
/* 401 */           int i = 1;
/* 403 */           RecordParser<T> recordParser = null;
/* 404 */           RecordListParser<T> recordListParser = null;
/* 406 */           if (pagedDataParser instanceof RecordListParser) {
/* 407 */               recordListParser = (RecordListParser) pagedDataParser;
                    } else {
/* 409 */               recordParser = (RecordParser) pagedDataParser;
                    }
/* 412 */           JsonToken jsonTokenNextToken = jsonParser.nextToken();
/* 413 */           while (jsonTokenNextToken != null && jsonTokenNextToken != JsonToken.END_OBJECT) {
                        switch (jsonTokenNextToken) {
                            case START_OBJECT:
/* 416 */                       if ("channel".equals(jsonParser.getCurrentName())) {
/* 417 */                           vinePagedData.channel = parseChannel(jsonParser);
                                } else {
/* 419 */                           jsonParser.skipChildren();
                                }
                                break;
                            case START_ARRAY:
/* 458 */                       if (PARAM_RECORDS.equals(jsonParser.getCurrentName())) {
/* 459 */                           if (recordListParser != null) {
/* 460 */                               vinePagedData.items.addAll(recordListParser.parse(jsonParser, bundle));
                                    } else {
/* 462 */                               if (recordParser == null) {
/* 463 */                                   throw new RuntimeException("Invalid parser.");
                                        }
/* 465 */                               JsonToken jsonTokenNextToken2 = jsonParser.nextToken();
                                int recordIndex = 0;
/* 466 */                               while (jsonTokenNextToken2 != null && jsonTokenNextToken2 != JsonToken.END_ARRAY) {
/* 467 */                                   T t = pagedDataParser == POST_PARSER ? (T) parseFeedPost(jsonParser) : recordParser.parse(jsonParser, bundle);
/* 468 */                                   if (t != null) {
/* 469 */                                       vinePagedData.items.add(t);
                                        }
                                    recordIndex++;
/* 471 */                                   jsonTokenNextToken2 = jsonParser.nextToken();
                                        }
                                    }
                                }
/* 475 */                       if ("users".equals(jsonParser.getCurrentName())) {
/* 476 */                           JsonToken jsonTokenNextToken3 = jsonParser.nextToken();
/* 477 */                           if (vinePagedData.participants == null) {
/* 478 */                               vinePagedData.participants = new ArrayList<>();
                                    }
/* 480 */                           while (jsonTokenNextToken3 != null && jsonTokenNextToken3 != JsonToken.END_ARRAY) {
/* 481 */                               VineUser vineUser = USER_PARSER.parse(jsonParser, null);
/* 482 */                               if (vineUser != null) {
/* 483 */                                   vinePagedData.participants.add(vineUser);
                                        }
/* 485 */                               jsonTokenNextToken3 = jsonParser.nextToken();
                                    }
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 435 */                       String currentName = jsonParser.getCurrentName();
/* 436 */                       if ("count".equals(currentName)) {
/* 437 */                           vinePagedData.count = jsonParser.getIntValue();
                                } else if ("nextPage".equals(currentName)) {
/* 439 */                           vinePagedData.nextPage = jsonParser.getIntValue();
                                } else if ("previousPage".equals(currentName)) {
/* 441 */                           vinePagedData.previousPage = jsonParser.getIntValue();
                                } else if (VineService.EXTRA_SIZE.equals(currentName)) {
/* 443 */                           vinePagedData.size = jsonParser.getIntValue();
                                } else if ("anchor".equals(currentName)) {
/* 445 */                           vinePagedData.anchor = jsonParser.getLongValue();
                                } else if ("title".equals(currentName)) {
/* 447 */                           vinePagedData.title = jsonParser.getText();
                                } else if ("unreadMessageCount".equals(currentName)) {
/* 449 */                           vinePagedData.unreadMessageCount = jsonParser.getLongValue();
                                } else if ("lastMessageRead".equals(currentName)) {
/* 451 */                           vinePagedData.lastMessageRead = jsonParser.getLongValue();
                                } else if ("lastMessage".equals(currentName)) {
/* 453 */                           vinePagedData.lastMessage = jsonParser.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 425 */                       if ("anchor".equals(jsonParser.getCurrentName())) {
/* 426 */                           vinePagedData.stringAnchor = jsonParser.getText();
                                } else if ("inbox".equals(jsonParser.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(jsonParser.getValueAsString())) {
/* 429 */                           i = 2;
                                }
                                break;
                        }
/* 491 */               jsonTokenNextToken = jsonParser.nextToken();
                    }
/* 494 */           vinePagedData.networkType = i;
/* 495 */           return vinePagedData;
                }

/* 497 */      private static <T extends Parcelable> VinePagedData<T> parseRecordArray(JsonParser parser, RecordParser<T> recordParser, Bundle extras) throws IOException {
/* 498 */          VinePagedData<T> result = new VinePagedData<>();
/* 499 */          result.items = new ArrayList<>();
/* 500 */          JsonToken token = parser.nextToken();
/* 501 */          while (token != null && token != JsonToken.END_ARRAY) {
/* 502 */              T item = recordParser.parse(parser, extras);
/* 503 */              if (item != null) {
/* 504 */                  result.items.add(item);
                    }
/* 506 */              token = parser.nextToken();
                }
/* 508 */          result.count = result.items.size();
/* 509 */          result.size = result.items.size();
/* 510 */          return result;
            }

/* 499 */       private static VineLike parseLike(JsonParser p) throws IOException {
/* 500 */           VineLike vineLike = new VineLike();
/* 501 */           JsonToken t = p.nextToken();
/* 502 */           int following = -1;
/* 503 */           int blocked = 0;
/* 505 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 508 */                       if ("user".equals(p.getCurrentName())) {
/* 509 */                           vineLike.user = parseUser(p);
                                }
                                break;
                            case START_ARRAY:
/* 514 */                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 531 */                       String cn = p.getCurrentName();
/* 532 */                       if ("likeId".equals(cn)) {
/* 533 */                           vineLike.likeId = p.getLongValue();
                                } else if ("userId".equals(cn)) {
/* 535 */                           vineLike.userId = p.getLongValue();
                                } else if ("verified".equals(cn)) {
/* 537 */                           vineLike.verified = p.getIntValue();
                                } else if ("following".equals(cn)) {
/* 539 */                           following = p.getIntValue();
                                } else if ("blocked".equals(cn)) {
/* 541 */                           blocked = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
/* 518 */                       String cn2 = p.getCurrentName();
/* 519 */                       if ("created".equals(cn2)) {
/* 520 */                           vineLike.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn2)) {
/* 522 */                           vineLike.location = p.getText();
                                } else if ("avatarUrl".equals(cn2)) {
/* 524 */                           vineLike.avatarUrl = p.getText();
                                } else if ("username".equals(cn2)) {
/* 526 */                           vineLike.username = p.getText();
                                }
                                break;
                        }
/* 550 */               t = p.nextToken();
                    }
/* 553 */           if (vineLike.user != null) {
/* 554 */               vineLike.user.following = following;
/* 555 */               vineLike.user.blocked = blocked;
                    }
/* 558 */           return vineLike;
                }

/* 561 */       private static VineByline parseByline(JsonParser p) throws IOException {
/* 562 */           String iconUrl = null;
/* 563 */           String body = null;
/* 564 */           ArrayList<VineEntity> entities = new ArrayList<>();
/* 566 */           JsonToken t = p.nextToken();
/* 568 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 571 */                       p.skipChildren();
                                break;
                            case START_ARRAY:
/* 575 */                       if ("entities".equals(p.getCurrentName())) {
/* 576 */                           JsonToken t2 = p.nextToken();
/* 577 */                           while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 578 */                               VineEntity entity = parseEntity(p);
/* 579 */                               if (entity != null) {
/* 580 */                                   entities.add(entity);
                                        }
/* 582 */                               t2 = p.nextToken();
                                    }
/* 584 */                           Util.validateAndFixEntities(entities);
                                }
                                break;
                            case VALUE_STRING:
/* 589 */                       String cn = p.getCurrentName();
/* 590 */                       if ("iconUrl".equals(cn)) {
/* 591 */                           iconUrl = p.getText();
                                } else if ("body".equals(cn)) {
/* 593 */                           body = p.getText();
                                }
                                break;
                        }
/* 598 */               t = p.nextToken();
                    }
/* 601 */           return new VineByline(body, iconUrl, entities);
                }

/* 604 */       private static VineRepost parseRepost(JsonParser p) throws IOException {
/* 605 */           String username = null;
/* 606 */           String avatarUrl = null;
/* 607 */           String location = null;
/* 608 */           String description = null;
/* 609 */           long userId = 0;
/* 610 */           long postId = 0;
/* 611 */           long repostId = 0;
/* 612 */           int verified = 0;
/* 613 */           int priv = 0;
/* 614 */           int unflaggable = 0;
/* 616 */           JsonToken t = p.nextToken();
/* 618 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 621 */                       if ("user".equals(p.getCurrentName())) {
/* 622 */                           JsonToken t2 = p.nextToken();
/* 623 */                           while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
                                            case START_ARRAY:
/* 653 */                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
/* 639 */                                       String cn = p.getCurrentName();
/* 640 */                                       if ("verified".equals(cn)) {
/* 641 */                                           verified = p.getIntValue();
                                                } else if ("private".equals(cn)) {
/* 643 */                                           priv = p.getIntValue();
                                                } else if ("unflaggable".equals(cn)) {
/* 645 */                                           unflaggable = p.getIntValue();
                                                } else if ("userId".equals(cn)) {
/* 647 */                                           userId = p.getLongValue();
                                                }
                                                break;
                                            case VALUE_STRING:
/* 626 */                                       String cn2 = p.getCurrentName();
/* 627 */                                       if ("description".equals(cn2)) {
/* 628 */                                           description = p.getText();
                                                } else if ("avatarUrl".equals(cn2)) {
/* 630 */                                           avatarUrl = p.getText();
                                                } else if ("location".equals(cn2)) {
/* 632 */                                           location = p.getText();
                                                } else if ("username".equals(cn2)) {
/* 634 */                                           username = p.getText();
                                                }
                                                break;
                                        }
/* 657 */                               t2 = p.nextToken();
                                    }
                                } else {
/* 660 */                           p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
/* 674 */                       p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 665 */                       String cn3 = p.getCurrentName();
/* 666 */                       if ("repostId".equals(cn3)) {
/* 667 */                           repostId = p.getLongValue();
                                } else if ("postId".equals(cn3)) {
/* 669 */                           postId = p.getLongValue();
                                }
                                break;
                        }
/* 677 */               t = p.nextToken();
                    }
/* 680 */           return new VineRepost(username, avatarUrl, location, description, userId, postId, repostId, verified, priv, unflaggable);
                }

/* 685 */       private static VinePost parsePost(JsonParser p) throws IOException {
/* 686 */           VinePost vinePost = new VinePost();
/* 687 */           String address = null;
/* 688 */           String categoryIconUrl = null;
/* 689 */           String categoryId = null;
/* 690 */           String categoryShortName = null;
/* 691 */           String city = null;
/* 692 */           String countryCode = null;
/* 693 */           String venueName = null;
/* 694 */           String state = null;
/* 695 */           String videoWebmUrl = null;
/* 696 */           JsonToken t = p.nextToken();
/* 697 */           boolean explicitContent = false;
/* 698 */           boolean verified = false;
/* 699 */           boolean liked = false;
/* 700 */           boolean revined = false;
/* 701 */           boolean promoted = false;
/* 702 */           boolean postVerified = false;
/* 703 */           boolean priv = false;
/* 704 */           long loops = 0;
/* 705 */           double velocity = 0.0d;
/* 706 */           boolean onFire = false;
/* 707 */           VineByline byline = null;
/* 708 */           boolean following = false;
/* 710 */           while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 713 */                       String cn = p.getCurrentName();
/* 714 */                       if ("comments".equals(cn)) {
/* 715 */                           vinePost.comments = parsePagedData(p, COMMENT_PARSER);
                        } else if ("likes".equals(cn)) {
/* 717 */                           vinePost.likes = parsePagedData(p, LIKES_PARSER);
                                } else if ("loops".equals(cn)) {
/* 719 */                           JsonToken t2 = p.nextToken();
/* 720 */                           while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
                                            case START_ARRAY:
/* 724 */                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
/* 736 */                                       if ("onFire".equals(p.getCurrentName())) {
/* 737 */                                           if (p.getIntValue() != 1) {
                                                        onFire = false;
                                                    } else {
                                                        onFire = true;
                                                    }
                                                }
                                                break;
                                            case VALUE_TRUE:
                                            case VALUE_FALSE:
/* 743 */                                       if ("onFire".equals(p.getCurrentName())) {
/* 744 */                                           onFire = p.getBooleanValue();
                                                }
                                                break;
                                            case VALUE_NUMBER_FLOAT:
/* 728 */                                       if ("count".equals(p.getCurrentName())) {
/* 729 */                                           loops = (long) p.getDoubleValue();
                                                } else if (Vine.PostColumns.VELOCITY.equals(p.getCurrentName())) {
/* 731 */                                           velocity = p.getDoubleValue();
                                                }
                                                break;
                                        }
/* 749 */                               t2 = p.nextToken();
                                    }
                        } else if ("user".equals(cn)) {
/* 752 */                           vinePost.user = parseUser(p);
/* 753 */                           vinePost.userBackgroundColor = vinePost.user.profileBackground;
                        } else if ("repost".equals(cn)) {
/* 755 */                           vinePost.repost = parseRepost(p);
                                } else if (VineAPI.RESOURCE_REPOSTS.equals(cn)) {
/* 757 */                           JsonToken t3 = p.nextToken();
/* 758 */                           while (t3 != null && t3 != JsonToken.END_OBJECT) {
                                        switch (t3) {
                                            case START_OBJECT:
                                            case START_ARRAY:
/* 765 */                                       p.skipChildren();
                                                break;
                                            case VALUE_NUMBER_INT:
/* 769 */                                       if ("count".equals(p.getCurrentName())) {
/* 770 */                                           vinePost.revinersCount = p.getIntValue();
                                                }
                                                break;
                                        }
/* 775 */                               t3 = p.nextToken();
                                    }
                        } else if ("byline".equals(cn)) {
/* 778 */                           byline = parseByline(p);
                                } else {
/* 780 */                           p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
/* 785 */                       String cn2 = p.getCurrentName();
/* 786 */                       if ("tags".equals(cn2)) {
/* 787 */                           vinePost.tags = parseTags(p);
                                } else if ("entities".equals(cn2)) {
/* 789 */                           JsonToken t4 = p.nextToken();
/* 790 */                           ArrayList<VineEntity> entities = null;
/* 791 */                           while (t4 != null && t4 != JsonToken.END_ARRAY) {
/* 792 */                               VineEntity entity = parseEntity(p);
/* 793 */                               if (entities == null) {
/* 794 */                                   entities = new ArrayList<>();
                                        }
/* 796 */                               if (entity != null) {
/* 797 */                                   entities.add(entity);
                                        }
/* 799 */                               t4 = p.nextToken();
                                    }
/* 801 */                           if (entities != null) {
/* 802 */                               Util.validateAndFixEntities(entities);
/* 803 */                               vinePost.entities = entities;
                                    }
                                } else if ("videoUrls".equals(cn2)) {
/* 806 */                           vinePost.setUpUrls(parseVideoUrls(p));
                                } else {
/* 808 */                           p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 859 */                       String cn3 = p.getCurrentName();
/* 860 */                       if ("explicitContent".equals(cn3)) {
/* 861 */                           explicitContent = p.getIntValue() == 1;
                                } else if ("postFlags".equals(cn3)) {
/* 863 */                           vinePost.postFlags = p.getIntValue();
                                } else if ("postVerified".equals(cn3)) {
/* 865 */                           postVerified = p.getIntValue() == 1;
                                } else if ("promoted".equals(cn3)) {
/* 867 */                           promoted = p.getIntValue() == 1;
                                } else if ("verified".equals(cn3)) {
/* 869 */                           verified = p.getIntValue() == 1;
                                } else if ("postId".equals(cn3)) {
/* 871 */                           vinePost.postId = p.getLongValue();
                                } else if ("userId".equals(cn3)) {
/* 873 */                           vinePost.userId = p.getLongValue();
                                } else if ("myRepostId".equals(cn3)) {
/* 875 */                           vinePost.myRepostId = p.getLongValue();
                                    if (vinePost.myRepostId > 0) {
/* 877 */                               revined = true;
                                    }
                                } else if (!"private".equals(cn3)) {
/* 881 */                           if (!VineEverydayNotification.TYPE_LIKED.equals(cn3)) {
/* 883 */                               if ("following".equals(cn3)) {
/* 884 */                                   if (p.getIntValue() != 1) {
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
/* 813 */                       String cn4 = p.getCurrentName();
/* 814 */                       if ("foursquareVenueId".equals(cn4)) {
/* 815 */                           vinePost.foursquareVenueId = p.getText();
                                } else if ("description".equals(cn4)) {
/* 817 */                           vinePost.description = p.getText();
                                } else if ("created".equals(cn4)) {
/* 819 */                           vinePost.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn4)) {
/* 821 */                           vinePost.location = p.getText();
                                } else if ("avatarUrl".equals(cn4)) {
/* 823 */                           vinePost.avatarUrl = p.getText();
                                } else if ("videoLowURL".equals(cn4)) {
/* 825 */                           vinePost.videoLowUrl = p.getText();
                                } else if ("videoUrl".equals(cn4)) {
/* 827 */                           vinePost.videoUrl = p.getText();
                                } else if ("videoWebmUrl".equals(cn4)) {
/* 829 */                           videoWebmUrl = p.getText();
                                } else if ("username".equals(cn4)) {
/* 831 */                           vinePost.username = p.getText();
                                } else if ("shareUrl".equals(cn4)) {
/* 833 */                           vinePost.shareUrl = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(cn4)) {
/* 835 */                           vinePost.thumbnailUrl = p.getText();
                                } else if ("venueAddress".equals(cn4)) {
/* 837 */                           address = p.getText();
                                } else if ("venueCategoryIconUrl".equals(cn4)) {
/* 839 */                           categoryIconUrl = p.getText();
                                } else if ("venueCategoryId".equals(cn4)) {
/* 841 */                           categoryId = p.getText();
                                } else if ("venueCategoryShortName".equals(cn4)) {
/* 843 */                           categoryShortName = p.getText();
                                } else if ("venueCity".equals(cn4)) {
/* 845 */                           city = p.getText();
                                } else if ("venueCountryCode".equals(cn4)) {
/* 847 */                           countryCode = p.getText();
                                } else if ("venueName".equals(cn4)) {
/* 849 */                           venueName = p.getText();
                                } else if ("state".equals(cn4)) {
/* 851 */                           state = p.getText();
                                } else if (VineAPI.RESOURCE_PROFILE_BACKGROUND.equals(cn4)) {
/* 853 */                           vinePost.userBackgroundColor = (int) Long.parseLong(p.getText().substring(2), 16);
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 890 */                       String cn5 = p.getCurrentName();
/* 891 */                       if (VineEverydayNotification.TYPE_LIKED.equals(cn5)) {
/* 892 */                           liked = p.getBooleanValue();
                                } else if ("revined".equals(cn5)) {
/* 894 */                           revined = p.getBooleanValue();
                                }
                                break;
                        }
/* 899 */               t = p.nextToken();
                    }
/* 902 */           if (TextUtils.isEmpty(vinePost.videoUrl) && videoWebmUrl != null) {
/* 903 */               vinePost.videoUrl = videoWebmUrl;
                    }
/* 906 */           if (vinePost.foursquareVenueId != null) {
/* 907 */               vinePost.venueData = new VineVenue(categoryIconUrl, categoryShortName, categoryId, city, countryCode, venueName, state, address);
                    }
/* 911 */           vinePost.setFlagExplicit(explicitContent);
/* 912 */           vinePost.setFlagVerified(verified);
/* 913 */           vinePost.setFlagRevined(revined);
/* 914 */           vinePost.setFlagLiked(liked);
/* 915 */           vinePost.setFlagPromoted(promoted);
/* 916 */           vinePost.setFlagPostVerified(postVerified);
/* 917 */           vinePost.setFlagPrivate(priv);
/* 919 */           vinePost.likesCount = vinePost.likes != null ? vinePost.likes.count : 0;
/* 920 */           vinePost.commentsCount = vinePost.comments != null ? vinePost.comments.count : 0;
/* 921 */           vinePost.loops = loops;
/* 922 */           vinePost.lastRefresh = System.currentTimeMillis();
/* 923 */           vinePost.velocity = velocity;
/* 924 */           vinePost.onFire = onFire;
/* 925 */           vinePost.byline = byline;
/* 926 */           vinePost.following = following;
                    vinePost.originalFollowingState = following;
/* 928 */           return vinePost;
                }

                /**
                 * The reconstructed full post parser can loop forever on some revival-feed
                 * records because their nested likes/comments objects do not match the old
                 * Vine schema. The feed card only needs the bounded top-level fields below.
                 */
/* 930 */      private static VinePost parseFeedPost(JsonParser p) throws IOException {
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

/* 931 */       private static List<VineVideoUrlTier> parseVideoUrls(JsonParser p) throws IOException {
/* 932 */           ArrayList<VineVideoUrlTier> urls = new ArrayList<>();
/* 933 */           JsonToken t = p.getCurrentToken();
/* 934 */           while (t != null && t != JsonToken.END_ARRAY) {
/* 935 */               t = p.nextToken();
/* 936 */               if (t == JsonToken.START_OBJECT) {
/* 937 */                   String url = null;
/* 938 */                   float rate = 0.0f;
/* 939 */                   String format = null;
/* 941 */                   t = p.nextToken();
/* 942 */                   while (t != null && t != JsonToken.END_OBJECT) {
/* 943 */                       String currentName = p.getCurrentName();
/* 944 */                       if (t == JsonToken.VALUE_STRING) {
/* 945 */                           if ("videoUrl".equals(currentName)) {
/* 946 */                               url = p.getText();
                                    } else if ("format".equals(currentName)) {
/* 948 */                               format = p.getText();
                                    }
                                } else if (t == JsonToken.VALUE_NUMBER_FLOAT) {
/* 951 */                           if ("rate".equals(currentName)) {
/* 952 */                               rate = p.getFloatValue();
                                    }
                                } else if (t == JsonToken.VALUE_NUMBER_INT && "rate".equals(currentName)) {
/* 956 */                           rate = p.getIntValue();
                                }
/* 959 */                       t = p.nextToken();
                            }
/* 962 */                   if (!VineVideoUrlTier.FORMAT_DASH.equals(format)) {
/* 963 */                       VineVideoUrlTier vinePostUrl = new VineVideoUrlTier(url, rate, format);
/* 964 */                       urls.add(vinePostUrl);
                            }
                        }
                    }
/* 968 */           return urls;
                }

/* 971 */       private static ArrayList<VineTag> parseTags(JsonParser p) throws IOException {
/* 972 */           ArrayList<VineTag> tags = new ArrayList<>();
/* 973 */           JsonToken t = p.nextToken();
/* 974 */           String tagName = null;
/* 975 */           long tagId = 0;
/* 976 */           while (t != null && t != JsonToken.END_ARRAY) {
                        switch (t) {
                            case START_OBJECT:
                                break;
                            default:
                                continue;
                        }
/* 979 */               while (t != null && t != JsonToken.END_OBJECT) {
                            switch (t) {
                                case VALUE_NUMBER_INT:
/* 985 */                           tagId = p.getLongValue();
                                    break;
                                case VALUE_STRING:
/* 982 */                           tagName = p.getText();
                                    break;
                            }
/* 988 */                   t = p.nextToken();
                        }
/* 990 */               tags.add(new VineTag(tagName, tagId));
/* 993 */               t = p.nextToken();
                    }
/* 995 */           return tags;
                }

/* 998 */       private static synchronized VineChannel parseChannel(JsonParser p) throws IOException {
                    VineChannel vineChannel;
                    try {
                        vineChannel = new VineChannel();
/* 1000 */              JsonToken t = p.nextToken();
/* 1001 */              while (t != null && t != JsonToken.END_OBJECT) {
                            switch (t) {
                                case START_OBJECT:
                                case START_ARRAY:
/* 1005 */                          p.skipChildren();
                                    break;
                                case VALUE_NUMBER_INT:
/* 1036 */                          String cn = p.getCurrentName();
/* 1037 */                          if ("channelId".equals(cn)) {
/* 1038 */                              vineChannel.channelId = p.getLongValue();
                                    } else if ("following".equals(cn)) {
/* 1040 */                              vineChannel.following = p.getIntValue() == 1;
                                    }
                                    break;
                                case VALUE_STRING:
/* 1009 */                          String cn2 = p.getCurrentName();
/* 1010 */                          if ("channel".equals(cn2)) {
/* 1011 */                              vineChannel.channel = p.getText();
                                    } else if ("iconFullUrl".equals(cn2)) {
/* 1013 */                              vineChannel.iconFullUrl = p.getText();
                                    } else if ("retinaIconFullUrl".equals(cn2)) {
/* 1015 */                              vineChannel.retinaIconFullUrl = p.getText();
                                    } else if ("created".equals(cn2)) {
/* 1017 */                              vineChannel.created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                    } else if ("backgroundColor".equals(cn2)) {
/* 1019 */                              String color = p.getText();
/* 1020 */                              if (!color.startsWith("#")) {
/* 1021 */                                  color = "#" + color;
                                        }
/* 1023 */                              vineChannel.backgroundColor = color;
                                    } else if ("fontColor".equals(cn2)) {
/* 1025 */                              String color2 = p.getText();
/* 1026 */                              if (!color2.startsWith("#")) {
/* 1027 */                                  color2 = "#" + color2;
                                        }
/* 1029 */                              vineChannel.fontColor = color2;
                                    } else if ("exploreRetinaIconFullUrl".equals(cn2)) {
/* 1031 */                              vineChannel.exploreRetinaIconFullUrl = p.getText();
                                    }
                                    break;
                            }
/* 1045 */                  t = p.nextToken();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return vineChannel;
                }

/* 1051 */      private static VineTag parseTag(JsonParser p) throws IOException {
/* 1052 */          VineTag tag = new VineTag();
/* 1053 */          JsonToken t = p.nextToken();
/* 1054 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 1064 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 1060 */                      tag.tagId = p.getLongValue();
                                break;
                            case VALUE_STRING:
/* 1057 */                      tag.tagName = p.getText();
                                break;
                        }
/* 1068 */              t = p.nextToken();
                    }
/* 1070 */          return tag;
                }

/* 1073 */      private static VineComment parseComment(JsonParser p) throws IOException {
/* 1074 */          JsonToken t = p.nextToken();
/* 1075 */          String avatarUrl = null;
/* 1076 */          String comment = null;
/* 1077 */          long timestamp = 0;
/* 1078 */          long commentId = 0;
/* 1079 */          String location = null;
/* 1080 */          VineUser user = null;
/* 1081 */          long userId = 0;
/* 1082 */          String username = null;
/* 1083 */          boolean verified = false;
/* 1084 */          ArrayList<VineEntity> entities = null;
/* 1086 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1090 */                      if ("user".equals(p.getCurrentName())) {
/* 1091 */                          user = parseUser(p);
                                } else {
/* 1093 */                          p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
/* 1129 */                      if ("entities".equals(p.getCurrentName())) {
/* 1130 */                          JsonToken t2 = p.nextToken();
/* 1131 */                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 1132 */                              VineEntity entity = parseEntity(p);
/* 1133 */                              if (entities == null) {
/* 1134 */                                  entities = new ArrayList<>();
                                        }
/* 1136 */                              if (entity != null) {
/* 1137 */                                  entities.add(entity);
                                        }
/* 1139 */                              t2 = p.nextToken();
                                    }
                                } else {
/* 1142 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 1113 */                      String cn = p.getCurrentName();
/* 1114 */                      if (ReportingFragment.EXTRA_COMMENT_ID.equals(cn)) {
/* 1115 */                          commentId = p.getLongValue();
                                } else if ("userId".equals(cn)) {
/* 1117 */                          userId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 1098 */                      String cn2 = p.getCurrentName();
/* 1099 */                      if ("avatarUrl".equals(cn2)) {
/* 1100 */                          avatarUrl = p.getText();
                                } else if ("comment".equals(cn2)) {
/* 1102 */                          comment = p.getText();
                                } else if ("created".equals(cn2)) {
/* 1104 */                          timestamp = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("location".equals(cn2)) {
/* 1106 */                          location = p.getText();
                                } else if ("username".equals(cn2)) {
/* 1108 */                          username = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 1123 */                      if ("verified".equals(p.getCurrentName())) {
/* 1124 */                          verified = p.getBooleanValue();
                                }
                                break;
                        }
/* 1147 */              t = p.nextToken();
                    }
/* 1149 */          if (entities != null) {
/* 1150 */              Util.validateAndFixEntities(entities);
                    }
                    if (TextUtils.isEmpty(avatarUrl) && user != null) {
                        avatarUrl = user.avatarUrl;
                    }
                    avatarUrl = normalizeMediaUrl(avatarUrl);
/* 1152 */          return new VineComment(commentId, comment, avatarUrl, user, timestamp, location, userId, username, verified, 0L, entities, false);
                }

/* 1156 */      private static Object parseGeneral(JsonParser p) throws IOException {
/* 1157 */          JsonToken t = p.nextToken();
/* 1158 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                                break;
                            case START_ARRAY:
/* 1176 */                      p.skipChildren();
                                continue;
                            case VALUE_NUMBER_INT:
                            case VALUE_STRING:
                            default:
                                continue;
                            case VALUE_TRUE:
/* 1161 */                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
/* 1162 */                          return API_RESPONSE.SUCCESS;
                                }
                                continue;
                            case VALUE_FALSE:
/* 1167 */                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
/* 1168 */                          return API_RESPONSE.FAILURE;
                                }
                                break;
                        }
/* 1172 */              p.skipChildren();
/* 1180 */              t = p.nextToken();
                    }
/* 1182 */          return null;
                }

/* 1185 */      private static Long parseLikeId(JsonParser p) throws IOException {
/* 1186 */          long id = 0;
/* 1187 */          JsonToken t = p.nextToken();
/* 1188 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
                                break;
                            case VALUE_NUMBER_INT:
/* 1191 */                      if ("likeId".equals(p.getCurrentName())) {
/* 1192 */                          id = p.getLongValue();
                                }
/* 1202 */                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
/* 1198 */              p.skipChildren();
/* 1202 */              t = p.nextToken();
                    }
/* 1204 */          return Long.valueOf(id);
                }

/* 1207 */      private static ArrayList<VineUser> parseUsersArray(JsonParser p, Bundle extras) throws IOException {
/* 1208 */          JsonToken t = p.nextToken();
/* 1209 */          ArrayList<VineUser> users = new ArrayList<>();
/* 1210 */          int sectionId = -1;
/* 1211 */          int sectionIndex = 0;
/* 1213 */          if (extras != null) {
/* 1214 */              long lastIndex = extras.getLong(VineParserReader.EXTRA_LAST_FRIEND_INDEX, -1L);
                        if (lastIndex >= 0) {
/* 1216 */                  sectionId = (int) (lastIndex >> 32);
/* 1217 */                  sectionIndex = (int) ((lastIndex - (((long) sectionId) << 32)) + 1);
                        }
                    }
/* 1221 */          while (t != null && t != JsonToken.END_ARRAY) {
/* 1222 */              if (t == JsonToken.START_OBJECT) {
/* 1223 */                  VineUser user = parseUser(p, sectionId);
/* 1224 */                  if (user.sectionId >= 0) {
/* 1225 */                      if (sectionId != user.sectionId) {
/* 1226 */                          user.friendIndex = ((long) user.sectionId) << 32;
/* 1227 */                          sectionIndex = 1;
/* 1228 */                          sectionId = user.sectionId;
                                } else {
/* 1230 */                          user.friendIndex = (((long) user.sectionId) << 32) + ((long) sectionIndex);
/* 1231 */                          sectionIndex++;
                                }
                            }
/* 1234 */                  if (user != null) {
/* 1235 */                      users.add(user);
                            }
                        }
/* 1238 */              t = p.nextToken();
                    }
/* 1240 */          return users;
                }

/* 1243 */      private static VineUser parseUser(JsonParser p) throws IOException {
/* 1244 */          return parseUser(p, -1);
                }

                /* JADX WARN: Unexpected iteration count in SwitchBreakVisitor. Please report as an issue */
                @Nullable
/* 1248 */      private static VineUser parseUser(JsonParser p, int sectionId) throws IOException {
/* 1249 */          String username = null;
/* 1250 */          String avatarUrl = null;
/* 1251 */          String description = null;
/* 1252 */          String location = null;
/* 1253 */          String phoneNumber = null;
/* 1254 */          String email = null;
/* 1255 */          String edition = null;
/* 1256 */          String sectionTitle = null;
/* 1257 */          long userId = 0;
/* 1258 */          long orderId = 0;
/* 1259 */          int blocked = 0;
/* 1260 */          int blocking = 0;
/* 1261 */          int explicit = 0;
/* 1262 */          boolean isExternal = false;
/* 1263 */          int followerCount = 0;
/* 1264 */          int followingCount = 0;
/* 1265 */          int following = -1;
/* 1266 */          int likeCount = 0;
/* 1267 */          int authoredPostCount = 0;
/* 1268 */          int postCount = 0;
/* 1269 */          int verified = 0;
/* 1270 */          int twitterConnected = 0;
/* 1271 */          int includePromoted = 0;
/* 1272 */          int privateAccount = 0;
/* 1273 */          boolean followApprovalPending = false;
/* 1274 */          boolean followRequested = false;
/* 1275 */          int repostsEnabled = 1;
/* 1276 */          boolean acceptsOutOfNetworkConversations = false;
/* 1277 */          int profileBackground = -1;
/* 1278 */          boolean emailVerified = false;
/* 1279 */          boolean phoneVerified = false;
/* 1280 */          boolean disableAddressBook = false;
/* 1281 */          boolean hiddenEmail = false;
/* 1282 */          boolean hiddenPhoneNumber = false;
/* 1283 */          boolean hiddenTwitter = false;
/* 1284 */          long loopCount = 0;
/* 1285 */          boolean notifyPosts = false;
/* 1287 */          JsonToken t = p.nextToken();
/* 1289 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 1379 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 1315 */                      String name = p.getCurrentName();
/* 1316 */                      if ("userId".equals(name) || "id".equals(name)) {
/* 1317 */                          userId = p.getLongValue();
                                    break;
                                } else if ("blocked".equals(name)) {
/* 1319 */                          blocked = p.getIntValue();
                                    break;
                                } else if (Vine.UserColumns.BLOCKING.equals(name)) {
/* 1321 */                          blocking = p.getIntValue();
                                    break;
                                } else if ("explicit".equals(name)) {
/* 1323 */                          explicit = p.getIntValue();
                                    break;
                                } else if ("externalUser".equals(name)) {
/* 1325 */                          isExternal = p.getIntValue() == 1;
                                    break;
                                } else if ("followerCount".equals(name)) {
/* 1327 */                          followerCount = p.getIntValue();
                                    break;
                                } else if ("followingCount".equals(name)) {
/* 1329 */                          followingCount = p.getIntValue();
                                    break;
                                } else if ("following".equals(name)) {
/* 1331 */                          following = p.getIntValue();
                                    break;
                                } else {
/* 1332 */                          if ("likeCount".equals(name)) {
/* 1333 */                              likeCount = p.getIntValue();
                                    } else if ("authoredPostCount".equals(name)) {
/* 1335 */                              authoredPostCount = p.getIntValue();
                                    } else if ("postCount".equals(name)) {
/* 1337 */                              postCount = p.getIntValue();
                                    } else if ("verified".equals(name)) {
/* 1339 */                              verified = p.getIntValue();
                                    } else if ("followId".equals(name)) {
/* 1341 */                              orderId = p.getLongValue();
                                    } else if ("twitterConnected".equals(name)) {
/* 1343 */                              twitterConnected = p.getIntValue();
                                    } else if ("includePromoted".equals(name)) {
/* 1345 */                              includePromoted = p.getIntValue();
                                    } else if ("private".equals(name)) {
/* 1347 */                              privateAccount = p.getIntValue();
                                    } else if ("followApprovalPending".equals(name)) {
/* 1349 */                              followApprovalPending = p.getIntValue() == 1;
                                    } else if ("followRequested".equals(name)) {
/* 1351 */                              followRequested = p.getIntValue() == 1;
                                    } else if ("repostsEnabled".equals(name)) {
/* 1353 */                              repostsEnabled = p.getIntValue();
                                    } else if ("verifiedEmail".equals(name)) {
/* 1355 */                              emailVerified = p.getIntValue() == 1;
                                    } else if ("verifiedPhoneNumber".equals(name)) {
/* 1357 */                              phoneVerified = p.getIntValue() == 1;
                                    } else if ("sectionId".equals(name)) {
/* 1359 */                              sectionId = p.getIntValue();
                                    } else if ("disableAddressBook".equals(name)) {
/* 1361 */                              disableAddressBook = p.getIntValue() == 1;
                                    } else if (VineAPI.RESOURCE_ACCEPT_OUT_OF_NETWORK_CONVERSATIONS.equals(name)) {
/* 1363 */                              acceptsOutOfNetworkConversations = p.getIntValue() == 1;
                                    } else if ("hiddenEmail".equals(name)) {
/* 1365 */                              hiddenEmail = p.getIntValue() == 1;
                                    } else if ("hiddenPhoneNumber".equals(name)) {
/* 1367 */                              hiddenPhoneNumber = p.getIntValue() == 1;
                                    } else if ("hiddenTwitter".equals(name)) {
/* 1369 */                              hiddenTwitter = p.getIntValue() == 1;
                                    } else if ("loopCount".equals(name)) {
/* 1371 */                              loopCount = p.getLongValue();
                                    } else if ("notifyPosts".equals(name)) {
/* 1373 */                              if (p.getIntValue() != 1) {
                                            notifyPosts = false;
                                        } else {
                                            notifyPosts = true;
                                        }
                                    }
                                    break;
                                }
                            case VALUE_STRING:
/* 1292 */                      String name2 = p.getCurrentName();
/* 1293 */                      if ("avatarUrl".equals(name2) || "avatar_url".equals(name2)) {
/* 1294 */                          avatarUrl = p.getText();
                                } else if ("username".equals(name2)) {
/* 1296 */                          username = p.getText();
                                } else if ("description".equals(name2)) {
/* 1298 */                          description = p.getText();
                                } else if ("location".equals(name2)) {
/* 1300 */                          location = p.getText();
                                } else if (VineRecipient.KEY_PHONE.equals(name2)) {
/* 1302 */                          phoneNumber = p.getText();
                                } else if ("email".equals(name2)) {
/* 1304 */                          email = p.getText();
                                } else if ("edition".equals(name2)) {
/* 1306 */                          edition = p.getText();
                                } else if (VineAPI.RESOURCE_PROFILE_BACKGROUND.equals(name2)) {
/* 1308 */                          profileBackground = (int) Long.parseLong(p.getText().substring(2), 16);
                                } else if ("section".equals(name2)) {
/* 1310 */                          sectionTitle = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                                if ("following".equals(p.getCurrentName())) {
                                    following = p.getBooleanValue() ? 1 : 0;
                                }
                                break;
                        }
/* 1385 */              t = p.nextToken();
                    }
                    if (userId <= 0) {
/* 1389 */              return null;
                    }
/* 1392 */          VineUser u = new VineUser(username, avatarUrl, description, location, userId, orderId, blocked, blocking, explicit, isExternal, followerCount, followingCount, following, likeCount, postCount, verified, email, phoneNumber, twitterConnected, includePromoted, privateAccount, repostsEnabled, edition, acceptsOutOfNetworkConversations, profileBackground, authoredPostCount, sectionTitle, disableAddressBook, hiddenEmail, hiddenPhoneNumber, hiddenTwitter, loopCount, notifyPosts);
/* 1398 */          u.sectionId = sectionId;
/* 1399 */          if (followApprovalPending) {
/* 1400 */              u.setApprovalPending();
                    }
/* 1402 */          if (followRequested) {
/* 1403 */              u.setFollowRequested();
                    }
/* 1405 */          if (emailVerified) {
/* 1406 */              u.setEmailVerified();
                    }
/* 1408 */          if (phoneVerified) {
/* 1409 */              u.setPhoneVerified();
/* 1411 */              return u;
                    }
/* 1411 */          return u;
                }

/* 1415 */      private static VineLogin parseSignUp(JsonParser p) throws IOException {
/* 1416 */          long userId = 0;
/* 1417 */          String edition = null;
/* 1418 */          JsonToken t = p.nextToken();
/* 1420 */          String sessionKey = null;
/* 1421 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1438 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
                                break;
                            case VALUE_NUMBER_INT:
/* 1424 */                      if ("userId".equals(p.getCurrentName())) {
/* 1425 */                          userId = p.getLongValue();
                                } else {
                                    continue;
                                }
/* 1444 */                      t = p.nextToken();
                                break;
                            case VALUE_STRING:
/* 1430 */                      if (VineService.EXTRA_KEY.equals(p.getCurrentName())) {
/* 1431 */                          sessionKey = p.getText();
                                    continue;
                                } else if ("edition".equals(p.getCurrentName())) {
/* 1433 */                          edition = p.getText();
                                }
/* 1444 */                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
/* 1441 */              p.skipChildren();
/* 1444 */              t = p.nextToken();
                    }
/* 1447 */          VineLogin vl = new VineLogin(sessionKey, null, userId, edition);
/* 1448 */          return vl;
                }

/* 1459 */      public static TwitterUser parseTwitterUser(JsonParser p) throws IOException {
/* 1460 */          String name = null;
/* 1461 */          String screenName = null;
/* 1462 */          String location = null;
/* 1463 */          String description = null;
/* 1464 */          String url = null;
/* 1465 */          String profileUrl = null;
/* 1466 */          boolean defaultProfileImage = false;
/* 1467 */          long userId = 0;
/* 1469 */          JsonToken t = p.nextToken();
/* 1470 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1504 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1508 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 1491 */                      if ("id".equals(p.getCurrentName())) {
/* 1492 */                          userId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 1473 */                      String cn = p.getCurrentName();
/* 1474 */                      if ("name".equals(cn)) {
/* 1475 */                          name = p.getText();
                                } else if ("screen_name".equals(cn)) {
/* 1477 */                          screenName = p.getText();
                                } else if ("profile_image_url".equals(cn)) {
/* 1479 */                          String profileUrl2 = p.getText();
/* 1480 */                          profileUrl = profileUrl2.replace("_normal", "_bigger");
                                } else if (PlusShare.KEY_CALL_TO_ACTION_URL.equals(cn)) {
/* 1482 */                          url = p.getText();
                                } else if ("description".equals(cn)) {
/* 1484 */                          description = p.getText();
                                } else if ("location".equals(cn)) {
/* 1486 */                          location = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 1498 */                      if ("default_profile_image".equals(p.getCurrentName())) {
/* 1499 */                          defaultProfileImage = p.getBooleanValue();
                                }
                                break;
                        }
/* 1515 */              t = p.nextToken();
                    }
/* 1517 */          return new TwitterUser(name, screenName, location, description, url, profileUrl, defaultProfileImage, userId);
                }

/* 1522 */      public static JSONObject parseClientProfile(JsonParser p) throws IOException {
/* 1523 */          SLog.m446d("Parse client profile.");
/* 1524 */          JsonToken t = p.nextToken();
/* 1525 */          JSONObject object = new JSONObject();
/* 1526 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1546 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1550 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
                                try {
/* 1539 */                          object.put(p.getCurrentName(), p.getIntValue());
                                } catch (JSONException e) {
/* 1541 */                          SLog.m447d("Failed to parse: {}.", p.getCurrentName());
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
                                try {
/* 1531 */                          object.put(p.getCurrentName(), p.getBooleanValue());
                                } catch (JSONException e2) {
/* 1533 */                          SLog.m447d("Failed to parse: {}.", p.getCurrentName());
                                }
                                break;
                        }
/* 1557 */              t = p.nextToken();
                    }
/* 1559 */          SLog.m447d("Parsed client profile: ", object);
/* 1560 */          return object;
                }

/* 1563 */      public static VineLogin parseLogin(JsonParser p) throws IOException {
/* 1564 */          String key = null;
/* 1565 */          String username = null;
/* 1566 */          String edition = null;
/* 1567 */          long userId = 0;
/* 1568 */          boolean success = false;
/* 1570 */          JsonToken t = p.nextToken();
/* 1571 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1574 */                      if ("data".equals(p.getCurrentName())) {
/* 1575 */                          JsonToken t2 = p.nextToken();
/* 1576 */                          while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case VALUE_NUMBER_INT:
/* 1590 */                                      if ("userId".equals(p.getCurrentName())) {
/* 1591 */                                          userId = p.getLongValue();
                                                }
                                                break;
                                            case VALUE_STRING:
/* 1579 */                                      String name = p.getCurrentName();
/* 1580 */                                      if (VineService.EXTRA_KEY.equals(name)) {
/* 1581 */                                          key = p.getText();
                                                } else if ("username".equals(name)) {
/* 1583 */                                          username = p.getText();
                                                } else if ("edition".equals(name)) {
/* 1585 */                                          edition = p.getText();
                                                }
                                                break;
                                        }
/* 1599 */                              t2 = p.nextToken();
                                    }
                                } else {
/* 1602 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 1608 */                      if (VineUploadService.EXTRA_SUCCESS.equals(p.getCurrentName())) {
/* 1609 */                          success = p.getBooleanValue();
                                }
                                break;
                        }
/* 1617 */              t = p.nextToken();
                    }
/* 1620 */          if (success) {
/* 1621 */              return new VineLogin(key, username, userId, edition);
                    }
                    return null;
                }

/* 1627 */      public static VineError parseError(JsonParser p) throws IOException {
/* 1628 */          int error = 0;
/* 1629 */          String reason = null;
/* 1630 */          String data = null;
/* 1631 */          JsonToken t = p.nextToken();
/* 1633 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1636 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1640 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 1644 */                      if ("code".equals(p.getCurrentName())) {
/* 1645 */                          error = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
/* 1650 */                      if ("error".equals(p.getCurrentName())) {
/* 1651 */                          reason = p.getText();
                                } else if ("data".equals(p.getCurrentName())) {
/* 1653 */                          data = p.getText();
                                }
                                break;
                        }
/* 1657 */              t = p.nextToken();
                    }
/* 1659 */          return new VineError(error, reason, data);
                }

/* 1662 */      public static VineError parseMessageError(JsonParser p) throws IOException {
/* 1663 */          int error = 0;
/* 1664 */          String reason = null;
/* 1665 */          JsonToken t = p.nextToken();
/* 1667 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1670 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1674 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 1678 */                      if ("code".equals(p.getCurrentName())) {
/* 1679 */                          error = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
/* 1684 */                      if ("message".equals(p.getCurrentName())) {
/* 1685 */                          reason = p.getText();
                                }
                                break;
                        }
/* 1689 */              t = p.nextToken();
                    }
/* 1691 */          return new VineError(error, reason);
                }

/* 1694 */      public static VineSingleNotification parsePushNotification(JsonParser p) throws IOException {
/* 1695 */          if (p.nextToken() == JsonToken.START_OBJECT) {
/* 1696 */              return parseNotification(p);
                    }
                    return null;
                }

/* 1702 */      public static ArrayList<VineRTCConversation> parseRTCEvent(JsonParser p) throws IOException {
/* 1703 */          ArrayList<VineRTCConversation> event = null;
/* 1705 */          JsonToken t = p.nextToken();
/* 1706 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1709 */                      if ("data".equals(p.getCurrentName())) {
/* 1710 */                          JsonToken t2 = p.nextToken();
/* 1711 */                          while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                        switch (t2) {
                                            case START_OBJECT:
/* 1714 */                                      if ("conversations".equals(p.getCurrentName())) {
/* 1715 */                                          event = parseRTCConversations(p);
                                                }
                                                break;
                                        }
/* 1720 */                              t2 = p.nextToken();
                                    }
                                }
                                break;
                            case START_ARRAY:
/* 1726 */                      p.skipChildren();
                                break;
                        }
/* 1730 */              t = p.nextToken();
                    }
/* 1733 */          return event;
                }

/* 1737 */      public static ArrayList<VineRTCConversation> parseRTCConversations(JsonParser p) throws IOException {
/* 1738 */          ArrayList<VineRTCConversation> conversations = new ArrayList<>();
/* 1742 */          JsonToken t = p.nextToken();
/* 1743 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1746 */                      long conversationId = Long.parseLong(p.getCurrentName());
/* 1747 */                      ArrayList<VineRTCParticipant> participants = parseRTCParticipants(p);
/* 1748 */                      conversations.add(new VineRTCConversation(conversationId, participants));
                                break;
                            case START_ARRAY:
/* 1752 */                      p.skipChildren();
                                break;
                        }
/* 1756 */              t = p.nextToken();
                    }
/* 1759 */          return conversations;
                }

/* 1763 */      public static ArrayList<VineRTCParticipant> parseRTCParticipants(JsonParser p) throws IOException {
/* 1764 */          ArrayList<VineRTCParticipant> participants = new ArrayList<>();
/* 1766 */          boolean connected = false;
/* 1767 */          boolean typing = false;
/* 1768 */          long lastMessageId = -1;
/* 1770 */          JsonToken t = p.nextToken();
/* 1771 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1774 */                      long userId = Long.parseLong(p.getCurrentName());
/* 1775 */                      JsonToken t2 = p.nextToken();
/* 1776 */                      while (t2 != null && t2 != JsonToken.END_OBJECT) {
                                    switch (t2) {
                                        case VALUE_NUMBER_INT:
/* 1779 */                                  if ("last_message_id".equals(p.getCurrentName())) {
/* 1780 */                                      lastMessageId = p.getLongValue();
                                            }
                                            break;
                                        case VALUE_TRUE:
                                        case VALUE_FALSE:
/* 1786 */                                  String cn = p.getCurrentName();
/* 1787 */                                  if ("connected".equals(cn)) {
/* 1788 */                                      connected = p.getBooleanValue();
                                            } else if (VineWebSocketClient.PARAM_TYPING.equals(cn)) {
/* 1790 */                                      typing = p.getBooleanValue();
                                            }
                                            break;
                                    }
/* 1795 */                          t2 = p.nextToken();
                                }
/* 1797 */                      participants.add(new VineRTCParticipant(userId, connected, typing, lastMessageId));
                                break;
                            case START_ARRAY:
/* 1802 */                      p.skipChildren();
                                break;
                        }
/* 1806 */              t = p.nextToken();
                    }
/* 1809 */          return participants;
                }

/* 1812 */      private static VineConversation parseConversation(JsonParser p) throws IOException {
/* 1813 */          JsonToken t = p.nextToken();
/* 1815 */          long conversationId = 0;
/* 1816 */          long createdBy = 0;
/* 1817 */          long lastMessage = 0;
/* 1819 */          long unreadMessageCount = 0;
/* 1821 */          ArrayList<VinePrivateMessage> messages = null;
/* 1822 */          ArrayList<Long> users = null;
/* 1823 */          int networkType = 1;
/* 1825 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1851 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1863 */                      String name = p.getCurrentName();
/* 1864 */                      if ("messages".equals(name)) {
/* 1865 */                          JsonToken t2 = p.nextToken();
/* 1866 */                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 1867 */                              VinePrivateMessage message = parsePrivateMessage(p);
/* 1868 */                              if (messages == null) {
/* 1869 */                                  messages = new ArrayList<>();
                                        }
/* 1871 */                              if (messages != null) {
/* 1872 */                                  messages.add(message);
                                        }
/* 1874 */                              t2 = p.nextToken();
                                    }
                                } else if ("users".equals(name)) {
/* 1877 */                          JsonToken t3 = p.nextToken();
/* 1878 */                          while (t3 != null && t3 != JsonToken.END_ARRAY) {
                                        switch (t3) {
                                            case VALUE_NUMBER_INT:
/* 1881 */                                      long userId = p.getLongValue();
/* 1882 */                                      if (users == null) {
/* 1883 */                                          users = new ArrayList<>();
                                                }
/* 1885 */                                      if (users != null) {
/* 1886 */                                          users.add(Long.valueOf(userId));
                                                }
                                                break;
                                        }
/* 1891 */                              t3 = p.nextToken();
                                    }
                                } else {
/* 1894 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 1836 */                      String cn = p.getCurrentName();
/* 1837 */                      if ("conversationId".equals(cn)) {
/* 1838 */                          conversationId = p.getLongValue();
                                } else if ("lastMessage".equals(cn)) {
/* 1840 */                          lastMessage = p.getLongValue();
                                } else if ("lastMessageRead".equals(cn)) {
/* 1842 */                          p.getLongValue();
                                } else if ("unreadMessageCount".equals(cn)) {
/* 1844 */                          unreadMessageCount = p.getLongValue();
                                } else if ("createdBy".equals(cn)) {
/* 1846 */                          createdBy = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 1828 */                      if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
/* 1830 */                          networkType = 2;
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 1857 */                      if (Vine.MessagesColumns.DELETED.equals(p.getCurrentName())) {
/* 1858 */                          p.getBooleanValue();
                                }
                                break;
                        }
/* 1899 */              t = p.nextToken();
                    }
/* 1902 */          VineConversation result = new VineConversation(conversationId, lastMessage, createdBy, messages, users, networkType, unreadMessageCount);
/* 1904 */          return result;
                }

/* 1908 */      private static VineNotificationSetting parseNotificationSetting(JsonParser p) throws IOException {
/* 1909 */          JsonToken t = p.nextToken();
/* 1910 */          boolean isBooleanSetting = false;
/* 1911 */          String name = null;
/* 1912 */          String title = null;
/* 1913 */          String section = null;
/* 1914 */          String value = null;
/* 1917 */          ArrayList<Pair<String, String>> choices = new ArrayList<>();
/* 1919 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 1940 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 1944 */                      if ("choices".equals(p.getCurrentName())) {
/* 1945 */                          JsonToken t2 = p.nextToken();
/* 1946 */                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 1947 */                              String choiceTitle = null;
/* 1948 */                              String choiceValue = null;
/* 1949 */                              if (t2 == JsonToken.START_OBJECT) {
/* 1950 */                                  JsonToken t3 = p.nextToken();
/* 1951 */                                  while (t3 != null && t3 != JsonToken.END_OBJECT) {
/* 1952 */                                      if ("title".equals(p.getCurrentName())) {
/* 1953 */                                          choiceTitle = p.getText();
                                                } else if (Vine.Settings.PROP_VALUE.equals(p.getCurrentName())) {
/* 1955 */                                          choiceValue = p.getText();
                                                }
/* 1957 */                                      t3 = p.nextToken();
                                            }
/* 1960 */                                  if (TextUtils.isEmpty(choiceTitle) && !TextUtils.isEmpty(choiceValue)) {
/* 1961 */                                      choiceTitle = choiceValue;
                                            }
/* 1963 */                                  choices.add(new Pair<>(choiceTitle, choiceValue));
                                        }
/* 1965 */                              t2 = p.nextToken();
                                    }
                                } else {
/* 1968 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 1934 */                      if ("boolean".equals(p.getCurrentName())) {
/* 1935 */                          if (p.getIntValue() != 1) {
                                        isBooleanSetting = false;
                                    } else {
                                        isBooleanSetting = true;
                                    }
                                }
                                break;
                            case VALUE_STRING:
/* 1922 */                      if ("title".equals(p.getCurrentName())) {
/* 1923 */                          title = p.getText();
                                } else if ("section".equals(p.getCurrentName())) {
/* 1925 */                          section = p.getText();
                                } else if (Vine.Settings.PROP_VALUE.equals(p.getCurrentName())) {
/* 1927 */                          value = p.getText();
                                } else if ("name".equals(p.getCurrentName())) {
/* 1929 */                          name = p.getText();
                                }
                                break;
                        }
/* 1973 */              t = p.nextToken();
                    }
/* 1976 */          VineNotificationSetting result = new VineNotificationSetting(isBooleanSetting, name, title, section, value, choices);
/* 1979 */          return result;
                }

/* 1982 */      public static VinePrivateMessage parsePrivateMessage(JsonParser p) throws IOException {
/* 1983 */          JsonToken t = p.nextToken();
/* 1984 */          long conversationId = 0;
/* 1985 */          long messageId = 0;
/* 1986 */          long userId = 0;
/* 1987 */          long created = 0;
/* 1988 */          String message = null;
/* 1989 */          String videoUrl = null;
/* 1990 */          String thumbnailUrl = null;
/* 1991 */          boolean vanished = false;
/* 1992 */          boolean ephemeral = false;
/* 1993 */          VinePost post = null;
/* 1994 */          int maxLoops = Integer.MAX_VALUE;
/* 1995 */          long vanishDate = -1;
/* 1996 */          int networkType = 1;
/* 1998 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 2043 */                      String name = p.getCurrentName();
/* 2044 */                      if ("post".equals(name)) {
/* 2045 */                          post = parsePost(p);
/* 2046 */                          videoUrl = post.videoUrl;
/* 2047 */                          thumbnailUrl = post.thumbnailUrl;
                                } else {
/* 2049 */                          p.skipChildren();
                                }
                                break;
                            case START_ARRAY:
/* 2054 */                      String name2 = p.getCurrentName();
/* 2056 */                      if ("videoUrls".equals(name2)) {
/* 2057 */                          List<VineVideoUrlTier> urls = parseVideoUrls(p);
/* 2058 */                          videoUrl = getHighestH264Url(urls);
/* 2059 */                          if (TextUtils.isEmpty(videoUrl)) {
/* 2060 */                              videoUrl = getHighestWebmUrl(urls);
                                    }
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 2020 */                      String name3 = p.getCurrentName();
/* 2021 */                      if ("conversationId".equals(name3)) {
/* 2022 */                          conversationId = p.getLongValue();
                                } else if ("messageId".equals(name3)) {
/* 2024 */                          messageId = p.getLongValue();
                                } else if ("userId".equals(name3)) {
/* 2026 */                          userId = p.getLongValue();
                                } else if ("maxLoops".equals(name3)) {
/* 2028 */                          maxLoops = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
/* 2001 */                      String name4 = p.getCurrentName();
/* 2002 */                      if ("message".equals(name4)) {
/* 2003 */                          message = p.getText();
                                } else if ("videoUrl".equals(name4)) {
/* 2005 */                          videoUrl = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name4)) {
/* 2007 */                          thumbnailUrl = p.getText();
                                } else if ("created".equals(name4)) {
/* 2009 */                          created = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("vanishedDate".equals(name4)) {
/* 2011 */                          vanishDate = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
/* 2014 */                          networkType = 2;
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 2034 */                      String name5 = p.getCurrentName();
/* 2035 */                      if (Vine.MessagesColumns.VANISHED.equals(name5)) {
/* 2036 */                          vanished = p.getBooleanValue();
                                } else if (Vine.MessagesColumns.EPHEMERAL.equals(name5)) {
/* 2038 */                          ephemeral = p.getBooleanValue();
                                }
                                break;
                        }
/* 2066 */              t = p.nextToken();
                    }
/* 2069 */          return new VinePrivateMessage(-1L, conversationId, messageId, userId, created, message, videoUrl, thumbnailUrl, networkType, false, vanished, maxLoops, vanishDate, ephemeral, 0L, post, 0, (String) null);
                }

/* 2074 */      private static String getHighestH264Url(List<VineVideoUrlTier> urls) {
/* 2075 */          if (urls != null && urls.size() > 0) {
/* 2076 */              VineVideoUrlTier topTier = null;
                        for (int i = 0; i < urls.size(); i++) {
/* 2078 */                  if (VineVideoUrlTier.FORMAT_H264.equals(urls.get(i).format) && (topTier == null || urls.get(i).rate > topTier.rate)) {
/* 2080 */                      VineVideoUrlTier topTier2 = urls.get(i);
                                topTier = topTier2;
                            }
                        }
/* 2084 */              if (topTier != null) {
/* 2085 */                  SLog.m447d("ryango highest h264 {}", topTier.url);
/* 2086 */                  return topTier.url;
                        }
                    }
                    return null;
                }

/* 2092 */      private static String getHighestWebmUrl(List<VineVideoUrlTier> urls) {
/* 2093 */          if (urls != null && urls.size() > 0) {
/* 2094 */              VineVideoUrlTier topTier = null;
                        for (int i = 0; i < urls.size(); i++) {
/* 2096 */                  if (VineVideoUrlTier.FORMAT_WEBM.equals(urls.get(i).format) && (topTier == null || urls.get(i).rate > topTier.rate)) {
/* 2098 */                      VineVideoUrlTier topTier2 = urls.get(i);
                                topTier = topTier2;
                            }
                        }
/* 2102 */              if (topTier != null) {
/* 2103 */                  SLog.m447d("ryango highest webm {}", topTier.url);
/* 2104 */                  return topTier.url;
                        }
                    }
                    return null;
                }

/* 2111 */      private static VineSingleNotification parseNotification(JsonParser p) throws IOException {
/* 2112 */          JsonToken t = p.nextToken();
/* 2113 */          String avatarUrl = null;
/* 2114 */          String username = null;
/* 2115 */          String thumbnailUrl = null;
/* 2116 */          String comment = null;
/* 2117 */          long notificationId = 0;
/* 2118 */          long userId = 0;
/* 2119 */          long postId = 0;
/* 2120 */          long createdAt = 0;
/* 2121 */          long conversationId = 0;
/* 2122 */          long unreadMessageCount = 0;
/* 2123 */          long recipientUserId = 0;
/* 2124 */          boolean verified = false;
/* 2125 */          int notificationTypeId = 0;
/* 2126 */          boolean privateAccount = false;
/* 2127 */          boolean followRequested = false;
/* 2128 */          boolean following = false;
/* 2129 */          ArrayList<VineEntity> entities = null;
/* 2130 */          String onboard = null;
/* 2131 */          String url = null;
/* 2132 */          String title = null;
/* 2134 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 2212 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 2184 */                      if ("entities".equals(p.getCurrentName())) {
/* 2185 */                          JsonToken t2 = p.nextToken();
/* 2186 */                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 2187 */                              VineEntity entity = parseEntity(p);
/* 2188 */                              if (entities == null) {
/* 2189 */                                  entities = new ArrayList<>();
                                        }
/* 2191 */                              if (entity != null) {
/* 2192 */                                  entities.add(entity);
                                        }
/* 2194 */                              t2 = p.nextToken();
                                    }
                                } else {
/* 2197 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 2157 */                      String name = p.getCurrentName();
/* 2158 */                      if ("notificationTypeId".equals(name)) {
/* 2159 */                          notificationTypeId = p.getIntValue();
                                } else if ("notificationId".equals(name)) {
/* 2161 */                          notificationId = p.getLongValue();
                                } else if ("userId".equals(name)) {
/* 2163 */                          userId = p.getLongValue();
                                } else if ("postId".equals(name)) {
/* 2165 */                          postId = p.getLongValue();
                                } else if ("verified".equals(name)) {
/* 2167 */                          verified = p.getIntValue() == 1;
                                } else if ("private".equals(name)) {
/* 2169 */                          privateAccount = p.getIntValue() == 1;
                                } else if ("followRequested".equals(name)) {
/* 2171 */                          followRequested = p.getIntValue() == 1;
                                } else if ("following".equals(name)) {
/* 2173 */                          following = p.getIntValue() == 1;
                                } else if ("conversationId".equals(name)) {
/* 2175 */                          conversationId = p.getLongValue();
                                } else if ("unreadMessageCount".equals(name)) {
/* 2177 */                          unreadMessageCount = p.getLongValue();
                                } else if ("recipientUserId".equals(name)) {
/* 2179 */                          recipientUserId = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 2137 */                      String name2 = p.getCurrentName();
/* 2138 */                      if ("avatarUrl".equals(name2)) {
/* 2139 */                          avatarUrl = p.getText();
                                } else if ("body".equals(name2)) {
/* 2141 */                          username = Util.getUsernameFromActivity(p.getText());
/* 2142 */                          comment = p.getText();
                                } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name2)) {
/* 2144 */                          thumbnailUrl = p.getText();
                                } else if ("created".equals(name2)) {
/* 2146 */                          createdAt = DateTimeUtil.getTimeInMsFromString(p.getText(), "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
                                } else if ("onboard".equals(name2)) {
/* 2148 */                          onboard = p.getText();
                                } else if (PlusShare.KEY_CALL_TO_ACTION_URL.equals(name2)) {
/* 2150 */                          url = p.getText();
                                } else if ("title".equals(name2)) {
/* 2152 */                          title = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 2203 */                      String name3 = p.getCurrentName();
/* 2204 */                      if ("followRequested".equals(name3)) {
/* 2205 */                          followRequested = p.getBooleanValue();
                                } else if ("following".equals(name3)) {
/* 2207 */                          following = p.getBooleanValue();
                                }
                                break;
                        }
/* 2216 */              t = p.nextToken();
                    }
/* 2219 */          VineSingleNotification result = new VineSingleNotification(avatarUrl, username, thumbnailUrl, comment, notificationId, userId, postId, createdAt, verified, notificationTypeId, conversationId, entities, unreadMessageCount, recipientUserId, onboard, url, title);
/* 2223 */          if (entities != null) {
/* 2224 */              Util.validateAndFixEntities(entities);
                    }
/* 2226 */          if (following) {
/* 2227 */              result.setFollowing();
                    }
/* 2229 */          if (followRequested) {
/* 2230 */              result.setFollowRequested();
                    }
/* 2232 */          if (privateAccount) {
/* 2233 */              result.setPrivate();
                    }
/* 2236 */          return result;
                }

/* 2241 */      private static VineEverydayNotification parseEverydayNotification(JsonParser p) throws IOException {
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
/* 2323 */      public static VineMilestone parseMilestone(JsonParser p) throws IOException {
/* 2324 */          String backgroundImageUrl = null;
/* 2325 */          String backgroundVideoUrl = null;
/* 2326 */          String iconUrl = null;
/* 2327 */          String title = null;
/* 2328 */          String description = null;
/* 2329 */          String milestoneUrl = null;
/* 2330 */          int overlayColor = 0;
/* 2331 */          int blurRadius = 0;
/* 2332 */          float overlayAlpha = 0.0f;
/* 2333 */          JsonToken t = p.nextToken();
/* 2334 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_NUMBER_INT:
/* 2359 */                      if ("gaussianBlurRadius".equals(p.getCurrentName())) {
/* 2360 */                          blurRadius = p.getIntValue();
                                }
                                break;
                            case VALUE_STRING:
/* 2337 */                      if ("backgroundImageUrl".equals(p.getCurrentName())) {
/* 2338 */                          backgroundImageUrl = p.getText();
                                } else if ("backgroundVideoUrl".equals(p.getCurrentName())) {
/* 2340 */                          backgroundVideoUrl = p.getText();
                                } else if ("iconUrl".equals(p.getCurrentName())) {
/* 2342 */                          iconUrl = p.getText();
                                } else if ("title".equals(p.getCurrentName())) {
/* 2344 */                          title = p.getText();
                                } else if ("description".equals(p.getCurrentName())) {
/* 2346 */                          description = p.getText();
                                } else if ("milestoneUrl".equals(p.getCurrentName())) {
/* 2348 */                          milestoneUrl = p.getText();
                                } else if ("overlayAlpha".equals(p.getCurrentName())) {
/* 2350 */                          overlayAlpha = Float.parseFloat(p.getText());
                                } else if ("overlayColor".equals(p.getCurrentName())) {
/* 2352 */                          overlayColor = Integer.decode(p.getText()).intValue();
                                } else if ("gaussianBlurRadius".equals(p.getCurrentName())) {
/* 2354 */                          blurRadius = Integer.parseInt(p.getText());
                                }
                                break;
                        }
/* 2365 */              t = p.nextToken();
                    }
/* 2367 */          return new VineMilestone(backgroundImageUrl, backgroundVideoUrl, iconUrl, title, description, milestoneUrl, overlayColor, overlayAlpha, blurRadius);
                }

/* 2372 */      public static VineEntity parseEntity(JsonParser p) throws IOException {
/* 2373 */          String type = null;
/* 2374 */          String link = null;
/* 2375 */          String title = null;
/* 2376 */          long id = 0;
/* 2377 */          int start = -1;
/* 2378 */          int end = -1;
/* 2379 */          JsonToken t = p.nextToken();
/* 2380 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_ARRAY:
/* 2394 */                      if ("range".equals(p.getCurrentName())) {
/* 2395 */                          JsonToken t2 = p.nextToken();
/* 2396 */                          while (t2 != null && t2 != JsonToken.END_ARRAY) {
                                        switch (t2) {
                                            case VALUE_NUMBER_INT:
/* 2399 */                                      if (start == -1) {
/* 2400 */                                          start = p.getIntValue();
                                                } else {
/* 2402 */                                          end = p.getIntValue();
                                                }
                                                break;
                                        }
/* 2407 */                              t2 = p.nextToken();
                                    }
                                } else {
/* 2410 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_NUMBER_INT:
                                break;
                            case VALUE_STRING:
/* 2383 */                      String cn = p.getCurrentName();
/* 2384 */                      if ("type".equals(cn)) {
/* 2385 */                          type = p.getText();
                                    continue;
                                } else if ("link".equals(cn)) {
/* 2387 */                          link = p.getText();
                                } else if ("title".equals(cn)) {
/* 2389 */                          title = p.getText();
                                }
/* 2423 */                      t = p.nextToken();
                                break;
                            default:
                                continue;
                        }
/* 2414 */              if ("id".equals(p.getCurrentName())) {
/* 2415 */                  id = p.getLongValue();
                        }
/* 2423 */              t = p.nextToken();
                    }
/* 2425 */          return new VineEntity(type, title, link, start, end, id);
                }

/* 2428 */      private static ServerStatus parseServerStatus(JsonParser p) throws IOException {
/* 2429 */          String uploadType = null;
/* 2430 */          String status = null;
/* 2431 */          String message = null;
/* 2433 */          String staticTimelineUrl = null;
/* 2434 */          JsonToken t = p.nextToken();
/* 2435 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 2439 */                      p.skipChildren();
                                break;
                            case VALUE_STRING:
/* 2443 */                      String cn = p.getCurrentName();
/* 2444 */                      if ("uploadType".equals(cn)) {
/* 2445 */                          uploadType = p.getText();
                                } else if ("status".equals(cn)) {
/* 2447 */                          status = p.getText();
                                } else if ("message".equals(cn)) {
/* 2449 */                          message = p.getText();
                                } else if ("staticTimelineUrl".equals(cn)) {
/* 2451 */                          staticTimelineUrl = p.getText();
                                }
                                break;
                        }
/* 2456 */              t = p.nextToken();
                    }
/* 2458 */          return new ServerStatus(uploadType, status, message, null, staticTimelineUrl);
                }

/* 2461 */      private static VineClientFlags parseClientFlags(JsonParser p) throws IOException {
/* 2462 */          String messageTitle = null;
/* 2463 */          String messageText = null;
/* 2464 */          String apiHost = null;
/* 2465 */          String rtcHost = null;
/* 2466 */          String mediaHost = null;
/* 2467 */          String exploreHost = null;
/* 2468 */          long ttlSeconds = 0;
/* 2469 */          boolean doubleTapToLikeOnPause = true;
/* 2470 */          boolean prefetchEnabled = false;
/* 2471 */          boolean useVinePlayer = SdkVideoView.getDefaultGlobalVinePlayer();
/* 2473 */          JsonToken t = p.nextToken();
/* 2474 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 2522 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 2508 */                      String cn = p.getCurrentName();
/* 2509 */                      if ("doubleTapToLikeOnPause".equals(cn)) {
/* 2510 */                          doubleTapToLikeOnPause = p.getIntValue() == 1;
                                } else if ("prefetchEnabled".equals(cn)) {
/* 2512 */                          prefetchEnabled = p.getIntValue() == 1;
                                } else if ("useVinePlayer".equals(cn)) {
/* 2514 */                          useVinePlayer = p.getIntValue() == 1;
                                } else if ("ttl_s".equals(p.getCurrentName())) {
/* 2516 */                          ttlSeconds = p.getLongValue();
                                }
                                break;
                            case VALUE_STRING:
/* 2490 */                      String cn2 = p.getCurrentName();
/* 2491 */                      if ("messageTitle".equals(cn2)) {
/* 2492 */                          messageTitle = p.getText();
                                } else if ("messageText".equals(cn2)) {
/* 2494 */                          messageText = p.getText();
                                } else if ("apiHost".equals(cn2)) {
/* 2496 */                          apiHost = p.getText();
                                } else if ("rtcHost".equals(cn2)) {
/* 2498 */                          rtcHost = p.getText();
                                } else if ("mediaHost".equals(cn2)) {
/* 2500 */                          mediaHost = p.getText();
                                } else if ("exploreHost".equals(cn2)) {
/* 2502 */                          exploreHost = p.getText();
                                }
                                break;
                            case VALUE_TRUE:
                            case VALUE_FALSE:
/* 2479 */                      String cn3 = p.getCurrentName();
/* 2480 */                      if ("doubleTapToLikeOnPause".equals(cn3)) {
/* 2481 */                          doubleTapToLikeOnPause = p.getBooleanValue();
                                } else if ("prefetchEnabled".equals(cn3)) {
/* 2483 */                          prefetchEnabled = p.getBooleanValue();
                                } else if ("useVinePlayer".equals(cn3)) {
/* 2485 */                          useVinePlayer = p.getBooleanValue();
                                }
                                break;
                        }
/* 2526 */              t = p.nextToken();
                    }
/* 2529 */          return new VineClientFlags(messageTitle, messageText, apiHost, rtcHost, mediaHost, exploreHost, ttlSeconds, doubleTapToLikeOnPause, prefetchEnabled, useVinePlayer);
                }

/* 2533 */      private static VinePostResponse parseVinePostResponse(JsonParser p) throws IOException {
/* 2534 */          long postId = 0;
/* 2535 */          long createdAt = 0;
/* 2536 */          JsonToken t = p.nextToken();
/* 2537 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
                            case START_ARRAY:
/* 2550 */                      p.skipChildren();
                                break;
                            case VALUE_NUMBER_INT:
/* 2540 */                      String cn = p.getCurrentName();
/* 2541 */                      if ("postId".equals(cn)) {
/* 2542 */                          postId = p.getLongValue();
                                } else if ("createdAt".equals(cn)) {
/* 2544 */                          createdAt = p.getLongValue();
                                }
                                break;
                        }
/* 2554 */              t = p.nextToken();
                    }
/* 2556 */          return new VinePostResponse(postId, createdAt);
                }

/* 2560 */      private static VinePrivateMessagePostResponseWithUsers parsePrivateMessageResponse(JsonParser p) throws IOException {
/* 2561 */          ArrayList<VinePrivateMessageResponse> responses = new ArrayList<>();
/* 2562 */          ArrayList<VineRecipient> recipients = new ArrayList<>();
/* 2563 */          ArrayList<VineUser> users = null;
/* 2564 */          VinePost post = null;
/* 2565 */          int networkType = 1;
/* 2567 */          JsonToken t = p.nextToken();
/* 2568 */          while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case START_OBJECT:
/* 2697 */                      p.skipChildren();
                                break;
                            case START_ARRAY:
/* 2579 */                      if ("messages".equals(p.getCurrentName())) {
/* 2580 */                          while (t != null && t != JsonToken.END_ARRAY) {
                                        switch (t) {
                                            case START_OBJECT:
/* 2583 */                                      VineError error = null;
/* 2584 */                                      String videoUrl = null;
/* 2585 */                                      String thumbnailUrl = null;
/* 2586 */                                      String shareUrl = null;
/* 2587 */                                      String email = null;
/* 2588 */                                      String phone = null;
/* 2589 */                                      String recipientIdString = null;
/* 2590 */                                      String videoWebmUrl = null;
/* 2591 */                                      long conversationId = 0;
/* 2592 */                                      long messageId = 0;
/* 2593 */                                      long userId = -1;
/* 2594 */                                      while (t != null && t != JsonToken.END_OBJECT) {
/* 2595 */                                          String name = p.getCurrentName();
                                                    switch (t) {
                                                        case START_OBJECT:
/* 2629 */                                                  String objName = p.getCurrentName();
/* 2630 */                                                  if ("to".equals(objName)) {
/* 2631 */                                                      while (t != null && t != JsonToken.END_OBJECT) {
/* 2632 */                                                          String toName = p.getCurrentName();
                                                                    switch (t) {
                                                                        case VALUE_NUMBER_INT:
/* 2645 */                                                                  if (toName.equals("userId")) {
/* 2646 */                                                                      userId = p.getLongValue();
                                                                            }
                                                                            break;
                                                                        case VALUE_STRING:
/* 2635 */                                                                  if (toName.equals("email")) {
/* 2636 */                                                                      email = p.getText();
                                                                            } else if (toName.equals(VineRecipient.KEY_PHONE)) {
/* 2638 */                                                                      phone = p.getText();
                                                                            } else if (toName.equals(VineRecipient.KEY_RECIPIENT_ID)) {
/* 2640 */                                                                      recipientIdString = p.getText();
                                                                            }
                                                                            break;
                                                                    }
/* 2651 */                                                          t = p.nextToken();
                                                                }
                                                            } else if ("error".equals(objName)) {
/* 2654 */                                                      error = parseMessageError(p);
                                                            } else if ("post".equals(objName)) {
/* 2656 */                                                      post = parsePost(p);
                                                            }
                                                            break;
                                                        case START_ARRAY:
/* 2618 */                                                  if ("videoUrls".equals(p.getCurrentName())) {
/* 2619 */                                                      List<VineVideoUrlTier> urls = parseVideoUrls(p);
/* 2620 */                                                      videoUrl = getHighestH264Url(urls);
/* 2621 */                                                      videoWebmUrl = getHighestWebmUrl(urls);
/* 2622 */                                                      if (TextUtils.isEmpty(videoUrl)) {
/* 2623 */                                                          videoUrl = videoWebmUrl;
                                                                }
                                                            }
                                                            break;
                                                        case VALUE_NUMBER_INT:
/* 2610 */                                                  if ("conversationId".equals(name)) {
/* 2611 */                                                      conversationId = p.getLongValue();
                                                            } else if ("messageId".equals(name)) {
/* 2613 */                                                      messageId = p.getLongValue();
                                                            }
                                                            break;
                                                        case VALUE_STRING:
/* 2598 */                                                  if ("videoUrl".equals(name)) {
/* 2599 */                                                      videoUrl = p.getText();
                                                            } else if (PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL.equals(name)) {
/* 2601 */                                                      thumbnailUrl = p.getText();
                                                            } else if ("shareUrl".equals(name)) {
/* 2603 */                                                      shareUrl = p.getText();
                                                            } else if ("videoWebmUrl".equals(name)) {
/* 2605 */                                                      videoWebmUrl = p.getText();
                                                            }
                                                            break;
                                                    }
/* 2661 */                                          t = p.nextToken();
                                                }
/* 2663 */                                      long recipientId = -1;
/* 2664 */                                      if (!TextUtils.isEmpty(recipientIdString)) {
/* 2665 */                                          recipientId = Long.valueOf(recipientIdString).longValue();
                                                }
/* 2668 */                                      VineRecipient recipient = null;
/* 2669 */                                      if (email != null) {
/* 2670 */                                          recipient = VineRecipient.fromEmail(null, userId, email, recipientId);
                                                } else if (phone != null) {
/* 2672 */                                          recipient = VineRecipient.fromPhone(null, userId, phone, recipientId);
                                                } else if (userId > 0) {
/* 2674 */                                          recipient = VineRecipient.fromUser(null, userId, 0, recipientId);
                                                } else {
/* 2676 */                                          CrashUtil.log("No email, phone, or userId for recipient for message {}", Long.valueOf(messageId));
                                                }
/* 2678 */                                      if (TextUtils.isEmpty(videoUrl)) {
/* 2679 */                                          videoUrl = videoWebmUrl;
                                                }
/* 2681 */                                      recipients.add(recipient);
/* 2682 */                                      responses.add(new VinePrivateMessageResponse(recipient, conversationId, messageId, videoUrl, thumbnailUrl, shareUrl, error, networkType));
                                                break;
                                        }
/* 2687 */                              t = p.nextToken();
                                    }
                                } else if ("users".equals(p.getCurrentName())) {
/* 2690 */                          users = parseUsersArray(p, null);
                                } else {
/* 2692 */                          p.skipChildren();
                                }
                                break;
                            case VALUE_STRING:
/* 2571 */                      if ("inbox".equals(p.getCurrentName()) && CommonUtil.FIELD_TYPE_OTHER.equals(p.getValueAsString())) {
/* 2573 */                          networkType = 2;
                                }
                                break;
                        }
/* 2701 */              t = p.nextToken();
                    }
/* 2704 */          return new VinePrivateMessagePostResponseWithUsers(responses, recipients, users, post);
                }

/* 2709 */      public static ArrayList<ComplaintMenuOption> parseComplaintMenu(JsonParser p) throws IOException {
/* 2710 */          ArrayList<ComplaintMenuOption> complaintMenuOptions = new ArrayList<>();
/* 2711 */          JsonToken t = p.nextToken();
/* 2712 */          while (t != null && t != JsonToken.END_OBJECT) {
/* 2713 */              p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
/* 2716 */                      String type = p.getCurrentName();
/* 2717 */                      ComplaintMenuOption c = parseComplaintMenuOption(p, type);
/* 2718 */                      complaintMenuOptions.add(c);
                                break;
                        }
/* 2724 */              t = p.nextToken();
                    }
/* 2726 */          return complaintMenuOptions;
                }

/* 2730 */      public static ComplaintMenuOption parseComplaintMenuOption(JsonParser p, String type) throws IOException {
/* 2731 */          ComplaintMenuOption option = new ComplaintMenuOption();
/* 2732 */          option.complaintType = type;
/* 2733 */          JsonToken t = p.nextToken();
/* 2734 */          while (t != JsonToken.END_OBJECT) {
/* 2735 */              String tokenName = p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
/* 2738 */                      if ("choices".equals(tokenName)) {
/* 2739 */                          option.complaintChoices = parseComplaintChoiceArray(p);
                                } else if ("prompt".equals(tokenName)) {
/* 2741 */                          p.nextToken();
/* 2742 */                          option.prompt = p.getText();
                                }
                                break;
                        }
/* 2750 */              t = p.nextToken();
                    }
/* 2752 */          return option;
                }

/* 2756 */      public static ComplaintMenuOption.ComplaintChoice[] parseComplaintChoiceArray(JsonParser p) throws IOException {
/* 2757 */          ArrayList<ComplaintMenuOption.ComplaintChoice> choices = new ArrayList<>();
/* 2758 */          JsonToken t = p.nextToken();
/* 2759 */          while (t != JsonToken.END_ARRAY) {
/* 2760 */              p.getCurrentName();
                        switch (t) {
                            case START_OBJECT:
/* 2763 */                      ComplaintMenuOption.ComplaintChoice choice = parseComplaintChoice(p);
/* 2764 */                      choices.add(choice);
                                break;
                        }
/* 2770 */              t = p.nextToken();
                    }
/* 2772 */          return (ComplaintMenuOption.ComplaintChoice[]) choices.toArray(new ComplaintMenuOption.ComplaintChoice[choices.size()]);
                }

/* 2776 */      public static ComplaintMenuOption.ComplaintChoice parseComplaintChoice(JsonParser p) throws IOException {
/* 2777 */          ComplaintMenuOption.ComplaintChoice choice = new ComplaintMenuOption.ComplaintChoice();
/* 2778 */          JsonToken t = p.nextToken();
/* 2779 */          while (t != JsonToken.END_OBJECT) {
/* 2780 */              String tokenName = p.getCurrentName();
                        switch (t) {
                            case FIELD_NAME:
/* 2783 */                      if ("title".equals(tokenName)) {
/* 2784 */                          p.nextToken();
/* 2785 */                          choice.title = p.getText();
                                } else if (Vine.Settings.PROP_VALUE.equals(tokenName)) {
/* 2787 */                          p.nextToken();
/* 2788 */                          choice.value = p.getText();
                                } else if ("confirmation".equals(tokenName)) {
/* 2791 */                          p.nextToken();
/* 2792 */                          choice.confirmation = p.getText();
                                }
                                break;
                        }
/* 2799 */              t = p.nextToken();
                    }
/* 2801 */          return choice;
                }

/* 2805 */      public static JsonParser createParser(InputStream inputStream) throws IOException {
/* 2806 */          JsonParser jp = JSON_FACTORY.createJsonParser(inputStream);
/* 2807 */          jp.nextToken();
/* 2808 */          return jp;
                }

/* 2811 */      public static JsonParser createParser(String inputString) throws IOException {
/* 2812 */          JsonParser jp = JSON_FACTORY.createJsonParser(inputString);
/* 2813 */          jp.nextToken();
/* 2814 */          return jp;
                }
            }
