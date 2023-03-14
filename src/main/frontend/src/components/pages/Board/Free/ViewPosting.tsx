import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Header from "../../../layout/Header";
import {
  Container,
  Grid,
  Stack,
  Select,
  MenuItem,
  Typography,
  Button,
} from "@mui/material";

/**
 * 자유 게시글 상세보기 페이지 입니다.
 */

//자유게시판 페이지 인터페이스
interface FreeBoardItems {
  id: number; //각 게시글이 가진 아이디
  title: String;
  content: String;
  nickname: String; 
  time: String;
  bookmark: number;
  comment: number;
}

//더미데이터
const initial = {
  id: 456,
  title: "제목입니다.",
  content: "내용입니다. 내용 예시 입니다. Content Example",
  nickname: "닉네임", 
  time: "2001-01-01",
  bookmark: 0,
  comment: 0
}

const ViewPosting: React.FC = (): JSX.Element => {
  const [postItem, setPostItem] = useState<FreeBoardItems|undefined>(initial);
  const { id } = useParams();
  console.log(`현재 게시글 id ${id}`);
  const detailPosting = postItem ? (
    <>
    <Grid container direction="column" spacing={2}>
          <Grid item>
            <Typography>{postItem.title}</Typography>
          </Grid>
          <Grid item>{postItem.nickname}</Grid>
          <Grid item>{postItem.time}</Grid>
          <Grid item>{postItem.content}</Grid>
          <Grid item>
            <Button>
              신고 {/*신고버튼 누르면 관리자에게 report, 신고 수 +1*/}
            </Button>
          </Grid>
          <Grid item>
            <Button>북마크</Button>
            {/*북마크버튼 누르면 사용자 정보의 북마크에 해당 게시글 추가,  게시글 정보의 북마크 수 +1, 한번더 클릭 시 해제*/}
          </Grid>
        </Grid>
        {/*<Stack>
          {viewReplies}
          <WriteReply loginUser={user1} />
  </Stack>*/ }
    </>
  ) : (
      <Typography>no data</Typography>
  );

 {/* const viewReplies = postItem.replies
    .filter((reply) => postItem.uuid === reply.reactedPosting.uuid)
    .map((reply, idx) => <ViewReply reply={reply} key={idx} />); */}

  return (
    <>
      <Container>
        <Header />
      {detailPosting}
      </Container>
    </>
  );
};

export default ViewPosting;
