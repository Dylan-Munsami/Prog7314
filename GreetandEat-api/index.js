const express = require('express');
const app = express();
app.use(express.json());

let orders = [];
let nextOrderId = 1;

app.post('/orders', (req, res) => {
    const { userId, items, total } = req.body;
    if (!userId || !items || !total) return res.status(400).json({ error: 'Missing fields' });

    const order = { id: nextOrderId.toString(), userId, items, total, status: 'PENDING' };
    orders.push(order);
    nextOrderId++;

    res.status(201).json(order);
});


app.get('/orders/:userId', (req, res) => {
    const userOrders = orders.filter(o => o.userId === req.params.userId);
    res.json(userOrders);
});


app.patch('/orders/:orderId', (req, res) => {
    const order = orders.find(o => o.id === req.params.orderId);
    if (!order) return res.status(404).json({ error: 'Order not found' });

    const { status } = req.body;
    order.status = status || order.status;
    res.json(order);
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`API running on port ${PORT}`));
