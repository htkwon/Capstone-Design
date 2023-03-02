import React from 'react';
import Header from '../../layout/Header';
import { 
  Typography,
  Container
} from '@mui/material';


const MainBoard: React.FC = () => {
    return (
      <> 
        <Container>
        <Header/>
        <Typography variant="h3">Q&A 게시판</Typography>
        </Container>
      </>
    );
  }
  
  export default MainBoard;