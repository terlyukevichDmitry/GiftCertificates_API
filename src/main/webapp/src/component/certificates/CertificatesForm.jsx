import React, { Component } from 'react';
import Table from 'react-bootstrap/Table'
import EllipsisText from "react-ellipsis-text";

import CertificatesService from '../service/CertificatesService';
import ShowDeleteModal from '../pageElements/ShowDeleteModal'
import ShowEditModel from '../pageElements/ShowEditModel'
import ViewModal from '../pageElements/modals/ViewModal'
import Pagination from "react-js-pagination";

import './certificates.css';

class CertificatesForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            giftCertificates: [],
            message: null,
            articlesDetails: [],
            activePage: 1,
            totalPages: null,
            itemsCountPerPage: null,
            totalItemsCount: null,
        };
        this.refreshCertificates = this.refreshCertificates.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
    }

    // sortDataByParameter() {
    //     let sortType = 'asc';
    //     if (this.state.checker) {
    //         this.setState({checker: false});
    //     } else {
    //         sortType = 'desc';
    //         this.setState({checker: true});
    //     }
    //    CertificatesService.sortDataByParameter('createDate', sortType).then(response => {
    //        this.setState({giftCertificates: response.data});
    //    });
        
    // }

    componentDidMount() {
        let page;
        if (localStorage.getItem('page') != null) {
            page = localStorage.getItem('page');
        } else {
            page = this.state.activePage;
        }
        this.refreshCertificates(page);
    }

    refreshCertificates(page) {
        CertificatesService.retrieveCertificates(page)
            .then(
                response => {
                    const totalPages = 4;
                    const itemsCountPerPage = response.data.length;
                    const totalItemsCount = 1000;

                    this.setState({totalPages: totalPages})
                    this.setState({totalItemsCount: totalItemsCount})
                    this.setState({itemsCountPerPage: itemsCountPerPage})
                    this.setState({articlesDetails: response});
                    this.setState({ giftCertificates: response.data })
                }
            );
            localStorage.setItem('page', page);
        }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({activePage: pageNumber});
        this.refreshCertificates(pageNumber);
    }
    
    render() {
        return (
            <div className="container">
            <div class="row">
                <div class="col"></div>
                <div class="col-12">
                    <Table striped bordered hover size="sm">
                        <thead className="thead">
                            <tr>
                                <th><img src="images/triangle.jpg" name="createDate" onClick={this.sortDataByParameter}/>&nbsp;&nbsp;Datetime</th>
                                <th>&nbsp;&nbsp;Title</th>
                                <th>&nbsp;&nbsp;Tags</th>
                                <th>&nbsp;&nbsp;Description</th>
                                <th>&nbsp;&nbsp;Price</th>
                                <th>&nbsp;&nbsp;Actions</th>
                            </tr>
                        </thead>
                        <tbody className="tbody"> 
                            {
                                this.state.giftCertificates.map(
                                    giftCertificate =>
                                        <tr key={giftCertificate.id}>
                                            <td>{giftCertificate.createDate}</td>
                                            <td>{giftCertificate.name}</td>
                                            <td>{giftCertificate.tags.map(tag => tag.name + ' ')}</td>
                                            <td><EllipsisText text={giftCertificate.description}length={"30"} /></td>
                                            <td>{giftCertificate.price}</td>
                                            <td class="form-inline">
                                                <ViewModal certificate={giftCertificate}/>
                                                <ShowEditModel certificate={giftCertificate}/>
                                                <ShowDeleteModal certificateId={giftCertificate.id}/>
                                            </td>
                                        </tr>
                                    )
                            }
                        </tbody>
                    </Table>
                 </div>
                <div class="col"></div>
            </div>
            <div className="paginationBlock">
                <div className="d-flex justify-content-center">
                    <Pagination
                        hideNavigation
                        activePage={this.state.activePage}
                        itemsCountPerPage={this.state.itemsCountPerPage}
                        totalItemsCount={this.state.totalItemsCount}
                        pageRangeDisplayed={9}
                        itemClass='page-item'
                        linkClass='btn btn-light'
                        onChange={this.handlePageChange}
                        />
                </div>
            </div>
            </div>
        )
    }
}

export default CertificatesForm;
