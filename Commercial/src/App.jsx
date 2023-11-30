import { useState } from 'react'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import { Footer } from './component/footer/Footer'
import { Header } from './component/header/Header'
import Home from './component/home/Home'
import { Products } from './component/products/products'
import { ProductDetail } from './component/productsDetail/ProductDetail'
import { Login } from './component/login/Login'
import { SignUp } from './component/signUp/SignUp'
import './App.css'
import { Contact } from './component/contact/Contact'
import { AboutUs } from './component/About/AboutUs'
import { Cart } from './component/cart/Cart'
import {Orders} from './component/orders/Orders'

function App() {


  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/login" element={<Login />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/aboutus" element={<AboutUs />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/products" element={<Products />} />
        <Route path="/products/:id" element={<ProductDetail />} />
      </Routes>
    </Router>
  )
}

export default App
