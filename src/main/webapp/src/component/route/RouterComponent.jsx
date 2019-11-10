import React from 'react';
import { Router, Route } from 'react-router-dom'
import { Redirect } from 'react-router'

import { CertificatesForm } from '../certificates/CertificatesForm';
import { history } from '../../redux/helpers/BrowserHistory';
import { LoginComponent } from '../login/LoginComponent'
import { PrivateRoute } from './PrivateRoute'
import { connect } from 'react-redux';

import './router.css';


class RouterComponent extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router history={history}>
                    <Route exact path="/" render={() => <Redirect to="/login" />} />
                    <PrivateRoute path="/certificates"
                        component={({ location, history }) => {
                            return <CertificatesForm query={location.search} history={history}/>
                        }}
                    /> 
                    <Route path="/login" component={LoginComponent}/>                      
            </Router>
        );
    }
}

const routerComponent = connect(null)(RouterComponent);
export { routerComponent as RouterComponent }; 