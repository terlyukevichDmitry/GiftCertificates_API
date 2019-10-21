import React, { Component } from 'react';

import CertificatesForm from './CertificatesForm';
import './certificates.css';
import Navbar from '../pageElements/Navbar'
import '../pageElements/navbar.css'

class CertificatesComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchParameters: '',
            errors: {
                searchParameters: ''
              }
        };
    }

    sub = (event) => {
        event.preventDefault();
        console.log(this.state.searchParameters);
    }

    handleChange = (event) => {
        event.preventDefault();
        const { name, value } = event.target;
        let errors = this.state.errors;
        switch (name) {
          case 'search': {
            errors.searchParameters = 
              value.length <= 3 || value.length >= 30
                ? 'Please, write smaller!'
                : '';
            break;
          }
            default:
                break;
        }
        this.setState({errors, [name]: value});
    }

    render() {
        return (
                <>
                    <Navbar/>
                    <div className="search">
                        <div className="container">
                            <div class="row">
                                <div class="col"></div>
                                <div class="col-12">
                                        <form className="input-group md-form form-sm form-1 pl-0">
                                            <input name="search" className="form-control my-0 py-1" type="text" onChange={this.handleChange}  placeholder="Search" aria-label="Search" />
                                            <input class="btn btn-primary" type="submit" onClick={this.sub} value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            Go!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"/>
                                        </form>
                                </div>
                                <div class="col"></div>
                            </div>
                        </div>
                    </div>
                    <CertificatesForm/>
                </>
        )
    }
}
export default CertificatesComponent;
