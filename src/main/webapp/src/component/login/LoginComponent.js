import React from 'react';
import { Button, FormGroup, FormControl } from "react-bootstrap";

import 'bootstrap/dist/css/bootstrap.min.css';
import './login.css';
import { connect } from 'react-redux';

import { userActions } from '../action/login-action';

class LoginComponent extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            username: '',
            password: '',
            disableForm: false,
            clientErrors:'',
            errors: {
                username: '',
                password: '' 
            }
        }
        this.login = this.login.bind(this);
    }

    componentDidMount() {
        localStorage.clear();
    }

    handleChange = (event) => {
        event.preventDefault();
        const { name, value } = event.target;
        let errors = this.state.errors;
    
        switch (name) {
          case 'username': {
            errors.username = 
              value.length <= 3 || value.length >= 30
                ? 'Username field length must not be less than 3 characters and greater than 30 characters!'
                : '';
            break;
          }
          case 'password': {
            errors.password = 
              value.length <= 4 || value.length >= 30
                ? 'Password length must not be less than 4 characters and greater than 30 characters!'
                : '';
            break;
          }
            default:
                break;
        }
        this.setState({errors, [name]: value});
        if ((this.state.username.trim() === '' || (this.state.username.length < 4 || this.state.username.length > 30))
        || (this.state.password.trim() === '' || (this.state.password.length < 4 || this.state.password.length > 30))) {
            this.state.disableForm = false;
        } else {
            this.state.disableForm = true;
        }
    }

    login = (event) => {
        event.preventDefault();
        let loginRequest = {login: this.state.username, password: this.state.password};
        const { dispatch } = this.props;
        dispatch(userActions.login(loginRequest));
    };

    render() {
        const { loggingIn } = this.props;

        const {errors, disableForm, clientErrors} = this.state;
        return(
            <div>
            <Navbar/>
            <div className="container">
            <div class="row">
                <div class="col"></div>
                <div class="col-6">
                    <div className="rounded font-weight-bold border border-light Login">
                        <div className="loginWord">Login</div>
                            <form name="form" onSubmit={this.login}>
                                <FormGroup controlId="username" bsSize="large">
                                    <FormControl type="text" placeholder="Username" name="username" value={this.state.username} onChange={this.handleChange} noValidate/>
                                    {<span className='error'>{errors.username}</span>}
                                </FormGroup>
                                <FormGroup controlId="password" bsSize="large">
                                    <FormControl type="password" placeholder="Password" name="password" value={this.state.password} onChange={this.handleChange} noValidate/>
                                    {<span className='error'>{errors.password}</span>}
                                </FormGroup>
                                <div>
                                <div className="clientErrors">{clientErrors}</div>
                                </div>
                                <div className="centerPlace">
                                    <Button class="btn btn-primary" disabled={!disableForm} type="submit">&nbsp;&nbsp;Login&nbsp;&nbsp;</Button>
                                    {loggingIn &&
                                            <img className="loadImage" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
                                    }
                                </div>
                            </form>
                         </div>
                     </div>
                <div class="col"></div>
            </div>
            </div>
            </div>
        )
    }

}

const Navbar = () => (
    <nav className="navbar navbar-expand-lg navbar-light navbar-custom">
        <nav className="navbar-brand" style={style}>Admin UI </nav>
    </nav>
);

const style = {
  color: '#acacac',
}

function mapStateToProps(state) {
    const { loggingIn } = state.authentication;
    return {
        loggingIn
    };
}
const connectedLoginPage = connect(mapStateToProps)(LoginComponent);
export {connectedLoginPage as LoginComponent}; 