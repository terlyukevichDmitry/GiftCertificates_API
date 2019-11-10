import {certificateConstants } from '../constants/CertificateConstants';
import CertificatesService from '../../actions/service/CertificatesService'
import { history } from '../../redux/helpers/BrowserHistory'
import { findCertificates } from '../../actions/helper/ActionHelper'
import { userActions } from './LoginActions'

export const certificateActions = {
    createCertificate,
    updateCertificate,
    deleteCertificate,
    findAllCertificates,
    findCertificatesByParameters,
    closeErrorForm,
    sortCertificatesByParameters
};

function createCertificate(certificate) {
    return dispatch => {
        CertificatesService.createCertificate(certificate)
        .then(response => {
            if (response.status === 201) {
                window.location.reload();
            } else if (response.status === 403) {
                dispatch(userActions.logout());
            } else {
                dispatch(getError());
            }
        });
    };
}

function updateCertificate(certificate) {
    return dispatch => {
        CertificatesService.updateCertificate(certificate)
        .then(response => {
            if (response.status === 200) {
                window.location.reload();
            } else if (response.status === 403) {
                dispatch(userActions.logout());
            } else {
                dispatch(getError());
            }
        });
    };
}

function deleteCertificate(certificateId) {
    return dispatch => {
        CertificatesService.deleteCertificate(certificateId)
        .then(response => {
            if (response.status === 200) {
                window.location.reload();
            } else if (response.status === 403) {
                dispatch(userActions.logout());
            } else {
                dispatch(getError());
            }
        });
    };
}

function findCertificatesByParameters(message, page, limit) {
    let searchParameters = findCertificates(message);
    return dispatch => {
        CertificatesService.findCertificatesByParameters(page, limit, searchParameters)
        .then(
            response => {
                if (response.status === 403) {
                    dispatch(userActions.logout());
                } else {
                    dispatch(success(response.data));
                    history.push('/certificates?' + searchParameters + `page=${page}&limit=${limit}`);
                }
            });
    };
}

function findAllCertificates(page, limit, query) {
    if (query === '') {
        query = `?page=${page}&limit=${limit}`;
    } else {
        query = query.split('page')[0] + `page=${page}&limit=${limit}`;
    }
    return dispatch => {
        CertificatesService.retrieveCertificates(query)
        .then(
            response => {
                if (response.status === 403) {
                    dispatch(userActions.logout());
                } else {
                    dispatch(success(response.data));
                    history.push('/certificates' + query);
                }
            });
                
    };
}

function sortCertificatesByParameters(field, type, page, limit) {
    let query = `?orderby=${field},${type}&page=${page}&limit=${limit}`;
    return dispatch => {
        CertificatesService.sortCertificatesByParameters(query)
        .then(
            response => {
                if (response.status === 403) {
                    dispatch(userActions.logout());
                } else {
                    dispatch(success(response.data));
                    history.push('/certificates' + query);
                }
            });
    };
}

function closeErrorForm() {
    return dispatch => {
        dispatch(closeForm());
    }
}
function getError() { return { type: certificateConstants.CERTIFICATE_ERROR } }
function closeForm() { return { type: certificateConstants.CLOSE_FORM } }
function success(giftCertificates) { return { type: certificateConstants.CERTIFICATE_SUCCESS, giftCertificates } }
