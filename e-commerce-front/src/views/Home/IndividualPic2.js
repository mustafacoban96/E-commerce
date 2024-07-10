import { useTheme } from '@emotion/react'
import { Button, Container,ImageListItem, styled, Typography } from '@mui/material'
import React from 'react'


const MyButton = styled(Button)(({theme}) =>({
            position:'absolute',
            color:'black',
            zIndex:'1',
            fontWeight:'bold',
            fontSize:{xs:'0.8em',sm:'1.1em',md:'1.8em'},
            padding:'1% 4% 1% 4%',
            bottom:'23%',
            backgroundColor:'#fafafa',
            '&:hover': {
                backgroundColor: '#bdbdbd',
                boxShadow: 'none',
              },
              '&:active': {
                boxShadow: 'none',
                backgroundColor: '#757575',
                
              },
}))

const IndividualPic2 = () => {
    const theme = useTheme();
  return (
    <Container sx={{
        marginY:'10px',
        position:'relative',
        display:'flex',
        justifyContent:'center',
        alignItems:'center',
        }}>
        
        <MyButton href='/products'> 
            Let's start
        </MyButton>
          <Typography
            sx={{
              position:'absolute',
              color:`${theme.palette.individualPic.main}`,
              zIndex:'1',
              fontWeight:'bold',
              fontSize:{xs:'1.2em',sm:'1.5em',md:'2.2em'},
              padding:'4px',
              bottom:'12%'
            }}
          >New Collection</Typography>
          <Typography
            sx={{
              position:'absolute',
              color:`${theme.palette.individualPic.main}`,
              zIndex:'1',
              fontWeight:'bold',
              fontSize:{xs:'0.8em',sm:'1.1em',md:'1.8em'},
              padding:'4px',
              bottom:'4%'
            }}
          >Trendy summer styles</Typography>
            <ImageListItem>
              <img src='https://image.hm.com/content/dam/global_campaigns/season_00/ladies/ws30l/WS30L-3x2-1.jpg?imwidth=1536'/>
            </ImageListItem>
      </Container>
  )
}

export default IndividualPic2
