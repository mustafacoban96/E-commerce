import React, { useState } from 'react'
import { Box, Button, CardMedia, Divider, IconButton, Paper, Stack, styled, TextField, Typography } from '@mui/material'
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import DeleteIcon from '@mui/icons-material/Delete';

const Item = styled(Paper)(({ theme }) => ({
    ...theme.typography.body2,
    color: theme.palette.text.secondary,
    padding:'15px',
    display:'flex',
    gap:12,
    marginTop:'10px'
  }));

  const listee = [1,2,3,4]

const Cart = () => {
    const [quantity, setQuantity] = useState(1);
    
  return (
   <Box 
    sx={{display:'flex',justifyContent:'space-around',flexDirection:{md:'row',xs:'column'}}}
   >
    {/* cart item area */}
    <Box sx={{width:{lg:'65%',md:'50%',xs:'90%'},marginX:'6%'}}>
        {
            listee.map((p,index) =>(
                <Item key={p} elevation={6}>
            <CardMedia
                component="img"
                image='https://lp2.hm.com/hmgoepprod?set=source[/ac/74/ac7495fa2aa2a0239a36e19102381cee9b5a0466.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'
                alt="green iguana"
                sx={{maxWidth:{md:120,sm:100}}}
            />
          
            <Stack direction={'column'}>
                <Typography variant='h6' sx={{fontWeight:'bold'}}>Product name</Typography>
                <Typography>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque in orci a leo varius feugiat. Donec a venenatis purus. </Typography>
                <Stack direction={'row'} sx={{marginTop:'5px',textAlign:'center'}} spacing={1}>
                    <Typography variant='h7' sx={{fontWeight:'bold'}}>Price :</Typography>
                    <Typography  variant='h7'>12 ₺</Typography>
                </Stack>
                <Stack 
                direction={'row'} 
                sx={{
                    display:'flex',
                    justifyContent:'left',
                    marginTop:'30px',
                    
                }}>
                    <IconButton  onClick={() => setQuantity(prev => Math.max(prev - 1, 1))} size="small">
                        <RemoveCircleOutlineIcon fontSize="inherit" />
                    </IconButton>
                    <TextField 
                        size='small' 
                        inputProps={{ style:{textAlign:'center'}}} 
                        value={Number(quantity)}
                        onChange={(e) => {
                            const value = Math.max(1, Math.min(Number(e.target.value), 10));
                            setQuantity(value);
                        }}
                        sx={{width:{lg:'10%',md:'12%'}}}
                    />
                    <IconButton onClick={() => setQuantity(prev => Math.min(prev + 1, 10))} size="small">
                        <AddCircleOutlineIcon fontSize="inherit" />
                    </IconButton>
                </Stack>
                <Stack>
                    <Box sx={{display:'inline-flex',justifyContent:'right'}}>
                       <Stack direction={'column'} spacing={2}>
                        <Stack direction={'row'} sx={{marginTop:'5px',textAlign:'center'}} spacing={1}>
                                <Typography sx={{fontWeight:'bold',}}>Total :</Typography>
                                <Typography>12 ₺</Typography>
                            </Stack>
                            <Button color='error' variant="contained" startIcon={<DeleteIcon />}>
                                Remove
                            </Button>
                       </Stack>
                       
                    </Box>
                </Stack>
            </Stack>
        </Item>
            ))
        }
        
        
        
    </Box>
        <Box sx={{width:{xl:'30%',lg:'35%',md:'40%'},p:2}}>
            <Box sx={{position:'fixed',boxShadow:'1px 1px 1px 1.4px lightgrey',padding:3}}>
            <Stack direction={'column'} spacing={1.5}>
                <Typography sx={{fontSize:'1.2em',fontWeight:'bold',color:'black'}}>Summary</Typography>
                <Stack direction={'row'} spacing={25}>
                    <Typography sx={{fontSize:'1.1em'}}>Subtotal</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>

                <Stack direction={'row'} spacing={25}>
                    <Typography sx={{fontSize:'1.1em'}}>Shipping</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>
                <Divider />
                <Stack direction={'row'} spacing={23}>
                    <Typography sx={{fontSize:'1.1em',fontWeight:'bold',color:'black'}}>Total Price</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>  
                <Button variant="contained" sx={{backgroundColor:'black'}}>Order</Button>
            </Stack>
            </Box>
        </Box>
        <Box sx={{display:{xs:'flex',md:'none'},justifyContent:'center',padding:'25px'}}>
        <Stack direction={'column'} spacing={1.5} sx={{width:{sm:'80%',xs:'90%'}}}>
                <Typography sx={{fontSize:'1.2em',fontWeight:'bold',color:'black'}}>Summary</Typography>
                <Stack direction={'row'} spacing={25}>
                    <Typography sx={{fontSize:'1.1em'}}>Subtotal</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>

                <Stack direction={'row'} spacing={25}>
                    <Typography sx={{fontSize:'1.1em'}}>Shipping</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>
                <Divider />
                <Stack direction={'row'} spacing={23}>
                    <Typography sx={{fontSize:'1.1em',fontWeight:'bold',color:'black'}}>Total Price</Typography>
                    <Typography sx={{fontSize:'1em'}}>200 ₺</Typography>
                </Stack>  
                <Button variant="contained" sx={{backgroundColor:'black'}}>Order</Button>
            </Stack>
        </Box>
   </Box>
  )
}

export default Cart
