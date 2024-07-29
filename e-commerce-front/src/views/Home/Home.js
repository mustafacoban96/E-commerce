import React from 'react'
import { ToastContainer } from 'react-toastify'
import SaleArea from './SaleArea'
import HomeSlide2 from './HomeSlide2'
import IndividualPic from './IndividualPic'
import IndividualPic2 from './IndividualPic2'



const Home = () => {
  console.log('home')
  return (
    <div>
      <SaleArea/>
      {/* <HomeSlide/> */}
      <IndividualPic/>
      <HomeSlide2/>
      <IndividualPic2/>
      <ToastContainer/>
      
    </div>
  )
}

export default Home
