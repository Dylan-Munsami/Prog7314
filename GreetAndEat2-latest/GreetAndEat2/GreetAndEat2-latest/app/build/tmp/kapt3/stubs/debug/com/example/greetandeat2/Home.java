package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\u0012\u0010\u0011\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0015J-\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u000e\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002J\u0010\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/greetandeat2/Home;", "Lcom/example/greetandeat2/BaseActivity;", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "repository", "Lcom/example/greetandeat2/repository/OfflineRepository;", "tvRewardPoints", "Landroid/widget/TextView;", "tvTotalOrders", "calculateRewardPoints", "", "userId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFCMTokenForTesting", "", "initializeUI", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "requestNotificationPermission", "updateRewardPoints", "updateTotalOrders", "app_debug"})
public final class Home extends com.example.greetandeat2.BaseActivity {
    private android.widget.TextView tvTotalOrders;
    private android.widget.TextView tvRewardPoints;
    private com.example.greetandeat2.repository.OfflineRepository repository;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    
    public Home() {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingInflatedId"})
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeUI() {
    }
    
    private final void requestNotificationPermission() {
    }
    
    private final void updateTotalOrders(java.lang.String userId) {
    }
    
    private final void updateRewardPoints() {
    }
    
    private final java.lang.Object calculateRewardPoints(java.lang.String userId, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final void getFCMTokenForTesting() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
}