import { useTheme } from '@emotion/react'
import { Box,CardContent, CardMedia,styled,Typography } from '@mui/material'
import React, { useState } from 'react'

const ProdcutPageCard = (props) => {
    const myTheme = useTheme();
    const [mode] = useState(myTheme.palette.mode);
    const myTextColor= (mode) =>{
        return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
      }
    const myBackgroundColor = (mode) =>{
        return (mode === 'light' ? myTheme.palette.footerColor.light : myTheme.palette.footerColor.dark)
    }


  return (
   
      <Box key={props.id} sx={{ margin:'0.75px',color:`${myTextColor(mode)}`}}>
        <CardMedia
             component="img"
             image={props.imgSource}
             alt="green iguana"
        />
        <CardContent 
        sx={{
            backgroundColor:`${myBackgroundColor(mode)}`,
        }}
        >
        <Typography gutterBottom variant="p"  sx={{fontSize:'0.88em'}}>
            {props.description}
        </Typography>
        <Typography variant="body2" color="text.secondary">
            {props.price} ₺
        </Typography>
      </CardContent>
      </Box>
    
  )
}

export default ProdcutPageCard
