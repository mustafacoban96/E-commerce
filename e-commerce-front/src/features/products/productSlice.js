import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axiosConfig from "../../service/api/newApi";



const initialState = {
    products: [],
    pageNo: 0,
    pageSize:12,
    totalElements: 0,
    totalPages: 1,
    last: false,
    isLoading:false,
    error:null
};

// toolkit promise status artcile:::https://medium.com/lamalab/async-operations-in-redux-with-the-redux-toolkit-thunk-e7d024cbf875
export const fetchProducts = createAsyncThunk('fetchProducts',async ({ pageNo=0, pageSize=12 }) =>{
    
    //?pageNo=5&pageSize=5
    const response = await axiosConfig.get(`/products/product-list?pageNo=${pageNo}&pageSize=${pageSize}`);

    // console.log("fetch product:::::",response.data)
    return response.data;
});


const productSlice = createSlice({
    name:'products',
    initialState,
    reducers:{},
    extraReducers(builder){
        builder.addCase(fetchProducts.pending, (state) =>{
            state.isLoading = true
        })
        builder.addCase(fetchProducts.fulfilled,(state,action) =>{
            state.isLoading = false;
            state.products = action.payload.content;
            state.pageNo = action.payload.pageNo;
            state.pageSize = action.payload.pageSize;
            state.totalElements = action.payload.totalElements;
            state.totalPages = action.payload.totalPages;
            state.last = action.payload.last;
        })
        builder.addCase(fetchProducts.rejected,(state,action) =>{
            state.isLoading = false
            state.error = action.error.message
        })
        // builder.addCase(fetchProducts.fulfilled,(state,action) =>{
        //     return action.payload;
        // })
    }
})


export const getAllProducts = (state) => state.products.products;
export const getIsLoading = (state) => state.products.isLoading;
export const getError = (state) => state.products.error;
export const getPageNo = (state) => state.products.pageNo
export const getPageSize = (state) => state.products.pageSize;
export const getTotalElements =(state) => state.products.totalElements;
export const getTotalPages = (state) => state.products.totalPages;
export const getLast = (state) => state.products.last;
export default productSlice.reducer;