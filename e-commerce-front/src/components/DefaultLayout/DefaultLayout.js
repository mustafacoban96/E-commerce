import React, { useEffect } from 'react'
import { useAuthContext } from '../../context/AuthContext'
import { Navigate, Outlet } from 'react-router';
import Navbar from '../Navbar/Navbar';
import Footer from '../Footer/Footer';
import { useDispatch } from 'react-redux';
import { fetchCartItems } from '../../features/cart/cartSlice';
  
const DefaultLayout = () => {

  const {access_token,user} = useAuthContext();
  const dispatch = useDispatch()
  useEffect(() => {
    if (user) {
      dispatch(fetchCartItems());  // Make sure this action fetches items from the server
    }
  }, [user, dispatch]);

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
