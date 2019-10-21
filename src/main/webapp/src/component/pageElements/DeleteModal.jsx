import React from 'react';
import PropTypes from 'prop-types';

import Portal from './Portal';
import Button from './Button';
import CertificatesService from '../service/CertificatesService'

import './modal.css';

const DeleteModal = ({isOpen, onCancel, certificateId}) => {
  return (
    <>
      { isOpen &&
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
                  <Button class="btn btn-danger" style={deleteStyle} onClick={() => deleteCertificate(certificateId)}>&nbsp;Delete&nbsp;</Button>
                  <Button className="btn btn-secondary" style={cancelStyle} onClick={onCancel} invert>&nbsp;Cancel&nbsp;</Button>
              </div>
            </div>
          </div>
        </Portal>
      }
    </>
  );
};

function deleteCertificate(certificateId){
  CertificatesService.deleteCertificate(certificateId)
  .then(
    response => {
      window.location.reload();
    }
  );
}

const deleteStyle = {
  border: '1px solid black'
}

const cancelStyle = {
  color:'black',
  background:'#e1e1e5',
  border: '1px solid #b3b3b5'
}

DeleteModal.propTypes = {
  certificateId: PropTypes.string,
  isOpen: PropTypes.bool,
  onCancel: PropTypes.func,
};
DeleteModal.defaultProps = {
  certificateId: '0',
  isOpen: false,
  onCancel: () => {},
  deleteCertificate: () => {},
};
export default DeleteModal;
