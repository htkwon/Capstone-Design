import React from 'react';
import Header from '../../layout/Header';
import { 
  Typography,
  Container, 
  Box,
} from '@mui/material';
import BookmarkIcon from '@mui/icons-material/BookmarkBorder';
import ChatIcon from '@mui/icons-material/ChatBubbleOutline';
import AccountProfile from '@mui/icons-material/AccountCircle';
import MostViewedPost from '../../layout/MostViewedPost';
import  { boardItems } from "../../data/BoardItems";

const MainBoard: React.FC = () => {
    return (
      <> 
        <Container>
        <Header/>
        {/* 조회수 높은 게시물 */}
        <MostViewedPost/>
        <Typography variant="h5" sx={{marginTop:8, marginBottom:5}}>Q&A 게시판</Typography>
        <Box>
        {boardItems.map((value) => {

          // 언어선택 여부에 따른 이미지 넣기 조정
          const LanguageImg = value.language ? (
            <img src={value.language} width="25" height="25"/>
          ) : (null); 
          
          return (
            <>     
            <Box
              sx={{
                bgcolor: 'background.paper',
                boxShadow: 2,
                borderRadius: 2,
                p: 2.5,
                minWidth: 300,
                marginTop:2,
                marginBottom:4,
                '&:hover': {
                  opacity: [0.9, 0.8, 0.7],
                },
              }}
            >
            <Box sx={{ display: 'flex', justifyContent: "space-between" }}>
              <Typography sx={{color: 'text.primary', fontSize: 22, fontWeight: 'medium'}}>
              {value.title} 
              </Typography>
              <Box sx={{display: 'flex'}}>
                <Typography sx={{marginRight:1}}>{value.time}</Typography>
                {LanguageImg}
              </Box>
            </Box>
            <Box sx={{marginTop:1, marginBottom:1}}>{value.content}</Box>
            <Box
            sx={{
            fontWeight: 'bold',
            mx: 0.5,
            fontSize: 15,
            display: "flex",
            justifyContent: "space-between",
            }}
            >
              <Box sx={{ color: 'text.secondary', display: 'flex'}}>
              <AccountProfile sx={{marginRight: 0.5}}/>
              <Typography>{value.nickname}</Typography>
              </Box>
            <Box sx={{display:'flex'}}>
              <BookmarkIcon/>
              <Typography>{value.bookmark}</Typography>
              <ChatIcon sx={{marginLeft:1, marginRight:0.5}}/>
              <Typography>{value.comment}</Typography>
            </Box>
            </Box>
            </Box>
            </>
              )
            })}
            </Box>
            </Container>
      </>
    );
  }
  
  export default MainBoard;