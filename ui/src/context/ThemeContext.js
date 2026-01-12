import React, { createContext, useEffect, useReducer } from "react";

export const ThemeContext = createContext();

const reducer = (previousState, updatedState) => ({
  ...previousState,
  ...updatedState,
});

// Simplified initial state - only essentials for QR ordering
const initialState = {
  background: { value: "light", label: "Light" },
  menuToggle: false,
  windowWidth: 0,
  windowHeight: 0,
};

const ThemeContextProvider = (props) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { background, menuToggle, windowWidth, windowHeight } = state;

  const body = document.querySelector("body");

  // Simple light/dark mode toggle
  const changeBackground = (name) => {
    body.setAttribute("data-theme-version", name.value);
    dispatch({ background: name });
  };

  const openMenuToggle = () => {
    dispatch({ menuToggle: !menuToggle });
  };

  useEffect(() => {
    const body = document.querySelector("body");

    // Set default attributes for QR ordering UI
    body.setAttribute("data-typography", "poppins");
    body.setAttribute("data-theme-version", "light");
    body.setAttribute("data-layout", "vertical");
    body.setAttribute("data-primary", "color_1");
    body.setAttribute("data-nav-headerbg", "color_3");
    body.setAttribute("data-headerbg", "color_3");
    body.setAttribute("data-sidebar-style", "overlay");
    body.setAttribute("data-sibebarbg", "color_1");
    body.setAttribute("data-secondary", "color_1");
    body.setAttribute("data-sidebar-position", "fixed");
    body.setAttribute("data-header-position", "fixed");
    body.setAttribute("data-container", "wide");
    body.setAttribute("direction", "ltr");

    // Handle responsive behavior
    let resizeWindow = () => {
      dispatch({ windowWidth: window.innerWidth });
      dispatch({ windowHeight: window.innerHeight });

      // Adjust sidebar for mobile
      if (window.innerWidth >= 768 && window.innerWidth < 1024) {
        body.setAttribute("data-sidebar-style", "mini");
      } else if (window.innerWidth <= 768) {
        body.setAttribute("data-sidebar-style", "overlay");
      } else {
        body.setAttribute("data-sidebar-style", "full");
      }
    };

    resizeWindow();
    window.addEventListener("resize", resizeWindow);
    return () => window.removeEventListener("resize", resizeWindow);
  }, []);

  return (
    <ThemeContext.Provider
      value={{
        body,
        windowWidth,
        windowHeight,
        menuToggle,
        openMenuToggle,
        changeBackground,
        background,
      }}
    >
      {props.children}
    </ThemeContext.Provider>
  );
};

export default ThemeContextProvider;
