import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Header from "../../../layout/Header";
import {
  Avatar,
  Box,
  Container,
  Stack,
  Typography,
  IconButton,
} from "@mui/material";
import BookmarkIcon from "@mui/icons-material/BookmarkBorder";
import WarningAmberIcon from "@mui/icons-material/WarningAmber";
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
      <Box sx={{ paddingLeft: 3, paddingRight: 3 }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <Typography variant="h5" sx={{ marginBottom: 1, fontWeight: 600 }}>
            {postItem.title}
          </Typography>
          <Typography variant="caption">{postItem.createdDate}</Typography>
        </Box>

        <Box sx={{ marginBottom: 5 }}>
          <Stack direction="row">
            <Avatar
              srcSet={postItem.profileImg as string}
              sx={{ width: "30px", height: "30px", marginRight: "5px" }}
            />
            <Typography variant="body2">
              {`${postItem.nickname} (${postItem.stuId
                .toString()
                .slice(0, 2)}학번)`}
            </Typography>
          </Stack>
        </Box>

        <Box sx={{ marginBottom: 1 }}>
          <Typography variant="body1">{postItem.content}</Typography>
          {/* 이미지에 대해서는 추후 논의 후 추가)*/}
        </Box>
        <Box sx={{ marginTop: 3, marginBottom: 3 }}>
          <Stack direction="row" sx={{ disply: "flex", justifyContent: "end" }}>
            <IconButton size="small">
              <WarningAmberIcon /> {postItem.report}
            </IconButton>
            <IconButton size="small">
              <BookmarkIcon /> {postItem.bookmarks}
            </IconButton>
          </Stack>
        </Box>

        <Box>
          <Typography
            variant="body1"
            sx={{ marginBottom: 5, paddingLeft: 3, fontWeight: 600 }}
          >
            {`${postItem.reply}개의 댓글이 있습니다.`}
          </Typography>
          <Box sx={{ border: "1px solid #787878", height: "50%" }}>
            {/*은서: 추후 이곳에 댓글 목록, 댓글 작성란 등 컴포넌트 추가하기*/}
          </Box>
        </Box>
      </Box>
    </>
  ) : (
    <Typography>no data</Typography>
  );

  return (
    <>
      <Container>
        <Header />
        <Box
          sx={{
            borderLeft: "1px solid black",
            borderRight: "1px solid black",
            padding: 10,
          }}
        >
          {detailPosting}
        </Box>
      </Container>
    </>
  );
};

export default ViewPosting;