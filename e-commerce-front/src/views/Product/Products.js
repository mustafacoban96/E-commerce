import React, { useState } from 'react'
import { Box, Grid, IconButton, Stack} from '@mui/material'
import {data} from '../../data'
import { Link } from 'react-router-dom'
import ProdcutPageCard from '../../components/Card/ProdcutPageCard'
import ViewArrayOutlinedIcon from '@mui/icons-material/ViewArrayOutlined';
import ViewColumnOutlinedIcon from '@mui/icons-material/ViewColumnOutlined';
import TuneOutlinedIcon from '@mui/icons-material/TuneOutlined';
import { useTheme } from '@emotion/react'
import { ToastContainer } from 'react-toastify'
import AddProduct from './AddProduct'
import ProductList from '../../features/products/ProductList'

const Products = () => {
  console.log('product')
  const [filterGrid,setFilterGrid] = useState(3);
  const myTheme = useTheme();
  const [mode] = useState(myTheme.palette.mode);
  const myTextColor= (mode) =>{
    return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
  }
  return (
    <>
    <ProductList/>
        <Box
    sx={{
    }}
    >
      <AddProduct/>
        <Stack direction={'row'} sx={{justifyContent:'right',paddingX:'3px',alignItems:'center'}}>
          <IconButton sx={{display:{xs:'none',xl:'inline-block'}}} onClick={() => setFilterGrid(3)}>
              <ViewArrayOutlinedIcon 
              sx={{
                fontSize:'1.2em', 
                color:`${myTextColor(mode)}`,
                borderBottom: filterGrid === 3 ? '1px black solid' :''}}/>
          </IconButton>
          <IconButton sx={{display:{xs:'none',xl:'inline-block'}}} onClick={() => setFilterGrid(2)}>
              <ViewColumnOutlinedIcon 
              sx={{
                fontSize:'1.2em', 
                color:`${myTextColor(mode)}`,
                borderBottom: filterGrid === 2 ? '1px black solid' :''}}/>
          </IconButton>
          <IconButton>
              <TuneOutlinedIcon sx={{fontSize:'1.2em', color:`${myTextColor(mode)}`}}/>
          </IconButton>
        </Stack>
        <Grid container sx={{padding:'5px'}}>
          {data.map((p, index) => (
        <Grid item key={index}
          xl={filterGrid}
          lg={3}
          md={4}
          sm={6}
          xs={12}
          >
            <Link to={'/productt'} key={index} style={{ textDecoration: 'none'}}>
              <ProdcutPageCard
                id={p.id}
                imgSource={p.src}
                description={p.description}
                price={p.price}
              />
              </Link>
        </Grid>
        ))}
        </Grid>
      
    </Box>
    <ToastContainer/>
    </>

  )
}

export default Products
