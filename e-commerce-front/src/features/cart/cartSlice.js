import { createAsyncThunk, createSelector, createSlice } from "@reduxjs/toolkit";
import axiosConfig from "../../service/api/newApi";

const initialState = {
    cart_items: [],
    isLoading: false,
    error: null,
    total_price: 0
};

export const fetchCartItems = createAsyncThunk('cart/fetchCartItems', async () => {
    const response = await axiosConfig.get('/cart/show-cart');
    return response.data;
});

export const addCartItem = createAsyncThunk('cart/addCartItem', async (itemId) => {
    const response = await axiosConfig.post('/cart/add-to-cart', {
        cart_item_id: itemId
    });
    console.log('add-cart::', response);
    return response.data;
});

export const removeCartItem = createAsyncThunk('cart/removeCartItem', async (itemId) => {
    const response = await axiosConfig.post('/cart/remove-from-cart', {
        cart_item_id: itemId
    });
    return itemId; // Returning the itemId to be used in the reducer
});

const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {
        incrementTotal: (state,action) =>{
            console.log('lala',action)
            state.total_price += action.payload;
            
        },
        decrementTotal: (state,action) =>{
            console.log('lale',action)
            
            state.total_price -= action.payload
            
        }
    },
    extraReducers(builder) {
        builder.addCase(fetchCartItems.pending, (state) => {
            state.isLoading = true;
        });
        builder.addCase(fetchCartItems.fulfilled, (state, action) => {
            state.isLoading = false;
            state.cart_items = action.payload;
            state.total_price = state.cart_items.reduce((total, item) => total + item.price, 0);
        });
        builder.addCase(fetchCartItems.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
        // Add item to cart
        builder.addCase(addCartItem.pending, (state) => {
            state.isLoading = true;
        });
        builder.addCase(addCartItem.fulfilled, (state, action) => {
            state.isLoading = false;
            state.cart_items.push(action.payload);
            state.total_price += action.payload.price;
        });
        builder.addCase(addCartItem.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
        // Remove item from cart
        builder.addCase(removeCartItem.pending, (state) => {
            state.isLoading = true;
        });
        builder.addCase(removeCartItem.fulfilled, (state, action) => {
            state.isLoading = false;
            state.cart_items = state.cart_items.filter(item => item.id !== action.payload);
            state.total_price = state.cart_items.reduce((total, item) => total + item.price, 0);
        });
        builder.addCase(removeCartItem.rejected, (state, action) => {
            state.isLoading = false;
            state.error = action.error.message;
        });
    }
});

export const getCartItems = (state) => state.cart.cart_items;
export const getIsLoading = (state) => state.cart.isLoading;
export const getError = (state) => state.cart.error;
export const getTotalPrice = (state) => state.cart.total_price;
export const { incrementTotal, decrementTotal} = cartSlice.actions


export default cartSlice.reducer;
