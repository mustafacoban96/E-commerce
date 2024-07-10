
import { Box, Button, Container, Grid, IconButton, Stack, Typography } from '@mui/material'
import React from 'react'
import {data} from '../../data'
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import MyCard from '../../components/Card/MyCard';
import CircleOutlinedIcon from '@mui/icons-material/CircleOutlined';
import CircleIcon from '@mui/icons-material/Circle';
import { Link } from 'react-router-dom';

const HomeSlide = () => {
  
  return (
    <Container
      sx={{
        
        marginY:'5px'
      }}
    >
      <Stack direction='row'
        sx={{
          justifyContent:'space-between'
        }}
      >
        <Box sx={{ 
          flexDirection: 'column', 
          display: 'flex', 
          justifyContent: 'center', 
          alignItems: 'center', 
        }}>
            <IconButton size='large'>
                <ArrowBackIcon sx={{fontSize:'1em'}}/>
            </IconButton>
        </Box>
        {
          data.map((p,index) =>(
            //route to product details
            <Link to={'/products'} style={{textDecoration:'none'}}>
              <Grid>
              <MyCard
                key={index}
                id={p.id}
                imgSource={p.src}
                description={p.description}
                price={p.price}
              />
              </Grid>
            </Link>
          ))
        }
        
        <Box sx={{ 
            flexDirection: 'column', 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
          }}>
          <IconButton size='large'>
                <ArrowForwardIcon sx={{fontSize:'1em'}}/>
            </IconButton>
        </Box>
      </Stack>
      <Box
       sx={{
        display:'flex',
        flexDirection:'row',
        justifyContent:'center',
        alignItems:'center'

      }}
       >
        <IconButton>
          <CircleIcon sx={{fontSize:'0.5em'}}/>
        </IconButton>
        <IconButton>
          <CircleOutlinedIcon sx={{fontSize:'0.5em'}}/>
        </IconButton>
        <IconButton>
          <CircleOutlinedIcon sx={{fontSize:'0.5em'}}/>
        </IconButton>
        <IconButton>
          <CircleOutlinedIcon sx={{fontSize:'0.5em'}}/>
        </IconButton>
        
      </Box>
    </Container>
  )
}

export default HomeSlide
