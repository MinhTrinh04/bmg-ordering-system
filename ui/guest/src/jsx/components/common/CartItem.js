import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const CartItem = ({
    id,
    image,
    title,
    price,
    quantity,
    onIncrease,
    onDecrease,
    onRemove
}) => {
    const subtotal = price * quantity;

    return (
        <div className="order-check d-flex align-items-center my-3">
            <div className="dlab-media">
                <img src={image} alt={title} />
            </div>
            <div className="dlab-info flex-grow-1">
                <div className="d-flex align-items-center justify-content-between">
                    <h4 className="dlab-title mb-0">
                        <Link to={"#"}>{title}</Link>
                    </h4>
                    <h4 className="text-primary ms-2 mb-0">
                        ${subtotal.toFixed(2)}
                    </h4>
                </div>
                <div className="d-flex align-items-center justify-content-between mt-2">
                    <span className="text-muted">${price.toFixed(2)} each</span>
                    <div className="d-flex align-items-center">
                        <div className="quntity me-2">
                            <button
                                data-decrease
                                onClick={() => onDecrease(id)}
                                disabled={quantity <= 1}
                            >
                                -
                            </button>
                            <input
                                data-value
                                type="text"
                                value={quantity}
                                readOnly
                            />
                            <button
                                data-increase
                                onClick={() => onIncrease(id)}
                            >
                                +
                            </button>
                        </div>
                        <button
                            className="btn btn-sm btn-outline-danger"
                            onClick={() => onRemove(id)}
                            title="Remove item"
                        >
                            <i className="fa-solid fa-trash"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

CartItem.propTypes = {
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    image: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    quantity: PropTypes.number.isRequired,
    onIncrease: PropTypes.func.isRequired,
    onDecrease: PropTypes.func.isRequired,
    onRemove: PropTypes.func.isRequired,
};

export default CartItem;
