const express = require("express");
const app = express();
const PORT = process.env.PORT || 10000;

// Middleware
app.use(express.json());

// In-memory orders
let orders = [];
let orderIdCounter = 1;

// Status progression order
const statusFlow = ["ORDER_RECEIVED", "PREPARING", "READY_FOR_PICKUP", "OUT_FOR_DELIVERY", "DELIVERED"];

app.get("/", (req, res) => {
  res.json({ message: "Hello from your Render API!" });
});

// Place a new order
app.post("/orders", (req, res) => {
  const order = req.body;
  order.id = orderIdCounter.toString();
  order.status = "ORDER_RECEIVED";
  order.createdAt = new Date().toISOString();
  order.estimatedDelivery = new Date(Date.now() + 45 * 60000).toISOString();
  order.statusHistory = [
    { 
      status: "ORDER_RECEIVED", 
      timestamp: new Date().toISOString(), 
      message: "Order received and payment confirmed" 
    }
  ];
  
  orders.push(order);
  orderIdCounter++;
  console.log("New order placed:", order.id);
  res.json(order);
});

// Get orders by user ID
app.get("/orders/:userId", (req, res) => {
  const userOrders = orders.filter(o => o.userId === req.params.userId);
  res.json(userOrders);
});

// Get specific order by ID
app.get("/order/:orderId", (req, res) => {
  const order = orders.find(o => o.id === req.params.orderId);
  if (!order) return res.status(404).json({ message: "Order not found" });
  res.json(order);
});

// ✅✅✅ ADD THIS CRITICAL ENDPOINT ✅✅✅
app.post("/orders/:orderId/next", (req, res) => {
  console.log(`Progressing order ${req.params.orderId} to next status`);
  
  const order = orders.find(o => o.id === req.params.orderId);
  if (!order) {
    console.log(`Order ${req.params.orderId} not found`);
    return res.status(404).json({ message: "Order not found" });
  }
  
  const currentIndex = statusFlow.indexOf(order.status);
  console.log(`Current status: ${order.status}, index: ${currentIndex}`);
  
  if (currentIndex < statusFlow.length - 1) {
    const nextStatus = statusFlow[currentIndex + 1];
    order.status = nextStatus;
    
    // Add to status history
    order.statusHistory.push({
      status: nextStatus,
      timestamp: new Date().toISOString(),
      message: getStatusMessage(nextStatus)
    });
    
    console.log(`Order ${order.id} progressed to: ${nextStatus}`);
    res.json(order);
  } else {
    console.log(`Order ${order.id} already at final status: ${order.status}`);
    res.json(order); // Already at final status
  }
});

// Update order status manually
app.patch("/orders/:orderId/status", (req, res) => {
  const { status, message } = req.body;
  const order = orders.find(o => o.id === req.params.orderId);
  
  if (!order) return res.status(404).json({ message: "Order not found" });
  
  order.status = status;
  order.statusHistory.push({
    status: status,
    timestamp: new Date().toISOString(),
    message: message || getStatusMessage(status)
  });
  
  res.json(order);
});

// Get all orders
app.get("/orders", (req, res) => {
  res.json(orders);
});

// Helper function for status messages
function getStatusMessage(status) {
  const messages = {
    "ORDER_RECEIVED": "Order received and payment confirmed",
    "PREPARING": "Kitchen is preparing your food", 
    "READY_FOR_PICKUP": "Your order is ready for delivery",
    "OUT_FOR_DELIVERY": "Driver is on the way with your order",
    "DELIVERED": "Order delivered! Enjoy your meal!"
  };
  return messages[status] || "Order status updated";
}

// Start server
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});