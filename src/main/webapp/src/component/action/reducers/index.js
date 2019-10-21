import { combineReducers } from 'redux';

import { authentication } from './auth-reducer';
import { alert } from './alert-reducer';
import { certificates } from './certificates-reducer';

const rootReducer = combineReducers({
  authentication,
  alert,
  certificates
});

export default rootReducer;