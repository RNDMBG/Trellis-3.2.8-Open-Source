            package com.facebook;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 85 */    public enum SessionState {
                CREATED(Category.CREATED_CATEGORY),
                CREATED_TOKEN_LOADED(Category.CREATED_CATEGORY),
                OPENING(Category.CREATED_CATEGORY),
                OPENED(Category.OPENED_CATEGORY),
                OPENED_TOKEN_UPDATED(Category.OPENED_CATEGORY),
                CLOSED_LOGIN_FAILED(Category.CLOSED_CATEGORY),
                CLOSED(Category.CLOSED_CATEGORY);

                private final Category category;

                private enum Category {
                    CREATED_CATEGORY,
                    OPENED_CATEGORY,
                    CLOSED_CATEGORY
                }

/* 86 */        SessionState(Category category) {
/* 88 */            this.category = category;
                }

/* 99 */        public boolean isOpened() {
                    return this.category == Category.OPENED_CATEGORY;
                }

/* 110 */       public boolean isClosed() {
                    return this.category == Category.CLOSED_CATEGORY;
                }
            }
