import React from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';

const CartButton = () => {
  const token = Cookies.get('token');

  if (token) {
    return (
      <Link to="/cart" style={{ marginLeft: '10px' }}>
        <span role="img" aria-label="Cart" style={{ fontSize: '25px', cursor: 'pointer' }}>
          🛒
        </span>
      </Link>
    );
  }

  return null;
};

export default CartButton;
