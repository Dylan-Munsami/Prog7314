package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u000bH\u0002J\u000e\u0010\u0019\u001a\u00020\u001aH\u0082@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0010H\u0082@\u00a2\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u00020\u001a2\b\u0010 \u001a\u0004\u0018\u00010!H\u0015J\b\u0010\"\u001a\u00020\u001aH\u0003J\u0010\u0010#\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\tH\u0003J\b\u0010%\u001a\u00020\u001aH\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/example/greetandeat2/TrackingActivity;", "Lcom/example/greetandeat2/BaseActivity;", "()V", "btnGoHome", "Landroid/widget/Button;", "btnRefresh", "cardNoOrder", "Landroidx/cardview/widget/CardView;", "currentOrder", "Lcom/example/greetandeat2/data/LocalOrder;", "currentStatusIndex", "", "progressBar", "Landroid/widget/ProgressBar;", "statusDescriptions", "", "", "statuses", "tvEstimatedTime", "Landroid/widget/TextView;", "tvOrderId", "tvStatus", "tvStatusHistory", "getEstimatedTime", "statusIndex", "loadCurrentOrder", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markOrderAsDelivered", "orderId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "progressToNextStatus", "setupTrackingUI", "order", "showNoOrderUI", "app_debug"})
public final class TrackingActivity extends com.example.greetandeat2.BaseActivity {
    private android.widget.TextView tvStatus;
    private android.widget.TextView tvEstimatedTime;
    private android.widget.TextView tvOrderId;
    private android.widget.TextView tvStatusHistory;
    private android.widget.Button btnRefresh;
    private android.widget.Button btnGoHome;
    private android.widget.ProgressBar progressBar;
    private androidx.cardview.widget.CardView cardNoOrder;
    private int currentStatusIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> statuses = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> statusDescriptions = null;
    @org.jetbrains.annotations.Nullable()
    private com.example.greetandeat2.data.LocalOrder currentOrder;
    
    public TrackingActivity() {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final java.lang.Object loadCurrentOrder(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void showNoOrderUI() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "SimpleDateFormat"})
    private final void setupTrackingUI(com.example.greetandeat2.data.LocalOrder order) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void progressToNextStatus() {
    }
    
    private final java.lang.Object markOrderAsDelivered(java.lang.String orderId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String getEstimatedTime(int statusIndex) {
        return null;
    }
}