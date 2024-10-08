import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import axiosConfig from "../../service/api/newApi"



const initialState = {
    order_items_list:[],
    total_price:0,
    user_id:null,
    isLoading: false,
    error: null,
    is_success_order:false, // for route order-end page after successful order
    //these field for after create  order 
    order_id:null,
    order_total:0,
    delivery_by:null,
    order_list:[]
}

export const createOrder = createAsyncThunk('orders/createOrder', async(orderData) =>{
    const response = await axiosConfig.post("/order/create-order",orderData)
    return response.data
})

export const fetchOrders = createAsyncThunk('orders/fetchOrder' , async(user_id) =>{
    const response = await axiosConfig.post('/order/list-order',user_id)
    return response.data
})

export const fetchOrderDetailById = createAsyncThunk('orders/fetchOrderDetailById' , async(order_id) =>{
    const response = await axiosConfig.get(`/order-items/${order_id}`)
    return response.data
})


const orderSlice = createSlice({
    name:'orders',
    initialState,
    reducers:{
        resetOrderSuccess: (state) => {
            state.is_success_order = false;
        }
    },
    extraReducers(builder){
        // Add item to cart
        builder.addCase(createOrder.pending, (state) => {
            state.isLoading = true;
        })
        builder.addCase(createOrder.fulfilled, (state, action) => {
          
            state.isLoading = false;
            state.order_id = action.payload.order_id;
            state.delivery_by = action.payload.delivery_by
            state.order_total = action.payload.order_total
            state.is_success_order = action.payload.is_success_order;

        })
        builder.addCase(createOrder.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
        //fetch orders
        builder.addCase(fetchOrders.pending, (state) => {
            state.isLoading = true;
        })
        builder.addCase(fetchOrders.fulfilled, (state, action) => {
          
            state.isLoading = false;
            state.order_list = action.payload

        })
        builder.addCase(fetchOrders.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
        // fetch order detail by Id
        builder.addCase(fetchOrderDetailById.pending, (state) => {
            state.isLoading = true;
        })
        builder.addCase(fetchOrderDetailById.fulfilled, (state, action) => {
          
            state.isLoading = false;
            state.order_items_list = action.payload

        })
        builder.addCase(fetchOrderDetailById.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });

    }
})

export const getIsLoading = (state) => state.orders.isLoading;
export const getError = (state) => state.orders.error;
export const getIsSuccessOrder = (state) => state.orders.is_success_order;
export const getOrderId = (state) => state.orders.order_id;
export const getDeliveryBy = (state) => state.orders.delivery_by;
export const getOrderTotal = (state) => state.orders.order_total;
export const getOrderList = (state) => state.orders.order_list
export const getOrderDetail = (state) => state.orders.order_items_list; 
export const {resetOrderSuccess} = orderSlice.actions;

export default orderSlice.reducer;

