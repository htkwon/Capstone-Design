import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage";
import BoardMain from "./components/pages/Board/Free/BoardMain";

function App() {
  return (
    <>
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="post" element={<BoardWrite/>}/>
        
        {/*<Route path="/free/*" element={<BoardMain boardData={여기에 자유게시판 데이터 들어가면됩니다.} />}>  그리고 localhost:3000/free 입력하고 자유게시판 화면 뜨면 될겁니다.
              <Route path="" element={<BoardMain boardData={} />} />
        </Route>*/}
      </Routes>
    </Router>
    </>
  )
}

export default App;