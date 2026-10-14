            package co.vine.android;

            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.cache.image.ImageKey;
            
            
            public class CommentViewHolder {
                String commentId;
                TextView content;
                ImageKey imageKey;
                ImageView profileImage;
                TextView timestamp;
                long userId;
                String username;
            }
