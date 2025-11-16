package com.example.greetandeat2.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0011H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0011H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0011H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0011H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010 \u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\"R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/example/greetandeat2/repository/OfflineRepository;", "", "database", "Lcom/example/greetandeat2/data/AppDatabase;", "(Lcom/example/greetandeat2/data/AppDatabase;)V", "cartDao", "Lcom/example/greetandeat2/data/CartDao;", "orderDao", "Lcom/example/greetandeat2/data/OrderDao;", "addToCart", "", "cartItem", "Lcom/example/greetandeat2/data/CartItem;", "(Lcom/example/greetandeat2/data/CartItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearCart", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCartItems", "", "getOrders", "Lcom/example/greetandeat2/data/LocalOrder;", "getUnsyncedCartItems", "getUnsyncedOrders", "markCartItemAsSynced", "itemId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderAsSynced", "orderId", "placeOrder", "order", "(Lcom/example/greetandeat2/data/LocalOrder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeFromCart", "updateOrderStatus", "status", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class OfflineRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.greetandeat2.data.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.greetandeat2.data.CartDao cartDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.greetandeat2.data.OrderDao orderDao = null;
    
    public OfflineRepository(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.AppDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToCart(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeFromCart(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCartItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.CartItem>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object clearCart(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUnsyncedCartItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.CartItem>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markCartItemAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object placeOrder(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.LocalOrder order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.LocalOrder>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUnsyncedOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.LocalOrder>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markOrderAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateOrderStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}