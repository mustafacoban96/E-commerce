import { useTheme } from '@emotion/react'
import { Container, List, styled, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'


const SaleArea = () => {
    const theme = useTheme();
    const [mode] = useState(theme.palette.mode);
    const [colorIndex,setColorIndex] = useState(0)
    const myTextColor= (mode) =>{
        return (mode === 'light' ? theme.palette.saleColor.light : theme.palette.saleColor.dark)
      }
    let colorChangeList = [1,2,3]
    
    
    useEffect(() =>{
                setTimeout(() =>{
                  
                    setColorIndex((colorIndex) => colorIndex +1)
                    if(colorIndex === colorChangeList.length){
                        setColorIndex(0)
                    }
                },1300);
    },[colorIndex])
    
    
    
    

  return (
    <Container
        sx={{
            backgroundColor:`${theme.palette.saleColor.main}`,
            display:'flex',
            flexDirection:'column',
            alignItems:'center',
            textAlign:'center',
            height:'100vh',
            justifyContent:'space-around',
        }}
    >
        <List>
            {
                colorChangeList.map((el,index) =>(
                    
                    <Typography
                        key={el}
                        sx={{
                            fontWeight:'bolder',
                            fontFamily:'serif',
                            fontSize:{xs:'1.7em',sm:'2.5em',md:'3em'},
                            color:index === colorIndex ? '#ffff00' : myTextColor(mode),
                            
                        }}
                    >LASTEST DISCOUNT</Typography>
                ))
            }
            <Typography variant='h5'
                sx={{
                    fontWeight:'bolder',
                    color:`${theme.palette.saleColor.textColor}`,
                    fontFamily:'serif',
                    color:`${myTextColor(mode)}`
                }}
            >Online and in Stores</Typography>
        </List>
        
        <Typography variant='p'
             sx={{
                
                color:`${theme.palette.saleColor.textColor}`,
                fontFamily:'serif',
                alignSelf:'center',
                color:`${myTextColor(mode)}`
            }}
        >The discount is valid on selected products between 07.06.2024 and 07.07.2024 and is subject to stock availability. Discounted prices may vary between in-store and online purchases. The discount cannot be combined with other promotions or offers. Unless otherwise stated on the product page, the discounted price is the lowest price in the last 30 days.</Typography>
    </Container>
  )
}

export default SaleArea


