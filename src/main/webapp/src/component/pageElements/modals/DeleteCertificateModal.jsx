import React, {Fragment} from 'react';

import Portal from '../Portal';
import Button from '../Button';

import { certificateActions } from '../../../redux/actions/CertificateActions';
import { connect } from 'react-redux';

import './modal.css';

class DeleteCertificateModal extends React.Component {
      constructor(props) {
          super(props);
          this.state = {
            certificateId: props.certificateId,
            isOpen: false
          };
    }

  openModal = () => {
    this.setState({ isOpen: true });
  };

  closeModal = () => {
    this.setState({ isOpen: false });
  };

  deleteCertificate = (event) => {
    event.preventDefault();
    const { dispatch } = this.props;
    dispatch(certificateActions.deleteCertificate(this.state.certificateId));
  }

  render() {
    const { isOpen, certificateId } = this.state;
    return (
      <div>
        <Button className="btn btn-danger" onClick={this.openModal}>Delete </Button>
        {isOpen &&
          <Portal>
            <div className="modalOverlay">
              <div className="modalWindow">
                <div className="modalHeader">
                  <div className="modalTitle">Delete confirmation</div>
                </div>
                <div className="modalBodyDelete">
                  Do you really want to delete certificates with id = {certificateId}?
                </div>
                <div className="modalFooter">
                  <Button class="btn btn-danger" style={{padding: '6px 15px 6px 15px', border: '1px solid black'}} onClick={this.deleteCertificate}>Delete</Button>
                  <Button className="btn btn-secondary" style={{padding: '6px 15px 6px 15px', color:'black',background:'#e1e1e5',border: '1px solid #b3b3b5'}} onClick={this.closeModal}>Cancel</Button>
                </div>
              </div>
            </div>
          </Portal>
        }
      </div>
    );
  }
}

const deleteCertificateModal = connect()(DeleteCertificateModal);
export {deleteCertificateModal as DeleteCertificateModal}; 
