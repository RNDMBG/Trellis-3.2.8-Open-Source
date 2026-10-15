            package co.vine.android.util;

            import android.content.Context;
            import android.content.Intent;
            import android.net.Uri;
            import co.vine.android.R;
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
            
            
    public class LinkDispatcher {
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

        public static void dispatch(String link, Context context) {
            dispatch(Uri.parse(link), context);
                }

        public static void dispatch(Uri linkUri, Context context) {
            if (linkUri != null) {
                String scheme = linkUri.getScheme();
                if ("vine".equals(scheme) || SCHEME_VINE_DEV.equals(scheme)) {
                    dispatchVineScheme(linkUri, context);
                        } else if (SCHEME_HTTP.equals(scheme) || SCHEME_HTTPS.equals(scheme)) {
                    WebViewActivity.start(context, linkUri);
                        }
                    }
                }

        private static void dispatchVineScheme(Uri linkUri, Context context) {
                    byte b = 1;
            String host = linkUri.getHost();
            List<String> pathSegments = linkUri.getPathSegments();
                    switch (host) {
                        case "channels":
                    if (pathSegments.size() > 0) {
                        Intent i = new Intent(context, (Class<?>) ExploreChannelsActivity.class);
                        i.setData(linkUri);
                        context.startActivity(i);
                                break;
                            }
                            break;
                        case "find-friends":
                    context.startActivity(new Intent(context, (Class<?>) FindFriendsActivity.class));
                            break;
                        case "user":
                        case "user-id":
                    startProfileActivityForLinkUri(context, linkUri);
                            break;
                        case "post":
                            try {
                       SingleActivity.start(context, Long.parseLong(linkUri.getLastPathSegment()));
                                break;
                            } catch (NumberFormatException e) {
                       Util.showCenteredToast(context, R.string.invalid_link);
                       return;
                            }
                        case "posts":
                   long postId = Long.parseLong(pathSegments.get(0));
                   String destination = linkUri.getLastPathSegment();
                            switch (destination) {
                                case "comments":
                           CommentsActivity.start(context, postId, -1L, -1L, true);
                                    break;
                                case "likes":
                           UsersActivity.startUserListForLikers(context, postId);
                                    break;
                                case "reposts":
                           UsersActivity.startUserListForReviners(context, postId);
                                    break;
                            }
                            break;
                        case "tag":
                   HashtagActivity.start(context, "#" + linkUri.getLastPathSegment());
                            break;
                        case "timelines":
                        case "popular-now":
                   ExploreVideoListActivity.start(context, linkUri);
                            break;
                        case "tw":
                   if (pathSegments != null && pathSegments.size() > 0) {
                       String str = pathSegments.get(0);
                                switch (str.hashCode()) {
                                    case 3446944:
                                        if (!str.equals("post")) {
                                            b = -1;
                                        }
                                        break;
                                    case 3599307:
                               b = !str.equals("user") ? (byte) -1 : (byte) 0;
                                        break;
                                    default:
                                        b = -1;
                                        break;
                                }
                                switch (b) {
                                    case 0:
                               startProfileActivityForLinkUri(context, linkUri);
                                        break;
                                    case 1:
                               startSinglePostActivityForLinkUri(context, linkUri);
                                        break;
                                }
                            }
                            break;
                    }
                }

       private static void startSinglePostActivityForLinkUri(Context context, Uri linkUri) {
                    try {
               long postId = Long.parseLong(linkUri.getLastPathSegment());
               SingleActivity.start(context, postId);
                    } catch (NumberFormatException e) {
               Util.showCenteredToast(context, R.string.invalid_link);
                    }
                }

       private static void startProfileActivityForLinkUri(Context context, Uri linkUri) {
           String lastPathSegment = linkUri.getLastPathSegment();
           if ("settings".equals(lastPathSegment)) {
               context.startActivity(new Intent(context, (Class<?>) SettingsActivity.class));
               return;
                    }
                    try {
               ProfileActivity.start(context, Long.parseLong(lastPathSegment), "LinkDispatcher");
                    } catch (NumberFormatException e) {
               Util.showCenteredToast(context, R.string.invalid_link);
                    }
                }
            }
