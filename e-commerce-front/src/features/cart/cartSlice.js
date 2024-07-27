import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axiosConfig from "../../service/api/newApi";


const initialState = {
    cart_items: [],
    isLoading:false,
    error:null,
    total_price:0
};



export const fetchCartItems = createAsyncThunk('cart/fetchCartItems',async () =>{
    
    const response = await axiosConfig.get('/cart/show-cart');
    return response.data;
});

const cartSlice = createSlice({
    name:'cart',
    initialState,
    reducers:{},
    extraReducers(builder){
        builder.addCase(fetchCartItems.pending, (state) =>{
            state.isLoading = true
        })
        builder.addCase(fetchCartItems.fulfilled,(state,action) =>{
            state.isLoading = false;
            state.cart_items = action.payload
            //total_price
            state.total_price = state.cart_items.reduce((total, item) => total + item.price, 0);
        })
        builder.addCase(fetchCartItems.rejected,(state,action) =>{
            state.isLoading = false
            state.error = action.error.message
        })
    }     
})

export const getCartItems = (state) => state.cart.cart_items;
export const getIsLoading = (state) => state.cart.isLoading;
export const getError = (state) => state.cart.error;
export const getTotalPrice = (state) => state.cart.total_price

export default cartSlice.reducer
