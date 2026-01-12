import React from 'react';
import { Link } from 'react-router-dom';


import review1 from './../../../images/popular-img/review-img/pic-1.jpg';
import review2 from './../../../images/popular-img/pic-1.jpg';
import review3 from './../../../images/popular-img/review-img/pic-2.jpg';
import review4 from './../../../images/popular-img/pic-2.jpg';
import review5 from './../../../images/popular-img/review-img/pic-3.jpg';
import review6 from './../../../images/popular-img/pic-3.jpg';
import review7 from './../../../images/popular-img/review-img/pic-4.jpg';
import review8 from './../../../images/popular-img/pic-4.jpg';

const OrderCard = [
    { orderno: '1', image1: review1, image2: review2, total: '11.18', title1: 'Pepperoni Pizza', title2: 'Fish Burger', btnstatus: 'Completed', btnTheme: 'success bgl-success' },
    { orderno: '2', image1: review3, image2: review4, total: '11.18', title1: 'Japan Ramen', title2: 'Beef Burger', btnstatus: 'Preparing', btnTheme: 'info bgl-info' },
    { orderno: '3', image1: review5, image2: review6, total: '11.18', title1: 'Fried Rice', title2: 'Cheese Burger', btnstatus: 'Ready', btnTheme: 'primary bgl-primary' },
    { orderno: '4', image1: review7, image2: review8, total: '11.18', title1: 'Vegan Pizza', title2: 'Double Burger', btnstatus: 'Completed', btnTheme: 'success bgl-success' },
    { orderno: '5', image1: review1, image2: review2, total: '11.18', title1: 'Japan Ramen', title2: 'Veg Burger', btnstatus: 'Preparing', btnTheme: 'info bgl-info' },
    { orderno: '6', image1: review3, image2: review6, total: '11.18', title1: 'Fried Rice', title2: 'Panner Burger', btnstatus: 'Ready', btnTheme: 'primary bgl-primary' }

];



const FoodOrder = () => {
    return (
        <>
            <div className="row">
                <div className="col-xl-12">
                    <div className="row">
                        {OrderCard.map((item, index) => (
                            <div className="col-xl-3 col-sm-6 sp15" key={index}>
                                <div className="card h-auto b-hover">
                                    <div className="card-body px-3">
                                        <div className="text-center">
                                            <h4>Order #{item.orderno}</h4>
                                            <p>Nov 11, 2022 , 18:38 PM</p>
                                        </div>
                                        <hr />
                                        <div>
                                            <h4>Fast Food Resto</h4>
                                            <div className="d-flex align-items-center">
                                                <svg width="16" height="15" viewBox="0 0 16 15" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                    <path d="M8 0.500031L9.79611 6.02789H15.6085L10.9062 9.4443L12.7023 14.9722L8 11.5558L3.29772 14.9722L5.09383 9.4443L0.391548 6.02789H6.20389L8 0.500031Z" fill="#FC8019" />
                                                </svg>
                                                <p className="mb-0 px-2">5.0</p>
                                                <svg className="me-2" width="4" height="5" viewBox="0 0 4 5" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                    <circle cx="2" cy="2.50003" r="2" fill="#C4C4C4" />
                                                </svg>
                                                <p className="mb-0">1k+ Reviews</p>
                                            </div>
                                            <hr />
                                        </div>
                                        <div className="order-menu">
                                            <h6 className="font-w600">Order Menu</h6>
                                            <div className="d-flex align-items-center mb-2">
                                                <img className="me-2" src={item.image1} alt="" />
                                                <div className="order-items">
                                                    <h6 className="font-w500 text-nowrap mb-0"><Link to={"#"}>{item.title1}</Link></h6>
                                                    <p className="mb-0">x1</p>
                                                </div>
                                                <h6 className="text-primary mb-0 ms-auto">+$5.59</h6>
                                            </div>
                                            <div className="d-flex align-items-center">
                                                <img className="me-2" src={item.image2} alt="" />
                                                <div className="order-items">
                                                    <h6 className="font-w500 text-nowrap mb-0"><Link to={"#"}>{item.title2}</Link></h6>
                                                    <p className="mb-0">x1</p>
                                                </div>
                                                <h6 className="text-primary mb-0 ms-auto">+$5.59</h6>
                                            </div>
                                            <hr />
                                            <div className="d-flex align-items-center justify-content-between mb-4">
                                                <h4 className="mb-0">Total</h4>
                                                <h4 className="mb-0 text-primary">${item.total}</h4>
                                            </div>
                                            <Link to={"#"} className={`btn btn-block btn-outline-${item.btnTheme}`}>{item.btnstatus}</Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </>
    )
}
export default FoodOrder;