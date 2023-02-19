import React, { useState } from "react";
import { Button, TextField, Typography } from "@mui/material";
import { Reply } from "../../model/board";
/**
 * 새 댓글 생성
 */
const CreateReply = () => {
  return (
    <>
    <Typography>유저 닉네임</Typography>
      <TextField></TextField>
      <Button>submit</Button>
    </>
  );
};

export default CreateReply;