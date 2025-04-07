import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import EShopService from "../repository/eshopRepository";
import "./Login.css";

const Login = ({ setToken }) => {
    const [formData, setFormData] = useState({ username: "", password: "" });
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await EShopService.login(formData.username, formData.password);
            const { token, userId, role } = response.data;

            if (token) {
                localStorage.setItem("token", token);
                localStorage.setItem("userId", userId);
                localStorage.setItem("role", role);
                setToken(token);
                navigate("/");
            }
        } catch (error) {
            console.error("Login error:", error);
            setErrorMessage("Invalid credentials. Please try again.");
        }
    };

    return (
        <div className="login-container">
            <h2 className="login-title">
                <FaUserCircle className="login-icon" />
                Login
            </h2>
            {errorMessage && <p className="login-error">{errorMessage}</p>}
            <form onSubmit={handleSubmit} className="login-form">
                <div className="login-input-container">
                    <label>Email</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        className="login-input"
                        required
                    />
                </div>
                <div className="login-input-container">
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        className="login-input"
                        required
                    />
                </div>
                <button type="submit" className="login-button">Login</button>
            </form>
        </div>
    );
};

export default Login;
