import { useTheme } from '@emotion/react'
import { Container, ImageList, ImageListItem, Typography } from '@mui/material'
import React from 'react'
import { Link } from 'react-router-dom';

const IndividualPic = () => {
  const theme = useTheme();
  
  return (
    
    <Container sx={{
      marginY:'10px',
      position:'relative',
      display:'flex',
      justifyContent:'center',
      alignItems:'center',
     
      }}>
        
        <Typography
          sx={{
            position:'absolute',
            color:`${theme.palette.individualPic.main}`,
            zIndex:'1',
            fontWeight:'bold',
            fontSize:{xs:'1.2em',sm:'1.5em',md:'2.2em'},
            backgroundColor:'red',
            padding:'4px'
          }}
        >Dress Collection</Typography>
        <Link to={'/products'} style={{display:'flex',justifyContent:'center',padding:'0'}}>
          <ImageList sx={{width:'80%',padding:'0'}}>
            <ImageListItem>
              <img src='https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fd9%2F12%2Fd912305cf28bf71881912af58f160828aee14753.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BLOOKBOOK%5D%2Cres%5Bm%5D%2Chmver%5B1%5D&call=url[file:/product/main]'/>
            </ImageListItem>
            <ImageListItem>
            <img src='https://lp2.hm.com/hmgoepprod?set=format%5Bwebp%5D%2Cquality%5B79%5D%2Csource%5B%2F67%2Fab%2F67ab75e99aa5221231ce83e7ee369748026524c0.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BLOOKBOOK%5D%2Cres%5Bm%5D%2Chmver%5B1%5D&call=url%5Bfile%3A%2Fproduct%2Fmain%5D'/>
            </ImageListItem>
          </ImageList>
        </Link>
    </Container>
  )
}

export default IndividualPic
