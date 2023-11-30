import React, { useState, useEffect } from 'react';
import { ArrowRight } from 'lucide-react';
import axios from 'axios';
import base_url from '../baseUrl/BaseUrl';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { useNavigate, Link } from 'react-router-dom';
import { Header } from '../header/Header';
import { Footer } from '../footer/Footer';

export const SignUp = () => {
    useEffect(() => {
        document.title = "Sign Up";
    },);
    const navigate = useNavigate();

    const [signUpData, setSignUpData] = useState({
        firstName: '',
        lastName: '',
        phone: '',
        email: '',
        password: '',
        address: ''
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSignUpData({ ...signUpData, [name]: value });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(`${base_url}/customers/signup`, signUpData);

            console.log(response);
            toast.success('Sign up successful!');
            navigate('/login');
        } catch (error) {
            console.error(error);
            toast.error('Unable to sign up. Please check your information and try again.');
        }
    };

    return (
        <div>
            <Header />
            <section>
                <div className="flex items-center justify-center px-4 py-10 sm:px-6 sm:py-16 lg:px-8 lg:py-24">
                    <div className="xl:mx-auto xl:w-full xl:max-w-sm 2xl:max-w-md">
                        <h2 className="text-center text-2xl font-bold leading-tight text-black">
                            Sign up to create an account
                        </h2>
                        <p className="mt-2 text-center text-base text-gray-600">
                            Already have an account?{' '}
                            <Link to="/login" className="font-medium text-black transition-all duration-200 hover:underline">
                                Sign In
                            </Link>
                        </p>
                        <form onSubmit={handleFormSubmit} className="mt-8">
                            <div className="space-y-5">
                                <div>
                                    <label htmlFor="firstName" className="text-base font-medium text-gray-900">
                                        {' '}
                                        First Name{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="text"
                                            id="firstName"
                                            name="firstName"
                                            value={signUpData.firstName}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="First Name"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <label htmlFor="lastName" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Last Name{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="text"
                                            id="lastName"
                                            name="lastName"
                                            value={signUpData.lastName}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="Last Name"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <label htmlFor="phone" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Phone{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="text"
                                            id="phone"
                                            name="phone"
                                            value={signUpData.phone}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="Contact Number"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <label htmlFor="address" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Address{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="text"
                                            id="address"
                                            name="address"
                                            value={signUpData.address}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="Address"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <label htmlFor="email" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Email address{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="email"
                                            id="email"
                                            name="email"
                                            value={signUpData.email}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="Email"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <label htmlFor="password" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Password{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            type="password"
                                            id="password"
                                            name="password"
                                            value={signUpData.password}
                                            onChange={handleInputChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            placeholder="Password"
                                        />
                                    </div>
                                </div>
                                <div>
                                    <button
                                        type="submit"
                                        className="inline-flex w-full items-center justify-center rounded-md bg-black px-3.5 py-2.5 font-semibold leading-7 text-white hover:bg-black/80"
                                    >
                                        Create Account <ArrowRight className="ml-2" size={16} />
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <ToastContainer />
            </section>
            <Footer />
        </div>

    );
};
