            package co.vine.android.util;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import co.vine.android.C0257R;
            import co.vine.android.CommentsActivity;
            import co.vine.android.ExploreChannelsActivity;
            import co.vine.android.ExploreVideoListActivity;
            import co.vine.android.FindFriendsActivity;
            import co.vine.android.HashtagActivity;
            import co.vine.android.ProfileActivity;
            import co.vine.android.SettingsActivity;
            import co.vine.android.SingleActivity;
            import co.vine.android.UsersActivity;
            import co.vine.android.WebViewActivity;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 62 */    public class LinkDispatcher {
                private static final String CHANNELS = "channels";
                private static final String COMMENTS = "comments";
                private static final String CONVERSATIONS = "conversations";
                private static final String FIND_FRIENDS = "find-friends";
                private static final String LIKES = "likes";
                private static final String POPULAR_NOW = "popular-now";
                private static final String POST = "post";
                private static final String POSTS = "posts";
                private static final String REPOSTS = "reposts";
                private static final String SCHEME_HTTP = "http";
                private static final String SCHEME_HTTPS = "https";
                private static final String SCHEME_VINE = "vine";
                private static final String SCHEME_VINE_DEV = "vine-dev";
                private static final String SETTINGS = "settings";
                private static final String TAG = "tag";
                private static final String TIMELINES = "timelines";
                private static final String TWITTER = "tw";
                private static final String USER = "user";
                private static final String USER_ID = "user-id";

/* 63 */        public static void dispatch(String link, Context context) {
/* 64 */            dispatch(Uri.parse(link), context);
                }

/* 67 */        public static void dispatch(Uri linkUri, Context context) {
/* 68 */            if (linkUri != null) {
/* 72 */                String scheme = linkUri.getScheme();
/* 74 */                if ("vine".equals(scheme) || SCHEME_VINE_DEV.equals(scheme)) {
/* 75 */                    dispatchVineScheme(linkUri, context);
                        } else if (SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme)) {
/* 77 */                    WebViewActivity.start(context, linkUri);
                        }
                    }
                }

/* 81 */        private static void dispatchVineScheme(Uri linkUri, Context context) {
                    byte b = 1;
/* 82 */            String host = linkUri.getHost();
/* 83 */            List<String> pathSegments = linkUri.getPathSegments();
                    switch (host) {
                        case "channels":
/* 86 */                    if (pathSegments.size() > 0) {
/* 87 */                        Intent i = new Intent(context, (Class<?>) ExploreChannelsActivity.class);
/* 88 */                        i.setData(linkUri);
/* 89 */                        context.startActivity(i);
                                break;
                            }
                            break;
                        case "find-friends":
/* 94 */                    context.startActivity(new Intent(context, (Class<?>) FindFriendsActivity.class));
                            break;
                        case "user":
                        case "user-id":
/* 99 */                    startProfileActivityForLinkUri(context, linkUri);
                            break;
                        case "post":
                            try {
/* 106 */                       SingleActivity.start(context, Long.parseLong(linkUri.getLastPathSegment()));
                                break;
                            } catch (NumberFormatException e) {
/* 108 */                       Util.showCenteredToast(context, C0257R.string.invalid_link);
/* 167 */                       return;
                            }
                        case "posts":
/* 116 */                   long postId = Long.parseLong(pathSegments.get(0));
/* 117 */                   String destination = linkUri.getLastPathSegment();
                            switch (destination) {
                                case "comments":
/* 120 */                           CommentsActivity.start(context, postId, -1L, -1L, true);
                                    break;
                                case "likes":
/* 124 */                           UsersActivity.startUserListForLikers(context, postId);
                                    break;
                                case "reposts":
/* 128 */                           UsersActivity.startUserListForReviners(context, postId);
                                    break;
                            }
                            break;
                        case "tag":
/* 135 */                   HashtagActivity.start(context, "#" + linkUri.getLastPathSegment());
                            break;
                        case "timelines":
                        case "popular-now":
/* 140 */                   ExploreVideoListActivity.start(context, linkUri);
                            break;
                        case "tw":
/* 150 */                   if (pathSegments != null && pathSegments.size() > 0) {
/* 152 */                       String str = pathSegments.get(0);
                                switch (str.hashCode()) {
                                    case 3446944:
                                        if (!str.equals("post")) {
                                            b = -1;
                                        }
                                        break;
                                    case 3599307:
/* 152 */                               b = !str.equals("user") ? (byte) -1 : (byte) 0;
                                        break;
                                    default:
                                        b = -1;
                                        break;
                                }
                                switch (b) {
                                    case 0:
/* 154 */                               startProfileActivityForLinkUri(context, linkUri);
                                        break;
                                    case 1:
/* 158 */                               startSinglePostActivityForLinkUri(context, linkUri);
                                        break;
                                }
                            }
                            break;
                    }
                }

/* 170 */       private static void startSinglePostActivityForLinkUri(Context context, Uri linkUri) {
                    try {
/* 171 */               long postId = Long.parseLong(linkUri.getLastPathSegment());
/* 172 */               SingleActivity.start(context, postId);
                    } catch (NumberFormatException e) {
/* 174 */               Util.showCenteredToast(context, C0257R.string.invalid_link);
                    }
                }

/* 178 */       private static void startProfileActivityForLinkUri(Context context, Uri linkUri) {
/* 179 */           String lastPathSegment = linkUri.getLastPathSegment();
/* 180 */           if ("settings".equals(lastPathSegment)) {
/* 181 */               context.startActivity(new Intent(context, (Class<?>) SettingsActivity.class));
/* 189 */               return;
                    }
                    try {
/* 184 */               ProfileActivity.start(context, Long.parseLong(lastPathSegment), "LinkDispatcher");
                    } catch (NumberFormatException e) {
/* 186 */               Util.showCenteredToast(context, C0257R.string.invalid_link);
                    }
                }
            }
