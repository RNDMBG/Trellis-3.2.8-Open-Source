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
            
            
            @TargetApi(3)
    public class RequestAsyncTask extends AsyncTask<Void, Void, List<Response>> {
                private static final String TAG = RequestAsyncTask.class.getCanonicalName();
                private static Method executeOnExecutorMethod;
                private final HttpURLConnection connection;
                private Exception exception;
                private final RequestBatch requests;



                static {
            Method[] arr$ = AsyncTask.class.getMethods();
                    for (Method method : arr$) {
                if ("executeOnExecutor".equals(method.getName())) {
                    Class<?>[] parameters = method.getParameterTypes();
                    if (parameters.length == 2 && parameters[0] == Executor.class && parameters[1].isArray()) {
                        executeOnExecutorMethod = method;
                        break;
                            }
                        }
                    }
                }

        public RequestAsyncTask(Request... requests) {
            this((HttpURLConnection) null, new RequestBatch(requests));
                }

        public RequestAsyncTask(Collection<Request> requests) {
            this((HttpURLConnection) null, new RequestBatch(requests));
                }

        public RequestAsyncTask(RequestBatch requests) {
            this((HttpURLConnection) null, requests);
                }

        public RequestAsyncTask(HttpURLConnection connection, Request... requests) {
            this(connection, new RequestBatch(requests));
                }

       public RequestAsyncTask(HttpURLConnection connection, Collection<Request> requests) {
           this(connection, new RequestBatch(requests));
                }

       public RequestAsyncTask(HttpURLConnection connection, RequestBatch requests) {
           this.requests = requests;
           this.connection = connection;
                }

       protected final Exception getException() {
           return this.exception;
                }

       protected final RequestBatch getRequests() {
           return this.requests;
                }

       public String toString() {
           return "{RequestAsyncTask:  connection: " + this.connection + ", requests: " + this.requests + "}";
                }

                @Override 
       protected void onPreExecute() {
           super.onPreExecute();
           if (this.requests.getCallbackHandler() == null) {
               this.requests.setCallbackHandler(new Handler());
                    }
                }

                
       protected void onPostExecute(List<Response> result) {
           super.onPostExecute(result);
           if (this.exception != null) {
               Log.d(TAG, String.format("onPostExecute: exception encountered during request: %s", this.exception.getMessage()));
                    }
                }

                
       protected List<Response> doInBackground(Void... params) {
                    List<Response> listExecuteConnectionAndWait;
                    try {
               if (this.connection == null) {
                   listExecuteConnectionAndWait = this.requests.executeAndWait();
                        } else {
                   listExecuteConnectionAndWait = Request.executeConnectionAndWait(this.connection, this.requests);
                        }
               return listExecuteConnectionAndWait;
                    } catch (Exception e) {
               this.exception = e;
               return null;
                    }
                }

       RequestAsyncTask executeOnSettingsExecutor() {
                    try {
               if (executeOnExecutorMethod != null) {
                   executeOnExecutorMethod.invoke(this, Settings.getExecutor(), null);
                        } else {
                   execute(new Void[0]);
                        }
                    } catch (IllegalAccessException e) {
                    } catch (InvocationTargetException e2) {
                    }
           return this;
                }
            }
