import axios from 'axios';
import Cookies from 'js-cookie';
import base_url from '../api/BootAPI';

const addToCart = async (productId, quantity) => {
  try {
    const token = Cookies.get('token');
    console.log('Error adding product to cart:', token);
    if (token === undefined) {
      return `Login to continue shopping`;
    }

    const response = await axios.post(
      `${base_url}/carts/cartItems`,
      {
        productId: productId,
        quantity: quantity,
      },
      {
        headers: {
          Authorization: `${token}`,
        },
      }
    );

    if (response.status >= 200 && response.status < 300) {
      return `Added ${quantity} item(s) to cart.`;
    } else {
      throw new Error('Failed to add product to cart.');
    }
  } catch (error) {
    console.log('Error adding product to cart:', error);
    throw new Error('Failed to add product to cart.');
  }
};

export default addToCart;
