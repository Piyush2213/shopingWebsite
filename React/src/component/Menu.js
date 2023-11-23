import React from 'react';
import { Link } from 'react-router-dom';

function Menu() {
  return (
    <div>
      <ul className="list-group">
        <li className="list-group-item">
          <Link to="/">Home</Link>
        </li>
        <li className="list-group-item">
          <Link to="/login">Login</Link>
        </li>
        <li className="list-group-item">
          <Link to="/signup">SignUp</Link>
        </li>
      </ul>
    </div>
  );
}

export default Menu;
