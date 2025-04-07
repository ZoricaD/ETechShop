import './App.css';
import { Routes, Route } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";
import Navbar from "../Navbar/Navbar";
import Home from "../../pages/Home";
import ProductDetails from "../../pages/ProductDetails";
import CategoryProducts from "../../pages/CategoryProducts";
import EditProduct from "../../pages/EditProduct";
import AddProduct from "../../pages/AddProduct";
import Login from "../../pages/Login";
import Register from "../../pages/Register";
import Cart from "../../pages/Cart";
import MyOrders from "../../pages/MyOrders";
import AdminOrders from "../../pages/AdminOrders";

function App() {
    const [token, setToken] = useState(localStorage.getItem("token"));


    useEffect(() => {
        if (token) {
            axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        } else {
            delete axios.defaults.headers.common["Authorization"];
        }
    }, [token]);

    return (
        <div className="App">
            <Navbar />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/products/:id" element={<ProductDetails />} />
                <Route path="/category/:id" element={<CategoryProducts />} />
                <Route path="/edit-product/:id" element={<EditProduct />} />
                <Route path="/products/add" element={<AddProduct />} />
                <Route path="/cart" element={<Cart />} />
                <Route path="/my-orders" element={<MyOrders />} />
                <Route path="/admin/orders" element={<AdminOrders />} />


                <Route path="/login" element={<Login setToken={setToken} />} />
                <Route path="/register" element={<Register />} />
            </Routes>
        </div>
    );
}

export default App;
