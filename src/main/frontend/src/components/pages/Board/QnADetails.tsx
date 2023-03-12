import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../../layout/Header';
import { 
  Typography,
  Container, 
  Box,
} from '@mui/material';
import BookmarkIcon from '@mui/icons-material/BookmarkBorder';
import ProfileIcon from '@mui/icons-material/AccountCircle';
import Money from '@mui/icons-material/MonetizationOn';

// Q&A 상세보기 데이터
interface DetailItems {
    id: number;
    title: String;
    content: String;
    time: String;
    nickname: String;
    language?: String;
    point?: number;
    bookmark: number;
    comment: number;
}

//axios get 이전에 UI 확인을 위한 데이터
const TestData : DetailItems = {
    id: 2,
    title: "컴포넌트 사용 도와주세요!",
    content: "자바스크립트 공부하고 있는데요. 부모 컴포넌트에서 자식 컴포넌트로 매개변수 값 보내고 싶은데 어떻게 해야하나요?",
    time: "10분전",
    nickname: "young",
    language: "JavaScript",
    point: 25,
    bookmark: 2,
    comment: 0,
}

//Q&A 상세보기
const QnADetails: React.FC = () => {
    //postItem은 상세보기에 들어갈 데이터 - DetailItems에 데이터 타입 지정
    //TestData로 초기값 지정해둔 상태 (postItem 값 지정된 경우)
    const [postItem, setPostItem] = useState<DetailItems | undefined>(TestData);
    /*
    초기값 없는 경우(undefiend) -> No Data 출력
    const [postItem, setPostItem] = useState<DetailItems | undefined>();
    */

    //axios get 할 때 받아올 게시글 번호
    let { id } = useParams();

    //axios 연동하는 경우 get 주소에 /api/qnaBoards/${id} 입력


    const PostDetails = postItem ? (
    //postItems 데이터 있는 경우 출력될 UI
    <>
    <Box sx={{
        display: 'flex',  
        marginTop: 8,
        marginBottom: 1
    }}>
        <Box sx={{fontSize:38, marginRight: 3}}>{postItem.title} </Box>
        <Box sx={{marginTop:2}}>{postItem.language}</Box>
    </Box>

    <Box sx={{
        display:'flex',
        justifyContent: 'space-between',
        marginBottom:8
    }}>
        <Box sx={{display:'flex'}}>
        <ProfileIcon sx={{fontSize:30, marginRight:0.5}}/>
        <Box sx={{fontSize:20}}>{postItem.nickname} ∙ {postItem.time}</Box>
        </Box>
        <Box sx={{display: 'flex'}}>
            <BookmarkIcon sx={{fontSize: 28}}/>
            <Box sx={{fontSize:20}}>{postItem.bookmark}</Box>
        </Box>
    </Box>

    <Box sx={{
        fontSize: 20,
        marginBottom: 12
    }}>
        {postItem.content}
    </Box>
    
    <Box sx={{ display:'flex', marginBottom:3}}>
        <Money sx={{color: '#ffcf40', fontSize:28}}/>
        <Box sx={{fontSize: 18, marginLeft:0.5}}>댓글 채택시 {postItem.point} 포인트를 적립해드립니다!</Box>
    </Box>

    <Box>
        <Typography variant='h5'>{postItem.comment}개의 댓글이 있습니다</Typography>
        <Box sx={{
            height:100,
            marginTop: 2,
            border: "1.5px solid grey",
            borderRadius: '16px',
        }}/>
    </Box>
    </>
    ) :     
    //postItems 데이터 없는 경우
    (<Typography variant='h3'>No Data</Typography>);

    return ( 
        <>
        <Container>
        <Header/>
        <Box>
            {PostDetails}
        </Box>
        </Container>
        </>
    )
}

export default QnADetails;