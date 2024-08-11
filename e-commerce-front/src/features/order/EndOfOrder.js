import { Box } from '@mui/material'
import React from 'react'
import { useSelector } from 'react-redux'
import { getIsSuccessOrder } from './orderSlice'
import { Navigate} from 'react-router'

const EndOfOrder = () => {
  const isSuccess = useSelector(getIsSuccessOrder)
 

  if (!isSuccess) {
    return <Navigate to="/home" />;
  }
    return (
      <Box>
        End of Order
      </Box>
    )
  
}

export default EndOfOrder
