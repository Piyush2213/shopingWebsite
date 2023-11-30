import React, { useState, useEffect } from 'react';
import { ArrowRight } from 'lucide-react';
import { Header } from '../header/Header';
import { Footer } from '../footer/Footer';
import axios from 'axios'; 
import base_url from '../baseUrl/BaseUrl';
import { useNavigate } from 'react-router-dom'; 
import { toast } from 'react-toastify'; 
import Cookies from 'js-cookie';

 

export function Login() {
    useEffect(() => {
        document.title = "Login In";
    }, []);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(`${base_url}/customers/login`, { email, password });
            const data = response.data;

            if (data.token) {
                toast.success('Login successful!');
                Cookies.set('token', data.token);
                Cookies.set('firstName', data.firstName);
                navigate('/products');
            } else {
                toast.error('Invalid email or password. Please try again.');
            }
        } catch (error) {
            alert("Invalid email or password.")
            console.log('Error:', error);
            toast.error('An error occurred. Please try again later.');
        }
    };

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    return (
        <div>
            <Header />

            <section>
                <div className="flex items-center justify-center px-4 py-10 sm:px-6 sm:py-16 lg:px-8 lg:py-24">
                    <div className="xl:mx-auto xl:w-full xl:max-w-sm 2xl:max-w-md">

                        <h2 className="text-center text-2xl font-bold leading-tight text-black">
                            Sign in to your account
                        </h2>
                        <p className="mt-2 text-center text-sm text-gray-600 ">
                            Don&apos;t have an account?{' '}
                            <a
                                href="/signup"
                                title=""
                                className="font-semibold text-black transition-all duration-200 hover:underline"
                            >
                                Create a free account
                            </a>
                        </p>
                        <form onSubmit={handleFormSubmit} className="mt-8">
                            <div className="space-y-5">
                                <div>
                                    <label htmlFor="email" className="text-base font-medium text-gray-900">
                                        {' '}
                                        Email address{' '}
                                    </label>
                                    <div className="mt-2">
                                        <input
                                            id="email"
                                            name="email"
                                            value={email}
                                            onChange={handleEmailChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            type="email"
                                            placeholder="Email"
                                        ></input>
                                    </div>
                                </div>
                                <div>
                                    <div className="flex items-center justify-between">
                                        <label htmlFor="password" className="text-base font-medium text-gray-900">
                                            {' '}
                                            Password{' '}
                                        </label>

                                    </div>
                                    <div className="mt-2">
                                        <input
                                            id="password"
                                            name="password"
                                            value={password}
                                            onChange={handlePasswordChange}
                                            className="flex h-10 w-full rounded-md border border-gray-300 bg-transparent px-3 py-2 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-1 focus:ring-gray-400 focus:ring-offset-1 disabled:cursor-not-allowed disabled:opacity-50"
                                            type="password"
                                            placeholder="Password"
                                        ></input>
                                    </div>
                                </div>
                                <div>
                                    <button
                                        type="submit"
                                        className="inline-flex w-full items-center justify-center rounded-md bg-black px-3.5 py-2.5 font-semibold leading-7 text-white hover:bg-black/80"
                                    >
                                        Log In<ArrowRight className="ml-2" size={16} />
                                    </button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </section>
            <Footer />
        </div>
    );
}
