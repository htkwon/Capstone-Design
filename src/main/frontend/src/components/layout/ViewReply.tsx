import React, { useState } from "react";
import { Typography } from "@mui/material";
import { Reply } from "../../model/board";
/**
 * 댓글창 보기 (이미 작성된 댓글)
 */
interface ViewReplyProps {
    reply: Reply;
}
const ViewReply = (props: ViewReplyProps) => {
  return (
    <>
      <Typography>{props.reply.nickname}</Typography>
      <Typography>{props.reply.article}</Typography>
    </>
  );
};

export default ViewReply;
