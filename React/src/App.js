import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './component/Home';
import SignUp from './component/SignUp';
import LogIn from './component/LogIn';
import Contact from './component/Contact';
import Products from './component/Products';
import CartPage from './component/CartPage';
import ProductDetail from './component/ProductDetail';
import YourOrders from './component/YourOrders';
import AboutUs from './component/AboutUs';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<LogIn />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/products" element={<Products />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/your-orders" element={<YourOrders />} />
        <Route path="/aboutus" element={<AboutUs />} />
      </Routes>
    </Router>
  );
}

export default App;
