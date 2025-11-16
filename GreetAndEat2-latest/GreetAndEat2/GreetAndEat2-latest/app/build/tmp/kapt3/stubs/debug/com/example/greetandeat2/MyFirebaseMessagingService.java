package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002J\n\u0010\b\u001a\u0004\u0018\u00010\u0006H\u0002J\u001c\u0010\t\u001a\u00020\u00042\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J6\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u00062\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000bH\u0003J6\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00062\b\u0010\u0016\u001a\u0004\u0018\u00010\u00062\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000bH\u0003J,\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00062\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u000bH\u0003J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\u001c\u0010\u0019\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u0006H\u0002\u00a8\u0006\u001b"}, d2 = {"Lcom/example/greetandeat2/MyFirebaseMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "createNotificationChannel", "", "channelId", "", "importance", "getCurrentUserId", "handleDataMessage", "data", "", "onMessageReceived", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "sendDeliveryNotification", "title", "message", "orderId", "sendNotification", "imageUrl", "sendPromotionNotification", "sendRegistrationToServer", "updateOrderStatus", "status", "app_debug"})
public final class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    
    public MyFirebaseMessagingService() {
        super();
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    private final java.lang.String getCurrentUserId() {
        return null;
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
    
    private final void handleDataMessage(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void updateOrderStatus(java.lang.String orderId, java.lang.String status) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void sendNotification(java.lang.String title, java.lang.String message, java.lang.String imageUrl, java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void sendPromotionNotification(java.lang.String title, java.lang.String message, java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void sendDeliveryNotification(java.lang.String title, java.lang.String message, java.lang.String orderId, java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void createNotificationChannel(java.lang.String channelId, java.lang.String importance) {
    }
    
    private final void sendRegistrationToServer(java.lang.String token) {
    }
}