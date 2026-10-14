            package com.flurry.sdk;

            import java.io.InputStream;
            import java.io.OutputStream;
            
            
            
    public class C0824ei<RequestObjectType, ResponseObjectType> extends C0826ek {

                
                private a<RequestObjectType, ResponseObjectType> f781a;

                
                private RequestObjectType f782b;

                
                private ResponseObjectType f783c;

                
                private InterfaceC0836eu<RequestObjectType> f784d;

                
                private InterfaceC0836eu<ResponseObjectType> f785e;

                
                public interface a<RequestObjectType, ResponseObjectType> {
                    
                    void mo575a(C0824ei<RequestObjectType, ResponseObjectType> c0824ei, ResponseObjectType responseobjecttype);
                }

                
        static  Object m842a(C0824ei c0824ei) {
            return c0824ei.f782b;
                }

                
                
        static  Object m843a(C0824ei c0824ei, Object obj) {
            c0824ei.f783c = obj;
                    return obj;
                }

                
        static  InterfaceC0836eu m844b(C0824ei c0824ei) {
            return c0824ei.f784d;
                }

                
        static  InterfaceC0836eu m845c(C0824ei c0824ei) {
            return c0824ei.f785e;
                }

                
        static  void m846d(C0824ei c0824ei) {
            c0824ei.m848n();
                }

                
        public void m851a(RequestObjectType requestobjecttype) {
            this.f782b = requestobjecttype;
                }

                
        public void m850a(InterfaceC0836eu<RequestObjectType> interfaceC0836eu) {
            this.f784d = interfaceC0836eu;
                }

                
        public void m852b(InterfaceC0836eu<ResponseObjectType> interfaceC0836eu) {
            this.f785e = interfaceC0836eu;
                }

                
        public void m849a(a<RequestObjectType, ResponseObjectType> aVar) {
            this.f781a = aVar;
                }

                @Override 
                
        public void mo576a() {
            m847m();
            super.mo576a();
                }

                
        class AnonymousClass1 implements C0826ek.c {
            AnonymousClass1() {
                    }

                    @Override 
                    
            public void mo855a(C0826ek c0826ek, OutputStream outputStream) throws Exception {
                if (C0824ei.m842a(C0824ei.this) != null && C0824ei.m844b(C0824ei.this) != null) {
                    C0824ei.m844b(C0824ei.this).mo917a(outputStream, C0824ei.m842a(C0824ei.this));
                        }
                    }

                    @Override 
                    
            public void mo854a(C0826ek c0826ek, InputStream inputStream) throws Exception {
                if (c0826ek.m877d() && C0824ei.m845c(C0824ei.this) != null) {
                    C0824ei.m843a(C0824ei.this, C0824ei.m845c(C0824ei.this).mo916a(inputStream));
                        }
                    }

                    @Override 
                    
            public void mo853a(C0826ek c0826ek) {
                C0824ei.m846d(C0824ei.this);
                    }
                }

                
        private void m847m() {
            m870a((C0826ek.c) new AnonymousClass1());
                }

                
        private void m848n() {
            if (this.f781a != null && !m875b()) {
               this.f781a.mo575a(this, this.f783c);
                    }
                }
            }
