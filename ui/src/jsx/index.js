import React, { useContext } from "react";

/// React router dom
import { Routes, Route, Outlet } from "react-router-dom";

/// Css
import "./index.css";
import "./chart.css";
import "./step.css";

/// Layout
import Nav from "./layouts/nav";
import Footer from "./layouts/Footer";
import { BottomNav } from "./components/common";

import ScrollToTop from "./layouts/ScrollToTop";

/// Dashboard - Essential Pages Only
import Home from "./components/Dashboard/Home";
import FoodOrder from "./components/Dashboard/FoodOrder";
import FavoriteMenu from "./components/Dashboard/FavoriteMenu";
import OrderHistory from "./components/Dashboard/OrderHistory";
import CheckoutPage from "./components/Dashboard/CheckoutPage";

import { ThemeContext } from "../context/ThemeContext";

const Markup = () => {

  // Simplified routes - only essential pages for QR ordering
  const allroutes = [
    /// Core ordering routes
    { url: "", component: <Home /> },
    { url: "dashboard", component: <Home /> },
    { url: "food-order", component: <FoodOrder /> },
    { url: "favorite-menu", component: <FavoriteMenu /> },
    { url: "order-history", component: <OrderHistory /> },
    { url: "checkout", component: <CheckoutPage /> },
  ];


  return (
    <>
      <Routes>
        <Route element={<MainLayout />} >
          {allroutes.map((data, i) => (
            <Route
              key={i}
              exact
              path={`${data.url}`}
              element={data.component}
            />
          ))}
        </Route>
      </Routes>
      <ScrollToTop />

    </>
  );
};


function MainLayout() {
  const { menuToggle } = useContext(ThemeContext);
  return (
    <div id="main-wrapper" className={`show ${menuToggle ? "menu-toggle" : ""}`}>
      <Nav />
      <div className="content-body" style={{ minHeight: window.screen.height - 45 }}>
        <div className="container">
          <Outlet />
        </div>
      </div>
      <Footer />
      <BottomNav cartItemCount={0} />
    </div>
  )

};

export default Markup;
