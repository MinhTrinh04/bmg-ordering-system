import React from 'react';


import swal from "sweetalert";

const CheckoutPage = () => {
    return (
        <>
            <div className="row">
                <div className="col-xl-12">
                    <div className="card">
                        <div className="card-body">
                            <div className="row">
                                <div className="col-xl-8">
                                    <h4 className="mb-4">Order Summary</h4>

                                    <div className="table-info mb-4">
                                        <div className="d-flex align-items-center justify-content-between p-3 bg-light rounded">
                                            <div>
                                                <h5 className="mb-1">Table Number</h5>
                                                <h3 className="text-primary mb-0">Table #12</h3>
                                            </div>
                                            <svg width="60" height="60" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z" fill="var(--primary)" opacity="0.3" />
                                            </svg>
                                        </div>
                                    </div>

                                    <div className="special-requests mb-4">
                                        <h5 className="mb-3">Special Requests (Optional)</h5>
                                        <textarea
                                            className="form-control"
                                            rows="4"
                                            placeholder="Any special instructions for your order? (e.g., no onions, extra spicy, etc.)"
                                        ></textarea>
                                    </div>

                                    <div className="payment-note p-3 bg-light rounded">
                                        <h6 className="mb-2"><i className="fa-solid fa-circle-info me-2"></i>Payment Information</h6>
                                        <p className="mb-0">You can pay at the counter when picking up your order or when the waiter brings it to your table.</p>
                                    </div>
                                </div>
                                <div className="col-xl-4">
                                    <div className="card">
                                        <div className="card-header py-3">
                                            <h4 className="mb-0">Your Cart</h4>
                                        </div>
                                        <div className="card-body pt-3">
                                            <div className="checkout-right">
                                                <div className="bill-details mt-4 border-bottom">
                                                    <h6>Bill Details</h6>
                                                    <div className="d-flex align-items-center justify-content-between mb-3">
                                                        <span>item Total</span>
                                                        <span><i className="fa-solid fa-dollar-sign">585</i></span>
                                                    </div>
                                                    <div className="d-flex align-items-center justify-content-between mb-3">
                                                        <span>Service Charge</span>
                                                        <span><i className="fa-solid fa-dollar-sign">10</i></span>
                                                    </div>
                                                </div>
                                                <div className="d-flex align-items-center justify-content-between mt-3 border-bottom">
                                                    <span className="mb-3">Tax (10%) <i className="fa-solid fa-circle-exclamation"></i></span>
                                                    <span className="text-primary mb-3"><i className="fa-solid fa-dollar-sign"></i>59.50</span>
                                                </div>
                                                <div className="d-flex align-items-center justify-content-between my-3">
                                                    <h5 className="mb-3">Total</h5>
                                                    <h4 className="text-primary mb-3"><i className="fa-solid fa-dollar-sign"></i>654.50</h4>
                                                </div>
                                                <button className="btn btn-primary btn-block" id="b3"
                                                    onClick={() => swal("Order Placed!", "Your order has been sent to the kitchen", "success")}
                                                >
                                                    PLACE ORDER
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </>
    )
}
export default CheckoutPage;