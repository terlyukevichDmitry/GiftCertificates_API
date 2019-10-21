import React, { Component } from 'react';
import {MDBCol} from "mdbreact";
import './search.css';
import 'bootstrap/dist/css/bootstrap.min.css';

class Search extends Component {
    render() {
      return (
            <div className="search">
                <MDBCol md="6">
                      <div className="input-group md-form form-sm form-1 pl-0">
                          <span className="input-group-text purple lighten-3" id="basic-text1">
                              <input className="form-control" type="text" placeholder="Search..." aria-label="Search" />
                          </span>
                              <input class="btn btn-primary" type="submit" value="Go!   "/>
                      </div>
                </MDBCol>
            </div>
      );
    }
  }
  
  export default Search;