import React, { useEffect, useState } from "react";
import { FaShoppingBag } from "react-icons/fa";
import EShopService from "../repository/eshopRepository";
import "./MyOrders.css";

const MyOrders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        if (userId) {
            EShopService.getOrdersByUserId(userId)
                .then(response => {
                    setOrders(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error("Error fetching orders:", error);
                    setLoading(false);
                });
        }
    }, [userId]);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    return (
        <div className="orders-container">
            <h2 className="orders-title">
                <FaShoppingBag className="orders-icon" />
                My Orders
            </h2>
            {orders.length === 0 ? (
                <p className="no-orders">No orders found.</p>
            ) : (
                <ul className="orders-list">
                    {orders.map(order => (
                        <li key={order.id} className="order-item">
                            <p><strong>Order ID:</strong> {order.id}</p>
                            <p><strong>Status:</strong> {order.status}</p>
                            <p><strong>Created At:</strong> {new Date(order.createdAt).toLocaleString()}</p>
                            <p><strong>Total Price:</strong> ${order.totalPrice.toFixed(2)}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MyOrders;
