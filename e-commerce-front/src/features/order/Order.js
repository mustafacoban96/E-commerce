import { Alert, Box, Button, CardMedia, Checkbox, CircularProgress, Modal, Stack } from '@mui/material';
import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router';
import Card from '@mui/material/Card';

import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { createOrder, getError, getIsLoading, getIsSuccessOrder,resetOrderSuccess} from './orderSlice';
import { toast, ToastContainer } from 'react-toastify';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
  };
const Order = () => {
    const location = useLocation();
    const data = location.state;
    //console.log('11:::',location)
    const [checked, setChecked] = useState(false);
    const [open, setOpen] = useState(false);
    const [orderInitiated, setOrderInitiated] = useState(false); //i don't want toast works when page is refreshed
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleChange = (event) => {
        setChecked(event.target.checked);
    };
      const error = useSelector(getError)
      const isLoading = useSelector(getIsLoading)
      const dispatch= useDispatch();
      const navigate = useNavigate();
      const isSuccess = useSelector(getIsSuccessOrder);
      

      
      useEffect(() => {
        if (orderInitiated) {
            if (error) {
                toast.error("Order was not performed.. maybe there is a product stock problem", {
                    autoClose: 2500,
                    position: 'top-right'
                });
            } else if (!isLoading) {
                toast.success("Order was created successfully", {
                    autoClose: 2000,
                    position: 'top-right'
                });
                setTimeout(() =>{
                    navigate("/order-end")
                },2300)
               
            }
        }
    }, [error, isLoading, orderInitiated,isSuccess]);

    const handleOrder = () => {
        setOrderInitiated(true);
        dispatch(createOrder({
            user_id: data.user_id,
            total_price: data.total_price,
            order_items_list: data.order_items_list
        }));
    };
    
    if(!data){
        setTimeout(() =>{
            navigate('/products')
        },3300)
        return (
        <Box sx={{ display: 'flex', justifyContent: 'center', height: '100vh', alignItems: 'center' }}>
            <Alert severity="info">There is any product in your cart, You are directing products page....</Alert>
        </Box>
        )
    }
   

    if (isLoading) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', height: '100vh', alignItems: 'center' }}>
                <CircularProgress color="success" /> 
            </Box>
        );
    }

    


    
  return (
    <>
     <Box sx={{p:2,position:'relative'}}>
        <Stack direction={'row'} sx={{width:'100%',justifyContent:'center'
        }} spacing={3}>
            <Stack spacing={1} sx={{width:'100%'}}>
                {
                    data.order_items_list.map((item,index) =>(
                        <Card key={index} variant="outlined" sx={{width:'63%'}}>
                        <Box sx={{ p: 2 ,backgroundColor:'#fafafa'}}>
                            <Stack direction="row" justifyContent="space-between" alignItems="center"
                            >
                            <Typography gutterBottom variant="h5" component="div">
                                {item.product_name}
                            </Typography>
                            <Typography gutterBottom variant="h6" component="div">
                                {item.total_price_per_product} ₺
                            </Typography>
                            </Stack>
                            
                        </Box>
                        <Divider />
                        <Box sx={{ p: 2 }}>
                            <Stack direction="row" spacing={6}
                                sx={{alignItems:'center'}}
                            >
                                <Link to={`/product/${item.product_id}`}>
                                    <CardMedia
                                        component="img"
                                        image='https://lp2.hm.com/hmgoepprod?set=source[/ac/74/ac7495fa2aa2a0239a36e19102381cee9b5a0466.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'
                                        alt="green iguana"
                                        sx={{ maxWidth: 80 }}
                                    />
                                </Link>
                                <Stack direction={'row'} spacing={1}>
                                    <Typography color="black" variant="body2">
                                        Quantity:
                                    </Typography>
                                    <Typography color="text.secondary" variant="body2">
                                        {item.quantity}
                                    </Typography>
                                </Stack>
                                <Stack direction={'row'} spacing={1}>
                                    <Typography color="black" variant="body2">
                                        Unit Price: 
                                    </Typography>
                                    <Typography color="text.secondary" variant="body2">
                                       {item.unit_price}
                                    </Typography>
                                </Stack>
                            </Stack>
                        </Box>
                    </Card>
                    ))
                }
                
            </Stack>
            <Box sx={{width:'30%',textAlign:'center',alignContent:'center',border:'0.5px solid lightgray',backgroundColor:'#fafafa',padding:3,position:'absolute', right:'2%'}}>
            <Stack direction={'column'} spacing={1.5}>
                    <Typography sx={{ fontSize: '1.2em', fontWeight: 'bold', color: 'black' }}>Confirm the Cart</Typography>
                    <Divider />
                    <Stack direction={'row'} spacing={20} justifyContent='center'>
                        <Typography sx={{ fontSize: '1.1em', fontWeight: 'bold', color: 'black' }}>Total Amount</Typography>
                        <Typography sx={{ fontSize: '1em' }}>{data.total_price} ₺</Typography>
                    </Stack>
                    <Stack direction={'row'} spacing={2} alignItems={'center'} justifyContent={'center'}>
                        <Checkbox
                        checked={checked}
                        onChange={handleChange}
                        inputProps={{ 'aria-label': 'controlled' }}
                        color='success'
                        />
                        
                        <Typography onClick={handleOpen} sx={{ fontSize: '1em',textDecoration:'underline', cursor:'pointer'}}> Read the conditions</Typography>
                    </Stack>
                    <Stack direction={'row'} spacing={20} justifyContent='center'>
                        <Button
                            variant="contained"
                            disabled={!checked}
                            sx={{
                                backgroundColor: '#00a156',
                                '&:hover': {
                                    backgroundColor: '#00a152', // Success color on hover
                                },
                                width:'70%'
                            }}
                            onClick={() => handleOrder()}
                        >
                            Checkout
                        </Button>
                    </Stack>
                    
                </Stack>
            </Box>
        </Stack>
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Text in a modal
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </Typography>
        </Box>
      </Modal>
     </Box>
     <ToastContainer/>
    </>
  )
}

export default Order
