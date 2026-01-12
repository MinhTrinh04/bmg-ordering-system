import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import thunk from 'redux-thunk';

const middleware = applyMiddleware(thunk);

const composeEnhancers =
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

// Temporary empty reducer until new reducers are implemented
const placeholderReducer = (state = {}, action) => state;

const reducers = combineReducers({
    placeholder: placeholderReducer,
    // New reducers will be added here:
    // menu: menuReducer,
    // cart: cartReducer,
    // order: orderReducer,
    // table: tableReducer,
});

export const store = createStore(reducers, composeEnhancers(middleware));
