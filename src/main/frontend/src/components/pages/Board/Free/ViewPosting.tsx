import React, { useState } from "react";
import Header from "../../../layout/Header";
import {
  Container,
  Grid,
  Stack,
  FormControl,
  Select,
  MenuItem,
  Typography,
  Button,
} from "@mui/material";
import { Posting } from "../../../../model/posting";
import ViewReply from "../../../layout/ViewReply";
import WriteReply from "../../../layout/WriteReply";
import { user1 } from "../../../data/UserData";

/**
 * 자유 게시글 상세보기 페이지 입니다.
 */
interface ViewPostingProps {
  posting: Posting;
}
const ViewPosting = (props: ViewPostingProps) => {
  const viewReplies = props.posting.replies.map( (reply, idx)=>(<ViewReply reply={reply} key={idx}/>));

  return (
    <>
    <Container>
      <Header />

      <Grid container direction="column" spacing={2}>
        <Grid item>
          <Typography>
            {props.posting.title}
          </Typography>
        </Grid>
        <Grid item>{props.posting.writer.profileImg}</Grid>
        <Grid item>{props.posting.writer.nickname}</Grid>
        <Grid item>{props.posting.article}</Grid>
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
      <Stack>
        {viewReplies}
        <WriteReply loginUser={user1}/>
      </Stack>
    </Container>
    </>
    
  );
};

export default ViewPosting;