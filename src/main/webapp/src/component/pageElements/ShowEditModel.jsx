import React, {Fragment} from 'react';

import Portal from './Portal';
import Button from './Button';

import CertificatesService from '../service/CertificatesService'
import { WithContext as ReactTags } from 'react-tag-input';
import { FormGroup, FormControl } from "react-bootstrap";
import {COUNTRIES} from './countries';

import './tags.css';
import './modal.css';

const suggestions = COUNTRIES.map((country) => {
  return {
    id: country,
    text: country
  }
})

const KeyCodes = {
  comma: 188,
  enter: 13,
};

const delimiters = [KeyCodes.comma, KeyCodes.enter];

export default class ShowModel extends React.Component {
      constructor(props) {
          super(props);
          this.state = {
            certificate: props.certificate,
            isOpen: false,
            title: props.certificate.name,
            duration: props.certificate.duration,
            description: props.certificate.description,
            price: props.certificate.price,
            tags: [],
            disableForm: false,
            suggestions: suggestions,
            tagErrorMessage: '',
            errors: {
              title: '',
              description: '',
              price: '',
              duration: ''
            }
          };
          this.handleDelete = this.handleDelete.bind(this);
          this.handleAddition = this.handleAddition.bind(this);
          this.handleDrag = this.handleDrag.bind(this);
          this.handleTagClick = this.handleTagClick.bind(this);
    }

  handleDelete(i) {
    const { tags } = this.state;
    this.setState({
      tags: tags.filter((tag, index) => index !== i),
    });
  }

  handleAddition(tag) {
    if (tag.text.length > 3 && tag.text.length <= 15) {
      this.setState(state => ({ tags: [...state.tags, tag] }));
      this.setState({tagErrorMessage: ''});
    } else {
      this.setState({tagErrorMessage: 'Tag name should be not less than 3 and greater than 15 characters!'});
    }
  }

  handleDrag(tag, currPos, newPos) {
    const tags = [...this.state.tags];
    const newTags = tags.slice();

    newTags.splice(currPos, 1);
    newTags.splice(newPos, 0, tag);

    this.setState({ tags: newTags });
  }

  handleTagClick(index) {
    console.log('The tag at index ' + index + ' was clicked');
  }

  handleChange = (event) => {
    event.preventDefault();
    const { name, value } = event.target;
    let errors = this.state.errors;
    switch (name) {
      case 'title': {
        errors.title = 
          value.length <= 6 || value.length >= 30
            ? 'Title field must not be less than 6 and greater than 30 characters!'
            : '';
        break;
      }
      case 'description': {
        errors.description = 
          value.length <= 12 || value.length >= 1000
            ? 'Description field must not be less than 12 and greater than 1000 characters!'
            : '';
        break;
      }
      case 'price': {
        const re = /(?<=^| )\d+\.\d+(?=$| )/;
        const reTwo = /^[0-9\b]+$/;
        if (!reTwo.test(value)) {
            errors.price = (value === '' || !re.test(value))? 'Price must be a number or float and be greater than 0!': '';
        } else {
          errors.price = '';
        }
        break;
      }
      case 'duration': {
        const re = /^[0-9\b]+$/;
        errors.duration = 
        value === '' || !re.test(value) ? 'Duration must be a number. 0 – indicates this is infinite certificate!': '';
        break;
      }
        default:
            break;
    }
    this.setState({errors, [name]: value});
    if (this.state.title === '' || this.state.description === '' || this.state.duration === '' || this.state.price === '') {
      this.setState({ disableForm: false});
    } else {
      this.setState({ disableForm: true});
    }
}

  openModal = () => {
    this.setState({ isOpen: true });
  };

  closeModal = () => {
    this.setState({ isOpen: false });
  };

  updateCertificate = () => {
    const newTagsColletion = this.state.tags.map((tag) => tag.text);
    let certificate = {id: this.state.certificate.id, name: this.state.title, description: this.state.description, price: this.state.price, duration: this.state.duration, tags: newTagsColletion};
    CertificatesService.updateCertificate(certificate)
      .then(
        data => {
            console.log("data");
            this.setState({ isOpen: false });
            window.location.reload();
        },
        error => {
          console.log("error");
          
        }
      );
    }

  render() {
    const {errors, disableForm} = this.state;
    const { isOpen, tags, suggestions, certificate,tagErrorMessage } = this.state;
    return (
      <div>
        <Button className="btn btn-warning"  onClick={this.openModal}>Edit </Button>
        {isOpen &&
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
                                <FormControl type="text" value={this.state.title} name="title" onChange={this.handleChange}/>
                                {<span className='error'>{errors.title}</span>}

                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Description </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <textarea class="form-control" defaultValue={certificate.description} name = "description" onChange={this.handleChange} rows="3"></textarea>
                                {<span className='error'>{errors.description}</span>}

                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Duration </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" defaultValue={certificate.duration} onChange={this.handleChange} name="duration"/>
                                {<span className='error'>{errors.duration}</span>}

                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Price </div>
                        <div class="col-sm-9">
                            <FormGroup bsSize="large">
                                <FormControl type="text" defaultValue={certificate.price} onChange={this.handleChange} name="price"/>
                                {<span className='error'>{errors.price}</span>}
                            </FormGroup>
                        </div>
                        <div class="col-sm-3">Tags </div>
                        <div class="col-sm-9">
                        {<span className='error'>{tagErrorMessage}</span>}

                        <ReactTags 
                                  inputFieldPosition="top"
                                  tags={tags}
                                  suggestions={suggestions}
                                  delimiters={delimiters}
                                  handleDelete={this.handleDelete}
                                  handleAddition={this.handleAddition}
                                  handleDrag={this.handleDrag}
                                  handleTagClick={this.handleTagClick}/>
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button style={saveStyle} onClick={this.updateCertificate} disabled={!disableForm}>&nbsp;&nbsp;&nbsp;Edit&nbsp;&nbsp;&nbsp;</Button>
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
const saveStyle = {
  border: '1px solid black'
}

const cancelStyle = {
  color:'black',
  background:'#e1e1e5',
  border: '1px solid #b3b3b5'
}
