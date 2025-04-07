import React, { useEffect, useState } from "react";
import EShopService from "../repository/eshopRepository";
import { FaShoppingCart } from "react-icons/fa";
import "./Cart.css";

const Cart = () => {
    const [cartItems, setCartItems] = useState([]);

    useEffect(() => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            console.error("No userId found in localStorage");
            return;
        }

        EShopService.getCartItems(userId)
            .then(response => {
                setCartItems(response.data);
            })
            .catch((error) => {
                console.error("Error fetching cart items: ", error);
            });
    }, []);

    const handleRemoveItem = (cartItemId) => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Not logged in!");
            return;
        }
        EShopService.removeCartItem(userId, cartItemId)
            .then(() => {
                setCartItems(cartItems.filter(item => item.id !== cartItemId));
            })
            .catch((error) => {
                console.error("Error: ", error);
            });
    };

    const handleCreateOrder = () => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            console.error("No userId found in localStorage");
            return;
        }

        EShopService.createOrder(userId)
            .then(() => {
                alert("Order created successfully!");
                setCartItems([]);
            })
            .catch((error) => {
                console.error("Error creating order: ", error);
            });
    };

    return (
        <div className="cart">
            <h2 className="title-cart">
                <FaShoppingCart className="cart-icon" />
                Cart Overview
            </h2>

            {cartItems.length === 0 ? (
                <p>Your cart is empty!</p>
            ) : (
                <ul className="cart-items">
                    {cartItems.map(item => (
                        <li key={item.id} className="cart-item">
                            <img src={item.product.imageUrl} alt={item.product.name} className="cart-item-image" />
                            <div className="cart-item-details">
                                <h3 className="product-name">{item.product.name}</h3>
                                <p className="cart-item-info">
                                    Quantity: {item.quantity} | Price: ${item.product.price} | Total: ${item.quantity * item.product.price}
                                </p>
                            </div>
                            <button className="remove-btn" onClick={() => handleRemoveItem(item.id)}>Remove</button>
                        </li>
                    ))}
                </ul>
            )}
            {cartItems.length > 0 && (
                <button className="checkout-btn" onClick={handleCreateOrder}>Place Order</button>
            )}
        </div>
    );
};

export default Cart;
