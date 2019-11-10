import React, {Fragment} from 'react';

import Portal from '../Portal';
import Button from '../Button';

import { WithContext as ReactTags } from 'react-tag-input';
import {COUNTRIES} from './Countries';
import { certificateActions } from '../../../redux/actions/CertificateActions';
import { connect } from 'react-redux';

import { Formik, Form, Field, ErrorMessage } from 'formik';
import {CertificateSchema} from '../../validation/Schemas'
import { validateDuration, validatePrice} from '../../validation/ValidateFunctions'
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

class EditCertificateModal extends React.Component {
      constructor(props) {
          super(props);
          this.state = {
            certificate: props.certificate,
            isOpen: false,
            tags: [],
            disableForm: false,
            suggestions: suggestions,
            tagErrorMessage: '',
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

  updateCertificate = (event) => {
    event.preventDefault();
    const newTagsColletion = this.state.tags.map((tag) => tag.text);
    let certificate = {id: this.state.certificate.id, name: event.target.elements.title.value, description: event.target.elements.description.value,
    price: event.target.elements.price.value, duration: event.target.elements.duration.value, tags: newTagsColletion}; 
    const {dispatch} = this.props;
    dispatch(certificateActions.updateCertificate(certificate));
  }

  render() {
    const {disableForm} = this.state;
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
              <Formik initialValues={{ username: "", password: "" }} validationSchema={CertificateSchema}>
              {({ touched, errors }) => (
                        <Form name="form" onSubmit={this.updateCertificate}>
              <div className="modalBody">
                    <div class="row">
                        <div class="col-sm-3">Title </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="title" placeholder="Title" defaultValue={certificate.name} className={`form-control ${touched.title && errors.title ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="title" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Description </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field component="textarea" type="text" name="description" defaultValue={certificate.description} placeholder="Description" className={`form-control ${touched.description && errors.description ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="description" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Duration </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="duration" placeholder="Duration" defaultValue={certificate.duration} validate={validateDuration} className={`form-control ${touched.duration && errors.duration ? "is-invalid" : ""}`}/>
                                  <ErrorMessage component="div" name="duration" className="invalid-feedback"/>
                              </div>
                        </div>
                        <div class="col-sm-3">Price </div>
                        <div class="col-sm-9">
                              <div className="form-group">
                                  <Field type="text" name="price" placeholder="Price" defaultValue={certificate.price} validate={validatePrice} className={`form-control ${touched.price && errors.price ? "is-invalid" : ""}`}/>
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
                                  handleDelete={this.handleDelete}
                                  handleAddition={this.handleAddition}
                                  handleDrag={this.handleDrag}/>
                        </div>
                        </div>
                    </div>
              </div>
              <div className="modalFooter">
                  <Button style={{border: '1px solid black', padding: '6px 20px 6px 20px'}} type="submit">Save</Button>
                  <Button className="btn btn-secondary" style={{padding: '6px 10px 6px 10px', color:'black', background:'#e1e1e5', border: '1px solid #b3b3b5'}} onClick={this.closeModal}>&nbsp;Cancel&nbsp;</Button>
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
const editCertificateModal = connect()(EditCertificateModal);
export {editCertificateModal as EditCertificateModal}; 
