import React from 'react'
import useProductService from '../../service/product/product-service'

const AddProduct = () => {
    const {addProduct} = useProductService();


    const addProductHandler = (e) =>{
       e.preventDefault();

        const payload = {
            name:"Adel Eraser",
            description:"soft eraser",
            stock:520,
            price:10
        }

        addProduct(payload);
        
    }
  return (
    <div>
      <button onClick={addProductHandler}>add product</button>
    </div>
  )
}

export default AddProduct
