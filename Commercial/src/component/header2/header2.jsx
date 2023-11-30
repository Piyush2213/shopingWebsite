import React from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';

export function Header2({ username, token }) {
  const handleLogout = () => {
    Cookies.remove('token');
    Cookies.remove('firstName');
    window.location.href = '/login';
  };

  return (
    <section className="relative overflow-hidden bg-white py-8">
      <div className="container relative z-10 mx-auto px-4">
        <div className="-m-8 flex flex-wrap items-center justify-between">
          <div className="w-auto p-8">
            <Link to="/products">
              <div className="inline-flex items-center">
                <img
                  src="https://svgsilh.com/svg_v2/189064.svg"
                  alt="DevUI Logo"
                  width="80"
                  height="46"
                />
                {username && (
                  <span className="font-medium text-gray-600 ml-12">{`Hello, ${username}!`}</span>
                )}
              </div>
            </Link>
          </div>
          <div className="w-auto p-8">
            <ul className="-m-5 flex flex-wrap items-center">
              <li className="p-5">
                <Link to="/products" className="font-medium text-gray-600 hover:text-gray-700">
                  Visit
                </Link>
              </li>

              {token ? (
                <>
                  <li className="p-5">
                    <Link to="/orders" className="font-medium text-gray-600 hover:text-gray-700">
                      Orders
                    </Link>
                  </li>
                  <li className="p-5">
                    <Link to="/cart" className="font-medium text-gray-600 hover:text-gray-700">
                      Cart
                    </Link>
                  </li>
                  <li className="p-5">
                    <button
                      className="font-medium text-gray-600 hover:text-gray-700"
                      onClick={handleLogout}
                    >
                      Logout
                    </button>
                  </li>
                </>
              ) : (
                <li className="p-5">
                  <Link to="/login" className="font-medium text-gray-600 hover:text-gray-700">
                    Login
                  </Link>
                </li>
              )}
            </ul>
          </div>
        </div>
      </div>
    </section>
  );
}
