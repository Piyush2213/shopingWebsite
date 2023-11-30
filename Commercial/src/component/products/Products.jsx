import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';
import base_url from '../baseUrl/BaseUrl';
import Cookies from 'js-cookie';
import { Footer } from '../footer/Footer';
import { Header2 } from '../header2/header2';
import { useNavigate } from 'react-router-dom';


export function Products() {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [searchTerm, setSearchTerm] = useState('');
  const perPage = 20;
  const [noResultsMessage, setNoResultsMessage] = useState('');
  const username = Cookies.get('firstName');
  const token = Cookies.get('token');

  const navigate = useNavigate();

  const handleAddToCart = async (productId) => {
    try {
      const responseMessage = await addToCart(productId);
      toast.success(responseMessage);
      if(token){navigate('/cart');}
      
    } catch (error) {
      toast.error(error.message);
    }
  };

  const addToCart = async (productId) => {
    try {
      if (!token ) {
        navigate('/login');
        return 'Login to continue shopping';
      }
      const response = await axios.post(
        `${base_url}/carts/cartItems`,
        {
          productId: productId,
          quantity: 1,
        },
        {
          headers: {
            Authorization: `${token}`,
          },
        }
      );

      console.log('Server Response:', response);

      if (response.status >= 200 && response.status < 300) {
        return `Added 1 item to cart.`;
      } else {
        throw new Error('Failed to add product to cart.');
      }
    } catch (error) {
      console.error('Error adding product to cart:', error);
      throw new Error('Failed to add product to cart.');
    }
  };


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
    setSearchTerm(e.target.value);
  };

  return (
    <div>
      <Header2 username={username} token={token} />
      <div>
        <input
          type="text"
          placeholder="Search products..."
          value={searchTerm}
          onChange={handleSearch}
        />
      </div>
      <div className="mx-auto grid w-full max-w-7xl items-center space-y-4 px-2 py-10 md:grid-cols-2 md:gap-6 md:space-y-0 lg:grid-cols-4">
        {products.map((product) => (
          <div key={product.id}>
            <Link to={`/products/${product.id}`}>
              <div className="rounded-md border">
                <img
                  src={product.imageURL}
                  alt={product.name}
                  className="aspect-[16/9] w-full rounded-md md:aspect-auto md:h-[300px] lg:h-[200px]"
                />
                <div className="p-4">
                  <h1 className="inline-flex items-center text-lg font-semibold">{product.name}</h1>
                  <p className="mt-3 text-sm text-gray-600">Lorem ipsum dolor sit amet consectetur adipisicing elit. Excepturi, debitis?</p>
                  <div className="mt-4">
                    <span className="block text-sm font-semibold">Price : ${product.price}</span>
                  </div>
                  <div className="mt-3 flex items-center space-x-2">
                    <span className="block text-sm font-semibold">Colors : </span>

                    <span className="block h-4 w-4 rounded-full border-2 border-gray-300 bg-red-400"></span>
                    <span className="block h-4 w-4 rounded-full border-2 border-gray-300 bg-purple-400"></span>
                    <span className="block h-4 w-4 rounded-full border-2 border-gray-300 bg-orange-400"></span>
                  </div>
                </div>
              </div>
            </Link>
            <button
              type="button"
              className="mt-4 w-full rounded-sm bg-black px-2 py-1.5 text-sm font-semibold text-white shadow-sm hover:bg-black/80 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-black"
              onClick={() => handleAddToCart(product.id)}
            >
              Add to Cart
            </button>
          </div>
        ))}
      </div>
      <div className="flex justify-between mt-4">
        <button
          type="button"
          onClick={handlePrevPage}
          className="rounded-sm bg-blue-500 px-3 py-1.5 text-sm font-semibold text-white shadow-sm hover:bg-blue-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue"
        >
          Previous
        </button>
        <button
          type="button"
          onClick={handleNextPage}
          className="rounded-sm bg-blue-500 px-3 py-1.5 text-sm font-semibold text-white shadow-sm hover:bg-blue-600 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue"
        >
          Next
        </button>
      </div>
      <Footer />
    </div>
  );
}
