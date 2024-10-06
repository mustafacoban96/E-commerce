import React from 'react';
import { Stack, TextField, Typography } from '@mui/material';

const CustomDateRangePicker = ({ startDate, endDate, setStartDate, setEndDate }) => {
  return (
    <Stack spacing={3} sx={{m: 2, display: 'flex',flexDirection:'column' }}>
        <Typography component={'h5'}>Date Picker</Typography>
      <TextField
        label="Start Date"
        type="date"
        value={startDate}
        onChange={(e) => setStartDate(e.target.value)}
        InputLabelProps={{
          shrink: true,
        }}
        sx={{ mr: 2 }}
      />
      <TextField
        label="End Date"
        type="date"
        value={endDate}
        onChange={(e) => setEndDate(e.target.value)}
        InputLabelProps={{
          shrink: true,
        }}
      />
    </Stack>
  );
};

export default CustomDateRangePicker;
