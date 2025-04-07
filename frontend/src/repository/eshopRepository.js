import axios from '../axios/axios';

const getToken = () => {
    return localStorage.getItem("token"); // Get the JWT token from localStorage
};

const EShopService = {

    // Categories
    fetchCategories: () => {
        const token = getToken();
        return axios.get("/categories", {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    addCategory: (category) => {
        const token = getToken();
        return axios.post("/categories/add", category, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    // Products
    fetchProducts: () => {
        const token = getToken();
        return axios.get("/products", {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    getProduct: (id) => {
        const token = getToken();
        return axios.get(`/products/${id}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    getProductsByCategory: (categoryId) => {
        const token = getToken();
        return axios.get(`/products/category/${categoryId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    addProduct: (product) => {
        const token = getToken();
        return axios.post("/products/add", product, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    updateProduct: (id, product) => {
        const token = getToken();
        return axios.put(`/products/edit/${id}`, product, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    deleteProduct: (id) => {
        const token = getToken();
        return axios.delete(`/products/delete/${id}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    // Authentication
    login: (email, password) => {
        return axios.post("/auth/login", { email, password });
    },
    register: (username, email, password) => {
        return axios.post("/auth/register", { username, email, password });
    },

    // Cart
    getCartItems: (userId) => {
        const token = getToken();
        return axios.get(`/cart/items/${userId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    addToCart: (userId, productId, quantity) => {
        const token = localStorage.getItem("token"); // Проверка дали токенот е точен

        return axios.post(`/cart/${userId}/add/${productId}`, null, {
            params: { quantity },
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    removeCartItem: (userId, cartItemId) => {
        const token = getToken();
        return axios.delete(`/cart/${userId}/remove/${cartItemId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    clearCart: () => {
        const token = getToken();
        return axios.delete("/cart/clear", {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    // Orders
    createOrder: (userId) => {
        const token = getToken();
        return axios.post(`/orders/${userId}`, null, {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    getOrdersByUserId: (userId) => {
        const token = getToken();
        return axios.get(`/orders/user/${userId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    updateOrderStatus: (orderId, status) => {
        const token = getToken();
        return axios.put(`/orders/${orderId}/status`, status, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    fetchAllOrders: () => {
        const token = getToken();
        return axios.get("/orders", {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    deleteOrder: (orderId) => {
        const token = getToken();
        return axios.delete(`/orders/${orderId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    

};

export default EShopService;
