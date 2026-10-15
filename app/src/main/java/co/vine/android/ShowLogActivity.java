            package co.vine.android;

            import android.os.Bundle;
            import android.support.v7.app.ActionBar;
            import android.view.View;
            import android.widget.Button;
            import android.widget.TextView;
            import co.vine.android.util.FileLogger;
            import java.io.File;
            import org.apache.commons.io.IOUtils;
            
            
    public class ShowLogActivity extends BaseActionBarActivity {
                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, R.layout.debug_home, false, true);
            File file = new File(getIntent().getAction());
            TextView tv = (TextView) findViewById(R.id.debuginfo);
            Button button = (Button) findViewById(R.id.writeSessionKeyToLog);
            button.setText("Clear Logs");
            findViewById(R.id.debug_query_parent).setVisibility(8);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle("Log " + file.getName());
                    }
            FileLogger logger = FileLogger.getLogger(file);
                    while (true) {
                String line = logger.read();
                        if (line != null) {
                    tv.append(line);
                    tv.append(IOUtils.LINE_SEPARATOR_UNIX);
                        } else {
                    button.setOnClickListener(new ViewOnClickListenerC02851(logger, tv));
                    return;
                        }
                    }
                }

                
        class ViewOnClickListenerC02851 implements View.OnClickListener {
                    final  FileLogger val$logger;
                    final  TextView val$tv;

            ViewOnClickListenerC02851(FileLogger fileLogger, TextView textView) {
                        this.val$logger = fileLogger;
                        this.val$tv = textView;
                    }

                    @Override 
            public void onClick(View v) {
                this.val$logger.clear();
                this.val$tv.setText("");
                    }
                }
            }
