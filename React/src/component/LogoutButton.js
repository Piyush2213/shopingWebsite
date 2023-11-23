import React from 'react';
import { Link } from 'react-router-dom';
import Cookies from 'js-cookie';

const LogoutButton = () => {
  const token = Cookies.get('token');

  if (token) {
    const handleLogout = () => {
      Cookies.remove('token');
      Cookies.remove('firstName');
      window.location.href = '/login';
    };

    return (
      <Link to="/login" style={{ marginLeft: '10px' }}>
        <span
          role="button"
          aria-label="Logout"
          onClick={handleLogout}
          style={{
            display: 'inline-block',
            width: '30px',
            height: '30px',
            backgroundImage: `url("https://cdn-icons-png.flaticon.com/128/10309/10309341.png")`,
            backgroundSize: 'cover',
            cursor: 'pointer',
          }}
        />
      </Link>
    );
  }

  return null;
};

export default LogoutButton;
