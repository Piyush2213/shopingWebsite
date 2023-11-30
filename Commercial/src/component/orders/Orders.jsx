import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import base_url from '../baseUrl/BaseUrl';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Container, Card } from 'react-bootstrap';
import styled from 'styled-components';
import { Header2 } from '../header2/header2';
import { Footer } from '../footer/Footer';


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

export const Orders = () => {
    const [orders, setOrders] = useState([]);
    const token = Cookies.get('token');
    const username = Cookies.get('firstName');

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

    return (<div>
        <Header2 username={username} token={token}/>
        <PageContainer>
            <ToastContainer />
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
         <Footer />
         </div>
        
    );
};


