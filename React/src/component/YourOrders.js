import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import base_url from '../api/BootAPI';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Container, Card } from 'react-bootstrap';
import styled from 'styled-components';
import { Navbar, Nav } from 'react-bootstrap';
import LogoutButton from './LogoutButton';
import { Link } from 'react-router-dom';

const PageContainer = styled.div`
  background-color: #f5f5f5;
  background-size: cover;
  background-position: center;
  padding: 20px;
  min-height: calc(100vh - 56px);
`;

const OrderCard = styled(Card)`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0;
  background-color: rgba(255, 255, 255, 0.95);
  max-width: 2000px;
  padding: 20px;
 
  border-radius: 10px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
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


const OrderContainer = styled(Container)`
  display: flex;
  flex-direction: column;
  align-items: center;
`;


const ProductImage = styled.img`
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 50%;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
`;

const OrderDetails = styled.div`
  flex: 1;
`;


const OrderTitle = styled.h4`
  margin-bottom: 5px;
  color: #333;
`;

const OrderText = styled.p`
  margin: 0;
  color: #666;
`;

const TotalAmountText = styled.p`
  font-size: 1.2em;
  font-weight: 600;
  color: #333;
  margin: 20px 0;
  text-align: center;
`;

const LeftAlignedText = styled.div`
  text-align: left;
`;

const YourOrders = () => {
  const [orders, setOrders] = useState([]);
  const token = Cookies.get('token');

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const token = Cookies.get('token');
      if (!token) {
        throw new Error('You need to log in first.');
      }

      const response = await axios.get(`${base_url}/orders`, {
        headers: {
          Authorization: token,
        },
      });

      setOrders(response.data);
    } catch (error) {
      toast.error(error.message);
    }
  };

  return (
    <PageContainer>
      <ToastContainer />

      <CustomNavbar expand="lg" className="navbar-dark bg-dark">

        <LogoLink to="/" style={{ marginLeft: '10px', backgroundImage: `url(data:image/png;base64,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)` }} />
        <Navbar.Collapse id="navbarSupportedContent">
          <Nav className="ml-auto">
            <WelcomeText>
              Welcome, to orders..
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



      <OrderContainer>
        {orders.length > 0 ? (
          orders.map((order) => (
            <OrderCard key={order.id} style={{ marginBottom: '20px' }}>
              <div style={{ backgroundColor: 'rgba(200, 200, 200, 0.9)', padding: '20px', borderRadius: '10px' }}>

                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <div style={{ marginBottom: '25px', fontSize: '17px' }}>
                    <OrderTitle style={{ marginBottom: '55px', fontSize: '16px' }}>Order ID: {order.id}</OrderTitle>
                  </div>
                  <div style={{ marginBottom: '30px', fontSize: '14px' }}>Date and Time: {order.dateTime}</div>
                  <div>
                    <TotalAmountText style={{ marginBottom: '50px', marginLeft: '185px', fontSize: '16px' }}>Total: ${order.totalAmount}</TotalAmountText>
                  </div>

                </div>

              </div>

              <div style={{ backgroundColor: '#f5f5f5', padding: '1px', borderRadius: '10px' }}>

                <hr style={{ margin: '20px 0', marginTop: '-50px' }} />
                {order.orderItems.map((item) => (
                  <div
                    key={item.productId}
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      marginBottom: '15px',
                      marginLeft: '-20px',
                      marginRight: '-20px',
                      padding: '20px',
                      backgroundColor: 'rgba(255, 255, 255, 0.95)',
                      borderRadius: '10px',
                    }}
                  >
                   
                      <ProductImage src={item.productImageURL} alt={item.productName} />
                      <OrderDetails>
                        <LeftAlignedText>
                          <OrderText>
                            <strong>{item.productName}</strong> - Quantity: {item.quantity}
                          </OrderText>
                          <OrderText>Price: ${item.price}</OrderText>
                        </LeftAlignedText>
                      </OrderDetails>
                      
                  </div>
                ))}
                <hr style={{ margin: ' 0' }} />
              </div>

              <div>
                {/* Footer */}
              </div>
            </OrderCard>
          ))
        ) : (
          <p>No orders available.</p>
        )}
      </OrderContainer>
    </PageContainer>
  );
};

export default YourOrders;
