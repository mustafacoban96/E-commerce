import React from 'react';
import ReactDOM from 'react-dom/client';
import {AuthProvider} from './context/AuthContext'
import { RouterProvider } from 'react-router-dom';
import router from './router';
import { CssBaseline, ThemeProvider } from '@mui/material';
import  theme  from './style/theme';
import 'react-toastify/dist/ReactToastify.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
    // <App />
  // </React.StrictMode>

  <AuthProvider>
    <ThemeProvider theme={theme}>
      <CssBaseline/>
      <RouterProvider router={router}/>
    </ThemeProvider>
  </AuthProvider>
);


