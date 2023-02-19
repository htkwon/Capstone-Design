/**
 * 기본(자유)게시판 메인 페이지 입니다.
 */
import React, { useState } from "react";
import {
  Card,
  CardActions,
  CardContent,
  Typography,
  Button,
  Pagination,
  SpeedDial,
  SpeedDialAction,
  SpeedDialIcon,
  TextField,
  IconButton,
} from "@mui/material";
import { Board } from "../../../../model/board";
import FilterPosting  from "../../../layout/FilterPosting";

const BoardTitle = () => <TextField>자유게시판</TextField>;

const BoardMain = (data: Array<Board>) => {
  const displayPosting = data.map((posting, idx) => {
    <PreviewPosting posting={posting} />;
  });

  return (
    <>
      <BoardTitle />
      <FilterPosting />

      <>{displayPosting}</>
      <Pagination></Pagination>
    </>
  );
};

interface PreviewPostingProps {
  posting: Board;
}
const PreviewPosting = (props: PreviewPostingProps) => {
  return (
    <Card sx={{ minWidth: 275 }}>
      <CardContent>
        {props.posting.writer.profileImg}
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          {props.posting.writer.nickname}
        </Typography>
        <Typography variant="h5" component="div">
          {props.posting.title}
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
          {props.posting.createdDate}
          {/*수정된 게시글이라면 modifiedDate가 나타나도록 추가해야함. */}
        </Typography>
        <Typography variant="body2">{props.posting.article}</Typography>
        {/*최대 n자까지만 나타나도록 수정요함. */}
      </CardContent>

      <CardActions>
        <Button size="small">신고(추후 이미지버튼으로 변경할것 또는 layout폴더에 신고 컴포넌트 만들 것)</Button>
        <Button size="small">북마크(추후 이미지버튼으로 변경할것 또는 layout폴더에 북마크 컴포넌트 만들 것)</Button>
      </CardActions>
    </Card>
  );
};
export default BoardMain;