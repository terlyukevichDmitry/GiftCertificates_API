import { userConstants } from '../constants/UserConstants';
import AuthService from '../../actions/service/AuthService';
import { history } from '../helpers/BrowserHistory';
import { parseJwt } from '../../actions/helper/ActionHelper'


export const userActions = {
    login,
    logout
};

function login(loginRequest) {
    return dispatch => {
        AuthService.login(loginRequest)
            .then(
                data => { 
                    dispatch(success(data));
                    let user = parseJwt();
                    if (user.role === 'ADMIN') {
                        history.push('/certificates');
                    } else {
                        dispatch(failure(data));
                    }
                }
            )
            .catch(error => {
                dispatch(failure(error.response));
            });;
    };

    function success(user) { return { type: userConstants.LOGIN_SUCCESS, user } }
    function failure(error) { return { type: userConstants.LOGIN_FAILURE, error } }
}

function logout() {
    return dispatch => {
        AuthService.logout()
    };
}
