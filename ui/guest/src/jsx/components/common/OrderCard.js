import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const OrderCard = ({
    orderId,
    orderNumber,
    orderDate,
    restaurantName,
    rating,
    items,
    total,
    status,
    tableNumber,
    onViewDetails
}) => {
    // Status theme mapping
    const statusThemes = {
        'Completed': 'success bgl-success',
        'Preparing': 'info bgl-info',
        'Ready': 'primary bgl-primary',
        'Received': 'warning bgl-warning',
        'Cancelled': 'danger bgl-danger'
    };

    const btnTheme = statusThemes[status] || 'secondary bgl-secondary';

    return (
        <div className="card h-auto b-hover">
            <div className="card-body px-3">
                <div className="text-center">
                    <h4>Order #{orderNumber}</h4>
                    <p className="text-muted mb-0">{orderDate}</p>
                    {tableNumber && (
                        <p className="text-primary mb-0">
                            <i className="fa-solid fa-table me-1"></i>
                            Table {tableNumber}
                        </p>
                    )}
                </div>
                <hr />

                <div>
                    <h4>{restaurantName}</h4>
                    {rating && (
                        <div className="d-flex align-items-center">
                            <svg width="16" height="15" viewBox="0 0 16 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M8 0.500031L9.79611 6.02789H15.6085L10.9062 9.4443L12.7023 14.9722L8 11.5558L3.29772 14.9722L5.09383 9.4443L0.391548 6.02789H6.20389L8 0.500031Z" fill="#FC8019" />
                            </svg>
                            <p className="mb-0 px-2">{rating}</p>
                            <svg className="me-2" width="4" height="5" viewBox="0 0 4 5" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <circle cx="2" cy="2.50003" r="2" fill="#C4C4C4" />
                            </svg>
                            <p className="mb-0">1k+ Reviews</p>
                        </div>
                    )}
                    <hr />
                </div>

                <div className="order-menu">
                    <h6 className="font-w600">Order Items</h6>
                    {items && items.map((item, index) => (
                        <div className="d-flex align-items-center mb-2" key={index}>
                            <img className="me-2" src={item.image} alt={item.title} />
                            <div className="order-items flex-grow-1">
                                <h6 className="font-w500 text-nowrap mb-0">
                                    <Link to={"#"}>{item.title}</Link>
                                </h6>
                                <p className="mb-0">x{item.quantity || 1}</p>
                            </div>
                            <h6 className="text-primary mb-0 ms-auto">
                                ${item.price.toFixed(2)}
                            </h6>
                        </div>
                    ))}
                    <hr />

                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <h4 className="mb-0">Total</h4>
                        <h4 className="mb-0 text-primary">${total.toFixed(2)}</h4>
                    </div>

                    <Link
                        to={"#"}
                        className={`btn btn-block btn-outline-${btnTheme}`}
                        onClick={(e) => {
                            if (onViewDetails) {
                                e.preventDefault();
                                onViewDetails(orderId);
                            }
                        }}
                    >
                        {status}
                    </Link>
                </div>
            </div>
        </div>
    );
};

OrderCard.propTypes = {
    orderId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    orderNumber: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    orderDate: PropTypes.string.isRequired,
    restaurantName: PropTypes.string.isRequired,
    rating: PropTypes.number,
    items: PropTypes.arrayOf(PropTypes.shape({
        image: PropTypes.string.isRequired,
        title: PropTypes.string.isRequired,
        quantity: PropTypes.number,
        price: PropTypes.number.isRequired,
    })).isRequired,
    total: PropTypes.number.isRequired,
    status: PropTypes.oneOf(['Completed', 'Preparing', 'Ready', 'Received', 'Cancelled']).isRequired,
    tableNumber: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    onViewDetails: PropTypes.func,
};

OrderCard.defaultProps = {
    rating: null,
    tableNumber: null,
    onViewDetails: null,
};

export default OrderCard;
