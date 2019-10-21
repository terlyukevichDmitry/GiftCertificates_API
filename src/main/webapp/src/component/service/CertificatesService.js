import axios from 'axios'

import {authHeader} from '../authent/authHeader'

import {handleOperationErrors} from '../service/HandleErrors'

class CertificatesService {
    retrieveCertificates(page) {
        const options = {
            headers: authHeader()
        };
        return axios.get(`https://localhost:9002/giftCertificates?page=${page}&limit=4`, options);
    }
    
    createCertificate(certificate) {
        let token = localStorage.getItem('token');
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              },
            body: JSON.stringify({'name': certificate.name, 'description': certificate.description, 
            'price': certificate.price, 'duration': certificate.duration, 'tags': certificate.tags}),
        };
        return fetch('https://localhost:9002/giftCertificates', options);
    }

    deleteCertificate(id) {
        const options = {
            method: 'DELETE',
            headers: authHeader()
        };
        return fetch('https://localhost:9002/giftCertificates/' + id, options)
    }

    updateCertificate(certificate) {
        let token = localStorage.getItem('token');
        const options = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
              },
            body: JSON.stringify({'id': certificate.id, 'name': certificate.name, 'description': certificate.description, 
            'price': certificate.price, 'duration': certificate.duration, 'tags': certificate.tags}),
        };
        return fetch('https://localhost:9002/giftCertificates/' + certificate.id, options);
    }
}

export default new CertificatesService
