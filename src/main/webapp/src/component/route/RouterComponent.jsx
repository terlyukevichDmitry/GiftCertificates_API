import React from 'react';
import { Router, Route } from 'react-router-dom'
import { Redirect } from 'react-router'

import CertificatesComponent from '../certificates/CertificatesComponent';
import {LoginComponent} from '../login/LoginComponent'
import {PrivateRoute} from './PrivateRoute'
import './router.css';

import { connect } from 'react-redux';

import { history } from '../action/constants/history';
import { alertActions } from '../action/constants/alert-actions';

class RouterComponent extends React.Component {
    constructor(props) {
        super(props);

        const { dispatch } = this.props;
        history.listen((location, action) => {
            dispatch(alertActions.clear());
        });
    }

    render() {
        return (
            <Router history={history}>
                    <Route exact path="/" render={() => <Redirect to="/login" />} />
                    <PrivateRoute exact path="/certificates" component={CertificatesComponent} /> 
                    <Route path="/login" component={LoginComponent}/>                      
            </Router>
        );
    }
}

function mapStateToProps(state) {
    const { alert } = state;
    return {
        alert
    };
}

const routerComponent = connect(mapStateToProps)(RouterComponent);
export { routerComponent as RouterComponent }; 