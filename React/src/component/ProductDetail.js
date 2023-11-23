import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { Form, Row, Col, Navbar, Nav } from 'react-bootstrap';
import base_url from '../api/BootAPI';
import addToCart from './CartItems';
import CartButton from './CartButton';
import styled from 'styled-components';
import Cookies from 'js-cookie';
import LogoutButton from './LogoutButton';



const CustomNavbar = styled(Navbar)`
  background-color: black;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);

  .navbar-brand {
    margin-right: 35px; 
  }
  img {
    filter: brightness(10); 
  }
  .nav-link {
    color: white ; 
  }
  .navbar-toggler-icon {
    background-color: white; 
  }

`;



const CartButtonContainer = styled.div`
  margin-left: auto;
`;


const LogoLink = styled(Link)`
  margin-right: 960px; 
  display: inline-block;
  width: 100px; 
  height: 60px; 
  background-size: cover; 
  background-position: center; 
  cursor: pointer;
`;

const PageContainer = styled.div`
  background-color: #f5f5f5;
  background-size: cover;
  background-position: center;
  padding: 20px;
  min-height: calc(100vh - 56px);
`;

const CardContainer = styled.div`
  display: flex;
  border: none;
  border-radius: 0px;
  box-shadow: none;
  transition: transform 0.3s ease;
  margin: auto;
  margin-top: 50px;
  margin-left: 50px;
  max-width: 800px;
`;

const CardImage = styled(Card.Img)`
  width: 30%;
  height: auto;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border-top-right-radius: 10px;
`;

const ProductDetails = styled.div`
  padding: 15px;
  width: 500%;
  justify-content: space-between;
`;

const ProductText = styled.div`
  font-size: 20px;
  line-height: 1;
`;

const QuantityInput = styled(Form.Control)`
  width: 100px;
`;

function ProductDetail() {
  const { id } = useParams();
  const token = Cookies.get('token');


  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);

  const fetchProduct = useCallback(async () => {
    try {
      const response = await axios.get(`${base_url}/products/${id}`);
      setProduct(response.data);
    } catch (error) {
      console.log('Error fetching product details:', error);
    }
  }, [id]);

  useEffect(() => {
    fetchProduct();
  }, [fetchProduct]);

  const handleAddToCart = async () => {
    try {
      const responseMessage = await addToCart(product.id, quantity);
      toast.success(responseMessage);
    } catch (error) {
      toast.error(error.message);
    }
  };

  const handleQuantityChange = (event) => {
    const newQuantity = parseInt(event.target.value, 10);
    if (!isNaN(newQuantity) && newQuantity >= 1 && newQuantity <= product.quantity) {
      setQuantity(newQuantity);
    }
  };

  return (
    <PageContainer>
      <CustomNavbar expand="lg" className="navbar-dark bg-dark">

        <LogoLink to="/" style={{ marginLeft: '10px', backgroundImage: `url(data:image/png;base64,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)` }} />
        <Navbar.Collapse id="navbarSupportedContent">
          <Nav className="ml-auto">
            <Nav.Link as={Link} to="/AboutUs">About Us</Nav.Link>
            <Nav.Link as={Link} to="/contact">
              Contact
            </Nav.Link>
            <CartButtonContainer>
              <CartButton />
            </CartButtonContainer>
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






      <div style={{ textAlign: 'center' }}>
        <ToastContainer />

        {product ? (
          <CardContainer>
            <CardImage variant="top" src={product.imageURL} alt={product.name} />
            <ProductDetails>
              <ProductText>
                <h2>{product.name}</h2>
                <p>Price: ${product.price}</p>
                <p>Gender: {product.gender}</p>
                <p>Category: {product.category}</p>
                <p>Subcategory: {product.subCategory}</p>
                <p>Color: {product.colour}</p>
                <p>Usage: {product.pUsage}</p>
              </ProductText>
              <Form>
                <Form.Group as={Row} controlId="formQuantity">
                  <Form.Label column sm={6}>
                    Quantity:
                  </Form.Label>
                  <Col sm={6}>
                    <QuantityInput
                      type="number"
                      value={quantity}
                      onChange={handleQuantityChange}
                      min={1}
                      max={product.quantity}
                    />
                  </Col>
                </Form.Group>
              </Form>
              <Button onClick={handleAddToCart} variant="success" style={{ marginTop: '10px' }}>
  Add to Cart
</Button>
            </ProductDetails>
          </CardContainer>
        ) : (
          <p>Loading product details...</p>
        )}
      </div>
    </PageContainer>
  );
}

export default ProductDetail;
