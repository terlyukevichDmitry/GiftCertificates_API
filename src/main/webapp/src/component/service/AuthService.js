import {handleErrors} from '../service/HandleErrors'

class AuthService {
    login(loginRequest){
        const options = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({'login': loginRequest.login, 'password': loginRequest.password})
        };
        return fetch(`https://localhost:9002/api/auth/signin`, options)
        .then(handleErrors)
        .then(data => {
            localStorage.setItem('token', data.accessToken);
            return 200;
        });
    }

    logout() {
        localStorage.removeItem('token');
    }
}

export default new AuthService();