import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Home from './component/home/Home'
import { Products } from './component/products/Products'
import { ProductDetail } from './component/productsDetail/ProductDetail'
import { Login } from './component/login/Login'
import { SignUp } from './component/signUp/SignUp'
import './App.css'
import { Contact } from './component/contact/Contact'
import { AboutUs } from './component/about/AboutUs'
import { Cart } from './component/cart/Cart'
import {Orders} from './component/orders/Orders'
import AddressEntry from './component/address/AddressEntry'
import { AdminLogin } from './component/login/AdminLogin'
import { OrdersList } from './component/allorders/OrdersList'

function App() {


  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/login" element={<Login />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/orders" element={<Orders />} />
        <Route path="/aboutus" element={<AboutUs />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/address-entry" element={<AddressEntry />} />
        <Route path="/products" element={<Products />} />
        <Route path="/Orderslist" element={<OrdersList />} />
        <Route path="/products/:id" element={<ProductDetail />} />
      </Routes>
    </Router>
  )
}

export default App
