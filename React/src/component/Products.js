import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { Navbar, FormControl, Col, Nav } from 'react-bootstrap';
import CartButton from './CartButton';
import base_url from '../api/BootAPI';
import Cookies from 'js-cookie';
import LogoutButton from './LogoutButton';



const LogoLink = styled(Link)`
  margin-right: 30px; 
  display: inline-block;
  width: 100px; 
  height: 60px; 
  background-size: cover; 
  background-position: center; 
  cursor: pointer;
`;
const WelcomeText = styled(Nav)`
  margin-right: 30px; 
  color: white;
  width: 150px;
`;

const PaginationContainer = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;

const PaginationButton = styled.button`
  margin: 0 10px;
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #fff;
  font-size: 14px;
  cursor: pointer;

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
`;

const PageContainer = styled.div`
  background-color: #f5f5f5;
  background-size: cover;
  background-position: center;
  padding: 20px;
  min-height: 100vh;
  display: grid;
  grid-template-columns: 300px 1fr; 
  gap: 20px;
`;



const SearchInput = styled(FormControl)`
  border: none;
  margin-right: 20px; 
  border-bottom: 1px solid #ccc;
  border-radius: 15px;
  outline: none;
  padding: 5px 10px;
  font-size: 16px;
  width: 700px;
  transition: border-bottom-color 0.2s;
  font-family: cursive;

  &::placeholder {
    color: #999;
  }

  &:focus {
    border-bottom-color: #007bff;
  }
`;

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

const ProductGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-top: 20px;
`;

const ProductItem = styled(Link)`
  display: block;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  color: #333;
  transition: transform 0.2s;

  &:hover {
    transform: translateY(-5px);
  }
`;

const ProductImage = styled.img`
  max-width: 100%;
  height: auto;
  border-radius: 10px;
`;



function Products() {
  const [products, setProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const perPage = 20;
  const [noResultsMessage, setNoResultsMessage] = useState('');
  const username = Cookies.get('firstName');
  const token = Cookies.get('token');

  useEffect(() => {
    async function fetchProductCount() {
      try {
        const response = await axios.get(`${base_url}/products/count`);
        const totalCount = response.data;
        const calculatedTotalPages = Math.ceil(totalCount / perPage);
        setTotalPages(calculatedTotalPages);
      } catch (error) {
        console.log('Error fetching product count:', error);
      }
    }
    fetchProductCount();
  }, []);

  useEffect(() => {
    async function fetchProducts() {
      try {
        const response = await axios.get(`${base_url}/products`, {
          params: {
            page: currentPage,
            perPage: perPage,
            q: searchTerm,
          },
        });
        if (response.data.length === 0) {
          setNoResultsMessage('No products found.');
        } else {
          setNoResultsMessage('');
        }
        setProducts(response.data);
      } catch (error) {
        console.log('Error fetching products:', error);
      }
    }
    fetchProducts();
  }, [currentPage, searchTerm]);

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    setCurrentPage(1);
  };



  return (
    <div>
      <CustomNavbar expand="lg" className="navbar-dark bg-dark">

          <LogoLink to="/" style={{ marginLeft: '10px', backgroundImage: `url(data:image/png;base64,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)` }} />
        <Navbar.Collapse id="navbarSupportedContent">
          <Nav className="ml-auto">
            <WelcomeText>
              Welcome, {username ? username : "Guest"}
            </WelcomeText>
            <div className="nav-item">
              <div className="d-flex">
                <SearchInput
                  className="form-control mr-sm-2"
                  type="search"
                  placeholder="Search"
                  aria-label="Search"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <button className="btn btn-outline-success my-2 my-sm-0" type="button" onClick={handleSearch}>
                  Search
                </button>
              </div>
            </div>
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
      <PageContainer>
        <div>
          <p>This is grid</p>
        </div>
        <div>
          <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
            {noResultsMessage && <p>{noResultsMessage}</p>}
          </div>
          <ProductGrid>
            {products.map((product) => (
              <Col key={product.id}>
                <ProductItem to={`/products/${product.id}`}>
                  <ProductImage src={product.imageURL} alt={product.name} />
                  <h3>{product.name}</h3>
                  <p>Price: ${product.price}</p>
                </ProductItem>
              </Col>
            ))}
          </ProductGrid>
          <PaginationContainer>
            <PaginationButton
              onClick={handlePrevPage}
              disabled={currentPage === 1}
            >
              Previous
            </PaginationButton>
            <PaginationButton
              onClick={handleNextPage}
              disabled={currentPage === totalPages}
            >
              Next
            </PaginationButton>
          </PaginationContainer>
        </div>
      </PageContainer>
    </div>
  );
}

export default Products;