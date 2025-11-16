package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\f\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J*\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00032\u0014\b\u0001\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/example/greetandeat2/ApiService;", "", "getOrder", "Lretrofit2/Response;", "Lcom/example/greetandeat2/Order;", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrders", "", "userId", "placeOrder", "order", "(Lcom/example/greetandeat2/Order;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "progressOrder", "registerToken", "", "body", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "orders")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object placeOrder(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.Order order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.greetandeat2.Order>> $completion);
    
    @retrofit2.http.GET(value = "orders/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOrders(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.example.greetandeat2.Order>>> $completion);
    
    @retrofit2.http.GET(value = "order/{orderId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.greetandeat2.Order>> $completion);
    
    @retrofit2.http.POST(value = "orders/{orderId}/next")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object progressOrder(@retrofit2.http.Path(value = "orderId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.example.greetandeat2.Order>> $completion);
    
    @retrofit2.http.POST(value = "register-token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerToken(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
}