import React from "react";
import { Link, useNavigate } from "react-router-dom";
import EShopService from "../../repository/eshopRepository";
import { FaShoppingCart, FaTrash, FaEdit } from "react-icons/fa";
import "./ProductCard.css";

const ProductCard = ({ product }) => {
    const navigate = useNavigate();

    const userRole = localStorage.getItem("role");

    const handleDelete = () => {
        if (window.confirm(`Are you sure you want to delete "${product.name}"?`)) {
            EShopService.deleteProduct(product.id)
                .then(() => {
                    alert("Product deleted successfully");
                    window.location.reload();
                })
                .catch(error => console.error("Error deleting product: ", error));
        }
    };

    const handleEdit = () => {
        navigate(`/edit-product/${product.id}`);
    };

    const handleAddToCart = () => {
        const userId = localStorage.getItem("userId");

        if (!userId) {
            alert("You need to log in first!");
            return;
        }

        EShopService.addToCart(userId, product.id, 1)
            .then(() => alert("Product added to cart successfully!"))
            .catch(error => {
                console.error("Error adding product to cart: ", error);
                alert("Failed to add product to cart!");
            });
    };

    return (
        <div className="product-card">
            <Link to={`/products/${product.id}`} className="product-link">
                <img src={product.imageUrl} alt={product.name} className="product-image"/>
                <h3 className="product-name">{product.name}</h3>
                <p className="product-price">Price: ${product.price}</p>
            </Link>
            <div className="product-card-buttons">
                {userRole === "ADMIN" && (
                    <>
                        <button onClick={handleEdit} className="icon-btn edit-btn">
                            <FaEdit />
                        </button>
                        <button onClick={handleDelete} className="icon-btn delete-btn">
                            <FaTrash />
                        </button>
                    </>
                )}
                <button onClick={handleAddToCart} className="icon-btn add-to-cart-btn">
                    Add
                    <FaShoppingCart />
                </button>
            </div>
        </div>
    );
};

export default ProductCard;
