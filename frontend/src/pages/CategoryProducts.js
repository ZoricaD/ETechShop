import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import EShopService from "../repository/eshopRepository";
import ProductCard from "../components/ProductCard/ProductCard";
import "./Home.css";

const CategoryProducts = () => {
    const { id } = useParams();
    const categoryId = parseInt(id, 10);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        EShopService.getProductsByCategory(categoryId)
            .then(response => setProducts(response.data))
            .catch(error => console.error("Error fetching products: ", error))
            .finally(() => setLoading(false));
    }, [categoryId]);

    return (
        <div className="product-list">
            {loading ? (
                <p>Loading products...</p>
            ) : products.length > 0 ? (
                products.map(product => (
                    <ProductCard key={product.id} product={product} />
                ))
            ) : (
                <div className="no-products">
                    <p>No products found for this category.</p>
                </div>
            )}
        </div>
    );
};

export default CategoryProducts;
