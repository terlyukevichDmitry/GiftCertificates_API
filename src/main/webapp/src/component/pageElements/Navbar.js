import React from 'react';
import ShowModel from './ShowModel'

import AuthService from '../service/AuthService'

function parseJwt() {
  var base64Url = localStorage.getItem('token').split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var user = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));
  return JSON.parse(user);
};

export default class Navbar extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      name: '',
    }
    this.logout = this.logout.bind(this);
  }

  logout() {
    AuthService.logout();
  }

  componentDidMount() {
    let user = parseJwt();
    this.setState({name: user.username})
  }

  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light navbar-custom">
      <nav className="navbar-brand" style={style}>Admin UI </nav>
      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item active">
            <ShowModel />
          </li>
        </ul>
        <form className="form-inline" onClick={this.logout}>
          {this.state.name}
          <input class="btn btn-outline-light" type="submit"style={styleBorder} value="Log out "/>
        </form>
      </div>
      </nav>
    )
  }
}

const styleBorder = {
  margin:'0px 10px',
  border: '#424242'  
}

const style = {
color: '#acacac',
}
