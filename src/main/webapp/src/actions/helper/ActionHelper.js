import queryString from 'query-string'

export function findCertificates(message) {
    let searchArray = message.split(' ');
    let tags = '';
    let data = '';
    for (let i = 0; i < searchArray.length; i++) {
        if (!searchArray[i].indexOf('#')) {
            tags = tags.concat(searchArray[i].substring(1) + ',').trim();
        } else {
            data = data.concat(searchArray[i] + ' ').trim();                      
        }
    }
    tags = tags.substring(0, tags.length - 1);
    let searchQuery = '';
    if (tags.trim() !== '') {
        searchQuery = searchQuery.concat(`tagname=${tags}&`);        
    }
    if (data.trim() !== '') {
        searchQuery = searchQuery.concat(`description=${data}&`);
        searchQuery = searchQuery.concat(`name=${data}&`);
    }
    return searchQuery;
}

export function parseJwt() {
    let token = localStorage.getItem('token');
    if (token !== null) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var user = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(user);
    }
  };

  export function checkSortType(sortChecker) {
      let type;
      if (sortChecker) {
        type = 'desc';
        sortChecker = false;
    } else {
        type = 'asc';
        sortChecker = true;
    }
    let sortInformation = {type: type, sortChecker: sortChecker};
    return sortInformation;
  }

  export function setParams({ query = ""}) {
    const searchParams = new URLSearchParams();
    searchParams.set("query", query);
    return searchParams.toString();
  }

  export function getParams(location) {
    const searchParams = queryString.parse(location.search);
    return {query: searchParams};
  }
  