            package co.vine.android.api;

            import com.fasterxml.jackson.core.JsonParser;
            import com.fasterxml.jackson.core.JsonToken;
            import java.io.IOException;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class VineUploadParsers {
/* 13 */        public static PostInfo parsePostInfo(JsonParser p) throws IOException {
/* 14 */            boolean postToTwitter = false;
/* 15 */            boolean postToFacebook = false;
/* 16 */            long channelId = -1;
/* 17 */            long postId = -1;
/* 18 */            long created = 0;
/* 19 */            int maxLoops = 0;
/* 20 */            String description = "";
/* 21 */            String message = "";
/* 22 */            String videoUrl = "";
/* 23 */            String thumbUrl = "";
/* 24 */            ArrayList<VineEntity> entities = null;
/* 25 */            ArrayList<VineRecipient> recipients = null;
/* 27 */            JsonToken t = p.nextToken();
/* 28 */            while (t != null && t != JsonToken.END_OBJECT) {
                        switch (t) {
                            case VALUE_STRING:
/* 31 */                        if ("description".equals(p.getCurrentName())) {
/* 32 */                            description = p.getText();
                                }
/* 34 */                        if ("message".equals(p.getCurrentName())) {
/* 35 */                            message = p.getText();
                                }
/* 37 */                        if ("videoUrl".equals(p.getCurrentName())) {
/* 38 */                            videoUrl = p.getText();
                                }
/* 40 */                        if ("thumbUrl".equals(p.getCurrentName())) {
/* 41 */                            thumbUrl = p.getText();
                                }
                                break;
                            case VALUE_FALSE:
                            case VALUE_TRUE:
/* 47 */                        if ("postToTwitter".equals(p.getCurrentName())) {
/* 48 */                            postToTwitter = p.getBooleanValue();
                                } else if ("postToFacebook".equals(p.getCurrentName())) {
/* 50 */                            postToFacebook = p.getBooleanValue();
                                }
                                break;
                            case VALUE_NUMBER_INT:
/* 55 */                        if ("channelId".equals(p.getCurrentName())) {
/* 56 */                            channelId = p.getLongValue();
                                } else if ("created".equals(p.getCurrentName())) {
/* 58 */                            created = p.getLongValue();
                                } else if ("maxLoops".equals(p.getCurrentName())) {
/* 60 */                            maxLoops = p.getIntValue();
                                } else if ("postId".equals(p.getCurrentName())) {
/* 62 */                            postId = p.getLongValue();
                                }
                                break;
                            case START_ARRAY:
/* 67 */                        String name = p.getCurrentName();
/* 68 */                        if ("entities".equals(name)) {
/* 69 */                            JsonToken t2 = p.nextToken();
/* 70 */                            while (t2 != null && t2 != JsonToken.END_ARRAY) {
/* 71 */                                VineEntity entity = VineParsers.parseEntity(p);
/* 72 */                                if (entities == null) {
/* 73 */                                    entities = new ArrayList<>();
                                        }
/* 75 */                                if (entity != null) {
/* 76 */                                    entities.add(entity);
                                        }
/* 78 */                                t2 = p.nextToken();
                                    }
                                } else if ("recipients".equals(name)) {
/* 81 */                            JsonToken t3 = p.nextToken();
/* 82 */                            while (t3 != null && t3 != JsonToken.END_ARRAY) {
/* 83 */                                VineRecipient recipient = VineRecipient.fromJson(p);
/* 84 */                                if (recipients == null) {
/* 85 */                                    recipients = new ArrayList<>();
                                        }
/* 87 */                                if (recipient != null) {
/* 88 */                                    recipients.add(recipient);
                                        }
/* 90 */                                t3 = p.nextToken();
                                    }
                                } else {
/* 93 */                            p.skipChildren();
                                }
                                break;
                        }
/* 101 */               t = p.nextToken();
                    }
/* 103 */           return new PostInfo(description, postToTwitter, postToFacebook, channelId, entities, message, postId, videoUrl, thumbUrl, created, recipients, maxLoops);
                }
            }
