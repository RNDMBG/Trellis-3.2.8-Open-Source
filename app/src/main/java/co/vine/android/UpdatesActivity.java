package co.vine.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import co.vine.android.views.SwitchInterface;





public class UpdatesActivity extends BaseControllerActionBarActivity implements View.OnClickListener {

    private TextView mStatus;
    private Button mCheckButton;
    private co.vine.android.views.SwitchInterface mAutoSwitch;
    private TextView mChangelog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.updates, true);
        setupActionBar((Boolean) true, (Boolean) true, R.string.updates_title, (Boolean) true, (Boolean) true);
        UpdateManager.ensureDownloadReceiver(getApplicationContext());

        mStatus = (TextView) findViewById(R.id.updates_status);
        mCheckButton = (Button) findViewById(R.id.updates_check);
        mCheckButton.setOnClickListener(this);
        mAutoSwitch = (co.vine.android.views.SwitchInterface) findViewById(R.id.updates_auto);
        mAutoSwitch.setChecked(UpdateManager.isAutoEnabled(this));
        mAutoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UpdateManager.setAutoEnabled(UpdatesActivity.this, isChecked);
            }
        });
        TextView githubRow = (TextView) findViewById(R.id.updates_github);
        githubRow.setOnClickListener(this);
        mChangelog = (TextView) findViewById(R.id.updates_changelog);
        refreshStatus();
        loadChangelog();
    }

    private void loadChangelog() {
        if (mChangelog == null) {
            return;
        }
        mChangelog.setText(R.string.updates_changelog_loading);
        UpdateManager.fetchAllReleaseNotes(this, new UpdateManager.ReleasesCallback() {
            @Override
            public void onResult(java.util.List<UpdateManager.ReleaseNotes> releases) {
                if (mChangelog == null) {
                    return;
                }
                if (releases == null || releases.isEmpty()) {
                    mChangelog.setText(R.string.updates_error);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (UpdateManager.ReleaseNotes notes : releases) {
                    if (notes == null) {
                        continue;
                    }
                    sb.append("<b>Build ").append(notes.build).append("</b><br>");
                    String body = notes.body.replace("`", "").trim().replace("\n", "<br>");
                    sb.append(body).append("<br><br>");
                }
                mChangelog.setText(android.text.Html.fromHtml(sb.toString().trim()));
            }

            @Override
            public void onError() {
                if (mChangelog != null) {
                    mChangelog.setText(R.string.updates_error);
                }
            }
        });
    }

    private void refreshStatus() {
        mStatus.setText(getString(R.string.updates_title) + " - " + UpdateManager.currentVersion(this) + " (build " + UpdateManager.currentBuild(this) + ")");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.updates_github) {
            UpdateManager.openReleasesPage(this);
            return;
        }
        if (v.getId() == R.id.updates_check) {
            mCheckButton.setEnabled(false);
            mStatus.setText(R.string.updates_checking);
            UpdateManager.checkForUpdate(this, new UpdateManager.UpdateCallback() {
                @Override
                public void onResult(UpdateManager.UpdateInfo info) {
                    mCheckButton.setEnabled(true);
                    if (info != null && info.newer && info.apkUrl != null) {
                        mStatus.setText(getString(R.string.updates_new_build, info.version));
                        UpdateManager.showUpdateDialog(UpdatesActivity.this, info);
                    } else if (info != null) {
                        mStatus.setText(R.string.updates_uptodate);
                    } else {
                        mStatus.setText(R.string.updates_error);
                    }
                }

                @Override
                public void onError() {
                    mCheckButton.setEnabled(true);
                    mStatus.setText(R.string.updates_error);
                }
            });
        }
    }
}
