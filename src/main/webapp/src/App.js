import React from 'react';
import './App.css';
import {RouterComponent} from "./component/route/RouterComponent";
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
      <div className="backColor">
          <RouterComponent/>
          <Footer/>
      </div>
  );
}

function FooterFunc({ children }) {
  return (
    <div>
      <div className="footerStyle">{children}</div>
    </div>
  );
}

const Footer = () => (
  <div>
    <FooterFunc className="footer">
      <p> {new Date().getFullYear()}, EXPEDIA Student</p>
    </FooterFunc>
    </div>
  );


export default App;
