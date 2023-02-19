import React, { useState } from "react";
import Header from "../../../layout/Header";
import {
  Container,
  Grid,
  Stack,
  FormControl,
  Select,
  MenuItem,
  TextField,
  Button,
} from "@mui/material";
import { Board } from "../../../../model/board";
import ViewReply from "../../../layout/ViewReply";
import CreateReply from "../../../layout/CreateReply";

/**
 * 자유 게시글 상세보기 페이지 입니다.
 */
const ViewPosting = (props: Board) => {
  const viewReplies = props.replies.map( (reply, idx)=>(<ViewReply reply={reply} key={idx}/>))

  return (
    <>
    <Container>
      <Header />

      <Grid container direction="column" spacing={2}>
        <Grid item>
          <TextField
            className="board title"
            id="board_title"
            maxRows={1}
            fullWidth
          >
            {props.title}
          </TextField>
        </Grid>
        <Grid item>{props.writer.profileImg}</Grid>
        <Grid item>{props.writer.nickname}</Grid>
        <Grid item>{props.article}</Grid>
        <Grid item>
          <Button>
            신고 {/*신고버튼 누르면 관리자에게 report, 신고 수 +1*/}
          </Button>
        </Grid>
        <Grid item>
          <Button>북마크</Button>{" "}
          {/*북마크버튼 누르면 사용자 정보의 북마크에 해당 게시글 추가,  게시글 정보의 북마크 수 +1, 한번더 클릭 시 해제*/}
        </Grid>
      </Grid>
      <Stack>
        {viewReplies}
        <CreateReply />
      </Stack>
    </Container>
    </>
    
  );
};
