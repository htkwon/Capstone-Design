/**
 * 기본(자유)게시판 메인 페이지 입니다.
 */
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../../layout/Header";
import {
  Avatar,
  Card,
  CardActions,
  CardActionArea,
  CardContent,
  CardMedia,
  Container,
  Typography,
  Button,
  SpeedDial,
  SpeedDialAction,
  SpeedDialIcon,
  //Pagination,
  TextField,
  IconButton,
} from "@mui/material";
import { Board } from "../../../../model/board";
import { Posting } from "../../../../model/posting";
import FilterPosting from "../../../layout/FilterPosting";
import { freeBoard } from "../../../data/BoardData";
import axios from "axios";
import { PaginationControl } from "react-bootstrap-pagination-control";
import "bootstrap/dist/css/bootstrap.min.css";

//자유게시판 페이지 인터페이스
interface FreeBoardItems {
  id: number; //각 게시글이 가진 아이디
  title: String;
  content: String;
  nickname: String; 
  time: String;
  report: number;
  bookmark: number;
  comment: number;
}

const BoardMain = () => {
  const [freeData, setFreeData] = useState<FreeBoardItems[]>([]);
  const [page, setPage] = useState(1);

  useEffect(() => {
    const curPage = page - 1;
    axios
      .get("/api/freeBoardsPage?page=" + curPage + "&size=4")
      .then((response) => setFreeData(response.data))
      .catch((error) => console.log(error));
  }, [page]);

  const displayPosting = freeData.map((element,idx) => (
    <PreviewPosting {...element} key={idx} />
  ));

  return (
    <>
    <Container>
        <Header />
      <Typography>{freeBoard.name}</Typography>
      <FilterPosting />

      {displayPosting}
      <p></p>
      <PaginationControl
        page={page}
        between={1}
        total={100}
        limit={20}
        changePage={(page: React.SetStateAction<number>) => setPage(page)}
        ellipsis={1}
      />
      </Container>
    </>
  );
};

const PreviewPosting:React.FunctionComponent<FreeBoardItems> = (props: FreeBoardItems) => {

  const navigate = useNavigate();

  const goToPost = (postId: number) => {
    navigate(`/free/${postId}`);
  };

  return (
    <Card sx={{ minWidth: 275 }}>
      <CardActionArea onClick={()=>goToPost(props.id)} >
      <CardContent>
          {/*<Avatar srcSet={props.posting.writer.profileImg as string}/>*/}
          <Typography variant="h5" color="text.secondary">
            {props.id}
          </Typography>
          <Typography variant="h5" component="div">
            {props.title}
          </Typography>
          <Typography sx={{ mb: 1.5 }} color="text.secondary">
            {props.time}
            {/*수정된 게시글이라면 modifiedDate가 나타나도록 추가해야함. */}
          </Typography>
          <Typography variant="h5">{props.content}</Typography>
          {/*최대 n자까지만 나타나도록 수정요함. */}
        </CardContent>
        {
          // props.posting.imgUrl?.map(img => <CardMedia component="img" width="140" height="140" image={img as string}/>)
        }
      </CardActionArea>
      <CardActions>
        <Button size="small">
          신고(추후 이미지버튼으로 변경할것 또는 layout폴더에 신고 컴포넌트 만들
          것)
        </Button>
        <Button size="small">
          북마크(추후 이미지버튼으로 변경할것 또는 layout폴더에 북마크 컴포넌트
          만들 것)
        </Button>
      </CardActions>
    </Card>
  );
};

export default BoardMain;
