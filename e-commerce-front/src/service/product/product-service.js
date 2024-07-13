import { toast } from "react-toastify";
//import useApi from "../api"
import axiosConfig from "../api/newApi";
//import axiosClient from "../auth/test-auth";


//https://beyondthecloud.dev/blog/then-vs-async-await-in-lwc



const useProductService = () =>{
    //const api = useApi();


    const fetchProducts = () =>{
        axiosConfig.get("/products/").then((response) =>{
            console.log('products::::',response)
          return response;  
        }).catch((error) =>{
            console.log('product-error:::',error)
            return error;
        })
        
    }




    const addProduct = (addProductPayload) =>{
        return axiosConfig.post("/products/create-product",addProductPayload).then((response) => {
           console.log('product-response:',response);
           let message = response.data
           toast.success(message , {
            autoClose: 2000,
            position: 'top-right',
        });
           
        })
        .catch((err) => {
            console.log('product-error:',err);
           
        });
    }




    return {
        addProduct,
        fetchProducts
    }

}

export default useProductService;