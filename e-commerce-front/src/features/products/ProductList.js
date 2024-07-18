import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { fetchProducts, getAllProducts, getError, getIsLoading, getLast, getPageSize, getTotalElements, selectProductById} from './productSlice'
import ProdcutPageCard from '../../components/Card/ProdcutPageCard'
import { Link } from 'react-router-dom'
import { Alert, Box, Button, CircularProgress, Grid, IconButton, Stack, Typography } from '@mui/material'
import ViewArrayOutlinedIcon from '@mui/icons-material/ViewArrayOutlined';
import ViewColumnOutlinedIcon from '@mui/icons-material/ViewColumnOutlined';
import TuneOutlinedIcon from '@mui/icons-material/TuneOutlined';
import { useTheme } from '@emotion/react'
import { ToastContainer } from 'react-toastify'
import ProductPagination from '../../components/Pagination/ProductPagination'

const ProductList = () => {
    const dispatch = useDispatch()
    // const products = useSelector(getAllProducts);
    const products = useSelector(getAllProducts);
    const isLoading = useSelector(getIsLoading);
    const error = useSelector(getError);
    const pageSize = useSelector(getPageSize);
    const [myPageSize,SetMyPageSize] = useState(pageSize);
    useEffect(() =>{
        dispatch(fetchProducts(myPageSize))
     },[dispatch,myPageSize])

     const [filterGrid,setFilterGrid] = useState(3);
     const myTheme = useTheme();
     const [mode] = useState(myTheme.palette.mode);
     const myTextColor= (mode) =>{
       return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
     }
     
     if (isLoading) {
      return (
        
          <Box sx={{display:'flex',justifyContent:'center',height:'100vh',alignItems:'center'}}>
          <CircularProgress color="success" />
        </Box>
        )
    }
  
    if (error) {
      return (
        <Box sx={{display:'flex',justifyContent:'center',height:'100vh',alignItems:'center'}}>
          <Alert severity="error">Products is not viewed properly...Please refresh your page</Alert>
        </Box>
      )
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
              <Link to={`/product/${product.id}`} key={product.id} style={{ textDecoration: 'none'}}>
                <ProdcutPageCard
                  product={product}
                />
                </Link>
          </Grid> 
  ))
          }
          
      </Grid>
      
      }
       <ProductPagination />
    </Box>
    <ToastContainer/>
    </>
   
  )
}

export default ProductList
