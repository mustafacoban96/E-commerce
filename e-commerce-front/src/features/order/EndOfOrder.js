import { Alert, Box, Container, Divider, List, ListItem,Stack, Typography } from '@mui/material'
import React from 'react'
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { useSelector } from 'react-redux';
import { getDeliveryBy, getOrderId, getOrderTotal } from './orderSlice';
import { getIsSuccessOrder } from './orderSlice'
import { Navigate} from 'react-router'

const EndOfOrder = () => {
  const is_success = useSelector(getIsSuccessOrder)
  const order_id = useSelector(getOrderId);
  const deliver_by = useSelector(getDeliveryBy)
  const order_total = useSelector(getOrderTotal);


  if (!is_success) {
    return <Navigate to="/home" />;
  }
    return (
      <Box 
      sx={{display:'flex',p:1,justifyContent:'center'}}
      >
        <Box
          sx={{width:{sm:'65%',xs:'100%'}}} 
        >
          <Box>
              <Stack sx={{m:1}}>
                <Typography variant='h6'>Thank you for your order</Typography>
                <Typography variant='p'>Order summary</Typography>
              </Stack>
              <Stack direction={'column'} sx={{m:1,p:'5px',boxShadow:'1px 1px lightgray'}}>
                <Stack direction={'row'} spacing={1}>
                  <Typography variant='p' sx={{fontSize:{xs:'0.8em',md:'1em'}}}>Order number:</Typography>
                  <Typography variant='p'
                    sx={{fontSize:{xs:'0.8em',md:'1em'},fontWeight:'bold', color:'darkslategrey'}}
                  >{order_id}</Typography>
                </Stack>
                <Divider/>
                <Stack direction={'row'} spacing={3.5}>
                  <Typography variant='p' sx={{fontSize:{xs:'0.8em',md:'1em'}}}>Order total:</Typography>
                  <Typography variant='p'
                    sx={{fontSize:{xs:'0.8em',md:'1em'},fontWeight:'bold', color:'darkslategrey'}}
                  >{order_total} ₺</Typography>
                 
                </Stack>
                <Stack direction={'row'} spacing={2}>
                  <Typography variant='p' sx={{fontSize:{xs:'0.8em',md:'1em'}}}>Delivered by:</Typography>
                  <Typography variant='p'
                    sx={{fontSize:{xs:'0.8em',md:'1em'},fontWeight:'bold', color:'darkslategrey'}}
                  >{deliver_by}</Typography>
                </Stack>
                <Box sx={{display:'flex',justifyContent:'right'}}>
                  <CheckCircleIcon sx={{color:'#4caf50'}} fontSize='normal'/>
                </Box>
              </Stack>
          </Box>
          <Box sx={{}}>
            <Container
            src='https://cdn.prod.website-files.com/6364b6fd26e298b11fb9391f/6364b6fd26e2983671b93d4b_drawkit-32%20(1).png'
            component={'img'}/>
          </Box>
        </Box>
        <Box sx={{display:{xs:'none',sm:'flex',flexDirection:'column'},width:'34%'}}>
          <Alert severity='success' color='success' icon={false}>
          <Typography variant='p'sx={{fontSize:{md:'1.4em'},fontWeight:'bold', color:'darkslategrey',textDecoration:'underline'}}>What is next</Typography>
          <List>
            <ListItem>
              <Typography>Your order is being prepared for packaging.</Typography>
            </ListItem>
            <ListItem>
              <Typography>Once your tracking number is ready, you will be notified via email.
              </Typography>
            </ListItem>
            <ListItem>
              <Typography>You can track the status of your order in the My Orders section of your account.
              </Typography>
            </ListItem>
          </List>
          <Typography variant='p'sx={{fontSize:{md:'1.4em'},fontWeight:'bold', color:'darkslategrey',textDecoration:'underline'}}>Need Help?</Typography>
          <Typography>If you have any questions, please don't hesitate to contact us:
              </Typography>
          <List sx={{listStyle:'circle'}}>
            <ListItem>
              <Typography>Email: support@example.com</Typography>
            </ListItem>
            <ListItem>
              <Typography>Phone: +1-800-123-4567
              </Typography>
            </ListItem>
          </List>
          <Typography>We look forward to serving you again!</Typography>
          </Alert>
        </Box>
      </Box>
    )
  
}

export default EndOfOrder
