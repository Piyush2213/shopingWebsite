import React from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';
import base_url from '../api/BootAPI';

const PlaceOrderButton = ({ cartItems, totalAmount, onPlaceOrder }) => {
  const handlePlaceOrder = async () => {
    try {
      const token = Cookies.get('token');
      if (!token) {
        throw new Error('You need to log in first.');
      }

      const response = await axios.post(
        `${base_url}/orders`,
        {},
        {
          headers: {
            Authorization: token,
          },
        }
      );

      if (response.status === 201) {
        onPlaceOrder(); // Call the callback function to update the UI after placing the order
      } else {
        throw new Error('Failed to place order.');
      }
    } catch (error) {
      console.log('Error placing order:', error);
    }
  };

  return (
    <button
      type="button"
      onClick={handlePlaceOrder}
      disabled={cartItems.length === 0}
      className="btn btn-success" 
    >
      Place Order
    </button>
  );
};

export default PlaceOrderButton;
