import { certificateConstants } from '../constants/CertificateConstants';

const initialState = {
  errorChecker: false,
  array: []
}

export function certificates(state = initialState, action) { 
  switch (action.type) {
    case certificateConstants.CERTIFICATE_SUCCESS:     
      return Object.assign({}, state, {array: action.giftCertificates});
    case certificateConstants.CERTIFICATE_ERROR:
      return Object.assign({}, state, {array: [...state.array],
      errorChecker: true});
    case certificateConstants.CLOSE_FORM:
      return Object.assign({}, state, {array: [...state.array],
            errorChecker: false});
    default:
      return state
  }
}