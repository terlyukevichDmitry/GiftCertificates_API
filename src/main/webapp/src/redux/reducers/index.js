import { combineReducers } from 'redux';

import { authentication } from './AuthenticationReducer';
import { certificates } from './CertificatesReducer';

const rootReducer = combineReducers({
  authentication,
  certificates
});

export default rootReducer;