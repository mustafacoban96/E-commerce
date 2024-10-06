import React, { useEffect } from 'react'
import { useParams } from 'react-router'
import { fetchOrderDetailById, getOrderDetail } from './orderSlice';
import { useDispatch, useSelector } from 'react-redux';
import { Box, Card, CardActionArea, CardContent, CardMedia, Container, Paper, Stack, styled, Typography } from '@mui/material';

const Item = styled(Paper)(({ theme }) => ({
  ...theme.typography.body2,
  color: theme.palette.text.secondary,
  padding: '5px',
  display: 'flex',
  alignItems:'center',
  
}));

const OrderDetail = () => {
  const {orderId} = useParams();
  console.log(orderId)
  const dispach = useDispatch();
  const orderDetail = useSelector(getOrderDetail)
  console.log(orderDetail)
  useEffect(() => {
    dispach(fetchOrderDetailById(orderId))
  },[orderId,dispach])
  return (
    <Box>
       <Typography
              sx={{fontWeight:'bolder',fontSize:'1.4em',marginLeft:'10px'}}
            >Order Details</Typography>
      {orderDetail.map((item,index) =>(
        <Stack key={index} sx={{marginX:'5%',marginY:'10px',width:'90%',border:'solid 1px black',p:1}} direction={'row'}>
              <CardMedia
                  component="img"
                  image='https://lp2.hm.com/hmgoepprod?set=source[/ac/74/ac7495fa2aa2a0239a36e19102381cee9b5a0466.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'
                  alt="green iguana"
                  sx={{ maxWidth: 100 }}
              />
          <Stack sx={{width:'100%'}}>
          <Box>
            <Typography
              sx={{fontWeight:'bolder',fontSize:'1.3em',marginLeft:'10px'}}
            >{item.name}</Typography>
          </Box>
          <Box sx={{display:'flex',justifyContent:'left',marginTop:4,marginLeft:'10px'}}>
           <Stack direction={'column'} spacing={1}>
            <Stack direction={'row'} spacing={1}>
              <Typography sx={{fontWeight:'bold'}}>Quantity:</Typography>
              <Typography>{item.quantity}</Typography>
            </Stack>
            <Stack direction={'row'} spacing={1}>
              <Typography sx={{fontWeight:'bold'}}>Unit price: </Typography>
              <Typography>{item.unit_price} ₺</Typography>
            </Stack>
           </Stack>
          </Box>
          <Box sx={{display:'flex',justifyContent:'right',margin:'15px'}}>
            <Stack direction={'row'} spacing={1}>
              <Typography sx={{fontWeight:'bold'}}>Total: </Typography>
              <Typography>{item.total_price} ₺</Typography>
            </Stack>
          </Box>
          </Stack>
        </Stack>
      ))}
    </Box>
  )
}

export default OrderDetail
