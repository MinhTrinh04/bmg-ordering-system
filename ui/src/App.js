import { Suspense } from 'react';

/// Components
import Index from "./jsx";
import { useLocation, useNavigate, useParams } from 'react-router-dom';
/// Style
import "./vendor/bootstrap-select/dist/css/bootstrap-select.min.css";
import "./css/style.css";


function withRouter(Component) {
  function ComponentWithRouterProp(props) {
    let location = useLocation();
    let navigate = useNavigate();
    let params = useParams();

    return (
      <Component
        {...props}
        router={{ location, navigate, params }}
      />
    );
  }

  return ComponentWithRouterProp;
}

function App() {
  // No authentication required - direct access to menu
  return (
    <>
      <Suspense fallback={
        <div id="preloader">
          <div className="sk-three-bounce">
            <div className="sk-child sk-bounce1"></div>
            <div className="sk-child sk-bounce2"></div>
            <div className="sk-child sk-bounce3"></div>
          </div>
        </div>
      }
      >
        <Index />
      </Suspense>
    </>
  );
};

export default withRouter(App);

