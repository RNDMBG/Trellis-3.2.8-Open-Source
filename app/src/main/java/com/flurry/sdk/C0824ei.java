            package com.flurry.sdk;

            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: renamed from: com.flurry.sdk.ei */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class C0824ei<RequestObjectType, ResponseObjectType> extends C0826ek {

                /* JADX INFO: renamed from: a */
                private a<RequestObjectType, ResponseObjectType> f781a;

                /* JADX INFO: renamed from: b */
                private RequestObjectType f782b;

                /* JADX INFO: renamed from: c */
                private ResponseObjectType f783c;

                /* JADX INFO: renamed from: d */
                private InterfaceC0836eu<RequestObjectType> f784d;

                /* JADX INFO: renamed from: e */
                private InterfaceC0836eu<ResponseObjectType> f785e;

                /* JADX INFO: renamed from: com.flurry.sdk.ei$a */
                public interface a<RequestObjectType, ResponseObjectType> {
                    /* JADX INFO: renamed from: a */
                    void mo575a(C0824ei<RequestObjectType, ResponseObjectType> c0824ei, ResponseObjectType responseobjecttype);
                }

                /* JADX INFO: renamed from: a */
/* 13 */        static /* synthetic */ Object m842a(C0824ei c0824ei) {
/* 14 */            return c0824ei.f782b;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: a */
/* 13 */        static /* synthetic */ Object m843a(C0824ei c0824ei, Object obj) {
/* 14 */            c0824ei.f783c = obj;
                    return obj;
                }

                /* JADX INFO: renamed from: b */
/* 13 */        static /* synthetic */ InterfaceC0836eu m844b(C0824ei c0824ei) {
/* 14 */            return c0824ei.f784d;
                }

                /* JADX INFO: renamed from: c */
/* 13 */        static /* synthetic */ InterfaceC0836eu m845c(C0824ei c0824ei) {
/* 14 */            return c0824ei.f785e;
                }

                /* JADX INFO: renamed from: d */
/* 13 */        static /* synthetic */ void m846d(C0824ei c0824ei) {
/* 14 */            c0824ei.m848n();
                }

                /* JADX INFO: renamed from: a */
/* 35 */        public void m851a(RequestObjectType requestobjecttype) {
/* 36 */            this.f782b = requestobjecttype;
                }

                /* JADX INFO: renamed from: a */
/* 39 */        public void m850a(InterfaceC0836eu<RequestObjectType> interfaceC0836eu) {
/* 40 */            this.f784d = interfaceC0836eu;
                }

                /* JADX INFO: renamed from: b */
/* 43 */        public void m852b(InterfaceC0836eu<ResponseObjectType> interfaceC0836eu) {
/* 44 */            this.f785e = interfaceC0836eu;
                }

                /* JADX INFO: renamed from: a */
/* 47 */        public void m849a(a<RequestObjectType, ResponseObjectType> aVar) {
/* 48 */            this.f781a = aVar;
                }

                @Override // com.flurry.sdk.C0826ek, com.flurry.sdk.AbstractRunnableC0844fc
                /* JADX INFO: renamed from: a */
/* 57 */        public void mo576a() {
/* 58 */            m847m();
/* 61 */            super.mo576a();
                }

                /* JADX INFO: renamed from: com.flurry.sdk.ei$1, reason: invalid class name */
/* 64 */        class AnonymousClass1 implements C0826ek.c {
/* 65 */            AnonymousClass1() {
                    }

                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 68 */            public void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
/* 69 */                if (C0824ei.m842a(C0824ei.this) != null && C0824ei.m844b(C0824ei.this) != null) {
/* 70 */                    C0824ei.m844b(C0824ei.this).mo917a(outputStream, C0824ei.m842a(C0824ei.this));
                        }
                    }

                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 76 */            public void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception {
/* 77 */                if (c0826ek.m877d() && C0824ei.m845c(C0824ei.this) != null) {
/* 82 */                    C0824ei.m843a(C0824ei.this, C0824ei.m845c(C0824ei.this).mo916a(inputStream));
                        }
                    }

                    @Override // com.flurry.sdk.C0826ek.c
                    /* JADX INFO: renamed from: a */
/* 87 */            public void mo853a(C0826ek c0826ek) {
/* 88 */                C0824ei.m846d(C0824ei.this);
                    }
                }

                /* JADX INFO: renamed from: m */
/* 65 */        private void m847m() {
/* 66 */            m870a((C0826ek.c) new AnonymousClass1());
                }

                /* JADX INFO: renamed from: n */
/* 93 */        private void m848n() {
/* 94 */            if (this.f781a != null && !m875b()) {
/* 102 */               this.f781a.mo575a(this, this.f783c);
                    }
                }
            }
