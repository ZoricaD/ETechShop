import React, { useEffect, useState } from "react";
import EShopService from "../repository/eshopRepository";
import ProductCard from "../components/ProductCard/ProductCard";
import "./Home.css";
import { useNavigate } from "react-router-dom";

const Home = () => {
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [isAdmin, setIsAdmin] = useState(false);

    useEffect(() => {
        const userRole = localStorage.getItem("role");
        if (userRole === "ADMIN") {
            setIsAdmin(true);
        }

        EShopService.fetchProducts()
            .then(response => setProducts(response.data))
            .catch(error => console.error("Error fetching products: ", error));
    }, []);

    return (
        <div className="home-container">
            <h2 className="home-title">💻 Find Your Next Favorite Gadget!</h2>
            <p className="home-subtitle">Great prices. Quality you can trust. All in one place.</p>

            {isAdmin && (
                <button className="add-product-btn" onClick={() => navigate("/products/add")}>
                    Add Product
                </button>
            )}
            <div className="product-list">
                {products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))}
            </div>
        </div>
    );
};

export default Home;
