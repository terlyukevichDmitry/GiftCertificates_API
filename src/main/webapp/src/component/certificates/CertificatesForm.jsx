import React from 'react';


import './certificates.css';
import '../pageElements/navbar.css'
import Table from 'react-bootstrap/Table'
import Pagination from "react-js-pagination";
import EllipsisText from "react-ellipsis-text";
import ViewModal from '../pageElements/modals/ViewModal'

import { Navbar } from '../pageElements/Navbar'
import { Button } from 'react-bootstrap';
import { connect } from 'react-redux';
import { Footer } from '../pageElements/Footer'
import { EditCertificateModal } from '../pageElements/modals/EditCertificateModal'
import { DeleteCertificateModal } from '../pageElements/modals/DeleteCertificateModal'
import { certificateActions } from '../../redux/actions/CertificateActions'
import { checkSortType } from '../../actions/helper/ActionHelper'


class CertificatesForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            message: '',
            activePage: 1,
            itemsCountPerPage: 20,
            totalItemsCount: 10000,
            sortChecker: false,
        };
        this.refreshCertificates = this.refreshCertificates.bind(this);
        this.handlePageChange = this.handlePageChange.bind(this);
        this.handleSearchChange = this.handleSearchChange.bind(this);
        this.handleSearchSubmit = this.handleSearchSubmit.bind(this);
        this.closeErrorForm = this.closeErrorForm.bind(this);
        this.handleSortSubmitByParameter = this.handleSortSubmitByParameter.bind(this);
        this.handleChangePageSize = this.handleChangePageSize.bind(this);
    }

    handleSortSubmitByParameter(event) {
        event.preventDefault();
        let sortInformation = checkSortType(this.state.sortChecker);
        this.state.sortChecker = sortInformation.sortChecker;
        const {dispatch} = this.props;
        this.state.activePage = 1;
        dispatch(certificateActions.sortCertificatesByParameters(event.target.elements.sortField.value, sortInformation.type, this.state.activePage, this.state.itemsCountPerPage));
    }

    componentDidMount() {
        let page;
        if (localStorage.getItem('page') != null && localStorage.getItem('limit') != null) {
            page = localStorage.getItem('page');
            this.state.itemsCountPerPage = localStorage.getItem('limit');            
        } else {
            page = this.state.activePage;           
        }
        this.refreshCertificates(page, this.state.itemsCountPerPage, this.props.query);
    }

    refreshCertificates(page, limit, query) {
        const { dispatch } = this.props;
        dispatch(certificateActions.findAllCertificates(page, limit, query));
        localStorage.setItem('page', page);
        localStorage.setItem('limit', limit);
    }

    handlePageChange(pageNumber) {
        this.setState({activePage: pageNumber});
        this.refreshCertificates(pageNumber, this.state.itemsCountPerPage, this.props.query);
    }

    handleSearchChange(event) {
        this.setState({message: event.target.value});
    }

    handleSearchSubmit(event) {
        event.preventDefault();
        const {dispatch} = this.props;
        dispatch(certificateActions.findCertificatesByParameters(this.state.message, 1, this.state.itemsCountPerPage));
    }

    closeErrorForm(event) {
        event.preventDefault();
        const {dispatch} = this.props;
        dispatch(certificateActions.closeErrorForm());
    }

    handleChangePageSize(event) {
        event.preventDefault();
        this.state.itemsCountPerPage = event.target.elements.pageSize.value;
        this.refreshCertificates(this.state.activePage, this.state.itemsCountPerPage, this.props.query);
    }
    
    render() {
        return (
            <div>
                <Navbar/>
                <div className="errorForm" hidden={!this.props.certificates.errorChecker}>
                    <div className="container">
                        <div class="row">
                            <div class="col">
                            </div> 
                            <div class="col-12">
                            <div className="customError">
                                <div class="row justify-content-between">
                                    <div class="col-4">
                                        &nbsp;<img src="images/cross.jpg"/>&nbsp;&nbsp;&nbsp;Error message
                                    </div>
                                    <div class="col-4">
                                        <div className="crossPlace">
                                            <form onSubmit={this.closeErrorForm}>
                                                <button className="imageButton" type="submit"></button>
                                            </form>
                                        </div>
                                     </div>
                                </div>
                            </div>
                            </div>
                            <div class="col">
                            </div>
                        </div>
                    </div>
                </div>
                <div className="search">
                    <div className="container">
                        <div class="row">
                            <div class="col">
                            </div>
                            <div class="col-12">
                                    <form className="input-group md-form form-sm form-1 pl-0" onSubmit={this.handleSearchSubmit}>
                                        <input className="form-control my-0 py-1" type="text" placeholder="Search..." value={this.state.message} onChange={this.handleSearchChange} />
                                        <Button class="btn btn-primary" type="submit" style={{padding: '5px 80px 5px 80px'}}>Go!</Button>
                                    </form>
                            </div>
                            <div class="col">
                            </div>
                        </div>
                    </div>
                </div>
            <div className="container">
            <div class="row">
                <div class="col"></div>
                <div class="col-12">
                    <Table striped bordered hover size="sm">
                        <thead className="thead">
                            <tr>
                                <th>
                                    <form className="sortDatetime" onSubmit={this.handleSortSubmitByParameter}>
                                        <input type="hidden" name="sortField" value="createDate"/>
                                        <button className="imageSortButton" type="submit"></button>Datetime
                                    </form>
                                </th>
                                <th>
                                    <form className="sortTitle" onSubmit={this.handleSortSubmitByParameter}>
                                        <input type="hidden" name="sortField" value="name"/>
                                        <button className="imageSortButton" type="submit"></button>Title
                                    </form>
                                </th>
                                <th>Tags</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody className="tbody"> 
                            {
                                this.props.certificates.array.map(
                                    giftCertificate =>
                                        <tr key={giftCertificate.id}>
                                            <td>{giftCertificate.createDate}</td>
                                            <td>{giftCertificate.name}</td>
                                            <td>{giftCertificate.tags.slice(0, 2).map(tag => tag.name + ' ')}...</td>
                                            <td><EllipsisText text={giftCertificate.description}length={"30"} /></td>
                                            <td>{giftCertificate.price}</td>
                                            <td class="form-inline">
                                                <ViewModal certificate={giftCertificate}/>
                                                <EditCertificateModal certificate={giftCertificate}/>
                                                <DeleteCertificateModal certificateId={giftCertificate.id}/>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </Table>
                 </div>
                <div className="col"></div>
            </div>
            </div>
            <div>
                <div className="paginationBlock">
                <div className="container">
                    <div className="row">
                        <div className="col">
                        </div>
                        <div className="col-5">
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
                    <div className="col">
                        <div className="dropup">
                            <button className="btn btn-light dropdown-toggle">{this.state.itemsCountPerPage}</button>
                            <div className="dropup-content">
                                <form onSubmit={this.handleChangePageSize}>
                                    <input type="hidden" name="pageSize" value="10"/>
                                    <button className="btn btn-light btn-sm" type="submit" style={{padding: '5px 20px 5px 20px'}}>10</button>
                                </form>
                                <form onSubmit={this.handleChangePageSize}>
                                    <input type="hidden" name="pageSize" value="20"/>
                                    <button className="btn btn-light btn-sm" type="submit" style={{padding: '5px 20px 5px 20px'}}>20</button>
                                </form>
                                <form onSubmit={this.handleChangePageSize}>
                                    <input type="hidden" name="pageSize" value="50"/>
                                    <button className="btn btn-light btn-sm" type="submit" style={{padding: '5px 20px 5px 20px'}}>50</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>  
                </div>
            </div>
            <Footer />
            </div>
        )
    }
}

function mapStateToProps(state) {    
    const { certificates } = state;
    return { certificates };
}
const certificatesForm = connect(mapStateToProps)(CertificatesForm);
export {certificatesForm as CertificatesForm}; 

