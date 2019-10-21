import React, { Component, Fragment } from 'react';
import DeleteModal from './DeleteModal';
import Button from './Button';

class ShowDeleteModel extends Component {
  constructor(props) {
    super(props)
    this.state = {
      isOpen: false,
      certificateId: props.certificateId
    }
  }

  openModal = () => {
    this.setState({ isOpen: true });
  }

  handleSubmit = () => {
    this.setState({ isOpen: false });
   
  }

  handleCancel = () => {
    this.setState({ isOpen: false });
  }

  render() {
    return (
      <Fragment>
        <Button className="btn btn-danger" onClick={this.openModal}>Delete </Button>
        <DeleteModal
          certificateId={this.state.certificateId}
          isOpen={this.state.isOpen}
          onCancel={this.handleCancel}
          onSubmit={this.handleSubmit}>
        </DeleteModal>
      </Fragment>
    );
  }
}

export default ShowDeleteModel;