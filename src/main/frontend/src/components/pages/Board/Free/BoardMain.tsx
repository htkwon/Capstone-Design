/**
 * 기본(자유)게시판 메인 페이지 입니다.
 */
import React, {useEffect, useState} from "react";
import {
  Avatar,
  Card,
  CardActions,
  CardContent,
  CardMedia,
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

import {PaginationControl} from 'react-bootstrap-pagination-control';
import 'bootstrap/dist/css/bootstrap.min.css';

// interface BoardMainProps {
//   boardData: Board;
// }

const BoardMain = () => {

  const [freeData, setFreeData] = useState([]);
  const [page, setPage] = useState(1);

  useEffect(() => {
    const curPage = page-1;
    axios
        .get("/api/freeBoardsPage?page="+curPage+"&size=4")
        .then((response) => setFreeData(response.data))
        .catch((error) => console.log(error));
  }, [page]);



  const displayPosting = freeData.map((content, idx) => (
    <PreviewPosting posting={content} key={idx} />
  ));


  return (
    <>
      <Typography>{freeBoard.name}</Typography>
      <FilterPosting />

      {displayPosting}
      <p></p>
      <PaginationControl
        page={page}
        between={1}
        total={100}
        limit={20}
        changePage={(page: React.SetStateAction<number>)=>setPage(page)}
        ellipsis={1}
       />
    </>
  );
};



interface PreviewPostingProps {
  posting: Posting;


}

const PreviewPosting = (props: any) => (
  <Card sx={{ minWidth: 275 }}>
    <CardContent>
    {/*<Avatar srcSet={props.posting.writer.profileImg as string}/>*/}
      <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
        {props.posting.id}
      </Typography>
      <Typography variant="h5" component="div">
        {props.posting.title}
      </Typography>
      <Typography sx={{ mb: 1.5 }} color="text.secondary">
        {props.posting.createdDate}
        {/*수정된 게시글이라면 modifiedDate가 나타나도록 추가해야함. */}
      </Typography>
      <Typography variant="body2">{props.posting.content}</Typography>
      {/*최대 n자까지만 나타나도록 수정요함. */}
    </CardContent>
    {
      // props.posting.imgUrl?.map(img => <CardMedia component="img" width="140" height="140" image={img as string}/>)
    }
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

export default BoardMain;
