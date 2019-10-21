import React from 'react';
import PropTypes from 'prop-types';

import Portal from './Portal';
import Button from './Button';

import CertificatesService from '../service/CertificatesService'

import { FormGroup, FormControl } from "react-bootstrap";

import './modal.css';

import {Tags} from './Tags'

const Modal = ({isOpen, onCancel}) => {
  return (
    <>
      { isOpen &&
        <Portal>
          <div className="modalOverlay">
            <div className="modalWindow">
              <div className="modalHeader">
                <div className="modalTitle">Add new certificate</div>
              </div>
              <form>
              <div className="modalBody">
                    <div class="row">
                        <div class="col-sm-3">Title </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" name="title"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Description </div>
                        <div class="col-sm-9">
                            <FormGroup controlId="description" bsSize="large">
                                <textarea class="form-control" name = "description" id="description" rows="3"></textarea>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Duration </div>
                        <div class="col-sm-9">
                            <FormGroup controlId="duration" bsSize="large">
                                <FormControl type="text"  name="duration"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Price </div>
                        <div class="col-sm-9">
                            <FormGroup controlId="price" bsSize="large">
                                <FormControl type="text"  name="price"/>
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Tags </div>
                        <div class="col-sm-9">
                            <Tags tags={null}/>
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button type="submit" style={saveStyle}>&nbsp;&nbsp;&nbsp;Save&nbsp;&nbsp;&nbsp;</Button>
                  <Button className="btn btn-secondary" style={cancelStyle} onClick={onCancel} invert>&nbsp;Cancel&nbsp;</Button>
              </div>
              </form>
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

Modal.propTypes = {
  title: PropTypes.string,
  isOpen: PropTypes.bool,
  onCancel: PropTypes.func,
  onSubmit: PropTypes.func,
  children: PropTypes.node,
};
Modal.defaultProps = {
  title: 'Modal title',
  isOpen: false,
  onCancel: () => {},
  onSubmit: () => {},
  children: null,
};
export default Modal;
