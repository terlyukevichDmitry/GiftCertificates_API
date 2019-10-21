import React from 'react';

import Portal from '../Portal';
import Button from '../Button'

import '../modal.css';

export default class ErrorModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          isOpen: false,
        }
    }
    openModal = () => {
        this.setState({ isOpen: true });
    };

    closeModal = () => {
        this.setState({ isOpen: false });
    };
  
    render() {
        const { isOpen} = this.state;
        return (
        <div>
            <Button className="btn btn-secondary" onClick={this.openModal}>Add new </Button>
            {isOpen &&
                <Portal>
                    <div className="modalOverlay">
                        <div className="modalWindow">
                            <form>
                                <div className="modalBody">
                                    <Button className="btn btn-secondary" style={cancelStyle} onClick={this.closeModal} invert>&nbsp;Cancel&nbsp;</Button>
                                </div>
                            </form>
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
