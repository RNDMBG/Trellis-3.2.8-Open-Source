            package com.facebook;

            import android.os.Handler;
            import java.util.AbstractList;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collection;
            import java.util.List;
            import java.util.concurrent.atomic.AtomicInteger;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class RequestBatch extends AbstractList<Request> {
                private static AtomicInteger idGenerator = new AtomicInteger();
                private String batchApplicationId;
                private Handler callbackHandler;
                private List<Callback> callbacks;

                /* JADX INFO: renamed from: id */
                private final String f525id;
                private List<Request> requests;
                private int timeoutInMilliseconds;

                public interface Callback {
                    void onBatchCompleted(RequestBatch requestBatch);
                }






/* 39 */        public RequestBatch() {
/* 31 */            this.requests = new ArrayList();
/* 32 */            this.timeoutInMilliseconds = 0;
/* 33 */            this.f525id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
/* 34 */            this.callbacks = new ArrayList();
/* 41 */            this.requests = new ArrayList();
                }

/* 47 */        public RequestBatch(Collection<Request> requests) {
/* 31 */            this.requests = new ArrayList();
/* 32 */            this.timeoutInMilliseconds = 0;
/* 33 */            this.f525id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
/* 34 */            this.callbacks = new ArrayList();
/* 49 */            this.requests = new ArrayList(requests);
                }

/* 55 */        public RequestBatch(Request... requests) {
/* 31 */            this.requests = new ArrayList();
/* 32 */            this.timeoutInMilliseconds = 0;
/* 33 */            this.f525id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
/* 34 */            this.callbacks = new ArrayList();
/* 57 */            this.requests = Arrays.asList(requests);
                }

/* 63 */        public RequestBatch(RequestBatch requests) {
/* 31 */            this.requests = new ArrayList();
/* 32 */            this.timeoutInMilliseconds = 0;
/* 33 */            this.f525id = Integer.valueOf(idGenerator.incrementAndGet()).toString();
/* 34 */            this.callbacks = new ArrayList();
/* 65 */            this.requests = new ArrayList(requests);
/* 66 */            this.callbackHandler = requests.callbackHandler;
/* 67 */            this.timeoutInMilliseconds = requests.timeoutInMilliseconds;
/* 68 */            this.callbacks = new ArrayList(requests.callbacks);
                }

/* 75 */        public int getTimeout() {
/* 76 */            return this.timeoutInMilliseconds;
                }

/* 83 */        public void setTimeout(int timeoutInMilliseconds) {
/* 84 */            if (timeoutInMilliseconds < 0) {
/* 85 */                throw new IllegalArgumentException("Argument timeoutInMilliseconds must be >= 0.");
                    }
/* 87 */            this.timeoutInMilliseconds = timeoutInMilliseconds;
                }

/* 95 */        public void addCallback(Callback callback) {
/* 96 */            if (!this.callbacks.contains(callback)) {
/* 97 */                this.callbacks.add(callback);
                    }
                }

/* 106 */       public void removeCallback(Callback callback) {
/* 107 */           this.callbacks.remove(callback);
                }

/* 111 */       public final boolean add(Request request) {
/* 112 */           return this.requests.add(request);
                }

/* 116 */       public final void add(int location, Request request) {
/* 117 */           this.requests.add(location, request);
                }

                @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
/* 121 */       public final void clear() {
/* 122 */           this.requests.clear();
                }

                @Override // java.util.AbstractList, java.util.List
/* 126 */       public final Request get(int i) {
/* 127 */           return this.requests.get(i);
                }

                @Override // java.util.AbstractList, java.util.List
/* 131 */       public final Request remove(int location) {
/* 132 */           return this.requests.remove(location);
                }

/* 136 */       public final Request set(int location, Request request) {
/* 137 */           return this.requests.set(location, request);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
/* 141 */       public final int size() {
/* 142 */           return this.requests.size();
                }

/* 145 */       final String getId() {
/* 146 */           return this.f525id;
                }

/* 149 */       final Handler getCallbackHandler() {
/* 150 */           return this.callbackHandler;
                }

/* 153 */       final void setCallbackHandler(Handler callbackHandler) {
/* 154 */           this.callbackHandler = callbackHandler;
                }

/* 157 */       final List<Request> getRequests() {
/* 158 */           return this.requests;
                }

/* 161 */       final List<Callback> getCallbacks() {
/* 162 */           return this.callbacks;
                }

/* 165 */       final String getBatchApplicationId() {
/* 166 */           return this.batchApplicationId;
                }

/* 169 */       final void setBatchApplicationId(String batchApplicationId) {
/* 170 */           this.batchApplicationId = batchApplicationId;
                }

/* 186 */       public final List<Response> executeAndWait() {
/* 187 */           return executeAndWaitImpl();
                }

/* 203 */       public final RequestAsyncTask executeAsync() {
/* 204 */           return executeAsyncImpl();
                }

/* 220 */       List<Response> executeAndWaitImpl() {
/* 221 */           return Request.executeBatchAndWait(this);
                }

/* 224 */       RequestAsyncTask executeAsyncImpl() {
/* 225 */           return Request.executeBatchAsync(this);
                }
            }
