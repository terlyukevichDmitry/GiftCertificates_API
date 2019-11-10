import React from 'react';

import { CreateCertificateModal } from './modals/CreateCertificateModal'
import { userActions } from '../../redux/actions/LoginActions';
import { parseJwt } from '../../actions/helper/ActionHelper'
import { connect } from 'react-redux';

import './navbar.css';

class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
    }
    this.logout = this.logout.bind(this);
  }

  logout() {
    const { dispatch } = this.props;
    dispatch(userActions.logout());
  }

  componentDidMount() {
    let user = parseJwt();
    this.setState({name: user.username})
  }

  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light navbar-custom">
      <nav className="navbar-brand" style={{color: '#acacac'}}>Admin UI </nav>
      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item active">
            <CreateCertificateModal />
          </li>
        </ul>
        <form className="form-inline" onClick={this.logout}>
          {this.state.name}
          <input class="btn btn-outline-light" type="submit" style={{margin: '0px 10px', border: '#424242'}} value="Log out "/>
        </form>
      </div>
      </nav>
    )
  }
}

const navbar = connect()(Navbar);
export {navbar as Navbar}; 
