            package co.vine.android;

            import android.app.PendingIntent;
            import android.appwidget.AppWidgetManager;
            import android.appwidget.AppWidgetProvider;
            import android.content.Context;
            import android.content.Intent;
            import android.widget.RemoteViews;
            import co.vine.android.service.ResourceService;
            import co.vine.android.util.analytics.FlurryUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class CameraWidgetProvider extends AppWidgetProvider {
                @Override // android.appwidget.AppWidgetProvider
/* 19 */        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
/* 20 */            RemoteViews views = new RemoteViews(context.getPackageName(), C0257R.layout.camera_widget);
                    for (int id : appWidgetIds) {
/* 22 */                Intent intent = AbstractRecordingActivity.getIntentForGeneric(context, -1, "CameraWidget");
/* 23 */                intent.setAction(StartActivity.ACTION_RECORD);
/* 24 */                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
/* 25 */                views.setOnClickPendingIntent(C0257R.id.widget_image, pendingIntent);
/* 26 */                appWidgetManager.updateAppWidget(id, views);
                    }
/* 28 */            context.startService(new Intent(context, (Class<?>) ResourceService.class));
                }

                @Override // android.appwidget.AppWidgetProvider
/* 32 */        public void onDeleted(Context context, int[] appWidgetIds) {
/* 33 */            FlurryUtils.trackCameraWidgetRemoved();
                }
            }
