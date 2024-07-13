import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, getAllProducts} from './productSlice'
import ProdcutPageCard from '../../components/Card/ProdcutPageCard'
import { Link } from 'react-router-dom'
import { Box, Grid, IconButton, Stack } from '@mui/material'
import ViewArrayOutlinedIcon from '@mui/icons-material/ViewArrayOutlined';
import ViewColumnOutlinedIcon from '@mui/icons-material/ViewColumnOutlined';
import TuneOutlinedIcon from '@mui/icons-material/TuneOutlined';
import { useTheme } from '@emotion/react'
import { ToastContainer } from 'react-toastify'

const ProductList = () => {
    const dispatch = useDispatch()
    const products = useSelector(getAllProducts);
    useEffect(() =>{
        console.log('fertch:::product',products)
        dispatch(fetchProducts())
     },[dispatch])
     const [filterGrid,setFilterGrid] = useState(3);
     const myTheme = useTheme();
     const [mode] = useState(myTheme.palette.mode);
     const myTextColor= (mode) =>{
       return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
     }
     
    
  return (
    <>
     <Box>
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
          { <Grid container sx={{padding:'5px'}}>
          {
              products.map(product =>(
          <Grid item key={product.id}
            xl={filterGrid}
            lg={3}
            md={4}
            sm={6}
            xs={12}
            >
              <Link to={'/productt'} key={product.id} style={{ textDecoration: 'none'}}>
                <ProdcutPageCard
                  product={product}
                />
                </Link>
          </Grid> 
  ))
          }
      </Grid>
      }
    </Box>
    <ToastContainer/>
    </>
   
  )
}

export default ProductList
