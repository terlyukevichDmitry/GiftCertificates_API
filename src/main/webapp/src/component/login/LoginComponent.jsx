import React from 'react';

import 'bootstrap/dist/css/bootstrap.min.css';
import './login.css';

import { connect } from 'react-redux';
import { LoginSchema } from '../validation/Schemas'
import { userActions } from '../../redux/actions/LoginActions';
import { Navbar, Footer } from '../pageElements/LoginHelper'
import { Formik, Form, Field, ErrorMessage } from 'formik';

class LoginComponent extends React.Component {
    constructor(props){
        super(props);
        this.login = this.login.bind(this);
        this.validateUsername = this.validateUsername.bind(this);
    }

    componentDidMount() {        
        localStorage.clear();
    }

    login = (event) => {
        event.preventDefault();
        let loginRequest = {login: event.target.elements.username.value, password: event.target.elements.password.value};
        const { dispatch } = this.props;
        dispatch(userActions.login(loginRequest));
    };

     validateUsername(value) {
        let error;
        if (value === 'admin') {
            error = 'Nice try!';
        }
        return error;
    }

    render() {
        return(
            <div>
            <Navbar/>
            <div className="container">
            <div class="row">
                <div class="col"></div>
                <div class="col-6">
                    <div className="rounded font-weight-bold border border-light Login">
                        <div className="loginWord">Login</div>
                        <Formik initialValues={{ username: "", password: "" }} validationSchema={LoginSchema}>
                         {({ touched, errors }) => (
                                <Form name="form" onSubmit={this.login}>
                                    <div className="form-group">
                                        <Field type="text" name="username" placeholder="Username" validate={this.validateUsername} className={`form-control ${touched.username && errors.username ? "is-invalid" : ""}`}/>
                                        <ErrorMessage component="div" name="username" className="invalid-feedback"/>
                                    </div>
                                    <div className="form-group">
                                        <Field type="password" name="password"  placeholder="Password" className={`form-control ${(touched.password && errors.password) ? "is-invalid" : ""}`}/>
                                        <ErrorMessage component="div" name="password" className="invalid-feedback"/>
                                    </div>
                                    <div className="clientErrors">{this.props.error}</div>
                                    <div className="centerPlace">
                                        <button type="submit" className="btn btn-primary customLoginButton">Login</button>
                                    </div>
                                </Form>)}
                        </Formik>
                        </div>
                     </div>
                <div class="col"></div>
            </div>
            </div>
                <Footer />
            </div>
        )
    }
}

function mapStateToProps(state) {
    const { error } = state.authentication;
    return {
        error
    };
}
const connectedLoginPage = connect(mapStateToProps)(LoginComponent);
export {connectedLoginPage as LoginComponent}; 