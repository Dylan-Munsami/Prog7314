package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0015J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\b\u0010\u0016\u001a\u00020\u0011H\u0002J\b\u0010\u0017\u001a\u00020\u0011H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/greetandeat2/CartActivity;", "Lcom/example/greetandeat2/BaseActivity;", "()V", "adapter", "Lcom/example/greetandeat2/CartAdapter;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "cartItems", "", "Lcom/example/greetandeat2/data/CartItem;", "repository", "Lcom/example/greetandeat2/repository/OfflineRepository;", "tvTotal", "Landroid/widget/TextView;", "isOnline", "", "loadCartItems", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "setupSyncWorker", "syncData", "updateTotal", "app_debug"})
public final class CartActivity extends com.example.greetandeat2.BaseActivity {
    private com.example.greetandeat2.CartAdapter adapter;
    private com.google.firebase.auth.FirebaseAuth auth;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.greetandeat2.data.CartItem> cartItems = null;
    private android.widget.TextView tvTotal;
    private com.example.greetandeat2.repository.OfflineRepository repository;
    
    public CartActivity() {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingInflatedId"})
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final boolean isOnline() {
        return false;
    }
    
    private final void loadCartItems() {
    }
    
    @android.annotation.SuppressLint(value = {"StringFormatMatches"})
    private final void updateTotal() {
    }
    
    private final void setupSyncWorker() {
    }
    
    private final void syncData() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
}