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
import "../../../../model/board";
import "../../../../model/user";
/**
 * 기본(자유)게시판 메인 페이지 입니다.
 */

const BoardMain = () => {
  const postDisplay = () => {
    return <PostPreview />;
  };
  return (
    <>
      <BoardTitle />
      <PostFilter />

      <>{postDisplay()}</>
      <WritingButton></WritingButton>
      <Pagination></Pagination>
    </>
  );
};

const BoardTitle = () => <TextField>자유게시판</TextField>;

const PostFilter = () => {
  return <>{/*추후 게시글 정렬 기능 추가요함. */}</>;
};

const WritingButton = () => {
  return (
    <IconButton
      sx={{ position: "absolute", bottom: 16, right: 16 }}
    ></IconButton>
  );
};

const PostPreview = () => {
  return (
    <Card sx={{ minWidth: 275 }}>
      <CardContent>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          작성자
        </Typography>
        <Typography variant="h5" component="div">
          게시글 제목
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
          게시글 등록일자
        </Typography>
        <Typography variant="body2">게시글 내용</Typography>
      </CardContent>

      <CardActions>
        <Button size="small">신고(추후 이미지버튼으로 변경할것)</Button>
        <Button size="small">북마크(추후 이미지버튼으로 변경할것)</Button>
      </CardActions>
    </Card>
  );
};
export default BoardMain;
