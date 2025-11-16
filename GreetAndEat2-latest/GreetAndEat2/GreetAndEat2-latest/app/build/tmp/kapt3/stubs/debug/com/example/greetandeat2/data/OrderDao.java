package com.example.greetandeat2.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u0011"}, d2 = {"Lcom/example/greetandeat2/data/OrderDao;", "", "getAllOrders", "", "Lcom/example/greetandeat2/data/LocalOrder;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrderById", "orderId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnsyncedOrders", "insertOrder", "", "order", "(Lcom/example/greetandeat2/data/LocalOrder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderAsSynced", "updateOrder", "app_debug"})
@androidx.room.Dao()
public abstract interface OrderDao {
    
    @androidx.room.Query(value = "SELECT * FROM orders ORDER BY createdAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.LocalOrder>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM orders WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUnsyncedOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.greetandeat2.data.LocalOrder>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrder(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.LocalOrder order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateOrder(@org.jetbrains.annotations.NotNull()
    com.example.greetandeat2.data.LocalOrder order, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE orders SET isSynced = 1 WHERE orderId = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markOrderAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM orders WHERE orderId = :orderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOrderById(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.greetandeat2.data.LocalOrder> $completion);
}