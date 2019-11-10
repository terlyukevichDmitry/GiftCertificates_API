import { userConstants } from '../constants/UserConstants';

let token = (localStorage.getItem('token'));
const initialState = token ? { loggedIn: true, token, error: '' } : {};

export function authentication(state = initialState, action) {
  switch (action.type) {
    case userConstants.LOGIN_SUCCESS:
      return {
        loggedIn: true,
        token: localStorage.getItem('token')
      };
    case userConstants.LOGIN_FAILURE:
      return {
        error: 'Login or Password is not found'
      };
    case userConstants.LOGOUT:
      return {};
    default:
      return state
  }
}