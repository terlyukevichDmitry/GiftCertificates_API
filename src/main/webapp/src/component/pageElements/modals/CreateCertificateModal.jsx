import React from 'react';

import Portal from '../Portal';
import Button from '../Button';

import { WithContext as ReactTags } from 'react-tag-input';
import {COUNTRIES} from './Countries';
import { connect } from 'react-redux';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { CertificateSchema } from '../../validation/Schemas';
import { certificateActions } from '../../../redux/actions/CertificateActions';
import { validateDuration, validatePrice} from '../../validation/ValidateFunctions';

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

class CreateCertificateModal extends React.Component {
      constructor(props) {
          super(props);
          this.state = {
            isOpen: false,
            tags: [],
            suggestions: suggestions,
            tagErrorMessage: ''
            };
            this.handleDelete = this.handleDelete.bind(this);
            this.handleAddition = this.handleAddition.bind(this);
            this.handleDrag = this.handleDrag.bind(this);
        }

  handleDelete(i) {
    const { tags } = this.state;
    this.setState({
      tags: tags.filter((tag, index) => index !== i),
    });
  }

  handleAddition(tag) {
    if (tag.text.length <= 3) {
        this.setState({tagErrorMessage: 'Tag name should be not less than 3 and!'});
    } else if (tag.text.length >= 15) {
        this.setState({tagErrorMessage: 'Tag name should be not greater than 15 characters!'});
    } else {
        this.setState(state => ({ tags: [...state.tags, tag] }));
        this.setState({tagErrorMessage: ''});
    }
  }

  handleDrag(tag, currPos, newPos) {
    const tags = [...this.state.tags];
    const newTags = tags.slice();

    newTags.splice(currPos, 1);
    newTags.splice(newPos, 0, tag);

    this.setState({ tags: newTags });
  }

  openModal = () => {
    this.setState({ isOpen: true });
  };

  closeModal = () => {
    this.setState({ isOpen: false });
  };

  createCertificate = (event) => {
    event.preventDefault();
    const newTagsColletion = this.state.tags.map((tag) => tag.text);
    let certificate = {name: event.target.elements.title.value, description: event.target.elements.description.value,
    price: event.target.elements.price.value, duration: event.target.elements.duration.value, tags: newTagsColletion};
    const { dispatch } = this.props;    
    dispatch(certificateActions.createCertificate(certificate));
  }

  render() {
    const { disableForm} = this.state;
    const { isOpen, tags, suggestions, tagErrorMessage } = this.state;
    return (
      <div>
        <Button style={{padding: '6px 25px 6px 25px'}} onClick={this.openModal}>Add new</Button>
        {isOpen &&
        <Portal>
          <div className="modalOverlay">
            <div className="modalWindow">
              <div className="modalHeader">
                <div className="modalTitle">Add new certificate</div>
              </div>
              <Formik initialValues={{ username: "", password: "" }} validationSchema={CertificateSchema}>
              {({ touched, errors }) => (
              <Form name="form" onSubmit={this.createCertificate}>
              <div className="modalBody">
                    <div class="row">
                        <div class="col-sm-3">Title </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="title" placeholder="Title" className={`form-control ${touched.title && errors.title ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="title" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Description </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field component="textarea" type="text" name="description" placeholder="Description" className={`form-control ${touched.description && errors.description ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="description" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Duration </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="duration" placeholder="Duration" validate={validateDuration} className={`form-control ${touched.duration && errors.duration ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="duration" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Price </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="price" placeholder="Price" validate={validatePrice} className={`form-control ${touched.price && errors.price ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="price" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Tags </div>
                        <div class="col-sm-9">
                        {<span className='error'>{tagErrorMessage}</span>}
                        <div>
                            <ReactTags 
                                  name="tags"
                                  inputFieldPosition="top"
                                  tags={tags}
                                  suggestions={suggestions}
                                  delimiters={delimiters}
                                  onChange={this.handleChange}
                                  handleDelete={this.handleDelete}
                                  handleAddition={this.handleAddition}
                                  handleDrag={this.handleDrag}/>
                        </div>
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button style={{border: '1px solid black', padding: '6px 20px 6px 20px'}} type="submit">Save</Button>
                  <Button className="btn btn-secondary" style={{padding: '6px 10px 6px 10px', color:'black', background:'#e1e1e5', border: '1px solid #b3b3b5'}} onClick={this.closeModal} invert>&nbsp;Cancel&nbsp;</Button>
              </div>
              </Form>)}
              </Formik>
            </div>
          </div>
        </Portal>
        }
      </div>
    );
  }
}
const createCertificateModal = connect()(CreateCertificateModal);
export {createCertificateModal as CreateCertificateModal}; 
