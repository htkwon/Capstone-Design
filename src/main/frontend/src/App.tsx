import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage";

function App() {
  return (
    <>
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="post" element={<BoardWrite/>}/>
      </Routes>
    </Router>
    </>
  )
}

export default App;
