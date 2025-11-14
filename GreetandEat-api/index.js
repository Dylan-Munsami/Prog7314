// index.js
const express = require('express');
const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const app = express();
app.use(express.json());

// In-memory storage
let orders = [];
let nextOrderId = 1;
const userTokens = {}; // userId -> FCM token

// Register FCM token
app.post('/register-token', (req, res) => {
    const { userId, fcmToken } = req.body;
    if (!userId || !fcmToken) return res.status(400).json({ error: 'Missing fields' });

    userTokens[userId] = fcmToken;
    console.log(`Registered FCM token for user ${userId}`);
    res.status(200).json({ success: true });
});

// Place a new order
app.post('/orders', (req, res) => {
    const { userId, items, total } = req.body;
    if (!userId || !items || !total) return res.status(400).json({ error: 'Missing fields' });

    const order = { id: nextOrderId.toString(), userId, items, total, status: 'PENDING' };
    orders.push(order);
    nextOrderId++;

    res.status(201).json(order);
});

// Get all orders for a user
app.get('/orders/:userId', (req, res) => {
    const userOrders = orders.filter(o => o.userId === req.params.userId);
    res.json(userOrders);
});

// Update order status
app.patch('/orders/:orderId', async (req, res) => {
    const order = orders.find(o => o.id === req.params.orderId);
    if (!order) return res.status(404).json({ error: 'Order not found' });

    const { status } = req.body;
    order.status = status || order.status;

    // Send FCM notification if token exists
    const token = userTokens[order.userId];
    if (token) {
        try {
            await admin.messaging().send({
                token,
                notification: {
                    title: 'Order Update',
                    body: `Your order #${order.id} is now ${order.status}`
                },
                data: {
                    type: 'order_status',
                    orderId: order.id,
                    status: order.status
                }
            });
            console.log(`Notification sent to user ${order.userId}`);
        } catch (err) {
            console.error('Error sending notification:', err);
        }
    }

    res.json(order);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`API running on port ${PORT}`));
