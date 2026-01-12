import React, { useContext, useEffect, useReducer } from 'react';
import { Link } from 'react-router-dom';


//Import Components
import { ThemeContext } from "../../../context/ThemeContext";
import BannerSlider from './Dashboard/BannerSlider';
import CategorySlider from './Dashboard/CategorySlider';
import PopularDishesSlider from './Dashboard/PopularDishesSlider';
import RecentOrderSlider from './Dashboard/RecentOrderSlider';

import Pic1 from './../../../images/popular-img/review-img/pic-1.jpg';
import Pic2 from './../../../images/popular-img/review-img/pic-2.jpg';
import Pic3 from './../../../images/popular-img/review-img/pic-3.jpg';


const orderBlog = [
	{ id: 1, image: Pic1, number: 1 },
	{ id: 2, image: Pic2, number: 1 },
	{ id: 3, image: Pic3, number: 1 },
	{ id: 4, image: Pic1, number: 1 },
];

const reducer = (previousState, updatedState) => ({
	...previousState,
	...updatedState,
});

const Home = () => {
	const { changeBackground } = useContext(ThemeContext);
	useEffect(() => {
		changeBackground({ value: "light", label: "Light" });
	}, []);

	const [state, setState] = useReducer(reducer, { orderBlog: orderBlog });
	const handleCountAdd = (e) => {
		let temp = state.orderBlog.map((data) => {
			if (e === data.id) {
				return { ...data, number: data.number + 1 };
			}
			return data;
		});
		setState({ orderBlog: temp });
	}
	const handleCountMinus = (e) => {
		let temp = state.orderBlog.map((data) => {
			if (e === data.id) {
				return { ...data, number: data.number > 0 ? data.number - 1 : data.number };
			}
			return data;
		});
		setState({ orderBlog: temp });
	}

	return (
		<>
			<div className="row">
				<div className="col-xl-8 col-xxl-7">
					<div className="row">
						<div className="col-xl-12">
							<BannerSlider />
						</div>

						<div className="col-xl-12">
							<div className="d-flex align-items-center justify-content-between mb-2 gap">
								<h4 className=" mb-0 cate-title">Category</h4>
								<Link to="/favorite-menu" className="text-primary">View all <i className="fa-solid fa-angle-right ms-2"></i></Link>
							</div>
							<CategorySlider />
						</div>
						<div className="col-xl-12">
							<div className="d-flex align-items-center justify-content-between mb-2">
								<h4 className=" mb-0 cate-title">Popular Dishes</h4>
								<Link to="/favorite-menu" className="text-primary">View all <i className="fa-solid fa-angle-right ms-2"></i></Link>
							</div>
							<PopularDishesSlider />
						</div>
						<div className="col-xl-12">
							<div className="d-flex align-items-center justify-content-between mb-2">
								<h4 className=" mb-0 cate-title">Recent Order</h4>
								<Link to="/favorite-menu" className="text-primary">View all <i className="fa-solid fa-angle-right ms-2"></i></Link>
							</div>
							<RecentOrderSlider />
						</div>
					</div>

				</div>
				<div className="col-xl-4 col-xxl-5">
					<div className="row">
						<div className="col-xl-12">
							<div className="card dlab-bg dlab-position">
								<div className="card-header border-0 pb-0">
									<h4 className="cate-title">Your Cart</h4>
								</div>
								<div className="card-body pt-0 pb-2">
									{state.orderBlog.map((item, index) => (
										<div className="order-check d-flex align-items-center my-3" key={index}>
											<div className="dlab-media">
												<img src={item.image} alt="" />
											</div>
											<div className="dlab-info">
												<div className="d-flex align-items-center justify-content-between">
													<h4 className="dlab-title"><Link to={"#"}>Pepperoni Pizza</Link></h4>
													<h4 className="text-primary ms-2">+$5.59</h4>
												</div>
												<div className="d-flex align-items-center justify-content-between">
													<span>x1</span>
													<div className="quntity">
														<button data-decrease onClick={() => handleCountMinus(item.id)}>-</button>
														<input data-value type="text" value={item.number} />
														<button data-increase onClick={() => handleCountAdd(item.id)}>+</button>
													</div>
												</div>
											</div>
										</div>
									))}

									<hr className="my-2 text-primary" style={{ opacity: "0.9" }} />
								</div>
								<div className="card-footer  pt-0 border-0">
									<div className="d-flex align-items-center justify-content-between">
										<p>Service</p>
										<h4 className="font-w500">+$1.00</h4>
									</div>
									<div className="d-flex align-items-center justify-content-between mb-3">
										<h4 className="font-w500">Total</h4>
										<h3 className="font-w500 text-primary">$202.00</h3>
									</div>
									<Link to="/checkout" className="btn btn-primary btn-block">Checkout</Link>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</>
	)
}
export default Home;