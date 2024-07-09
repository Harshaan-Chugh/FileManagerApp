import React from 'react';
import ReactDOM from 'react-dom/client';
import './core/index.css';
import App from './core/App';
import reportWebVitals from './core/reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

reportWebVitals();