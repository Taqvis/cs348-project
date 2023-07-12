import { createStore } from 'redux';

// Define the initial state
const initialState = {
  username: '', // Initially empty
};

// Define a reducer function to handle state updates
const reducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_USERNAME':
      return {
        ...state,
        username: action.payload,
      };
    default:
      return state;
  }
};

// Create the Redux store
const store = createStore(reducer);

export default store;
