import React, { useState, useEffect } from 'react';
import { Trash, Heart } from 'lucide-react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { toast } from 'react-toastify';
import base_url from '../baseUrl/BaseUrl';
import { Header2 } from '../header2/header2';
import { Footer } from '../footer/Footer';
import { useNavigate } from 'react-router-dom';

export function Cart() {
    const [cartItems, setCartItems] = useState([]);
    const [totalAmount, setTotalAmount] = useState(0);
    const [userDetails, setUserDetails] = useState(null);
    const token = Cookies.get('token');
    const username = Cookies.get('firstName');
    const navigate = useNavigate();

    useEffect(() => {
        fetchUserCart();
    }, []);

    const fetchUserCart = async () => {
        try {
            if (!token) {
                alert("Login First!")
                throw new Error('You need to log in first.');
            }

            const response = await axios.get(`${base_url}/carts/cartItems`, {
                headers: {
                    Authorization: token,
                },
            });
            console.log(response);
            setUserDetails(response.data.userDetails);
            setCartItems(response.data.cartItems);
            setTotalAmount(response.data.totalAmount);
        } catch (error) {
            toast.error(error.message);
        }
    };

    const handleRemoveFromCart = async (itemId) => {
        try {
            if (!token) {
                throw new Error('You need to log in first.');
            }

            await axios.delete(`${base_url}/carts/cartItems/${itemId}`, {
                headers: {
                    Authorization: token,
                },
            });
            fetchUserCart();
            toast.success('Product removed from cart.');
        } catch (error) {
            toast.error(error.message);
        }
    };
    const handleBackToShop = () => {
        navigate('/products');
    };

    const handlePlaceOrder = async () => {
        try {
            const token = Cookies.get('token');
            if (!token) {
                throw new Error('You need to log in first.');
            }
            const response = await axios.post(
                `${base_url}/orders`,
                {
                    cartItems: cartItems.map((item) => ({
                        productId: item.productId,
                        quantity: item.quantity,
                    })),
                },
                {
                    headers: {
                        Authorization: token,
                    },
                }
            );

            if (response.status === 201) {
                setCartItems([]);
                setTotalAmount(0);
                toast.success('Order placed successfully!');
                navigate('/orders');
            } else {
                throw new Error('Failed to place order.');
            }
        } catch (error) {
            console.log('Error placing order:', error);
        }
    };

    return (
        <div>
            <Header2 username={username} token={token} />
            <div className="mx-auto flex max-w-3xl flex-col space-y-4 p-6 px-2 sm:p-10 sm:px-2">
                <h2 className="text-3xl font-bold">Your cart</h2>
                {userDetails && (
                    <div className="flex items-center space-x-4">
                        <img
                            className="h-12 w-12 rounded-full object-cover"
                            src={userDetails.profileImage}
                            alt={userDetails.name}
                        />
                        <span className="text-lg font-semibold">{userDetails.name}</span>
                    </div>
                )}
                <p className="mt-3 text-sm font-medium text-gray-700">
                    We are waiting for your Checkout! Hurry Up! Get your fastest delivery order today...
                </p>
                <ul className="flex flex-col divide-y divide-gray-200">
                    {cartItems.map((cartItem) => (
                        <li key={cartItem.id} className="flex flex-col py-6 sm:flex-row sm:justify-between">
                            <div className="flex w-full space-x-2 sm:space-x-4">
                                <img
                                    className="h-20 w-20 flex-shrink-0 rounded object-contain outline-none dark:border-transparent sm:h-32 sm:w-32"
                                    src={cartItem.imageURL}
                                    alt={cartItem.productName}
                                />
                                <div className="flex w-full flex-col justify-between pb-4">
                                    <div className="flex w-full justify-between space-x-2 pb-2">
                                        <div className="space-y-1">
                                            <h3 className="text-lg font-semibold leading-snug sm:pr-8">{cartItem.productName}</h3>
                                            <p className="text-sm">Quantity: {cartItem.quantity}</p>
                                        </div>
                                        <div className="text-right">
                                            <p className="text-lg font-semibold">{cartItem.amount}</p>
                                        </div>
                                    </div>
                                    <div className="flex divide-x text-sm">
                                        <button
                                            type="button"
                                            onClick={() => handleRemoveFromCart(cartItem.id)}
                                            className="flex items-center space-x-2 px-2 py-1 pl-0"
                                        >
                                            <Trash size={16} />
                                            <span>Remove</span>
                                        </button>
                                        <button type="button" className="flex items-center space-x-2 px-2 py-1">
                                            <Heart size={16} />
                                            <span>Add to favorites</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
                <div className="space-y-1 text-right">
                    <p>
                        Total amount:
                        <span className="font-semibold"> ₹{totalAmount}</span>
                    </p>
                </div>
                <div className="flex justify-end space-x-4">
                    <button
                        type="button"
                        onClick={handleBackToShop}
                        className="rounded-md border border-black px-3 py-2 text-sm font-semibold text-black shadow-sm focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-black"
                    >
                        Back to shop
                    </button>
                    <button
                        type="button"
                        onClick={handlePlaceOrder}
                        className="rounded-md border border-black px-3 py-2 text-sm font-semibold text-black shadow-sm focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-black"
                    >
                        Checkout
                    </button>
                </div>
            </div><Footer /></div>

    );
}
