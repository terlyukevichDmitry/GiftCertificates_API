import { userConstants } from './constants/user-constants';
import AuthService from '../service/AuthService';
import CertificatesService from '../service/CertificatesService'
import { alertActions } from './constants/alert-actions';
import { history } from './constants/history';

export const userActions = {
    login,
    logout,
    getAll
};

function login(loginRequest) {
    return dispatch => {
        dispatch(request(loginRequest.login));
        AuthService.login(loginRequest)
            .then(
                data => { 
                    dispatch(success(data));
                    history.push('/certificates');
                },
                error => {
                    dispatch(failure(error));
                    dispatch(alertActions.error(error));
                }
            );
    };

    function request(user) { return { type: userConstants.LOGIN_REQUEST, user } }
    function success(user) { return { type: userConstants.LOGIN_SUCCESS, user } }
    function failure(error) { return { type: userConstants.LOGIN_FAILURE, error } }
}

function logout() {
    AuthService.logout();
    return { type: userConstants.LOGOUT };
}

function getAll(page) {
    return dispatch => {
        dispatch(request());
        CertificatesService.retrieveCertificates(page)
        .then(
            giftCertificates => dispatch(success(giftCertificates.data)));
    };

    function request() { return { type: userConstants.GETALL_REQUEST } }
    function success(giftCertificates) { return { type: userConstants.GETALL_SUCCESS, giftCertificates } }
    // function failure(error) { return { type: userConstants.GETALL_FAILURE, error } }
}