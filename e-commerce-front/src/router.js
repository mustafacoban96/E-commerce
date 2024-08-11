import { Navigate, createBrowserRouter } from "react-router-dom";
import DefaultLayout from "./components/DefaultLayout/DefaultLayout"
import GuestLayout from './components/GuestLayout/GuestLayout'
import Dashboard from './views/DashBoard/Dashboard'
import Home from "./views/Home/Home";
import Login from "./views/Login/Login";
import NotFound from './views/NotFound/NotFound'
import Register from "./views/Register/Register";

import ProductList from "./features/products/ProductList";
import ProductDetail from "./features/products/ProductDetail";
import Cart from "./features/cart/Cart";
import Order from "./features/order/Order";
import OrderSummary from "./features/order/OrderSummary";
import EndOfOrder from "./features/order/EndOfOrder";





const router = createBrowserRouter([
    {
        path:'/',
        element: <DefaultLayout/>,
        children:[
            {
                path:'/',
                element:<Navigate to='/home'/>
            },
            {
                path:'/dashboard',
                element:<Dashboard/>
            },
            {
                path:'home',
                element:<Home/>
            },
            {
                path:'/products',
                element:<ProductList/>
            },
            {   
                element:<ProductDetail/>,
                path:'/product/:productId',
               
            },
            {
                element:<Cart/>,
                path:"/cart"
            },
            {
                element:<Order/>,
                path:"/order-page"
            },
            {
                element:<OrderSummary/>,
                path:"/order-summary"
            },
            {
                element:<EndOfOrder/>,
                path:"/order-end"
            }
        ]
    },
    {
        path:'/',
        element:<GuestLayout/>,
        children:[
            {
                path:'login',
                element:<Login/>
            },
            {
                path:'register',
                element:<Register/>
            }
        ]
    },
    {
        path:'*',
        element:<NotFound/>
    }
])

export default router