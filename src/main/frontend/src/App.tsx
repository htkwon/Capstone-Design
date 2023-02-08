import React, { useEffect, useState } from "react";
import axios from "axios";
import Home from "./components/pages/Home";
import BoardWrite from "./components/pages/BoardWritePage";

function App() {
  const [test, setTest] = useState(""); // 연동 테스트용 state

  useEffect(() => {
    axios
      .get("/api/test")
      .then((response) => setTest(response.data))
      .catch((error) => console.log(error));
  }, []);

  // fetch
  /*
  useEffect(() => {
    fetch("/api/test")
      .then((response) => response.text())
      .then(setTest);
  }, []);
  */

  return (
    <>
    <div>프론트엔드 백엔드 연동 테스트 : {test}</div>
    {/*<Home/> //라우터 아직 적용안해놨습니다.(은서)*/}
    <BoardWrite/>
    </>
  )
}

export default App;
