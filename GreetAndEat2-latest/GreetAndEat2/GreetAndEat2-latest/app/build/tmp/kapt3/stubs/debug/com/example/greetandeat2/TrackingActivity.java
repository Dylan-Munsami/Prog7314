package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0015J\b\u0010\u0013\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/greetandeat2/TrackingActivity;", "Lcom/example/greetandeat2/BaseActivity;", "()V", "btnGoHome", "Landroid/widget/Button;", "btnRefresh", "currentStatusIndex", "", "statuses", "", "tvEstimatedTime", "Landroid/widget/TextView;", "tvOrderId", "tvStatus", "tvStatusHistory", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "progressToNextStatus", "app_debug"})
public final class TrackingActivity extends com.example.greetandeat2.BaseActivity {
    private android.widget.TextView tvStatus;
    private android.widget.TextView tvEstimatedTime;
    private android.widget.TextView tvOrderId;
    private android.widget.TextView tvStatusHistory;
    private android.widget.Button btnRefresh;
    private android.widget.Button btnGoHome;
    private int currentStatusIndex = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> statuses = null;
    
    public TrackingActivity() {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingInflatedId"})
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void progressToNextStatus() {
    }
}