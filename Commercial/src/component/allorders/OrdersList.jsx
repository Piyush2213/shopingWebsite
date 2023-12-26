import React, { useState, useEffect } from 'react';
import axios from 'axios';
import base_url from '../baseUrl/BaseUrl';
import Cookies from 'js-cookie';
import { Footer } from '../footer/Footer';
import { Header2 } from '../header2/header2';
import { useNavigate } from 'react-router-dom';
import OrderCard from './OrderCard';

export const OrdersList = () => {
  const [orders, setOrders] = useState([]);
  const token = Cookies.get('token');
  const navigate = useNavigate();

  const fetchOrders = async () => {
    try {
      const response = await axios.get(`${base_url}/orders/getAllOrders`, {
        headers: {
          Authorization: token,
        },
      });
      setOrders(response.data);
    } catch (error) {
      console.error('Error fetching orders:', error);
      navigate('/admin-login');
    }
  };

  useEffect(() => {
    fetchOrders();
  }, [token, navigate]);

  const handleUpdateStatus = (orderId, newStatus) => {
    setOrders((prevOrders) =>
      prevOrders.map((order) =>
        order.id === orderId ? { ...order, orderStatus: newStatus } : order
      )
    );
  };

  return (
    <div>
      <Header2 />

      <div className="mx-auto grid w-full max-w-7xl items-center space-y-4 px-2 py-10 md:grid-cols-2 md:gap-6 md:space-y-0 lg:grid-cols-4">
        {orders.map((order) => (
          <OrderCard key={order.id} order={order} onUpdateStatus={handleUpdateStatus} />
        ))}
      </div>
      <Footer />
    </div>
  );
};


