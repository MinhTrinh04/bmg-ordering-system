import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './BottomNav.css';

const BottomNav = ({ cartItemCount = 0 }) => {
    const location = useLocation();

    const isActive = (path) => {
        return location.pathname === path ? 'active' : '';
    };

    return (
        <nav className="bottom-nav">
            <Link to="/" className={`nav-item ${isActive('/')}`}>
                <i className="fa-solid fa-home"></i>
                <span>Home</span>
            </Link>

            <Link to="/favorite-menu" className={`nav-item ${isActive('/favorite-menu')}`}>
                <i className="fa-solid fa-utensils"></i>
                <span>Menu</span>
            </Link>

            <Link to="/checkout" className={`nav-item ${isActive('/checkout')}`}>
                <div className="icon-wrapper">
                    <i className="fa-solid fa-cart-shopping"></i>
                    {cartItemCount > 0 && (
                        <span className="badge">{cartItemCount}</span>
                    )}
                </div>
                <span>Cart</span>
            </Link>

            <Link to="/food-order" className={`nav-item ${isActive('/food-order')}`}>
                <i className="fa-solid fa-receipt"></i>
                <span>Orders</span>
            </Link>
        </nav>
    );
};

export default BottomNav;
