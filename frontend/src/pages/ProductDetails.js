import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import EShopService from "../repository/eshopRepository";
import { FaShoppingCart } from 'react-icons/fa';
import "./ProductDetails.css";

const ProductDetails = () => {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {
        EShopService.getProduct(id)
            .then(response => setProduct(response.data))
            .catch(error => console.error("Error fetching product details", error));
    }, [id]);

    const handleAddToCart = () => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Please log in to add to cart!");
            return;
        }

        EShopService.addToCart(userId, product.id, quantity)
            .then(() => {
                alert("Product added to cart successfully!");
            })
            .catch(error => {
                console.error("Error adding product to cart", error);
                alert("Failed to add product to cart!");
            });
    };

    if (!product) {
        return <p>Loading product details...</p>;
    }

    return (
        <div className="product-details">
            <img src={product.imageUrl} alt={product.name} className="product-image" />
            <div className="product-info">
                <h2>{product.name}</h2>
                <p>{product.description}</p>
                <p className="price">${product.price}</p>

                <input
                    type="number"
                    value={quantity}
                    min="1"
                    onChange={(e) => setQuantity(e.target.value)}
                    className="quantity-input"
                />
                <button onClick={handleAddToCart} className="add-to-cart-btn">
                    <FaShoppingCart className="icon" /> Add to Cart
                </button>
            </div>
        </div>
    );
};

export default ProductDetails;
