            package co.vine.android;

            import android.app.PendingIntent;
            import android.appwidget.AppWidgetManager;
            import android.appwidget.AppWidgetProvider;
            import android.content.Context;
            import android.content.Intent;
            import android.widget.RemoteViews;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.analytics.FlurryUtils;
            
            
    public class CameraWidgetProvider extends AppWidgetProvider {
                @Override 
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.camera_widget);
                    for (int id : appWidgetIds) {
                Intent intent = AbstractRecordingActivity.getIntentForGeneric(context, -1, "CameraWidget");
                intent.setAction(StartActivity.ACTION_RECORD);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);
                appWidgetManager.updateAppWidget(id, views);
                    }
            context.startService(new Intent(context, (Class<?>) ResourceService.class));
                }

                @Override 
        public void onDeleted(Context context, int[] appWidgetIds) {
            FlurryUtils.trackCameraWidgetRemoved();
                }
            }
