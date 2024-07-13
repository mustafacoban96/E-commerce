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
   
      <Box key={props.product.id} sx={{ margin:'0.75px',color:`${myTextColor(mode)}`}}>
        <CardMedia
             component="img"
             image={'https://lp2.hm.com/hmgoepprod?set=source[/22/9b/229b87236e1e7400a0cd0ba0da29955ba89b365f.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'}
             alt="green iguana"
        />
        <CardContent 
        sx={{
            backgroundColor:`${myBackgroundColor(mode)}`,
        }}
        >
        <Typography gutterBottom variant="p"  sx={{fontSize:'0.88em'}}>
            {props.product.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
            {props.product.price} ₺
        </Typography>
      </CardContent>
      </Box>
    
  )
}

export default ProdcutPageCard
