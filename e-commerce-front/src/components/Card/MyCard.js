import React from 'react'
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

const MyCard = (props) => {
  return (
    <Card key={props.id} elevation={0} sx={{ maxWidth: 220,m:'12px'}}>
    <CardActionArea>
      <CardMedia
        component="img"
        image={props.imgSource}
        alt="green iguana"
        
      />
      <CardContent>
        <Typography gutterBottom variant="p" >
        {props.description}
        </Typography>
        <Typography variant="body2" color="text.secondary">
            {props.price} ₺
        </Typography>
      </CardContent>
    </CardActionArea>

  </Card>
  )
}

export default MyCard
