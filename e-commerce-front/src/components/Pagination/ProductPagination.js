import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Pagination, Stack } from '@mui/material'
import { getPageNo,getTotalPages,fetchProducts} from '../../features/products/productSlice'


const ProductPagination = () => {
    const dispatch = useDispatch()
    const pageNo = useSelector(getPageNo)
    const totalPages = useSelector(getTotalPages)

    const handleChange = (event, value) => {
        dispatch(fetchProducts({ pageNo: value - 1 }))
    }

    return (
        <Stack spacing={2} sx={{ alignItems: 'center', margin: '20px 0' }}>
            <Pagination
                count={totalPages}
                page={pageNo + 1}
                onChange={handleChange}
                color="primary"
            />
        </Stack>
    )
}

export default ProductPagination
