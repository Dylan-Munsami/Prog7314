package com.example.greetandeat2.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u0012"}, d2 = {"Lcom/example/greetandeat2/data/CartDao;", "", "clearCart", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteCartItem", "cartItem", "Lcom/example/greetandeat2/data/CartItem;", "(Lcom/example/greetandeat2/data/CartItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllCartItems", "", "getUnsyncedItems", "insertCartItem", "markAsSynced", "itemId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateCartItem", "app_debug"})
@androidx.room.Dao()
public abstract interface CartDao {
    
    @androidx.room.Query(value = "SELECT * FROM cart_items ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllCartItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.CartItem>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM cart_items WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedItems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.CartItem>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCartItem(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateCartItem(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteCartItem(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.CartItem cartItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM cart_items")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearCart(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE cart_items SET isSynced = 1 WHERE id = :itemId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}