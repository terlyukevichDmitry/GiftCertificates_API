import React from 'react';

import { Route, Redirect } from 'react-router-dom';
import { parseJwt } from '../../actions/helper/ActionHelper'

export const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        let user = parseJwt();
        if (!localStorage.getItem('token') || user.role !== 'ADMIN') {
            return <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
        }
        return <Component {...props} />
    }} />
)
