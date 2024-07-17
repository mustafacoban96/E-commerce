import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Pagination, Stack } from '@mui/material'
import { getPageNo,getTotalPages,fetchProducts} from '../../features/products/productSlice'
import { useTheme } from '@emotion/react'


const ProductPagination = () => {
    const dispatch = useDispatch()
    const pageNo = useSelector(getPageNo)
    const totalPages = useSelector(getTotalPages)

    const handleChange = (event, value) => {
        dispatch(fetchProducts({ pageNo: value - 1 }))
    }
    const myTheme = useTheme();
     const [mode] = useState(myTheme.palette.mode);
     const myTextColor= (mode) =>{
       return (mode === 'light' ? myTheme.palette.myBlack.light : myTheme.palette.myBlack.dark)
     }

    return (
        <Stack spacing={2} sx={{ alignItems: 'center', margin: '20px 0' }}>
            <Pagination
                count={totalPages}
                page={pageNo + 1}
                onChange={handleChange}
                sx={{color:myTextColor(mode)}}
            />
        </Stack>
    )
}

export default ProductPagination
