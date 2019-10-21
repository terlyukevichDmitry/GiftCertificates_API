import React from 'react';
import axios from 'axios';
import Pagination from "react-js-pagination";

import {authHeader} from '../authent/authHeader'

class FetchNews extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      articlesDetails: [],
      activePage:1,
      totalPages: null,
      itemsCountPerPage:null,
      totalItemsCount:null
    };
    this.handlePageChange = this.handlePageChange.bind(this);
    this.fetchURL = this.fetchURL.bind(this);
  }

  fetchURL(page) {

    const options = {
        headers: authHeader()
    };

    axios.get(`https://localhost:9002/giftCertificates?page=${page}&limit=4`, options)
      .then( response => {

          const totalPages = 4;
          const itemsCountPerPage = response.data.length;
          const totalItemsCount = 40;


          this.setState({totalPages: totalPages})
          this.setState({totalItemsCount: totalItemsCount})
          this.setState({itemsCountPerPage: itemsCountPerPage})
          this.setState({articlesDetails: response});
        }
      );
    }

  componentDidMount () {
      this.fetchURL(this.state.activePage)
}

  handlePageChange(pageNumber) {
    console.log(`active page is ${pageNumber}`);
    this.setState({activePage: pageNumber})
    this.fetchURL(pageNumber)
    }
  render(){

    return (
      <div >
      <div className="d-flex justify-content-center">
        <Pagination
         hideNavigation
         activePage={this.state.activePage}
         itemsCountPerPage={this.state.itemsCountPerPage}
         totalItemsCount={this.state.totalItemsCount}
         pageRangeDisplayed={4}
         itemClass='page-item'
         linkClass='btn btn-light'
         onChange={this.handlePageChange}
         />
       </div>
      </div>
    );
  }
}

export default FetchNews;