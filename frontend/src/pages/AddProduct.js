import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import EShopService from "../repository/eshopRepository";
import "./AddProduct.css";

const AddProduct = () => {
    const navigate = useNavigate();
    const [product, setProduct] = useState({
        name: "",
        description: "",
        price: "",
        imageUrl: "",
        category: { id: "" }
    });
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        EShopService.fetchCategories()
            .then(response => setCategories(response.data))
            .catch(error => console.error("Error fetching categories", error));
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "category") {
            setProduct({ ...product, category: { id: value } });
        } else {
            setProduct({ ...product, [name]: value });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        EShopService.addProduct(product)
            .then(() => {
                alert("Product added successfully!");
                navigate("/");
            })
            .catch(error => console.error("Error adding product", error));
    };

    return (
        <div className="add-product">
            <h2>Add New Product</h2>
            <form onSubmit={handleSubmit}>
                <label>Name:</label>
                <input type="text" name="name" value={product.name} onChange={handleChange} required />

                <label>Description:</label>
                <textarea name="description" value={product.description} onChange={handleChange} required />

                <label>Price:</label>
                <input type="number" name="price" value={product.price} onChange={handleChange} required />

                <label>Image URL:</label>
                <input type="text" name="imageUrl" value={product.imageUrl} onChange={handleChange} required />

                <label>Category:</label>
                <select name="category" value={product.category.id} onChange={handleChange} required>
                    <option value="">Select Category</option>
                    {categories.map(cat => (
                        <option key={cat.id} value={cat.id}>{cat.name}</option>
                    ))}
                </select>

                <button type="submit">Add Product</button>
            </form>
        </div>
    );
};

export default AddProduct;
