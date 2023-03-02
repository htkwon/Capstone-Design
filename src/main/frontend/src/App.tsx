import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage";
import BoardMain from "./components/pages/Board/Free/BoardMain";
import { freeBoard } from "./components/data/BoardData";
import QnABoard from "./components/pages/Board/QnABoard";
import RecruitBoard from "./components/pages/Board/RecruitBoard";
import MyPage from "./components/pages/MyPage";
import Notice from "./components/pages/Notice";

function App() {
  return (
    <>
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="post" element={<BoardWrite/>}/>
        <Route path="/free/*" element={<BoardMain boardData={freeBoard} />}> 
              <Route path="" element={<BoardMain boardData={freeBoard} />} />
        </Route>
        <Route path="/questions/*" element={<QnABoard/>}/>
        <Route path="/recruit/*" element={<RecruitBoard/>}/>
        <Route path="mypage" element={<MyPage/>}/>
        <Route path="notice" element={<Notice/>}/>
      </Routes>
    </Router>
    </>
  )
}

export default App;