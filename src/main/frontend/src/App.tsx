import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage";
import BoardMain from "./components/pages/Board/Free/BoardMain";
import { freeBoard } from "./components/data/BoardData";

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
      </Routes>
    </Router>
    </>
  )
}

export default App;