import React, { useEffect, useState } from 'react';
import { Alert, Box, Button, CardMedia, CircularProgress, Divider, IconButton, Paper, Stack, styled, Typography } from '@mui/material';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import DeleteIcon from '@mui/icons-material/Delete';
import { useDispatch, useSelector } from 'react-redux';
import { fetchCartItems, getCartItems, getError, getIsLoading, getTotalPrice, removeCartItem,incrementTotal,decrementTotal } from './cartSlice';
import { useAuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router';


const Item = styled(Paper)(({ theme }) => ({
    ...theme.typography.body2,
    color: theme.palette.text.secondary,
    padding: '15px',
    display: 'flex',
    gap: 12,
    marginTop: '10px'
}));

const Cart = () => {
    const [quantities, setQuantities] = useState({});
    const dispatch = useDispatch();
    const cartItems = useSelector(getCartItems);
    const isLoading = useSelector(getIsLoading);
    const error = useSelector(getError);
    const totalPrice = useSelector(getTotalPrice);
    const [orderItems,setOrderItems] = useState({});
    const {user} = useAuthContext(); 
    const navigate = useNavigate();

    //for field order Item.
    //quantity,unit price,total individual price,
    const handleOrderItems = () => {
        console.log(orderItems)
        setTimeout(() =>{
            navigate("/order-page",{state: orderItems})
        },2500)
    };

    //order
    //userId,order items
   

    const handleRemoveFromCart = (productId) => {
        dispatch(removeCartItem(productId));
    };

    useEffect(() => {
        // Initialize quantities state and calculate total price when cartItems change
        const initialQuantities = cartItems.reduce((acc, item) => {
            acc[item.id] = 1; // Default quantity to 1 for each item
            return acc;
        }, {});
        
        setQuantities(initialQuantities);
    }, [cartItems]);
    useEffect(() =>{
        const quantityAndUnitPrice = cartItems.reduce((acc, item) => {
            acc.push({
                product_id: item.id,
                product_name: item.name,
                unit_price: item.price,
                total_price_per_product:(quantities[item.id] || 1) * item.price,
                quantity: quantities[item.id] || 1, // Handle undefined quantities
            });
            return acc;
        }, []);
        const orderItemsState = {
            user: user.user_id,
            total_price: totalPrice,
            orderItemsInfo: quantityAndUnitPrice
        };

        setOrderItems(orderItemsState);
    },[user,totalPrice,quantities,cartItems])
   
    const handleQuantityChange = (productId, newQuantity) => {
       
        const updatedQuantities = {
            
            ...quantities,
            [productId]: newQuantity
        };
       // console.log("asasasasas: ",updatedQuantities)
        setQuantities(updatedQuantities);
        
        
    };
   

    useEffect(() => {
        dispatch(fetchCartItems());
    }, [dispatch]);

    

    if (isLoading) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', height: '100vh', alignItems: 'center' }}>
                <CircularProgress color="success" /> 
            </Box>
        );
    }

    if (error) {
        return (
            <Box sx={{ display: 'flex', justifyContent: 'center', height: '100vh', alignItems: 'center' }}>
                <Alert severity="error">The cart is not viewed properly...Please refresh the page</Alert>
            </Box>
        );
    }

    return (
       <Box sx={{ display: 'flex', justifyContent: 'space-around', flexDirection: { md: 'row', xs: 'column' } }}>
          
            {/* cart item area */}
            <Box sx={{ width: { lg: '65%', md: '50%', xs: '90%' }, marginX: '6%' }}>
            
                {cartItems.length === 0 ? 
                <Box sx={{height:'45vh',display:'flex',alignItems:'center',justifyContent:'center'
            
                }}>
                   <Alert  severity="warning">The cart is empty</Alert>
                </Box> : cartItems.map((p, index) => (
                    <Item key={p.id} elevation={6}>
                        <CardMedia
                            component="img"
                            image='https://lp2.hm.com/hmgoepprod?set=source[/ac/74/ac7495fa2aa2a0239a36e19102381cee9b5a0466.jpg],origin[dam],category[],type[LOOKBOOK],res[z],hmver[1]&call=url[file:/product/main]'
                            alt="green iguana"
                            sx={{ maxWidth: { md: 120, sm: 100 } }}
                        />
                        <Stack direction={'column'} sx={{ width: '100%', position: 'relative' }}>
                            <Typography variant='h6' sx={{ fontWeight: 'bold' }}>{p.name}</Typography>
                            <Typography>{p.description}</Typography>
                            <Stack direction={'row'} sx={{ marginTop: '5px', textAlign: 'center' }} spacing={1}>
                                <Typography variant='h7' sx={{ fontWeight: 'bold' }}>Price :</Typography>
                                <Typography variant='h7'>{p.price} ₺</Typography>
                            </Stack>
                            <Stack
                                direction={'row'}
                                sx={{
                                    display: 'flex',
                                    justifyContent: 'left',
                                    marginTop: '30px',
                                    
                                }}
                            >
                                <IconButton onClick={() => {
                                    handleQuantityChange(p.id, Math.max(quantities[p.id] - 1, 1));
                                    dispatch(decrementTotal(p.price));
                                    }} size="small" disabled={quantities[p.id] === 1 ? true : false}>
                                    <RemoveCircleOutlineIcon fontSize="inherit" />
                                </IconButton>
                                <Typography sx={{paddingTop:'2px',paddingX:'3px'}}>{quantities[p.id]}</Typography>
                                {/* <TextField
                                    size='small'
                                    inputProps={{ style: { textAlign: 'center' } }}
                                    value={quantities[p.id] || 1}
                                    onChange={(e) => {
                                        const value = Math.max(1, Math.min(Number(e.target.value), p.stock));
                                        handleQuantityChange(p.id, value);
                                    }}
                                    sx={{ width: { lg: '10%', md: '12%' } }}
                                /> */}
                                <IconButton onClick={() => {
                                    handleQuantityChange(p.id, Math.min(quantities[p.id] + 1, p.stock));
                                    
                                    dispatch(incrementTotal(p.price));
                                    }} size="small" disabled={quantities[p.id] === p.stock ? true : false}>
                                    <AddCircleOutlineIcon fontSize="inherit" />
                                </IconButton>
                            </Stack>
                            <Stack>
                                <Box sx={{ display: 'inline-flex', justifyContent: 'right', position: 'absolute', bottom: '5px', right: '4px' }}>
                                    <Stack direction={'column'} spacing={2}>
                                        <Stack direction={'row'} sx={{ marginTop: '5px', textAlign: 'center' }} spacing={1}>
                                            <Typography sx={{ fontWeight: 'bold', }}>Total :</Typography>
                                            <Typography>{p.price * (quantities[p.id] || 1)} ₺</Typography>
                                        </Stack>
                                        <Button onClick={() => handleRemoveFromCart(p.id)} color='error' variant="contained" startIcon={<DeleteIcon />}>
                                            Remove
                                        </Button>
                                    </Stack>
                                </Box>
                            </Stack>
                        </Stack>
                    </Item>
                ))}
            </Box>
            <Box sx={{ width: { xl: '30%', lg: '35%', md: '40%' }, p: 2 }}>
                <Box sx={{ position: 'fixed', boxShadow: '1px 1px 1px 1.4px lightgrey', display: { xs: 'none', md: 'block' }, padding: 3 }}>
                    <Stack direction={'column'} spacing={1}>
                        <Typography sx={{ fontSize: '1.2em', fontWeight: 'bold', color: 'black' }}>Summary</Typography>
                        <Stack direction={'row'} spacing={20}>
                            <Typography sx={{ fontSize: '1.1em' }}>Subtotal</Typography>
                            <Typography sx={{ fontSize: '1em' }}>200 ₺</Typography>
                        </Stack>
                        <Stack direction={'row'} spacing={20}>
                            <Typography sx={{ fontSize: '1.1em' }}>Shipping</Typography>
                            <Typography sx={{ fontSize: '1em' }}>200 ₺</Typography>
                        </Stack>
                        <Divider />
                        <Stack direction={'row'} spacing={15}>
                            <Typography sx={{ fontSize: '1.1em', fontWeight: 'bold', color: 'black' }}>Total Price</Typography>
                            <Typography sx={{ fontSize: '1em' }}>{totalPrice} ₺</Typography>
                        </Stack>
                        <Button
                            onClick={() => handleOrderItems()}
                            variant="contained"
                            sx={{
                                backgroundColor: 'black',
                                '&:hover': {
                                    backgroundColor: '#00a152' // Success color on hover
                                },
                            }}
                        >
                            Order
                        </Button>
                    </Stack>
                </Box>
            </Box>
            <Box sx={{ display: { xs: 'flex', md: 'none' }, justifyContent: 'center', padding: '25px' }}>
                <Stack direction={'column'} spacing={1.5} sx={{ width: { sm: '80%', xs: '90%' } }}>
                    <Typography sx={{ fontSize: '1.2em', fontWeight: 'bold', color: 'black' }}>Summary</Typography>
                    <Stack direction={'row'} spacing={20}>
                        <Typography sx={{ fontSize: '1.1em' }}>Subtotal</Typography>
                        <Typography sx={{ fontSize: '1em' }}>200 ₺</Typography>
                    </Stack>
                    <Stack direction={'row'} spacing={20}>
                        <Typography sx={{ fontSize: '1.1em' }}>Shipping</Typography>
                        <Typography sx={{ fontSize: '1em' }}>200 ₺</Typography>
                    </Stack>
                    <Divider />
                    <Stack direction={'row'} spacing={20}>
                        <Typography sx={{ fontSize: '1.1em', fontWeight: 'bold', color: 'black' }}>Total Price</Typography>
                        <Typography sx={{ fontSize: '1em' }}>{totalPrice} ₺</Typography>
                    </Stack>
                    <Button
                        onClick={() => handleOrderItems()}
                        variant="contained"
                        sx={{
                            backgroundColor: 'black',
                            '&:hover': {
                                backgroundColor: '#00a152', // Success color on hover
                            },
                        }}
                    >
                        Order
                    </Button>
                </Stack>
            </Box>
        </Box>
    );
};

export default Cart;
