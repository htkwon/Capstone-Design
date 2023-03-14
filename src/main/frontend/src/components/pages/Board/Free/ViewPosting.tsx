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
import { Reply } from "../../../../model/reply";

//자유 상세보기 인터페이스
interface FreeDetailItems {
  id: number;
  title: String;
  content: String;
  nickname: String;
  profileImg: String;
  stuId: number;
  createdDate: String;
  report: number;
  bookmarks: number;
  reply: number;
  replyList?: Array<Reply> | undefined; //은서: 추후 댓글창 관련해서 이야기 나눠보고 수정해도 될 것 같습니다!
}

//더미데이터
const initial = {
  id: 456,
  title: "더미데이터의 제목입니다.",
  content:
    "내용입니다. 내용 예시 입니다. Content Example. 내용입니다. 내용 예시 입니다. Content Example. ",
  nickname: "예시닉네임상상부기",
  profileImg:
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYe-zIT7cyG-M6Vlla38oVJ6twus0auSO7tQ&usqp=CAU" as string,
  stuId: 1971096,
  createdDate: "2023-03-14",
  report: 0,
  bookmarks: 0,
  reply: 0,
};

const ViewPosting: React.FC = (): JSX.Element => {
  const [postItem, setPostItem] = useState<FreeDetailItems | undefined>(
    initial
  );
  const { id } = useParams();
  const detailPosting = postItem ? (
    <>
      <Grid container direction="column" spacing={2}>
        <Grid item>
          <Typography>{postItem.title}</Typography>
        </Grid>
        <Grid item>{postItem.nickname}</Grid>
        <Grid item>{postItem.createdDate}</Grid>
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
    </>
  ) : (
    <Typography>no data</Typography>
  );

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
