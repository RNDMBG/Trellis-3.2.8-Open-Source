            package android.support.v4.app;

            import android.app.Activity;
            import android.app.PendingIntent;
            import android.content.ComponentName;
            import android.content.Context;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.os.Build;
            import android.os.Bundle;
            import android.support.v4.content.ContextCompat;
            import android.util.Log;
            import java.util.ArrayList;
            import java.util.Iterator;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    public class TaskStackBuilder implements Iterable<Intent> {
                private static final TaskStackBuilderImpl IMPL;
                private static final String TAG = "TaskStackBuilder";
                private final ArrayList<Intent> mIntents = new ArrayList<>();
                private final Context mSourceContext;

                public interface SupportParentable {
                    Intent getSupportParentActivityIntent();
                }

                interface TaskStackBuilderImpl {
                    PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle);
                }

/* 79 */        static class TaskStackBuilderImplBase implements TaskStackBuilderImpl {
/* 80 */            TaskStackBuilderImplBase() {
                    }

                    @Override // android.support.v4.app.TaskStackBuilder.TaskStackBuilderImpl
/* 83 */            public PendingIntent getPendingIntent(Context context, Intent[] intents, int requestCode, int flags, Bundle options) {
/* 84 */                Intent topIntent = new Intent(intents[intents.length - 1]);
/* 85 */                topIntent.addFlags(268435456);
/* 86 */                return PendingIntent.getActivity(context, requestCode, topIntent, flags);
                    }
                }

/* 88 */        static class TaskStackBuilderImplHoneycomb implements TaskStackBuilderImpl {
/* 89 */            TaskStackBuilderImplHoneycomb() {
                    }

                    @Override // android.support.v4.app.TaskStackBuilder.TaskStackBuilderImpl
/* 92 */            public PendingIntent getPendingIntent(Context context, Intent[] intents, int requestCode, int flags, Bundle options) {
/* 93 */                intents[0] = new Intent(intents[0]).addFlags(268484608);
/* 96 */                return TaskStackBuilderHoneycomb.getActivitiesPendingIntent(context, requestCode, intents, flags);
                    }
                }

/* 99 */        static class TaskStackBuilderImplJellybean implements TaskStackBuilderImpl {
/* 100 */           TaskStackBuilderImplJellybean() {
                    }

                    @Override // android.support.v4.app.TaskStackBuilder.TaskStackBuilderImpl
/* 103 */           public PendingIntent getPendingIntent(Context context, Intent[] intents, int requestCode, int flags, Bundle options) {
/* 104 */               intents[0] = new Intent(intents[0]).addFlags(268484608);
/* 107 */               return TaskStackBuilderJellybean.getActivitiesPendingIntent(context, requestCode, intents, flags, options);
                    }
                }

                static {
/* 115 */           if (Build.VERSION.SDK_INT >= 11) {
/* 116 */               IMPL = new TaskStackBuilderImplHoneycomb();
                    } else {
/* 118 */               IMPL = new TaskStackBuilderImplBase();
                    }
                }

/* 124 */       private TaskStackBuilder(Context a) {
/* 126 */           this.mSourceContext = a;
                }

/* 136 */       public static TaskStackBuilder create(Context context) {
/* 137 */           return new TaskStackBuilder(context);
                }

/* 149 */       public static TaskStackBuilder from(Context context) {
/* 150 */           return create(context);
                }

/* 160 */       public TaskStackBuilder addNextIntent(Intent nextIntent) {
/* 161 */           this.mIntents.add(nextIntent);
/* 162 */           return this;
                }

/* 177 */       public TaskStackBuilder addNextIntentWithParentStack(Intent nextIntent) {
/* 178 */           ComponentName target = nextIntent.getComponent();
/* 179 */           if (target == null) {
/* 180 */               target = nextIntent.resolveActivity(this.mSourceContext.getPackageManager());
                    }
/* 182 */           if (target != null) {
/* 183 */               addParentStack(target);
                    }
/* 185 */           addNextIntent(nextIntent);
/* 186 */           return this;
                }

                /* JADX WARN: Multi-variable type inference failed */
/* 196 */       public TaskStackBuilder addParentStack(Activity activity) {
/* 197 */           Intent parent = null;
/* 198 */           if (activity instanceof SupportParentable) {
/* 199 */               parent = ((SupportParentable) activity).getSupportParentActivityIntent();
                    }
/* 201 */           if (parent == null) {
/* 202 */               parent = NavUtils.getParentActivityIntent(activity);
                    }
/* 205 */           if (parent != null) {
/* 208 */               ComponentName target = parent.getComponent();
/* 209 */               if (target == null) {
/* 210 */                   target = parent.resolveActivity(this.mSourceContext.getPackageManager());
                        }
/* 212 */               addParentStack(target);
/* 213 */               addNextIntent(parent);
                    }
/* 215 */           return this;
                }

/* 225 */       public TaskStackBuilder addParentStack(Class<?> sourceActivityClass) {
/* 226 */           return addParentStack(new ComponentName(this.mSourceContext, sourceActivityClass));
                }

/* 237 */       public TaskStackBuilder addParentStack(ComponentName sourceActivityName) {
/* 238 */           int insertAt = this.mIntents.size();
                    try {
/* 240 */               Intent parent = NavUtils.getParentActivityIntent(this.mSourceContext, sourceActivityName);
/* 241 */               while (parent != null) {
/* 242 */                   this.mIntents.add(insertAt, parent);
/* 243 */                   parent = NavUtils.getParentActivityIntent(this.mSourceContext, parent.getComponent());
                        }
/* 249 */               return this;
                    } catch (PackageManager.NameNotFoundException e) {
/* 246 */               Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
/* 247 */               throw new IllegalArgumentException(e);
                    }
                }

/* 255 */       public int getIntentCount() {
/* 256 */           return this.mIntents.size();
                }

/* 269 */       public Intent getIntent(int index) {
/* 270 */           return editIntentAt(index);
                }

/* 281 */       public Intent editIntentAt(int index) {
/* 282 */           return this.mIntents.get(index);
                }

                @Override // java.lang.Iterable
/* 288 */       public Iterator<Intent> iterator() {
/* 289 */           return this.mIntents.iterator();
                }

/* 300 */       public void startActivities() {
/* 301 */           startActivities(null);
                }

/* 315 */       public void startActivities(Bundle options) {
/* 316 */           if (this.mIntents.isEmpty()) {
/* 317 */               throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
                    }
/* 321 */           Intent[] intents = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
/* 322 */           intents[0] = new Intent(intents[0]).addFlags(268484608);
/* 325 */           if (!ContextCompat.startActivities(this.mSourceContext, intents, options)) {
/* 326 */               Intent topIntent = new Intent(intents[intents.length - 1]);
/* 327 */               topIntent.addFlags(268435456);
/* 328 */               this.mSourceContext.startActivity(topIntent);
                    }
                }

/* 343 */       public PendingIntent getPendingIntent(int requestCode, int flags) {
/* 344 */           return getPendingIntent(requestCode, flags, null);
                }

/* 360 */       public PendingIntent getPendingIntent(int requestCode, int flags, Bundle options) {
/* 361 */           if (this.mIntents.isEmpty()) {
/* 362 */               throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
                    }
/* 366 */           Intent[] intents = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
/* 367 */           intents[0] = new Intent(intents[0]).addFlags(268484608);
/* 371 */           return IMPL.getPendingIntent(this.mSourceContext, intents, requestCode, flags, options);
                }

/* 381 */       public Intent[] getIntents() {
/* 382 */           Intent[] intents = new Intent[this.mIntents.size()];
/* 383 */           if (intents.length != 0) {
/* 385 */               intents[0] = new Intent(this.mIntents.get(0)).addFlags(268484608);
                        for (int i = 1; i < intents.length; i++) {
/* 389 */                   intents[i] = new Intent(this.mIntents.get(i));
                        }
                    }
/* 391 */           return intents;
                }
            }
