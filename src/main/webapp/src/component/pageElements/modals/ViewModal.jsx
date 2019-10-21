import React, {Fragment} from 'react';

import Portal from '../Portal';
import Button from '../Button';

import '../tags.css';
import '../modal.css';

export default class ViewModal extends React.Component {
    constructor(props) {
          super(props);
          this.state = {
          certificate: props.certificate,
          isOpen: false,
          };
    }

  openModal = () => {
    this.setState({ isOpen: true });
  };

  closeModal = () => {
    this.setState({ isOpen: false });
  };

  render() {
    const { isOpen, certificate } = this.state;
    return (
      <div>
        <Button className="btn btn-primary"  onClick={this.openModal}>View </Button>
        {isOpen &&
        <Portal>
          <div className="modalOverlay">
            <div className="modalWindow">
              <div className="modalHeader">
                <div className="modalTitle">Certificate with ID = {certificate.id} </div>
              </div>
              <div className="modalBody">
                    <div class="row">
                        <div class="col-sm-4">Title </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.name}
                        </div>
                        <div class="col-sm-4">Description </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.description}
                        </div>
                        <div class="col-sm-4">Duration </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.duration}
                        </div>
                        <div class="col-sm-4">Price </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.price}
                        </div>
                        <div class="col-sm-4">Tags </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.tags.map(tag => tag.name + ' ')}
                        </div>
                        <div class="col-sm-4">Create date </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.createDate}
                        </div>
                        <div class="col-sm-4">Last update date </div>
                        <div class="col-sm-8 borderElement9sm">
                        {certificate.lastUpdateDate}
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button className="btn btn-secondary" style={cancelStyle} onClick={this.closeModal} invert>&nbsp;Cancel&nbsp;</Button>
              </div>
            </div>
          </div>
        </Portal>
        }
      </div>
    );
  }
}

const cancelStyle = {
  color:'black',
  background:'#e1e1e5',
  border: '1px solid #b3b3b5'
}
