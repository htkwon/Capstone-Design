import React, {useEffect, useState} from "react";
import axios from "axios"
import Time from "../layout/Time";
import { 
    Typography,
    Box 
 } from "@mui/material";
import { RxAvatar } from "react-icons/rx";
import { BsBookmarkStar } from "react-icons/bs";
import { TfiCommentAlt } from "react-icons/tfi";
import "../style/Board.css";

// FreeBoardItems 인터페이스
interface FreeBoardItems {
    uuid: number;
    title: string;
    content: string;
    writer: string;
    createdDate: string;
    bookmarks: number;
    reply: number;
}

const Board: React.FC = () => {
    const [boardItems, setBoardItems] = useState<FreeBoardItems[]>([]);

    useEffect(() => {
        axios
            .get("/api/freeBoards")
            .then((response) => setBoardItems(response.data.data))
            .catch((error) => console.log(error));
    }, []);

    const timeForToday = (value: string) => {
        const today = new Date();
        const timeValue = new Date(value);

        const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
        if (betweenTime < 1) return '방금전';
        if (betweenTime < 60) {
            return `${betweenTime}분전`;
        }

        const betweenTimeHour = Math.floor(betweenTime / 60);
        if (betweenTimeHour < 24) {
            return `${betweenTimeHour}시간전`;
        }

        const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
        if (betweenTimeDay < 365) {
            return `${betweenTimeDay}일전`;
        }

        return `${Math.floor(betweenTimeDay / 365)}년전`;
    }

    return (
        <>
            <div className="board">
                <Typography variant="h5" className="boardTitle">자유게시판</Typography>
                {boardItems.map((posting, index) => {
                    return (
                        <div key={index}>
                        <Box sx={{
                            width: 400, 
                            height: 130, 
                            '&:hover': {
                                backgroundColor: 'gainsboro',
                                opacity: [1.0, 0.8, 0.7],
                            },
                        }} className="box">
                            <p><RxAvatar size={30} className="icon"/></p>
                            <p className="name">
                                {posting.writer} · <Time date={posting.createdDate}/> 
                            </p>
                            <p className="title">{posting.title}</p>
                            <p className="comment"><TfiCommentAlt size={20}/> {posting.reply}</p>
                            <p className="bookmark"><BsBookmarkStar size={20}/>{posting.bookmarks}</p>
                        </Box>
                        </div>
                    );
                })}
            </div>
        </>
    );
};
  
  export default Board;