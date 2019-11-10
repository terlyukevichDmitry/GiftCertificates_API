import React from 'react';

function FooterFunc({ children }) {
    return (
      <div>
        <div className="footerStyleLogin">{children}</div>
      </div>
    );
}
  
export const Footer = () => (
    <div>
        <FooterFunc className="footerLogin">
            <p> {new Date().getFullYear()}, EXPEDIA Student</p>
        </FooterFunc>
    </div>
);
  
export const Navbar = () => (
    <nav className="navbar navbar-expand-lg navbar-light navbar-custom">
        <nav className="navbar-brand" style={{color: '#acacac'}}>
                Admin UI 
        </nav>
    </nav>
);