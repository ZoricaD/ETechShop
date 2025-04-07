import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaUserPlus } from "react-icons/fa";
import EShopService from "../repository/eshopRepository";
import "./Register.css";

const Register = () => {
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        password: "",
    });
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await EShopService.register(formData.username, formData.email, formData.password);
            alert("Registration successful!");
            navigate("/login");
        } catch (error) {
            console.error("There was an error registering!", error);
            setErrorMessage("Registration failed! Please try again.");
        }
    };

    return (
        <div className="register-container">
            <h2 className="register-title">
                <FaUserPlus style={{ marginRight: "10px", color: "#c82333" }} />
                Register
            </h2>
            {errorMessage && <p className="register-error">{errorMessage}</p>}
            <form className="register-form" onSubmit={handleSubmit}>
                <div className="register-input-container">
                    <label>Username</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        className="register-input"
                        required
                    />
                </div>
                <div className="register-input-container">
                    <label>Email</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        className="register-input"
                        required
                    />
                </div>
                <div className="register-input-container">
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        className="register-input"
                        required
                    />
                </div>
                <button type="submit" className="register-button">Register</button>
            </form>
        </div>
    );
};

export default Register;
