import React from 'react'
import { useAuthContext } from '../../context/AuthContext'
import { Navigate, Outlet } from 'react-router';
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';

const DefaultLayout = () => {

  const {access_token} = useAuthContext();

  if(!access_token){
    return <Navigate to='login'/>
  }


  return (
    <div>
      <Navbar/>
      <Outlet/>
      <Footer/>
    </div>
  )
}

export default DefaultLayout
