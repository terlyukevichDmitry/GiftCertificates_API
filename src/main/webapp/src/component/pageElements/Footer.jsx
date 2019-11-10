import React from 'react';

function FooterFunc({ children }) {
    return (
      <div>
        <div className="footerStyle">{children}</div>
      </div>
    );
}
  
export const Footer = () => (
    <div>
        <FooterFunc className="footer">
            <p> {new Date().getFullYear()}, EXPEDIA Student</p>
        </FooterFunc>
    </div>
);
  