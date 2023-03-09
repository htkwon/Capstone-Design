import React, {useEffect, useState} from 'react';
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
import axios from "axios";
import c from "../../data/c_logo.png"

// BoardItems 인터페이스
interface BoardItems {
    nickname: String;
    time: String;
    title: String;
    content: String;
    language?: String;
    bookmark: number;
    comment: number;
}

// MostViewedItems 인터페이스
export interface MostViewedItems {
    nickname: String;
    title: String;
    language?: String;
}

const MainBoard: React.FC = () => {
    const [boardItems, setBoardItems] = useState<BoardItems[]>([]); // 인터페이스로 state 타입 지정
    const [mostViewedItems, setMostViewedItems] = useState<MostViewedItems[]>([]); // 인터페이스로 state 타입 지정

    useEffect(() => {
        // 목록 조회 부분
        axios
            .get(`/api/qnaBoards/list`)
            .then((response) => setBoardItems(response.data))
            .catch((err) => console.log(err));
        // 조회수가 높은 게시글 조회 부분
        axios
            .get(`/api/qnaBoards/most`)
            .then((response) => setMostViewedItems(response.data))
            .catch((err) => console.log(err));
    }, [])

    return (
        <>
            <Container>
                <Header/>
                {/* 조회수 높은 게시물 */}
                <MostViewedPost
                    data = {mostViewedItems} // mostViewedItems 를 props 로 전달
                />
                <Typography variant="h5" sx={{marginTop: 8, marginBottom: 5}}>Q&A 게시판</Typography>
                <Box>
                    {boardItems?.map((value) => {

                        // 언어선택 여부에 따른 이미지 넣기 조정
                        const LanguageImg = value.language ? (
                            <img src={c} width="25" height="25"/> // 이미지 관련 논의 필요, 정적 파일로 임시 지정
                        ) : null;

                        return (
                            <>
                                <Box
                                    sx={{
                                        bgcolor: 'background.paper',
                                        boxShadow: 2,
                                        borderRadius: 2,
                                        p: 2.5,
                                        minWidth: 300,
                                        marginTop: 2,
                                        marginBottom: 4,
                                        '&:hover': {
                                            opacity: [0.9, 0.8, 0.7],
                                        },
                                    }}
                                >
                                    <Box sx={{display: 'flex', justifyContent: "space-between"}}>
                                        <Typography sx={{color: 'text.primary', fontSize: 22, fontWeight: 'medium'}}>
                                            {value.title}
                                        </Typography>
                                        <Box sx={{display: 'flex'}}>
                                            <Typography sx={{marginRight: 1}}>{value.time}</Typography>
                                            {LanguageImg}
                                        </Box>
                                    </Box>
                                    <Box sx={{marginTop: 1, marginBottom: 1}}>{value.content}</Box>
                                    <Box
                                        sx={{
                                            fontWeight: 'bold',
                                            mx: 0.5,
                                            fontSize: 15,
                                            display: "flex",
                                            justifyContent: "space-between",
                                        }}
                                    >
                                        <Box sx={{color: 'text.secondary', display: 'flex'}}>
                                            <AccountProfile sx={{marginRight: 0.5}}/>
                                            <Typography>{value.nickname}</Typography>
                                        </Box>
                                        <Box sx={{display: 'flex'}}>
                                            <BookmarkIcon/>
                                            <Typography>{value.bookmark}</Typography>
                                            <ChatIcon sx={{marginLeft: 1, marginRight: 0.5}}/>
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
};
  
  export default MainBoard;