import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import App from './App';
import ReactDOMClient from 'react-dom/client'


const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>);


