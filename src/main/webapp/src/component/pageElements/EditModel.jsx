
import React from 'react';
import PropTypes from 'prop-types';

import Portal from './Portal';
import Button from './Button';

import { FormGroup, FormControl } from "react-bootstrap";

import './modal.css';

import {Tags} from './Tags'

const EditModal = ({
  isOpen, onCancel, onSubmit, certificate
}) => {
  return (
    <>
      { isOpen &&
        <Portal>
          <div className="modalOverlay">
            <div className="modalWindow">
              <div className="modalHeader">
                <div className="modalTitle">Edit certificate with ID = {certificate.id} </div>
              </div>
              <div className="modalBody">
                    <div class="row">
                        <div class="col-sm-3">Title </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" defaultValue={certificate.name} name="title"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Description </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <textarea class="form-control" defaultValue={certificate.description}  id="description" rows="3"></textarea>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Duration </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" defaultValue={certificate.duration} name="duration"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Price </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" defaultValue={certificate.price} name="price"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Tags </div>
                        <div class="col-sm-9">
                            <Tags tags={certificate.tags}/>
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button style={saveStyle} onClick={onSubmit}>&nbsp;&nbsp;&nbsp;Edit&nbsp;&nbsp;&nbsp;</Button>
                  <Button className="btn btn-secondary" style={cancelStyle} onClick={onCancel} invert>&nbsp;Cancel&nbsp;</Button>
              </div>
            </div>
          </div>
        </Portal>
      }
    </>
  );
};

const saveStyle = {
  border: '1px solid black'
}

const cancelStyle = {
  color:'black',
  background:'#e1e1e5',
  border: '1px solid #b3b3b5'
}

EditModal.propTypes = {
    certificate: PropTypes.node,
    isOpen: PropTypes.bool,
    onCancel: PropTypes.func,
    children: PropTypes.node,
};
EditModal.defaultProps = {
  certificate: null,
  isOpen: false,
  onCancel: () => {},
  onSubmit: () => {},
};
export default EditModal;
