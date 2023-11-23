import React, { useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Container, Card, Button } from 'react-bootstrap';
import base_url from '../api/BootAPI';
import { Navbar, Nav } from 'react-bootstrap';
import LogoutButton from './LogoutButton';
import { Link } from 'react-router-dom';
import PlaceOrderButton from './PlaceOrderButton'; 
import styled, { keyframes } from 'styled-components';




const gradientAnimation = keyframes`
  0% {
    background-position: 0% 50%;
  }
  100% {
    background-position: 100% 50%;
  }
`;

const TotalAmount = styled.h3`
  font-size: 32px;
  background: linear-gradient(45deg, #ff5733, #ff8e00, #ff5733);
  background-size: 200% auto;
  color: white;
  display: inline-block;
  padding: 10px 20px;
  border-radius: 5px;
  animation: ${gradientAnimation} 3s infinite;

  @media (max-width: 768px) {
    font-size: 28px;
  }
`;

const LogoLink = styled(Link)`
  margin-right: 230px; 
  display: inline-block;
  width: 100px; 
  height: 60px; 
  background-size: cover; 
  background-position: center; 
  cursor: pointer;
`;
const WelcomeText = styled(Nav)`
  margin-right: 630px; 
  color: white;
  width: 150px;
`;
const CustomNavbar = styled(Navbar)`
margin: 0;

`;

const SimpleLink = styled(Link)`
  color: #333;
  text-decoration: none;

  &:hover {
    color: #007bff; 
    text-decoration: none;
   
  }
`;


const PageContainer = styled.div`
  background-size: cover;
  background-position: center;
  padding: 20px;
  min-height: calc(100vh - 56px);
  background-color: rgba(255, 255, 255, 0.8);
`;


const CartContainer = styled(Container)`
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: flex-start;
`;


const CartCard = styled(Card)`
  display: flex;
  flex-direction: row;
  margin: 10px;
  background-color: rgba(255, 255, 255, 0.8);
  max-width: 450px;
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;

  &:hover {
    transform: scale(1.03);
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  }
`;


const CardImage = styled(Card.Img)`
  width: 40%;
  height: auto;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  transition: transform 0.2s ease-in-out;

  ${CartCard}:hover & {
    transform: scale(1.1);
  }
`;

const CardDetails = styled(Card.Body)`
  padding: 20px;
  width: 60%;
  transition: transform 0.2s ease-in-out;

  ${CartCard}:hover & {
    transform: translateX(10px);
  }
`;

const RemoveButton = styled(Button)`
  background-color: #ff5733;
  border: none;
  transition: background-color 0.2s ease-in-out;

  &:hover {
    background-color: #ff8e00;
  }
`;

const PlaceOrderButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;


const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);
  const token = Cookies.get('token');

  useEffect(() => {
    fetchCartItems();
  }, []);

  const fetchCartItems = async () => {
    try {
      const token = Cookies.get('token');
      if (!token) {
        throw new Error('You need to log in first.');
      }

      const response = await axios.get(`${base_url}/carts/cartItems`, {
        headers: {
          Authorization: token,
        },
      });
      setCartItems(response.data.cartItems);
      setTotalAmount(response.data.totalAmount);
    } catch (error) {
      toast.error(error.message);
    }
  };

  const handleRemoveFromCart = async (itemId) => {
    try {
      const token = Cookies.get('token');
      if (!token) {
        throw new Error('You need to log in first.');
      }

      await axios.delete(`${base_url}/carts/cartItems/${itemId}`, {
        headers: {
          Authorization: token,
        },
      });
      fetchCartItems();
      toast.success('Product removed from cart.');
    } catch (error) {
      toast.error(error.message);
    }
  };

  const handlePlaceOrder = () => {
    // Clear the cart and update the UI after placing the order
    setCartItems([]);
    setTotalAmount(0);
    toast.success('Order placed successfully!');
  };

  return (
    <PageContainer>
      <ToastContainer />

      <CustomNavbar expand="lg" className="navbar-dark bg-dark">

          <LogoLink to="/" style={{ marginLeft: '10px', backgroundImage: `url(data:image/png;base64,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)` }} />
        <Navbar.Collapse id="navbarSupportedContent">
          <Nav className="ml-auto">
            <WelcomeText>
              Welcome, to cart..
            </WelcomeText>
            
            <Nav.Link as={Link} to="/AboutUs">About Us</Nav.Link>
            <Nav.Link as={Link} to="/contact">
              Contact
            </Nav.Link>
            
            {token ? (
              <>

                <Nav.Link as={Link} to="/your-orders">
                  Orders
                </Nav.Link>
                <LogoutButton />
              </>
            ) : (
              <Nav.Link as={Link} to="/login">
                Log In
              </Nav.Link>
            )}
          </Nav>
        </Navbar.Collapse>
      </CustomNavbar>


      <CartContainer>
      {cartItems.length > 0 ? (
  cartItems.map((item) => (
    <SimpleLink key={item.id} to={`/products/${item.productId}`}>
      <CartCard>
        <CardImage variant="top" src={item.imageURL} />
        <CardDetails>
          <h5>{item.productName}</h5>
          <p>Price: ${item.amount}</p>
          <p>Quantity: {item.quantity}</p>
          <RemoveButton
            variant="danger"
            onClick={() => handleRemoveFromCart(item.id)}
          >
            Remove
          </RemoveButton>
        </CardDetails>
      </CartCard>
    </SimpleLink>
  ))
) : (
  <p>Your cart is empty.</p>
)}

      </CartContainer>

      
      <PlaceOrderButtonWrapper>
        <PlaceOrderButton
          cartItems={cartItems}
          totalAmount={totalAmount}
          onPlaceOrder={handlePlaceOrder}
        />
      </PlaceOrderButtonWrapper>

      <TotalAmount>Total Amount: ${totalAmount}</TotalAmount>
    </PageContainer>
  );
};

export default CartPage;
