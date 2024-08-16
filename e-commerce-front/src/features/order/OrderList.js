import { Box, Button, Divider, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import ViewListIcon from '@mui/icons-material/ViewList';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { fetchOrders, getOrderList } from './orderSlice';
import { useAuthContext } from '../../context/AuthContext';
import CustomDateRangePicker from '../../components/DatePicker/CustomDateRangePicker ';


const OrderList = () => {
  const dispatch = useDispatch();
  const orderList = useSelector(getOrderList);
  const { user } = useAuthContext();

  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [filteredOrders, setFilteredOrders] = useState([]);

  useEffect(() => {
    if (user?.user_id) {
      dispatch(fetchOrders({ user_id: user.user_id }));
    }
  }, [dispatch, user.user_id]);

  // Filter orders when date range or orderList changes
  useEffect(() => {
    if (orderList.length > 0 && startDate && endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);

      const filtered = orderList.filter((order) => {
        const orderDate = new Date(order.delivery_by); // Use created_at if necessary
        return orderDate >= start && orderDate <= end;
      });

      setFilteredOrders(filtered);
    } else {
      setFilteredOrders(orderList); // Default to showing all orders
    }
  }, [orderList, startDate, endDate]);

  return (
    <Box sx={{ display: 'flex', p: 1, justifyContent: 'space-around' }}>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          width: { xs: '30%', sm: '25%', md: '20%' },
          border: '1px solid grey',
          height: '80vh',
        }}
      >
        <Stack>
          <CustomDateRangePicker
            startDate={startDate}
            endDate={endDate}
            setStartDate={setStartDate}
            setEndDate={setEndDate}
          />
          <Divider/>
        </Stack>
      </Box>

      <Box sx={{ width: '70%' }}>
        <Typography variant="h6">Order Summaries</Typography>
        {filteredOrders.length > 0 ? (
          filteredOrders.map((order, index) => (
            <Box key={index} sx={{ display: 'flex', flexDirection: 'column' }}>
              <Box>
                <Stack sx={{ m: 1 }}></Stack>
                <Stack direction={'column'} sx={{ m: 1, p: '5px', boxShadow: '1px 1px lightgray' }}>
                  <Stack direction={'row'} spacing={1}>
                    <Typography variant="body1" sx={{ fontSize: { xs: '0.8em', md: '1em' } }}>
                      Order number:
                    </Typography>
                    <Typography
                      variant="body1"
                      sx={{ fontSize: { xs: '0.8em', md: '1em' }, fontWeight: 'bold', color: 'darkslategrey' }}
                    >
                      {order.order_id}
                    </Typography>
                  </Stack>
                  <Divider />
                  <Stack direction={'row'} spacing={3.5}>
                    <Typography variant="body1" sx={{ fontSize: { xs: '0.8em', md: '1em' } }}>
                      Order total:
                    </Typography>
                    <Typography
                      variant="body1"
                      sx={{ fontSize: { xs: '0.8em', md: '1em' }, fontWeight: 'bold', color: 'darkslategrey' }}
                    >
                      {order.order_total} ₺
                    </Typography>
                  </Stack>
                  <Stack direction={'row'} spacing={2}>
                    <Typography variant="body1" sx={{ fontSize: { xs: '0.8em', md: '1em' } }}>
                      Delivered by:
                    </Typography>
                    <Typography
                      variant="body1"
                      sx={{ fontSize: { xs: '0.8em', md: '1em' }, fontWeight: 'bold', color: 'darkslategrey' }}
                    >
                      {order.delivery_by}
                    </Typography>
                  </Stack>
                  <Box sx={{ display: 'flex', justifyContent: 'right' }}>
                    <Link to={`/order/${order.order_id}`}>
                      <Button sx={{ backgroundColor: '#8e24aa' ,
                      '&:hover': {
                          backgroundColor: '#7b1fa2', // Color when hovered
                        },
                      '&:active': {
                          backgroundColor: '#6a1b9a', // Color when clicked
                        },

                      }} variant="contained" startIcon={<ViewListIcon />}>
                        Detail
                      </Button>
                    </Link>
                  </Box>
                </Stack>
              </Box>
            </Box>
          ))
        ) : (
          <Typography>No orders found for the selected date range</Typography>
        )}
      </Box>
    </Box>
  );
};

export default OrderList;
