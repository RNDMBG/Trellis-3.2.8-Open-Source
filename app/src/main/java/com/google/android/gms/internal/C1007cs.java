            package com.google.android.gms.internal;

            import android.R;
            import android.app.AlertDialog;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.view.View;
            import android.webkit.ConsoleMessage;
            import android.webkit.JsPromptResult;
            import android.webkit.JsResult;
            import android.webkit.WebChromeClient;
            import android.webkit.WebStorage;
            import android.webkit.WebView;
            import android.widget.EditText;
            import android.widget.LinearLayout;
            import android.widget.TextView;
            
            
            
            public class C1007cs extends WebChromeClient {

                
                private final C1005cq f1621fG;

                
                static class AnonymousClass1 implements DialogInterface.OnCancelListener {

                    
                    final  JsResult f1622ii;

                    AnonymousClass1(JsResult jsResult) {
                        this.f1622ii = jsResult;
                    }

                    @Override 
                    public void onCancel(DialogInterface dialog) {
                        this.f1622ii.cancel();
                    }
                }

                
                static class AnonymousClass2 implements DialogInterface.OnClickListener {

                    
                    final  JsResult f1623ii;

                    AnonymousClass2(JsResult jsResult) {
                        this.f1623ii = jsResult;
                    }

                    @Override 
                    public void onClick(DialogInterface dialog, int which) {
                        this.f1623ii.cancel();
                    }
                }

                
                static class AnonymousClass3 implements DialogInterface.OnClickListener {

                    
                    final  JsResult f1624ii;

                    AnonymousClass3(JsResult jsResult) {
                        this.f1624ii = jsResult;
                    }

                    @Override 
                    public void onClick(DialogInterface dialog, int which) {
                        this.f1624ii.confirm();
                    }
                }

                
                static class AnonymousClass4 implements DialogInterface.OnCancelListener {

                    
                    final  JsPromptResult f1625ij;

                    AnonymousClass4(JsPromptResult jsPromptResult) {
                        this.f1625ij = jsPromptResult;
                    }

                    @Override 
                    public void onCancel(DialogInterface dialog) {
                        this.f1625ij.cancel();
                    }
                }

                
                static class AnonymousClass5 implements DialogInterface.OnClickListener {

                    
                    final  JsPromptResult f1626ij;

                    AnonymousClass5(JsPromptResult jsPromptResult) {
                        this.f1626ij = jsPromptResult;
                    }

                    @Override 
                    public void onClick(DialogInterface dialog, int which) {
                        this.f1626ij.cancel();
                    }
                }

                
                static class AnonymousClass6 implements DialogInterface.OnClickListener {

                    
                    final  JsPromptResult f1627ij;

                    
                    final  EditText f1628ik;

                    AnonymousClass6(JsPromptResult jsPromptResult, EditText editText) {
                        this.f1627ij = jsPromptResult;
                        this.f1628ik = editText;
                    }

                    @Override 
                    public void onClick(DialogInterface dialog, int which) {
                        this.f1627ij.confirm(this.f1628ik.getText().toString());
                    }
                }

                
                static  class AnonymousClass7 {

                    
                    static final  int[] f1629il = new int[ConsoleMessage.MessageLevel.values().length];

                    static {
                        try {
                            f1629il[ConsoleMessage.MessageLevel.ERROR.ordinal()] = 1;
                        } catch (NoSuchFieldError e) {
                        }
                        try {
                            f1629il[ConsoleMessage.MessageLevel.WARNING.ordinal()] = 2;
                        } catch (NoSuchFieldError e2) {
                        }
                        try {
                            f1629il[ConsoleMessage.MessageLevel.LOG.ordinal()] = 3;
                        } catch (NoSuchFieldError e3) {
                        }
                        try {
                            f1629il[ConsoleMessage.MessageLevel.TIP.ordinal()] = 4;
                        } catch (NoSuchFieldError e4) {
                        }
                        try {
                            f1629il[ConsoleMessage.MessageLevel.DEBUG.ordinal()] = 5;
                        } catch (NoSuchFieldError e5) {
                        }
                    }
                }

                public C1007cs(C1005cq c1005cq) {
                    this.f1621fG = c1005cq;
                }

                
                private static void m1491a(AlertDialog.Builder builder, String str, JsResult jsResult) {
                    builder.setMessage(str).setPositiveButton(R.string.ok, new AnonymousClass3(jsResult)).setNegativeButton(R.string.cancel, new AnonymousClass2(jsResult)).setOnCancelListener(new AnonymousClass1(jsResult)).create().show();
                }

                
                private static void m1492a(Context context, AlertDialog.Builder builder, String str, String str2, JsPromptResult jsPromptResult) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(1);
                    TextView textView = new TextView(context);
                    textView.setText(str);
                    EditText editText = new EditText(context);
                    editText.setText(str2);
                    linearLayout.addView(textView);
                    linearLayout.addView(editText);
                    builder.setView(linearLayout).setPositiveButton(R.string.ok, new AnonymousClass6(jsPromptResult, editText)).setNegativeButton(R.string.cancel, new AnonymousClass5(jsPromptResult)).setOnCancelListener(new AnonymousClass4(jsPromptResult)).create().show();
                }

                
                private static boolean m1493a(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(str);
                    if (z) {
                        m1492a(context, builder, str2, str3, jsPromptResult);
                        return true;
                    }
                    m1491a(builder, str2, jsResult);
                    return true;
                }

                
                protected final void m1494a(View view, int i, WebChromeClient.CustomViewCallback customViewCallback) {
                    BinderC0967bf binderC0967bfM1471au = this.f1621fG.m1471au();
                    if (binderC0967bfM1471au == null) {
                        C1002cn.m1458q("Could not get ad overlay when showing custom view.");
                        customViewCallback.onCustomViewHidden();
                    } else {
                        binderC0967bfM1471au.m1307a(view, customViewCallback);
                        binderC0967bfM1471au.setRequestedOrientation(i);
                    }
                }

                @Override 
                public final void onCloseWindow(WebView webView) {
                    if (!(webView instanceof C1005cq)) {
                        C1002cn.m1458q("Tried to close a WebView that wasn't an AdWebView.");
                        return;
                    }
                    BinderC0967bf binderC0967bfM1471au = ((C1005cq) webView).m1471au();
                    if (binderC0967bfM1471au == null) {
                        C1002cn.m1458q("Tried to close an AdWebView not associated with an overlay.");
                    } else {
                        binderC0967bfM1471au.close();
                    }
                }

                @Override 
                public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
                    switch (AnonymousClass7.f1629il[consoleMessage.messageLevel().ordinal()]) {
                        case 1:
                            C1002cn.m1455n(str);
                            break;
                        case 2:
                            C1002cn.m1458q(str);
                            break;
                        case 3:
                        case 4:
                            C1002cn.m1456o(str);
                            break;
                        case 5:
                            C1002cn.m1454m(str);
                            break;
                        default:
                            C1002cn.m1456o(str);
                            break;
                    }
                    return super.onConsoleMessage(consoleMessage);
                }

                @Override 
                public final void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                    long j = 5242880 - totalUsedQuota;
                    if (j <= 0) {
                        quotaUpdater.updateQuota(currentQuota);
                        return;
                    }
                    if (currentQuota == 0) {
                        if (estimatedSize > j || estimatedSize > 1048576) {
                            estimatedSize = 0;
                        }
                    } else if (estimatedSize == 0) {
                        estimatedSize = Math.min(Math.min(131072L, j) + currentQuota, 1048576L);
                    } else {
                        if (estimatedSize <= Math.min(1048576 - currentQuota, j)) {
                            currentQuota += estimatedSize;
                        }
                        estimatedSize = currentQuota;
                    }
                    quotaUpdater.updateQuota(estimatedSize);
                }

                @Override 
                public final void onHideCustomView() {
                    BinderC0967bf binderC0967bfM1471au = this.f1621fG.m1471au();
                    if (binderC0967bfM1471au == null) {
                        C1002cn.m1458q("Could not get ad overlay when hiding custom view.");
                    } else {
                        binderC0967bfM1471au.m1305R();
                    }
                }

                @Override 
                public final boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
                    return m1493a(webView.getContext(), url, message, null, result, null, false);
                }

                @Override 
                public final boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
                    return m1493a(webView.getContext(), url, message, null, result, null, false);
                }

                @Override 
                public final boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
                    return m1493a(webView.getContext(), url, message, null, result, null, false);
                }

                @Override 
                public final boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
                    return m1493a(webView.getContext(), url, message, defaultValue, null, result, true);
                }

                public final void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                    long j = 131072 + spaceNeeded;
                    if (5242880 - totalUsedQuota < j) {
                        quotaUpdater.updateQuota(0L);
                    } else {
                        quotaUpdater.updateQuota(j);
                    }
                }

                @Override 
                public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                    m1494a(view, -1, customViewCallback);
                }
            }
