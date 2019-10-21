import axios from 'axios'

import {authHeader} from '../authent/authHeader'

class SearchService {
    retrieveCertificates(page) {
        const options = {
            headers: authHeader()
        };
        return axios.get(`https://localhost:9002/giftCertificates?page=${page}&limit=4`, options);
    }
}

export default new SearchService
