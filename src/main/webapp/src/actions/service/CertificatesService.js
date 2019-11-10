import axios from 'axios'

import { checkAuthHeader } from '../authent/AuthenticationHeader'
class CertificatesService {
    retrieveCertificates(query) {
        const options = {
            headers: checkAuthHeader()
        };          
        return axios.get(`https://localhost:9002/giftCertificates` + query, options);
    }

    findCertificatesByParameters(page, limit, searchParameters) {
        const options = {
            headers: checkAuthHeader()
        };
        let url = 'https://localhost:9002/giftCertificates?';
        url = url.concat(searchParameters);
        url = url.concat(`page=${page}&limit=${limit}`);
        return axios.get(url, options);
    }

    sortCertificatesByParameters(query) {
        const options = {
            headers: checkAuthHeader()
        };
        return axios.get(`https://localhost:9002/giftCertificates` + query, options);
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
            headers: checkAuthHeader()
        };
        return fetch('https://localhost:9002/giftCertificates/' + id, options);
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
