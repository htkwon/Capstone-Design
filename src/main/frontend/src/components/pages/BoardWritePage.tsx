import React, { useState } from "react";
import Header from "../layout/Header";
import { SelectChangeEvent } from "@mui/material";
import {
  Select,
  TextField,
  Button,
  Grid,
  FormControl,
  MenuItem,
  InputLabel,
} from "@mui/material";
import { Container } from "@mui/system";
/*
 * 기본 게시글 작성 UI폼
 */
const BoardWrite = () => {
  const [boardType, setBoardType] = React.useState("");
  const handleChange = (event: SelectChangeEvent<unknown>) => {
    setBoardType(event.target.value as string);
  };
  return (
    <>
      <Container>
        <Header />
        <Grid container direction="column" spacing={2}>
          <Grid item>
            <FormControl style={{ minWidth: "120px" }}>
              <Select value={boardType} onChange={handleChange} size="small">
                <MenuItem value={"free"} defaultChecked>
                  자유게시판
                </MenuItem>
                <MenuItem value={"question"}>Q&A게시판</MenuItem>
                <MenuItem value={"recruit"}>구인게시판</MenuItem>
                <MenuItem value={"notice"}>공지사항</MenuItem>
              </Select>
            </FormControl>
          </Grid>
          <Grid item>
            <TextField
              className="board title"
              maxRows={1}
              placeholder={"제목"}
              fullWidth
            ></TextField>
          </Grid>
          <Grid item>
            <TextField
              className="board context"
              multiline
              rows={20}
              placeholder={"내용을 작성해주세요."}
              fullWidth
            ></TextField>
          </Grid>
          <Grid item>
            <Button
              className="board button"
              variant="outlined"
              size="small"
              disableElevation
            >
              게시
            </Button>
          </Grid>
        </Grid>
      </Container>
    </>
  );
};

/*
 * 구인 게시판에서 구인 조건을 작성할 때 사용
 */
const Condition = () => {
  return (
    <Grid container spacing={4} direction="column" justifyContent="space-around">
      <Grid item>
        <TextField
          required
          label="필수"
          placeholder="필수 조건을 기입하세요."
        ></TextField>
        <TextField
          label="우대 사항"
          placeholder="우대 사항을 기입하세요."
        ></TextField>
      </Grid>
    </Grid>
  );
};

export default BoardWrite;
