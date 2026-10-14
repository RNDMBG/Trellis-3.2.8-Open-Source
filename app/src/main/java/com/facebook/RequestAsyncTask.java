            package com.facebook;

            import android.annotation.TargetApi;
            import android.os.AsyncTask;
            import android.os.Handler;
            import android.util.Log;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.net.HttpURLConnection;
            import java.util.Collection;
            import java.util.List;
            import java.util.concurrent.Executor;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            @TargetApi(3)
/* 33 */    public class RequestAsyncTask extends AsyncTask<Void, Void, List<Response>> {
                private static final String TAG = RequestAsyncTask.class.getCanonicalName();
                private static Method executeOnExecutorMethod;
                private final HttpURLConnection connection;
                private Exception exception;
                private final RequestBatch requests;



                static {
/* 46 */            Method[] arr$ = AsyncTask.class.getMethods();
                    for (Method method : arr$) {
/* 47 */                if ("executeOnExecutor".equals(method.getName())) {
/* 48 */                    Class<?>[] parameters = method.getParameterTypes();
/* 49 */                    if (parameters.length == 2 && parameters[0] == Executor.class && parameters[1].isArray()) {
/* 50 */                        executeOnExecutorMethod = method;
/* 55 */                        break;
                            }
                        }
                    }
                }

/* 63 */        public RequestAsyncTask(Request... requests) {
/* 64 */            this((HttpURLConnection) null, new RequestBatch(requests));
                }

/* 73 */        public RequestAsyncTask(Collection<Request> requests) {
/* 74 */            this((HttpURLConnection) null, new RequestBatch(requests));
                }

/* 83 */        public RequestAsyncTask(RequestBatch requests) {
/* 84 */            this((HttpURLConnection) null, requests);
                }

/* 96 */        public RequestAsyncTask(HttpURLConnection connection, Request... requests) {
/* 97 */            this(connection, new RequestBatch(requests));
                }

/* 109 */       public RequestAsyncTask(HttpURLConnection connection, Collection<Request> requests) {
/* 110 */           this(connection, new RequestBatch(requests));
                }

/* 121 */       public RequestAsyncTask(HttpURLConnection connection, RequestBatch requests) {
/* 123 */           this.requests = requests;
/* 124 */           this.connection = connection;
                }

/* 127 */       protected final Exception getException() {
/* 128 */           return this.exception;
                }

/* 131 */       protected final RequestBatch getRequests() {
/* 132 */           return this.requests;
                }

/* 136 */       public String toString() {
/* 137 */           return "{RequestAsyncTask:  connection: " + this.connection + ", requests: " + this.requests + "}";
                }

                @Override // android.os.AsyncTask
/* 141 */       protected void onPreExecute() {
/* 142 */           super.onPreExecute();
/* 144 */           if (this.requests.getCallbackHandler() == null) {
/* 146 */               this.requests.setCallbackHandler(new Handler());
                    }
                }

                /* JADX INFO: renamed from: onPostExecute, reason: avoid collision after fix types in other method */
/* 151 */       protected void onPostExecute(List<Response> result) {
/* 152 */           super.onPostExecute(result);
/* 154 */           if (this.exception != null) {
/* 155 */               Log.d(TAG, String.format("onPostExecute: exception encountered during request: %s", this.exception.getMessage()));
                    }
                }

                /* JADX INFO: renamed from: doInBackground, reason: avoid collision after fix types in other method */
/* 161 */       protected List<Response> doInBackground(Void... params) {
                    List<Response> listExecuteConnectionAndWait;
                    try {
/* 162 */               if (this.connection == null) {
/* 163 */                   listExecuteConnectionAndWait = this.requests.executeAndWait();
                        } else {
/* 165 */                   listExecuteConnectionAndWait = Request.executeConnectionAndWait(this.connection, this.requests);
                        }
/* 169 */               return listExecuteConnectionAndWait;
                    } catch (Exception e) {
/* 168 */               this.exception = e;
/* 169 */               return null;
                    }
                }

/* 174 */       RequestAsyncTask executeOnSettingsExecutor() {
                    try {
/* 175 */               if (executeOnExecutorMethod != null) {
/* 176 */                   executeOnExecutorMethod.invoke(this, Settings.getExecutor(), null);
                        } else {
/* 185 */                   execute(new Void[0]);
                        }
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e2) {
                    }
/* 186 */           return this;
                }
            }
