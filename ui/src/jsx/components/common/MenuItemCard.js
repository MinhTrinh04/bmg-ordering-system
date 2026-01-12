import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const MenuItemCard = ({
    id,
    image,
    title,
    description,
    price,
    category,
    rating,
    onAddToCart
}) => {
    return (
        <div className="card menu-item-card h-100">
            <div className="card-body">
                <div className="menu-item-image mb-3">
                    <img
                        src={image}
                        alt={title}
                        className="img-fluid rounded"
                        style={{ width: '100%', height: '200px', objectFit: 'cover' }}
                    />
                </div>

                <div className="menu-item-info">
                    {category && (
                        <span className="badge badge-primary mb-2">{category}</span>
                    )}

                    <h5 className="menu-item-title mb-2">
                        <Link to={`#`} className="text-dark">{title}</Link>
                    </h5>

                    {description && (
                        <p className="menu-item-description text-muted mb-3">
                            {description}
                        </p>
                    )}

                    <div className="d-flex align-items-center justify-content-between mb-3">
                        <h4 className="text-primary mb-0">${price.toFixed(2)}</h4>

                        {rating && (
                            <div className="d-flex align-items-center">
                                <svg width="16" height="15" viewBox="0 0 16 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M8 0.500031L9.79611 6.02789H15.6085L10.9062 9.4443L12.7023 14.9722L8 11.5558L3.29772 14.9722L5.09383 9.4443L0.391548 6.02789H6.20389L8 0.500031Z" fill="#FC8019" />
                                </svg>
                                <span className="ms-1 text-muted">{rating}</span>
                            </div>
                        )}
                    </div>

                    <button
                        className="btn btn-primary btn-block w-100"
                        onClick={() => onAddToCart({ id, title, price, image })}
                    >
                        <i className="fa-solid fa-cart-plus me-2"></i>
                        Add to Cart
                    </button>
                </div>
            </div>
        </div>
    );
};

MenuItemCard.propTypes = {
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    image: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    description: PropTypes.string,
    price: PropTypes.number.isRequired,
    category: PropTypes.string,
    rating: PropTypes.number,
    onAddToCart: PropTypes.func.isRequired,
};

MenuItemCard.defaultProps = {
    description: '',
    category: '',
    rating: null,
};

export default MenuItemCard;
