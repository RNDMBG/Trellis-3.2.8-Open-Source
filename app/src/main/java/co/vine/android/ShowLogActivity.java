            package co.vine.android;

            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.view.View;
            import android.widget.Button;
            import android.widget.TextView;
            import co.vine.android.util.FileLogger;
            import java.io.File;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ShowLogActivity extends BaseActionBarActivity {
                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 19 */        protected void onCreate(Bundle savedInstanceState) {
/* 20 */            super.onCreate(savedInstanceState, C0257R.layout.debug_home, false, true);
/* 21 */            File file = new File(getIntent().getAction());
/* 23 */            TextView tv = (TextView) findViewById(C0257R.id.debuginfo);
/* 24 */            Button button = (Button) findViewById(C0257R.id.writeSessionKeyToLog);
/* 25 */            button.setText("Clear Logs");
/* 26 */            findViewById(C0257R.id.debug_query_parent).setVisibility(8);
/* 27 */            ActionBar ab = getSupportActionBar();
/* 28 */            if (ab != null) {
/* 29 */                ab.setTitle("Log " + file.getName());
                    }
/* 32 */            FileLogger logger = FileLogger.getLogger(file);
                    while (true) {
/* 35 */                String line = logger.read();
                        if (line != null) {
/* 36 */                    tv.append(line);
/* 37 */                    tv.append(IOUtils.LINE_SEPARATOR_UNIX);
                        } else {
/* 39 */                    button.setOnClickListener(new ViewOnClickListenerC02851(logger, tv));
/* 46 */                    return;
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ShowLogActivity$1 */
/* 37 */        class ViewOnClickListenerC02851 implements View.OnClickListener {
                    final /* synthetic */ FileLogger val$logger;
                    final /* synthetic */ TextView val$tv;

/* 38 */            ViewOnClickListenerC02851(FileLogger fileLogger, TextView textView) {
                        this.val$logger = fileLogger;
                        this.val$tv = textView;
                    }

                    @Override // android.view.View.OnClickListener
/* 41 */            public void onClick(View v) {
/* 42 */                this.val$logger.clear();
/* 43 */                this.val$tv.setText("");
                    }
                }
            }
